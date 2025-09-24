package pl.cc.events.real;

import java.util.Vector;

import org.apache.log4j.Logger;
import pl.cc.core.AgentStatusInQueue;
import pl.cc.core.PauseType;
import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

public class RealAgentListenerList extends Vector<RealAgentListener> implements RealAgentListener {
	static Logger log = Logger.getLogger(RealAgentListenerList.class);
	
	public boolean add(RealAgentListener listener){
		if (contains(listener)) {
			log.warn("Already contains: "+listener);
			return false;
		}
		return super.add(listener);
	}
	
	public void onPause(RealAgent agent, boolean paused, PauseType pauseType, int pauseTime, int timePoor, int timeBad) {
		for (RealAgentListener listener : this){
			listener.onPause(agent, paused, pauseType, pauseTime, timePoor, timeBad);
		}
	}

	public void onAgentNewCall(RealCall call, RealQueue queue) {
		for (RealAgentListener listener : this){
			listener.onAgentNewCall(call, queue);
		}
	}

	public void onHangeup(RealAgent realAgent, RealCall call) {
		for (RealAgentListener listener : this){
			listener.onHangeup(realAgent, call);
		}
	}

	public void onAgentConnect(RealAgent realAgent, RealCall call, RealQueue queue) {
		for (RealAgentListener listener : this){
			listener.onAgentConnect(realAgent, call, queue);
		}
	}

	public void onCallLeave(RealAgent realAgent,RealCall callLeave, RealQueue queue) {
		for (RealAgentListener listener : this){
			listener.onCallLeave(realAgent, callLeave, queue);
		}
	}

	public void onChangeStatusAsQueueMember(RealAgent realAgent, RealQueue queue, AgentStatusInQueue status) {
		for (RealAgentListener listener : this){
			listener.onChangeStatusAsQueueMember(realAgent, queue, status);
		}
	}

}
