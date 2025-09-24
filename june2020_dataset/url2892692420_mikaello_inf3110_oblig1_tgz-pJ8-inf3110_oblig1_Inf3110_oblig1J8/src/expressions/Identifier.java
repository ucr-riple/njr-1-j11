package expressions;

import interfaces.Handler;
import statements.VarDecl;
import util.Program;

/**
 * An identifier is a name for a value. All values are stored 
 * in robot as VarDecl. So to get the value of this identifier, 
 * it has to call the getVarDecl in Robot, and get the result
 * from that one.
 * 
 * @author mikaello
 */
public class Identifier extends Exp implements Handler {
    String name;
    
    public Identifier(String name) {
        this.name = name;
    }

    public Identifier(Identifier ident) {
        name = ident.name;
    }
            
    @Override
    public void interpret() {
        
    }
    
    /**
     * Gets the variable declaration corresponding to this identifier.
     * @return value of this identifier
     */
    @Override
    public Numbers getNumberResult() {
        VarDecl vd = Program.robotGlobal.getVarDecl(this);
        return vd.getResult();
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Identifier)) {
            return false;
        }
        
        Identifier i = (Identifier) o;
        
        if (name.equals(i.name)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    
    @Override
    public String toString() { return name; }
}
