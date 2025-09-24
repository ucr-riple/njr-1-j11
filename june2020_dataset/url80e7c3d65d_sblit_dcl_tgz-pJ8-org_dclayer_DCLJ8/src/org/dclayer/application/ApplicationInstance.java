package org.dclayer.application;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.dclayer.application.applicationchannel.AbsApplicationChannel;
import org.dclayer.application.applicationchannel.ApplicationChannelActionListener;
import org.dclayer.application.applicationchannel.DCLApplicationChannel;
import org.dclayer.application.applicationchannel.slot.ApplicationChannelSlot;
import org.dclayer.application.applicationchannel.slot.ApplicationChannelSlotMap;
import org.dclayer.application.exception.ConnectionException;
import org.dclayer.application.exception.RevisionNegotiationConnectionException;
import org.dclayer.application.networktypeslotmap.NetworkEndpointSlot;
import org.dclayer.application.networktypeslotmap.NetworkEndpointSlotMap;
import org.dclayer.callback.VoidCallback;
import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.KeyPair;
import org.dclayer.crypto.key.RSAKey;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.exception.crypto.InvalidCipherCryptoException;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.a2s.A2SMessage;
import org.dclayer.net.a2s.A2SMessageReceiver;
import org.dclayer.net.a2s.message.ApplicationChannelAcceptMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelDataMessageI;
import org.dclayer.net.a2s.message.ApplicationChannelOutgoingRequestMessageI;
import org.dclayer.net.a2s.message.DataMessageI;
import org.dclayer.net.a2s.rev0.Rev0Message;
import org.dclayer.net.address.Address;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;
import org.dclayer.net.buf.StreamByteBuf;
import org.dclayer.net.component.AbsKeyComponent;
import org.dclayer.net.componentinterface.AbsKeyComponentI;
import org.dclayer.net.lla.LLA;
import org.dclayer.net.network.NetworkType;

public class ApplicationInstance extends Thread implements A2SMessageReceiver {
	
	public static final int REVISION = 0;
	
	//
	
	private StreamByteBuf streamByteBuf;
	
	private A2SMessage receiveMessage = new Rev0Message();
	private A2SMessage sendMessage = new Rev0Message();
	
	private boolean receivedRevision = false;
	private int sentRevision;
	private int revision = REVISION;
	
	private NetworkEndpointSlotMap networkEndpointSlotMap = new NetworkEndpointSlotMap();
	
	private Address<RSAKey> address;
	
	private NetworkEndpointActionListener defaultNetworkEndpointActionListener;
	private Map<NetworkType, NetworkEndpointActionListener> networkTypeListeners = new HashMap<>();
	
	private ApplicationChannelSlotMap applicationChannelSlotMap = new ApplicationChannelSlotMap();
	
	public ApplicationInstance(
			InetAddress inetAddress,
			int port,
			KeyPair<RSAKey> addressKeyPair,
			NetworkEndpointActionListener defaultNetworkEndpointsActionListener) throws ConnectionException {
		
		this.address = new Address<>(addressKeyPair);
		
		this.defaultNetworkEndpointActionListener = defaultNetworkEndpointsActionListener;
		
		try {
			
			Socket socket = new Socket(inetAddress, port);
			
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
			
			this.streamByteBuf = new StreamByteBuf(inputStream, outputStream);
			
		} catch(IOException e) {
			throw new ConnectionException(e);
		}
		
		try {
			
			this.sentRevision = REVISION;
			
			for(;;) {
				
				this.receivedRevision = false;
				
				sendRevisionMessage(sentRevision);
				
				receiveMessage().callOnReceiveMethod(this);
				
				if(!this.receivedRevision) {
					throw new RevisionNegotiationConnectionException();
				}
				
				if(this.revision == this.sentRevision) {
					break;
				}
				
				switch(this.revision) {
				case 0: {
					this.sentRevision = this.revision;
					break;
				}
				default: {
					throw new RevisionNegotiationConnectionException();
				}
				}
				
			}
			
		} catch (BufException e) {
			throw new ConnectionException(e);
		} catch (ParseException e) {
			throw new ConnectionException(e);
		}
		
		this.start();
		
		if(addressKeyPair != null) {
			sendAddressPublicKeyMessage(addressKeyPair.getPublicKey());
			
			if(defaultNetworkEndpointsActionListener != null) {
				sendJoinDefaultNetworksMessage();
			}
		}
		
	}
	
	//
	
	@Override
	public void run() {
		for(;;) {
			
			A2SMessage a2sMessage;
			
			try {
				a2sMessage = receiveMessage();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (BufException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
//			System.out.println(a2sMessage.represent(true));
			
			a2sMessage.callOnReceiveMethod(this);
			
		}
	}
	
	//
	
	private void send() {
		try {
			sendMessage();
		} catch (BufException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//
	
	private void sendMessage() throws BufException {
		streamByteBuf.write(sendMessage);
	}
	
	private A2SMessage receiveMessage() throws ParseException, BufException {
		receiveMessage.read(streamByteBuf);
		return receiveMessage;
	}
	
	//
	
	private synchronized void sendRevisionMessage(int revision) throws BufException {
		sendMessage.setRevisionMessage().setRevision(revision);
		sendMessage();
	}
	
	private synchronized void sendAddressPublicKeyMessage(RSAKey publicKey) {
		sendMessage.setAddressPublicKeyMessage().getKeyComponent().setRSAKeyComponent().setKey(publicKey);
		send();
	}
	
	private synchronized void sendJoinDefaultNetworksMessage() {
		sendMessage.setJoinDefaultNetworksMessage();
		send();
	}
	
	private synchronized void sendDataMessage(NetworkEndpointSlot networkEndpointSlot, Data destinationAddressData, Data data) {
		DataMessageI dataMessageI = sendMessage.setDataMessage();
		dataMessageI.getSlotNumComponent().setNum(networkEndpointSlot.getSlot());
		dataMessageI.getAddressComponent().setAddressData(destinationAddressData);
		dataMessageI.getDataComponent().setData(data);
		send();
	}
	
	private synchronized void sendKeyCryptoResponseDataMessage(Data responseData) {
		sendMessage.setKeyCryptoResponseDataMessage().getResponseDataComponent().setData(responseData);
		send();
	}
	
	private synchronized void sendKeyResponseNumMessage(int responseNum) {
		sendMessage.setKeyResponseNumMessage().setResponseNum(responseNum);
		send();
	}
	
	private synchronized void sendApplicationChannelRequestMessage(int networkSlotId, int channelSlotId, String actionIdentifierSuffix, Key remotePublicKey) {
		ApplicationChannelOutgoingRequestMessageI applicationChannelRequestMessage = sendMessage.setApplicationChannelOutgoingRequestMessage();
		applicationChannelRequestMessage.setNetworkSlot(networkSlotId);
		applicationChannelRequestMessage.setChannelSlot(channelSlotId);
		applicationChannelRequestMessage.setActionIdentifierSuffix(actionIdentifierSuffix);
		applicationChannelRequestMessage.getKeyComponent().setKey(remotePublicKey);
		send();
	}
	
	private synchronized void sendApplicationChannelAcceptMessage(int networkSlotId, int channelSlotId, String actionIdentifierSuffix, Key remotePublicKey, LLA senderLLA, Data ignoreData) {
		ApplicationChannelAcceptMessageI applicationChannelAcceptMessage = sendMessage.setApplicationChannelAcceptMessage();
		applicationChannelAcceptMessage.setNetworkSlot(networkSlotId);
		applicationChannelAcceptMessage.setChannelSlot(channelSlotId);
		applicationChannelAcceptMessage.setActionIdentifierSuffix(actionIdentifierSuffix);
		applicationChannelAcceptMessage.getKeyComponent().setKey(remotePublicKey);
		applicationChannelAcceptMessage.setSenderLLA(senderLLA);
		applicationChannelAcceptMessage.getIgnoreDataComponent().setData(ignoreData);
		send();
	}
	
	private synchronized void sendApplicationChannelDataMessage(int channelSlotId, Data data) {
		ApplicationChannelDataMessageI applicationChannelDataMessage = sendMessage.setApplicationChannelDataMessage();
		applicationChannelDataMessage.setChannelSlot(channelSlotId);
		applicationChannelDataMessage.getDataComponent().setData(data);
		send();
	}
	
	//
	
	public void send(NetworkEndpointSlot networkEndpointSlot, Data destinationAddressData, Data data) {
		sendDataMessage(networkEndpointSlot, destinationAddressData, data);
	}
	
	public synchronized void requestApplicationChannel(NetworkEndpointSlot networkEndpointSlot, String actionIdentifier, Key remotePublicKey, ApplicationChannelActionListener applicationChannelActionListener) {
		
		Address remoteAddress = new Address<>(KeyPair.fromPublicKey(remotePublicKey));
		ApplicationChannelTarget applicationChannelTarget = new ApplicationChannelTarget(remoteAddress, actionIdentifier);
		
		ApplicationChannelSlot applicationChannelSlot = applicationChannelSlotMap.find(applicationChannelTarget);
		if(applicationChannelSlot == null) {
			applicationChannelSlot = applicationChannelSlotMap.add(new DCLApplicationChannel(applicationChannelTarget, applicationChannelActionListener, true));
		} else {
			applicationChannelSlot.getApplicationChannel().setApplicationChannelActionListener(applicationChannelActionListener);
		}
		
		sendApplicationChannelRequestMessage(networkEndpointSlot.getSlot(), applicationChannelSlot.getSlot(), actionIdentifier, remotePublicKey);
		
	}
	
	//

	@Override
	public synchronized void onReceiveRevisionMessage(int revision) {
		this.receivedRevision = true;
		this.revision = revision;
	}

	@Override
	public synchronized void onReceiveDataMessage(int slot, Data addressData, Data data) {
		NetworkEndpointSlot networkEndpointSlot = networkEndpointSlotMap.get(slot);
		NetworkEndpoint networkEndpoint = networkEndpointSlot.getNetworkEndpoint();
		networkEndpoint.getNetworkEndpointActionListener().onReceive(networkEndpointSlot, data, addressData);
	}

	@Override
	public void onReceiveGenerateKeyMessage() {
		// TODO illegal
	}

	@Override
	public void onReceiveJoinNetworkMessage(NetworkType networkType) {
		// TODO illegal
	}

	@Override
	public synchronized void onReceiveSlotAssignMessage(int slot, NetworkType networkType, Data addressData) {
		
		NetworkEndpointActionListener networkEndpointActionListener = networkTypeListeners.get(networkType);
		
		if(networkEndpointActionListener == null) {
			if(defaultNetworkEndpointActionListener != null) {
				networkEndpointActionListener = defaultNetworkEndpointActionListener;
			} else {
				return;
			}
		}
		
		NetworkEndpoint networkEndpoint = new NetworkEndpoint(networkType, networkEndpointActionListener);
		NetworkEndpointSlot networkEndpointSlot = networkEndpointSlotMap.put(slot, networkEndpoint);
		networkEndpoint.getNetworkEndpointActionListener().onJoin(networkEndpointSlot, addressData);
		
	}

	@Override
	public void onReceiveAddressPublicKeyMessage(AbsKeyComponentI absKeyComponentI) {
		// TODO illegal
	}

	@Override
	public void onReceiveJoinDefaultNetworksMessage() {
		// TODO illegal
	}

	@Override
	public synchronized void onReceiveKeyEncryptDataMessage(Data plainData) {
		Data cipherData;
		try {
			cipherData = this.address.getKeyPair().getPrivateKey().encrypt(plainData);
		} catch (InvalidCipherCryptoException e) {
			// TODO
			cipherData = null;
		}
		sendKeyCryptoResponseDataMessage(cipherData);
	}

	@Override
	public synchronized void onReceiveKeyDecryptDataMessage(Data cipherData) {
		Data plainData;
		try {
			plainData = this.address.getKeyPair().getPrivateKey().decrypt(cipherData);
		} catch (InvalidCipherCryptoException e) {
			// TODO
			plainData = null;
		}
		sendKeyCryptoResponseDataMessage(plainData);
	}

	@Override
	public void onReceiveKeyCryptoResponseDataMessage(Data responseData) {
		// TODO illegal
	}
	
	@Override
	public void onReceiveKeyMaxEncryptionBlockNumBytesRequestMessage() {
		sendKeyResponseNumMessage(this.address.getKeyPair().getPrivateKey().getMaxEncryptionBlockNumBytes());
	}
	
	@Override
	public void onReceiveKeyResponseNumMessage(int maxEncryptionBlockNumBytes) {
		// TODO illegal
	}

	@Override
	public synchronized void onReceiveApplicationChannelIncomingRequestMessage(int networkSlotId, String actionIdentifierSuffix, AbsKeyComponent keyComponent, LLA senderLLA, Data ignoreData) {
		
		Key remotePublicKey;
		try {
			remotePublicKey = keyComponent.getKey();
		} catch (CryptoException e) {
			e.printStackTrace();
			return;
		}
		
		NetworkEndpointSlot networkEndpointSlot = networkEndpointSlotMap.get(networkSlotId);
		
		if(networkEndpointSlot == null) return;
		
		NetworkEndpointActionListener networkEndpointActionListener = networkEndpointSlot.getNetworkEndpoint().getNetworkEndpointActionListener();
		
		ApplicationChannelActionListener applicationChannelActionListener = networkEndpointActionListener.onApplicationChannelRequest(networkEndpointSlot, remotePublicKey, actionIdentifierSuffix, senderLLA);
		
		if(applicationChannelActionListener != null) {
			
			Address remoteAddress = new Address<>(KeyPair.fromPublicKey(remotePublicKey));
			ApplicationChannelTarget applicationChannelTarget = new ApplicationChannelTarget(remoteAddress, actionIdentifierSuffix);
			ApplicationChannelSlot applicationChannelSlot = applicationChannelSlotMap.add(new DCLApplicationChannel(applicationChannelTarget, applicationChannelActionListener, false));
			
			sendApplicationChannelAcceptMessage(networkSlotId, applicationChannelSlot.getSlot(), actionIdentifierSuffix, remotePublicKey, senderLLA, ignoreData);
			
		}
		
	}

	@Override
	public synchronized void onReceiveApplicationChannelOutgoingRequestMessage(int networkSlotId, int channelSlotId, String actionIdentifierSuffix, AbsKeyComponent keyComponent) {
		// TODO illegal
	}

	@Override
	public synchronized void onReceiveApplicationChannelAcceptMessage(int networkSlotId, int channelSlotId, String actionIdentifierSuffix, AbsKeyComponent keyComponent, LLA senderLLA, Data ignoreData) {
		// TODO illegal
	}
	
	@Override
	public synchronized void onReceiveApplicationChannelConnectMessage(int channelSlotId) {
		
		ApplicationChannelSlot applicationChannelSlot = applicationChannelSlotMap.get(channelSlotId);
		final AbsApplicationChannel applicationChannel = applicationChannelSlot.getApplicationChannel();
		
		applicationChannel.makePipes(new VoidCallback<Data>() {
			@Override
			public void callback(Data data) {
				sendApplicationChannelDataMessage(applicationChannel.getApplicationChannelSlot().getSlot(), data);
			}
		});
		
		applicationChannel.getApplicationChannelActionListener().onApplicationChannelConnected(applicationChannel);
		
	}
	
	@Override
	public synchronized void onReceiveApplicationChannelDataMessage(int channelSlotId, Data data) {
		
		ApplicationChannelSlot applicationChannelSlot = applicationChannelSlotMap.get(channelSlotId);
		AbsApplicationChannel applicationChannel = applicationChannelSlot.getApplicationChannel();
		applicationChannel.pushData(data);
		
	}
	
	//
	
}
