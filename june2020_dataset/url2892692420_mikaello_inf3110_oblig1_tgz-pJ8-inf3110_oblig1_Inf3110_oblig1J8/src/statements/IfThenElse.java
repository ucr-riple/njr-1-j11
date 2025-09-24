package statements;

import expressions.BooleanExp;
import interfaces.Handler;

/**
 * Controls an if-then-else statement. If the boolean expression 
 * is true, then all the statements in the ifStmtList parameter 
 * is executed, else is all the statements in the elseStmtList
 * executed.
 * 
 * @author mikaello
 */
public class IfThenElse extends Stmt implements Handler {
    private BooleanExp boolE;
    private Stmt[] ifStmtList, elseStmtList;
    
    public IfThenElse(BooleanExp boolE, Stmt[] ifStmtList) {
        this.boolE = boolE;
        this.ifStmtList = ifStmtList;
    }
    
    public IfThenElse(BooleanExp boolE, Stmt[] ifStmtList, 
            Stmt[] elseStmtList) {
        this(boolE, ifStmtList);
        this.elseStmtList = elseStmtList;
    }
    public void interpret() {
        // Check boolean expression
        if(boolE.getBooleanResult()) {
            // Execute if-statement
            for (Stmt st : ifStmtList) {
                st.interpret();
            }
        } else if (elseStmtList != null) {
            // Execute else-statement
            for (Stmt st : elseStmtList) {
                st.interpret();
            }
        }
    }
    
    public String toString() {
        // First append the "if" and boolean expression
        StringBuilder sb = new StringBuilder("if (");
        sb.append(boolE);
        sb.append(") {\n");
        
        // Then append the if-part
        for (int i = 0; i < ifStmtList.length; i++) {
            sb.append("\t");
            sb.append(ifStmtList[i]);
            sb.append("\n");
        }

        sb.append("}");
        
        // And eventually append the else-part
        if (elseStmtList != null) {
            sb.append(" else {\n");
            for (int i = 0; i < elseStmtList.length; i++) {
                sb.append("\t");
                sb.append(elseStmtList[i]);
                sb.append("\n");
            }
            
            sb.append("}");
        } 
        
        return sb.toString();
        
    }
}
