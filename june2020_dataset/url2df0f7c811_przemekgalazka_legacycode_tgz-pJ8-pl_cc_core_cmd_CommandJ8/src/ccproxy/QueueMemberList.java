package ccproxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueueMemberList {
	List<QueueMember> aclist = Collections.synchronizedList( new ArrayList<QueueMember>() );

	public QueueMemberList() {

	}

	public synchronized int size() {
		return aclist.size();
	}

	public synchronized QueueMember get(int i) {
		return aclist.get(i);
	}

	public synchronized boolean contains(Object o) {
		return aclist.contains(o);
	}

	public synchronized boolean containsNumer(String s) {
		for (int i = 0; i < aclist.size(); i++) {
			QueueMember qm = aclist.get(i);
			if (qm.ac.agentNumber.equals(s))
				return true;
		}
		return false;
	}

	public synchronized void add(QueueMember ac) {
		aclist.add(ac);
	}

	public synchronized void remove(QueueMember ac) {
		aclist.remove(ac);
	}

	public synchronized void remove(int i) {
		aclist.remove(i);
	}

	public synchronized QueueMember getByIP(String ip) {
		for (int i = 0; i < aclist.size(); i++) {
			if (aclist.get(i).ac.ip.equals(ip))
				return aclist.get(i);
		}
		return null;
	}

	public synchronized QueueMember getQueueMemberByNumer(String number) {
		for (int i = 0; i < aclist.size(); i++) {
			if (aclist.get(i).ac.agentNumber.equals(number))
				// //System.out.println("dupadapduapdsa");
				return aclist.get(i);
		}
		return null;
	}

	public synchronized void pushMessage(String message) {
		for (int i = 0; i < aclist.size(); i++) {
			aclist.get(i).ac.out.println(message);
		}
	}

	public void pushMessage(String message, QueueMember qm) {
		qm.ac.out.println(message);
	}

	public void pushQueueMessage(String message) {
		for (int i = 0; i < aclist.size(); i++) {
			// get(i).out.println(message);
		}
	}
	/**
	 * Kiedyś poniższe notyfikacje trafiały tylko do supervisorów.
	 * W chwili obecnej agenci otrzymują te same notyfikacje co supervsorzy
	 * @param message
	 */
	public synchronized void pushSuperMessage(String message) {
		for (int i = 0; i < aclist.size(); i++) {
				aclist.get(i).ac.out.println(message);
		}
	}

	public void remove(AgentConnection ac) {
		QueueMember qm = getQueueMemberByNumer(ac.agentNumber);
		if (qm != null)
			remove(qm);

	}

}
