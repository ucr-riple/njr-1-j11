package org.dclayer.net.network.routing;

import org.dclayer.net.Data;
import org.dclayer.net.address.Address;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.NetworkType;

public abstract class RoutingTable {
	
	public abstract boolean add(NetworkNode networkNode);
	public abstract boolean remove(NetworkNode networkNode);
	public abstract Nexthops lookup(Data destinationAddressData, Address originAddress, Object originIdentifierObject, int offset);
	
	public abstract NetworkType getNetworkType();
	
	public abstract void connect(RoutingTable routingTable);
	public abstract void disconnect();
	
	@Override
	public abstract String toString();
	
}
