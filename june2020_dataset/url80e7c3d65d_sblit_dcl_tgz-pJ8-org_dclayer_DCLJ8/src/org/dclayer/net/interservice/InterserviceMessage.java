package org.dclayer.net.interservice;

import org.dclayer.net.PacketComponent;

public abstract class InterserviceMessage extends PacketComponent {
	
	public abstract int getTypeId();
	public abstract void callOnReceiveMethod(InterserviceChannel interserviceChannel);
	
}
