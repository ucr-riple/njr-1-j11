package logfilegen.allparsers;

import logfilegen.allmodels.record.request.Protocol;

public class ParserProtocol {
	public Protocol parser(String protocolStr){
		Protocol protocol = new Protocol();
		String[] split = protocolStr.split("/");
		protocol.setProtocol(split[0]);
		protocol.setVersion(split[1]);
		
		return protocol;
	}
	
	
	
}
