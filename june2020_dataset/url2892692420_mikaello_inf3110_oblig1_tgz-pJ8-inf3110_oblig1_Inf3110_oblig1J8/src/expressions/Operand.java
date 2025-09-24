package expressions;

/**
 * Enums representing operands used by the class PlusExp.
 * 
 * @author mikaello
 */
public enum Operand {
    plus("+"),
    minus("-"),
    times("*");
    
    public final String symbol;
    
    Operand(String symbol) {
        this.symbol = symbol;
    }
    
    /**
     * @return the Operand corresponding to the symbol
     */
    public Operand getOperand(String symbol) {
        for (Operand o : values()) {
            if (o.symbol.equals(symbol)) {
                return o;
            }
        }
        
        return null;
    }
    
    @Override
    public String toString() { return symbol; }
}
