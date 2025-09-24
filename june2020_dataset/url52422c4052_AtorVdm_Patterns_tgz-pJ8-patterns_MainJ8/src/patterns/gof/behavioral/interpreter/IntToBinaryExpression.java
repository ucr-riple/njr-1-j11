package patterns.gof.behavioral.interpreter;

public class IntToBinaryExpression implements Expression {
	private int decimal;
    
    public IntToBinaryExpression(int c){
    	decimal = c;
    }
    
    @Override
    public String interpret(InterpreterContext ic) {
        return ic.getBinary(decimal);
    }
}