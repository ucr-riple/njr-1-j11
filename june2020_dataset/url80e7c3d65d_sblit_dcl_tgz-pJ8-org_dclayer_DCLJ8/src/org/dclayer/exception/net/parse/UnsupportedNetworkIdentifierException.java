package org.dclayer.exception.net.parse;

/**
 * an Exception which is thrown when an unknown network identifier is received
 */
public class UnsupportedNetworkIdentifierException extends ParseException {
	
	private String networkIdentifier;
	
	/**
	 * creates a new {@link UnsupportedNetworkIdentifierException}
	 * @param identifier the invalid network identifier that was received
	 */
	public UnsupportedNetworkIdentifierException(String networkIdentifier) {
		super(String.format("Unsupported network identifier: %s", networkIdentifier));
		this.networkIdentifier = networkIdentifier;
	}
	
	public String getNetworkIdentifier() {
		return networkIdentifier;
	}
	
}
