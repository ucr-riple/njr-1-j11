package org.dclayer.net.packetcomponent;

import org.dclayer.net.PacketComponent;

public abstract class ChildPacketComponent extends PacketComponent {
	
	private int indexInParent = -1;
	
	public void setIndexInParent(int indexInParent) {
		this.indexInParent = indexInParent;
	}
	
	public int getIndexInParent() {
		return indexInParent;
	}
	
}
