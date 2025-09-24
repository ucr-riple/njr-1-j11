package logfilegen.allmodels.record.request;

public class Method {
	private String method;
	public Method() {};
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public Method(String method){
		this.method = method;
	}
	public String toString(){
		return method;
	}
}
