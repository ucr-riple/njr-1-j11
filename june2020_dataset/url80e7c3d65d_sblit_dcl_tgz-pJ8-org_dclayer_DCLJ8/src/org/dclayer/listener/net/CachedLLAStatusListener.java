package org.dclayer.listener.net;

import org.dclayer.net.lla.CachedLLA;

public interface CachedLLAStatusListener {
	public void onStatusChanged(CachedLLA cachedLLA, int oldStatus, int newStatus);
}
