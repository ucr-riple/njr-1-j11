package org.dclayer.net.network.slot;

import org.dclayer.datastructure.map.slotmap.SlotMap;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.net.address.Address;

public class AddressSlotMap extends SlotMap<Address, Address, AddressSlot> {

	private HierarchicalLevel parentHierarchicalLevel;
	private boolean remote = false;
	
	public AddressSlotMap(HierarchicalLevel parentHierarchicalLevel, boolean remote) {
		this.parentHierarchicalLevel = parentHierarchicalLevel;
		this.remote = remote;
	}
	
	@Override
	public AddressSlot makeSlot(int slotId, Address address) {
		return new AddressSlot(parentHierarchicalLevel, remote, slotId, address);
	}
	
}
