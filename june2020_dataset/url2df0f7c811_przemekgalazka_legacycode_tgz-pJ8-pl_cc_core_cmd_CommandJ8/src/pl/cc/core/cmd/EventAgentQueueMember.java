package pl.cc.core.cmd;

import pl.cc.core.AgentStatusInQueue;
import pl.cc.real.RealAgent;
import pl.cc.real.RealQueue;

public class EventAgentQueueMember extends Command {
	RealQueue queue;
	RealAgent agent;
	AgentStatusInQueue status;
	
	// UWAGA - marek jako agent wysyďż˝a dla zmyďż˝ki extension a nie agentId
	//EVENT [Agent], Action [QueueMember], Queue [znikam], Agent [850], Paused [0], Penalty [0], Status [RINGING] 
	private EventAgentQueueMember(String orginalLine, RealQueue queue, RealAgent agent, String status) {
		super(orginalLine);
		this.queue = queue;
		this.agent = agent;
		this.status = AgentStatusInQueue.find(status);
	}

	public static Command factoryInt(String s){
		String [] names = {"event","action","agent","queue","status"};
		String [] v = getValues(s, names); 
		if (v==null) return null;
		if (!v[0].toLowerCase().equals("Agent".toLowerCase())) return null;
		if (!v[1].toLowerCase().equals("QueueMember".toLowerCase())) return null;
		
		return new EventAgentQueueMember(
				s,
				new RealQueue(v[3],0),
				new RealAgent(new Integer(v[2])),
				v[4]
				);
	}

	
	@Override
	public int getType() {
		return CMD_EVENT_AGENT_QUEUE_MEMBER;
	}

	public RealQueue getQueue() {
		return queue;
	}

	public RealAgent getAgent() {
		return agent;
	}

	public AgentStatusInQueue getStatus() {
		return status;
	}


}