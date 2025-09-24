package patterns.gof.behavioral.interpreter;

public class Interpreter {
	private InterpreterContext context;
	
	public Interpreter(InterpreterContext ic) {
		context = ic;		
	}
	
	public String interpret(String str) {
		Expression exp = null;
		
	    //create rules for expressions
	    if (str.contains("hexadecimal")) {
	    	exp = new IntToHexExpression(Integer.parseInt
	    			(str.substring(0, str.indexOf(" "))));
	    } else if(str.contains("binary")) {
	    	exp = new IntToBinaryExpression(Integer.parseInt
	    			(str.substring(0, str.indexOf(" "))));
	    } else return str;
	    
	    return exp.interpret(context);
	}
}