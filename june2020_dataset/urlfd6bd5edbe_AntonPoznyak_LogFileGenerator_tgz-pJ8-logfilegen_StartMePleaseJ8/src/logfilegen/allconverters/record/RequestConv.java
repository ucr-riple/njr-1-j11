package logfilegen.allconverters.record;

import logfilegen.allconverters.record.request.MethodConv;
import logfilegen.allconverters.record.request.ProtocolConv;
import logfilegen.allconverters.record.request.UrlConv;
import logfilegen.allmodels.record.Request;

public class RequestConv {
	private Request request;
	
	public RequestConv(Request request){
		this.request = request;
	}
	public String convertToString(){
		StringBuilder builder = new StringBuilder();
		MethodConv method = new MethodConv(request.getProtocolMeth());
		builder.append(method.convertToString());
		
		builder.append(" ");
		UrlConv url = new UrlConv(request.getUrl());
		builder.append(url.convertToString());
		builder.append(" ");
		ProtocolConv protocol = new ProtocolConv(request.getProtocol());
		builder.append(protocol.convertToString());
		
		return builder.toString();
	} 
}
