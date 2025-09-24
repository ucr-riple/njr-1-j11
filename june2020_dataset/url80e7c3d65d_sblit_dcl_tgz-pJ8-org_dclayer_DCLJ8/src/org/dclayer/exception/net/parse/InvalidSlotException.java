package org.dclayer.exception.net.parse;

/**
 * an exception which is thrown when a network packet with an invalid slot is received
 */
public class InvalidSlotException extends ParseException {
	
	/**
	 * creates a new {@link InvalidSlotException}
	 * @param slot the invalid slot
	 */
	public InvalidSlotException(int slot) {
		super(String.format("Invalid slot: %d", slot));
	}
	
}
