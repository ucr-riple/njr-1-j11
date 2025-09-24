package org.dclayer.application.applicationchannel.slot;

import org.dclayer.application.applicationchannel.AbsApplicationChannel;
import org.dclayer.datastructure.map.slotmap.SlotMap;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;

public class ApplicationChannelSlotMap extends SlotMap<AbsApplicationChannel, ApplicationChannelTarget, ApplicationChannelSlot> {

	@Override
	public ApplicationChannelSlot makeSlot(int slotId, AbsApplicationChannel applicationChannel) {
		ApplicationChannelSlot applicationChannelSlot = new ApplicationChannelSlot(slotId, applicationChannel);
		applicationChannel.setApplicationChannelSlot(applicationChannelSlot);
		return applicationChannelSlot;
	}
	
}
