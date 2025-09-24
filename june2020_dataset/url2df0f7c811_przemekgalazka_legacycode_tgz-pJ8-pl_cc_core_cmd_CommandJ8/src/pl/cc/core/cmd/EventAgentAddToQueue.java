package pl.cc.core.cmd;

import pl.cc.real.RealAgent;
import pl.cc.real.RealQueue;

public class EventAgentAddToQueue extends Command {
	RealQueue queue;
	RealAgent agent;
	
	//EVENT [Agent], Action [AddToQueue], Agent [330], Queue [allegro], Exten [867], Name [Sieczka Magdalena], Penalty [0], ID [200810191212.330]
	//EVENT [Agent], Action [AddToQueue], Agent [313], Queue [nogawka], Exten [978], Name [Litewka Dorota]
	private EventAgentAddToQueue(String orginalLine, RealQueue queue, RealAgent agent) {
		super(orginalLine);
		this.queue = queue;
		this.agent = agent;
	}

	public static Command factoryInt(String s){
		String [] names = {"event","action","agent","queue","exten","name"};
		String [] v = getValues(s, names); 
		if (v==null) return null;
		if (!v[0].toLowerCase().equals("agent")) return null;
		if (!v[1].toLowerCase().equals("addtoqueue")) return null;
		
		return new EventAgentAddToQueue(
				s,
				new RealQueue(v[3],0),
				new RealAgent(v[2],v[5],v[4])
				);
	}

	
	@Override
	public int getType() {
		return CMD_EVENT_AGENT_ADD_TO_QUEUE;
	}

	public RealQueue getQueue() {
		return queue;
	}

	public RealAgent getAgent() {
		return agent;
	}


}