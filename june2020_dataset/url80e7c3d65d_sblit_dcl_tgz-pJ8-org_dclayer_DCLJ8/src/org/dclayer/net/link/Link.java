package org.dclayer.net.link;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.dclayer.crypto.key.KeyPair;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.buf.DataByteBuf;
import org.dclayer.net.link.bmcp.BMCPManagementChannel;
import org.dclayer.net.link.bmcp.crypto.PacketCipher;
import org.dclayer.net.link.bmcp.crypto.PlainPacketCipher;
import org.dclayer.net.link.channel.Channel;
import org.dclayer.net.link.channel.ChannelCollection;
import org.dclayer.net.link.channel.data.ApplicationDataChannel;
import org.dclayer.net.link.channel.data.DataChannel;
import org.dclayer.net.link.channel.management.ManagementChannel;
import org.dclayer.net.link.component.LinkPacketHeader;
import org.dclayer.net.link.control.FlowControl;
import org.dclayer.net.link.control.discontinuousblock.DiscontinuousBlockCollection;
import org.dclayer.net.link.control.packetbackup.PacketBackup;

/**
 * a layer that is placed above the UDP socket and provides reliable and encrypted communication
 */
public class Link<T> implements HierarchicalLevel {
	
	public static enum Status {
		None,
		ConnectingActive(Status.CONNECTING),
		ConnectingPassive(Status.CONNECTING),
		InitializingActive(Status.INITIALIZING),
		InitializingPassive(Status.INITIALIZING),
		Connected(Status.CONNECTED),
		Disconnecting(Status.DISCONNECTING),
		Disconnected;
		
		public static final int CONNECTED = 1 << 0;
		public static final int CONNECTING = 1 << 1;
		public static final int INITIALIZING = 1 << 2;
		public static final int DISCONNECTING = 1 << 3;
		
		//
		
		private final boolean connected;
		private final boolean connecting;
		private final boolean initializing;
		private final boolean disconnecting;
		
		Status(int flags) {
			this.connected = (flags & CONNECTED) != 0;
			this.connecting = (flags & CONNECTING) != 0;
			this.initializing = (flags & INITIALIZING) != 0;
			this.disconnecting = (flags & DISCONNECTING) != 0;
		}
		
		Status() {
			this(0);
		}
		
		public boolean connected() {
			return connected;
		}
		
		public boolean connecting() {
			return connecting;
		}
		
		public boolean initializing() {
			return initializing;
		}
		
		public boolean disconnecting() {
			return disconnecting;
		}
		
	}
	
	public static enum CloseReason {
		Disconnect,
		Timeout,
		RemoteKill,
		ExceptionInCryptoInit,
		UnknownMessage
	}
	
	@Override
	public String toString() {
		return String.format("Link@%x", System.identityHashCode(this));
	}
	
	private HierarchicalLevel parentHierarchicalLevel;
	
	/**
	 * An {@link OnLinkActionListener} called upon opening of a new channel
	 */
	private OnLinkActionListener<T> onLinkActionListener;
	
	/**
	 * {@link LinkPacketHeader} for parsing inbound packets
	 */
	private LinkPacketHeader inLinkPacketHeader = new LinkPacketHeader();
	/**
	 * {@link LinkPacketHeader} for writing outbound packets
	 */
	private LinkPacketHeader outLinkPacketHeader = new LinkPacketHeader();
	
	/**
	 * the {@link ManagementChannel}
	 */
	private ManagementChannel managementChannel;
	
	private PacketCipher inPacketCipher = new PlainPacketCipher();
	private PacketCipher outPacketCipher = new PlainPacketCipher();
	
	private Data decryptedPacketData = new Data();
	private DataByteBuf readDataByteBuf = new DataByteBuf();
	
	/**
	 * a map mapping all {@link ApplicationDataChannel}s to their channel names
	 */
	private Map<String, ApplicationDataChannel> applicationChannelMap = new HashMap<String, ApplicationDataChannel>();
	/**
	 * the {@link ChannelCollection} containing all {@link Channel}s
	 */
	private ChannelCollection channelCollection = new ChannelCollection();
	
	/**
	 * the transmission rate regulating {@link FlowControl} instance for this {@link Link}
	 */
	private FlowControl flowControl;
	
	/**
	 * the total amount of bytes received on this {@link Link} so far
	 */
	private long numBytesReceived = 0;
	/**
	 * the value of {@link System#nanoTime()} at the point in time this {@link Link} started to receive packets
	 */
	private long startedReceiving = 0;
	
	/**
	 * the link's status
	 */
	private Status status = Status.None;
	
	/**
	 * {@link ReentrantLock} locked while receiving
	 */
	private ReentrantLock receiveLock = new ReentrantLock();
	/**
	 * {@link ReentrantLock} locked while sending
	 */
	private ReentrantLock sendLock = new ReentrantLock();
	
	private T referenceObject;
	private LinkSendInterface<T> linkSendInterface;
	
	private Data packetPrefixData;
	
	private boolean initiator = false;
	
	/**
	 * creates a new {@link Link}
	 * @param linkSendInterface the {@link LinkSendInterface} to use
	 * @param onOpenChannelRequestListener the {@link OnLinkActionListener} to use
	 */
	public Link(LinkSendInterface<T> linkSendInterface, OnLinkActionListener<T> onOpenChannelRequestListener, T referenceObject, HierarchicalLevel parentHierarchicalLevel) {
		this.linkSendInterface = linkSendInterface;
		this.flowControl = new FlowControl(this);
		this.onLinkActionListener = onOpenChannelRequestListener;
		this.referenceObject = referenceObject;
		this.parentHierarchicalLevel = parentHierarchicalLevel;
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return parentHierarchicalLevel;
	}
	
	public KeyPair getLinkCryptoInitializationKeyPair() {
		return onLinkActionListener.getLinkCryptoInitializationKeyPair();
	}
	
	public boolean isInitiator() {
		return initiator;
	}
	
	/**
	 * locks the receiveLock
	 * used by BMCPManagementChannel's ChannelBlockStatusRequest Thread
	 */
	public void lockReceive() {
		receiveLock.lock();
	}
	
	/**
	 * unlocks the receiveLock
	 * used by BMCPManagementChannel's ChannelBlockStatusRequest Thread
	 */
	public void unlockReceive() {
		receiveLock.unlock();
	}
	
	/**
	 * sets the status of this link
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		Status oldStatus = this.status;
		Log.msg(this, "setting status from %s to %s", this.status, status);
		this.status = status;
		onLinkActionListener.onLinkStatusChange(referenceObject, oldStatus, status);
	}
	
	/**
	 * returns the status of this link
	 * @return the status of this link
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * returns the {@link ChannelCollection} of this link
	 * @return the {@link ChannelCollection} of this link
	 */
	public ChannelCollection getChannelCollection() {
		return channelCollection;
	}
	
	/**
	 * Sets this {@link Link}'s data transmission rate to the given value
	 * @param bytesPerSecond the rate at which data is sent over this {@link Link}
	 */
	public void setFlowControlBytesPerSecond(long bytesPerSecond) {
		flowControl.setBytesPerSecond(bytesPerSecond);
	}
	
	/**
	 * @return the current maximum rate at which data can be sent over this {@link Link} 
	 */
	public long getFlowControlBytesPerSecond() {
		return flowControl.getBytesPerSecond();
	}
	
	/**
	 * @return the total amount of bytes received on this {@link Link} so far
	 */
	public long getNumBytesReceived() {
		return numBytesReceived;
	}
	
	/**
	 * @return the total amount of bytes sent on this {@link Link} so far
	 */
	public long getNumBytesSent() {
		return flowControl.getNumBytesSent();
	}
	
	/**
	 * @return the value of {@link System#nanoTime()} at the point in time this {@link Link} started to receive packets
	 */
	public long getStartedReceiving() {
		return startedReceiving;
	}
	
	/**
	 * onReceive callback, called when a new packet was received for this link
	 * @param data the {@link ByteBuf} containing the packet data
	 * @return true if this packet was received successfully, false otherwise
	 */
	// no need for synchronization, receiveLinkPacket() locks receiveLock
	public boolean onReceive(Data data) {
		
		Log.debug(this, "onReceive()");
		
		if(startedReceiving == 0) {
			startedReceiving = System.nanoTime();
		}
		
		try {
			
			receiveLinkPacket(data);
			
		} catch(ParseException e) {
			Log.exception(this, e);
			return false;
		} catch(BufException e) {
			Log.exception(this, e);
			return false;
		} catch(CryptoException e) {
			Log.exception(this, e);
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * parses the received link packet
	 * @param data the {@link ByteBuf} containing the link packet data
	 * @throws ParseException
	 * @throws BufException
	 * @throws CryptoException
	 */
	// locks receiveLock; called by onReceive() only
	private void receiveLinkPacket(Data data) throws ParseException, BufException, CryptoException {
		
		receiveLock.lock();
		
		Data decryptedPacketData = inPacketCipher.decrypt(data, this.decryptedPacketData);
		readDataByteBuf.setData(decryptedPacketData);
		
		inLinkPacketHeader.read(readDataByteBuf);
		
		long channelId = inLinkPacketHeader.getChannelId().getNum();
		long dataId = inLinkPacketHeader.getDataId().getNum();
		int length = (int) inLinkPacketHeader.getChannelDataLength().getNum();
		
		int linkPacketLength = inLinkPacketHeader.length() + length;
		this.numBytesReceived += linkPacketLength;
		
		Log.debug(this, "received (%d bytes): %s", linkPacketLength, inLinkPacketHeader.represent(true));
		
		Channel channel;
		
		if(managementChannel == null) {
			
			// first create and add the management channel, then unlock
			managementChannel = new BMCPManagementChannel(this, channelId, -1, "mgmt");
			putChannel(managementChannel);
			channel = managementChannel;
			
			receiveLock.unlock();
			
		} else {
			
			// safe to unlock here (ChannelCollection is synchronized)
			receiveLock.unlock();
			
			channel = channelCollection.get(channelId);
			
		}
		
		if(channel != null) {
			Log.debug(this, "dataId %d: calling channel.receiveLinkPacketBody()", dataId);
			if(!channel.isOpen()) {
				Log.msg(this, "channel %s is not open, opening...", channel);
				channel.open(false);
			}
			channel.receiveLinkPacketBody(dataId, channelId, readDataByteBuf, length);
		} else {
			Log.msg(this, "dataId %d: no channel with channelId %d, ignoring", dataId, channelId);
		}
			
	}
	
	/**
	 * reports the existence of a gap in one of this {@link Link}'s channels' {@link DiscontinuousBlockCollection} to the management channel upon receipt of a packet
	 */
	public void onGapReceive() {
		this.managementChannel.onGapReceive();
	}
	
	/**
	 * sets and applies a new outbound {@link PacketCipher}
	 * @param outPacketCipher the new outbound packet cipher to apply
	 */
	// locks sendLock
	public void applyNewOutPacketCipher(PacketCipher outPacketCipher) {
		Log.debug(this, "applying new outbound packet cipher: %s", outPacketCipher);
		sendLock.lock();
		this.outPacketCipher = outPacketCipher;
		sendLock.unlock();
	}
	
	/**
	 * sets and applies a new inbound {@link PacketCipher}
	 * @param inPacketCipher the new inbound packet cipher to apply
	 */
	// locks receiveLock
	public void applyNewInPacketCipher(PacketCipher inPacketCipher) {
		Log.debug(this, "applying new inbound packet cipher: %s", inPacketCipher);
		receiveLock.lock();
		this.inPacketCipher = inPacketCipher;
		receiveLock.unlock();
	}
	
	/**
	 * called when peer wants to open a new channel
	 * @param channelId the channelId of the new channel
	 * @param protocol the protocol of the new channel
	 * @return true if channel opening is permitted, false otherwise
	 */
	// locks receiveLock; newChannel is isolated and ChannelCollection (which is being added to in putChannel) is synchronized
	// called by the ManagementChannel only
	public boolean onOpenChannelRequest(long channelId, String protocol) {
		
		receiveLock.lock();
		
		Channel channel = newChannel(channelId, protocol);
		if(channel != null) {
			putChannel(channel);
			channel.open(false);
		}
		
		receiveLock.unlock();
		
		return channel != null;
		
	}
	
	//
	
	/**
	 * writes the header and the encrypted payload to the given out packet {@link Data} object
	 * @param dataId the data id for this packet
	 * @param channelId the channel id of the channel this packet belongs to
	 * @param payloadPacketComponent a {@link PacketComponentI} object containing the payload data
	 * @param outPacketData the {@link Data} object the header should be written to
	 * @throws BufException
	 */
	// locks sendLock
	public void writePacket(long dataId, long channelId, PacketComponentI payloadPacketComponent, Data outPacketData) throws BufException, CryptoException {
		
		sendLock.lock();
		
		outLinkPacketHeader.setDataId(dataId);
		outLinkPacketHeader.setChannelId(channelId);
		outLinkPacketHeader.setChannelDataLength(payloadPacketComponent.length());
		
		Log.debug(this, "sending: %s", outLinkPacketHeader.represent(true));
		
		outPacketCipher.encrypt(outPacketData, packetPrefixData, outLinkPacketHeader, payloadPacketComponent);
		this.packetPrefixData = null;
		
		sendLock.unlock();
	
	}
	
	/**
	 * Queues the given {@link PacketBackup} for transmission
	 * @param packetBackup the {@link PacketBackup} to queue for transmission
	 */
	// no need for synchronization
	public void send(PacketBackup packetBackup) {
		
		send(packetBackup, false);
		
	}
	
	/**
	 * Queues the given {@link PacketBackup} for transmission
	 * @param packetBackup the {@link PacketBackup} to queue for transmission
	 * @param waitTilSent pass true if this operation should block until the {@link PacketBackup} is actually sent, pass false otherwise
	 */
	public void send(PacketBackup packetBackup, boolean waitTilSent) {
		
		flowControl.queue(packetBackup, waitTilSent);
		
	}
	
	/**
	 * called by the {@link FlowControl} when finally sending a packet
	 * @param data the {@link Data} containing the packet
	 */
	public void transmitNow(Data data) {
		linkSendInterface.sendLinkPacket(referenceObject, data);
	}
	
	public void onConnected() {

		Log.debug(this, "connected");
		setStatus(Status.Connected);
		
	}
	
	/**
	 * callback for when:<br />
	 * a) the remote disconnects, or<br />
	 * b) we initiated the disconnect and the remote confirmed
	 */
	public void onDisconnected() {
		
		Log.debug(this, "disconnected, current status: %s", status);
		setStatus(Status.Disconnected);
		
		closeChannels();
		
	}
	
	public void kill(CloseReason closeReason) {
		
		Log.debug(this, "killed, current status %s, close reason: %s", status, closeReason);
		setStatus(Status.Disconnected);
		
		closeChannels();
		
	}
	
	private void closeChannels() {
		synchronized(channelCollection) {
			for(Channel channel : channelCollection.getChannels()) {
				channel.onClose();
			}
		}
	}
	
	//
	
	/**
	 * connects this link
	 */
	// locks receiveLock
	public void connect(Data firstLinkPacketPrefixData) {
		
		initiator = true;
		
		receiveLock.lock();
		
		setStatus(Status.ConnectingActive);
		
		int channelId = (int)(Math.random() * Integer.MAX_VALUE);
		
		BMCPManagementChannel bmcpManagementChannel = new BMCPManagementChannel(this, channelId, -1, "mgmt");
		managementChannel = bmcpManagementChannel;
		channelCollection.put(managementChannel);
		// connect() starts the channel
		
		if(firstLinkPacketPrefixData != null) {
			this.packetPrefixData = firstLinkPacketPrefixData;
		}
		
		bmcpManagementChannel.open(true);
		
		receiveLock.unlock();
		
	}
	
	/**
	 * connects this link
	 */
	public void connect() {
		connect(null);
	}
	
	/**
	 * tells the management channel to disconnects this link
	 */
	public void disconnect() {
		
		managementChannel.disconnect();
		
	}
	
	public void abortCryptoInitialization(Exception e) {
		managementChannel.onAbortCryptoInitialization(e);
	}
	
	/**
	 * returns a new {@link DataChannel} object for the given protocol identifier and channel id
	 * @param channelId the channel id of the new {@link DataChannel}
	 * @param protocol the protocol identifier of the protocol in the new {@link DataChannel}
	 * @return the new {@link DataChannel}
	 */
	// no synchronization needed
	private DataChannel newChannel(long channelId, String protocol) {
		return onLinkActionListener.onOpenChannelRequest(referenceObject, channelId, protocol);
	}
	
	/**
	 * adds a new channel to this link
	 * @param channel the channel to add
	 */
	// no synchronization needed; called from receiveLinkPacket, onOpenChannelRequest and openChannel, which all lock receiveLock, only
	private void putChannel(Channel channel) {
		if(channel instanceof ApplicationDataChannel) {
			applicationChannelMap.put(channel.getChannelName(), (ApplicationDataChannel) channel);
		}
		channelCollection.put(channel);
	}
	
	/**
	 * additionally reassigns the given channel to the channel collection using its unreliable channel id
	 * @param channel the {@link Channel} to reassign using its unreliable channel id
	 */
	public void addUnreliableChannelId(Channel channel) {
		channelCollection.assign(channel.getUnreliableChannelId(), channel);
	}
	
	/**
	 * opens a new {@link DataChannel} on this link
	 * @param protocol the protocol identifier of the protocol to use on the new channel
	 * @return the newly added channel
	 */
	// locks receiveLock
	public DataChannel openChannel(String protocol) {
		
		long channelId;
		
		receiveLock.lock();
		
		do {
			channelId = (long)(Math.random() * Integer.MAX_VALUE);
		} while(channelCollection.get(channelId) != null);
		
		DataChannel channel = newChannel(channelId, protocol);
		
		if(channel != null) {
			
			Log.msg(this, "opening new channel: channelId %d, protocol: %s", channelId, protocol);
			putChannel(channel);
			managementChannel.requestOpenChannel(channelId, protocol);
			
		} else {
			
			Log.msg(this, "can't open new channel for protocol: %s", protocol);
			
		}
		
		receiveLock.unlock();
		
		return channel;
		
	}
	
	/**
	 * returns the channel on this link matching the given name
	 * @param channelName the name of the channel to return
	 * @return the channel with a matching name
	 */
	public ApplicationDataChannel getChannel(String channelName) {
		return applicationChannelMap.get(channelName);
	}
	
}
