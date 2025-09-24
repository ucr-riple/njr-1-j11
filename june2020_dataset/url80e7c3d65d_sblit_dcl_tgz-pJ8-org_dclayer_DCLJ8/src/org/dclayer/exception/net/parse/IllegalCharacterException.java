package org.dclayer.exception.net.parse;

public class IllegalCharacterException extends ParseException {
	
	/**
	 * creates a new {@link IllegalCharacterException}
	 * @param slot the invalid slot
	 */
	public IllegalCharacterException(char c) {
		super(String.format("Illegal character: '%c' (%d)", c, (int)c));
	}
	
}
