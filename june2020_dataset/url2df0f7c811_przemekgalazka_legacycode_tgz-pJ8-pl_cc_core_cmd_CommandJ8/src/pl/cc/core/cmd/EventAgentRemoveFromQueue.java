package pl.cc.core.cmd;

import pl.cc.real.RealAgent;
import pl.cc.real.RealQueue;

public class EventAgentRemoveFromQueue extends Command {
		RealQueue queue;
		RealAgent agent;
		
		//EVENT [Agent], Action [AddToQueue], Agent [313], Queue [nogawka], Exten [978], Name [Litewka Dorota]
		// EVENT [Agent], Action [RemoveFromQueue], Agent [313], Queue [nogawka], Exten [978], Name [Litewka Dorota]
		private EventAgentRemoveFromQueue(String orginalLine, RealQueue queue, RealAgent agent) {
			super(orginalLine);
			this.queue = queue;
			this.agent = agent;
		}

		public static Command factoryInt(String s){
			String [] names = {"event","action","agent","queue","exten","name"};
			String [] v = getValues(s, names); 
			if (v==null) return null;
			if (!v[0].toLowerCase().equals("agent")) return null;
			if (!v[1].toLowerCase().equals("removefromqueue")) return null;
			
			return new EventAgentRemoveFromQueue(
					s,
					new RealQueue(v[3],0),
					new RealAgent(v[2],v[5],v[4])
					);
		}

		
		@Override
		public int getType() {
			return CMD_EVENT_AGENT_REMOVE_FROM_QUEUE;
		}

		public RealQueue getQueue() {
			return queue;
		}

		public RealAgent getAgent() {
			return agent;
		}


	}