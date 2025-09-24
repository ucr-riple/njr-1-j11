package logfilegen.allparsers;

import logfilegen.allmodels.record.Request;

public class ParserRequest {
private ParserMethod parserMethod = new ParserMethod();
private ParserUrl parserUrl = new ParserUrl();
	private ParserProtocol parserProtocol= new ParserProtocol();
	
	public Request parser(String requestStr){
		Request r = new Request();
		String[] split = requestStr.split(" ");
		r.setProtocolMeth(parserMethod.parser(split[0]));
		r.setUrl(parserUrl.parser(split[1]));
		r.setProtocol(parserProtocol.parser(split[2]));
		
	return r;
	}
}
