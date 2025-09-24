package pl.cc.events.real;

import pl.cc.core.AgentStatusInQueue;
import pl.cc.core.PauseType;
import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

public interface RealAgentListener {
	
	public void onPause(RealAgent agent, boolean paused, PauseType pauseType, int pauseTime, int timePoor, int timeBad);
	public void onChangeStatusAsQueueMember(RealAgent realAgent, RealQueue queue, AgentStatusInQueue status);
	
	public void onAgentNewCall(RealCall call, RealQueue queue);
	public void onAgentConnect(RealAgent realAgent, RealCall call, RealQueue queue);
	public void onCallLeave(RealAgent realAgent, RealCall callLeave, RealQueue queue);
	public void onHangeup(RealAgent realAgent, RealCall call);
	
	
	
}
