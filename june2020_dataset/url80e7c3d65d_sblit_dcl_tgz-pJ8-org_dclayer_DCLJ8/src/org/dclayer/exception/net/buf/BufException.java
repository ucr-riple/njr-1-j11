package org.dclayer.exception.net.buf;

/**
 * an Exception which is thrown when errors occur while operating on a Buf
 */
public class BufException extends Exception {
	/**
	 * creates a new {@link BufException} with an error message
	 * @param msg the error message
	 */
	public BufException(String msg) {
		super(msg);
	}
	
	/**
	 * creates a new {@link BufException} with a {@link Throwable} as cause
	 * @param cause the Throwable that caused the BufException
	 */
	public BufException(Throwable cause) {
		super(cause);
	}
}
