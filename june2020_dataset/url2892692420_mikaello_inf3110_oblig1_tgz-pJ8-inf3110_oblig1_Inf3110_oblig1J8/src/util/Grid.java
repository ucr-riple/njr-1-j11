package util;

import expressions.Numbers;
import interfaces.Handler;
import java.util.HashMap;
import log.Log;
import statements.Size;

/**
 * This class prints the grid. It contains data about how big it is, 
 * but no other info is stored here.
 * @author mikaello
 */
public class Grid implements Handler {
    /** Size of this map */
    private Size sizeGlobal = new Size(new Numbers(0), new Numbers(0));
    
    public Grid() {
    }
    
    public Grid(Size sizeIns) {
        Log.writeLogLine(sizeIns.toString());
        setSizeGlobal(sizeIns);
    }
    
    
    @Override
    public void interpret() {
    }
    
    /**
     * @see printGrid(int, int, String)
     */
    public void printGrid() {
        printGrid(new Position(new Numbers(1), new Numbers(1)));
    }
    
    /**
     * @see printGrid(int, int, String)
     */
    public void printGrid(Position p) {
        HashMap<Position, Direction> pList = new HashMap<Position, Direction>();
        
        // Must make new Position to get a Direction to be standby 
        // without altering the original Position p
        pList.put(new Position(new Numbers(p.getXCord()), 
                new Numbers(p.getYCord())), p.getDirection());
        
        printGrid(pList, p);
    }
    
    public void printGrid(Numbers x, Numbers y, Direction symbol) {      
        printGrid(new Position(x, y, symbol));
    }
    
    /**
     * Makes a grid of dots. At the point (x,y) it will print the
     * symbol argument instead of dots.
     *
     * @param x x coordinate (starts from 1)
     * @param y y coordinate (starts from 1)
     * @param symbol symbol of robot to be filled
     */
    public void printGrid(HashMap<Position, Direction> positionMap, Position lastPos) {
        // Top frame 
        for (int i = 0; i < sizeGlobal.getXBound() +2; i++) {
            if (i == 0) {
                System.out.print("\n    #");
            } else {
                System.out.print(" #");
            }
        }
        
        System.out.println();

        // Main grid 
        for (int i = 0; i < sizeGlobal.getYBound(); i++) {
            // This for-loop defines the y-axis
            
            // Numbers and frame on the left hand side
            if (sizeGlobal.getYBound() - i < 10) {
                System.out.print(sizeGlobal.getYBound() - i + "   #");
            } else if (sizeGlobal.getYBound() - i < 100) {
                System.out.print(sizeGlobal.getYBound() - i + "  #");
            } else if (sizeGlobal.getYBound() - i < 1000) {
                System.out.print(sizeGlobal.getYBound() - i + " #");
            }
            
            for (int j = 0; j < sizeGlobal.getXBound(); j++) {
                // This for-loop defines the x-axis
                
                // Position instance used for checking the HashMap
                Position p = new Position(new Numbers(j+1), 
                        new Numbers(sizeGlobal.getYBound()-i));

                if (positionMap.containsKey(p)) {
                    // Position exists in HashMap
                    System.out.print(" " + positionMap.get(p));
                    
                } else if (i == sizeGlobal.getYBound() - lastPos.getYCord().number && 
                        j+1 == lastPos.getXCord().number) {
                    // Position is p (no pen)
                    System.out.print(" " + lastPos.getDirection());
                } else {
                    // Position is not a symbl, print standard
                    System.out.print(" .");
                }
            }
            System.out.println(" #"); // Right frame
        }
        
        // Bottom frame
        for (int i = 0; i < sizeGlobal.getXBound() + 2; i++) {
            if (i == 0) {
                System.out.print("    #");
            } else {
                System.out.print(" #");
            }
        }
        
        // Bottom numbers (x-axis)
        System.out.print("\n      ");
        for (int i = 1; i < sizeGlobal.getXBound()+1; i++) {
            if (i < 10) {
                System.out.print(i + " ");
            } else {
                System.out.print(i);
            }
        }
        
        System.out.println();
        
        // Information about current position and direction
        if (lastPos.getDirection() != Direction.standby) {
            System.out.printf("Robot is now in position (%s, %s), heading ", 
                    lastPos.getXCord(),lastPos.getYCord());
            switch(lastPos.getDirection()) {
                case minusX:
                    System.out.printf("left (%s)\n", Direction.minusX.symbol);
                    break;
                case x:
                    System.out.printf("right (%s)\n", Direction.x.symbol);
                    break;
                case minusY:
                    System.out.printf("down (%s)\n", Direction.minusY.symbol);
                    break;
                case y:
                    System.out.printf("up (%s)\n", Direction.y.symbol);
                    break;
            }
        }
    }
    
    /**
     * Checks if a coordinate is inside the grid. That means that 
     * both numbers are positive (x > 0 && y > 0) and that they are 
     * less than size (x < size.getXBound() && y < size.getXBound())
     * @param x x coordinate to be checked
     * @param y y coordinate to be checked
     * @return true if the (x,y) coordinate is inside the grid
     */
    public boolean isInsideGrid(Numbers x, Numbers y) {
        return sizeGlobal.isBiggerThanOrEqual(x, y) && 
                x.isPositive() &&
                y.isPositive();
    }
    
    private void setSizeGlobal(Size sizeIns) {
        sizeGlobal = sizeIns;
    }

    public Size getSize() {
        return new Size(sizeGlobal);
    }
    
}