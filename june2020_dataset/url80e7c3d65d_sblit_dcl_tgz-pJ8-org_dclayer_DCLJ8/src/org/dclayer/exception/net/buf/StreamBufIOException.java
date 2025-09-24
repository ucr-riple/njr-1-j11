package org.dclayer.exception.net.buf;

/**
 * an Exception which is thrown when an IOException occurs
 */
public class StreamBufIOException extends BufException {
	
	/**
	 * creates a {@link StreamBufIOException}
	 * @param cause the {@link Throwable} that caused the {@link StreamBufIOException}
	 */
	public StreamBufIOException(Throwable cause) {
		super(cause);
	}
	
}
