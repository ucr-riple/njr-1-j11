package nl.rug.peerbox.middleware;

import java.util.Date;

class RemoteHost {

	private int hostID;
	private int processedMessageID;
	private int actualMessageID;
	private long lastLifeSign = 0;
	private int count = 0;
	
	static RemoteHost find(int hostID) {
		RemoteHost p  = new RemoteHost();
		p.setHostID(hostID);
		return p;
	}
	
	synchronized void heartbeated() {
		lastLifeSign = new Date().getTime();
		count = 0;
	}
	
	synchronized int incrementCount() {
		return ++count;
	}
	
	synchronized int getCount() {
		return count;
	}
	
	synchronized long getLastLifeSign() {
		return lastLifeSign;
	}
	
	int getHostID() {
		return hostID;
	}

	void setHostID(int hostID) {
		this.hostID = hostID;
	}

	int getReceivedMessageID() {
		return processedMessageID;
	}

	void setReceivedMessageID(int receivedMessageID) {
		this.processedMessageID = receivedMessageID;
	}

	int getSeenMessageID() {
		return actualMessageID;
	}

	void setSeenMessageID(int seenMessageID) {
		this.actualMessageID = seenMessageID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof RemoteHost) {
			RemoteHost other = (RemoteHost)obj;
			return this.hostID == other.hostID;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return hostID;
	}

}
