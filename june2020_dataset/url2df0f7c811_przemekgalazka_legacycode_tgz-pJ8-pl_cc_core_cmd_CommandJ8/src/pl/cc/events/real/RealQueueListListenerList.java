package pl.cc.events.real;

import java.util.Vector;

import pl.cc.real.RealQueue;


public class RealQueueListListenerList extends Vector<RealQueueListListener> implements RealQueueListListener {

	public void onQueueAdd(RealQueue realQueue) {
		for(RealQueueListListener listener : this){
			listener.onQueueAdd(realQueue);
		}
	}

	public void onQueueRemoved(RealQueue realQueue) {
		for(RealQueueListListener listener : this){
			listener.onQueueRemoved(realQueue);
		}
	}

	public void onClear() {
		for(RealQueueListListener listener : this){
			listener.onClear();
		}
	}

}
