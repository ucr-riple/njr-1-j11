package org.dclayer.net.componentinterface;

import org.dclayer.crypto.key.Key;
import org.dclayer.exception.crypto.CryptoException;

public interface AbsKeyComponentI {

	public Key getKey() throws CryptoException;
	
}
