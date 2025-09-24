package org.dclayer.application;

import org.dclayer.application.applicationchannel.ApplicationChannelActionListener;
import org.dclayer.application.networktypeslotmap.NetworkEndpointSlot;
import org.dclayer.crypto.key.Key;
import org.dclayer.net.Data;
import org.dclayer.net.lla.LLA;

public interface NetworkEndpointActionListener {

	public void onJoin(NetworkEndpointSlot networkEndpointSlot, Data ownAddressData);
	public void onReceive(NetworkEndpointSlot networkEndpointSlot, Data data, Data sourceAddressData);
	public ApplicationChannelActionListener onApplicationChannelRequest(NetworkEndpointSlot networkEndpointSlot, Key remotePublicKey, String actionIdentifier, LLA remoteLLA);
	
}
