package org.dclayer.net.network.properties;

public class CommonNetworkPayloadProperties {
	
	public boolean destinedForService = false;
	public boolean sourceAddress = false;
	
	public CommonNetworkPayloadProperties destinedForService(boolean destinedForService) {
		this.destinedForService = destinedForService;
		return this;
	}
	
	public CommonNetworkPayloadProperties sourceAddress(boolean sourceAddress) {
		this.sourceAddress = sourceAddress;
		return this;
	}
	
}
