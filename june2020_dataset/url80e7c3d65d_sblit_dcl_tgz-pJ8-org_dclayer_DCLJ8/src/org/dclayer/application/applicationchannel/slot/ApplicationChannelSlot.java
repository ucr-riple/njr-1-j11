package org.dclayer.application.applicationchannel.slot;

import org.dclayer.application.applicationchannel.AbsApplicationChannel;
import org.dclayer.datastructure.map.slotmap.Slot;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;


public class ApplicationChannelSlot extends Slot<ApplicationChannelTarget> {
	
	private AbsApplicationChannel applicationChannel;
	
	public ApplicationChannelSlot(int slotId, AbsApplicationChannel applicationChannel) {
		super(slotId);
		this.applicationChannel = applicationChannel;
	}
	
	public AbsApplicationChannel getApplicationChannel() {
		return applicationChannel;
	}

	@Override
	public ApplicationChannelTarget getSearchObject() {
		return applicationChannel.getApplicationChannelTarget();
	}
	
	@Override
	public String contentToString() {
		return applicationChannel.toString();
	}
	
}
