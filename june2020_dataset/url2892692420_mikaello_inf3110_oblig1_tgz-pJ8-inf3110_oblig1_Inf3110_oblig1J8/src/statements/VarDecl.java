package statements;

import expressions.Exp;
import expressions.Identifier;
import expressions.Numbers;
import interfaces.Handler;

/**
 * A variable declaration. It contains a identifier and a corresponding
 * expression.
 * 
 * @author mikaello
 */
public class VarDecl implements Handler {

    Identifier ident;
    Exp e;
    
    /** 
     * Interprets a expression and assign it's new value to this 
     * variable.
     * @param e new expression for this variable
     */
    public void assignNewExp(Exp e) {
        e.interpret();
        this.e = new Exp(e.getNumberResult());
    }
    
    /** 
     * @param ident identifier for this variable
     * @param e  expression for this variable
     */
    public VarDecl(Identifier ident, Exp e) {
        this.ident = ident;
        this.e = e;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VarDecl)) {
            return false;
        }
        
        VarDecl vd = (VarDecl) o;
        
        if (ident.equals(vd.ident)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.ident != null ? this.ident.hashCode() : 0);
        return hash;
    }

    
    @Override
    public void interpret() {
        
    }
    
    @Override
    public String toString() { return "var " + ident + " = " + e; }

    
    /**
     * @return a copy of this identifier
     */
    public Identifier getIdentifier() {
        return new Identifier(ident);
    }

    /**
     * @return the result of the expression assigned to this variable
     */
    public Numbers getResult() {
        return e.getNumberResult();
    }
    
    
}
