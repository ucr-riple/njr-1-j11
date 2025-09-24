package expressions;

import interfaces.Handler;

/**
 *
 * @author mikaello
 */
public class BooleanExp extends Exp implements Handler {
    private Exp e1, e2;
    private Booleans booleanValue;
    private Boolean result;
    
    public BooleanExp(Exp e1, Booleans booleanValue, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.booleanValue = booleanValue;
        
        interpret();
    }
    
    /**
     * Does an boolean check on two expressions based on the boolean
     * value. The result is stored in the boolean variable "result".
     */
    @Override
    public void interpret() {
         switch(booleanValue) {
            case greaterThan:
                result = checkGreater(e1, e2);
                break;
            case lessThan:
                result = checkLess(e1, e2);
                break;
            case equal:
                result = checkEqual(e1, e2);
                break;
            default: 
                System.out.println("Compile error in BooleanExp.interpret(): " +
                        booleanValue);
                System.exit(1);
        }
    }
    
    /**
     * @return the result calculated by interpret()
     */
    public boolean getBooleanResult() {
       return result;
    }

    /**
     * @return true if e1 is e2, else returns false
     */
    private boolean checkGreater(Exp e1, Exp e2) {
        return e1.getNumberResult().number > e2.getNumberResult().number;
    }

    /**
     * @return true if e1 is less than e2, else returns false
     */
    private boolean checkLess(Exp e1, Exp e2) {
        return e1.getNumberResult().number < e2.getNumberResult().number;
    }

    /**
     * @return true if e1 is like e2, else returns false
     */
    private boolean checkEqual(Exp e1, Exp e2) {
        return e1.getNumberResult() == e2.getNumberResult();
    }
    
    public String toString() { 
        return e1 + " " + booleanValue + " " + e2; 
    }
}
