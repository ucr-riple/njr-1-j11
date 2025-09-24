package pl.cc.core.cmd;

import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

public class EventAgentNewCall extends Command {
		RealQueue queue;
		RealAgent agent;
		RealCall call;
		//EVENT [Agent], Action [NewCall], Agent [304], Exten [2001], Queue [bluzeczka], CallerID [17031574], Uniqueid[123]
		//EVENT [Agent], Action [NewCall], Agent [304], Exten [2001], Queue [bluzeczka], CallerID [17031574], Uniqueid[123], CreationTime [2009-07-03 12:56:11]
		private EventAgentNewCall(String orginalLine, RealQueue queue, RealAgent agent,RealCall call) {
			super(orginalLine);
			this.queue = queue;
			this.agent = agent;
			this.call = call;
		}

		public static Command factoryInt(String s){
			String [] names = {"event","action","agent","callerid","uniqueid","queue","creationtime"};
			boolean [] required = {true,true,true,      true,true,true,					  false};
			String [] v = getValues(s, names, required); 
			if (v==null) return null;
			if (!v[0].toLowerCase().equals("agent")) return null;
			if (!v[1].toLowerCase().equals("newcall")) return null;
			
			RealCall call = new RealCall(v[3],v[4]); 
			if (v[6]!=null){
				call.setCreationTimeAsString(v[6]);
			}
			
			return new EventAgentNewCall(
					s,
					new RealQueue(v[5],0),
					new RealAgent(v[2],null,null),
					call
					);
		}

		
		@Override
		public int getType() {
			return CMD_EVENT_AGENT_NEW_CALL;
		}

		public RealQueue getQueue() {
			return queue;
		}

		public RealAgent getAgent() {
			return agent;
		}

		public RealCall getCall() {
			return call;
		}


	}