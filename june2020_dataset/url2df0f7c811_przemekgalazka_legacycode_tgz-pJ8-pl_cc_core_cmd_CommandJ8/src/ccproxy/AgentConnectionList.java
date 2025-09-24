package ccproxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.cc.SystemCoreState;
import pl.cc.core.cmd.Command;

public class AgentConnectionList {
	private List<AgentConnection> aclist = Collections.synchronizedList(new ArrayList<AgentConnection>());
	SystemCoreState coreState;
	
	public AgentConnectionList(SystemCoreState coreState) {
		this.coreState = coreState;
	}

	public synchronized int size() {
		return aclist.size();
	}

	public synchronized AgentConnection get(int i) {
		return aclist.get(i);
	}

	public synchronized boolean contains(Object o) {
		return aclist.contains(o);
	}

	public synchronized void add(AgentConnection ac) {
		aclist.add(ac);
	}

	public synchronized void remove(AgentConnection ac) {
		aclist.remove(ac);
	}

	public synchronized void remove(int i) {
		aclist.remove(i);
	}

	public synchronized AgentConnection getByIP(String ip) {
		for (int i = 0; i < aclist.size(); i++) {
			if (aclist.get(i).ip.equals(ip))
				return aclist.get(i);
		}
		return null;
	}

	public synchronized AgentConnection getAgentConnectionByNumer(String number) {
		for (int i = 0; i < aclist.size(); i++) {
			if (aclist.get(i).agentNumber.equals(number))
				// //System.out.println("dupadapduapdsa");
				return aclist.get(i);
		}
		return null;
	}

	public synchronized AgentConnection getAgentConnectionByExten(String exten) {
		for (int i = 0; i < aclist.size(); i++) {
			if (aclist.get(i).agentExten.equals(exten))
				// //System.out.println("dupadapduapdsa");
				return aclist.get(i);
		}
		return null;
	}

    public synchronized void pushSupervisorMessage(String message) {
        for (int i = 0; i < aclist.size(); i++) {
            if (aclist.get(i).supervisor){
                aclist.get(i).out.println(message);
            }
        }
    }

    public synchronized void pushMessage(String message) {
		for (int i = 0; i < aclist.size(); i++) {
			aclist.get(i).out.println(message);
		}
	}

	public void pushMessage(String message, AgentConnection ac) {
		ac.out.println(message);
	}

	public void pushQueueMessage(String message) {
		for (int i = 0; i < aclist.size(); i++) {
			// get(i).out.println(message);
		}
	}

	public void pushSuperMessage(String message){
		pushSuperMessage(message, false);
	}
	
	public synchronized void pushSuperMessage(String message, boolean coreStateOnly) {
		// wysyłamy do komponentu budującego stan systemu komendy dokładnie takie jakgdyby był supervisorem
		Command cmd = Command.factory(message, "");
		coreState.process(cmd, null);
		if (!coreStateOnly){
		// tu notyfikacje rzeczywistych supervisorów
		for (int i = 0; i < aclist.size(); i++) {
			aclist.get(i).out.println(message);
		}
		}
	}

}
