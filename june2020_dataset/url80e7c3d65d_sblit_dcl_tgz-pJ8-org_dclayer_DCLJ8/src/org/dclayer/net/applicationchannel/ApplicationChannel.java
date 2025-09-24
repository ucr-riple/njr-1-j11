package org.dclayer.net.applicationchannel;

import org.dclayer.net.a2s.ApplicationConnection;
import org.dclayer.net.address.Address;
import org.dclayer.net.interservice.InterserviceChannel;

public class ApplicationChannel {

	private ApplicationConnection localApplicationConnection;
	private InterserviceChannel remoteInterserviceChannel;
	
	private ApplicationChannelTarget applicationChannelTarget;
	
	private ApplicationSideApplicationChannelActionListener applicationSideApplicationChannelActionListener;
	private ServiceSideApplicationChannelActionListener serviceSideApplicationChannelActionListener;
	
	private org.dclayer.net.a2s.applicationchannelslot.ApplicationChannelSlot applicationConnectionApplicationChannelSlot;
	private org.dclayer.net.interservice.applicationchannelslot.ApplicationChannelSlot interserviceChannelApplicationChannelSlot;
	
	public ApplicationChannel(ApplicationChannelTarget applicationChannelTarget, ApplicationConnection localApplicationConnection, ApplicationSideApplicationChannelActionListener applicationSideApplicationChannelActionListener) {
		this.localApplicationConnection = localApplicationConnection;
		this.applicationChannelTarget = applicationChannelTarget;
		this.applicationSideApplicationChannelActionListener = applicationSideApplicationChannelActionListener;
	}
	
	public ApplicationConnection getLocalApplicationConnection() {
		return localApplicationConnection;
	}
	
	public void setApplicationConnectionApplicationChannelSlot(org.dclayer.net.a2s.applicationchannelslot.ApplicationChannelSlot applicationChannelSlot) {
		this.applicationConnectionApplicationChannelSlot = applicationChannelSlot;
	}
	
	public org.dclayer.net.a2s.applicationchannelslot.ApplicationChannelSlot getApplicationConnectionApplicationChannelSlot() {
		return applicationConnectionApplicationChannelSlot;
	}
	
	public void setInterserviceChannelApplicationChannelSlot(org.dclayer.net.interservice.applicationchannelslot.ApplicationChannelSlot interserviceChannelApplicationChannelSlot) {
		this.interserviceChannelApplicationChannelSlot = interserviceChannelApplicationChannelSlot;
	}
	
	public org.dclayer.net.interservice.applicationchannelslot.ApplicationChannelSlot getInterserviceChannelApplicationChannelSlot() {
		return interserviceChannelApplicationChannelSlot;
	}
	
	public void setRemoteInterserviceChannel(InterserviceChannel remoteInterserviceChannel) {
		this.remoteInterserviceChannel = remoteInterserviceChannel;
	}
	
	public InterserviceChannel getRemoteInterserviceChannel() {
		return remoteInterserviceChannel;
	}
	
	public ApplicationChannelTarget getApplicationChannelTarget() {
		return applicationChannelTarget;
	}

	public Address getLocalAddress() {
		return localApplicationConnection.getApplicationAddress();
	}

	public Address getRemoteAddress() {
		return applicationChannelTarget.getRemoteAddress();
	}

	public String getActionIdentifier() {
		return applicationChannelTarget.getActionIdentifier();
	}
	
	public ApplicationSideApplicationChannelActionListener getApplicationSideApplicationChannelActionListener() {
		return applicationSideApplicationChannelActionListener;
	}
	
	public void setServiceSideApplicationChannelActionListener(ServiceSideApplicationChannelActionListener serviceSideApplicationChannelActionListener) {
		this.serviceSideApplicationChannelActionListener = serviceSideApplicationChannelActionListener;
	}
	
	public ServiceSideApplicationChannelActionListener getServiceSideApplicationChannelActionListener() {
		return serviceSideApplicationChannelActionListener;
	}
	
}
