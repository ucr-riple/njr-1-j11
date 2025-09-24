package statements;

import expressions.Exp;
import expressions.Identifier;
import interfaces.Handler;
import util.Program;

/**
 * An assignment is a class holding an identifier and an expression.
 * When interpreting an assignment, the corresponding variable
 * declaration in robot is updated with this expression.
 * 
 * @author mikaello
 */
public class Assignment extends Stmt implements Handler {
    Identifier ident;
    Exp e;
    
    public Assignment(Identifier ident, Exp e) {
        this.ident = ident;
        this.e = e;
        System.out.println("New assignment: " + e);
    }
    
    @Override
    public void interpret() {
        // Picks up the VarDecl stored in robot
        VarDecl vd = Program.robotGlobal.getVarDecl(ident);
        
        // Updates this VarDecl
        vd.assignNewExp(e);
    }
    
    @Override
    public String toString() {
        return ident + " = " + e;
    }
}
