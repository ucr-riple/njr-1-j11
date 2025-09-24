package logfilegen.allgenerators.record.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logfilegen.allmodels.record.request.Method;

public class MethodGen {
	Random random = new Random();
	private List<String> methods = new ArrayList<String>();
	
	public MethodGen(){
		methods.add("OPTIONS");
		methods.add("GET");
		methods.add("HEAD");
		methods.add("POST");
		methods.add("PUT");
		methods.add("DELETE");
		methods.add("TRACE");
		methods.add("LINK");
		methods.add("UNLINK");
		methods.add("CONNECT");
	}
	public Method generate(){
		int randomMethodNum = random.nextInt(methods.size());
		return new Method(methods.get(randomMethodNum));
	}
}
