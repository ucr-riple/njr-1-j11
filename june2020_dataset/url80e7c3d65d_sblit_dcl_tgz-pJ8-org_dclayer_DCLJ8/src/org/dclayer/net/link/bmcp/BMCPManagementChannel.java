package org.dclayer.net.link.bmcp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.meta.Log;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.component.ArrayPacketComponent;
import org.dclayer.net.component.FlexNum;
import org.dclayer.net.link.Link;
import org.dclayer.net.link.Link.CloseReason;
import org.dclayer.net.link.Link.Status;
import org.dclayer.net.link.bmcp.component.ChannelReportComponent;
import org.dclayer.net.link.bmcp.component.IdBlock;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitPacketComponentI;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitializationMethod;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitializer;
import org.dclayer.net.link.bmcp.crypto.init.component.CryptoInitializerIdentifierComponent;
import org.dclayer.net.link.bmcp.message.AckMessage;
import org.dclayer.net.link.bmcp.message.BMCPMessage;
import org.dclayer.net.link.bmcp.message.ChangeProtocolRequestMessage;
import org.dclayer.net.link.bmcp.message.ChannelBlockStatusReportMessage;
import org.dclayer.net.link.bmcp.message.ChannelBlockStatusRequestMessage;
import org.dclayer.net.link.bmcp.message.ConnectReplyMessage;
import org.dclayer.net.link.bmcp.message.ConnectRequestMessage;
import org.dclayer.net.link.bmcp.message.CryptoInitMessage;
import org.dclayer.net.link.bmcp.message.DisconnectMessage;
import org.dclayer.net.link.bmcp.message.KillMessage;
import org.dclayer.net.link.bmcp.message.OpenChannelRequestMessage;
import org.dclayer.net.link.bmcp.message.ThrottleMessage;
import org.dclayer.net.link.channel.Channel;
import org.dclayer.net.link.channel.ChannelCollection;
import org.dclayer.net.link.channel.management.ManagementChannel;
import org.dclayer.net.link.control.discontinuousblock.DiscontinuousBlock;
import org.dclayer.net.link.control.idcollection.IdBoundary;
import org.dclayer.net.link.control.idcollection.IdCollection;
import org.dclayer.net.link.control.packetbackup.PacketBackup;
import org.dclayer.net.link.control.packetbackup.PacketBackupCollection;
import org.dclayer.net.packetcomponent.OnReceive;

/**
 * a {@link ManagementChannel} implementation using
 * the basic management channel protocol 
 */
public class BMCPManagementChannel extends ManagementChannel {
	
	private class AckCallbackRunnable implements Runnable {
		
		private long dataId;
		private boolean hasDataId = false;
		private boolean ack = false;
		
		private void clear() {
			BMCPManagementChannel.this.clear(dataId, 0);
		}
		
		public synchronized void run() {
			if(hasDataId) {
				clear();
			} else {
				ack = true;
			}
		}
		
		protected synchronized void setDataId(long dataId) {
			this.dataId = dataId;
			if(ack) {
				clear();
			} else {
				this.hasDataId = true;
			}
		}
		
	}
	
	private final CryptoInitializationMethod[] cryptoInitializationMethods = CryptoInitializationMethod.getPubliclyUsableCryptoInitMethods();
	
	/**
	 * {@link ReentrantLock} locked while receiving
	 */
	private ReentrantLock receiveLock = new ReentrantLock();
	/**
	 * {@link ReentrantLock} locked while sending
	 */
	private ReentrantLock sendLock = new ReentrantLock();
	
	private BMCPMessage inBMCPMessage = new BMCPMessage(this);
	private BMCPMessage outBMCPMessage = new BMCPMessage();
	
	private DiscontinuousBlock discontinuousBlock;
	private long dataId;
	
	private CryptoInitializer cryptoInitializer;
	
	/**
	 * the currently pending data id
	 */
	private long currentPendingDataId = -1;
	/**
	 * the data id of the current block status request
	 */
	private long channelBlockStatusRequestDataId = -1;
	/**
	 * the data id of the current disconnect command
	 */
	private long disconnectDataId = -1;
	
	private HashMap<Long, Runnable> onAckCallbacks = new HashMap<>();
	
	/**
	 * the {@link System#nanoTime()} value at the point in time the last throttle message was sent 
	 */
	private long lastThrottleSent = 0;
	/**
	 * the amount of total bytes received on the {@link Link} when the last throttle message was sent
	 */
	private long lastNumBytesReceived = 0;
	/**
	 * the minimum time in nanoseconds to wait after sending a throttle message before sending the next throttle message 
	 */
	private long throttleSendDelayNanos = 250000000L;
	
	private long lastThrottleReceived = 0;
	private long lastNumBytesSent = 0;
	
	/**
	 * if set to true, will disconnect as soon as connected.
	 * (used if disconnect() is called during connection initiation)
	 */
	private boolean disconnect = false;
	
	/**
	 * a Thread for periodically requesting the channel block status from the peer
	 */
	private Thread blockStatusThread = new Thread() {
		@Override
		public void run() {
			for(;;) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					Log.exception(BMCPManagementChannel.this, e, "exception in BlockStatusThread");
				}
				Log.debug(BMCPManagementChannel.this, "BlockStatusThread: ChannelCollection: %s", getLink().getChannelCollection().represent(true));
				if(getLink().getStatus() == Link.Status.Connected && channelBlockStatusRequestDataId == -1) {
					requestChannelBlockStatus();
				} else if(getLink().getStatus() == Link.Status.Disconnected) {
					Log.debug(BMCPManagementChannel.this, "BlockStatusThread: exiting");
					return;
				}
			}
		}
	};
	
	/**
	 * creates a new {@link BMCPChannelDataComponent}
	 * @param link the {@link Link} to create this channel on
	 * @param channelId the channel id for this channel
	 * @param channelName the name for this channel
	 */
	public BMCPManagementChannel(Link link, long channelId, long unreliableChannelId, String channelName) {
		super(link, channelId, unreliableChannelId, channelName);
	}
	
	@Override
	public void onOpenChannel(boolean initiator) {
		blockStatusThread.start();
		if(initiator) connect();
	}
	
	// locks receiveLock (theoretically, as long as ManegementChannel synchronizes calls to its read() function, no synchronization is needed here)
	// this is where messages arrive, called from the ManagementChannel superclass
	@Override
	protected void readCommand(DiscontinuousBlock discontinuousBlock, long dataId, ByteBuf byteBuf) throws BufException {
		
		receiveLock.lock();
		
		this.discontinuousBlock = discontinuousBlock;
		this.dataId = dataId;
		
		try {
			inBMCPMessage.read(byteBuf);
		} catch (ParseException e) {
			Log.exception(this, e, "ParseException while reading message");
			kill(CloseReason.UnknownMessage);
			receiveLock.unlock();
			return;
		}
		
		Log.debug(this, "received: %s", inBMCPMessage.represent(true));
		inBMCPMessage.callOnReceive();
		
		receiveLock.unlock();
		
	}
	
	@Override
	public void onGapReceive() {
		
		long now = System.nanoTime();
		long relativeNanos = now - lastThrottleSent;
		
		if(lastThrottleSent == 0 || relativeNanos > throttleSendDelayNanos) {
			
			long totalBytes = getLink().getNumBytesReceived();
			
			if(lastThrottleSent == 0) {
				
				Log.debug(this, "starting receive rate measuring, sending throttle with maximum transmission rate 0");
				sendThrottle(0);
				
			} else {
				
				long relativeBytes = totalBytes - lastNumBytesReceived;
				long bytesPerSecond_ = (1000000000L*relativeBytes)/relativeNanos;
				long bytesPerSecond = Math.max(10, bytesPerSecond_); // less makes no sense
				
				Log.debug(this, "sending throttle, maximum transmission rate (bytes per second): %d (calculated %d)", bytesPerSecond, bytesPerSecond_);
				
				sendThrottle(bytesPerSecond);
				
			}
			
			lastThrottleSent = now;
			lastNumBytesReceived = totalBytes;
			
		}
		
	}
	
	private long send() {
		return send((DiscontinuousBlock) null);
	}
	
	private long send(DiscontinuousBlock discontinuousBlock) {
		return send(outBMCPMessage, discontinuousBlock);
	}
	
	//
	
	private void setCryptoInitializationMethod(CryptoInitializationMethod cryptoInitializationMethod) {
		Log.msg(this, "applying crypto initialization method: %s", cryptoInitializationMethod);
		this.cryptoInitializer = cryptoInitializationMethod.make(getLink());
		this.inBMCPMessage.cryptoInit.setCryptoInitPacketComponent(cryptoInitializer.getReceivePacketComponent());
	}
	
	//
	
	private void processCryptoInitializationMessage() {
		
		boolean ack = cryptoInitializer.processPacketComponent();
		
		if(ack) {
			ack();
		}
		
	}
	
	private boolean sendCryptoInitializationMessages() {
		
		for(;;) {
			
			AckCallbackRunnable ackCallbackRunnable = new AckCallbackRunnable();
			
			CryptoInitPacketComponentI cryptoInitPacketComponent = cryptoInitializer.getSendPacketComponent(ackCallbackRunnable);
			if(cryptoInitPacketComponent == null) break;
			
			Log.debug(this, "sending crypto initialization message: %s", cryptoInitPacketComponent.represent(true));
			long dataId = sendCryptoInit(cryptoInitPacketComponent);
			
			ackCallbackRunnable.setDataId(dataId);
			
		}
		
		return cryptoInitializer.completedSuccessfully();
		
	}
	
	private void cryptoInitialization() {
		
		boolean completedSuccessfully = sendCryptoInitializationMessages();
		
		if(completedSuccessfully) {
			getLink().setStatus(Status.Connected);
		}
		
	}
	
	//
	
	private void ack() {
		sendAck(dataId);
	}
	
	private void onAck(long dataId, Runnable runnable) {
		Log.debug(this, "adding on ack callback for data id %d", dataId);
		onAckCallbacks.put(dataId, runnable);
	}
	
	private void runOnAckCallback(long dataId) {
		
		Runnable runnable = onAckCallbacks.remove(dataId);
		if(runnable != null) {
			Log.debug(this, "running on ack callback for data id %d", dataId);
			runnable.run();
		} else {
			Log.debug(this, "no on ack callback for data id %d", dataId);
		}
		
	}
	
	//
	
	@OnReceive
	public void onReceiveConnectRequest(ConnectRequestMessage connectRequestMessage) {
		
		Log.msg(this, "onReceiveConnectRequest, link status: %s", getLink().getStatus());
		if(getLink().getStatus() != Link.Status.None) {
			Log.msg(this, "ignoring connect request, link status != %s", Link.Status.None);
			return;
		}
		
		CryptoInitializationMethod cryptoInitializationMethod = null;
		
		for(CryptoInitializerIdentifierComponent cryptoInitializerIdentifierComponent : connectRequestMessage.cryptoInitializerIdentifierComponents) {
			if(cryptoInitializerIdentifierComponent.getCryptoInitializationMethod() != null
					&& cryptoInitializerIdentifierComponent.getCryptoInitializationMethod().publiclyUsable()) {
				cryptoInitializationMethod = cryptoInitializerIdentifierComponent.getCryptoInitializationMethod();
				break;
			}
		}
		
		if(cryptoInitializationMethod == null) {
			Log.warning(this, "ignoring connect request, no acceptable crypto initialization methods specified by remote: %s", connectRequestMessage.represent(true));
			return;
		}
		
		setCryptoInitializationMethod(cryptoInitializationMethod);
		
		getLink().setStatus(Link.Status.ConnectingPassive);
		
		int unreliableChannelId;
		do { unreliableChannelId = (int)(Math.random() * Integer.MAX_VALUE); } while(unreliableChannelId == getChannelId());
		this.setUnreliableChannelId(unreliableChannelId);
		getLink().addUnreliableChannelId(this);
		
		sendConnectReply(cryptoInitializationMethod, unreliableChannelId);
		
	}
	
	@OnReceive
	public void onReceiveConnectReply(ConnectReplyMessage connectReplyMessage) {
		
		Log.msg(this, "onReceiveConnectReply, link status: %s", getLink().getStatus());
		if(getLink().getStatus() != Link.Status.ConnectingActive) {
			Log.msg(this, "ignoring connect echo request, link status != %s", Link.Status.ConnectingActive);
			return;
		}
		
		// remove the connect request message that is currently being actively resent from the sent-PacketBackupCollection
		clear(currentPendingDataId, 0);
		
		CryptoInitializationMethod cryptoInitializationMethod = connectReplyMessage.cryptoInitializerIdentifierComponent.getCryptoInitializationMethod();
		// TODO further check if crypto init method is ok
		
		if(cryptoInitializationMethod == null) {
			Log.warning(this, "ignoring connect reply, inacceptable crypto initialization method specified by remote: %s", connectReplyMessage.represent(true));
			// TODO cancel connecting
			return;
		}
		
		getLink().setStatus(Link.Status.InitializingActive);
		
		long unreliableChannelId = connectReplyMessage.unreliableChannelId.getNum();
		this.setUnreliableChannelId(unreliableChannelId);
		Log.debug(this, "unreliableChannelId=%d", unreliableChannelId);
		getLink().addUnreliableChannelId(this);
		
		setCryptoInitializationMethod(cryptoInitializationMethod);
		
		cryptoInitialization();
		
	}
	
	@OnReceive
	public void onReceiveDisconnect(DisconnectMessage disconnectMessage) {
		
		Log.debug(this, "onReceiveDisconnect, link status: %s", getLink().getStatus());
		
		onDisconnected();
		sendKill(discontinuousBlock);
		
	}
	
	@OnReceive
	public void onReceiveKill(KillMessage killMessage) {

		Log.debug(this, "onReceiveKill, link status: %s", getLink().getStatus());
		
		onRemoteKill();
		
	}
	
	@OnReceive
	public void onReceiveCryptoInit(CryptoInitMessage cryptoInitMessage) {
		
		Log.msg(this, "onReceiveCryptoInit, link status: %s", getLink().getStatus());
		if(!getLink().getStatus().initializing()) {
			if(getLink().getStatus() == Status.ConnectingPassive) {
				
				Log.debug(this, "received first crypto initialization message, status %s, beginning crypto initialization", getLink().getStatus());
				getLink().setStatus(Status.InitializingPassive);
				
			} else {
				
				Log.msg(this, "ignoring init message, link status neither initializing nor ConnectingPassive");
				return;
				
			}
		}
		
		Log.debug(this, "received crypto initialization message: %s", cryptoInitMessage.represent(true));
		
		processCryptoInitializationMessage();
		
		cryptoInitialization();
		
	}
	
	@OnReceive
	public void onReceiveAck(AckMessage ackMessage) {
		
		Log.debug(this, "onReceiveAck, link status (not relevant): %s", getLink().getStatus());
		
		long ackDataId = ackMessage.ackDataId.getNum();
		Log.debug(this, "received acknowledgement for data id %d", ackDataId);
		
		runOnAckCallback(ackDataId);
		
		tryClear(ackDataId);
		
	}
	
	@OnReceive
	public void onReceiveChangeProtocolRequest(ChangeProtocolRequestMessage changeProtocolRequestMessage) {
		// TODO
	}
	
	@OnReceive
	public void onReceiveChannelBlockStatusRequest(ChannelBlockStatusRequestMessage channelBlockStatusRequestMessage) {
		
		Log.debug(this, "onReceiveChannelBlockStatusRequest, link status: %s", getLink().getStatus());
		if(getLink().getStatus() != Link.Status.Connected) {
			Log.debug(this, "ignoring channel block status request, link status != %s", Link.Status.Connected);
			return;
		}
		
		// TODO reply with reports for the channels requested only, instead of all channels
		
		sendChannelBlockStatusReport();
		
	}
	
	@OnReceive
	public void onReceiveChannelBlockStatusReport(ChannelBlockStatusReportMessage channelBlockStatusReportMessage) {

		Log.debug(this, "onReceiveChannelBlockStatusReport, link status: %s", getLink().getStatus());
		if(getLink().getStatus() != Link.Status.Connected) {
			Log.debug(this, "ignoring channel block status report, link status != %s", Link.Status.Connected);
			return;
		}

		if(channelBlockStatusRequestDataId != -1) {
			clear(channelBlockStatusRequestDataId, 0);
			channelBlockStatusRequestDataId = -1;
		}

		for(ChannelReportComponent channelReport : channelBlockStatusReportMessage.channelReports) {

			long channelId = channelReport.channelId.getNum();

			Channel channel = getLink().getChannelCollection().get(channelId);
			if(channel == null) continue;

			IdCollection idCollection = channel.getSentDataIdCollection();
			if(idCollection == null) continue;

			PacketBackupCollection packetBackupCollection = channel.getSentPacketBackupCollection();
			if(packetBackupCollection == null) continue;

			PacketBackup packetBackup;

			long lowestDataId = channelReport.lowestDataId.getNum();
			long highestDataId = channelReport.highestDataId.getNum();
			final long numDataIds = channelReport.numDataIds.getNum();

			final long numMissingSingleIds = channelReport.missingSingleIds.getNumElements();
			final long numMissingIdBlocks = channelReport.missingIdBlocks.getNumElements();

			final long realLowestDataId = idCollection.getLowestId();
			final long realHighestDataId = idCollection.getHighestId();

			boolean clear = numDataIds > 0; // whether or not we can clear anything from PacketBackupCollection
			long clearUpTo = highestDataId; // what id to clear PacketBackupCollection up to

			if(numDataIds > 0) {

				// normal case: peer has received some packets, but not all.

				if(lowestDataId > realLowestDataId && lowestDataId <= realHighestDataId) {
					clear = false; // we can not clear anything since peer is missing ids at the very beginning
					do {
						lowestDataId--;
						if((packetBackup = packetBackupCollection.get(lowestDataId)) != null) {
							channel.resend(packetBackup);
						}
					} while(lowestDataId > realLowestDataId);
				}

				while(highestDataId < realHighestDataId && highestDataId >= realLowestDataId) {
					highestDataId++;
					if((packetBackup = packetBackupCollection.get(highestDataId)) != null) {
						channel.resend(packetBackup);
					}
				}

			} else if(numDataIds < idCollection.getNumIds()) {

				// special case: we have sent packets, but peer did not receive any of them

				for(IdBoundary idBoundary : idCollection) {
					for(long curDataId = idBoundary.boundaryStart; curDataId < idBoundary.boundaryPostEnd; curDataId++) {
						packetBackup = packetBackupCollection.get(curDataId);
						channel.resend(packetBackup);
					}
				}


			}

			long j = 0;
			for(FlexNum singleId : channelReport.missingSingleIds) {
				if(j++ >= numMissingSingleIds) break;
				final long missingDataId = singleId.getNum();
				if(missingDataId >= realLowestDataId && missingDataId <= realHighestDataId && (packetBackup = packetBackupCollection.get(missingDataId)) != null) {
					if(missingDataId <= clearUpTo) clearUpTo = (missingDataId-1); // can not clear beyond this id
					channel.resend(packetBackup);
				}
			}

			j = 0;
			for(IdBlock idBlock : channelReport.missingIdBlocks) {
				if(j++ >= numMissingIdBlocks) break;
				long missingDataId = idBlock.startId.getNum();
				final long maxDataId = missingDataId + idBlock.innerSize.getNum() + 1;
				if(missingDataId >= realLowestDataId && maxDataId <= realHighestDataId) {
					if(missingDataId <= clearUpTo) clearUpTo = (missingDataId-1); // can not clear beyond this id
					while(missingDataId <= maxDataId) {
						packetBackup = packetBackupCollection.get(missingDataId);
						channel.resend(packetBackup);
						missingDataId++;
					}
				}
			}

			Log.debug(this, "CHANNEL %d: CAN (clear=%s) CLEAR UP TO DATAID %d", channelId, clear, clearUpTo);

			if(clear) {
				packetBackupCollection.clearUpTo(clearUpTo);
			}

		}

	}
	
	@OnReceive
	// no synchronization needed, this is called from process() which is already synchronized
	public void onReceiveOpenChannelRequest(OpenChannelRequestMessage openChannelRequestMessage) {

		Log.debug(this, "onReceiveOpenChannelRequest, link status: %s", getLink().getStatus());
		if(getLink().getStatus() != Link.Status.Connected) {
			Log.debug(this, "ignoring open channel request, link status != %s", Link.Status.Connected);
			return;
		}

		// TODO optimize (directly copy over PacketComponents to confirmation message)
		long channelId = openChannelRequestMessage.channelId.getNum();
		String protocol = openChannelRequestMessage.protocol.getString();

		Log.msg(this, "peer wants to open channelId %d with protocol: %s", channelId, protocol);

		if(getLink().onOpenChannelRequest(channelId, protocol)) {

			// channel can be opened
			Log.msg(this, "confirming (acknowledging) open channel request for channelId %d with protocol: %s", channelId, protocol);
			ack();

		} else {

			// channel can not be opened
			Log.msg(this, "ignoring open channel request for channelId %d with protocol: %s", channelId, protocol);

		}

	}
	
	@OnReceive
	public void onReceiveThrottle(ThrottleMessage throttleMessage) {
		
		Log.debug(this, "onReceiveThrottle, link status: %s", getLink().getStatus());
		if(getLink().getStatus() != Link.Status.Connected) {
			Log.debug(this, "ignoring throttle message, link status != %s", Link.Status.Connected);
			return;
		}
		
		long now = System.nanoTime();
		long numBytesSent = getLink().getNumBytesSent();
		
		long originalBytesPerSecond = throttleMessage.bytesPerSecond.getNum();
		
		if(originalBytesPerSecond == 0) {
			
			Log.debug(this, "throttle message with maximum transmission rate 0 received, disabling transmission rate limitation");
			getLink().setFlowControlBytesPerSecond(0);
			
		} else {
			
			if(lastThrottleReceived == 0) {
				lastThrottleReceived = getLink().getStartedReceiving();
			}
			
			long relativeBytesSent = numBytesSent - lastNumBytesSent;
			long relativeNanos = now - lastThrottleReceived;
			long bytesPerSecondSent = (1000000000L*relativeBytesSent)/relativeNanos;
			long currentBytesPerSecond = getLink().getFlowControlBytesPerSecond();
			
			if(originalBytesPerSecond >= bytesPerSecondSent && (originalBytesPerSecond < currentBytesPerSecond || currentBytesPerSecond == 0)) {
				// don't apply a maximum transmission rate bigger than what we were sending
				Log.debug(this, "throttle message received (bytes per second requested by remote: %d), ignoring because bigger than sent rate (%d) and smaller than current maximum transmission rate (%d)", originalBytesPerSecond, bytesPerSecondSent, currentBytesPerSecond);
			} else {
				long bytesPerSecond = Math.max(originalBytesPerSecond, 10);
				Log.debug(this, "throttle message received, applying maximum transmission rate (bytes per second): %d (requested by remote: %d)", bytesPerSecond, originalBytesPerSecond);
				getLink().setFlowControlBytesPerSecond(bytesPerSecond);
			}
		}
		
		lastThrottleReceived = now;
		lastNumBytesSent = numBytesSent;
		
	}
	
	//
	
	// locks sendLock
	private void sendConnectRequest(CryptoInitializationMethod[] cryptoInitializationMethods) {
		
		sendLock.lock();
		
		ArrayPacketComponent<CryptoInitializerIdentifierComponent> cryptoInitializerIdentifierComponents = outBMCPMessage.set(outBMCPMessage.connectRequest).cryptoInitializerIdentifierComponents;
		cryptoInitializerIdentifierComponents.setElements(cryptoInitializationMethods.length);
		
		int i = 0;
		for(CryptoInitializerIdentifierComponent cryptoInitializerIdentifierComponent : cryptoInitializerIdentifierComponents) {
			cryptoInitializerIdentifierComponent.setCryptoInitializationMethod(cryptoInitializationMethods[i++]);
		}
		
		currentPendingDataId = send();
		
		sendLock.unlock();
		
	}
	
	// locks sendLock
	private void sendConnectReply(CryptoInitializationMethod cryptoInitializationMethod, long unreliableChannelId) {
		
		sendLock.lock();
		
		outBMCPMessage.set(outBMCPMessage.connectReplyMessage);
		outBMCPMessage.connectReplyMessage.cryptoInitializerIdentifierComponent.setCryptoInitializationMethod(cryptoInitializationMethod);
		outBMCPMessage.connectReplyMessage.unreliableChannelId.setNum(unreliableChannelId);
		
		send(discontinuousBlock);
		
		sendLock.unlock();
		
	}
	
	// locks sendLock
	private long sendCryptoInit(CryptoInitPacketComponentI cryptoInitPacketComponent) {
		
		sendLock.lock();
		
		outBMCPMessage.set(outBMCPMessage.cryptoInit).setCryptoInitPacketComponent(cryptoInitPacketComponent);
		long dataId = send();
		
		sendLock.unlock();
		
		return dataId;
		
	}
	
	// locks sendLock
	private void sendAck(long dataId) {
		
		sendLock.lock();
		
		outBMCPMessage.set(outBMCPMessage.ack).ackDataId.setNum(dataId);
		send(discontinuousBlock);
		
		sendLock.unlock();
		
	}
	
	// locks sendLock
	private void sendChannelBlockStatusReport() {
		
		sendLock.lock();
		
		ChannelBlockStatusReportMessage channelBlockStatusReport = outBMCPMessage.set(outBMCPMessage.channelBlockStatusReport);
		
		// LOCK LINK'S RECEIVELOCK
		Log.debug(this, "sendChannelBlockStatusReport: locking Link's receiveLock...");
		// lock the link's receiveLock, so no changes occur to any channels.
		getLink().lockReceive();
		Log.debug(this, "sendChannelBlockStatusReport: locked Link's receiveLock, building channel block status report...");
		
		ChannelCollection channelCollection = getLink().getChannelCollection();
		List<Channel> channels = channelCollection.getChannels();
		
		ArrayPacketComponent<ChannelReportComponent> reports = channelBlockStatusReport.channelReports;
		reports.setElements(channels.size()); // make sure there are channels.size() ChannelReportComponents available
		
		Iterator<ChannelReportComponent> reportIterator = reports.iterator();
		
		int numReports = 0;

		for(Channel channel : channels) {

			IdCollection idCollection = channel.getReceivedDataIdCollection();
			if(idCollection == null) continue;
			
			numReports++;
			ChannelReportComponent report = reportIterator.next();
			
			report.channelId.setNum(channel.getChannelId());
			
			ArrayPacketComponent<FlexNum> missingSingleIds = report.missingSingleIds;
			ArrayPacketComponent<IdBlock> missingIdBlocks = report.missingIdBlocks;
			
			missingSingleIds.setMaxElements();
			missingIdBlocks.setMaxElements();
			
			Iterator<FlexNum> singleIdIterator = missingSingleIds.iterator();
			Iterator<IdBlock> idBlockIterator = missingIdBlocks.iterator();
			
			final int missingSingleIdsSize = missingSingleIds.getNumElements();
			final int missingIdBlocksSize = missingIdBlocks.getNumElements();
			
			int numMissingSingleIds = 0;
			int numMissingIdBlocks = 0;
			
			synchronized(idCollection) {

				long lowestId = idCollection.getLowestId();
				report.lowestDataId.setNum(lowestId < 0 ? 0 : lowestId);
				long highestId = idCollection.getHighestId();
				report.highestDataId.setNum(highestId < 0 ? 0 : highestId);
				
				report.numDataIds.setNum(idCollection.getNumIds());

				Iterator<IdBoundary> boundaryIterator = idCollection.iterator();
				IdBoundary lastBoundary = boundaryIterator.hasNext() ? boundaryIterator.next() : null;
				while(boundaryIterator.hasNext()) {

					IdBoundary curBoundary = boundaryIterator.next();
					if(curBoundary.boundaryStart > lastBoundary.boundaryPostEnd) {

						if((curBoundary.boundaryStart - lastBoundary.boundaryPostEnd) > 1) {

							// block
							IdBlock idBlock;
							numMissingIdBlocks++;
							if(numMissingIdBlocks > missingIdBlocksSize) {
								// append a new block to the array packet component
								idBlock = missingIdBlocks.addElement();
							} else {
								// use an existing block from the array packet component
								idBlock = idBlockIterator.next();
							}

							idBlock.startId.setNum(lastBoundary.boundaryPostEnd);
							idBlock.innerSize.setNum(curBoundary.boundaryStart - lastBoundary.boundaryPostEnd - 2);

						} else {

							// single id
							FlexNum singleId;
							numMissingSingleIds++;
							if(numMissingSingleIds > missingSingleIdsSize) {
								// append a new single id to the array packet component
								singleId = missingSingleIds.addElement();
							} else {
								// use an existing single id from the array packet component
								singleId = singleIdIterator.next();
							}

							singleId.setNum(lastBoundary.boundaryPostEnd);

						}

					}

					lastBoundary = curBoundary;
				}

			}
			
			missingSingleIds.setElements(numMissingSingleIds);
			missingIdBlocks.setElements(numMissingIdBlocks);

		}
		
		reports.setElements(numReports);

		// UNLOCK LINK'S RECEIVELOCK
		Log.debug(this, "sendChannelBlockStatusReport: built channel block status report, unlocking Link's receiveLock...");
		getLink().unlockReceive();
		Log.debug(this, "sendChannelBlockStatusReport: unlocked Link's receiveLock.");
		
		send(outBMCPMessage, discontinuousBlock);
		
		sendLock.unlock();
		
	}
	
	// locks sendLock
	private void sendChannelBlockStatusRequest() {

		sendLock.lock();

		outBMCPMessage.set(outBMCPMessage.channelBlockStatusRequest).channelIds.setElements(0);
		channelBlockStatusRequestDataId = send();

		sendLock.unlock();

	}
	
	// locks sendLock
	private long sendOpenChannelRequest(long channelId, String protocol) {
		
		sendLock.lock();
		
		outBMCPMessage.set(outBMCPMessage.openChannelRequest);
		outBMCPMessage.openChannelRequest.channelId.setNum(channelId);
		outBMCPMessage.openChannelRequest.protocol.setString(protocol);

		long dataId = send(outBMCPMessage);
		
		sendLock.unlock();
		
		return dataId;
		
	}
	
	// locks sendLock
	private void sendThrottle(long bytesPerSecond) {
		
		sendLock.lock();
		
		outBMCPMessage.set(outBMCPMessage.throttle).bytesPerSecond.setNum(bytesPerSecond);
		sendUnreliable(outBMCPMessage);
		
		sendLock.unlock();
		
	}
	
	// locks sendLock
	private void sendDisconnect() {
		
		sendLock.lock();
		
		outBMCPMessage.set(outBMCPMessage.disconnect);
		disconnectDataId = send();
		
		sendLock.unlock();
		
	}
	
	// locks sendLock
	private void sendKill(DiscontinuousBlock discontinuousBlock) {
		
		sendLock.lock();
		
		outBMCPMessage.set(outBMCPMessage.kill);
		
		send(discontinuousBlock);
		
		sendLock.unlock();
		
	}
	
	// --- ACTION INITIATION METHODS, CALLED FROM OUTSIDE ---
	
	/**
	 * starts this {@link BMCPManagementChannel} and connects to the peer
	 */
	private void connect() {
		
		sendConnectRequest(cryptoInitializationMethods);
		
	}
	
	@Override
	public void disconnect() {
		
		getLink().lockReceive();
		
		if(getLink().getStatus().connected()) {
			
			disconnectNow();
			
		} else if(getLink().getStatus().connecting()) {
			
			disconnect = true;
			
		}
		
		getLink().unlockReceive();
		
	}
	
	private void disconnectNow() {
		getLink().setStatus(Status.Disconnecting);
		sendDisconnect();
	}
	
	private void onConnected() {

		if(disconnect) {
			Log.debug(this, "connected, but disconnect is true, disconnecting");
			disconnectNow();
		} else {
			Log.debug(this, "connected");
			getLink().onConnected();
		}
		
	}
	
	private void onExit() {
		exitResendThread();
	}
	
	private void onDisconnected() {
		
		onExit();
		getLink().onDisconnected();
		
	}
	
	private void onRemoteKill() {
		
		onExit();
		if(getLink().getStatus().disconnecting()) {
			onDisconnected();
		} else {
			onKilled(CloseReason.RemoteKill);
		}
		
	}
	
	private void onKilled(CloseReason closeReason) {
		Log.debug(this, "killed, CloseReason: %s", closeReason);
		getLink().kill(closeReason);
	}
	
	private void kill(CloseReason closeReason) {
		Log.debug(this, "killing, CloseReason: %s", closeReason);
		getLink().kill(closeReason);
	}
	
	@Override
	public void onAbortCryptoInitialization(Exception e) {
		Log.warning(this, "connection (with crypto initialization method %s) aborted: %s", cryptoInitializer.getCryptoInitializationMethod(), Log.getStackTraceAsString(e));
		kill(CloseReason.ExceptionInCryptoInit);
	}
	
	@Override
	public void requestOpenChannel(final long channelId, String protocol) {

		long dataId = sendOpenChannelRequest(channelId, protocol);
		
		onAck(dataId, new Runnable() {
			public void run() {
				
				Channel channel = getLink().getChannelCollection().get(channelId);
				
				Log.msg(BMCPManagementChannel.this, "opening of channel id %d (%s) confirmed", channelId, channel);
				
				if(channel != null) {
					
					if(channel.isOpen()) {
						Log.msg(BMCPManagementChannel.this, "channel %s is already open", channel);
					} else {
						channel.open(true);
					}
					
				}
				
			}
		});
		
	}
	
	/**
	 * requests the channel block status for all channels from the peer
	 */
	public void requestChannelBlockStatus() {
		
		sendChannelBlockStatusRequest();
		
	}
	
	// --- SEND METHODS ---
	
	@Override
	public void onTimeout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
	
}
