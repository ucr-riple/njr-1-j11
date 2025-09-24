package org.dclayer.net.link.bmcp.crypto.init;

import org.dclayer.net.PacketComponentI;

public interface CryptoInitPacketComponentI extends PacketComponentI {
	
	public CryptoInitializationMethod getCryptoInitializationMethod();

}
