package statements;

import interfaces.Handler;
import util.Robot;

/**
 * Writes a mark after the robot in the grid. This class only 
 * alters the value Robot.penDown to be true.
 * 
 * @author mikaello
 */
public class PenDown extends Pen implements Handler {
    @Override
    public void interpret() {
        Robot.penDown = true;
    }
    
    public String toString() { return "down"; }
}
