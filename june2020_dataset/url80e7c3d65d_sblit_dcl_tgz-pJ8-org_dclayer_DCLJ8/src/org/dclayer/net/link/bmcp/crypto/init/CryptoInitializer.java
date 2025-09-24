package org.dclayer.net.link.bmcp.crypto.init;

import org.dclayer.net.PacketComponentI;

public interface CryptoInitializer {
	
	public CryptoInitializationMethod getCryptoInitializationMethod();
	
	/**
	 * @return a {@link PacketComponentI} object that can be used to read messages into
	 */
	public CryptoInitPacketComponentI getReceivePacketComponent();
	
	/**
	 * @param ackCallbackRunnable callback for declaring this message as delivered
	 * @return a {@link PacketComponentI} object that contains the initial message to
	 * start the crypto initialization
	 */
	public CryptoInitPacketComponentI getSendPacketComponent(Runnable ackCallbackRunnable);
	
	public boolean processPacketComponent();
	
	public boolean completedSuccessfully();
	
}
