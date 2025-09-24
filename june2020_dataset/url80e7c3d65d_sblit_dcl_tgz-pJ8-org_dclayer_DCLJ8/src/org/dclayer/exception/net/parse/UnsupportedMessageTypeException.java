package org.dclayer.exception.net.parse;

/**
 * an Exception which is thrown when a message with an unsupported message type is received
 */
public class UnsupportedMessageTypeException extends ParseException {
	
	/**
	 * creates a new {@link UnsupportedMessageTypeException}
	 * @param type the invalid message type that was received
	 */
	public UnsupportedMessageTypeException(int type) {
		super(String.format("Unsupported message type: %d", type));
	}
	
}
