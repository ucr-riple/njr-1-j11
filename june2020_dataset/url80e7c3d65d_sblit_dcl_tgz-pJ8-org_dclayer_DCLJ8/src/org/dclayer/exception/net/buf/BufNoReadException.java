package org.dclayer.exception.net.buf;

/**
 * an Exception which is thrown when attempting to read from a write-only buf
 */
public class BufNoReadException extends BufException {

	/**
	 * creates a {@link BufNoReadException}
	 */
	public BufNoReadException() {
		super("Buf can not be read from");
	}

}
