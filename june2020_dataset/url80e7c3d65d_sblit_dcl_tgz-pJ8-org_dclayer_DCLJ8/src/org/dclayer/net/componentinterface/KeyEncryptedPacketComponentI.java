package org.dclayer.net.componentinterface;

import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.KeyPair;
import org.dclayer.net.PacketComponentI;

public interface KeyEncryptedPacketComponentI extends PacketComponentI {

	public void setKeyPair(KeyPair keyPair);
	public void setKeyPair(Key publicKey, Key privateKey);
	
	public Key getPrivateKey();
	public Key getPublicKey();
	
}
