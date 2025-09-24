package expressions;

/**
 * Possible boolean operators used by the class BooleanExp.
 * 
 * @author mikaello
 */
public enum Booleans {
    greaterThan(">"),
    lessThan("<"),
    equal("=");
    
    public final String symbol;
    
    Booleans(String symbol) {
        this.symbol = symbol;
    }
    
    /**
     * @return the Boolean corresponding to symbol
     */
    public Booleans getBooleans(String symbol) {
        for (Booleans b : values()) {
            if (b.symbol.equals(symbol)) {
                return b;
            }
        }
            
        
        return null;
        
    }
    
    @Override
    public String toString() { return symbol; }
}
