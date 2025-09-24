package logfilegen.allconverters.record;

import logfilegen.allmodels.record.Status;

public class StatusConv {
	private Status status;
	
	public StatusConv(Status status){
		this.status = status;
	}
	
	
	public String convertToString(){
		return status.getStatus();
	}
}
