package org.dclayer.net.link.bmcp.crypto.init.rsaaesgcm;

import java.security.SecureRandom;

import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.KeyPair;
import org.dclayer.exception.crypto.CryptoException;
import org.dclayer.net.Data;
import org.dclayer.net.link.Link;
import org.dclayer.net.link.bmcp.crypto.AesGcmPacketCipher;
import org.dclayer.net.link.bmcp.crypto.PacketCipher;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitPacketComponentI;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitializationMethod;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitializer;
import org.dclayer.net.link.bmcp.crypto.init.rsaaesgcm.component.RsaAesGcmAeskeyMessage;
import org.dclayer.net.link.bmcp.crypto.init.rsaaesgcm.component.RsaAesGcmInitializationMessage;
import org.dclayer.net.link.bmcp.crypto.init.rsaaesgcm.component.RsaAesGcmPubkeyMessage;
import org.dclayer.net.packetcomponent.OnReceive;

public class RsaAesGcmCryptoInitializer implements CryptoInitializer {
	
	private static final int KEY_BYTES = 16;
	
	//
	
	private Link link;
	
	private RsaAesGcmInitializationMessage receiveMessage = new RsaAesGcmInitializationMessage(this);
	private RsaAesGcmInitializationMessage sendMessage = new RsaAesGcmInitializationMessage();
	
	private KeyPair rsaKeyPair;
	private Key remotePubkey;
	
	private Data inAeskeyData;
	private Data encryptedInAeskeyData;
	
	private PacketCipher inPacketCipher;
	
	private boolean receivedAeskey = false;
	private boolean sentAeskey = false;
	
	private boolean receivedPubkey = false;
	private boolean sentPubkey = false;
	
	private Runnable pubkeyAckCallbackRunnable;
	
	private boolean ack = false;
	
	public RsaAesGcmCryptoInitializer(Link link) {
		
		this.link = link;
		this.rsaKeyPair = link.getLinkCryptoInitializationKeyPair();
		this.inAeskeyData = new Data(KEY_BYTES);
		new SecureRandom().nextBytes(inAeskeyData.getData());
		
		this.inPacketCipher = new AesGcmPacketCipher(false, inAeskeyData.getData());
		
	}
	
	@Override
	public CryptoInitializationMethod getCryptoInitializationMethod() {
		return CryptoInitializationMethod.CRYPTO_INIT_RSA_AES128_GCM;
	}

	@Override
	public CryptoInitPacketComponentI getReceivePacketComponent() {
		return receiveMessage;
	}

	@Override
	public boolean processPacketComponent() {
		receiveMessage.callOnReceive();
		return ack;
	}

	@Override
	public CryptoInitPacketComponentI getSendPacketComponent(Runnable ackCallbackRunnable) {
		
		if(receivedPubkey && !sentAeskey) {
			
			sendAeskey();
			applyInCipher();
			sentAeskey = true;
			
		} else if(!sentPubkey) {
			
			sendPubkey();
			sentPubkey = true;
			pubkeyAckCallbackRunnable = ackCallbackRunnable;
			
		} else {
			
			return null;
			
		}
		
		return sendMessage;
		
	}
	
	private void abort(CryptoException e) {
		link.abortCryptoInitialization(e);
	}
	
	private void sendPubkey() {
		sendMessage.set(sendMessage.pubkeyMessage).pubkeyComponent.setKey(rsaKeyPair.getPublicKey());
	}
	
	private void sendAeskey() {
		sendMessage.set(sendMessage.aeskeyMessage).encryptedAesKey.setData(encryptedInAeskeyData);
	}
	
	private void applyInCipher() {
		link.applyNewInPacketCipher(inPacketCipher);
	}
	
	private void applyOutCipher(Data aeskeyData) throws CryptoException {
		link.applyNewOutPacketCipher(new AesGcmPacketCipher(true, aeskeyData.copyToByteArray()));
	}
	
	@OnReceive(index = RsaAesGcmInitializationMessage.PUBKEY)
	public void onReceivePubkeyMessage(RsaAesGcmPubkeyMessage pubkeyMessage) {
		
		if(!receivedPubkey) {
			try {
				this.remotePubkey = pubkeyMessage.pubkeyComponent.getKeyComponent().getKey();
				this.encryptedInAeskeyData = remotePubkey.encrypt(inAeskeyData);
			} catch (CryptoException e) {
				abort(e);
			}
		}
		
		receivedPubkey = true;
		ack = false;
		
	}
	
	@OnReceive(index = RsaAesGcmInitializationMessage.AESKEY)
	public void onReceiveAeskeyMessage(RsaAesGcmAeskeyMessage aeskeyMessage) {
		
		Data aesKey;
		try {
			aesKey = this.rsaKeyPair.getPrivateKey().decrypt(aeskeyMessage.encryptedAesKey.getData());
		} catch (CryptoException e) {
			abort(e);
			return;
		}
		
		try {
			applyOutCipher(aesKey);
		} catch (CryptoException e) {
			abort(e);
			return;
		}
		
		if(pubkeyAckCallbackRunnable != null) {
			pubkeyAckCallbackRunnable.run();
			pubkeyAckCallbackRunnable = null;
		}
		
		receivedAeskey = true;
		ack = true;
		
	}
	
	@Override
	public boolean completedSuccessfully() {
		return sentAeskey && receivedAeskey;
	}
	
}
