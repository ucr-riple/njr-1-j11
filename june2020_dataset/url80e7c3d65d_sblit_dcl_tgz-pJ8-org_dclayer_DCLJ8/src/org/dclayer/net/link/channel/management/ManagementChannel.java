package org.dclayer.net.link.channel.management;
import java.util.concurrent.locks.ReentrantLock;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.buf.DataByteBuf;
import org.dclayer.net.link.Link;
import org.dclayer.net.link.channel.Channel;
import org.dclayer.net.link.channel.component.ChannelDataComponent;
import org.dclayer.net.link.control.FlowControl;
import org.dclayer.net.link.control.ResendPacketQueue;
import org.dclayer.net.link.control.discontinuousblock.DiscontinuousBlock;
import org.dclayer.net.link.control.discontinuousblock.DiscontinuousBlockCollection;
import org.dclayer.net.link.control.idcollection.IdCollection;
import org.dclayer.net.link.control.packetbackup.PacketBackup;
import org.dclayer.net.link.control.packetbackup.PacketBackupCollection;
import org.dclayer.net.link.control.packetbackup.UnreliablePacketBackupCollection;

/**
 * an abstract base class for management channel implementations
 */
public abstract class ManagementChannel extends Channel {
	
	/**
	 * {@link IdCollection} of all sent data ids
	 */
	private IdCollection sentDataIdCollection = new IdCollection();
	/**
	 * {@link PacketBackupCollection} of sent packets
	 */
	private PacketBackupCollection sentPacketBackupCollection = new PacketBackupCollection(this, 1024);
	/**
	 * {@link UnreliablePacketBackupCollection} of {@link PacketBackup}s used for unreliable messages
	 */
	private UnreliablePacketBackupCollection unreliablePacketBackupCollection = new UnreliablePacketBackupCollection(this, 1024);
	/**
	 * {@link IdCollection} of all received data ids
	 */
	private IdCollection receivedDataIdCollection = new IdCollection();
	/**
	 * {@link DiscontinuousBlockCollection} of received packets
	 */
	private DiscontinuousBlockCollection receivedDiscontinuousBlockCollection = new DiscontinuousBlockCollection(this, 1024);
	
	private DataByteBuf readDataByteBuf = new DataByteBuf();
	
	/**
	 * {@link ReentrantLock} locked while receiving
	 */
	private ReentrantLock receiveLock = new ReentrantLock();
	/**
	 * {@link ReentrantLock} locked while sending
	 */
	private ReentrantLock sendLock = new ReentrantLock();
	
	/**
	 * the data id of the last sent packet
	 */
	private long dataId = -1;
	
	/**
	 * the channel id to use when sending unreliable channel messages over this channel
	 */
	private long unreliableChannelId;
	
	private boolean exitResendThread = false;
	
	/**
	 * {@link ResendPacketQueue} for automatically resending lost packets
	 */
	private ResendPacketQueue resendPacketQueue = new ResendPacketQueue(this);
	/**
	 * Thread that resends queued packet from the {@link ResendPacketQueue}
	 */
	private Thread resendThread = new Thread() {
		@Override
		public void run() {
			for(;;) {
				// wait until the next PacketBackup is due for resend
				PacketBackup packetBackup = resendPacketQueue.waitAndPopNext();
				boolean resend = packetBackup.getResendPacketQueueProperties().resend(ManagementChannel.this);
				Log.debug(ManagementChannel.this, "%s, resend=%s", packetBackup, resend);
				if(!resend && resendPacketQueue.timeout()) {
					// timeout
					timeout();
					return;
				} else if(exitResendThread) {
					Log.debug(ManagementChannel.this, "resend thread: exiting");
					return;
				} else {
					// resend this packet
					resend(packetBackup);
					resendPacketQueue.queue(packetBackup); // requeue the packet
				}
			}
		}
	};
	
	public ManagementChannel(Link link, long channelId, long unreliableChannelId, String channelName) {
		super(link, channelId, channelName);
		this.unreliableChannelId = unreliableChannelId;
	}
	
	public void exitResendThread() {
		exitResendThread = true;
	}
	
	@Override
	public long getUnreliableChannelId() {
		return unreliableChannelId;
	}
	
	public void setUnreliableChannelId(long unreliableChannelId) {
		this.unreliableChannelId = unreliableChannelId;
	}
	
	@Override
	public IdCollection getSentDataIdCollection() {
		return null;
	}
	
	@Override
	public PacketBackupCollection getSentPacketBackupCollection() {
		return sentPacketBackupCollection;
	}
	
	@Override
	public IdCollection getReceivedDataIdCollection() {
		return null;
	}
	
	// locks receiveLock
	@Override
	public void receiveLinkPacketBody(long dataId, long channelId, ByteBuf byteBuf, int length) throws BufException {

		receiveLock.lock();
		
		if(channelId == unreliableChannelId) {
			receiveUnreliableLinkPacketBody(dataId, byteBuf, length);
		} else {
			receiveReliableLinkPacketBody(dataId, byteBuf, length);
		}

		receiveLock.unlock();
		
	}
	
	// no need for synchronization here, this is called inside receiveLinkPacketBody
	private void receiveReliableLinkPacketBody(long dataId, ByteBuf byteBuf, int length) throws BufException {
		
		long dataIdRead = receivedDiscontinuousBlockCollection.getDataIdRead();

		if(dataId < dataIdRead) {

			DiscontinuousBlock block = receivedDiscontinuousBlockCollection.get(dataId);

			if(block != null && block.getReplyPacketBackup() != null) {

				// this block was received already and has a stored response, reply with the same packet
				Log.debug(this, "already replied to dataId %d, resending reply", dataId);
				resend(block.getReplyPacketBackup());
				
			} else {
				
				Log.msg(this, "received a dataId (%d) below this channel's dataIdRead (%d) and found no linked reply (discontinuousBlock=%s), ignoring", dataId, dataIdRead, block);
				
			}

		} else {

			receivedDataIdCollection.add(dataId);

			if(receivedDiscontinuousBlockCollection.put(dataId, byteBuf, length)) {

				// put() returned true, we've just written the next block to read
				do {

					long curDataId = receivedDiscontinuousBlockCollection.getDataIdRead();
					DiscontinuousBlock discontinuousBlock = receivedDiscontinuousBlockCollection.readFirst();

					Log.debug(this, "reading dataId %d (discontinuousBlock=%s)...", curDataId, discontinuousBlock);

					read(curDataId, discontinuousBlock);

				} while(receivedDiscontinuousBlockCollection.available());

			} else {

				Log.debug(this, "saved dataId %d, missing block(s) inbetween (dataIdRead=%d)", dataId, receivedDiscontinuousBlockCollection.getDataIdRead());
				
				getLink().onGapReceive();
				
			}

		}
		
	}
	
	// no need for synchronization here, this is called inside receiveLinkPacketBody
	private void receiveUnreliableLinkPacketBody(long dataId, ByteBuf byteBuf, int length) throws BufException {
		
		readCommand(null, dataId, byteBuf);
		
	}
	
	/**
	 * reads the packet body
	 * @param byteBuf the {@link ByteBuf} holding packet body data
	 * @param length the length of the packet body data
	 * @throws BufException
	 */
	// no need for synchronization, this is called by receiveReliableLinkPacketBody, which is called by receiveLinkPacketBody, which locks receiveLock
	private void read(long dataId, DiscontinuousBlock discontinuousBlock) throws BufException {
		
		final Data data = discontinuousBlock.getData();
		
		readDataByteBuf.setData(data);
		readCommand(discontinuousBlock, dataId, readDataByteBuf);
		
	}
	
	protected long send(PacketComponentI payloadPacketComponent) {
		return send(payloadPacketComponent, null);
	}
	
	// locks sendLock
	protected long send(PacketComponentI payloadPacketComponent, DiscontinuousBlock discontinuousBlock) {
		
		sendLock.lock();
		
		// TODO optimize (remove if, call initDataId() where appropriate)
		if(this.dataId < 0) this.dataId = (int)(Math.random() * Integer.MAX_VALUE);
		else this.dataId++;
		long dataId = this.dataId;
		
		sentDataIdCollection.add(dataId);
		
		PacketBackup packetBackup = sentPacketBackupCollection.put(dataId, getChannelId(), FlowControl.PRIO_MGMT);
		Data outPacketData = packetBackup.getPacketProperties().data;
		
		Log.debug(this, "sending (discontinuousBlock=%s): %s", discontinuousBlock, payloadPacketComponent.represent(true));
		
		try {
			getLink().writePacket(dataId, getChannelId(), payloadPacketComponent, outPacketData);
		} catch (Exception e) {
			Log.exception(this, e);
			sendLock.unlock();
			return 0;
		}
		
		packetBackup.getPacketProperties().ready = true;
		
		sendLock.unlock();
		
		if(discontinuousBlock == null) {
			packetBackup.getResendPacketQueueProperties().setResend(System.nanoTime()/1000000L, 1000, 1.0, 8, resendPacketQueue);
		} else {
			discontinuousBlock.setReplyPacketBackup(packetBackup);
		}
		
		getLink().send(packetBackup);
		
		return dataId;
		
	}
	
	/**
	 * unreliably sends the given {@link PacketComponentI}
	 * @param payloadPacketComponent the {@link PacketComponentI} to send
	 */
	// locks sendLock
	protected long sendUnreliable(PacketComponentI payloadPacketComponent) {
		
		sendLock.lock();
		
		long dataId = (int)(Math.random() * Integer.MAX_VALUE);
		
		PacketBackup packetBackup = unreliablePacketBackupCollection.get(dataId, getUnreliableChannelId(), FlowControl.PRIO_MGMT);
		Data data = packetBackup.getPacketProperties().data;
		
		Log.debug(this, "sending unreliably: %s", payloadPacketComponent.represent(true));
		
		try {
			getLink().writePacket(dataId, getUnreliableChannelId(), payloadPacketComponent, data);
		} catch (Exception e) {
			Log.exception(this, e);
			sendLock.unlock();
			return 0;
		}
		
		packetBackup.getPacketProperties().ready = true;
		
		sendLock.unlock();
		
		getLink().send(packetBackup);
		
		return dataId;
		
	}
	
	/**
	 * clears the specified {@link PacketBackup}s from this channel's {@link PacketBackupCollection}
	 * @param startDataId
	 * @param numFollowing
	 */
	protected void clear(long startDataId, int numFollowing) {
		sentPacketBackupCollection.clear(startDataId, numFollowing);
	}
	
	/**
	 * tries to clears the specified {@link PacketBackup}s from this channel's {@link PacketBackupCollection}
	 * @param dataId the data id to try to clear
	 */
	protected void tryClear(long dataId) {
		sentPacketBackupCollection.tryClear(dataId);
	}
	
	private void timeout() {
		
		receiveLock.lock();
		
		onTimeout();
		getLink().kill(Link.CloseReason.Timeout);
		
		receiveLock.unlock();
		
	}
	
	@Override
	public final void onOpen(boolean initiator) {
		resendThread.start();
		onOpenChannel(initiator);
	}
	
	/**
	 * called when the channel is actually opened
	 * @param initiator true if we requested this channel, false if the remote requested it
	 */
	protected abstract void onOpenChannel(boolean initiator);
	/**
	 * called upon receipt of a command
	 * @param discontinuousBlock the {@link DiscontinuousBlock} of this message
	 * @param dataId the data id of this message
	 * @param byteBuf the ByteBuf holding the command
	 * @throws BufException
	 */
	protected abstract void readCommand(DiscontinuousBlock discontinuousBlock, long dataId, ByteBuf byteBuf) throws BufException;
	/**
	 * requests a channel opening from the peer
	 * @param channelId the id of the new channel
	 * @param protocol the protocol of the new channel
	 */
	public abstract void requestOpenChannel(long channelId, String protocol);
	
	/**
	 * called by the link, starts disconnect communication.
	 * link will be killed by the management channel when disconnecting is done
	 */
	public abstract void disconnect();
	
	public abstract void onAbortCryptoInitialization(Exception e);
	
	/**
	 * reports the existence of a gap in the {@link DiscontinuousBlockCollection} of one of the channels upon packet receipt<br />
	 * called every time a packet is received and a gap exists
	 */
	public abstract void onGapReceive();
	
	public abstract void onTimeout();
	
}
