package org.dclayer.net;

import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.KeyPair;

public interface CryptoPacketComponentI extends PacketComponentI {

	public void setKeyPair(KeyPair keyPair);
	public void setKeyPair(Key publicKey, Key privateKey);
	public Key getPrivateKey();
	public Key getPublicKey();

}
