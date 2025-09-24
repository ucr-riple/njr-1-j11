package clarion.system;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;

/**
 * This class implements a rule collection within CLARION. It extends the HashMap class and
 * implements the InterfaceTracksMatchStatistics and InterfaceHandlesFeedback interfaces.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The rule collection acts as a container for rules that are used by a CLARION agent for
 * action decision making. This class is most often used within the ACS as the container for the 
 * rule stores.
 * <p>
 * The rule collection also tracks (or generates): rule variations, child rules, and collection-wide match statistics.
 * In addition, it has the ability to correctly place newly added rules within the collection by determining
 * if the rule should be in the collection or within a child collection.
 * <p>
 * For all of the rules in the rule collection that are refineable (i.e. they extend from the RefineableRule class), 
 * hidden minor variations of those rules are generated automatically and tracked by this class in order to 
 * facilitate generalization and specialization.
 * <p>
 * In addition to tracking match statistics for the hidden variations, the rule collection also keeps track of
 * collection-wide match statistics that are used for variable level selection within the ACS as well as for
 * performance monitoring and reporting purposes.
 * <p>
 * Note that while it is possible to use the "put" and "remove" methods that were extended from HashMap, it is
 * HIGHLY advised that you NOT use them! The methods "add" and "remove" have been provided within this class
 * that allow you to directly (and correctly) add and remove a rule from the rule collection.
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class RuleCollection extends HashMap <Object, HashMap <Object, HashMap <Object, LinkedList <AbstractRule>>>>
implements InterfaceTracksMatchStatistics, InterfaceHandlesFeedback{
	private static final long serialVersionUID = 8247993623873488699L;
	
	/**Contains the hidden rule variations for all rules in the rule collection.*/
	protected HiddenRuleCollection Variations = new HiddenRuleCollection ();
	/**Contains any existent child rules for the rules in the rule collection.*/
	protected HiddenRuleCollection Children = new HiddenRuleCollection ();
	
	/**The immediate feedback (if given).*/
	private double Feedback;
	/**The positive match counter.*/
	protected double PM = 0;
	/**The negative match counter.*/
	protected double NM = 0;
	/**The threshold that must be passed to meet the positive match criterion.*/
	public static double GLOBAL_POSITIVE_MATCH_THRESHOLD = .9;
    /**The threshold that must be passed to meet the positive match criterion.*/
    public double POSITIVE_MATCH_THRESHOLD = GLOBAL_POSITIVE_MATCH_THRESHOLD;
	
	/**
	 * Initializes a rule collection.
	 */
	public RuleCollection ()
	{
		super();
	}
	
	/**
	 * Initializes a rule collection with the collection of rules specified.
	 * @param rules The rules for the collection.
	 */
	public RuleCollection (Collection <? extends AbstractRule> rules)
	{
		super();
		for(AbstractRule r : rules)
			add(r);
	}
	
	/**
	 * Gets a rule in the collection that matches the specified condition and 
	 * action. If the collection does not contain a rule with the specified condition and 
	 * action, this method returns null.
	 * NOTE: If the specified condition of the rule to get is null (such as in the case of a fixed rule without a condition), 
	 * this method will return the condition-less rule for the specified action.
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
	 * Gets the rules in the collection that cover the specified rule. If the collection does 
	 * not contain any rules that cover the specified rule, this method returns null.
	 * @param R The rule you wish to check for the existence of a covering rule within the collection.
	 * @return A collection of rules that cover the specified rule.
	 */
	public Collection <AbstractRule> getCover (AbstractRule R)
	{
		HashMap<Object, HashMap<Object,LinkedList <AbstractRule>>> rc = get(R.getAction().getID());
		if (rc == null || R.getCondition() == null)
			return null;
		HashSet <AbstractRule> candidates = new HashSet<AbstractRule> ();
		boolean flag = false;
		for(Dimension d : R.getCondition().values())
		{
			HashMap<Object,LinkedList <AbstractRule>> rrc = rc.get(d.getID());
			if(rrc == null)
				return null;
			for(Value v : d.values())
			{
				if(v.isFullyActivated())
				{
					if(!flag)
					{
						candidates.addAll(rrc.get(v.getID()));
						flag = true;
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
		for(Iterator <AbstractRule> i = candidates.iterator(); i.hasNext();)
		{
			AbstractRule a = i.next();
			if(a.getCondition().equals(R.getCondition()) || !a.getCondition().equalsKeys(R.getCondition()))
				i.remove();
		}
		if(candidates.size() == 0)
			return null;
		else
			return candidates;
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
	 * Checks to see if the collection contains a rule that covers the specified rule.
	 * @param rule The rule object you wish to check within this collection.
	 * @return True if the collection contains a rule that covers the rule specified, 
	 * otherwise false.
	 */
	public boolean containsCover (AbstractRule rule)
	{
		if(getCover(rule) != null)
			return true;
		return false;
	}

	/**
	 * Adds a rule into the rule collection. If the specified rule is covered by any rules 
	 * currently in this collection, then the rule will be added to the collection of child 
	 * rules for that rule. If the specified rule covers any rules in this collection, those rules
	 * will be added to the child rules of the specified rule and the specified rule will 
	 * replace those rules in this collection. If the specified rule is equal to any rules 
	 * currently in this collection the rule will NOT be added. If the condition of the specified
	 * rule is null, it will be added to the "null" slot of the rule collection. If there is already
	 * a condition-less rule in the "null" slot, it will be REPLACED by the specified rule. 
	 * @param R The rule to add.
	 * @return True if the rule was successfully added, otherwise false.
	 */
	public boolean add (AbstractRule R)
	{
		HashSet <AbstractRule> candidates = new HashSet<AbstractRule> ();
		HashSet <AbstractRule> coveredBy = new HashSet<AbstractRule> ();
		HashMap<Object, HashMap<Object,LinkedList <AbstractRule>>> rc = get(R.getAction().getID());
		boolean candidateFlag = false;
		boolean coveredByFlag = false;
		if (rc == null)
		{
			if(R.getCondition() != null)
				rc = new HashMap<Object, HashMap<Object,LinkedList<AbstractRule>>> (R.getCondition().size());
			else
				rc = new HashMap<Object, HashMap<Object,LinkedList<AbstractRule>>> (1);
			put(R.getAction().getID(), rc);
			candidateFlag = true;
			coveredByFlag = true;
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
					coveredBy.clear();
					candidateFlag = true;
					coveredByFlag = true;
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
							if(!coveredByFlag)
							{
								coveredBy.addAll(rrc.get(v.getID()));
							}
						}
						else
						{
							for(Iterator <AbstractRule> i = coveredBy.iterator(); i.hasNext();)
							{
								AbstractRule a = i.next();
								if(a.getCondition().get(d.getID()).get(v.getID()).isFullyActivated())
								{
									i.remove();
								}
							}
						}
					}
				}
				coveredByFlag = true;
			}
			
			if(candidates.size() > 0)
			{
				for (Iterator <AbstractRule> i = candidates.iterator(); i.hasNext();)
				{
					AbstractRule parent = i.next();
					if(!parent.getCondition().equalsKeys(R.getCondition()))
						i.remove();
				}
				
				for (AbstractRule a : candidates)
				{
					if(a.equals(R))
					{
						return false;
					}
				}
				
				if(candidates.size() > 0)
				{
					boolean success = Children.add(R);
					AbstractRule r = R;
					if(!success)
						r = Children.get(R.getCondition(), R.getAction());
					
					for (AbstractRule parent : candidates)
					{
						if(parent.getChildren() == null)
							parent.initChildren();
						parent.getChildren().add(r);
					}
				}
				else
				{
					attachRule(R);
				}
			}
			else 	
			{
				attachRule(R);
				
				for(AbstractRule a : coveredBy)
				{
					if(!a.equals(R))
					{
						remove(a);
						Children.add(a);
					}
				}
				
				HashMap<Object, HashMap<Object,LinkedList <AbstractRule>>> crc = Children.get(R.getAction().getID());
				if(crc != null)
				{
					HashSet <AbstractRule> covers = new HashSet<AbstractRule> ();
					for(Dimension d : R.getCondition().values())
					{
						HashMap<Object,LinkedList <AbstractRule>> crrc = crc.get(d.getID());
						if(crrc != null)
						{
							for(Value v : d.values())
							{
								if(v.isFullyActivated())
								{
									LinkedList <AbstractRule> crrrc = crrc.get(v.getID());
									if(crrrc != null)
										covers.addAll(crrrc);
								}
							}
						}
					}
					
					for(AbstractRule c : covers)
					{
						if(R.covers(c) && !R.equals(c))
						{
							if(R.getChildren() == null)
								R.initChildren();
							R.getChildren().add(c);
						}
					}
				}
			}
			R.resetMatchStatistics();
			return true;
		}
	}
	
	/**
	 * Adds all rules in the specified collection of rules into the rule collection. If a rule in the specified 
	 * collection is equal to any rules currently in this collection, the rule will NOT 
	 * be added.
	 * @param R The rules to add to the rule collection.
	 */
	public void addAll (Collection <? extends AbstractRule> R)
	{
		for(AbstractRule r : R)
		{
			add(r);
		}
	}
	
	/**
	 * Removes the specified rule from this rule collection if the rule is contained within the rule collection. This 
	 * method also removes all of the rule variations associated with the rule being removed as long as the variation 
	 * is not also a variation for a different rule in the rule store.
	 * @param R The rule you want to remove.
	 * @return True if the rule was successfully removed, otherwise false.
	 */
	public boolean remove (AbstractRule R)
	{
		HashMap<Object, HashMap<Object, LinkedList <AbstractRule>>> rc = get(R.getAction().getID());
		if(rc == null)
			return false;
		boolean success = false;
		for(Dimension d : R.getCondition().values())
		{
			HashMap<Object, LinkedList <AbstractRule>> rrc = rc.get(d.getID());
			if(rrc == null)
				return false;
			for(Value v : d.values())
			{
				LinkedList <AbstractRule> rrrc = rrc.get(v.getID());
				if(v.isFullyActivated())
				{
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
								remove(R.getAction().getID());
						}
					}
				}
				
				if(rrrc != null)
				{
					for(AbstractRule rr : rrrc)
					{
						if(rr instanceof RefineableRule)
						{
							if(((RefineableRule)rr).getVariations() != null)
								((RefineableRule)rr).getVariations().remove(R);
						}
					}
				}
			}
		}
		
		if(R instanceof RefineableRule)
		{
			removeVariations((RefineableRule)R);
			Variations.remove(R);
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
		if (R instanceof RefineableRule)
			generateVariations((RefineableRule)R);
		return true;
	}
	
	/**
	 * Removes the variations from the variations list for all variations of the 
	 * specified rule that are not also a variation for another rule in the rule collection.
	 * In addition, this method also removes the variations from the specified rule.
	 * @param R The rule whose variations you wish to remove.
	 */
	private void removeVariations (RefineableRule R)
	{
		if(R.getVariations() != null)
		{
			HashSet <AbstractRule> checked = new HashSet <AbstractRule> ();
			HashSet <AbstractRule> choppingblock = new HashSet <AbstractRule> (R.getVariations());
			HashMap<Object, HashMap<Object, LinkedList <AbstractRule>>> rc = get(R.getAction().getID());
			if(rc != null)
			{
				for(Dimension d : R.getCondition().values())
				{
					HashMap<Object, LinkedList <AbstractRule>> rrc = rc.get(d.getID());
					if(rrc != null)
					{
						for(LinkedList <AbstractRule> rrrc : rrc.values())
						{
							for(AbstractRule rr : rrrc)
							{
								if(rr instanceof RefineableRule && !checked.contains(rr))
								{
									for(Iterator <AbstractRule> i = choppingblock.iterator(); i.hasNext();)
									{
										AbstractRule vr = i.next();
										if(((RefineableRule) rr).getVariations() != null && ((RefineableRule) rr).getVariations().contains(vr))
										{
											i.remove();
										}
									}
									checked.add(rr);
								}
							}
						}
					}
				}
				for(AbstractRule vr : choppingblock)
				{
					Variations.remove(vr);
				}
			}
			R.resetVariations();
		}
	}
	
	/**
	 * Generates variations for the rule r and adds any variations that are not already
	 * in the variations list to that list. This method also adds the variations
	 * for the specified rule to that rule's internal variations list.
	 * @param R The rule from which to generate variations.
	 */
	private void generateVariations (RefineableRule R)
	{
		for(Dimension d : R.getCondition().values())
		{
			for(Value v : d.values())
			{
				RefineableRule r = new RefineableRule (new GeneralizedConditionChunk(R.getCondition().clone()),R.getAction());
				if(v.isFullyActivated())
				{
					r.getCondition().get(d.getID()).get(v.getID()).setActivation(v.MINIMUM_ACTIVATION_THRESHOLD);
				}
				else
					r.getCondition().get(d.getID()).get(v.getID()).setActivation(v.FULL_ACTIVATION_THRESHOLD);
				
				if(r.getCondition().get(d.getID()).getNumFullyActivatedVals() > 0 
						&& !r.getCondition().checkMatchAll() && !contains(r))
				{
					if(!Variations.contains(r))
						Variations.add(r);
					else
						r = (RefineableRule)Variations.get(r.getCondition(), r.getAction());
					if(R.getVariations() == null)
						R.initVariations();
					R.getVariations().add(r);
					r.setMatchAll(R.getMatchAll());
				}
			}
		}
	}
	
	/**
	 * Discounts the positive and negative match statistics for all rules in the rule 
	 * collection and all rule variations.
	 * @param Discount The discount factor to be applied to the match statistics of the rules.
	 */
	public void discountMatchStatistics(double Discount)
	{
		for(AbstractRule i : getRules())
		{
			i.setPM(i.getPM() * Discount);
			i.setNM(i.getNM() * Discount);
		}
		Variations.discountMatchStatistics(Discount);
		PM *= Discount;
		NM *= Discount;
	}
	
	/**
	 * Gets the positive match statistic.
	 * @return The positive match statistic.
	 */
	public double getPM ()
	{
		return PM;
	}
	
	/**
	 * Gets the negative match statistic.
	 * @return The negative match statistic.
	 */
	public double getNM ()
	{
		return NM;
	}
	
	/**
	 * Sets the positive match statistic. This method can be used to update the 
	 * positive match statistic if the user wishes to provide their own match criterion 
	 * function.
	 * @param pm The value to set as the positive match statistic.
	 */
	public void setPM (double pm)
	{
		PM = pm;
	}
	
	/**
	 * Sets the negative match statistic. This method can be used to update the 
	 * negative match statistic if the user wishes to provide their own match criterion 
	 * function.
	 * @param nm The value to set as the negative match statistic.
	 */
	public void setNM (double nm)
	{
		NM = nm;
	}
	
	/**
     * Gets the feedback. This method is only used if feedback is being provided.
     * @return The feedback.
     */
    public double getFeedback()
    {
    	return Feedback;
    }
    
    /**
     * Sets the feedback. This method should be called before the updateMatchStatistics method is called. 
     * This method is only used if feedback is being provided.
     * @param feedback The value of the feedback.
     */
    public void setFeedback (double feedback)
    {
    	Feedback = feedback;
    }
	
	/**
	 * Updates the positive or negative match statistics based on the feedback.
	 * <p>
	 * This update is usually performed after the feedback has been set.
	 * @param MatchCalculator The match calculator to use to determine positivity.
	 */
	public void updateMatchStatistics (AbstractMatchCalculator MatchCalculator)
	{
		if (MatchCalculator.isPositive(Feedback, POSITIVE_MATCH_THRESHOLD))
		{
			++PM;
		}
		else
			++NM;
	}
	
	/**
	 * Resets the match statistics.
	 */
	public void resetMatchStatistics ()
	{
		PM = 0;
		NM = 0;
	}
	
	/**
	 * Increments the positive match statistic. This method can be used to update the 
	 * positive match statistic if the user wishes to provide their own match criterion 
	 * function.
	 */
	public void incrementPM()
	{
		++PM;
	}
	
	/**
	 * Increments the negative match statistic. This method can be used to update the 
	 * negative match statistic if the user wishes to provide their own match criterion 
	 * function.
	 */
	public void incrementNM()
	{
		++NM;
	}
    
	/**
	 * This method does nothing as it is not used by the CLARION Library for this collection.
	 * @return False
	 */
	public boolean checkMatchCriterion() {
		return false;
	}
	
	/**
	 * Returns the number of rules in the rule collection.
	 * @return The number of rules.
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
					for(AbstractRule r : rccc)
					{
						r.resetChildren();
						if(r instanceof RefineableRule)
						{
							((RefineableRule)r).resetVariations();
						}
					}
					rccc.clear();
				}
				rcc.clear();
			}
			rc.clear();
		}
		super.clear();
		Children.clear();
		Variations.clear();
		resetMatchStatistics();
	}
	
	/**
	 * Gets the number of variations being tracked by this collection.
	 * @return The number of variations.
	 */
	public int numVariations ()
	{
		return Variations.size();
	}
	
	/**
	 * Gets the number of children being tracked by this collection.
	 * @return The number of children.
	 */
	public int numChildren ()
	{
		return Children.size();
	}
	
	/**
	 * Gets the rule variations from the rule collection. This method is used mainly for
	 * internal purposes and for reporting the rule variations (if desired).
	 * <p>
	 * This method should NOT be used to manipulate the rule variations from outside of the 
	 * CLARION library.
	 * @return A collection containing the rule variations in this rule collection.
	 */
	public Collection <AbstractRule> getVariations ()
	{
		return Variations.getRules();
	}
	
	/**
	 * Gets the child rules from the rule collection. This method is used mainly for
	 * internal purposes and for reporting the rule variations (if desired).
	 * <p>
	 * This method should NOT be used to manipulate the child rules from outside of the 
	 * CLARION library.
	 * @return A collection containing the child rule in this rule collection.
	 */
	public Collection <AbstractRule> getChildren ()
	{
		return Children.getRules();
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
