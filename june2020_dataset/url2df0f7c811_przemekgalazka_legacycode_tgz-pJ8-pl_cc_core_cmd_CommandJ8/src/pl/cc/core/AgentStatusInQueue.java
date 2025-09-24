package pl.cc.core;

import org.apache.log4j.Logger;

public enum AgentStatusInQueue {
	RINGING("RINGING","Tel Dzwoni"),
	IN_USE("IN_USE", "Rozmawia"),
	NOT_INUSE("NOT_INUSE", "Wolny"),
	UNKNOWN("UNKNOWN","n/a"),
	RINGINUSE("RINGINUSE");
	
	private static final Logger log = Logger.getLogger(AgentStatusInQueue.class);
	String status;
	String desc;
	
	public static AgentStatusInQueue find(String status){
		if (status==null) return null;
		for (AgentStatusInQueue as : values()){
			if (as.status.toLowerCase().equals(status.toLowerCase())){ 
				if (as==RINGINUSE) {
					as = IN_USE;
				}
				return as;
			}
		}
		log.warn("Nie znaleziono statusu: ["+status+"]");
		return UNKNOWN;
	}

	private AgentStatusInQueue(String status, String desc){
		this.status  = status;
		this.desc = desc;
	}

	
	private AgentStatusInQueue(String status){
		this(status, status);
	}

	public String getStatus() {
		return status;
	}
		
	public String getDesc() {
		return desc;
	}
}
