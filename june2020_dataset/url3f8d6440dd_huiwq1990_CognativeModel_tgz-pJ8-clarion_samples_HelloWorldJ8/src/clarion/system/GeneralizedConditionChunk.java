package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements a generalized condition chunk within CLARION. It extends the DimensionValueCollection class and
 * is most often used within an AbstractRule.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A condition usually contains the same dimension-value pairs as the input nodes of an implicit module (or a subset thereof)
 * and generally has the same structure as the bottom level to which it is related (or from which it was extracted).
 * <p>
 * Values within a dimension in a condition are OR linked; whereas dimensions themselves are AND linked. This means that
 * multiple values within the same dimension can have activations greater than 0. This is different than the sensory input
 * (or a dimension-value collection in general), which can only have at most 1 activated value for any dimension.
 * <p>
 * It is not required that a rule have a condition. However, only 1 condition-less rule is allowed for each action. 
 * This is only the case for a rule that does not have a condition.
 * <p>
 * Note that while we refer to the condition of a rule as a "chunk", it is not the same as a chunk as defined
 * by the CLARION theory (as well as the AbstractChunk class within this library). The condition chunk is
 * a specialized kind of chunk in that it does not have an ID, and it has special methods for calculating its
 * strength when compared to another dimension-value collection (usually the current input).
 * @version 6.0.6
 * @author Nick Wilson
 */
public class GeneralizedConditionChunk extends DimensionValueCollection{
	private static final long serialVersionUID = 5496821003835063531L;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**
	 * Initializes a condition.
	 */
	public GeneralizedConditionChunk ()
	{
		super();
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes a condition with the collection of dimensions specified.
	 * @param dims The dimensions for the collection.
	 */
	public GeneralizedConditionChunk (Collection <? extends Dimension> dims)
	{
		super(dims);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the condition with the map of dimensions.
	 * @param map The map of dimensions for the collection.
	 */
	public GeneralizedConditionChunk (Map <? extends Object, ? extends Dimension> map)
	{
		super(map);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Gets the weight of the condition. By default this is 1/n where n is the number of dimensions.
	 * @return The weight of the weight
	 */
	public double getWeight()
	{
		return 1/(double)size();
	}
	
	/**
	 * Gets the strength of the condition based on the current input.
	 * @param CurrentInput The current input.
	 * @return The strength of the condition.
	 */
	public double getStrength(Collection <? extends Dimension> CurrentInput)
	{
		if(CurrentInput == null)
			return 0;
		double Strength = 0;
		double Weight = getWeight();
		for(Dimension i : CurrentInput)
		{
			Dimension k = get(i.getID());
			if(k != null)
			{
				if(k.checkMatchAll())
					Strength += k.getMaxActivation() * Weight;
				else
				{
					double Max = 0;
					for (Value j : i.values())
					{
						Value l = k.get(j.getID());
						if(l != null && 
								(Math.abs(l.getActivation() - j.getActivation()) <= Value.GLOBAL_ACTIVATION_EPSILON ||
										l.getActivation() > j.getActivation())
								&& 
								j.getActivation() >  Max)
						{
							Max = j.getActivation();
						}
					}
					Strength += Max * Weight;
				}
			}
		}
		return Strength;
	}
	
	/**
	 * Checks to see if the specified condition is covered by this condition.
	 * @param cond The condition to check against this condition.
	 * @return True if this condition covers the specified condition, otherwise false.
	 */
	public boolean covers (GeneralizedConditionChunk cond)
	{
		if(cond == null)
			return false;
		if(!equalsKeys(cond))
			return false;
		for(Dimension i : cond.values())
		{
			Dimension d = get(i.getID());
			if(!d.checkMatchAll())
			{
				for(Value j : i.values())
				{
					if(!(d.containsKey(j.getID())))
						return false;
					Value v = d.get(j.getID());
					if(v.getActivation() < j.getActivation() && 
							!(Math.abs(v.getActivation() - j.getActivation()) <= Value.GLOBAL_ACTIVATION_EPSILON))
						return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkMatchAll()
	{
		boolean ma = true;
		for(Dimension dd : this.values())
		{
			if(!dd.checkMatchAll())
				ma = false;
		}
		return ma;
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the condition (including all of it's dimensions).
	 * @return A copy of the condition.
	 */
	public GeneralizedConditionChunk clone ()
	{
		GeneralizedConditionChunk a = new GeneralizedConditionChunk();
		for (Dimension i : values())
		{
			Dimension d = i.clone();
			a.put(d.getID(), d);
		}
		a.hash = hash;
		return a;
	}
}
