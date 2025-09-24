package pl.cc.core.cmd;

import pl.cc.real.RealAgent;

public class EventAgentLogin extends Command {

	RealAgent agent;
	
	//EVENT [Agent], Action [Login], Agent [302], Exten [702], Name [Mijalska Katarzyna]
	private EventAgentLogin(String orginalLine, RealAgent agent) {
		super(orginalLine);
		this.agent = agent;
	}

	public static Command factoryInt(String s){
		String [] names = {"event","action","agent","exten","name"};
		String [] v = getValues(s, names); 
		if (v==null) return null;
		if (!v[0].toLowerCase().equals("agent")) return null;
		if (!v[1].toLowerCase().equals("login")) return null;
		
		return new EventAgentLogin(
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