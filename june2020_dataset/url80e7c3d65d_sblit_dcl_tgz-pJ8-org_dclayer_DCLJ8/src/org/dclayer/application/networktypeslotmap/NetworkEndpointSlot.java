package org.dclayer.application.networktypeslotmap;

import org.dclayer.application.NetworkEndpoint;
import org.dclayer.datastructure.map.slotmap.Slot;
import org.dclayer.net.network.NetworkType;


public class NetworkEndpointSlot extends Slot<NetworkType> {
	
	private NetworkEndpoint networkEndpoint;
	
	public NetworkEndpointSlot(int slotId, NetworkEndpoint networkEndpoint) {
		super(slotId);
		this.networkEndpoint = networkEndpoint;
	}
	
	public NetworkType getNetworkType() {
		return networkEndpoint.getNetworkType();
	}
	
	public NetworkEndpoint getNetworkEndpoint() {
		return networkEndpoint;
	}

	@Override
	public NetworkType getSearchObject() {
		return networkEndpoint.getNetworkType();
	}
	
	@Override
	public String contentToString() {
		return getNetworkType().toString();
	}
	
}
