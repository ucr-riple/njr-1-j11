package org.dclayer.net.applicationchannel;

import org.dclayer.net.Data;

public interface ApplicationSideApplicationChannelActionListener {

	public void onConnected(ApplicationChannel applicationChannel);
	public void onDisconnected(ApplicationChannel applicationChannel);
	public void onData(ApplicationChannel applicationChannel, Data data);
	
}
