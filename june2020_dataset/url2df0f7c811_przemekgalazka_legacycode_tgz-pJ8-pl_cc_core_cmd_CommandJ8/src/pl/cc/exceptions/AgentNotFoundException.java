package pl.cc.exceptions;

import pl.cc.real.RealAgent;
import pl.cc.real.RealQueue;

public class AgentNotFoundException extends Exception {
	RealAgent agent;
	RealQueue queue;

	public AgentNotFoundException(RealAgent agent){
		this.agent = agent;
	}
	
	public AgentNotFoundException(RealAgent agent, RealQueue queue){
		this.agent = agent;
		this.queue = queue;
	}

	
	public String getMessage(){
		if (queue==null){
			return new String("Nie znaleziono agenta: "+agent.toString());
		} else {
			return new String("Nie znaleziono agenta: "+agent.toString()+" w kolejce: "+queue.toString());
		}
	}
	
}
