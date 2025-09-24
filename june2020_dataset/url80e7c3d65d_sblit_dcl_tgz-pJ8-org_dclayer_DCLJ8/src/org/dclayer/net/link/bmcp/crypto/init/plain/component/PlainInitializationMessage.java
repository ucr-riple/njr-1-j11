package org.dclayer.net.link.bmcp.crypto.init.plain.component;

import org.dclayer.net.link.bmcp.crypto.init.CryptoInitPacketComponentI;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitializationMethod;
import org.dclayer.net.packetcomponent.ParentPacketComponent;

public class PlainInitializationMessage extends ParentPacketComponent implements CryptoInitPacketComponentI {

	@Override
	public CryptoInitializationMethod getCryptoInitializationMethod() {
		return CryptoInitializationMethod.CRYPTO_INIT_PLAIN;
	}

}
