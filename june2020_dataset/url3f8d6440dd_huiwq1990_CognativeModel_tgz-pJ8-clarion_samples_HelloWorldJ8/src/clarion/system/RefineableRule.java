package clarion.system;

import java.util.HashSet;
import java.util.Collection;

/**
 * This class implements a refineable rule within CLARION. It extends the AbstractRule class
 * and implements the InterfaceDeleteable interface.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A refineable rule is a rule within the ACS that can be specialized, generalize, and deleted automatically
 * within CLARION. This is the base class for any rule type that can be refined. It is also the class that is
 * generated when RER rules are extracted from the bottom level. The primary difference between a refineable
 * rule and other rules is that refineable rules keep track or hidden minor rule variations that are the central
 * components for enabling generalization and specialization.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractIRLRule</li>
 * </ul>
 * @version 6.0.6
 * @author Nick Wilson
 */
public class RefineableRule extends AbstractRule implements InterfaceDeleteableByDensity {
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**The Match all rule related to the action chunk of the rule. This rule is considered to be a 
	 * hidden rule. It is used specifically for rule refinement.*/
	protected AbstractRule MatchAll;
	
	/**The rule variations (hidden rules that contain + or - one activated dimension-value pair
	 * in the condition of the rule). The rule variations are used specifically for rule refinement.*/
	protected  HashSet <AbstractRule> Variations;
	
	/**
	 * Initializes a refineable rule with the condition and action specified.
	 * @param Cond The condition for the rule.
	 * @param Act The action for the rule.
	 */
	public RefineableRule(GeneralizedConditionChunk Cond, AbstractAction Act) {
		super(Cond, Act);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Gets the number of rule variations this rule has.
	 * @return The number of rule variations.
	 */
	public int getNumVariations()
	{
		if(Variations != null)
			return Variations.size();
		else return 0;
	}
	
	/**
	 * Gets a collection of the variations of this rule.
	 * @return A collection the variations of this rule.
	 */
	public Collection <AbstractRule> getVariations()
	{
		return Variations;
	}
	
	/**
	 * Initializes the rule variations collection.
	 */
	public void initVariations()
	{
		Variations = new HashSet <AbstractRule> ();
	}
	
	/**
	 * Resets the rule variations collection.
	 */
	public void resetVariations()
	{
		if(Variations != null)
			Variations.clear();
		Variations = null;
	}
	
	/**
	 * Gets the rule variation (containing 1 additional activated dimension-value pair) with the 
	 * maximum information gain compared to this rule.
	 * @param r The rule refine to use for calculating the information gain.
	 * @return The rule variation with the maximum information gain.
	 */
	public AbstractRule getMaxVariationPlusOne(RuleRefiner r)
	{
		AbstractRule Cprime = null;
		if(Variations != null)
		{
			int numactivatedthis = 0;
			for (Dimension d : rCondition.values())
				numactivatedthis += d.getNumActivatedVals();
			for (AbstractRule i : Variations)
			{
				int numactivatedcp = 0;
				for(Dimension d : i.getCondition().values())
					numactivatedcp += d.getNumActivatedVals();
				
				if (numactivatedthis < numactivatedcp)
				{
					if(Cprime == null)
						Cprime = i;
					else
					{
						if (r.calculateInformationGain(i, this) > r.calculateInformationGain(Cprime, this))
							Cprime = i;
					}
				}
			}
		}
		return Cprime;
	}
	
	/**
	 * Gets the rule variation (containing 1 less activated dimension-value pair) with the 
	 * maximum information gain compared to this rule.
	 * @param r The rule refine to use for calculating the information gain.
	 * @return The rule variation with the maximum information gain.
	 */
	public AbstractRule getMaxVariationMinusOne(RuleRefiner r)
	{
		AbstractRule Cprime = null;
		int numactivatedthis = 0;
		if(Variations != null)
		{
			for (Dimension d : rCondition.values())
				numactivatedthis += d.getNumActivatedVals();
			for (AbstractRule i : Variations)
			{
				int numactivatedcp = 0;
				for(Dimension d : i.getCondition().values())
					numactivatedcp += d.getNumActivatedVals();
				
				if (numactivatedthis > numactivatedcp)
				{
					if(Cprime == null)
						Cprime = i;
					else
					{
						if (r.calculateInformationGain(i, this) > r.calculateInformationGain(Cprime, this))
							Cprime = i;
					}
				}
			}
		}
		return Cprime;
	}
	
	/**
	 * Gets the match all rule related to this rule.
	 * @return The match all rule.
	 */
	public AbstractRule getMatchAll()
	{
		return MatchAll;
	}
	
	/**
	 * Sets the match all rule related to this rule.
	 * @param MA The match all rule.
	 */
	public void setMatchAll(AbstractRule MA)
	{
		MatchAll = MA;
	}
	
	/**
	 * Checks to see if deletion should occur based on the density parameter.
	 * @param TimeStamp The current time stamp.
	 * @return True if deletion should occur based on density, otherwise false.
	 */
	public boolean checkDeletionByDensity (long TimeStamp)
	{
		if(LastMatch > 0 && (((double)LastMatch)/(double)TimeStamp) <= DENSITY)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks to see if deletion should occur. If you wish to implement
	 * a condition for deletion for a rule, this method should be be overridden.
	 * @return False by default.
	 */
	public boolean checkDeletion() {
		return false;
	}

	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the rule.
	 * @return A copy of the rule.
	 */
	public RefineableRule clone()
	{
		RefineableRule r = new RefineableRule (rCondition.clone(),rAction);
		r.MatchAll = MatchAll;
		if(Variations != null)
			r.Variations = new HashSet <AbstractRule> (Variations);
		if(Children != null)
			r.Children = new HashSet <AbstractRule> (Children);
		r.PARTIAL_MATCH_THRESHOLD = PARTIAL_MATCH_THRESHOLD;
		r.POSITIVE_MATCH_THRESHOLD = POSITIVE_MATCH_THRESHOLD;
		r.SELECTION_TYPE = SELECTION_TYPE;
		r.WEIGHT = WEIGHT;
		r.C = C;
		r.D = D;
		r.INITIAL_BLA = INITIAL_BLA;
		r.T = T.clone();
		r.LatestTimeStamp = LatestTimeStamp;
		r.V = V;
		r.UTILITY_OPTION = UTILITY_OPTION;
		r.BENEFIT_CONSTANT = BENEFIT_CONSTANT;
		r.COST_CONSTANT = COST_CONSTANT;
		r.C7 = C7;
		r.C8 = C8;
		r.DENSITY = DENSITY;
		r.hash = hash;
		return r;
	}
}
