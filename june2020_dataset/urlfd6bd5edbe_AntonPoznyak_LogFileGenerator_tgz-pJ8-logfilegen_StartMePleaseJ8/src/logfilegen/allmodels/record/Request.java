package logfilegen.allmodels.record;

import logfilegen.allmodels.record.request.Method;
import logfilegen.allmodels.record.request.Protocol;
import logfilegen.allmodels.record.request.Url;

public class Request {
	private Method method;
	private Url url;
	private Protocol protocol;
	
	public Request(){};
	
	public Method getProtocolMeth() {
		return method;
	}
	public void setProtocolMeth(Method protocolMeth) {
		this.method = protocolMeth;
	}
	public Url getUrl() {
		return url;
	}
	public void setUrl(Url url) {
		this.url = url;
	}
	public Protocol getProtocol() {
		return protocol;
	}
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	
	public Request(Method method, Url url, Protocol protocol){
		this.method = method;
		this.url = url;
		this.protocol = protocol;
	}
	
	public String toString(){
		return "\""+method.toString()+" "+url.toString()+" "+protocol.toString()+"\"";
	}
}
