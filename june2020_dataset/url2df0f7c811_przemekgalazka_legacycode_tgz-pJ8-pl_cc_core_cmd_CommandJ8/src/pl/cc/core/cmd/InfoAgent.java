package pl.cc.core.cmd;

import pl.cc.real.RealAgent;
import pl.cc.real.RealQueue;

public class InfoAgent extends Command {
	RealQueue queue;
	RealAgent agent;

	//+INFO Queue 'rekawek', Agent '313', Exten '877', Name: 'Litewka Dorota'
	private InfoAgent(String orginalLine, RealQueue queue, RealAgent agent){
		super(orginalLine);
		this.queue=queue;
		this.agent=agent;
	}
	
	public static Command factoryInt(String s){
		String [] names = {"queue","agent","exten","name"};
		String [] v = getValuesINFO(s, names); 
		if (v==null) return null;
		return new InfoAgent(
				s,
				new RealQueue(v[0],"0"),
				new RealAgent(v[1],v[3],v[2])
		);
	}

	@Override
	public int getType() {
		return CMD_INFO_AGENT;
	}

	public RealQueue getQueue() {
		return queue;
	}

	public RealAgent getAgent() {
		return agent;
	}
	
}
