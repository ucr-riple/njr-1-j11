package org.dclayer.net.a2s;

import org.dclayer.net.PacketComponent;

public abstract class A2SPacketComponent extends PacketComponent {
	
	public abstract void callOnReceiveMethod(A2SMessageReceiver a2sMessageReceiver);
	
}
