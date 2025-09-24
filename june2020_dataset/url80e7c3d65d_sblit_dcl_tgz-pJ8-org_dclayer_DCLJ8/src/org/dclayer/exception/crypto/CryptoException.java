package org.dclayer.exception.crypto;
/**
 * an Exception which is thrown when errors occur while crypto operation
 */
public class CryptoException extends Exception {
	/**
	 * creates a new {@link CryptoException} with an error message
	 * @param msg the error message
	 */
	public CryptoException(String msg) {
		super(msg);
	}
	
	/**
	 * creates a new {@link CryptoException} with a {@link Throwable} as cause
	 * @param cause the Throwable that caused the BufException
	 */
	public CryptoException(Throwable cause) {
		super(cause);
	}
	
	public CryptoException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
