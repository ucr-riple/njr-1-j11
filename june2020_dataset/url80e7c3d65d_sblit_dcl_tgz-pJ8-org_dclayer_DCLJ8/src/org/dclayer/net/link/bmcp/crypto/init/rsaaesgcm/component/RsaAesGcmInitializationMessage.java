package org.dclayer.net.link.bmcp.crypto.init.rsaaesgcm.component;

import org.dclayer.net.PacketComponentI;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitPacketComponentI;
import org.dclayer.net.link.bmcp.crypto.init.CryptoInitializationMethod;
import org.dclayer.net.packetcomponent.Child;
import org.dclayer.net.packetcomponent.SwitchPacketComponent;

public class RsaAesGcmInitializationMessage extends SwitchPacketComponent<PacketComponentI> implements CryptoInitPacketComponentI {
	
	public static final int PUBKEY = 0;
	public static final int AESKEY = 1;
	
	//
	
	@Child(index = PUBKEY) public RsaAesGcmPubkeyMessage pubkeyMessage;
	@Child(index = AESKEY) public RsaAesGcmAeskeyMessage aeskeyMessage;
	
	//
	
	public RsaAesGcmInitializationMessage() {
		
	}
	
	public RsaAesGcmInitializationMessage(Object onReceiveObject) {
		super(onReceiveObject);
	}
	
	@Override
	public CryptoInitializationMethod getCryptoInitializationMethod() {
		return CryptoInitializationMethod.CRYPTO_INIT_RSA_AES128_GCM;
	}

}
