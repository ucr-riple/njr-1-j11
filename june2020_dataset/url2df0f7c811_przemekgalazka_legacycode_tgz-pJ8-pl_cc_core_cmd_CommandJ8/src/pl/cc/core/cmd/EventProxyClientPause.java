package pl.cc.core.cmd;

import pl.cc.core.PauseType;
import pl.cc.real.RealAgent;

/**
 * Zdarzenie pauzy/unpauzy dla konkretrnego agenta - generowane przez proxyclient (ogólnie proxyClient i proxySupervisor)
 *
 * @since 2009-06-01
 */
public class EventProxyClientPause extends Command {
		boolean pause;
		String type;
		RealAgent agent;
		int pauseTime;
		int timePoor;
        int timeBad;
		//EVENT [ProxyClient], Action [Pause], Value [True], Type [Default], Agent [313], Exten [874]

		public EventProxyClientPause(String orginalLine, 	boolean pause, String type, RealAgent agent, int pauseTime, int timepoor, int timebad) {
			super(orginalLine);
			this.pause = pause;
			this.type = type;
			this.agent = agent;
			this.pauseTime = pauseTime;
            this.timePoor = timepoor;
            this.timeBad = timebad;
		}

		public static Command factoryInt(String s){
			String [] names = {"event","action","value","type","agent","exten","time","timepoor","timebad"};
			String [] v = getValues(s, names, 5); 
			if (v==null) return null;
			if (!"ProxyClient".equalsIgnoreCase(v[0])) return null;
			if (!"Pause".equalsIgnoreCase(v[1])) return null;
			int pauseTime = PauseType.PAUSE_FOREVER;
            if (v[6]!=null){
                pauseTime=new Integer(v[6]).intValue();
            }else{ pauseTime=PauseType.PAUSE_FOREVER;};
            int timepoor=0, timebad=0;
            if (v[7]!=null){
                timepoor=new Integer(v[7]).intValue();
                timebad=new Integer(v[8]).intValue();
            };

			return new EventProxyClientPause(
					s,
					new Boolean(v[2]),
					v[3],
					new RealAgent(v[4],null,v[5],false),
					pauseTime, timepoor, timebad
					);
		}

		
		@Override
		public int getType() {
			return CMD_EVENT_PROXYCLIENT_PAUSE;
		}

		public boolean isPause() {
			return pause;
		}

    public int getTimePoor() {
        return timePoor;
    }

    public int getTimeBad() {
        return timeBad;
    }

    public void setPause(boolean pause) {
			this.pause = pause;
		}

		public RealAgent getAgent() {
			return agent;
		}

		public void setAgent(RealAgent agent) {
			this.agent = agent;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getPauseType(){
			return type;
		}

		public int getPauseTime(){
			return pauseTime;
		}



	}