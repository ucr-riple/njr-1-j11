package statements;

import expressions.Numbers;
import interfaces.Handler;

/**
 * Size of the grid. Holds to Numbers, one for x-axis and one
 * for y-axis.
 * 
 * @author mikaello
 */
public class Size extends Stmt implements Handler {
    private final Numbers x_bound_global;
    private final Numbers y_bound_global;

    @Override
        public void interpret() {
	// write the interpret logic here if there is any
    }

    public Size(Size s) {
        x_bound_global = new Numbers(s.x_bound_global);
        y_bound_global = new Numbers(s.y_bound_global);
    }
    
    public Size(Numbers x_bound, Numbers y_bound) {
        x_bound_global = x_bound;
        y_bound_global = y_bound;
    }
    
    public int getXBound() { return x_bound_global.number; }
    public int getYBound() { return y_bound_global.number; }

    /**
     * @return true if the coordinates stored in this class is bigger
     * than the coordinates given as parameter (x is compared to 
     * x_bound_global and y to y_bound_global)
     */
    public boolean isBiggerThanOrEqual(Numbers x, Numbers y) {
        return x.number <= x_bound_global.number &&
                y.number <= y_bound_global.number;
    }
    
    public String toString() { 
        return "size(" + x_bound_global + " * " + y_bound_global + ")";
    }
}