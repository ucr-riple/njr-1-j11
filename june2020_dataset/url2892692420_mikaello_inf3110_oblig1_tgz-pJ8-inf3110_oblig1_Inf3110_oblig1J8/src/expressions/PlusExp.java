package expressions;

import interfaces.Handler;

/**
 * Takes to expressions and does an add, subtract or multiply on 
 * these to expressions.
 * 
 * @author mikaello
 */
public class PlusExp extends Exp implements Handler {
    private Exp e1, e2;
    private Operand operand;
    
    public PlusExp(Exp e1, Operand operand, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.operand = operand;
        
        interpret();
    }
    
    /**
     * Does an operation on two numbers based on the operand. The 
     * result is stored in the variable "result" in "class Exp"
     */
    @Override
    public void interpret() {
        switch(operand) {
            case plus:
                result = add(e1, e2);
                break;
            case minus:
                result = subtract(e1, e2);
                break;
            case times:
                result = multiply(e1, e2);
                break;
            default:
                System.out.println("Compile error in PlusExp.interpret(): " + 
                        operand);
                System.exit(1);
        }
        
    }
    
    /**
     * @return the sum of adding e1 to e2
     */
    private Numbers add(Exp e1, Exp e2) {
        return new Numbers(e1.getNumberResult().number + e2.getNumberResult().number);
    }
    
    /**
     * @return the sum of subtracting e2 from e1
     */
    private Numbers subtract(Exp e1, Exp e2) {
        return new Numbers(e1.getNumberResult().number - e2.getNumberResult().number);
    }

    /**
     * @return the product of multiplying e1 with e2
     */
    private Numbers multiply(Exp e1, Exp e2) {
        return new Numbers(e1.getNumberResult().number * e2.getNumberResult().number);                
    }
    
    /**
     * e1 and e2 is instances of Exp.
     * operand is an enum of Operand
     */
    @Override
    public String toString() { 
        // Times has higher priority than plus and minus
        if (operand == Operand.times) {
            return "" + e1 + operand + e2;
        } else {
            return e1 + " " + operand + " " + e2;  
        }
    }
}
