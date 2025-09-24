package pl.cc.core.cmd;

import java.util.Date;

import pl.cc.core.PauseType;
import pl.cc.real.RealAgent;
import pl.cc.utils.Utils;

public class EventAgentPause extends Command {
		RealAgent agent;
		String pauseType;
		int pauseTime;
		Date since;


    int  timePoor;
        int timeBad;

    //EVENT [Agent], Action [Pause], Agent [313], Exten [877], Name [Litewka Dorota], Type [default]
		private EventAgentPause(String orginalLine, RealAgent agent, String pauseType, String since, int pauseTime, int timepoor, int timebad) {
			super(orginalLine);
			this.agent = agent;
			this.pauseType = pauseType;
			this.pauseTime = pauseTime;
			this.since = Utils.parseDate(since);
            this.timePoor = timepoor;
            this.timeBad = timebad;
		}

		public Date getSince() {
			return since;
		}

		public int getPauseTime() {
			return pauseTime;
		}

		public static Command factoryInt(String s){
			String [] names = {"event","action","agent", "type", "since", "time", "timepoor", "timebad"};
			String [] v = getValues(s, names,4); 
			if (v==null) return null;
			if (!v[0].toLowerCase().equals("agent")) return null;
			if (!v[1].toLowerCase().equals("pause")) return null;
			int pauseTime;
            if (v[5]!=null){
                pauseTime=new Integer(v[5]).intValue();
            }else{ pauseTime=PauseType.PAUSE_FOREVER;};

            int timepoor=0, timebad=0;
            if (v[6]!=null){
                timepoor=new Integer(v[6]).intValue();
                timebad=new Integer(v[7]).intValue();
            };

          	return new EventAgentPause(
					s,
					new RealAgent(v[2],null,null,true),
					v[3],v[4], pauseTime, timepoor, timebad
					);
		}

		
		@Override
		public int getType() {
			return CMD_EVENT_AGENT_PAUSE;
		}

		public RealAgent getAgent() {
			return agent;
		}

		public String getPauseType() {
			return pauseType;
		}

    public int getTimePoor() {
        return timePoor;
    }

    public int getTimeBad() {
        return timeBad;
    }

}