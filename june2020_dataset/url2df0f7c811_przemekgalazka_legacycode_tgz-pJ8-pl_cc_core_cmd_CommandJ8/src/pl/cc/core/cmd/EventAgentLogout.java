package pl.cc.core.cmd;

import pl.cc.real.RealAgent;

public class EventAgentLogout extends Command {

	RealAgent agent;
	
	//EVENT [Agent], Action [Logout], Agent [302], Exten [702], Name [Mijalska Katarzyna]
	private EventAgentLogout(String orginalLine, RealAgent agent) {
		super(orginalLine);
		this.agent = agent;
	}

	public static Command factoryInt(String s){
		String [] names = {"event","action","agent","exten","name"};
		String [] v = getValues(s, names); 
		if (v==null) return null;
		if (!v[0].toLowerCase().equals("agent")) return null;
		if (!v[1].toLowerCase().equals("logout")) return null;
		
		return new EventAgentLogout(
				s,
				new RealAgent(v[2],v[4],v[3])
				);
	}

	
	@Override
	public int getType() {
		return CMD_EVENT_AGENT_LOGIN;
	}

	public RealAgent getAgent() {
		return agent;
	}


}