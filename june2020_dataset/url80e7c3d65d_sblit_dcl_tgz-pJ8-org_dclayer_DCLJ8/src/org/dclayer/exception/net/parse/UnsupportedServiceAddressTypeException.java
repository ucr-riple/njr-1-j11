package org.dclayer.exception.net.parse;

import org.dclayer.net.serviceaddress.ServiceAddress;

/**
 * an Exception which is thrown when an unsupported {@link ServiceAddress} type is parsed
 */
public class UnsupportedServiceAddressTypeException extends ParseException {
	
	/**
	 * creates a new {@link UnsupportedServiceAddressTypeException}
	 * @param type the unsupported {@link ServiceAddress} type that was parsed
	 */
	public UnsupportedServiceAddressTypeException(int type) {
		super(String.format("Unsupported service address type: %d", type));
	}
	
}
