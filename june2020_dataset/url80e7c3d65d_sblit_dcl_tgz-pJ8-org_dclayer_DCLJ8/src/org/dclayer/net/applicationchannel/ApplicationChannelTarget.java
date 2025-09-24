package org.dclayer.net.applicationchannel;

import org.dclayer.net.address.Address;

public class ApplicationChannelTarget {

	private Address remoteAddress;
	private String actionIdentifier;
	
	public ApplicationChannelTarget(Address remoteAddress, String actionIdentifier) {
		this.remoteAddress = remoteAddress;
		this.actionIdentifier = actionIdentifier;
	}
	
	public Address getRemoteAddress() {
		return remoteAddress;
	}
	
	public String getActionIdentifier() {
		return actionIdentifier;
	}
	
	@Override
	public int hashCode() {
		return remoteAddress.hashCode() + actionIdentifier.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof ApplicationChannelTarget)) return false;
		ApplicationChannelTarget applicationChannelTarget = (ApplicationChannelTarget) o;
		return remoteAddress.equals(applicationChannelTarget.remoteAddress) && actionIdentifier.equals(applicationChannelTarget.actionIdentifier);
	}
	
}
