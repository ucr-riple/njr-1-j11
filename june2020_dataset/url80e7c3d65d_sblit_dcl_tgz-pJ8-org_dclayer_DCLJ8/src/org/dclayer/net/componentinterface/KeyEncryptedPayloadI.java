package org.dclayer.net.componentinterface;

import org.dclayer.crypto.key.Key;
import org.dclayer.net.buf.ByteBuf;

public interface KeyEncryptedPayloadI {

	public ByteBuf getWriteByteBuf(Key publicKey, Key privateKey, int payloadLength);
	public ByteBuf getReadByteBuf();
	
	public Key getKey();
	
}
