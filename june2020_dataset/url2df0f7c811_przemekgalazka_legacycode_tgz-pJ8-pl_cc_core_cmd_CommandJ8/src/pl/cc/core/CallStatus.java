package pl.cc.core;

import org.apache.log4j.Logger;

public enum CallStatus {
	RINGING("RINGING", "Dzwoni"),
	ANSWERED("ANSWERED", "Odebrane"),
	UNKNOWN("ANSWERED", "Odebrane"),
;
	
	private static final Logger log = Logger.getLogger(CallStatus.class);
	String status;
	String desc;
	
	public static CallStatus find(String status){
		if (status==null) return null;
		for (CallStatus as : values()){
			if (as.status.toLowerCase().equals(status.toLowerCase())){ 
				return as;
			}
		}
		log.warn("Nie znaleziono statusu: ["+status+"]");
		return UNKNOWN;
	}

	private CallStatus(String status, String desc){
		this.status  = status;
		this.desc = desc;
	}

	
	private CallStatus(String status){
		this(status, status);
	}

	public String getStatus() {
		return status;
	}
	
	public String getDesc() {
		return desc;
	}

		
}
