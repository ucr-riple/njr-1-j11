package org.dclayer.net.componentinterface;

import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.RSAKey;
import org.dclayer.exception.crypto.InsufficientKeySizeException;

public interface RSAKeyComponentI {

	public Key getKey() throws InsufficientKeySizeException;
	public void setKey(RSAKey rsaKey);
	
}
