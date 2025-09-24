package rpg.item;

import be.kuleuven.cs.som.annotate.*;
import rpg.item.WeightUnit;


/**
 * A class of weights involving a numeral and a weight unit.
 * @invar  The numeral of each weight must be a valid numeral.
 *         | isValidNumeral(getNumeral())
 * @invar  The unit of each weight must be a valid unit.
 *         | isValidUnit(getUnit())
 * @author Mathias, Frederic
 *
 */
public class Weight implements Comparable<Weight> {
	
	/**
	 * Initialize a new weight with the given numeral and unit
	 * 
	 * @param numeral
	 *        The numeral 
	 * @param unit
	 *        The unit
	 * @post  The numeral of this new weight equals the given numeral
	 * 		  | new.getNumeral() == numeral
	 * @post  The unit of this new weight equals the given weight
	 * 		  | new.getUnit() == unit
	 */
	@Raw
	public Weight(double numeral, WeightUnit unit)
	{
		this.numeral = numeral;
		this.unit = unit;
	}
	private final double numeral;
	private final WeightUnit unit;
	/**
	 * Compare this weight with the other weight taking the weight units into consideration.
	 * @param  other
	 *         The other weight to compare with.
	 * @return The result is equal to the comparison of the numeral of this weight
	 *         with the numeral of the other weight converted to this weight unit.
	 *         | let
	 *         |   otherConverted = other.toUnit(getUnit())
	 *         | in
	 *         |   getNumeral().compareTo(otherConverted.getNumeral())
	 * @return The result is equal to 0 if the other weight is not effective.
	 *         | if ( other == null )
	 *         |     result == 0
	 */
	@Override
	public int compareTo(Weight other) {
		if(other==null)
			return 0;
		Weight otherConverted = other.toUnit(getUnit());
		return new Double(getNumeral()).compareTo(otherConverted.getNumeral());
	}
	
	/**
	 * Check whether the given unit is a valid unit for any weight.
	 * 
	 * @param  weightUnit
	 *         The weightUnit to check.
	 * @return True if and only if the given weightUnit is effective.
	 *         | result == ( weightUnit != null )
	 */
	public static boolean isValidUnit(WeightUnit weightUnit)
	{
		return weightUnit != null;
	}
	/**
	 * Check whether the given numeral is a valid numeral for any weight.
	 * @param  numeral
	 *         The numeral to check.
	 * @return True if and only if the numeral is greater than or equal to 0.
	 *         | result == ( numeral >= 0 )
	 */
	public static boolean isValidNumeral(double numeral)
	{
		return numeral >= 0;
	}
	/**
	 * Return a weight that has the same value as this weight expressed in the given weight unit.
	 * 
	 * @param  unit
	 *         The unit to convert to.
	 * @return The resulting weight has the given unit as its unit.
	 *         | result.getUnit() == unit
	 * @return The numeral of the resulting weight is equal to the numeral of this weight 
	 *         multiplied with the conversion rate from the unit of this weight to the given unit.
	 *         | result.getNumeral() == this.getUnit().toUnit(unit)*this.getNumeral()
	 */
	public Weight toUnit(WeightUnit unit)
	{
		if(unit == null)
			return null;
		if(this.getUnit() == unit)
			return this;
		double conversionRate = this.getUnit().toUnit(unit);
		return new Weight(getNumeral()*conversionRate, unit);
	}

	/**
	 * Return the numeral of this weight.
	 */
	@Basic @Raw
	public double getNumeral() {
		return numeral;
	}
	
	/**
	 * Return the weight unit of this weight.
	 */
	@Basic @Raw
	public WeightUnit getUnit() {
		return unit;
	}
	
	/**
	 * Returns the sum of this weight and another weight, 
	 * taking weight units into consideration.
	 * @param other
	 *        The other weight to add to this weight.
	 * @return The resulting weight has this weight unit as its weight unit.
	 *         | result.getUnit() == getUnit()
	 * @return The numeral of the resulting weight is the sum of this numeral
	 *         and the numeral of the other weight converted to this weight unit.
	 *         | result.getNumeral == other.toUnit(getUnit()).getNumeral() + getNumeral()
	 * @return If the other weight is not effective this weight is returned.
	 *         | result == this
	 */
	public Weight add(Weight other)
	{
		if(other == null)
			return this;
		return new Weight(other.toUnit(getUnit()).getNumeral() + getNumeral(), getUnit());
	}
	
	/**
	 * Returns the subtraction of this weight and another weight, 
	 * taking weight units into consideration.
	 * 
	 * @param  other
	 *         The other weight to subtract from this weight.
	 * @return The resulting weight has this weight unit as its weight unit.
	 *         | result.getUnit() == getUnit()
	 * @return If the subtraction of this numeral and the numeral of the other weight converted to 
	 * 		   this weight unit is positive, the numeral of the resulting weight equals this subtraction.
	 *         | if(other.toUnit(getUnit()).getNumeral() - getNumeral() > 0) then
	 *         |      new.getNumeral() == - getNumeral() - other.toUnit(getUnit()).getNumeral()
	 *         Otherwise the resulting numeral is equal to 0
	 *         | new.getNumeral() == 0         
	 * @return If the other weight is not effective this weight is returned.
	 *         | result == this
	 */
	public Weight subtract(Weight other)
	{
		if(other == null)
			return this;
		double newNumeral = getNumeral() - other.toUnit(getUnit()).getNumeral();
		if(newNumeral > 0)
			return new Weight(newNumeral, getUnit());
		else
			return new Weight(0, getUnit());
	}
	
	/**
	 * Returns this weight multiplied with the given multiplier.
	 * 
	 * @param  multiplier
	 *         The number to multiply this weight with.
	 * @return The resulting weight has this weight unit as its weight unit.
	 *         | result.getUnit() == getUnit()
	 * @return The numeral of the resulting weight is the numeral of this
	 *         weight multiplied with the given multiplier.
	 *         | result.getNumeral() = getNumeral()*multiplier
	 */
	public Weight multiply(double multiplier)
	{
		return new Weight(getNumeral()*multiplier, getUnit());
	}
	
	/**
	 * Returns the hashcode for this weight.
	 * 
	 * @see p.290
	 */
	@Override
	public int hashCode() {
		return new Double(getNumeral()).hashCode() + getUnit().hashCode();
	}
	
	/**
	 * Returns the textual representation of this weight.
	 *
	 * @return A string consisting of the textual representation of the numeral
	 *         followed by the textual representation of this weight unit separated by a space.
	 *         | result.equals( getNumeral() + " " + getUnit().toString() )
	 */
	@Override
	public String toString() {
		return getNumeral() + " " + getUnit().toString();
	}
	
	/**
	 * Check whether this weight is equal to the given object.
	 * 
	 * @return True if and only if the given object is effective,
	 *         if this weight and the given object belong to the
	 *         same class and if this weight and other object 
	 *         interpreted as a weight have equal numerals and equal weight units.
	 *         | result == ( (other != null) 
	 *                       && (this.getClass() == other.getClass()) 
	 *                       && this.getNumeral() == ((Weight)other).getNumeral() 
	 *                       && this.getUnit() == ((Weight)other).getUnit() )
	 *
	 *@see p.289 equality
	 */
	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if( this.getClass() != other.getClass())
			return false;
		Weight otherWeight = (Weight)other;
		return ( this.getNumeral() == otherWeight.getNumeral() && this.getUnit() == otherWeight.getUnit());
	}
	
}
