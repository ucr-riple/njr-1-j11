package org.dclayer.exception.net.parse;

import org.dclayer.net.network.NetworkType;

public class MalformedAttributeStringException extends ParseException {
	
	public MalformedAttributeStringException(NetworkType networkType, String attributeString) {
		super(String.format("Malformed attribute string for network type '%s': %s", networkType.getIdentifier(), attributeString));
	}
	
	public MalformedAttributeStringException(NetworkType networkType, String attributeString, Throwable t) {
		super(String.format("Malformed attribute string for network type '%s': %s", networkType.getIdentifier(), attributeString), t);
	}
	
}
