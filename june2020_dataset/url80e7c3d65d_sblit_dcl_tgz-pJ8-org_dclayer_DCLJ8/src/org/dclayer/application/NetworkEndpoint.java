package org.dclayer.application;

import org.dclayer.net.network.NetworkType;

public class NetworkEndpoint {
	
	private NetworkType networkType;
	private NetworkEndpointActionListener onReceiveListener;
	
	public NetworkEndpoint(NetworkType networkType, NetworkEndpointActionListener onReceiveListener) {
		this.networkType = networkType;
		this.onReceiveListener = onReceiveListener;
	}
	
	public NetworkType getNetworkType() {
		return networkType;
	}
	
	public NetworkEndpointActionListener getNetworkEndpointActionListener() {
		return onReceiveListener;
	}
	
}
