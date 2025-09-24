package org.dclayer.net.network;

import org.dclayer.net.address.Address;
import org.dclayer.net.network.slot.NetworkSlot;

public abstract class RemoteNetworkNode extends NetworkNode<NetworkSlot> {
	
	public RemoteNetworkNode(NetworkType networkType, Address address, NetworkSlot networkSlot) {
		super(networkType, address, networkSlot, false);
	}
	
}
