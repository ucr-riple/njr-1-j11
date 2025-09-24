package org.dclayer.net.interservice.applicationchannelslot;

import org.dclayer.datastructure.map.slotmap.Slot;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;


public class ApplicationChannelSlot extends Slot<ApplicationChannelTarget> {
	
	private ApplicationChannel applicationChannel;
	private ApplicationChannelSlot remoteEquivalent;
	
	public ApplicationChannelSlot(int slotId, ApplicationChannel applicationChannel) {
		super(slotId);
		this.applicationChannel = applicationChannel;
	}
	
	public ApplicationChannel getApplicationChannel() {
		return applicationChannel;
	}
	
	public void setRemoteEquivalent(ApplicationChannelSlot remoteEquivalent) {
		this.remoteEquivalent = remoteEquivalent;
	}
	
	public ApplicationChannelSlot getRemoteEquivalent() {
		return remoteEquivalent;
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
