package util;

/**
 * Enums of all possible directions in the language ROBOL. Possible directions
 * include -x, x, -y and y. 
 * 
 * @author mikaello
 */
public enum Direction {
    minusX("-x", "<"),
    x("x", ">"), 
    minusY("-y", "v"),
    y("y", "^"), 
    standby(".", "."); // Used when printing grid with only dots
    
    public final String symbol, representation;
    
    Direction(String symbol, String representation) {
        this.symbol = symbol;
        this.representation = representation;
    }
    
    /**
     * Gets the direction corresponding to the string given as parameter. 
     * Returns null if not found.
     * 
     * @param s text representation of direction (e.g. -x, x, ...)
     * @return Direction enum corresponding to parameter
     */
    public Direction getDirection(String s) {
        for (Direction d : values()) {
            if (d.symbol.equals(s)) {
                return d;
            }
        }
        
        return null;
    }
    
    public String toString() { return representation; }
    public String getSymbol() { return symbol; }
}
