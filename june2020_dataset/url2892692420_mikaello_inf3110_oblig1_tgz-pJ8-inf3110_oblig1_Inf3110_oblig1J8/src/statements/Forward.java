package statements;

import expressions.Exp;
import expressions.Numbers;
import interfaces.Handler;
import util.Position;
import util.Program;

/**
 * This moves the robot forward. The number of steps it should move
 * forward is determined by the expression given as parameter upon
 * construction of the element.

 * @author mikaello
 */
public class Forward extends Move implements Handler {
    private Exp e;
    
    public Forward(Exp e) {
        this.e = e;
    }

    public void interpret() {
        // Picks up information about distance to move
        Numbers n = lastMovedDistance = e.getNumberResult();
        
        // Get last distance so we can move relative to that
        Position p = Program.robotGlobal.getPos();
        
        // Store a distance, so we can record pen marks relative to that
        curPos = new Position(p);
        
        // Update new position according to which direction the robot is in
        switch(p.getDirection()) {
            case x:
                p.setXCord(new Numbers(p.getXCord().number + n.number));
                break;
            case minusX:
                p.setXCord(new Numbers(p.getXCord().number - n.number));
                break;
            case y:
                p.setYCord(new Numbers(p.getYCord().number + n.number));
                break;
            case minusY:
                p.setYCord(new Numbers(p.getYCord().number - n.number));
                break;
        }
        
        System.out.println(this + " \t" + p);
        
        // Gives robot new position and records step if pen down
        updateRobot(p);
        
        // Print out grid if Program.printGrid == true
        updateGrid(p);
    }

    public String toString() { return "forward(" + e + ")"; }
    
}