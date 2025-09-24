package org.dclayer.net.a2s.applicationchannelslot;

import org.dclayer.datastructure.map.slotmap.SlotMap;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;

public class ApplicationChannelSlotMap extends SlotMap<ApplicationChannel, ApplicationChannelTarget, ApplicationChannelSlot> {

	@Override
	public ApplicationChannelSlot makeSlot(int slotId, ApplicationChannel applicationChannel) {
		return new ApplicationChannelSlot(slotId, applicationChannel);
	}
	
}
