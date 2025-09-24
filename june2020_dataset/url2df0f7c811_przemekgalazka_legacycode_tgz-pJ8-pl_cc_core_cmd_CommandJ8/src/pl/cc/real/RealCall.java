package pl.cc.real;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import pl.cc.core.CallStatus;
import pl.cc.events.real.RealCallListener;
import pl.cc.events.real.RealCallListenerList;
import pl.cc.utils.Utils;


public class RealCall {
	public static final Logger log = Logger.getLogger(RealCall.class);

	private RealCallListenerList callListeners = new RealCallListenerList();
	
	protected String callerID;
	protected String uniqueID;
	/**
	 * Kolejka w ramach której to połaczenie istnieje
	 */
	RealQueue queue = null;
	Date crationTime = GregorianCalendar.getInstance().getTime();
	
	public String getCrationTimeFormated() {
		return Utils.formatDate(crationTime);
	}

	private long leaveQueueAfrer = -1;
	private long hangupQueueAfrer = -1;
	
	/**
	 * Jeśli ktoś odebrał to połączenie mamy wypełnione:
	 * connectedAgent i connectedQueue
	 */
	private RealAgent connectedAgent;
	private RealQueue connectedQueue;
	
	public RealCall(String callerID, String uniqueId){
		this.callerID = callerID;
		this.uniqueID = uniqueId;
	}

	public RealCall(String callerID, String uniqueId, RealQueue queue, Date crationTime) {
		this(callerID, uniqueId);
		setQueue(queue);
		this.crationTime=crationTime;
	}

	public boolean callsWithId(String uniqueID) {
		return (this.uniqueID.equals(uniqueID));
	}
	
	public String toString() {
	    String retValue = "";
	    retValue = "Call ("
			//+ super.toString() +" "
	        + "callerID: [" + this.callerID + "] "
	        + "uniqueID: [" + this.uniqueID + "] "
	        + ")";
	    return retValue;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public String getCallerID() {
		return callerID;
	}

	public long getSecElapsed(){
		Date timeNow = GregorianCalendar.getInstance().getTime();
		return (timeNow.getTime()-crationTime.getTime()) /1000;
	}
	
	public String getTimeElapsed(){
		return Utils.secToString(getSecElapsed());
	}
	
	public RealAgent getConnectedAgent() {
		return connectedAgent;
	}
	
	public void resetTimer() {
		crationTime = GregorianCalendar.getInstance().getTime();
	}

	public void leaveQueue() {
		leaveQueueAfrer=getSecElapsed();		
		crationTime = GregorianCalendar.getInstance().getTime();
	}

	public void hangup() {
		hangupQueueAfrer=getSecElapsed();		
		crationTime = GregorianCalendar.getInstance().getTime();
	}

	
	public long getLeaveQueueAfrer() {
		return leaveQueueAfrer;
	}

	public long getHangupQueueAfrer() {
		return hangupQueueAfrer;
	}
	
	public boolean equals(Object o){
		if (o==null) return false;
		if (!(o instanceof RealCall)) return false;
		if (uniqueID.equals(((RealCall)o).uniqueID)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void addCallListener(RealCallListener realCallListener){
		this.callListeners.add(realCallListener);
	}
	
	public void removeCallListener(RealCallListener realCallListener){
		this.callListeners.remove(realCallListener);
	}
	
	public void agentConnect(RealAgent agent, RealQueue queue){
		this.connectedAgent = agent;
		this.connectedQueue = queue;
		callListeners.onAgentConnect(this, agent, queue);
	}
	
	public void agentHangeup(RealAgent agent, RealQueue queue){
		if (this.connectedAgent.getId().equals(agent.getId()) && this.connectedQueue.getName().equals(queue.getName())){
			this.connectedAgent = null;
			this.connectedQueue = null;
			callListeners.onAgentHangeup(this, agent, queue);
		}
	}

	public void setQueue(RealQueue queue) {
		this.queue = queue;		
	}

	public RealQueue getQueue() {
			return queue;
	}

	public RealQueue getconnectedQueue() {
		return connectedQueue;
	}

	public CallStatus getStatus() {
		if (connectedAgent != null){
			return CallStatus.ANSWERED;
		} else {
			return CallStatus.RINGING;
		}
	}

	public void setCreationTimeAsString(String string) {
		crationTime = Utils.parseDate(string);
	}
	
	
}
