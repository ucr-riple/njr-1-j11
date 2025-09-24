package org.dclayer.net.a2s.message;

import org.dclayer.net.Data;
import org.dclayer.net.componentinterface.NetworkTypeComponentI;

public interface SlotAssignMessageI {

	public NetworkTypeComponentI getNetworkTypeComponent();
	
	public int getSlot();
	public void setSlot(int slot);
	
	public void setAddressData(Data addressData);
	public Data getAddressData();
	
}
