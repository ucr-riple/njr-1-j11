package org.dclayer.exception.net.buf;

/**
 * an Exception which is thrown when attempting to read/write from/to a buf which has reached the end
 */
public class EndOfBufException extends BufException {
	
	/**
	 * creates an {@link EndOfBufException} with an error message containing the length of the buf
	 * @param length the length of the Buf
	 */
	public EndOfBufException(int length) {
		super(String.format("End of buf (length: %d)", length));
	}
	
	/**
	 * creates an {@link EndOfBufException}
	 */
	public EndOfBufException() {
		super("End of buf");
	}
}
