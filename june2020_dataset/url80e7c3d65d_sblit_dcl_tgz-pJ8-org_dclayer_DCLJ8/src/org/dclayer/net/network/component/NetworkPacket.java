package org.dclayer.net.network.component;

import org.dclayer.net.Data;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.component.DataComponent;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.slot.GenericNetworkSlot;

/**
 * Base class for all routed Packets (e.g. APBRPacket)
 * @author Martin Exner
 */
public abstract class NetworkPacket extends PacketComponent {
	
	private final GenericNetworkSlot<NetworkNode> networkSlot;
	
	public NetworkPacket(GenericNetworkSlot<NetworkNode> networkSlot) {
		this.networkSlot = networkSlot;
	}
	
	public final GenericNetworkSlot<NetworkNode> getNetworkSlot() {
		return networkSlot;
	}
	
	public abstract Data getDestinationAddressData();
	public abstract void setDestinationAddressData(Data destinationAddressData);
	public abstract DataComponent getDataComponent();
	
}
