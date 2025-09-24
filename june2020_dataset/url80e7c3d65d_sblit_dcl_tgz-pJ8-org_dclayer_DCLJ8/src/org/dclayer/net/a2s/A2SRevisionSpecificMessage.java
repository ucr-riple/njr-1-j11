package org.dclayer.net.a2s;


public abstract class A2SRevisionSpecificMessage extends A2SPacketComponent {

	public abstract byte getType();
	public abstract int getMessageRevision();
	
}
