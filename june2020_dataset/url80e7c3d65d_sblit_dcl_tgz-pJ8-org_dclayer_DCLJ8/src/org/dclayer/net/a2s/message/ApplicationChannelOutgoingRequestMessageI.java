package org.dclayer.net.a2s.message;

import org.dclayer.net.componentinterface.KeyComponentI;


public interface ApplicationChannelOutgoingRequestMessageI {
	
	public int getNetworkSlot();
	public void setNetworkSlot(int networkSlot);
	
	public int getChannelSlot();
	public void setChannelSlot(int channelSlot);
	
	public String getActionIdentifierSuffix();
	public void setActionIdentifierSuffix(String actionIdentifierSuffix);
	
	public KeyComponentI getKeyComponent();
	
}
