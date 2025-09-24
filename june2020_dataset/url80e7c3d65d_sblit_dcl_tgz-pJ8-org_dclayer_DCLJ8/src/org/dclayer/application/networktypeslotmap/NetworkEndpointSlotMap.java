package org.dclayer.application.networktypeslotmap;

import org.dclayer.application.NetworkEndpoint;
import org.dclayer.datastructure.map.slotmap.SlotMap;
import org.dclayer.net.network.NetworkType;

public class NetworkEndpointSlotMap extends SlotMap<NetworkEndpoint, NetworkType, NetworkEndpointSlot> {

	@Override
	public NetworkEndpointSlot makeSlot(int slotId, NetworkEndpoint networkEndpoint) {
		return new NetworkEndpointSlot(slotId, networkEndpoint);
	}
	
}
