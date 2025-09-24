package org.dclayer.net.interservice;

import java.util.Arrays;
import java.util.List;

import org.dclayer.DCLService;
import org.dclayer.crypto.challenge.CryptoChallenge;
import org.dclayer.crypto.challenge.Fixed128ByteCryptoChallenge;
import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.KeyPair;
import org.dclayer.crypto.key.RSAKey;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.buf.EndOfBufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.address.Address;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;
import org.dclayer.net.applicationchannel.ServiceSideApplicationChannelActionListener;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.interservice.applicationchannelslot.ApplicationChannelSlot;
import org.dclayer.net.interservice.applicationchannelslot.ApplicationChannelSlotMap;
import org.dclayer.net.interservice.message.ApplicationChannelDataInterserviceMessage;
import org.dclayer.net.interservice.message.ApplicationChannelSlotAssignInterserviceMessage;
import org.dclayer.net.interservice.message.ConnectionbaseNoticeInterserviceMessage;
import org.dclayer.net.interservice.message.CryptoChallengeReplyInterserviceMessage;
import org.dclayer.net.interservice.message.CryptoChallengeRequestInterserviceMessage;
import org.dclayer.net.interservice.message.IntegrationConnectRequestInterserviceMessage;
import org.dclayer.net.interservice.message.IntegrationRequestInterserviceMessage;
import org.dclayer.net.interservice.message.LLAReplyInterserviceMessage;
import org.dclayer.net.interservice.message.LLARequestInterserviceMessage;
import org.dclayer.net.interservice.message.LocalLLAReplyInterserviceMessage;
import org.dclayer.net.interservice.message.LocalLLARequestInterserviceMessage;
import org.dclayer.net.interservice.message.NetworkJoinNoticeInterserviceMessage;
import org.dclayer.net.interservice.message.NetworkLeaveNoticeInterserviceMessage;
import org.dclayer.net.interservice.message.NetworkPacketInterserviceMessage;
import org.dclayer.net.interservice.message.TrustedSwitchInterserviceMessage;
import org.dclayer.net.interservice.message.VersionInterserviceMessage;
import org.dclayer.net.link.channel.data.ThreadDataChannel;
import org.dclayer.net.lla.CachedLLA;
import org.dclayer.net.lla.LLA;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.NetworkType;
import org.dclayer.net.network.RemoteNetworkNode;
import org.dclayer.net.network.component.NetworkPacket;
import org.dclayer.net.network.slot.AddressSlot;
import org.dclayer.net.network.slot.AddressSlotMap;
import org.dclayer.net.network.slot.GenericNetworkSlot;
import org.dclayer.net.network.slot.NetworkSlot;
import org.dclayer.net.network.slot.NetworkSlotMap;

public class InterserviceChannel extends ThreadDataChannel implements ServiceSideApplicationChannelActionListener, NetworkPacketProvider {
	
	public static long VERSION = 0;
	
	public static byte CONNECTIONBASE_STRANGER = 0;
	public static byte CONNECTIONBASE_TRUSTED = 1;
	
	//
	
	private boolean initiator = false;
	private long version = -1;
	
	private boolean ready = false;
	private boolean alive = true;
	
	private InterservicePacket inInterservicePacket = new InterservicePacket(this);
	private InterservicePacket outInterservicePacket = new InterservicePacket(this);
	
	private DCLService dclService;
	private InterserviceChannelActionListener interserviceChannelActionListener;
	private CachedLLA cachedLLA;
	private InterservicePolicy interservicePolicy;
	
	private LLA reportedLocalLLA;
	
	private AddressSlotMap remoteAddressSlotMap = new AddressSlotMap(this, true);
	private AddressSlotMap localAddressSlotMap = new AddressSlotMap(this, false);
	
	private NetworkSlotMap remoteNetworkSlotMap = new NetworkSlotMap();
	private NetworkSlotMap localNetworkSlotMap = new NetworkSlotMap();
	
	private ApplicationChannelSlotMap localApplicationChannelSlotMap = new ApplicationChannelSlotMap(false);
	private ApplicationChannelSlotMap remoteApplicationChannelSlotMap = new ApplicationChannelSlotMap(true);
	
	private Thread llaRequestThread = new Thread(new Runnable() {
		public void run() {
			runLLARequestLoop();
		}
	});

	public InterserviceChannel(DCLService dclService, InterserviceChannelActionListener interserviceChannelActionListener, CachedLLA cachedLLA, long channelId, String channelName) {
		super(cachedLLA.getLink(), channelId, channelName);
		this.dclService = dclService;
		this.interserviceChannelActionListener = interserviceChannelActionListener;
		this.cachedLLA = cachedLLA;
		this.interservicePolicy = cachedLLA.getInterservicePolicy();
	}
	
	public CachedLLA getCachedLLA() {
		return cachedLLA;
	}
	
	public boolean isInitiator() {
		return initiator;
	}
	
	@Override
	public synchronized void onOpenChannel(boolean initiator) {
		this.initiator = initiator;
		Log.msg(this, "opening channel, initiator=%s, interservice policy: %s", initiator, interservicePolicy);
		if(initiator) {
			this.version = VERSION;
			sendVersion(this.version);
		}
	}

	@Override
	public synchronized void onCloseChannel() {
		
		Log.msg(this, "channel closed");
		
		this.alive = false;
		
		for(NetworkSlot remoteNetworkSlot : remoteNetworkSlotMap) {
			for(NetworkNode remoteNetworkNode : remoteNetworkSlot.getNetworkNodes()) {
				interserviceChannelActionListener.onRemoveRemoteNetworkNode(this, remoteNetworkNode);
			}
		}
		
		interserviceChannelActionListener.onInterserviceChannelClosed(this);
		
	}
	
	private void removeRemoteAddressSlot(AddressSlot remoteAddressSlot) {
		
		for(NetworkSlot remoteNetworkSlot : remoteAddressSlot.getNetworkSlots()) {
			
			NetworkNode remoteNetworkNode = remoteNetworkSlot.removeNetworkNode(remoteAddressSlot.getAddress());
			interserviceChannelActionListener.onRemoveRemoteNetworkNode(this, remoteNetworkNode);
			
			checkRemoteNetworkSlot(remoteNetworkSlot);
			
		}
		
		remoteAddressSlotMap.remove(remoteAddressSlot.getSlot());
		
	}
	
	private void removeRemoteNetworkSlot(NetworkSlot remoteNetworkSlot) {
		
		for(NetworkNode remoteNetworkNode : remoteNetworkSlot.getNetworkNodes()) {
			
			AddressSlot remoteAddressSlot = remoteAddressSlotMap.find(remoteNetworkNode.getAddress());
			remoteAddressSlot.removeNetworkSlot(remoteNetworkSlot);
			
			interserviceChannelActionListener.onRemoveRemoteNetworkNode(this, remoteNetworkNode);
			
		}
		
		GenericNetworkSlot localNetworkSlot = remoteNetworkSlot.getRemoteEquivalent();
		if(localNetworkSlot != null) {
			remoteNetworkSlot.setRemoteEquivalent(null);
			localNetworkSlot.setRemoteEquivalent(null);
		}
		
		remoteNetworkSlotMap.remove(remoteNetworkSlot.getSlot());
		
	}
	
	private void checkRemoteNetworkSlot(NetworkSlot remoteNetworkSlot) {
		
		if(remoteNetworkSlot.getNetworkNodes().size() <= 0) {
			
			Log.msg(this, "remote network slot %s is empty, removing", remoteNetworkSlot);
			
			GenericNetworkSlot localNetworkSlot = remoteNetworkSlot.getRemoteEquivalent();
			if(localNetworkSlot != null) {
				remoteNetworkSlot.setRemoteEquivalent(null);
				localNetworkSlot.setRemoteEquivalent(null);
			}
			
			remoteNetworkSlotMap.remove(remoteNetworkSlot.getSlot());
			
		}
		
	}
	
	public void startTrustedSwitchOnAllAddressSlots() {
		Log.debug(this, "starting trusted switch on all (%d) address slots", localAddressSlotMap.size());
		for(AddressSlot addressSlot : localAddressSlotMap) {
			startTrustedSwitch(addressSlot);
		}
	}
	
	public void startTrustedSwitch(AddressSlot addressSlot) {
		Log.debug(this, "starting trusted switch on address slot: %s", addressSlot);
		addressSlot.setMaxAllowedOutConnectionBase(CONNECTIONBASE_TRUSTED);
		KeyPair keyPair = addressSlot.getAddress().getKeyPair();
		addressSlot.setInCryptoChallenge(new Fixed128ByteCryptoChallenge(keyPair.getPrivateKey()));
		sendTrustedSwitch(addressSlot, (RSAKey) keyPair.getPublicKey());
	}
	
	private void cancelTrustedSwitch(AddressSlot addressSlot) {
		Log.debug(this, "cancelling trusted switch on address slot: %s", addressSlot);
		addressSlot.setMaxAllowedOutConnectionBase(CONNECTIONBASE_STRANGER);
		addressSlot.setInCryptoChallenge(null);
	}
	
	private synchronized void finishTrustedSwitch(AddressSlot remoteAddressSlot, boolean success) {
		
		if(success) {
			
			Log.msg(this, "remote successfully completed crypto challenge for address slot: %s", remoteAddressSlot);
			
			remoteAddressSlot.setTrustedSwitchOutCryptoChallenge(null);
			remoteAddressSlot.setConnectionBase(CONNECTIONBASE_TRUSTED);
			
			List<ApplicationChannel> requestApplicationChannels = interservicePolicy.getRequestApplicationChannelsForRemoteAddress(remoteAddressSlot.getAddress());
			
			if(requestApplicationChannels != null) {
				for(ApplicationChannel applicationChannel : requestApplicationChannels) {
					AddressSlot localAddressSlot = localAddressSlotMap.find(applicationChannel.getLocalAddress());
					if(localAddressSlot != null && localAddressSlot.getConnectionBase() >= CONNECTIONBASE_TRUSTED) {
						requestApplicationChannel(localAddressSlot, remoteAddressSlot, applicationChannel);
					}
				}
			}
			
		} else {
			
			Log.msg(this, "remote failed crypto challenge for address slot, removing: %s", remoteAddressSlot);
			removeRemoteAddressSlot(remoteAddressSlot);
			
		}
		
	}
	
	private void setVersion(long version) {
		Log.msg(this, "setting version from %d to %d", this.version, version);
		this.version = version;
	}
	
	private synchronized void setReady() {
		
		Log.msg(this, "InterserviceChannel ready%s, version %d", this.ready ? " (was ready before)" : "", this.version);
		if(this.ready) return;
		
		this.ready = true;
		this.interserviceChannelActionListener.onReadyChange(this, ready);
		
		// TODO: make sure all required addresses for application channels we need to open are acknowledged by the remote
		
		for(AddressSlot addressSlot : localAddressSlotMap) {
			startTrustedSwitch(addressSlot);
		}
		
		this.llaRequestThread.start();
		
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public synchronized void openApplicationChannel(ApplicationChannel applicationChannel) {
		
		Log.msg(this, "opening application channel %s", applicationChannel);
		
		AddressSlot localAddressSlot = localAddressSlotMap.find(applicationChannel.getLocalAddress());
		AddressSlot remoteAddressSlot = remoteAddressSlotMap.find(applicationChannel.getRemoteAddress());
		
		if(localAddressSlot != null && remoteAddressSlot != null && localAddressSlot.getConnectionBase() >= CONNECTIONBASE_TRUSTED && remoteAddressSlot.getConnectionBase() >= CONNECTIONBASE_TRUSTED) {
			
			Log.msg(this, "both local and remote address slots exist and are on trusted connection base, requesting application channel immediately");
			requestApplicationChannel(localAddressSlot, remoteAddressSlot, applicationChannel);
			
		} else {
			
			Log.msg(this, "local and remote address slots not both ready, adding application channel to interservice policy");
			interserviceChannelActionListener.addDefaultOutgoingApplicationChannelInterservicePolicyRules(interservicePolicy, applicationChannel);
			
		}
		
	}
	
	/**
	 * called when the remote notifies us of its incoming connection base
	 * @param connectionBase the incoming connection base of the remote
	 */
	private synchronized void setOutConnectionBase(AddressSlot localAddressSlot, byte outConnectionBase) {
		byte oldOutConnectionBase = localAddressSlot.getConnectionBase();
		localAddressSlot.setOutConnectionBase(outConnectionBase);
		
		if(oldOutConnectionBase < CONNECTIONBASE_TRUSTED && localAddressSlot.getConnectionBase() >= CONNECTIONBASE_TRUSTED) {
			
			joinNetworks(localAddressSlot);
			
			List<ApplicationChannel> requestApplicationChannels = interservicePolicy.getRequestApplicationChannelsForLocalAddress(localAddressSlot.getAddress());
			
			if(requestApplicationChannels != null) {
				for(ApplicationChannel applicationChannel : requestApplicationChannels) {
					AddressSlot remoteAddressSlot = remoteAddressSlotMap.find(applicationChannel.getRemoteAddress());
					if(remoteAddressSlot != null && remoteAddressSlot.getConnectionBase() >= CONNECTIONBASE_TRUSTED) {
						requestApplicationChannel(localAddressSlot, remoteAddressSlot, applicationChannel);
					}
				}
			}
			
		}
	}
	
	private synchronized void joinNetworks(AddressSlot addressSlot) {
		List<NetworkNode> networkNodes = addressSlot.popNetworkNodesToJoin();
		Log.msg(this, "initially joining %d networks for address slot: %s", networkNodes.size(), addressSlot);
		for(NetworkNode networkNode : networkNodes) {
			joinNetwork(addressSlot, networkNode);
		}
	}
	
	public synchronized void joinNetwork(NetworkNode networkNode) {
		
		AddressSlot localAddressSlot = localAddressSlotMap.find(networkNode.getAddress());
		
		if(localAddressSlot == null) {
			
			Address localAddress = networkNode.getAddress();
			localAddressSlot = localAddressSlotMap.add(localAddress);
			
			if(isReady()) {
				startTrustedSwitch(localAddressSlot);
			}
			
		}
		
		joinNetwork(localAddressSlot, networkNode);
		
	}
	
	private synchronized void joinNetwork(AddressSlot addressSlot, NetworkNode networkNode) {
		
		if(addressSlot.getConnectionBase() < CONNECTIONBASE_TRUSTED) {
			
			Log.debug(this, "saving network node %s for later joining with address slot: %s", networkNode, addressSlot);
			addressSlot.addNetworkNodeToJoin(networkNode);
			return;
			
		}
		
		NetworkSlot networkSlot = localNetworkSlotMap.find(networkNode.getNetworkType());
		
		boolean newSlot = (networkSlot == null);
		
		if(newSlot) {
			
			networkSlot = localNetworkSlotMap.add(networkNode.getNetworkType());
			
			NetworkSlot remoteNetworkSlot = remoteNetworkSlotMap.find(networkNode.getNetworkType());
			networkSlot.setRemoteEquivalent(remoteNetworkSlot);
			if(remoteNetworkSlot != null) {
				remoteNetworkSlot.setRemoteEquivalent(networkSlot);
				
				for(NetworkNode remoteNetworkNode : remoteNetworkSlot.getNetworkNodes()) {
					interserviceChannelActionListener.onNewRemoteNetworkNode(this, remoteNetworkNode, networkSlot);
				}
			}
			
		}
		
		networkSlot.addNetworkNode(networkNode);
		
		Log.debug(this, "joining %s network slot %s: %s", newSlot ? "new" : "existing", networkSlot, addressSlot);
		
		sendNetworkJoinNotice(addressSlot.getSlot(), newSlot ? networkSlot.getNetworkType() : null, networkSlot.getSlot());
		
	}
	
	private synchronized void requestApplicationChannel(AddressSlot localAddressSlot, AddressSlot remoteAddressSlot, ApplicationChannel applicationChannel) {
		
		Log.debug(this, "requesting application channel: %s", applicationChannel);
		
		ApplicationChannelSlot localApplicationChannelSlot = localApplicationChannelSlotMap.add(applicationChannel);
		sendApplicationChannelSlotAssign(localApplicationChannelSlot, localAddressSlot, remoteAddressSlot);
		
	}
	
	private synchronized void acceptIncomingApplicationChannelRequest(ApplicationChannel applicationChannel, AddressSlot localAddressSlot, AddressSlot remoteAddressSlot, int remoteApplicationChannelSlotId) {
		
		Log.msg(this, "accepting request for application channel: %s", applicationChannel);
		
		ApplicationChannelSlot remoteApplicationChannelSlot = remoteApplicationChannelSlotMap.put(remoteApplicationChannelSlotId, applicationChannel);
		ApplicationChannelSlot localApplicationChannelSlot = localApplicationChannelSlotMap.add(applicationChannel);
		
		localApplicationChannelSlot.setRemoteEquivalent(remoteApplicationChannelSlot);
		remoteApplicationChannelSlot.setRemoteEquivalent(localApplicationChannelSlot);
		
		sendApplicationChannelSlotAssign(localApplicationChannelSlot, localAddressSlot, remoteAddressSlot);
		
		connectApplicationChannel(applicationChannel);
		
	}
	
	private synchronized void finishOutgoingApplicationChannelRequest(ApplicationChannelSlot localApplicationChannelSlot, int remoteApplicationChannelSlotId) {
		
		ApplicationChannel applicationChannel = localApplicationChannelSlot.getApplicationChannel();
		
		Log.debug(this, "finishing request for application channel: %s", applicationChannel);
		
		ApplicationChannelSlot remoteApplicationChannelSlot = remoteApplicationChannelSlotMap.put(remoteApplicationChannelSlotId, applicationChannel);
		
		localApplicationChannelSlot.setRemoteEquivalent(remoteApplicationChannelSlot);
		remoteApplicationChannelSlot.setRemoteEquivalent(localApplicationChannelSlot);
		
		connectApplicationChannel(applicationChannel);
		
	}
	
	private synchronized void connectApplicationChannel(ApplicationChannel applicationChannel) {
		
		applicationChannel.setServiceSideApplicationChannelActionListener(this);
		applicationChannel.getApplicationSideApplicationChannelActionListener().onConnected(applicationChannel);
		
	}
	
	private void runLLARequestLoop() {
		for(;;) {
			
			synchronized(this) {
				if(!alive) break;
				sendLLARequest(10); // TODO
				sendLocalLLARequest(); // TODO maybe not every 10 seconds
			}
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				return;
			}
			
		}
	}

	@Override
	public void readConstantly(ByteBuf byteBuf) {
		
		for(;;) {
			
			try {
				
				inInterservicePacket.read(byteBuf);
				
			} catch(EndOfBufException e) {
				Log.debug(this, "read ByteBuf ended");
				return;
			} catch(BufException e) {
				Log.exception(this, e);
			} catch (ParseException e) {
				Log.exception(this, e);
			}
			
			processPacket();
			
		}
		
	}
	
	private void processPacket() {
		
		InterserviceMessage interserviceMessage = inInterservicePacket.getInterserviceMessage();
		interserviceMessage.callOnReceiveMethod(this);
		
	}
	
	private void sendOutInterservicePacket() {
		try {
			outInterservicePacket.write(getWriteByteBuf());
			flush();
		} catch (BufException e) {
			Log.exception(this, e);
		}
	}
	
	// send methods
	
	private synchronized void sendVersion(long version) {
		Log.debug(this, "sending version: %d", version);
		outInterservicePacket.setVersionInterserviceMessage().setVersion(version);
		sendOutInterservicePacket();
	}
	
	private synchronized void sendLLARequest(int limit) {
		Log.debug(this, "sending LLA request for %d LLAs", limit);
		outInterservicePacket
		.setLLARequestInterserviceMessage()
		.setLimit(limit);
		sendOutInterservicePacket();
	}
	
	private synchronized void sendLLAReply(List<LLA> llas) {
		Log.debug(this, "sending LLA reply with %d LLAs: %s", llas.size(), Arrays.toString(llas.toArray()));
		outInterservicePacket
		.setLLAReplyInterserviceMessage()
		.getLowerLevelAddressListComponent()
		.setAddresses(llas);
		sendOutInterservicePacket();
	}
	
	private synchronized void sendTrustedSwitch(AddressSlot addressSlot, RSAKey rsaKey) {
		Log.debug(this, "sending trusted switch message for address slot: %s", addressSlot);
		TrustedSwitchInterserviceMessage trustedSwitchInterserviceMessage = outInterservicePacket.setTrustedSwitchInterserviceMessage();
		trustedSwitchInterserviceMessage.getKeyComponent().setRSAKeyComponent().setKey(rsaKey);
		trustedSwitchInterserviceMessage.setAddressSlot(addressSlot.getSlot());
		sendOutInterservicePacket();
	}
	
	private synchronized void sendCryptoChallengeRequest(AddressSlot addressSlot, Data plainData) {
		Log.debug(this, "sending crypto challenge request");
		CryptoChallengeRequestInterserviceMessage cryptoChallengeRequestInterserviceMessage = outInterservicePacket.setCryptoChallengeRequestInterserviceMessage();
		cryptoChallengeRequestInterserviceMessage.setAddressSlot(addressSlot.getSlot());
		cryptoChallengeRequestInterserviceMessage.getDataComponent().setData(plainData);
		sendOutInterservicePacket();
	}
	
	private synchronized void sendCryptoChallengeReply(AddressSlot addressSlot, Data cipherData) {
		Log.debug(this, "sending crypto challenge reply");
		CryptoChallengeReplyInterserviceMessage cryptoChallengeReplyInterserviceMessage = outInterservicePacket.setCryptoChallengeReplyInterserviceMessage();
		cryptoChallengeReplyInterserviceMessage.setAddressSlot(addressSlot.getSlot());
		cryptoChallengeReplyInterserviceMessage.getDataComponent().setData(cipherData);
		sendOutInterservicePacket();
	}
	
	private synchronized void sendConnectionbaseNotice(AddressSlot addressSlot) {
		Log.debug(this, "sending connection base notice for connection base %d on address slot: %s", addressSlot.getConnectionBase(), addressSlot);
		ConnectionbaseNoticeInterserviceMessage connectionbaseNoticeInterserviceMessage = outInterservicePacket.setConnectionbaseNoticeInterserviceMessage();
		connectionbaseNoticeInterserviceMessage.setAddressSlot(addressSlot.getSlot());
		connectionbaseNoticeInterserviceMessage.setConnectionBase(addressSlot.getConnectionBase());
		sendOutInterservicePacket();
	}
	
	private synchronized void sendNetworkJoinNotice(int addressSlotId, NetworkType networkType, int networkSlotId) {
		Log.debug(this, "sending network join notice for address slot id %d, network slot id %d and network type %s", addressSlotId, networkSlotId, networkType);
		NetworkJoinNoticeInterserviceMessage networkJoinNoticeInterserviceMessage = outInterservicePacket.setNetworkJoinNoticeInterserviceMessage();
		networkJoinNoticeInterserviceMessage.setAddressSlot(addressSlotId);
		networkJoinNoticeInterserviceMessage.setNetworkSlot(networkSlotId);
		networkJoinNoticeInterserviceMessage.getNetworkTypeComponent().setNetworkType(networkType);
		sendOutInterservicePacket();
	}
	
	private synchronized void sendNetworkLeaveNotice(int slot) {
		Log.debug(this, "sending network leave notice for slot %d", slot);
		outInterservicePacket
		.setNetworkLeaveNoticeInterserviceMessage()
		.setNetworkSlot(slot);
		sendOutInterservicePacket();
	}
	
	private synchronized void sendNetworkPacket(NetworkPacket networkPacket, int slot) {
		Log.debug(this, "sending network packet on slot %d: %s", slot, networkPacket);
		NetworkPacketInterserviceMessage networkPacketInterserviceMessage = outInterservicePacket.setNetworkPacketInterserviceMessage();
		networkPacketInterserviceMessage.setSlot(slot);
		networkPacketInterserviceMessage.setNetworkPacket(networkPacket);
		sendOutInterservicePacket();
	}
	
	private synchronized void sendApplicationChannelSlotAssign(ApplicationChannelSlot localApplicationChannelSlot, AddressSlot localAddressSlot, AddressSlot remoteAddressSlot) {
		ApplicationChannelSlotAssignInterserviceMessage applicationChannelSlotAssignInterserviceMessage = outInterservicePacket.setApplicationChannelSlotAssignInterserviceMessage();
		applicationChannelSlotAssignInterserviceMessage.setApplicationChannelSlot(localApplicationChannelSlot.getSlot());
		applicationChannelSlotAssignInterserviceMessage.setSenderAddressSlot(localAddressSlot.getSlot());
		applicationChannelSlotAssignInterserviceMessage.setReceiverAddressSlot(remoteAddressSlot.getSlot());
		applicationChannelSlotAssignInterserviceMessage.setActionIdentifier(localApplicationChannelSlot.getApplicationChannel().getActionIdentifier());
		sendOutInterservicePacket();
	}
	
	private synchronized void sendApplicationChannelDataInterserviceMessage(ApplicationChannelSlot applicationChannelSlot, Data data) {
		ApplicationChannelDataInterserviceMessage applicationChannelDataInterserviceMessage = outInterservicePacket.setApplicationChannelDataInterserviceMessage();
		applicationChannelDataInterserviceMessage.setApplicationChannelSlot(applicationChannelSlot.getSlot());
		applicationChannelDataInterserviceMessage.getDataComponent().setData(data);
		sendOutInterservicePacket();
	}
	
	private synchronized void sendLocalLLARequest() {
		outInterservicePacket.setLocalLLARequestInterserviceMessage();
		sendOutInterservicePacket();
	}
	
	private synchronized void sendLocalLLAReplyInterserviceMessage(LLA localLLA) {
		LocalLLAReplyInterserviceMessage localLLAReplyInterserviceMessage = outInterservicePacket.setLocalLLAReplyInterserviceMessage();
		localLLAReplyInterserviceMessage.setLocalLLA(localLLA);
		sendOutInterservicePacket();
	}
	
	//
	
	private void onNetworkJoinNotice(AddressSlot remoteAddressSlot, final int networkSlotId, NetworkType networkType) {
		
		if(remoteAddressSlot.getConnectionBase() < CONNECTIONBASE_TRUSTED) {
			Log.msg(this, "ignoring network join notice on address slot (incoming connection base insufficient): %s", remoteAddressSlot);
			return;
		}
		
		NetworkSlot remoteNetworkSlot = remoteNetworkSlotMap.get(networkSlotId);
		
		boolean existing = remoteNetworkSlot != null;
		
		if(existing) {
			
			if(networkType == null) {
				
				networkType = remoteNetworkSlot.getNetworkType();
				
			} else if(!remoteNetworkSlot.getNetworkType().equals(networkType)) {
				
				Log.warning(this, "received network join notice for address slot %s joining network slot %s where network slot's network type (%s) does not equal (surprisingly) given network type of network join notice (%s), emptying existing network slot first", remoteAddressSlot, remoteNetworkSlot, remoteNetworkSlot.getNetworkType(), networkType);
				removeRemoteNetworkSlot(remoteNetworkSlot);
				remoteNetworkSlot = remoteNetworkSlotMap.put(networkSlotId, networkType);
				
			}
			
		} else {
			
			if(networkType == null) {
				Log.msg(this, "ignoring network join notice for empty network slot id %d, network type is not given", networkSlotId);
				return;
			}
			
			remoteNetworkSlot = remoteNetworkSlotMap.put(networkSlotId, networkType);
			
		}
		
		NetworkNode remoteNetworkNode = new RemoteNetworkNode(networkType, remoteAddressSlot.getAddress(), remoteNetworkSlot) {
			@Override
			public boolean onForward(NetworkPacket networkPacket) {
				sendNetworkPacket(networkPacket, networkSlotId);
				return true;
			}
		};
		
		remoteNetworkSlot.addNetworkNode(remoteNetworkNode);
		
		remoteAddressSlot.addNetworkSlot(remoteNetworkSlot);
		
		Log.msg(this, "remote joined network %s with address slot %s, network node: %s", remoteNetworkSlot, remoteAddressSlot, remoteNetworkNode);
		
		NetworkSlot localNetworkSlot = localNetworkSlotMap.find(remoteNetworkSlot.getNetworkType());
		
		remoteNetworkSlot.setRemoteEquivalent(localNetworkSlot);
		if(localNetworkSlot != null) {
			localNetworkSlot.setRemoteEquivalent(remoteNetworkSlot);
			interserviceChannelActionListener.onNewRemoteNetworkNode(this, remoteNetworkNode, localNetworkSlot);
		}
		
	}
	
	private void onNetworkLeaveNotice(AddressSlot remoteAddressSlot, int remoteNetworkSlotId) {
		
		NetworkSlot remoteNetworkSlot = remoteNetworkSlotMap.get(remoteNetworkSlotId);
		
		if(remoteNetworkSlot == null) {
			Log.warning(this, "remote left nonexistent network type slot %d with address slot: %s", remoteNetworkSlotId, remoteAddressSlot);
			return;
		}
		
		NetworkNode remoteNetworkNode = remoteNetworkSlot.removeNetworkNode(remoteAddressSlot.getAddress());
		
		if(remoteNetworkNode == null) {
			Log.warning(this, "remote left network slot %s which it did not join with address slot: %s", remoteNetworkSlot, remoteAddressSlot);
			return;
		}
		
		remoteAddressSlot.removeNetworkSlot(remoteNetworkSlot);
		
		Log.msg(this, "remote left network %s, network slot %s with address slot: %s", remoteNetworkNode, remoteNetworkSlot, remoteAddressSlot);
		
		checkRemoteNetworkSlot(remoteNetworkSlot);
		
		interserviceChannelActionListener.onRemoveRemoteNetworkNode(this, remoteNetworkNode);
		
	}
	
	private void onReceiveNetworkPacket(NetworkPacket networkPacket) {
		
		GenericNetworkSlot<NetworkNode> localNetworkSlot = networkPacket.getNetworkSlot();
		if(localNetworkSlot.getRemoteEquivalent() == null) {
			// remote peer sends to our network slot even though itself didn't join that network
			Log.warning(this, "ignoring network packet on network slot %s, remote did not join that network", localNetworkSlot);
			return;
		}
		
		// just forward this to the first node as their routing tables are connected anyways
		if(!localNetworkSlot.getNetworkNodes().get(0).forward(networkPacket)) {
			// TODO: could not route, maybe notify remote?
		}
		
	}
	
	// onReceive methods
	
	public synchronized void onReceiveVersionInterserviceMessage(VersionInterserviceMessage versionInterserviceMessage) {
		
		// TODO probably don't let the remote change version during normal operation
		long version = versionInterserviceMessage.getVersion();
		Log.debug(this, "received version: %d", version);
		
		if(version == this.version) {
			
			Log.debug(this, "versions match, not replying");
			setReady();
			
		} else {
			
			if(isReady()) {
				
				Log.debug(this, "ignoring version message");
				
			} else {
				
				setVersion(Math.min(VERSION, version));
				sendVersion(this.version);
				if(this.version == version) {
					// agreeing with remote's proposal
					setReady();
				}
				
			}
			
		}
	}
	
	public void onReceiveLLARequestInterserviceMessage(LLARequestInterserviceMessage llaRequestInterserviceMessage) {
		// TODO don't let the remote request infinite LLAs infinite times
		int limit = (int) Math.min(Integer.MAX_VALUE, llaRequestInterserviceMessage.getLimit());
		Log.debug(this, "received LLA request for at most %d LLAs", limit);
		List<LLA> llas = this.dclService.getRandomConnectedLLAs(limit);
		sendLLAReply(llas);
	}
	
	public void onReceiveLLAReplyInterserviceMessage(LLAReplyInterserviceMessage llaReplyInterserviceMessage) {
		// TODO don't let the remote flood you with this
		List<LLA> llas = llaReplyInterserviceMessage.getLowerLevelAddressListComponent().getNewAddresses();
		Log.debug(this, "received LLA reply message with %d LLAs: %s", llas.size(), Arrays.toString(llas.toArray()));
		dclService.storeLLAs(llas, this.cachedLLA);
	}
	
	public synchronized void onReceiveTrustedSwitchInterserviceMessage(TrustedSwitchInterserviceMessage trustedSwitchInterserviceMessage) {
		
		int addressSlotId = trustedSwitchInterserviceMessage.getAddressSlot();
		
		Key key;
		try {
			key = trustedSwitchInterserviceMessage.getKeyComponent().getKeyComponent().getKey();
		} catch (CryptoException e) {
			Log.exception(this, e, "could not parse trusted switch message for address slot %d, crypto exception while parsing key", addressSlotId);
			// TODO notify remote of failure
			return;
		}
		
		Address remoteAddress = new Address<>(KeyPair.fromPublicKey(key));
		
		AddressSlot addressSlot = remoteAddressSlotMap.get(addressSlotId);
		
		if(addressSlot != null) {
			if(addressSlot.getAddress().equals(remoteAddress)) {
				Log.msg(this, "ignoring trusted switch message for address slot (slot already exists with same address): %s", addressSlot);
				return;
			} else {
				Log.msg(this, "received trusted switch message for existing slot with different key, overwriting slot: %s", addressSlot);
				removeRemoteAddressSlot(addressSlot);
			}
		}
		
		Log.debug(this, "received trusted switch message for address slot id %d with address: %s", addressSlotId, remoteAddress);
		
		addressSlot = remoteAddressSlotMap.put(addressSlotId, remoteAddress);
		
		CryptoChallenge trustedSwitchOutCryptoChallenge = new Fixed128ByteCryptoChallenge(key);

		Data plainData = trustedSwitchOutCryptoChallenge.makeChallengeData();
		
		addressSlot.setTrustedSwitchOutCryptoChallenge(trustedSwitchOutCryptoChallenge);
		sendCryptoChallengeRequest(addressSlot, plainData);
		
	}
	
	public synchronized void onReceiveCryptoChallengeRequestInterserviceMessage(CryptoChallengeRequestInterserviceMessage cryptoChallengeRequestInterserviceMessage) {
		
		int addressSlotId = cryptoChallengeRequestInterserviceMessage.getAddressSlot();
		AddressSlot addressSlot = localAddressSlotMap.get(addressSlotId);
		
		if(addressSlot == null) {
			Log.msg(this, "received crypto challenge request for empty address slot %d, ignoring", addressSlotId);
			return;
		}
		
		CryptoChallenge inCryptoChallenge = addressSlot.getInCryptoChallenge();
		
		if(inCryptoChallenge == null) {
			Log.msg(this, "ignoring crypto challenge request for address slot (not performing trusted switch): %s", addressSlot);
			return;
		}
		
		addressSlot.setInCryptoChallenge(null);
		
		Log.debug(this, "received crypto challenge request for address slot: %s", addressSlot);
		
		Data plainData = cryptoChallengeRequestInterserviceMessage.getDataComponent().getData();
		
		Data cipherData;
		try {
			cipherData = inCryptoChallenge.solveChallengeData(plainData);
		} catch (CryptoException e) {
			Log.exception(this, e, "could not solve crypto challenge for address slot: %s", addressSlot);
			cancelTrustedSwitch(addressSlot);
			// TODO notify remote of failure
			return;
		}
		
		sendCryptoChallengeReply(addressSlot, cipherData);
		
	}
	
	public synchronized void onReceiveCryptoChallengeReplyInterserviceMessage(CryptoChallengeReplyInterserviceMessage cryptoChallengeReplyInterserviceMessage) {
		
		int addressSlotId = cryptoChallengeReplyInterserviceMessage.getAddressSlot();
		AddressSlot addressSlot = remoteAddressSlotMap.get(addressSlotId);
		
		if(addressSlot == null) {
			Log.msg(this, "received crypto challenge reply for empty address slot %d, ignoring", addressSlotId);
			return;
		}
		
		CryptoChallenge trustedSwitchOutCryptoChallenge = addressSlot.getTrustedSwitchOutCryptoChallenge();
		
		if(trustedSwitchOutCryptoChallenge == null) {
			Log.msg(this, "ignoring crypto challenge reply for address slot (didn't challenge the remote): %s", addressSlot);
			return;
		}
		
		Log.debug(this, "received crypto challenge reply for address slot: %s", addressSlot);
		
		Data cipherData = cryptoChallengeReplyInterserviceMessage.getDataComponent().getData();
		
		boolean success;
		try {
			success = trustedSwitchOutCryptoChallenge.verifySolvedData(cipherData);
		} catch (CryptoException e) {
			Log.exception(this, e, "could not verify crypto challenge reply for address slot: %s", addressSlot);
			// TODO notify remote of failure
			return;
		}
		
		finishTrustedSwitch(addressSlot, success);
		
		sendConnectionbaseNotice(addressSlot);
		
	}
	
	public synchronized void onReceiveConnectionbaseNoticeInterserviceMessage(ConnectionbaseNoticeInterserviceMessage connectionbaseNoticeInterserviceMessage) {

		byte outConnectionBase = connectionbaseNoticeInterserviceMessage.getConnectionBase();
		
		int addressSlotId = connectionbaseNoticeInterserviceMessage.getAddressSlot();
		AddressSlot addressSlot = localAddressSlotMap.get(addressSlotId);
		
		if(addressSlot == null) {
			Log.msg(this, "ignoring connection base notice message for empty address slot %d (connection base %d)", addressSlotId, outConnectionBase);
			return;
		}
		
		Log.debug(this, "received connection base notice message from remote, connection base %d for address slot: %s", outConnectionBase, addressSlot);
		setOutConnectionBase(addressSlot, outConnectionBase);
		
	}
	
	public synchronized void onReceiveNetworkJoinNoticeInterserviceMessage(NetworkJoinNoticeInterserviceMessage networkJoinNoticeInterserviceMessage) {

		int addressSlotId = networkJoinNoticeInterserviceMessage.getAddressSlot();
		int networkSlotId = networkJoinNoticeInterserviceMessage.getNetworkSlot();
		NetworkType networkType = networkJoinNoticeInterserviceMessage.getNetworkTypeComponent().getNetworkType();
		
		AddressSlot addressSlot = remoteAddressSlotMap.get(addressSlotId);
		
		if(addressSlot == null) {
			Log.msg(this, "ignoring network join notice for empty address slot id %d, network slot id is %d, network type is: %s", addressSlotId, networkSlotId, networkType);
			return;
		}
		
		if(addressSlot.getConnectionBase() < CONNECTIONBASE_TRUSTED) {
			Log.msg(this, "ignoring network join notice for network slot id %d, network type %s and address slot (incoming connection base insufficient): %s", networkSlotId, networkType, addressSlot);
			return;
		}
		
		Log.debug(this, "received network join notice for network type %s and network slot id %d on address slot: %s", networkType, networkSlotId, addressSlot);

		onNetworkJoinNotice(addressSlot, networkSlotId, networkType);
		
	}
	
	public synchronized void onReceiveNetworkLeaveNoticeInterserviceMessage(NetworkLeaveNoticeInterserviceMessage networkLeaveNoticeInterserviceMessage) {
		
		int addressSlotId = networkLeaveNoticeInterserviceMessage.getAddressSlot();
		int networkSlotId = networkLeaveNoticeInterserviceMessage.getNetworkSlot();
		
		AddressSlot addressSlot = remoteAddressSlotMap.get(addressSlotId);
		
		if(addressSlot == null) {
			Log.msg(this, "ignoring network leave notice for empty address slot id %d, network slot id is %d", addressSlotId, networkSlotId);
			return;
		}
		
		if(addressSlot.getConnectionBase() < CONNECTIONBASE_TRUSTED) {
			Log.msg(this, "ignoring network leave notice for address slot (incoming connection base insufficient): %s", addressSlot);
			return;
		}
		
		Log.debug(this, "received network leave notice for address slot %s, network slot id %d", addressSlot, networkSlotId);
		
		onNetworkLeaveNotice(addressSlot, networkSlotId);
		
	}
	
	public synchronized void onReceiveIntegrationRequestInterserviceMessage(IntegrationRequestInterserviceMessage integrationRequestInterserviceMessage) {
		
	}
	
	public synchronized void onReceiveIntegrationConnectRequestInterserviceMessage(IntegrationConnectRequestInterserviceMessage integrationConnectRequestInterserviceMessage) {
		
	}
	
	public synchronized void onReceiveLocalLLARequestInterserviceMessage(LocalLLARequestInterserviceMessage localLLARequestInterserviceMessage) {
		Log.debug(this, "received local lla request, replying with LLA %s", this.cachedLLA.getLLA());
		sendLocalLLAReplyInterserviceMessage(this.cachedLLA.getLLA());
	}
	
	public synchronized void onReceiveLocalLLAReplyInterserviceMessage(LocalLLAReplyInterserviceMessage localLLAReplyInterserviceMessage) {
		
		LLA reportedLocalLLA = localLLAReplyInterserviceMessage.getLocalLLA();
		LLA oldReportedLocalLLA = this.reportedLocalLLA;
		
		this.reportedLocalLLA = reportedLocalLLA;
		
		Log.debug(this, "received local lla reply: %s (old: %s)", reportedLocalLLA, oldReportedLocalLLA);
		
		if(oldReportedLocalLLA == null || !oldReportedLocalLLA.equals(reportedLocalLLA)) {
			interserviceChannelActionListener.onLocalLLAReport(this, oldReportedLocalLLA, reportedLocalLLA);
		}
		
	}
	
	public synchronized void onReceiveNetworkPacketInterserviceMessage(NetworkPacketInterserviceMessage networkPacketInterserviceMessage) {

		NetworkPacket networkPacket = networkPacketInterserviceMessage.getNetworkPacket();
		
		if(networkPacket.getNetworkSlot().getNetworkNodes().size() <= 0) {
			Log.msg(this, "ignoring network packet, no network nodes on network slot %s", networkPacket.getNetworkSlot());
			return;
		}
		
		Log.debug(this, "received network packet on slot %s: %s", networkPacket.getNetworkSlot(), networkPacket);
		onReceiveNetworkPacket(networkPacket);
		
	}
	
	public synchronized void onReceiveApplicationChannelSlotAssignInterserviceMessage(ApplicationChannelSlotAssignInterserviceMessage applicationChannelSlotAssignInterserviceMessage) {
		
		int localAddressSlotId = applicationChannelSlotAssignInterserviceMessage.getReceiverAddressSlot();
		int remoteAddressSlotId = applicationChannelSlotAssignInterserviceMessage.getSenderAddressSlot();
		
		int remoteApplicationChannelSlotId = applicationChannelSlotAssignInterserviceMessage.getApplicationChannelSlot();
		
		String actionIdentifier = applicationChannelSlotAssignInterserviceMessage.getActionIdentifier();
		
		AddressSlot remoteAddressSlot = remoteAddressSlotMap.get(remoteAddressSlotId);
		
		if(remoteAddressSlot == null) {
			Log.msg(this, "ignoring application channel slot assign message for empty remote address slot id %d", remoteAddressSlotId);
			return;
		}
		
		if(remoteAddressSlot.getConnectionBase() < CONNECTIONBASE_TRUSTED) {
			Log.msg(this, "ignoring application channel slot assign message for remote address slot (incoming connection base insufficient): %s", remoteAddressSlot);
			return;
		}
		
		AddressSlot localAddressSlot = localAddressSlotMap.get(localAddressSlotId);
		
		if(localAddressSlot == null) {
			Log.msg(this, "ignoring application channel slot assign message for empty locla address slot id %d", localAddressSlotId);
			return;
		}
		
		Log.debug(this, "received application channel slot assign message for local address slot %s, remote address slot %s, application channel slot id %d and action identifier %s", localAddressSlot, remoteAddressSlot, remoteApplicationChannelSlotId, actionIdentifier);
		
		ApplicationChannelTarget applicationChannelTarget = new ApplicationChannelTarget(remoteAddressSlot.getAddress(), actionIdentifier);
		
		ApplicationChannelSlot localApplicationChannelSlot = localApplicationChannelSlotMap.find(applicationChannelTarget);
		
		if(localApplicationChannelSlot == null) {
			
			ApplicationChannel applicationChannel = interservicePolicy.checkIncomingApplicationChannel(applicationChannelTarget);
			if(applicationChannel == null) {
				Log.msg(this, "ignoring application channel slot assign message, not accepting application channel request");
			} else {
				acceptIncomingApplicationChannelRequest(applicationChannel, localAddressSlot, remoteAddressSlot, remoteApplicationChannelSlotId);
			}
			
		} else {
			
			finishOutgoingApplicationChannelRequest(localApplicationChannelSlot, remoteApplicationChannelSlotId);
			
		}
		
	}
	
	public synchronized void onReceiveApplicationChannelDataInterserviceMessage(ApplicationChannelDataInterserviceMessage applicationChannelDataInterserviceMessage) {
		
		int applicationChannelSlotId = applicationChannelDataInterserviceMessage.getApplicationChannelSlot();
		ApplicationChannelSlot applicationChannelSlot = localApplicationChannelSlotMap.get(applicationChannelSlotId);
		
		if(applicationChannelSlot == null) {
			Log.msg(this, "ignoring application channel data message for empty local application channel slot id %d", applicationChannelSlotId);
			return;
		}
		
		Data data = applicationChannelDataInterserviceMessage.getDataComponent().getData();
		Log.msg(this, "forwarding data from application channel slot %s to application connection: %s", applicationChannelSlot, data);
		
		ApplicationChannel applicationChannel = applicationChannelSlot.getApplicationChannel();
		applicationChannel.getApplicationSideApplicationChannelActionListener().onData(applicationChannel, data);
		
	}
	
	//

	@Override
	public NetworkPacket getNetworkPacket(int slot) {
		NetworkSlot networkSlot = localNetworkSlotMap.get(slot);
		return networkSlot == null ? null : networkSlot.getNetworkPacket();
	}
	
	//

	@Override
	public synchronized void onData(ApplicationChannel applicationChannel, Data data) {
		
		ApplicationChannelSlot applicationChannelSlot = applicationChannel.getInterserviceChannelApplicationChannelSlot();
		
		Log.msg(this, "sending data on application channel slot %s: %s", applicationChannelSlot, data);
		sendApplicationChannelDataInterserviceMessage(applicationChannelSlot, data);
		
	}
	
}
