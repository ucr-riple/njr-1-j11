package org.dclayer.net.a2s.message;

import org.dclayer.net.componentinterface.AddressComponentI;
import org.dclayer.net.componentinterface.DataComponentI;
import org.dclayer.net.componentinterface.NumComponentI;

public interface DataMessageI {
	
	public NumComponentI getSlotNumComponent();
	public AddressComponentI getAddressComponent();
	public DataComponentI getDataComponent();
	
}
