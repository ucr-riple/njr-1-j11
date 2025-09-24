package logfilegen.allconverters.record.request;

import logfilegen.allmodels.record.request.Protocol;

public class ProtocolConv {
	private Protocol protocol;
	
	public ProtocolConv(Protocol protocol){
		this.protocol = protocol;
	}
	public String convertToString(){
		StringBuilder builder = new StringBuilder();
		builder.append(protocol.getProtocol());
		
		builder.append("/");
		builder.append(protocol.getVersion());
		return builder.toString();
	} 
}
