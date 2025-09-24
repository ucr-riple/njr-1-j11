package pl.cc.core.cmd;

import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

/**
 * Zdarzenie AgentComplete
 *
 * @since 2009-07-08
 */
public class EventAgentComplete extends Command {
			RealQueue queue;
			RealAgent agent;
			RealCall call;

			// EVENT [Agent], Action [AgentComplete], Agent [301], Exten [873], Queue [bluzeczka], Reason [caller], Uniqueid [1197554949.1659]
			private EventAgentComplete(String orginalLine, RealQueue queue, RealAgent agent,RealCall call) {
				super(orginalLine);
				this.queue = queue;
				this.agent = agent;
				this.call = call;
			}

			public static Command factoryInt(String s){
				String [] names = {"event","action","agent","uniqueid","queue"};
				String [] v = getValues(s, names); 
				if (v==null) return null;
				if (!v[0].toLowerCase().equals("agent")) return null;
				if (!v[1].toLowerCase().equals("agentcomplete")) return null;
				
				RealCall call = new RealCall(null, v[3]); 

				return new EventAgentComplete(
						s,
						new RealQueue(v[4],0),
						new RealAgent(v[2],null,null),
						call
						);
			}

			
			@Override
			public int getType() {
				return CMD_EVENT_AGENT_COMPLETED;
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