package rpg.item;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Weapon is a special type of ItemImplementation involving additionally a damage
 * @author Mathias, Frederic
 *
 * @invar The maximum damage for all weapons is a valid maximum damage.
 *        | hasValidMaxDamage()
 * @invar The damage of this weapon is a valid damage.
 *        | hasValidDamage()
 * @invar The damage value factor of this weapon is strictly positive.
 *        | getDamageValueFactor() > 0
 */
public class Weapon extends ItemImplementation {

	/**
	 * Initializes this weapon with the given weight, parent, damage and value.
	 * @param  weight
	 * 		   The weight of this weapon
	 * @param  value
	 *         The value of this weapon.
	 * @param  damage
	 *         The damage of this weapon.
	 * @effect A new container is initialized with 
	 * 		   the generated id and the given weight and value
	 * 		   | super(generateId(), weight, value)
	 * @effect The id is shifted.
	 *         | shiftId()
	 * @effect The damage of this weapon is set to the given damage.
	 *         | setDamage(damage)
	 */
	@Raw
	public Weapon(Weight weight, int damage, int value) {
		super(generateId(), weight, value);
		setDamage(damage);
		shiftId();
	}

	/**
	 * Initializes this weapon with the given weight and damage.
	 * 
	 * @param  weight
	 * 		   The weight of this weapon
	 * @param  damage
	 *         The damage of this weapon.
	 * @effect A new weapon is initialized with the given weight and damage and 1 as its value.
	 *         | this(weight, damage, 1)
	 */
	@Raw
	public Weapon(Weight weight, int damage)
	{
		this(weight, damage, 1);
	}
	private static long lastId = 0;
	
	/**
	 * Returns the last id.
	 */
	@Model @Basic
	private static long getLastId()
	{
		return lastId;
	}
	
	/**
	 * Generates the next id larger than the last id.
	 * 
	 * @return The first id that is greater than the last id
	 *         and is a multiple of 3 and a multiple of 2.
	 *         | (result % 2 == 0)
	 *         | && (result % 3 == 0)
	 *         | && (result > getLastId())
	 */
	@Model
	private static long generateId()
	{
		long nextId = lastId + 3;
		while(nextId%2 != 0 || nextId%3 !=0)
			nextId += 3;
		return nextId;
	}
	
	/**
	 * Sets the last id to the id of this weapon.
	 * 
	 * @post The last id is equal to the id of this weapon.
	 *       | getLastId() == getId()
	 */
	@Model @Raw
	private void shiftId()
	{
		lastId = getId();
	}
	
	private static int maxDamage = 100;
	
	/**
	 * Returns the maximum damage of all weapons.
	 */
	@Basic
	public static int getMaxDamage()
	{
		return maxDamage;
	}
	
	/**
	 * Sets the maximum damage of all weapons.
	 * 
	 * @param maxDamage
	 *        The maximum damage to be set
	 * @pre   The given maximum damage must be a valid maximum damage.
	 *        | isValidMaxDamage( maxDamage )
	 * @post  The maximum damage for all weapons is equal to the given maximum damage.
	 *        | getMaxDamage() == maxDamage
	 */
	public static void setMaxDamage(int maxDamage)
	{
		Weapon.maxDamage = maxDamage;
	}
	
	/**
	 * Checks whether the given maximum damage is a valid maximum damage.
	 * 
	 * @param maxDamage
	 *        The maximum damage to check
	 * @return True if and only if the given maximum damage is greater then
	 *         or equal to 1.
	 *         | result == ( maxDamage >= 1 )
	 */
	public static boolean isValidMaxDamage(int maxDamage)
	{
		return maxDamage >= 1;
	}
	
	/**
	 * Checks whether the maximum damage is valid.
	 * 
	 * @return True if and only if the maximum damage is a valid maximum damage.
	 *         | result == isValidMaxDamage( getMaxDamage() )
	 */
	@Raw
	public boolean hasValidMaxDamage()
	{
		return isValidMaxDamage(getMaxDamage());
	}
	
	private int damage;
	
	/**
	 * Returns the damage of this weapon.
	 */
	@Basic @Raw
	public int getDamage()
	{
		return damage;
	}
	
	/**
	 * Sets the damage of this weapon.
	 * @param damage
	 *        The damage to be set
	 * @pre   The given damage must be a valid damage.
	 *        | isValidDamage(damage)
	 * @post  The damage of this weapon is equal to the given damage.
	 *        | getDamage() == damage
	 */
	@Raw
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	/**
	 * Checks whether the given damage is valid.
	 * 
	 * @param damage
	 *        The damage to check
	 * @return True if and only if the given damage is a multiple of 7
	 *         and the given damage is less than or equal to the maximum damage
	 *         and the given damage is more than or equal to 1.
	 *         | result == ((damage % 7 == 0)
	 *                      && (damage <= getMaxDamage())
	 *                      && (damage >= 1))
	 */
	 public static boolean isValidDamage(int damage)
	 {
		 return (damage % 7 == 0) && (damage <= getMaxDamage()) && (damage >= 1);
	 }
	 
	 /**
	  * Checks whether the damage of this weapon is valid.
	  * 
	  * @return True if and only if the damage of this weapon is a valid damage.
	  *         | result == isValidDamage(getDamage())
	  */
	 @Raw
	 public boolean hasValidDamage()
	 {
		return isValidDamage(getDamage()); 
	 }
	 
	 private static double damageValueFactor = 2d;
	 
	 /**
	  * Sets the damage value factor to the given factor.
	  * 
	  * @param factor
	  *        The factor to set.
	  * @pre   The given factor must be strictly positive.
	  *        | getDamageValueFactor() > 0
	  */
	 public static void setDamageValueFactor(double factor)
	 {
		 damageValueFactor = factor;
	 }
	 
	 /**
	  * Returns the damage value factor of all weapons.
	  */
	 @Basic
	 public static double getDamageValueFactor()
	 {
		 return damageValueFactor;
	 }
	 
	 private static boolean useFormula = true;
	 
	 /**
	  * Sets whether or not the value is dependent of the damage.
	  * 
	  * @param useFormula
	  *        Whether or to use the formula or not.
	  * @post  useFormula is equal to the given useFormula.
	  *        | getUseFormula() == useFormula
	  */
	 public static void setUseFormula(boolean useFormula)
	 {
		 Weapon.useFormula = useFormula;
	 }
	 
	 /**
	  * Returns whether or not to use a formula when calculating the value of weapons.
	  */
	 @Basic
	 public static boolean getUseFormula()
	 {
		 return Weapon.useFormula;
	 }
	 /**
	  * @return If use formula is true the value is equal to the product of
	  *         the damage of this weapon and the damage value factor.
	  *         | if(getUseFormula())
	  *         |    result == ( getDamageValueFactor()*getDamage() )
	  *         Otherwise the result is the value of this weapon.
	  *         | else then
	  *         |    result == super.getValue()
	  * 
	  */
	 public int getValue()
	 {
		 if(getUseFormula())
			 return (int)getDamageValueFactor()*getDamage();
		 return super.getValue();
	 }
	 
	 /**
	  * Checks whether the given value is a valid value for this weapon.
	  * 
	  * @return True if and only if the given value is between 1 and 200
	  *         | result == ( (value >= 1) && (value <= 200) )
	  */
	 @Override @Raw
	 public boolean canHaveAsValue(int value)
	 {
		 return (value >= 1 && value <= 200);
	 }
	
	 /**
	 * Checks whether this weapon has a valid value.
	 * 
	 * @return True if and only if this weapon can have its value as its value.
	 *         | result == canHaveAsValue( getValue() )
	 */
	 @Override @Raw
	 public boolean hasValidValue()
	 {
		return canHaveAsValue(getValue());
	 }
}
