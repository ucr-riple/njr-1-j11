package util;

import exceptions.RobotOutOfGridException;
import expressions.Numbers;

/**
 * This class specifies a position. That means a given 
 * direction and x and y coordinate. This class is also used as
 * just a position with the x and y coordinate, the direction
 * is then just set as "standby".
 * 
 * @author mikaello
 */
public class Position implements Comparable<Position>{
    /* Where the robot is heading */
    private Direction dir;
    
    /* Position on grid */
    private Numbers xCord, yCord;
    
    public Position() {
        dir = Direction.x;
        xCord = new Numbers(1);
        yCord = new Numbers(1);
    }
    
    public Position(Position p) {
        dir = p.getDirection();
        xCord = new Numbers(p.getXCord());
        yCord = new Numbers(p.getYCord());
    }
    
    public Position(Numbers x, Numbers y) {
        this(x, y, Direction.standby);
    }
    
    public Position(Numbers x, Numbers y, Direction d) {
        xCord = x;
        yCord = y;
        dir = d;
    }
        
    public void setDirection(Direction dir) {
        this.dir = dir;
    }
    
    public void setXCord(Numbers x) {
        setCoordinates(x, getYCord());
    }
    
    public void setYCord(Numbers y) {
        setCoordinates(getXCord(), y);
    }
    
    /**
     * This method sets new coordinates. It checks that both
     * coordinates are inside grid, and throws a 
     * RobotOutOfGridException (with an appropriate message) 
     * and quits the program.
     * @param x
     * @param y 
     */
    private void setCoordinates(Numbers x, Numbers y) {
        try {
            if (Program.gridGlobal.isInsideGrid(x, y)) {
                xCord = x;
                yCord = y;
            } else {
                
                throw new RobotOutOfGridException("Grid " + 
                        Program.gridGlobal.getSize() + 
                        ", attempt to put robot in position x=" + x + 
                        " y=" + y);
            }
        } catch (RobotOutOfGridException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    public Direction getDirection() { return dir; }
    public Numbers getXCord() { return xCord; }
    public Numbers getYCord() { return yCord; }
    
    public String toString() {
        return dir.getSymbol() + " (" + xCord + "," + yCord + ")";
    }

    /**
     * Compares on xValue, yValue and direction. The result is the 
     * sum of the x and y value of "o", subtracted from this object 
     * x and y value. If this is equal, then the Direction.compareTo() 
     * will be used.
     * @param o
     * @return 0 if equal, less than 0 if smaller coordinate or greater
     * than 0 if bigger coordinate.
     */
    @Override
    public int compareTo(Position o) {
        int xValue = xCord.number - o.xCord.number;
        int yValue = yCord.number - o.yCord.number;
        int combineValue = xValue + yValue;
        
        if (combineValue == 0) {
            return dir.compareTo(o.dir);
        } else {
            return combineValue;
        }
    }

    /**
     * @param o object to be checked against
     * @see equals(Position)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            return equals((Position) o);
        } else {
            return false;
        }
    }



    /**
     * This method must be here because I HashMap.contains(), and it will 
     * not return true unless hashCode() in the elements in the map is 
     * overriden.
     * @return hash code for this object
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.dir != null ? this.dir.hashCode() : 0);
        hash = 89 * hash + (this.xCord != null ? this.xCord.hashCode() : 0);
        hash = 89 * hash + (this.yCord != null ? this.yCord.hashCode() : 0);
        return hash;
    }

    
    /**
     * @return true if compareTo(p) == 0
     */    
    public boolean equals(Position p) {
        if (compareTo(p) == 0) {
            return true;
        } else {
            return false;
        }
    }
    
}
