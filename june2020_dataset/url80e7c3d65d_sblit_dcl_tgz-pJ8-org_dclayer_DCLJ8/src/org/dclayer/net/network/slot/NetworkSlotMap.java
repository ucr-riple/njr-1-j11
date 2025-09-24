package org.dclayer.net.network.slot;

import org.dclayer.datastructure.map.slotmap.SlotMap;
import org.dclayer.net.network.NetworkType;

public class NetworkSlotMap extends SlotMap<NetworkType, NetworkType, NetworkSlot> {

	@Override
	public NetworkSlot makeSlot(int slotId, NetworkType networkType) {
		return new NetworkSlot(slotId, networkType);
	}
	
}
