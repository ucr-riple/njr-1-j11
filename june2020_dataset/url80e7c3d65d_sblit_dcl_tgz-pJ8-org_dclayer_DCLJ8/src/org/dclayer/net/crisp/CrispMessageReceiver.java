package org.dclayer.net.crisp;

import org.dclayer.net.crisp.message.NeighborRequestCrispMessageI;

public interface CrispMessageReceiver<T> {

	public void onReceiveNeighborRequestCrispMessage(NeighborRequestCrispMessageI neighborRequestCrispMessage, T o);
	
}
