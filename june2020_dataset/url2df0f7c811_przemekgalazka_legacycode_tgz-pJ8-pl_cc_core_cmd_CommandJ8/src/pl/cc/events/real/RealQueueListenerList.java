package pl.cc.events.real;

import java.util.Vector;

import pl.cc.real.RealCall;



public class RealQueueListenerList extends Vector<RealQueueListener> implements RealQueueListener {

	public void onWaitCountChange(int load) {
		for (RealQueueListener listener : this){
			listener.onWaitCountChange(load);
		}
	}

	public void onQueueLeave(RealCall call) {
		for (RealQueueListener listener : this){
			listener.onQueueLeave(call);
		}	
	}

	public void onFreeAgentCountChange(int freeCount) {
		for (RealQueueListener listener : this){
			listener.onFreeAgentCountChange(freeCount);
		}	
	}

	public void onTalkingAgentCountChange(int talkingCount) {
		for (RealQueueListener listener : this){
			listener.onTalkingAgentCountChange(talkingCount);
		}	
	}

}
