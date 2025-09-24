package rpg.item;

import be.kuleuven.cs.som.annotate.*;
/**
 * A class of dukats involving an id, value and weight.
 * 
 * @author Mathias, Frederic
 *
 */
public class Dukat implements Item{

	
	/**
	 * Return the id of this dukat, which is always 0
	 */
	@Override @Basic @Immutable @Raw
	public long getId() {
		return 0;
	}
	
	/**
	 * Return the value of this dukat, which is always 1
	 */
	@Override @Basic @Immutable @Raw
	public int getValue() {
		return 1;
	}
	
	private static final Weight WEIGHT = new Weight(50, WeightUnit.G);
	
	/**
	 * Return the weight of this dukat which is always 50 gram
	 */
	@Override @Basic @Immutable @Raw
	public Weight getWeight() {
		return WEIGHT;
	}
	
	/**
	 * Checks whether this dukat can have the given id as its id.
	 * 
	 * @return True if and only if the given id is equal to 0.
	 *         | result == ( id == 0 )
	 */
	@Override @Raw
	public boolean canHaveAsId(long id) {
		return id == 0;
	}
	
	/**
	 * Checks whether this dukat has a valid id.
	 * 
	 * @return True if and only if this dukat can have its id as its id.
	 *         | result == canHaveAsId( getId() )
	 * 
	 */
	@Override @Raw
	public boolean hasValidId() {
		return canHaveAsId(getId());
	}
	/**
	 * Check whether this dukat has a valid value
	 * 
	 * @return True if and only if the given id is equal to 1
	 * 		   | result == (value == 1)
	 */
	@Override @Raw
	public boolean canHaveAsValue(int value) {
		return value == 1 ;
	}
	
	/**
	 * Checks whether this dukat has a valid value.
	 * 
	 * @return True if and only if this dukat can have its value as its value.
	 *         | result == canHaveAsValue( getValue() )
	 */
	@Override @Raw
	public boolean hasValidValue() {
		return false;
	}

	
}
