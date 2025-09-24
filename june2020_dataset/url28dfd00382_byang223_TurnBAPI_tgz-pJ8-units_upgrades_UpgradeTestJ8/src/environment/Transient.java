package environment;

import achiever.Achiever;
import attribute.Attribute;
import attribute.AttributeTurn;

/**
 * Type of environment that is not permanently on the map
 * @author lenajia
 * 
 */
public abstract class Transient extends Environment implements Achiever, java.io.Serializable {
	
	protected int myStartTurn;
	protected int myEndTurn;
	
	AttributeTurn turns;
	
	 /**
     * Constructor for transient environments. 
     * Sets the image-to-tile ratio.
     * @param ratio
     */
	public Transient(double ratio, int start, int end) {
	    super(ratio);
	    turns = new AttributeTurn(this);
	    myStartTurn = start;
	    myEndTurn = end;
	    if (myStartTurn > 1) {
	        this.setActive(false);
	    }
	    
    }
	

	/**Sets active if the current turn is within the active turn bounds
	 * 
	 * @param turn
	 * @return boolean
	 */
	public boolean isActive(int turn){
		if (turn >= myStartTurn && turn <= myEndTurn){
			this.setActive(true);
			return true;
		}
		this.setActive(false);
		return false;
	}
	
	@Override
	public Attribute getAttribute(String name) {
	    if (name.equals(Attribute.TURN)) {
            return turns;	        
	    }
	    else return null;
	}
	
	@Override
	public void modify() {}
	
	@Override
	public void addAttribute(Attribute attribute) {};
	
	@Override
	public void removeAttribute(Attribute attribute) {};
	
}
