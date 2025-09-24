package org.dclayer.net.a2s.remotekey;

import org.dclayer.net.Data;

public interface RemoteRSAKeyCommunicationListener {
	
	public void onResponseDataMessage(Data responseData);
	public void onResponseNumMessage(int responseNum);

}
