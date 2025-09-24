package pl.cc.events.real;

import pl.cc.real.RealQueue;

public interface RealQueueListListener {
	
	public void onQueueAdd(RealQueue realQueue);
	public void onQueueRemoved(RealQueue realQueue);
	public void onClear();
	
}
