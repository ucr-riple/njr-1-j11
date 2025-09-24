package rpg.item;

import be.kuleuven.cs.som.annotate.*;

/**
 * An interface of items involving a weight, a value and an identification
 * 
 * @author Mathias
 * 
 * @invar Each Item has a valid id.
 *        | hasValidId()
 * @invar Each Item has an effective weight.
 *        | getWeight() != null 
 * @invar Each Item has a valid value.
 *        | hasValidValue()
 */
public interface Item {
	
	
	/**
	 * Return the identification of this item
	 */
	@Basic @Raw
	public long getId();
	
	/**
	 * Checks whether or not this item can have the given id as its id.
	 * 
	 * @return False if the given id is negative.
	 *         | if( id < 0 ) then
	 *         |    result == false
	 */
	@Raw
	public boolean canHaveAsId(long id);
	/**
	 * Checks whether or not this item has a valid id.
	 * 
	 * @return True if and only if this item has a valid id.
	 *         | result == canHaveAsId( getId() )
	 */
	@Raw
	public boolean hasValidId();
	
	/**
	 * Return the value of this item
	 */
	@Basic
	public int getValue();
	
	 /**
	  * Checks whether the given value is a valid value for this weapon.
	  * 
	  * @return False if the given value is negative
	  *         | if(value < 0) then
	  *         |	result == false
	  */
	@Raw
	 public boolean canHaveAsValue(int value);
	/**
	 * Checks whether this item has a valid value.
	 * 
	 * @return True if and only if this item can have its value as its value.
	 *         | result == canHaveAsValue( getValue() )
	 */
	@Raw
	public boolean hasValidValue();
	
	/**
	 * Return the weight of this item
	 */
	@Basic @Immutable
	public Weight getWeight();
	
}
