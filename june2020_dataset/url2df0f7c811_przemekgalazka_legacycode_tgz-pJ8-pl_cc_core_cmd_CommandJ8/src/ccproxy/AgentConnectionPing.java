package ccproxy;

import org.apache.log4j.Logger;

public class AgentConnectionPing extends Thread {
	static Logger log = Logger.getLogger(AgentConnectionPing.class);
	
	AgentConnection ac;
	Thread pingThread = null, mainThread;
	private boolean stop = false;
	
	public AgentConnectionPing(AgentConnection agentConnection,
			Thread mainThread) {
		ac = agentConnection;

		this.mainThread = mainThread;
	}

	public void stopThread(){
		stop=true;
	}
	
	public void run() {
		setName("Ping: "+ac.agentNumber);
		pingThread = currentThread();
		;

		while (true) {
			if (isInterrupted())
				break;
			if (ac.loggedout)
				break;
			if (stop){
				break;
			}
			if (!ac.checkPing(pingThread, mainThread))
				break;
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				log.debug(e);
			}
		}
		log.info("Ping thread stoped");
	}

}
