package patterns.gof.behavioral.interpreter;

import java.util.ArrayList;
import java.util.List;

import patterns.gof.helpers.Client;

public class InterpreterClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		List<String> requests = new ArrayList<String>(); 
		requests.add("57 in binary");
		requests.add("11 in binary");
		requests.add("47 in hexadecimal");
		requests.add("183 in hexadecimal");
		
		Interpreter interpreter = new Interpreter(new InterpreterContext());
		
		for(String s : requests) {
			addOutput(s + ": " + interpreter.interpret(s));
		}
		
		super.main("Interpreter");
	}
}