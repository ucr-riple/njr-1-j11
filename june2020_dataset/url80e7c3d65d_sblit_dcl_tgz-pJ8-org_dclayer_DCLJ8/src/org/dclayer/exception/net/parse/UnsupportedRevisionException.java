package org.dclayer.exception.net.parse;

/**
 * an Exception which is thrown when a packet of an unsupported revision is received
 */
public class UnsupportedRevisionException extends ParseException {
	
	/**
	 * creates a new {@link UnsupportedRevisionException}
	 * @param revision the unsupported revision that was received
	 */
	public UnsupportedRevisionException(int revision) {
		super(String.format("Unsupported revision: %d", revision));
	}
	
}
