package rpg.item;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of armors
 * Armors are a special type of itemimplementation, involving aditionally 
 * a maximum value and a protection
 * 
 * @author Mathias, Frederic
 * 
 * @invar Each armor has a valid maximum value
 * 	      | hasValidMaxValue()
 * @invar Each armor has a valid maximum protection
 * 	      | hasValidMaxProtection()
 * @invar Each armor must have a valid protection
 * 		  | hasValidProtection()
 *
 */
public class Armor extends ItemImplementation{
	/**
	 * Initialize this armor with the given id, weight and parent
	 * 
	 * @param  id
	 * 		   The id of this new armor
	 * @param  weight
	 * 		   The weight of this armor
	 * @param  maxValue
	 * 		   The maximum value of this armor
	 * @param  protection
	 *         The protection of this armor
	 * @param  maxProtection
	 * 		   The maximum protection factor of this item
	 * @pre    The given maximum value must be a valid maximum value.
	 *         | isValidMaxValue(value)
	 * @pre    The given maximum protection must be a valid maximum protection.
	 *         | isValidMaxProtection(maxProtection)
	 * @pre    The given protection must be a valid protection
	 *         | isValidProtection(protection)
	 * @post   The maximum value of this armor equals the given maximum value
	 * 		   | getMaxValue() == maxValue
	 * @post   The maximum protection of this armor equals the given maximum protection
	 * 		   | getMaxProtection() == maxProtection
	 * @effect The protection factor of this armor is set to the given protection
	 * 		   | setProtection(protection)
	 * @effect The id of this armor is set to the given id
	 * 		   | setId(id)
	 */
	@Raw
	public Armor(long id, Weight weight,
			int maxValue, int protection, int maxProtection) {
		
		super(weight);
				
		this.maxValue = maxValue;
		
		this.maxProtection = maxProtection;
		setProtection(protection);
		setId(id);
	}
	 /**
	  * Checks whether this armor can have the given id as its id
	  * 
	  * @param  id
	  *         The id to check.
	  * @return False if and only if there is a divisor of the id between 2
	  *         and the id minus one or if the id is less than or equal to 1.
	  *         | result == ( for each integer in 2..(id-1): id%integer == 0 )
	  *         |           || (id <= 1)
	  * @see    p.128 formal specification of for loops
	  */
	 @Raw
	 public boolean canHaveAsId(long id)
	 {
		 if(id <= 1)
			 return false;
		 for(int i = 2; i < id; i++)
			 if(id%i == 0)
				 return false;
		 return true;
	 }
	
	
	/**
	 * Set the id of this armor to the given id
	 * 
	 * @param id
	 * 		  The id to set
	 * @post  If this armor can have the given id as its id, 
	 * 		  the id of this armor equals the given id
	 *        | if(canHaveAsId(id)) then
	 *        |		getId() == id
	 *        Otherwise the id equals 2
	 *        | else
	 *        |		getId() == 2
	 */
	@Override @Model @Raw
	protected void setId(long id){
		if(canHaveAsId(id))
			this.id = id;
		else
			this.id = 2;
		
	}

	private final int maxValue;
	
	/**
	 * Return the maximum value of this armor
	 */
	@Basic @Raw
	public int getMaxValue() {
		return maxValue;
	}
	
	/**
	 * Check whether the given maximum value is valid
	 * 
	 * @param  value
	 * 		   The value to check
	 * @return True if and only if the given maximum value is positive
	 * 		   and less than or equal to 1000
	 * 		   | result == value>=0 && value<=1000
	 */
	public static boolean isValidMaxValue(int value){
		return value>=0 && value<=1000;
	}
	 
	/**
	  * Checks whether the maximum value of this armor is valid.
	  * 
	  * @return True if and only if the maximum value of this armor 
	  * 		is a valid maximum value.
	  *         | result == isValidMaxValue(getMaxValue())
	  */
	@Raw
	 public boolean hasValidMaxValue()
	 {
		return isValidMaxValue(getMaxValue()); 
	 }
	 
	 /**
	  * Return the value of this armor
	  * 
	  * @return The result is the product of the maximum value 
	  * 		of this armor and the quotient of the maximum protection factor
	  * 		and the actual protection factor
	  * 		| result == getMaxValue() * (getProtection()/getMaxProtection())
	  */
	 @Override
	 public int getValue(){
		return (int)((double)getMaxValue() * ((double)getProtection()/(double)getMaxProtection()));
		 
	 }
	 
	 private final int maxProtection;
	 
	 /**
	  * Return the maximum protection factor of this armor
	  */
	 @Basic @Raw
	 public int getMaxProtection() {
			return maxProtection;
	 }
	 
	 /**
	 * Check whether the given maximum protection is valid
	 * 
	 * @param  maxProtection
	 * 		   The maximum protection to check
	 * @return True if and only if the given maximum protection is 
	 * 		   greater than or equal to 1 and less than or equal to 100
	 * 		   | result == value>=1 && value<=100
	 */
	 @Raw
	public static boolean isValidMaxProtection(int maxProtection){
		return maxProtection>=1 && maxProtection<=100;
	}
	 
	/**
	  * Checks whether the maximum protection of this armor is valid.
	  * 
	  * @return True if and only if the maximum protection of this armor 
	  * 		is a valid maximum protection.
	  *         | result == isValidMaxProtection(getMaxProtection())
	  */
	 @Raw
	 public boolean hasValidMaxProtection()
	 {
		return isValidMaxProtection(getMaxProtection()); 
	 }
	 
	 private int protection;
	 
	 /**
	 * Return the protection factor of this armor
	 */
	 @Raw @Basic
	public int getProtection() {
		return protection;
	}

	/**
	 * Set the protection factor of this armor to the given protection
	 * 
	 * @param protection 
	 * 		  The protection to set
	 * @pre	  This armor can have the given protection as its protection
	 * 		  | canHaveAsProtection(protection)
	 * @post  The protection factor of this armor equals the given protection
	 * 		  | new.getProtection() == protection
	 */
	 @Raw
	public void setProtection(int protection) {
		this.protection = protection;
	}
	
	/**
	 * Check whether this armor can have the given protection as its protection
	 * 
	 * @param  protection
	 * 		   The protection to check
	 * @return True if and only if the given protection is positive
	 * 		   and less than or equal to the maximum protection
	 * 		   | result == ( ( protection >= 0) && (protection <= getMaxProtection() ) )
	 */
	 @Raw
	public boolean canHaveAsProtection(int protection){
		return protection >= 0 && protection <= getMaxProtection();
	}
	
	/**
	 * Check whether this armor can has a valid protection
	 * 
	 * @return True if and only if this armor can have its protection as its protection
	 * 		   | result == canHaveAsProtection(getProtection())
	 */
	 @Raw
	public boolean hasValidProtection(){
		return canHaveAsProtection(getProtection());
	}
}
