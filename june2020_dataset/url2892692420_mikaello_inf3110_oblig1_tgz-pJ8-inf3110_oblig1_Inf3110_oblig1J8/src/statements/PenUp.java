package statements;

import interfaces.Handler;
import util.Robot;

/**
 * Stops recording the movement of the robot. This class only 
 * alters the value of Robot.penDown to be false.
 *  
 * @author mikaello
 */
public class PenUp extends Pen implements Handler {
    @Override
    public void interpret() {
        Robot.penDown = false;
    }

    
    public String toString() { return "up"; }
}