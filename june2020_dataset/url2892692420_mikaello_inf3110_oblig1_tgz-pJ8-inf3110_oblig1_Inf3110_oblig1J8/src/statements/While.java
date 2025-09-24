package statements;

import expressions.BooleanExp;
import interfaces.Handler;

/**
 * Executes statements until boolean expression is false.
 * Statements should be given to the class constructor as regular
 * Statements, they will automatically appear as a list of statements
 * in the class.
 * 
 * @author mikaello
 */
public class While extends Stmt implements Handler {
    private BooleanExp boolE;
    private Stmt[] stmtList;
    
    public While(BooleanExp boolE, Stmt... stmtList) {
        this.boolE = boolE;
        this.stmtList = stmtList;
    }
    
    @Override
    public void interpret() {
        // Execute statements as long as the boolean expression is true
        while(boolE.getBooleanResult()) {
            for (Stmt st : stmtList) {
                st.interpret();
            }
        
            // Update boolean expression so we don't evaluate the same
            boolE.interpret();
        }
        
    }
    
    public String toString() {
        // First append "while" and boolean expression
        StringBuilder sb = new StringBuilder("while (");
        sb.append(boolE);
        sb.append(") {\n");
        
        // Then append all the statements
        for (int i = 0; i < stmtList.length; i++) {
            sb.append("\t");
            sb.append(stmtList[i]);
            sb.append("\n");
        }
        sb.append("}");
        
        return sb.toString();
    }
    
}
