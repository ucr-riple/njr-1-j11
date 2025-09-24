package clarion.system;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;

/**
 * This class implements a hidden rule collection within CLARION. It extends the HashMap class.
 * This class is used exclusively within a rule collection for tracking child rules and rule
 * variations.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The hidden rule collection acts as a container for rules that are not explicitly accessible by a
 * CLARION agent. That is, the rules contained within a hidden rule collection can't be used for
 * action decision making.
 * <p>
 * Rules that are normally stored in the hidden rule collection include:<br>
 * <ul>
 * <li><b>Rule variations</b> - rules that differ by a single value within the condition to rules currently in 
 * the rule store. They act as minor variations whose match statistics are tracked and, based on a information 
 * gain measure (see the Sun tutorial, 2003 or the RuleRefiner class in this library), these variations have a 
 * chance of becoming actual rules within the rule store acting as a more generalized and/or specialized version 
 * of the rule in the rule store to which it is associated.</li>
 * <li><b>Child rules</b> - rules that USED to be in the rule store, but have been replaced by more generalized rules
 * that cover them. These rules are maintained in the event that the more generalized rule is deleted from the
 * rule store (for various reasons), at which time they have the opportunity to return back to the rule store.</li>
 * </ul>
 * <p>
 * Note that while it is possible to use the "put" and "remove" methods that were extended from HashMap, it is
 * HIGHLY advised that you NOT use them! The methods "add" and "remove" have been provided within this class
 * that allow you to directly (and correctly) add and remove a rule from the rule collection.
 * <p>
 * The hidden rule collection itself is a somewhat more simplified version of a rule collection in that it
 * does not need to track (or generate) things like rule variations, child rules, or collection-wide match statistics.
 * In addition, it doesn't have to worry about correctly placing newly added rules within the collection by determining
 * if the rule should be in the collection or within the child collection.
 * <p>
 * Conceptually speaking, rules contained within a hidden rule collection really only exist "abstractly."
 * It is important to reiterate that rules contained within this collection are NOT accessible to the CLARION agent
 * for any purposes other than rule refinement.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class HiddenRuleCollection extends HashMap <Object, HashMap <Object, HashMap <Object, LinkedList <AbstractRule>>>>{
	private static final long serialVersionUID = 8247993623873488699L;
	
	/**
	 * Initializes a rule collection.
	 */
	public HiddenRuleCollection ()
	{
		super();
	}
	
	/**
	 * Initializes a rule collection with the collection of rules specified.
	 * @param rules The rules for the collection.
	 */
	public HiddenRuleCollection (Collection <? extends AbstractRule> rules)
	{
		super();
		for(AbstractRule r : rules)
			add(r);
	}
	
	/**
	 * Gets a rule in the collection that matches the specified condition and 
	 * action. If the collection does not contain a rule with the specified condition and 
	 * action, this method returns null.
	 * @param cond The condition of the rule to get.
	 * @param act The action of the rules to get.
	 * @return A rules with the specified condition and action.
	 */
	public AbstractRule get (GeneralizedConditionChunk cond, AbstractAction act)
	{
		HashMap<Object, HashMap<Object,LinkedList <AbstractRule>>> rc = get(act.getID());
		if (rc == null)
			return null;
		if(cond == null)
		{
			//Only 1 condition-less rule is allowed for each action. This is only the case for a rule 
			//that does not have a condition.
			LinkedList <AbstractRule> candidates = rc.get(null).get(null);
			if(candidates == null)
				return null;
			return candidates.iterator().next();
		}
		else
		{
			for(Dimension d : cond.values())
			{
				HashMap<Object,LinkedList <AbstractRule>> rrc = rc.get(d.getID());
				if(rrc == null)
					return null;
				for(Value v : d.values())
				{
					if(v.isFullyActivated())
					{
						LinkedList <AbstractRule> candidates = rrc.get(v.getID());
						if(candidates == null)
							return null;
						for(AbstractRule r : candidates)
						{
							if(r.getCondition().equals(cond))
								return r;
						}
						return null;
					}
				}
			}
		}
		//It is highly unlikely this return statement will be reached. The specified condition would have to 
		//have no dimensions OR all of the values within all of the dimensions of the specified condition
		//would have to be "off", in which case the condition would never match any state and would therefore
		//not be very useful.
		return null;
	}
	
	/**
	 * Checks to see if the collection contains the specified rule.
	 * @param rule The rule object you wish to check for within this collection.
	 * @return True if the collection contains the rule specified, otherwise false.
	 */
	public boolean contains (AbstractRule rule)
	{
		if(get(rule.rCondition, rule.rAction) != null)
			return true;
		return false;
	}
	
	/**
	 * Adds a rule into the rule collection. If the specified rule is equal to any rules 
	 * currently in the collection, the rule will NOT be added. In addition, this method
	 * also resets all of the rule variations, children, and match statistics from the specified 
	 * rule (if the rule is refineable) since it is not necessary to track anything for rules that are 
	 * not in the rule store.
	 * @param R The rule to add.
	 * @return True if the rule was successfully added, otherwise false.
	 */
	public boolean add (AbstractRule R)
	{
		HashSet <AbstractRule> candidates = new HashSet<AbstractRule> ();
		HashMap<Object, HashMap<Object,LinkedList <AbstractRule>>> rc = get(R.getAction().getID());
		boolean candidateFlag = false;
		if (rc == null)
		{
			if(R.getCondition() != null)
				rc = new HashMap<Object, HashMap<Object,LinkedList<AbstractRule>>> (R.getCondition().size());
			else
				rc = new HashMap<Object, HashMap<Object,LinkedList<AbstractRule>>> (1);
			put(R.getAction().getID(), rc);
			candidateFlag = true;
		}
		if(R.getCondition() == null)
		{
			HashMap<Object,LinkedList <AbstractRule>> rrc = rc.get(null);
			if(rrc == null)
			{
				rrc = new HashMap<Object, LinkedList<AbstractRule>> (1);
				rc.put(null, rrc);
			}
			LinkedList<AbstractRule> rrrc = rrc.get(null);
			if(rrrc == null)
			{
				rrrc = new LinkedList<AbstractRule> ();
				rrc.put(null, rrrc);
			}
			rrrc.clear();
			rrrc.add(R);
			return true;
		}
		else
		{
			for(Dimension d : R.getCondition().values())
			{
				HashMap<Object,LinkedList <AbstractRule>> rrc = rc.get(d.getID());
				if(rrc == null)
				{
					rrc = new HashMap<Object, LinkedList<AbstractRule>> (d.size());
					rc.put(d.getID(), rrc);
					candidates.clear();
					candidateFlag = true;
				}
				for(Value v : d.values())
				{
					LinkedList <AbstractRule> rrrc = rrc.get(v.getID());
					if(rrrc == null && v.isFullyActivated())
					{
						rrrc = new LinkedList<AbstractRule> ();
						rrc.put(v.getID(), rrrc);
						candidates.clear();
						candidateFlag = true;
					}
					else
					{
						if(v.isFullyActivated())
						{
							if(!candidateFlag)
							{
								candidates.addAll(rrc.get(v.getID()));
								candidateFlag = true;
							}
							else
							{
								LinkedList <AbstractRule> rem = rrc.get(v.getID());
								HashSet <AbstractRule> cut = new HashSet <AbstractRule> ();
								cut.addAll(candidates);
								for(AbstractRule a : rem)
								{
									if(candidates.contains(a))
									{
										cut.remove(a);
									}
								}
								
								candidates.removeAll(cut);
							}
						}
					}
				}
			}
			
			if(candidates.size() > 0)
			{
				for (AbstractRule a : candidates)
				{
					if(a.equals(R))
					{
						return false;
					}
				}
			}
			attachRule(R);
			R.resetChildren();
			R.resetMatchStatistics();
			if(R instanceof RefineableRule)
				((RefineableRule)R).resetVariations();
			return true;
		}
	}
	
	/**
	 * Adds a collection of rules into the rule collection. If a rule in the specified 
	 * collection is equal to any rules currently in this collection, the rule will NOT 
	 * be added.
	 * @param R The rules to add to the collection.
	 */
	public void addAll (Collection <? extends AbstractRule> R)
	{
		for(AbstractRule r : R)
		{
			add(r);
		}
	}
	
	private boolean attachRule (AbstractRule R)
	{
		if(R.getCondition() == null)
			return false;
		
		for(Dimension d : R.getCondition().values())
		{
			for(Value v : d.values())
			{
				if(v.isFullyActivated())
				{
					get(R.getAction().getID()).get(d.getID()).get(v.getID()).add(R);
				}
			}
		}
		return true;
	}
	
	/**
	 * Removes the specified rule from this rule collection if the rule is contained within the rule collection.
	 * @param R The rule you want to remove.
	 * @return True if the rule was successfully removed, otherwise false.
	 */
	public boolean remove (AbstractRule R)
	{
		HashMap<Object, HashMap<Object, LinkedList <AbstractRule>>> rc = get(R.getAction().getID());
		if (rc == null)
			return false;
		boolean success = false;
		for(Dimension d : R.getCondition().values())
		{
			HashMap<Object, LinkedList <AbstractRule>> rrc = rc.get(d.getID());
			if(rrc == null)
				return false;
			for(Value v : d.values())
			{
				if(v.isFullyActivated())
				{
					LinkedList <AbstractRule> rrrc = rrc.get(v.getID());
					if(rrrc == null)
						return false;
					success = rrrc.remove(R);
					if(rrrc.size() == 0)
					{
						rrc.remove(v.getID());
						if(rrc.size() == 0)
						{
							rc.remove(d.getID());
							if(rc.size() == 0)
							{
								remove(R.getAction().getID());
							}
						}
					}
				}
			}
		}
		return success;
	}
	
	/**
	 * Gets all of the rules in the rule collection.
	 * @return A collection containing the rules in the rule collection.
	 */
	public Collection <AbstractRule> getRules()
	{
		HashSet <AbstractRule> rc = new HashSet<AbstractRule> ();
		for(HashMap<Object, HashMap<Object, LinkedList <AbstractRule>>> i : values())
		{
			for(HashMap<Object, LinkedList <AbstractRule>> j : i.values())
			{
				for(LinkedList <AbstractRule> k : j.values())
				{
					rc.addAll(k);
				}
			}
		}
		return rc;
	}
	
	/**
	 * Gets all of the rules in the rule collection for the specified action.
	 * @param act The action whose associated rules you wish to get.
	 * @return A collection containing the rules in the rule collection for the specified action.
	 */
	public Collection <AbstractRule> getRules(AbstractAction act)
	{
		HashSet <AbstractRule> rc = new HashSet<AbstractRule> ();
		HashMap<Object, HashMap <Object, LinkedList<AbstractRule>>> i = get(act.getID());
		if(i != null)
		{
			for(HashMap<Object, LinkedList <AbstractRule>> j : i.values())
			{
				for(LinkedList <AbstractRule> k : j.values())
				{
					rc.addAll(k);
				}
			}
		}
		return rc;
	}
	
	/**
	 * Discounts the positive and negative match statistics for all rules in the rule 
	 * collection.
	 * @param Discount The discount factor to be applied to the match statistics of the rules.
	 */
	public void discountMatchStatistics(double Discount)
	{
		for(AbstractRule i : getRules())
		{
			i.setPM(i.getPM() * Discount);
			i.setNM(i.getNM() * Discount);
		}
	}
	
	/**
	 * Updates the positive or negative match statistics based on the information specified.
	 * @param CurrentInput The current input.
	 * @param ChosenAction The chosen action.
	 * @param feedback The feedback.
	 * @param TimeStamp	The current time stamp.
	 */
	public void updateMatchStatistics(DimensionValueCollection CurrentInput, AbstractAction ChosenAction, 
			double feedback, AbstractMatchCalculator MatchCalculator, long TimeStamp)
	{
		for(AbstractRule i : getRules(ChosenAction))
		{
			i.setCurrentInput(CurrentInput);
			if(i.checkEligibility())
			{
				i.setFeedback(feedback);
				if(i instanceof InterfaceHasMatchCalculator)
					i.updateMatchStatistics(((InterfaceHasMatchCalculator)i).getMatchCalculator());
				else
					i.updateMatchStatistics(MatchCalculator);
			}
		}
	}
	
	/**
	 * Returns the number of rules in the rule collection.
	 */
	public int size ()
	{
		return getRules().size();
	}
	
	public void clear()
	{
		for(HashMap<Object, HashMap<Object,LinkedList <AbstractRule>>> rc : this.values())
		{
			for(HashMap<Object,LinkedList <AbstractRule>> rcc : rc.values())
			{
				for(LinkedList <AbstractRule> rccc : rcc.values())
				{
					rccc.clear();
				}
				rcc.clear();
			}
			rc.clear();
		}
		super.clear();
	}
	
	public String toString()
	{
		String s = "";
		for(Iterator <AbstractRule> i = getRules().iterator(); i.hasNext();)
		{
			AbstractRule r = i.next();
			s += r.toString();
			if(i.hasNext())
				s+="\n";
		}
		return s;
	}
}
