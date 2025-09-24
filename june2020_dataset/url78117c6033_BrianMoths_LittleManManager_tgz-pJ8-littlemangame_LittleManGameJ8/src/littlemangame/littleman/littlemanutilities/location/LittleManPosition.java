/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littleman.littlemanutilities.location;

import java.awt.Point;
import littlemangame.littleman.PositionGetterAdapter;

/**
 * This class is used to represent the position of the little man.
 *
 * @author brian
 */
public class LittleManPosition {

    private final int pathY;
    private final int stepSize;
    private final PositionGetterAdapter positionGetterAdapter;
    private int x;
    private int y;

    /**
     * constructs the little man position with the given path values
     *
     * @param pathY the y coordinate that the little man will first attain when
     * walking between locations with different x coordinates. This will ensure
     * that he never bumps into anything in the office.
     * @param stepSize how many pixels the little man will move in a frame
     * @param initialPoint the initial position of the little man
     * @param positionGetterAdapter a component responsible for telling the
     * position of each {@link OfficeLocation}
     */
    public LittleManPosition(int pathY, int stepSize, Point initialPoint, PositionGetterAdapter positionGetterAdapter) {
        this.pathY = pathY;
        this.stepSize = stepSize;
        x = initialPoint.x;
        y = initialPoint.y;
        this.positionGetterAdapter = positionGetterAdapter;
    }

    /**
     * moves the little man closer to the {@link OfficeLocation} by one step.
     * Returns a boolean indicating whether or not the little man's journey is
     * complete.
     *
     * @param officeLocation the location the little man is to move to
     *
     * @return whether or not the little man arrived at the given computer
     * location after this call
     */
    public boolean goTo(OfficeLocation officeLocation) {
        return officeLocation.goTo(this);
    }

    public boolean isAtLocation(OfficeLocation officeLocation) {
        return officeLocation.isHere(this);
    }

    boolean goToPoint(Point point) {
        if (isAtX(point.x)) {
            return stepInDirectionOfY(point.y);
        } else {
            goToX(point.x);
            return false;
        }
    }

    private boolean goToX(int xDestination) {
        if (x != xDestination) {
            if (!isOnPath()) {
                goToPath();
                return false;
            } else {
                return stepInDirectionOfX(xDestination);
            }
        } else {
            return true;
        }
    }

    private boolean goToPath() {
        return stepInDirectionOfY(pathY);
    }

    private boolean stepInDirectionOfX(int xDestination) {
        if (x < xDestination) {
            x += Math.min(stepSize, xDestination - x);
        } else if (x > xDestination) {
            x -= Math.min(x - xDestination, stepSize);
        }
        return x == xDestination;
    }

    private boolean stepInDirectionOfY(int yDestination) {
        if (y < yDestination) {
            y += Math.min(stepSize, yDestination - y);
        } else if (y > yDestination) {
            y -= Math.min(y - yDestination, stepSize);
        }
        return y == yDestination;
    }

    private boolean isOnPath() {
        return isAtY(pathY);
    }

    private boolean isAtX(int x) {
        return this.x == x;
    }

    private boolean isAtY(int y) {
        return this.y == y;
    }

    boolean isAtPoint(Point point) {
        return isAtX(point.x) && isAtY(point.y);
    }

    private int getPathY() {
        return pathY;
    }

    private int getStepSize() {
        return stepSize;
    }

    /**
     * returns the x position of this little man position
     *
     * @return the x position of this little man position
     */
    public int getX() {
        return x;
    }

    /**
     * returns the y position of this little man position
     *
     * @return the y position of this little man position
     */
    public int getY() {
        return y;
    }

    PositionGetterAdapter getPositionGetterAdapter() {
        return positionGetterAdapter;
    }

}
