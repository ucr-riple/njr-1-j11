package statements;

import expressions.Exp;
import expressions.Numbers;
import interfaces.Handler;
import util.Direction;
import util.Position;
import util.Program;

/**
 * This moves the robot left. The number of steps it should move
 * left is determined by the expression given as parameter upon
 * construction of the element.
 * 
 * @author mikaello
 */
public class Left extends Move implements Handler {
    private Exp e;
    
    public Left(Exp e) {
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
                p.setYCord(new Numbers(p.getYCord().number + n.number));
                p.setDirection(Direction.y);
                break;
            case minusX:
                p.setYCord(new Numbers(p.getYCord().number - n.number));
                p.setDirection(Direction.minusY);
                break;
            case y:
                p.setXCord(new Numbers(p.getXCord().number - n.number));
                p.setDirection(Direction.minusX);
                break;
            case minusY:
                p.setXCord(new Numbers(p.getXCord().number + n.number));
                p.setDirection(Direction.x);
                break;
        }
        
        System.out.println(this + " \t" + p);   
         
        // Gives robot new position and records step if pen down
        updateRobot(p);
        
        // Print out grid if Program.printGrid == true
        updateGrid(p);
    }
    
    public String toString() { return "left(" + e + ")"; }

}