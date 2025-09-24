package org.dclayer.exception.net.parse;

import org.dclayer.net.Data;


public class InvalidHashParseException extends ParseException {
	
	public InvalidHashParseException(Data declaredHash, Data actualHash) {
		super(String.format("Invalid hash: declared: %s, actual: %s"));
	}
	
}
