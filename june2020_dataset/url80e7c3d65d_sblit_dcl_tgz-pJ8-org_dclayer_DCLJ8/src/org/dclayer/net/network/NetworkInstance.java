package org.dclayer.net.network;

import org.dclayer.crypto.key.Key;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.address.Address;
import org.dclayer.net.lla.LLA;
import org.dclayer.net.network.component.NetworkPacket;
import org.dclayer.net.network.routing.Nexthops;
import org.dclayer.net.network.routing.RoutingTable;

public abstract class NetworkInstance extends NetworkNode<Object> implements HierarchicalLevel {
	
	private HierarchicalLevel parentHierarchicalLevel;
	private RoutingTable routingTable;
	
	public NetworkInstance(HierarchicalLevel parentHierarchicalLevel, NetworkType networkType, Address address, boolean endpoint) {
		super(networkType, address, null, endpoint);
		this.parentHierarchicalLevel = parentHierarchicalLevel;
		this.routingTable = networkType.makeRoutingTable(this);
	}
	
	@Override
	public RoutingTable getRoutingTable() {
		return routingTable;
	}
	
	@Override
	public boolean forward(NetworkPacket networkPacket) {
		
		Data destinationAddressData = networkPacket.getDestinationAddressData();
		Nexthops nexthops = routingTable.lookup(destinationAddressData, null, networkPacket.getNetworkSlot().getRemoteEquivalent(), 0);
		
		if(nexthops == null) {
			
			Log.warning(this, "could not forward network packet, no next hops found for address %s", destinationAddressData);
			return false;
			
		} else {
			
			return nexthops.forward(networkPacket);
			
		}
		
	}
	
	@Override
	public String toString() {
		return String.format("network instance for %s", super.toString());
	}
	
	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return parentHierarchicalLevel;
	}
	
	public abstract void neighborRequest(Key senderPublicKey, String actionIdentifier, LLA senderLLA, boolean response, Data ignoreData);
	
}
