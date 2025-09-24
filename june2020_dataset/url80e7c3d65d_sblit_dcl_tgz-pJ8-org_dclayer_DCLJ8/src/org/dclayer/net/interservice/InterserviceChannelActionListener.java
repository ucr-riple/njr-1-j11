package org.dclayer.net.interservice;

import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.lla.LLA;
import org.dclayer.net.network.NetworkInstance;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.slot.NetworkSlot;

public interface InterserviceChannelActionListener {
	
	public void onReadyChange(InterserviceChannel interserviceChannel, boolean ready);
	public void onNewRemoteNetworkNode(InterserviceChannel interserviceChannel, NetworkNode networkNode, NetworkSlot localNetworkSlot);
	public void onRemoveRemoteNetworkNode(InterserviceChannel interserviceChannel, NetworkNode networkNode);
	public void onInterserviceChannelClosed(InterserviceChannel interserviceChannel);
	
	public void onLocalLLAReport(InterserviceChannel interserviceChannel, LLA oldLocalLLA, LLA newLocalLLA);
	
	public InterservicePolicy addDefaultIncomingApplicationChannelInterservicePolicyRules(InterservicePolicy interservicePolicy, NetworkInstance networkInstance, ApplicationChannel applicationChannel, LLA remoteLLA);
	public InterservicePolicy addDefaultOutgoingApplicationChannelInterservicePolicyRules(InterservicePolicy interservicePolicy, ApplicationChannel applicationChannel);
	
}
