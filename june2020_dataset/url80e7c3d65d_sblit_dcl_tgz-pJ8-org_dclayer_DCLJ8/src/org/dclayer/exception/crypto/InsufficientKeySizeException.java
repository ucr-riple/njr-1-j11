package org.dclayer.exception.crypto;

public class InsufficientKeySizeException extends CryptoException {

	public InsufficientKeySizeException() {
		super("Key too small");
	}
	
}
