package statements;

import interfaces.Handler;
import log.Log;
import util.Program;

/**
 * Stops the program. It will print out the log to screen (if 
 * Program.prettyPrint == true) and quits program with status 0.
 * 
 * @author mikaello
 */
public class Stop extends Stmt implements Handler {

    
    public void interpret() {
        // Make final logline
        Log.writeLogLine(Program.STARS);
        
        if (Program.printPrettyPrint)
            Program.printPrettyPrint();
        
        System.exit(0);
    }
    
    public String toString() { return "stop"; }
}
