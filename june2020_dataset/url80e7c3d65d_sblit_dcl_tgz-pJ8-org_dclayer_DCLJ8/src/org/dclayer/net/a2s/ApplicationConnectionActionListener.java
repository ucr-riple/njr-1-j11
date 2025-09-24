package org.dclayer.net.a2s;

import java.net.Socket;

import org.dclayer.listener.net.NetworkInstanceListener;
import org.dclayer.net.Data;
import org.dclayer.net.address.Address;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.interservice.InterservicePolicy;
import org.dclayer.net.lla.LLA;
import org.dclayer.net.network.ApplicationNetworkInstance;
import org.dclayer.net.network.NetworkInstance;
import org.dclayer.net.network.component.NetworkPayload;

public interface ApplicationConnectionActionListener extends NetworkInstanceListener {
	public ApplicationConnection onApplicationConnection(Socket socket);
	public void onAddress(Address asymmetricKeyPairAddress);
	public void onServiceNetworkPayload(NetworkPayload networkPayload, NetworkInstance networkInstance);
	
	public LLA getLocalLLA(boolean wait);
	
	public Data getServiceIgnoreData();
	
	public void connect(LLA lla, InterservicePolicy interservicePolicy);
	public void prepareForIncomingApplicationChannel(LLA lla, ApplicationNetworkInstance applicationNetworkInstance, ApplicationChannel applicationChannel, Data ignoreData);
	
	public void initiateApplicationChannel(LLA lla, ApplicationNetworkInstance applicationNetworkInstance, ApplicationChannel applicationChannel);
}
