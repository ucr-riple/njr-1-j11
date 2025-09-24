package pl.cc.core.cmd;

import pl.cc.real.RealAgent;
import pl.cc.utils.Utils;

import java.util.Date;

public class EventAgentCallTimeBad extends Command {
		RealAgent agent;
		int time;

		//EVENT [Agent], Action [Unause], Agent [313], Exten [877], Name [Litewka Dorota]
		private EventAgentCallTimeBad(String orginalLine, RealAgent agent, int time) {
			super(orginalLine);
			this.agent = agent;
            this.time=time;
		}

		public static Command factoryInt(String s){
			String [] names = {"event","action","agent","name","time"};
			String [] v = getValues(s, names, 5);
			if (v==null) return null;
			if (!v[0].toLowerCase().equals("agent")) return null;
			if (!v[1].toLowerCase().equals("calltimebad")) return null;
			
			return new EventAgentCallTimeBad(
					s,
					new RealAgent(v[2],v[3],null,false),
                    new Integer(v[4]).intValue()
					);
		}

		public int getTime() {
			return time;
		}
		
		@Override
		public int getType() {
			return CMD_EVENT_AGENT_CALL_TIME_BAD;
		}

		public RealAgent getAgent() {
			return agent;
		}


	}