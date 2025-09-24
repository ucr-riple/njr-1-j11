package pl.cc.events.real;

import pl.cc.real.RealAgent;

public interface RealAgentListListener {
	
	public void onAgentAdd(RealAgent realAgent);
	public void onAgentRemoved(RealAgent realAgent);
	public void onClear();
	
}
