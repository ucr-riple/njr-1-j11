package statements;

import expressions.Exp;
import expressions.Numbers;
import interfaces.Handler;
import util.Direction;
import util.Position;
import util.Program;

/**
 * This moves the robot backward. The number of steps it should move
 * backward is determined by the expression given as parameter upon
 * construction of the element.
 * 
 * @author mikaello
 */
public class Backward extends Move implements Handler {
    private Exp e;
    
    public Backward(Exp e) {
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
                p.setXCord(new Numbers(p.getXCord().number - n.number));
                p.setDirection(Direction.minusX);
                break;
            case minusX:
                p.setXCord(new Numbers(p.getXCord().number + n.number));
                p.setDirection(Direction.x);
                break;
            case y:
                p.setYCord(new Numbers(p.getYCord().number - n.number));
                p.setDirection(Direction.minusY);
                break;
            case minusY:
                p.setYCord(new Numbers(p.getYCord().number + n.number));
                p.setDirection(Direction.y);
                break;
        }
        
        System.out.println(this + " \t" + p);
        
        // Gives robot new position and records step if pen down
        updateRobot(p);
        
        // Print out grid if Program.printGrid == true
        updateGrid(p);
    }
    
    public String toString() { return "backward(" + e + ")"; }

}