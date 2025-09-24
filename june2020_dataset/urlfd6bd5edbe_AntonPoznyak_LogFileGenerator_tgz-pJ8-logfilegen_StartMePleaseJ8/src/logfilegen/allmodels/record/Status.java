package logfilegen.allmodels.record;

public class Status {
	private String status;
	public Status(){}
	public Status(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString(){
		return status;
	}
}
