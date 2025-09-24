package org.dclayer.net.network.routing;

import org.dclayer.net.Data;
import org.dclayer.net.address.Address;
import org.dclayer.net.network.component.NetworkPacket;

public interface ForwardDestination<T> {
	
	public abstract boolean onForward(NetworkPacket networkPacket);
	public abstract Address getAddress();
	public abstract Data getScaledAddress();
	
	public abstract T getIdentifierObject();
	
}
