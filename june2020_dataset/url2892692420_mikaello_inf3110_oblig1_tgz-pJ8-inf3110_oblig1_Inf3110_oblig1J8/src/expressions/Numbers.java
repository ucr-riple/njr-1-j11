package expressions;

/**
 * This is an immutable class containing a single number and some 
 * methods to check what properties this number got. 
 * 
 * @author mikaello
 */
public class Numbers implements Comparable<Numbers> {
    public final int number;
    
    public Numbers(int number) {
        this.number = number;
    }
    
    public Numbers(Numbers n) {
        number = n.number;
    }
    
    @Override
    public String toString() { return "" + number; }

    /**
     * @return if this number is greater than zero
     */
    public boolean isPositive() {
        return number > 0;
    }
    
    @Override
    public int compareTo(Numbers n) {
        return number - n.number;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.number;
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Numbers) {
            return hashCode() == o.hashCode();
        } else {
            return false;
        }
    }
}
