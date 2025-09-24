package org.dclayer.exception.net.parse;


public class UnknownHashAlgorithmException extends ParseException {
	
	public UnknownHashAlgorithmException(String identifier) {
		super(String.format("Unknown hash algorithm identifier: %s", identifier));
	}
	
}
