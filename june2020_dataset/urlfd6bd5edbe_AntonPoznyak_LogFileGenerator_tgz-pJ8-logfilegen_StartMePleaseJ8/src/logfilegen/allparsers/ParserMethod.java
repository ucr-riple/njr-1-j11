package logfilegen.allparsers;

import logfilegen.allmodels.record.request.Method;

public class ParserMethod {
	public Method parser(String methodStr){
		return new Method(methodStr);
	}
}
