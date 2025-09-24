package org.dclayer.net.interservice.applicationchannelslot;

import org.dclayer.datastructure.map.slotmap.SlotMap;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;

public class ApplicationChannelSlotMap extends SlotMap<ApplicationChannel, ApplicationChannelTarget, ApplicationChannelSlot> {
	
	private boolean assignSlotToChannel;
	
	public ApplicationChannelSlotMap(boolean assignSlotToChannel) {
		this.assignSlotToChannel = assignSlotToChannel;
	}

	@Override
	public ApplicationChannelSlot makeSlot(int slotId, ApplicationChannel applicationChannel) {
		ApplicationChannelSlot applicationChannelSlot = new ApplicationChannelSlot(slotId, applicationChannel);
		if(assignSlotToChannel) applicationChannel.setInterserviceChannelApplicationChannelSlot(applicationChannelSlot);
		return applicationChannelSlot;
	}
	
}
