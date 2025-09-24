package pl.cc.events.real;

import java.util.Vector;

import pl.cc.real.RealAgent;


public class RealAgentListListenerList extends Vector<RealAgentListListener> implements RealAgentListListener {

	public void onAgentAdd(RealAgent realAgent) {
		for(RealAgentListListener listener : this){
			listener.onAgentAdd(realAgent);
		}
	}

	public void onAgentRemoved(RealAgent realAgent) {
		for(RealAgentListListener listener : this){
			listener.onAgentRemoved(realAgent);
		}
	}

	public void onClear() {
		for(RealAgentListListener listener : this){
			listener.onClear();
		}
	}

	

}
