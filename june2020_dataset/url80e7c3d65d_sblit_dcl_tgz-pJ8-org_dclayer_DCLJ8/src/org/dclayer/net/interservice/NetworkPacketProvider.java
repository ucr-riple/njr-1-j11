package org.dclayer.net.interservice;

import org.dclayer.net.network.component.NetworkPacket;


public interface NetworkPacketProvider {

	public NetworkPacket getNetworkPacket(int slot);
	
}
