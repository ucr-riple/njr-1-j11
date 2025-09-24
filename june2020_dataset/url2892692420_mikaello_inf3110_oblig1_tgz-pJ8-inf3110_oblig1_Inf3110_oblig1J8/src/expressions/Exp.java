package expressions;

import interfaces.Handler;

/**
 * An expression could be anything than ends up in a number or true
 * or false.
 * 
 * @author mikaello
 */
public class Exp implements Handler {
    protected Numbers result;
    
    public Exp() {
    }
    
    public Exp(Numbers number) {
        result = number;
    }
    
    @Override
    public void interpret() {
        
    }
    
    public Numbers getNumberResult() {
        return result;
    }
    
    @Override
    public String toString() { return "" + result; }
}
