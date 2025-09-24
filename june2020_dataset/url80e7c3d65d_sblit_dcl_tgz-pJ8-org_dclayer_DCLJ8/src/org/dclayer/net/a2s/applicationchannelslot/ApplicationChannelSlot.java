package org.dclayer.net.a2s.applicationchannelslot;

import org.dclayer.datastructure.map.slotmap.Slot;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;


public class ApplicationChannelSlot extends Slot<ApplicationChannelTarget> {
	
	private ApplicationChannel applicationChannel;
	
	public ApplicationChannelSlot(int slotId, ApplicationChannel applicationChannel) {
		super(slotId);
		this.applicationChannel = applicationChannel;
		applicationChannel.setApplicationConnectionApplicationChannelSlot(this);
	}
	
	public ApplicationChannel getApplicationChannel() {
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
