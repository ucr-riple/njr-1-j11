package org.dclayer.exception.net.parse;
import org.dclayer.exception.net.parse.ParseException;

/**
 * an Exception which is thrown when a message with an unsupported BMCP command type is received
 */
public class UnsupportedBMCPCommandTypeException extends ParseException {
	
	/**
	 * creates a new {@link UnsupportedBMCPCommandTypeException}
	 * @param type the invalid BMCP command type that was received
	 */
	public UnsupportedBMCPCommandTypeException(int type) {
		super(String.format("Unsupported BMCP command type: %d", type));
	}
	
}
