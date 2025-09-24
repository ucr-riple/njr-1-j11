package logfilegen.allconverters.record.request;

import logfilegen.allmodels.record.request.Method;

public class MethodConv {
	private Method method;
	
	public MethodConv(Method method){
		this.method = method;
	}
	
	public String convertToString(){
		return method.getMethod();
	}
} 
