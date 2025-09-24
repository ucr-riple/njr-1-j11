package pl.cc.real;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import pl.cc.ProxyTasks;
import pl.cc.core.AgentStatusInQueue;
import pl.cc.core.LoginCredentials;
import pl.cc.core.PauseType;
import pl.cc.core.cmd.OkWelcome;
import pl.cc.events.real.RealAgentListener;
import pl.cc.events.real.RealAgentListenerList;
import pl.cc.events.ui.RealAgentUIEvents;

public class RealAgent implements RealAgentUIEvents{
	public static final Logger log = Logger.getLogger(RealAgent.class);
		
	protected Integer id;
	protected String name;
	protected String extension;
	protected boolean isPaused = false;
	private PauseType pauseType;
	private int pauseTime = PauseType.PAUSE_FOREVER;
	
	private RealAgentListenerList agentListenerList = new RealAgentListenerList();
	private ProxyTasks proxyTasks;
	private Date lastStateChange=GregorianCalendar.getInstance().getTime();
	
	/* Okreďż˝la (trochďż˝ kulawo) bieďż˝ďż˝cy status agenta */
	RealCall connectedCall = null;
	RealCall ringingCall = null;
	RealCall lastCall = null;
	
	AgentStatusInQueue status = AgentStatusInQueue.NOT_INUSE;
	/* Jeśli agent otrzymał połączenie w ramach kolejki - ta zmienna jest odpowiednio ustawione */
	private RealQueue connectedInQueue = null;	
	protected ArrayList<String> allowedPauseTypes=null;
    private int timePoor;


    private int timeBad;

    public void setProxyTasks(ProxyTasks proxyTasks) {
		this.proxyTasks = proxyTasks;
	}

	public RealAgent(RealAgent agent, boolean copyListeners) {
		this(agent);
		if (copyListeners) {
			this.agentListenerList = agent.agentListenerList;
		}
	}
	
	/**
	 * Konstruktor kopiujący.
	 * NIE kopiuje agentListenerList
	 * @param agent
	 */
	public RealAgent(RealAgent agent) {
	    this.id = agent.id;
	    this.name = agent.name;
	    this.extension = agent.extension;
	    this.isPaused = agent.isPaused;
	    this.pauseType = agent.pauseType;
	}
	
	public RealAgent(RealAgent agent, ProxyTasks  proxyTasks) {
		this(agent);
	    this.proxyTasks = proxyTasks;
	}

	public RealAgent(String id, String name, String extension) {
		this.name=name;
		this.extension = extension;
		this.id = new Integer(id).intValue();
	}

	public RealAgent(Integer id, String name, String extension) {
		this(id.toString(),name,extension);
	}

	public RealAgent(Integer id) {
		this(id.toString(),null, null);
	}
	
	public RealAgent(String id, String name, String extension, boolean isPaused) {
		this(id,name,extension);
		this.isPaused = isPaused;
	}

	public RealAgent(LoginCredentials login) {
		this(login.username,login.username,login.exten);
	}
	
	public void addAgentListener(RealAgentListener realAgentListener){
		this.agentListenerList.add(realAgentListener);
	}
	
	public void removeAgentListener(RealAgentListener realAgentListener){
		this.agentListenerList.remove(realAgentListener);
	}
	
	public RealAgent(OkWelcome selfInfo) {
		this(selfInfo.getAgentNumber(), selfInfo.getName(), selfInfo.getExtension());
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}

	public int getPauseTime() {
		return pauseTime;
	}

    public int getTimePoor() {
        return timePoor;
    }

    public int getTimeBad() {
        return timeBad;
    }


	public boolean setPaused(boolean paused, PauseType pauseType) {
		return setPaused(paused, pauseType, PauseType.PAUSE_FOREVER, 0, 0);
	}
	
	public boolean setPaused(boolean paused, PauseType pauseType, int pauseTime, int timePoor, int timeBad) {
		if (this.isPaused!=paused || this.pauseType==null || !this.pauseType.equals(pauseType)){
			 this.isPaused = paused;
			 this.pauseType = pauseType;
			 this.pauseTime = pauseTime;
             this.timePoor = timePoor;
             this.timeBad = timeBad;
			agentListenerList.onPause(this, paused, pauseType, pauseTime, timePoor, timeBad);
			lastStateChange=GregorianCalendar.getInstance().getTime();
			return true;
		}
		return false;
	}

	
	public Date getLastStateChange() {
		return lastStateChange;
	}

	public int getTimeInLastState(){
		if (connectedCall!=null) return (int) connectedCall.getSecElapsed();
		return (int) ((GregorianCalendar.getInstance().getTime().getTime()-lastStateChange.getTime())/1000);
	}
	
	public void setLastStateChange(Date lastStateChange) {
		this.lastStateChange = lastStateChange;
	}

	public void onSpy() {
		log.debug("UI onSpy: "+getName());
		if (proxyTasks!=null){
			proxyTasks.doSpyAgent(this);
		} else {
			log.debug("proxyTasks == null");
		}
	}
	
	public void doPause(boolean pause, PauseType pauseType) {
		log.debug("UI doPause: "+getName());
		if (proxyTasks!=null){
			proxyTasks.doPauseAgent(this, pause, pauseType);
		} else {
			log.debug("proxyTasks == null");
		}
	}
	
	@Override
	public void doPauseAdministrative(boolean pause) {
		if (proxyTasks!=null){
			proxyTasks.doPauseAgentAdministrative(this, pause);
		} else {
			log.debug("proxyTasks == null");
		}
	}
	
	public void onAgentNewCall(RealCall ringingCall, RealQueue queue) {
		this.ringingCall = ringingCall;
		this.connectedCall = null;
		agentListenerList.onAgentNewCall(ringingCall, queue);
	}

	public void onCallLeave(RealCall callLeave, RealQueue queue) {
		if (ringingCall==null) return;
		if (callLeave.getUniqueID()==ringingCall.getUniqueID()){
			ringingCall=null;
			agentListenerList.onCallLeave(this, callLeave, queue);
		}
	}
	
	public void onHangeup(RealCall call) {
		if ((connectedCall != null ) && (connectedCall.equals(call))){
			connectedCall = null;
			ringingCall = null;
			//status = AgentStatusInQueue.NOT_INUSE;
			connectedInQueue = null;
			agentListenerList.onHangeup(this, call);

		} else if ((ringingCall != null )){
			connectedCall = null;
			ringingCall = null;
			//status = AgentStatusInQueue.NOT_INUSE;
			connectedInQueue = null;
			agentListenerList.onHangeup(this, call);
		}	
	}
	
	
		
	public void onAgentConnect(RealCall callAgentConnect, RealQueue queue) {
		connectedCall = callAgentConnect;
		ringingCall = null;
		status = AgentStatusInQueue.IN_USE;
		connectedInQueue = queue;
		agentListenerList.onAgentConnect(this, callAgentConnect, queue);
	}
	
	public void onChangeStatusAsQueueMember(RealQueue queue, AgentStatusInQueue status) {
		if ((this.status==AgentStatusInQueue.IN_USE&status==AgentStatusInQueue.NOT_INUSE)|
			(this.status==AgentStatusInQueue.RINGING&status==AgentStatusInQueue.IN_USE)|
			(this.status==AgentStatusInQueue.NOT_INUSE&status==AgentStatusInQueue.IN_USE))
			lastStateChange=GregorianCalendar.getInstance().getTime();

		this.status = status;
		agentListenerList.onChangeStatusAsQueueMember(this, queue, status);
	}

	public RealCall getConnectedCall() {
		return connectedCall;
	}

	public AgentStatusInQueue getStatus() {
		return status;
	}
	
	public boolean equals(Object o){
		if (o==null) {
			return false;
		} else if (! (o instanceof RealAgent)) {
			return false;
		} else {
			return getId().equals(((RealAgent)o).getId());
		}
	}

	public boolean isPaused() {
		return isPaused;
	}

	@Override
	public String toString() {
		String pauseType = "null";
		if (this.pauseType!=null){
			pauseType = this.pauseType.getName();
		}
		String retValue = "RealAgent ("
			+ "id=[" + this.id +"], "
			+ "name=[" + this.name +"], "
			+ "extension=[" + this.extension +"], "
			+ "isPaused=[" + this.isPaused +"], "
			+ "pauseType=[" + pauseType +"], "
			+ "connectedCall=[" + this.connectedCall +"], "
			+ "ringingCall=[" + this.ringingCall +"], "
			+ "status=[" + this.status +"], "
		+ ")";
		return retValue;
	}

	public void dumpListenerList(){
		log.info("Dump listener list ["+agentListenerList.size()+"] for: "+toString());
		int c = 1;
		for (RealAgentListener listener : agentListenerList){
			log.info((new Integer(c++).toString())+" "+listener.getClass());
		}
	}
	
	public RealCall getCall() {
		if (connectedCall != null) return connectedCall;
		if (ringingCall != null) return ringingCall;
		return null;
	}

	public PauseType getPauseType() {
		return pauseType;
	}

	/**
	 * Jeśli agent otrzymał połączenie w ramach kolejki - ta zmienna jest odpowiednio ustawione
	 * @return kolejka, bądź null
	 */
	public RealQueue getConnectedInQueue() {
		return connectedInQueue;
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
