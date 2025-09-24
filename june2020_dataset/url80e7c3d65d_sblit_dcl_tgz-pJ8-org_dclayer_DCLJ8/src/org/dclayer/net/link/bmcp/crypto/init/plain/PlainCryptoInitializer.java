package org.dclayer.net.link.bmcp.crypto.init.plain;

import org.dclayer.net.link.bmcp.crypto.init.CryptoInitPacketComponentI;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitializationMethod;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitializer;
import org.dclayer.net.link.bmcp.crypto.init.plain.component.PlainInitializationMessage;

public class PlainCryptoInitializer implements CryptoInitializer {
	
	private CryptoInitPacketComponentI sendMessage = new PlainInitializationMessage();
	private CryptoInitPacketComponentI receiveMessage = new PlainInitializationMessage();
	
	private boolean sent = false;
	
	@Override
	public CryptoInitializationMethod getCryptoInitializationMethod() {
		return CryptoInitializationMethod.CRYPTO_INIT_PLAIN;
	}
	
	@Override
	public CryptoInitPacketComponentI getReceivePacketComponent() {
		return receiveMessage;
	}
	
	@Override
	public CryptoInitPacketComponentI getSendPacketComponent(Runnable ackCallbackRunnable) {
		if(sent) return null;
		sent = true;
		ackCallbackRunnable.run();
		return sendMessage;
	}
	
	@Override
	public boolean processPacketComponent() {
		return false;
	}
	
	@Override
	public boolean completedSuccessfully() {
		return sent;
	}
	
}
