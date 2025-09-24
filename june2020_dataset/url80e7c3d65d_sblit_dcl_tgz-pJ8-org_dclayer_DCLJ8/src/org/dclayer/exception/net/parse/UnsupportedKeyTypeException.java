package org.dclayer.exception.net.parse;

/**
 * an Exception which is thrown when a key with an unsupported type is received
 */
public class UnsupportedKeyTypeException extends ParseException {
	
	/**
	 * creates a new {@link UnsupportedKeyTypeException}
	 * @param type the invalid type that was received
	 */
	public UnsupportedKeyTypeException(int type) {
		super(String.format("Unsupported key type: %d", type));
	}
	
}
