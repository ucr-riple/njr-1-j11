package patterns.gof.behavioral.interpreter;

public class IntToHexExpression implements Expression {
	private int decimal;
    
    public IntToHexExpression(int c){
    	decimal = c;
    }
    
    @Override
    public String interpret(InterpreterContext ic) {
        return ic.getHexadecimal(decimal);
    }
}
