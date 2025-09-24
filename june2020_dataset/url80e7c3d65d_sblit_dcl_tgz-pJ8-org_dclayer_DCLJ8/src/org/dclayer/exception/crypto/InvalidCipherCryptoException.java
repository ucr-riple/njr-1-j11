package org.dclayer.exception.crypto;

public class InvalidCipherCryptoException extends CryptoException {
	public InvalidCipherCryptoException(Throwable cause) {
		super("Invalid cipher", cause);
	}
}
