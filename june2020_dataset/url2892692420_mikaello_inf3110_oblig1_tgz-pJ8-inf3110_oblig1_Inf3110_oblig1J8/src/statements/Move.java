package statements;

import expressions.Numbers;
import interfaces.Handler;
import java.util.HashMap;
import util.Direction;
import util.Position;
import util.Program;
import util.Robot;

public abstract class Move extends Stmt implements Handler {
    /** This is the current position of the robot (used in fillPositions(), 
     * there we get a newer position, and we can compare with this one */
    protected Position curPos;
    
    /** When pen is down, all movements are stored in this HashMap*/
    private static HashMap<Position, Direction> penPositions = new HashMap<Position, Direction>();
    
    /** Distanced moved in the last move */
    protected Numbers lastMovedDistance;
    
    /**
     * Clears all the positions in the HashMap containing all pen
     * positions. This is useful when a new program is starting, then you 
     * want paths from previous programs to be cleared.
     */
    public static void clearPenPositions() {
        penPositions.clear();
    }
    
    
    /**
     * Update robot with current position. If Robot.penDown == true,
     * then update the last moved path.
     * @param p 
     */
    void updateRobot(Position p) {
        Program.robotGlobal.setPos(p);
        if (Robot.penDown) {
            fillPositions(p);
        } 
    }
    
    /** 
     * If Program.printGrid is true, this method call Grid.printGrid() 
     * which prints out the grid.
     */
    void updateGrid(Position p) {
        if (Program.printGrid) {
            Program.gridGlobal.printGrid(penPositions, p);
            System.out.println("Last distance moved: " + lastMovedDistance);
        }

    }

    /**
     * This method will fill global variable penPositions with 
     * Position's and Direction symbols. Positions will be calculated
     * based previous position, plus number of steps moved in last
     * move.
     * @param p new position
     */
    private void fillPositions(Position p) {
        
        for (int i = 0; i < lastMovedDistance.number+1; i++) {
            // This switch is a little awkward, for all runs in this 
            // for-loop, it will be the same result in the switch 
            // (because I don't alter p.getDirection())
            switch(p.getDirection()) {
                case x:
                    penPositions.put(new Position(new Numbers(curPos.getXCord().number+i), curPos.getYCord()), 
                            Direction.x);
                    break;
                case minusX:
                    penPositions.put(new Position(new Numbers(curPos.getXCord().number-i), curPos.getYCord()), 
                            Direction.minusX);
                    break;
                case y:
                    penPositions.put(new Position(curPos.getXCord(), new Numbers(curPos.getYCord().number+i)), 
                            Direction.y);
                    break;
                case minusY:
                    penPositions.put(new Position(curPos.getXCord(), new Numbers(curPos.getYCord().number-i)), 
                            Direction.minusY);
                    break;
            }
        }
    }
}







