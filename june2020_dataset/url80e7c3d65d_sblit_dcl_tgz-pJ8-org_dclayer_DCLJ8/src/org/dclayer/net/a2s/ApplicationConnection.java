package org.dclayer.net.a2s;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;

import org.dclayer.DCL;
import org.dclayer.crypto.Crypto;
import org.dclayer.crypto.challenge.CryptoChallenge;
import org.dclayer.crypto.challenge.Fixed128ByteCryptoChallenge;
import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.KeyPair;
import org.dclayer.crypto.key.RemoteRSAKey;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.a2s.applicationchannelslot.ApplicationChannelSlot;
import org.dclayer.net.a2s.applicationchannelslot.ApplicationChannelSlotMap;
import org.dclayer.net.a2s.message.ApplicationChannelConnectedMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelDataMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelIncomingRequestMessageI;
import org.dclayer.net.a2s.message.DataMessageI;
import org.dclayer.net.a2s.message.RevisionMessageI;
import org.dclayer.net.a2s.message.SlotAssignMessageI;
import org.dclayer.net.a2s.remotekey.ApplicationConnectionRemoteRSAKeyInterface;
import org.dclayer.net.a2s.remotekey.RemoteRSAKeyCommunicationInterface;
import org.dclayer.net.a2s.remotekey.RemoteRSAKeyCommunicationListener;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.a2s.rev35.Rev35Message;
import org.dclayer.net.address.Address;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;
import org.dclayer.net.applicationchannel.ApplicationSideApplicationChannelActionListener;
import org.dclayer.net.buf.StreamByteBuf;
import org.dclayer.net.component.AbsKeyComponent;
import org.dclayer.net.componentinterface.AbsKeyComponentI;
import org.dclayer.net.crisp.CrispPacket;
import org.dclayer.net.crisp.message.NeighborRequestCrispMessageI;
import org.dclayer.net.lla.LLA;
import org.dclayer.net.network.ApplicationNetworkInstance;
import org.dclayer.net.network.NetworkInstance;
import org.dclayer.net.network.NetworkInstanceCollection;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.NetworkType;
import org.dclayer.net.network.component.NetworkPacket;
import org.dclayer.net.network.component.NetworkPayload;
import org.dclayer.net.network.properties.CommonNetworkPayloadProperties;
import org.dclayer.net.network.slot.GenericNetworkSlot;
import org.dclayer.net.network.slot.GenericNetworkSlotMap;
import org.dclayer.threadswitch.ThreadEnvironment;
import org.dclayer.threadswitch.ThreadExecutor;
import org.dclayer.threadswitch.ThreadSwitch;

/**
 * a connection to an application instance
 */
public class ApplicationConnection extends Thread implements A2SMessageReceiver, RemoteRSAKeyCommunicationInterface, ApplicationSideApplicationChannelActionListener, HierarchicalLevel {
	
	private Socket socket;
	
	private HierarchicalLevel parentHierarchicalLevel;
	private ApplicationConnectionActionListener applicationConnectionActionListener;
	
	private StreamByteBuf streamByteBuf;
	
	private final Rev0Message receiveRev0Message = new Rev0Message();
	private final Rev0Message sendRev0Message = new Rev0Message();
	
	private final Rev35Message receiveRev35Message = new Rev35Message();
	private final Rev35Message sendRev35Message = new Rev35Message();
	
	private ThreadSwitch<A2SMessage> receiveThreadSwitch;
	
	private A2SMessage sendMessage = sendRev35Message;
	
	//
	
	private int revision = 0;
	
	private KeyPair applicationAddressKeyPair = null;
	private Address applicationAddress = null;
	
	private GenericNetworkSlotMap<ApplicationNetworkInstance> networkSlotMap = new GenericNetworkSlotMap<>();
	
	private LinkedList<NetworkType> networksToJoin = new LinkedList<>();
	
	private RemoteRSAKeyCommunicationListener remoteRSAKeyCommunicationListener;
	
	private boolean ignoreNeighborRequests = false;
	
	private ApplicationChannelSlotMap applicationChannelSlotMap = new ApplicationChannelSlotMap();
	
	/**
	 * create a new {@link ApplicationConnection} for the given {@link TCPSocketConnection}
	 * @param tcpSocketConnection the {@link TCPSocketConnection} to create an {@link ApplicationConnection} for
	 */
	public ApplicationConnection(HierarchicalLevel parentHierarchicalLevel, ApplicationConnectionActionListener applicationConnectionActionListener, Socket socket) {
		
		this.socket = socket;
		this.parentHierarchicalLevel = parentHierarchicalLevel;
		this.applicationConnectionActionListener = applicationConnectionActionListener;
		
		InputStream inputStream;
		OutputStream outputStream;
		
		try {
			inputStream = socket.getInputStream();
		} catch (IOException e) {
			Log.exception(this, e);
			return;
		}
		
		try {
			outputStream = new BufferedOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			Log.exception(this, e);
			return;
		}
		
		this.streamByteBuf = new StreamByteBuf(inputStream, outputStream);
		
		this.start();
		
	}
	
	public synchronized void setApplicationAddressKeyPair(KeyPair applicationAddressKeyPair) {
		
		this.applicationAddressKeyPair = applicationAddressKeyPair;
		this.applicationAddress = new Address(applicationAddressKeyPair, new NetworkInstanceCollection());
		
		applicationConnectionActionListener.onAddress(applicationAddress);
		
		for(NetworkType networkType : networksToJoin) {
			joinNetwork(networkType);
		}
		
		networksToJoin = null;
		
	}
	
	public Address getApplicationAddress() {
		return applicationAddress;
	}
	
	private void generateKeyPair() {
		Log.msg(this, "generating rsa address key pair");
		setApplicationAddressKeyPair(Crypto.generateAddressRSAKeyPair());
		Log.msg(this, "generated %d bits rsa address key pair", this.applicationAddressKeyPair.getPublicKey().getNumBits());
	}
	
	private void onForward(NetworkPayload networkPayload, GenericNetworkSlot<? extends NetworkNode> networkSlot, NetworkPacket networkPacket, NetworkInstance networkInstance) {
		
		Log.debug(this, "received NetworkPacket: %s", networkPacket.represent(true));
		
		try {
			networkPayload.read();
		} catch (ParseException e) {
			Log.exception(this, e, "could not parse network payload");
			return;
		} catch (BufException e) {
			Log.exception(this, e, "could not parse network payload");
			return;
		}
		
		if(networkPayload.isDestinedForService()) {
			Log.debug(this, "delivering network payload destined for service: %s", networkPayload.represent(true));
			applicationConnectionActionListener.onServiceNetworkPayload(networkPayload, networkInstance);
		} else {
			Log.debug(this, "sending data message to application, network payload: %s", networkPayload.represent(true));
			sendDataMessage(networkSlot.getSlot(), networkPayload);
		}
		
	}
	
	private synchronized void joinNetwork(final NetworkType networkType) {
		
		if(applicationAddress == null) {
			networksToJoin.add(networkType);
			return;
		}
		
		final NetworkPayload inNetworkPayload = networkType.makeInNetworkPayload(null);
		
		ApplicationNetworkInstance applicationNetworkInstance = new ApplicationNetworkInstance(this, networkType, applicationAddress) {
			@Override
			public synchronized boolean onForward(NetworkPacket networkPacket, GenericNetworkSlot<? extends NetworkNode> networkSlot) {
				inNetworkPayload.setReadDataComponent(networkPacket.getDataComponent());
				ApplicationConnection.this.onForward(inNetworkPayload, networkSlot, networkPacket, this);
				return true;
			}

			@Override
			public void neighborRequest(Key senderPublicKey, String actionIdentifier, LLA senderLLA, boolean response, Data ignoreData) {
				onNeighborRequest(this, senderPublicKey, actionIdentifier, senderLLA, response, ignoreData);
			}
		};
		
		applicationAddress.getNetworkInstanceCollection().addNetworkInstance(applicationNetworkInstance);
		
		applicationConnectionActionListener.onNetworkInstance(applicationNetworkInstance);
		
		GenericNetworkSlot<ApplicationNetworkInstance> networkSlot = networkSlotMap.add(networkType);
		networkSlot.addNetworkNode(applicationNetworkInstance);
		applicationNetworkInstance.setNetworkSlot(networkSlot);
		
		applicationNetworkInstance.setRequestedApplicationChannelTargets(new HashSet<ApplicationChannelTarget>());
		
		// TODO make these changeable for the connected application
		CommonNetworkPayloadProperties commonNetworkPayloadProperties = new CommonNetworkPayloadProperties()
				.destinedForService(false)
				.sourceAddress(true);
		
		applicationNetworkInstance.setOutNetworkPayload(commonNetworkPayloadProperties);
		
		applicationNetworkInstance.setOutCrispPacket();
		
		Log.msg(this, "joined network: %s", networkSlot);
		
		sendSlotAssignMessage(networkSlot.getSlot(), networkType, applicationNetworkInstance.getScaledAddress());
		
	}
	
	//
	
	public synchronized void onNeighborRequest(ApplicationNetworkInstance applicationNetworkInstance, Key senderPublicKey, String fullActionIdentifier, LLA senderLLA, boolean response, Data ignoreData) {
		
		if(!fullActionIdentifier.startsWith(DCL.ACTION_IDENTIFIER_APPLICATION_CHANNEL_PREFIX)) {
			Log.debug(this, "ignoring neighbor request, unknown action identifier: fullActionIdentifier=%s, senderLLA=%s", fullActionIdentifier, senderLLA);
			return;
		}
		
		String actionIdentifierSuffix = fullActionIdentifier.substring(DCL.ACTION_IDENTIFIER_APPLICATION_CHANNEL_PREFIX.length());
		Address remoteAddress = new Address<>(KeyPair.fromPublicKey(senderPublicKey));
		
		ApplicationChannelSlot applicationChannelSlot = applicationChannelSlotMap.find(new ApplicationChannelTarget(remoteAddress, actionIdentifierSuffix));
		
		if(applicationChannelSlot == null) {
			
			if(ignoreNeighborRequests || response) {
				Log.debug(this, "ignoring neighbor request%s: fullActionIdentifier=%s, senderLLA=%s", response ? " (unsolicited respons)" : "", fullActionIdentifier, senderLLA);
				return;
			}
			
			Log.debug(this, "forwarding neighbor request to application: actionIdentifierSuffix=%s, senderLLA=%s", actionIdentifierSuffix, senderLLA);
			
			sendApplicationChannelIncomingRequestMessage(applicationNetworkInstance.getNetworkSlot().getSlot(), actionIdentifierSuffix, remoteAddress.getKeyPair().getPublicKey(), senderLLA, ignoreData);
			
		} else {
			
			Log.debug(this, "got neighbor request response: fullActionIdentifier=%s, senderLLA=%s", fullActionIdentifier, senderLLA);
			
			if(response) {
				
				Log.msg(this, "initiating application channel to %s", senderLLA);
				applicationConnectionActionListener.initiateApplicationChannel(senderLLA, applicationNetworkInstance, applicationChannelSlot.getApplicationChannel());
				
			} else {
				
				Log.debug(this, "expected response to previously sent neighbor request, got another request instead, responding");
				respondNeighbor(applicationNetworkInstance, applicationChannelSlot.getApplicationChannel(), senderLLA, ignoreData);
				
			}
			
		}
		
	}
	
	private synchronized void requestNeighbor(ApplicationNetworkInstance applicationNetworkInstance, Address destinationAddress, String actionIdentifier) {
		
		sendNeighborRequest(applicationNetworkInstance, destinationAddress, DCL.ACTION_IDENTIFIER_APPLICATION_CHANNEL_PREFIX + actionIdentifier, false);
		
	}
	
	private synchronized void respondNeighbor(ApplicationNetworkInstance applicationNetworkInstance, ApplicationChannel applicationChannel, LLA senderLLA, Data ignoreData) {
		
		Log.msg(this, "expecting incoming connection from %s", senderLLA);
		
		Address remoteAddress = applicationChannel.getApplicationChannelTarget().getRemoteAddress();
		String actionIdentifier = applicationChannel.getApplicationChannelTarget().getActionIdentifier();
		
		applicationConnectionActionListener.prepareForIncomingApplicationChannel(senderLLA, applicationNetworkInstance, applicationChannel, ignoreData);
		
		sendNeighborRequest(applicationNetworkInstance, remoteAddress, DCL.ACTION_IDENTIFIER_APPLICATION_CHANNEL_PREFIX + actionIdentifier, true);
		
	}
	
	//
	
	@Override
	public void run() {
		
		{
		
			Rev35Message tempReceiveMessage = new Rev35Message();
			
			for(;;) {
				
				try {
					tempReceiveMessage.read(streamByteBuf);
				} catch (ParseException e) {
					Log.exception(this, e);
					close();
					return;
				} catch (BufException e) {
					Log.exception(this, e);
					close();
					return;
				}
				
				if(tempReceiveMessage.getType() != Rev35Message.REVISION && tempReceiveMessage.getType() != Rev35Message.REVISION_BINARY) {
					Log.warning(this, "ignoring application to service message (first message must be revision message): %s", tempReceiveMessage.represent(true));
				}
				
				Log.debug(this, "received revision application to service message: %s", tempReceiveMessage.represent(true));
				
				tempReceiveMessage.callOnReceiveMethod(this);
				break;
				
			}
			
		}
		
		ThreadExecutor<A2SMessage> threadExecutor = new ThreadExecutor<A2SMessage>() {

			@Override
			public void exec(ThreadEnvironment<A2SMessage> threadEnvironment, A2SMessage message) {
				message.callOnReceiveMethod(ApplicationConnection.this);
			}
			
		};
		
		for(;;) {
			
			ThreadEnvironment<A2SMessage> threadEnvironment = receiveThreadSwitch.get();
			A2SMessage receiveMessage = threadEnvironment.getObject();
			
			Log.debug(this, "using thread: %s", threadEnvironment);
			
			try {
				receiveMessage.read(streamByteBuf);
			} catch (ParseException e) {
				Log.exception(this, e);
				close();
				return;
			} catch (BufException e) {
				Log.exception(this, e);
				close();
				return;
			}
			
			Log.debug(this, "received application to service message: %s", receiveMessage.represent(true));
			
			threadEnvironment.exec(threadExecutor);
			
		}
		
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			Log.exception(this, e, "exception while closing Socket");
		}
	}
	
	private void send() {
		try {
			streamByteBuf.write(sendMessage);
		} catch (BufException e) {
			Log.exception(this, e, "error while sending application to service message %s", sendMessage.represent(true));
			return;
		}
	}
	
	//
	
	private void sendNetworkPacket(GenericNetworkSlot<? extends NetworkNode> networkSlot, Data addressData, Data data) {
		
		NetworkNode networkNode = networkSlot.getNetworkNodes().get(0);

		NetworkPacket networkPacket = networkSlot.getNetworkPacket();
		NetworkPayload networkPayload = networkNode.getOutNetworkPayload();
		
		synchronized(networkNode) {
			
			networkPacket.setDestinationAddressData(addressData);
			networkPayload.setDestinedForService(false);
			
			networkPayload.setPayloadData(data);
			
			try {
//				networkPayload.write(networkPacket.getDataComponent());
				networkPacket.getDataComponent().setData(networkPayload);
			} catch (BufException e) {
				Log.exception(this, e, "could not write network packet payload");
				return;
			}
			
			networkNode.forward(networkPacket);
			
		}
		
	}
	
	private void sendNeighborRequest(ApplicationNetworkInstance applicationNetworkInstance, Address destinationAddress, String actionIdentifier, boolean response) {
		
		GenericNetworkSlot<? extends NetworkNode> networkSlot = applicationNetworkInstance.getNetworkSlot();

		NetworkPacket networkPacket = networkSlot.getNetworkPacket();
		NetworkPayload networkPayload = applicationNetworkInstance.getOutNetworkPayload();
		
		CrispPacket crispPacket = applicationNetworkInstance.getOutCrispPacket();
		
		Data destinationAddressData = applicationNetworkInstance.getNetworkType().scaleAddress(destinationAddress);
		
		synchronized(applicationNetworkInstance) {
			
			networkPacket.setDestinationAddressData(destinationAddressData);
			networkPayload.setDestinedForService(true);
			
			if(applicationConnectionActionListener.getLocalLLA(false) == null) {
				Log.warning(this, "about to get local LLA from service, might block until it is known");
			}
			
			NeighborRequestCrispMessageI neighborRequestCrispMessage = crispPacket.setNeighborRequestCrispMessage();
			neighborRequestCrispMessage.setKeyPair(applicationNetworkInstance.getAddress().getKeyPair());
			neighborRequestCrispMessage.setActionIdentifier(actionIdentifier);
			neighborRequestCrispMessage.setSenderLLA(applicationConnectionActionListener.getLocalLLA(true));
			neighborRequestCrispMessage.setResponse(response);
			if(!response) {
				neighborRequestCrispMessage.getIgnoreDataComponent().setData(applicationConnectionActionListener.getServiceIgnoreData());
			}
			
			Log.debug(this, "sending neighbor request crisp packet: %s", crispPacket.represent(true));
			
			try {
				networkPayload.setPayloadData(crispPacket);
				networkPacket.getDataComponent().setData(networkPayload);
			} catch (BufException e) {
				Log.exception(this, e, "could not write network packet payload");
				return;
			}
			
			Log.debug(this, "sending neighbor request network packet: %s", networkPacket.represent(true));
			
			applicationNetworkInstance.forward(networkPacket);
			
		}
		
	}
	
	//
	
	private synchronized void sendRevisionMessage(int revision) {
		RevisionMessageI revisionMessage = sendMessage.setRevisionMessage();
		revisionMessage.setRevision(revision);
		send();
	}
	
	private synchronized void sendDataMessage(int slot, NetworkPayload networkPayload) {
		DataMessageI dataMessage = sendMessage.setDataMessage();
		dataMessage.getSlotNumComponent().setNum(slot);
		dataMessage.getAddressComponent().setAddressData(networkPayload.getSourceAddressData()); // this may be null, doesn't matter though
		dataMessage.getDataComponent().setData(networkPayload.getPayloadData());
		send();
	}
	
	private synchronized void sendSlotAssignMessage(int slot, NetworkType networkType, Data addressData) {
		SlotAssignMessageI slotAssignMessage = sendMessage.setSlotAssignMessage();
		slotAssignMessage.setSlot(slot);
		slotAssignMessage.getNetworkTypeComponent().setNetworkType(networkType);
		slotAssignMessage.setAddressData(addressData);
		send();
	}
	
	private synchronized void sendKeyEncryptMessage(Data plainData) {
		sendMessage.setKeyEncryptDataMessage().getPlainDataComponent().setData(plainData);
		send();
	}
	
	private synchronized void sendKeyDecryptMessage(Data cipherData) {
		sendMessage.setKeyDecryptDataMessage().getCipherDataComponent().setData(cipherData);
		send();
	}
	
	private synchronized void sendKeyMaxEncryptionBlockNumBytesRequestMessage() {
		sendMessage.setKeyMaxEncryptionBlockNumBytesRequestMessage();
		send();
	}
	
	private synchronized void sendApplicationChannelIncomingRequestMessage(int networkSlotId, String actionIdentifierSuffix, Key remotePublicKey, LLA senderLLA, Data ignoreData) {
		ApplicationChannelIncomingRequestMessageI applicationChannelIncomingRequestMessage = sendMessage.setApplicationChannelIncomingRequestMessage();
		applicationChannelIncomingRequestMessage.setNetworkSlot(networkSlotId);
		applicationChannelIncomingRequestMessage.setActionIdentifierSuffix(actionIdentifierSuffix);
		applicationChannelIncomingRequestMessage.getKeyComponent().setKey(remotePublicKey);
		applicationChannelIncomingRequestMessage.setSenderLLA(senderLLA);
		applicationChannelIncomingRequestMessage.getIgnoreDataComponent().setData(ignoreData);
		send();
	}
	
	private synchronized void sendApplicationChannelConnectedMessage(int applicationChannelSlotId) {
		ApplicationChannelConnectedMessageI applicationChannelConnectedMessage = sendMessage.setApplicationChannelConnectedMessage();
		applicationChannelConnectedMessage.setChannelSlot(applicationChannelSlotId);
		send();
	}
	
	private synchronized void sendApplicationChannelDataMessage(int channelSlotId, Data data) {
		ApplicationChannelDataMessageI applicationChannelDataMessage = sendMessage.setApplicationChannelDataMessage();
		applicationChannelDataMessage.setChannelSlot(channelSlotId);
		applicationChannelDataMessage.getDataComponent().setData(data);
		send();
	}
	
	//
	
	public synchronized void onReceiveRevisionMessage(int revision) {
		
		Log.debug(this, "onReceiveRevisionMessage(%d)", revision);

		this.revision = revision;
		
		switch(revision) {
		case 0: {
			this.sendMessage = sendRev0Message;
			this.receiveThreadSwitch = new ThreadSwitch<A2SMessage>(Rev0Message.class);
			break;
		}
		case 35: {
			this.sendMessage = sendRev35Message;
			this.receiveThreadSwitch = new ThreadSwitch<A2SMessage>(Rev35Message.class);
			break;
		}
		default: {
			Log.debug(this, "revision %d is not supported, using revision 0", revision);
			this.revision = 0;
		}
		}
		
		Log.debug(this, "using revision %d, application connection %sready", this.revision, (revision == this.revision) ? "" : "not ");
		
		sendRevisionMessage(this.revision);
		
	}
	
	public synchronized void onReceiveDataMessage(int slot, Data addressData, Data data) {
		
		Log.debug(this, "onReceiveDataMessage(%d, %s, %s)", slot, addressData, data);
		
		GenericNetworkSlot<? extends NetworkNode> networkSlot = networkSlotMap.get(slot);
		
		if(networkSlot == null) {
			Log.warning(this, "received data message for empty network slot %d", slot);
			return;
		}
		
		sendNetworkPacket(networkSlot, addressData, data);
		
	}
	
	public void onReceiveGenerateKeyMessage() {
		Log.debug(this, "onReceiveGenerateKeyMessage");
		generateKeyPair();
	}
	
	public void onReceiveJoinNetworkMessage(NetworkType networkType) {
		Log.debug(this, "onReceiveJoinNetworkMessage(%s)", networkType);
		joinNetwork(networkType);
	}
	
	public void onReceiveSlotAssignMessage(int slot, NetworkType networkType, Data addressData) {
		// TODO illegal
	}
	
	@Override
	public void onReceiveAddressPublicKeyMessage(AbsKeyComponentI absKeyComponentI) {
		
		Log.debug(this, "onReceiveAddressPublicKeyMessage(%s)", absKeyComponentI);
		
		try {
			
			final Key publicKey = absKeyComponentI.getKey();
			final RemoteRSAKey privateKey = new RemoteRSAKey(new ApplicationConnectionRemoteRSAKeyInterface(publicKey.getNumBits(), this));
			
			// lets confirm this right away
			final CryptoChallenge challengingCryptoChallenge = new Fixed128ByteCryptoChallenge(publicKey);
			final CryptoChallenge solvingCryptoChallenge = new Fixed128ByteCryptoChallenge(privateKey);
			final Data plainData = challengingCryptoChallenge.makeChallengeData();
			
			Data cipherData = solvingCryptoChallenge.solveChallengeData(plainData);
			if(!challengingCryptoChallenge.verifySolvedData(cipherData)) {
				Log.warning(ApplicationConnection.this, "the application could not pass the crypto challenge for the public key it provided");
				// TODO react
			} else {
				Log.debug(ApplicationConnection.this, "application successfully completed crypto challenge for public key provided");
			}

			setApplicationAddressKeyPair(KeyPair.fromKeys(publicKey, privateKey));
			
		} catch (CryptoException e) {
			
			Log.exception(this, e);
			
		}
		
	}

	@Override
	public void onReceiveJoinDefaultNetworksMessage() {
		Log.debug(this, "onReceiveJoinDefaultNetworksMessage()");
		for(NetworkType networkType : DCL.DEFAULT_NETWORK_TYPES) {
			joinNetwork(networkType);
		}
	}

	@Override
	public void onReceiveKeyEncryptDataMessage(Data plainData) {
		// TODO illegal
	}

	@Override
	public void onReceiveKeyDecryptDataMessage(Data cipherData) {
		// TODO illegal
	}
	
	@Override
	public void onReceiveKeyCryptoResponseDataMessage(Data responseData) {
		Log.debug(this, "received key crypto response data: %s", responseData);
		if(this.remoteRSAKeyCommunicationListener != null) this.remoteRSAKeyCommunicationListener.onResponseDataMessage(responseData);
	}
	
	@Override
	public void onReceiveKeyMaxEncryptionBlockNumBytesRequestMessage() {
		// TODO illegal
	}
	
	@Override
	public void onReceiveKeyResponseNumMessage(int responseNum) {
		if(this.remoteRSAKeyCommunicationListener != null) this.remoteRSAKeyCommunicationListener.onResponseNumMessage(responseNum);
	}
	
	@Override
	public synchronized void onReceiveApplicationChannelOutgoingRequestMessage(int networkSlotId, int channelSlotId, String actionIdentifierSuffix, AbsKeyComponent keyComponent) {
		
		Log.debug(this, "received application channel request for network slot %d and channel slot %d, actionIdentifierSuffix=%s", networkSlotId, channelSlotId, actionIdentifierSuffix);
		
		Key remotePublicKey;
		try {
			remotePublicKey = keyComponent.getKey();
		} catch (CryptoException e) {
			Log.exception(this, e);
			return;
		}
		
		Address remoteAddress = new Address<>(KeyPair.fromPublicKey(remotePublicKey));
		
		GenericNetworkSlot<ApplicationNetworkInstance> networkSlot = networkSlotMap.get(networkSlotId);
		ApplicationNetworkInstance applicationNetworkInstance = networkSlot.getNetworkNodes().get(0);
		
		ApplicationChannel applicationChannel = new ApplicationChannel(new ApplicationChannelTarget(remoteAddress, actionIdentifierSuffix), this, this);
		ApplicationChannelSlot applicationChannelSlot = applicationChannelSlotMap.put(channelSlotId, applicationChannel);
		
		requestNeighbor(applicationNetworkInstance, remoteAddress, actionIdentifierSuffix);
		
	}

	@Override
	public void onReceiveApplicationChannelIncomingRequestMessage(int networkSlotId, String actionIdentifierSuffix, AbsKeyComponent keyComponent, LLA senderLLA, Data ignoreData) {
		// TODO illegal
	}

	@Override
	public synchronized void onReceiveApplicationChannelAcceptMessage(int networkSlotId, int channelSlotId, String actionIdentifierSuffix, AbsKeyComponent keyComponent, LLA senderLLA, Data ignoreData) {
		
		Log.debug(this, "received application channel accept message for network slot %d and channel slot %d, actionIdentifierSuffix=%s, senderLLA=%s", networkSlotId, channelSlotId, actionIdentifierSuffix, senderLLA);
		
		Key publicKey;
		try {
			publicKey = keyComponent.getKey();
		} catch (CryptoException e) {
			Log.exception(this, e);
			return;
		}
		
		GenericNetworkSlot<ApplicationNetworkInstance> networkSlot = networkSlotMap.get(networkSlotId);
		ApplicationNetworkInstance applicationNetworkInstance = networkSlot.getNetworkNodes().get(0);
		
		Address remoteAddress = new Address<>(KeyPair.fromPublicKey(publicKey));
		
		ApplicationChannel applicationChannel = new ApplicationChannel(new ApplicationChannelTarget(remoteAddress, actionIdentifierSuffix), this, this);
		applicationChannelSlotMap.put(channelSlotId, applicationChannel);
		
		respondNeighbor(applicationNetworkInstance, applicationChannel, senderLLA, ignoreData);
		
	}

	@Override
	public void onReceiveApplicationChannelConnectMessage(int channelSlotId) {
		// TODO illegal
	}

	@Override
	public void onReceiveApplicationChannelDataMessage(int channelSlotId, Data data) {
		
		Log.debug(this, "received application channel data message for channel slot %d, data: %s", channelSlotId, data);
		
		ApplicationChannelSlot applicationChannelSlot = applicationChannelSlotMap.get(channelSlotId);
		ApplicationChannel applicationChannel = applicationChannelSlot.getApplicationChannel();
		applicationChannel.getServiceSideApplicationChannelActionListener().onData(applicationChannel, data);
		
	}
	
	//

	@Override
	public void onConnected(ApplicationChannel applicationChannel) {
		Log.debug(this, "notifying application of connected application channel: %s", applicationChannel);
		sendApplicationChannelConnectedMessage(applicationChannel.getApplicationConnectionApplicationChannelSlot().getSlot());
	}

	@Override
	public void onDisconnected(ApplicationChannel applicationChannel) {
		Log.debug(this, "notifying application of disconnected application channel: %s", applicationChannel);
		// TODO
	}

	@Override
	public void onData(ApplicationChannel applicationChannel, Data data) {
		Log.debug(this, "notifying application of data from application channel %s: %s", applicationChannel, data);
		sendApplicationChannelDataMessage(applicationChannel.getApplicationConnectionApplicationChannelSlot().getSlot(), data);
	}
	
	//

	@Override
	public void setRemoteRSAKeyCommunicationListener(RemoteRSAKeyCommunicationListener remoteRSAKeyCommunicationListener) {
		this.remoteRSAKeyCommunicationListener = remoteRSAKeyCommunicationListener;
	}

	@Override
	public void sendEncryptMessage(Data plainData) {
		Log.debug(this, "sending key encrypt data message for plain data: %s", plainData);
		sendKeyEncryptMessage(plainData);
	}

	@Override
	public void sendDecryptMessage(Data cipherData) {
		Log.debug(this, "sending key decrypt data message for cipher data: %s", cipherData);
		sendKeyDecryptMessage(cipherData);
	}
	
	@Override
	public void sendMaxEncryptionBlockNumBytesRequestMessage() {
		Log.debug(this, "sending key max encryption block num bytes request message");
		sendKeyMaxEncryptionBlockNumBytesRequestMessage();
	}
	
	//
	
	@Override
	public String toString() {
		return String.format("ApplicationConnection %s:%d", socket.getInetAddress().toString(), socket.getPort());
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return parentHierarchicalLevel;
	}
	
}
