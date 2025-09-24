package pl.cc.real;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import pl.cc.events.real.RealQueueListener;
import pl.cc.events.real.RealQueueListenerList;
import pl.cc.events.ui.RealQueueUIEvents;

public class RealQueue implements RealQueueUIEvents{
	public static final Logger log = Logger.getLogger(RealQueue.class);
	private RealQueueListenerList queueListenerList = new RealQueueListenerList();
	
	protected String name = null;
	protected int waitCount;
	protected RealAgentList agentList = new RealAgentList();
	protected RealCallList callsList = new RealCallList();
	protected String customType = null;
	
	/*
	 * penalty, predefined, paused są własnościami kolejek tylko z punktu widzenia agenta, który jest (może być) w nich zalogowany
	 */
	protected Integer penalty;
	protected boolean predefined=false;
	protected Boolean paused=null;
    protected int wrapup;
    protected int wrapupTimePoor;
    protected int wrapupTimeBad;
    protected int talkTimePoor;

    public int getTalkTimeBad() {
        return talkTimeBad;
    }

    public void setTalkTimeBad(int talkTimeBad) {
        this.talkTimeBad = talkTimeBad;
    }

    public int getWrapupTimePoor() {
        return wrapupTimePoor;
    }

    public void setWrapupTimePoor(int wrapupTimePoor) {
        this.wrapupTimePoor = wrapupTimePoor;
    }

    public int getWrapupTimeBad() {
        return wrapupTimeBad;
    }

    public void setWrapupTimeBad(int wrapupTimeBad) {
        this.wrapupTimeBad = wrapupTimeBad;
    }

    public int getTalkTimePoor() {
        return talkTimePoor;
    }

    public void setTalkTimePoor(int talkTimePoor) {
        this.talkTimePoor = talkTimePoor;
    }

    protected int talkTimeBad;
	protected ArrayList<String> allowedPauseTypes=null;
	
	public RealQueue(RealQueue queue) {
	    this.name = queue.name;
	    this.waitCount = queue.waitCount;
	    this.agentList = new RealAgentList(queue.agentList);
	    this.callsList = queue.callsList;
	    this.penalty = queue.penalty;
	    this.predefined = queue.predefined;
	    this.paused = queue.paused;
	    this.customType = queue.customType;
	}

	public RealQueue(String name, int waitCount) {
		super();
		this.name = name;
		this.waitCount = waitCount;
	}

	public RealQueue(String name, String waitCount) {
		this.name=name;
		this.waitCount = new Integer(waitCount).intValue();
	}

	public RealQueue(String name, String waitCount, boolean predefined) {
		this.name=name;
		this.waitCount = new Integer(waitCount).intValue();
		this.predefined = predefined;
	}
	
	public RealQueue(String name, String waitCount, String penalty) {
		this.name=name;
		this.waitCount = new Integer(waitCount).intValue();
		try {
			this.penalty = new Integer(penalty).intValue();
		} catch (NumberFormatException e) {
			log.warn("Parse penalty: "+e.getMessage());
		}
	}

	public void addQueueListListener(RealQueueListener queueListener){
		this.queueListenerList.add(queueListener);
	}
	
	public void removeQueueListListener(RealQueueListener queueListener){
		this.queueListenerList.remove(queueListener);
	}
	
	public String getName() {
		return name;
	}
	
	public int getWrapup() {
		return wrapup;
	}

	public void setWaitCount(int waitCount){
		if (waitCount!=this.waitCount){
			this.waitCount = waitCount;
			queueListenerList.onWaitCountChange(waitCount);
		}
	}

	public void onFeedback(int i) {
		log.debug("UI generated feedback event with param: "+i);
		setWaitCount(i);
	}
	
	public RealAgentList  getAgentList() {
		return agentList;
	}

	public RealAgent getAgentByID(Integer agentID) {
		return  (RealAgent) agentList.get(agentID);
	}

	/**
	 * Poďż˝ďż˝czenie opuďż˝ciďż˝o kolejkďż˝ przed odebraniem
	 * @param call
	 */
	public void onQueueLeave(RealCall call) {
		agentList.onCallLeave(call, this);
		queueListenerList.onQueueLeave(call);
	}
	
	/**
	 * Zmieniďż˝a siďż˝ iloďż˝ďż˝ agentďż˝w wolnych/rozmawiajďż˝cych
	 */
	public void updateAgentCounts() {
		queueListenerList.onFreeAgentCountChange(getFreeAgentCount());
		queueListenerList.onTalkingAgentCountChange(getTalkingAgentCount());
	}

	/**
	 * @return Iloďż˝ďż˝ wolnych agentďż˝w - nie rozmawiajďż˝cych i nie zapauzowanych
	 */
	public int getFreeAgentCount(){
		return agentList.getFreeAgentCount();
	}

	/**
	 * @return Iloďż˝ďż˝  agentďż˝w  zapauzowanych
	 */
	public int getPausedAgentCount(){
		return agentList.getPausedAgentCount();
	}
	
	
	/**
	 * @return Iloďż˝ďż˝ rozmawiajďż˝cych agentďż˝w
	 */
	public int getTalkingAgentCount(){
		return agentList.getTalkingAgentCount();
	}

	public int getWaitCount() {
		return waitCount;
	}

	public RealCallList getCallList() {
		return callsList;
	}

	public void setPredefined(boolean predefined) {
		this.predefined = predefined;		
	}

	public Integer getPenalty() {
		return penalty;
	}

	public boolean isPredefined() {
		return predefined;
	}

	public String getCustomType() {
		return customType;
	}

	public void setCustomType(String customType) {
		this.customType = customType;
	}
	
	@Override
	public String toString() {
		String retValue = "RealQueue ("
			+ "name=[" + this.name +"], "
			+ "waitCount=[" + this.waitCount +"], "
			+ "customType=[" + this.customType +"], "
			//+ "agentList=[" + this.agentList +"], "
			//+ "callsList=[" + this.callsList +"], "
			//+ "penalty=[" + this.penalty +"], "
			//+ "predefined=[" + this.predefined +"], "
		+ ")";
		return retValue;
	}

	public boolean containsCall(RealCall call) {
		return callsList.containsCall(call);
	}

	public boolean addAgent(RealAgent agent) {
		return agentList.add(agent);
	}

	public Boolean isPaused() {
		return paused;
	}

	public void setPaused(Boolean paused) {
		this.paused = paused;
	}

	public Object getPausedAgentCount(int idDefault) {
		return agentList.getPausedAgentCount(idDefault);
	}

	public void setWrapup(int wrapup) {
		this.wrapup=wrapup;
	}
	
	public void setAllowedPauseTypes (ArrayList<String> allowedPauseTypes){
		this.allowedPauseTypes=allowedPauseTypes;
	}
	public ArrayList<String> getAllowedPauseTypes (){
		return (this.allowedPauseTypes);
	}
	public boolean isAllowedPauseType (String pauseType){
		if (allowedPauseTypes==null) 
			return true;
		return allowedPauseTypes.contains(pauseType);
	}
}
