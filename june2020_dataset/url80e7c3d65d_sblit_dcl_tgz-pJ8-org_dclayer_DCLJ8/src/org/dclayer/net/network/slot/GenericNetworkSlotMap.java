package org.dclayer.net.network.slot;

import org.dclayer.datastructure.map.slotmap.SlotMap;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.NetworkType;

public class GenericNetworkSlotMap<T extends NetworkNode> extends SlotMap<NetworkType, NetworkType, GenericNetworkSlot<T>> {

	@Override
	public GenericNetworkSlot<T> makeSlot(int slotId, NetworkType networkType) {
		return new GenericNetworkSlot<T>(slotId, networkType);
	}
	
}
