package pl.cc.core.cmd;

import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;

public class EventAgentHangup extends Command {
		RealAgent agent;
		RealCall call;

		//EVENT [Agent], Action [Hangup], Agent [313], Exten [877], Uniqueid [1197554026.1571]
		//EVENT [Agent], Action [Hangup], Agent [313], Exten [877], Uniqueid [1197554563.1607]
		private EventAgentHangup(String orginalLine, RealAgent agent,RealCall call) {
			super(orginalLine);
			this.agent = agent;
			this.call = call;
		}

		public static Command factoryInt(String s){
			String [] names = {"event","action","agent","uniqueid"};
			String [] v = getValues(s, names); 
			if (v==null) return null;
			if (!v[0].toLowerCase().equals("agent")) return null;
			if (!v[1].toLowerCase().equals("hangup")) return null;
			
			return new EventAgentHangup(
					s,
					new RealAgent(v[2],null,null),
					new RealCall(null,v[3])
					);
		}

		
		@Override
		public int getType() {
			return CMD_EVENT_AGENT_HANGUP;
		}

		public RealAgent getAgent() {
			return agent;
		}

		public RealCall getCall() {
			return call;
		}


		}