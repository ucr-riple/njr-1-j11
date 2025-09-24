package pl.cc.core.cmd;

import java.util.Date;

import pl.cc.real.RealAgent;
import pl.cc.utils.Utils;

public class EventAgentUnPause extends Command {
		RealAgent agent;
		Date since;

		//EVENT [Agent], Action [Unause], Agent [313], Exten [877], Name [Litewka Dorota]
		private EventAgentUnPause(String orginalLine, RealAgent agent, String since) {
			super(orginalLine);
			this.agent = agent;
			this.since = Utils.parseDate(since);
		}

		public static Command factoryInt(String s){
			String [] names = {"event","action","agent","since"};
			String [] v = getValues(s, names, 3); 
			if (v==null) return null;
			if (!v[0].toLowerCase().equals("agent")) return null;
			if (!v[1].toLowerCase().equals("unpause")) return null;
			
			return new EventAgentUnPause(
					s,
					new RealAgent(v[2],null,null,false),
					v[3]
					);
		}

		public Date getSince() {
			return since;
		}
		
		@Override
		public int getType() {
			return CMD_EVENT_AGENT_UNPAUSE;
		}

		public RealAgent getAgent() {
			return agent;
		}


	}