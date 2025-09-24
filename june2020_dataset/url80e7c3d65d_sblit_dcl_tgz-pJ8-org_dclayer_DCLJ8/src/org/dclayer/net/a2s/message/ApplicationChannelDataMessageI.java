package org.dclayer.net.a2s.message;

import org.dclayer.net.componentinterface.DataComponentI;

public interface ApplicationChannelDataMessageI {
	
	public void setChannelSlot(int channelSlot);
	public int getChannelSlot();
	
	public DataComponentI getDataComponent();

}
