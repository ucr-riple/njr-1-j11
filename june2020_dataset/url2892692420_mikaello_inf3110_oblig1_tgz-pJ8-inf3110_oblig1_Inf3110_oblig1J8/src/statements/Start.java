package statements;

import expressions.Exp;
import interfaces.Handler;
import util.Direction;
import util.Program;

/**
 * Starts the show. Sets the position of the robot and prints the
 * grid if Prorgam.printGrid == true.
 * 
 * @author mikaello
 */
public class Start extends Stmt implements Handler {
    /** Expressions representing the start coordinates for the robot */
    private Exp x, y;
    
    /** Start direction for the robot */
    private Direction dir;
    
    /**
     * @param x x coordinate the robot will start in
     * @param y y coordinate the robot will start in
     * @param dir direction the robot will start in
     */
    public Start(Exp x, Exp y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    @Override
    public void interpret() {
        // Set start position to the robot
        Program.robotGlobal.setPos(dir, x.getNumberResult(), y.getNumberResult());
        
        // Print the grid
        if (Program.printGrid) {
            Program.gridGlobal.printGrid(x.getNumberResult(), y.getNumberResult(), dir);
        }
    }
    
    public String toString() { 
        return "start(" + x + ", " + y + ", " + dir.symbol + ")"; }
    
}
