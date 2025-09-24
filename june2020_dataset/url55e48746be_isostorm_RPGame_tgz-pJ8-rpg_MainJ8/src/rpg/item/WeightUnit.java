package rpg.item;

import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;


@Value
public enum WeightUnit {
	KG, G, POUND;
	
	/**
	 * Initializes this weight unit.
	 */
	@Raw
	private WeightUnit(){}
	
	/**
	 * Return the value of one 1 unit of this weight unit 
	 * in the other weight unit.
	 * @param other
	 *        The weight unit to convert to.
	 * @return If the other weight unit is not effective return 0.
	 *         | if ( other == null ) then
	 *         |     result == 0
	 * @return If this weight unit is equal to the given unit the result is 1.
	 *         | if ( this == other ) then
	 *         |     result == 1
	 * @return If the conversion rate isn't registered then the resulting 
	 *         conversion rate is the inverse of the conversion rate from 
	 *         the other weight unit to this weight unit.
	 *         | if( conversionRates[this.ordinal()][other.ordinal()] == 0.0 ) then
	 *         |    result == 1/conversionRates[other.ordinal()][this.ordinal()];
	 * @return If the conversion rate is registered then the result is the 
	 *         registered conversion rate.
	 *         | result == conversionRates[this.ordinal()][other.ordinal()];
	 *         
	 *         
	 * @see p.294
	 */
	public double toUnit(WeightUnit other)
	{
		if(other == null)
			return 0;
		if(conversionRates[this.ordinal()][other.ordinal()] == 0.0)
			conversionRates[this.ordinal()][other.ordinal()] = 1/conversionRates[other.ordinal()][this.ordinal()];
		return conversionRates[this.ordinal()][other.ordinal()];
	}
	private static double[][] conversionRates = new double[3][3];
	static {
		conversionRates[KG.ordinal()][KG.ordinal()] = 1;
		conversionRates[G.ordinal()][G.ordinal()] = 1;
		conversionRates[POUND.ordinal()][POUND.ordinal()] = 1;
		conversionRates[KG.ordinal()][G.ordinal()] = 1000.0;
		conversionRates[KG.ordinal()][POUND.ordinal()] =  2.20462262;
		conversionRates[G.ordinal()][POUND.ordinal()] = 0.00220462262;
	}
}
