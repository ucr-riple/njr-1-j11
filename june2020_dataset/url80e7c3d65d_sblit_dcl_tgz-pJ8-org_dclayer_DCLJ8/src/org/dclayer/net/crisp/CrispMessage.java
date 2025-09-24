package org.dclayer.net.crisp;

import org.dclayer.net.PacketComponentI;


public interface CrispMessage extends PacketComponentI {

	public int getType();
	public <T> void callOnReceiveMethod(CrispMessageReceiver<T> crispMessageReceiver, T o);
	
}
