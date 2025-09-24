package org.dclayer.exception.net.buf;

/**
 * an Exception which is thrown when attempting to write to a read-only buf
 */
public class BufNoWriteException extends BufException {

	/**
	 * creates a {@link BufNoWriteException}
	 */
	public BufNoWriteException() {
		super("Buf can not be written to");
	}

}
