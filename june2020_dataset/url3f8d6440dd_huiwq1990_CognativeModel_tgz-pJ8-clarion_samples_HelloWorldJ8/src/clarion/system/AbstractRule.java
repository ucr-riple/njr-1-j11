package clarion.system;

import java.util.Collection;
import java.util.HashSet;

/**
 * This class implements an abstract rule within CLARION. It extends the AbstractExplicitModule class
 * and implements the InterfaceTracksMatchStatistics, InterfaceHandlesFeedbackWithTime, and InterfaceTracksTime
 * interfaces. This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The abstract rule is the base class for rules within the ACS. All rule types within the rule collection 
 * in the ACS extend from this class.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractFixedRule</li>
 * <li>AbstractIRLRule</li>
 * <li>RefineableRule</li>
 * </ul>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public abstract class AbstractRule extends AbstractExplicitModule 
				implements InterfaceTracksMatchStatistics, InterfaceHandlesFeedback, InterfaceTracksTime{
	/**The various types of measures that can be selected over when performing action decision making.*/
	public enum SelectionTypes {SUPPORT,UTILITY};
	/**The options for calculating the utility of a rule.*/
	public enum UtilityOptions {CONSTANT, EQUATION};
	
	/**The condition chunk of the rule.*/
	protected GeneralizedConditionChunk rCondition;
	/**The action chunk of the rule.*/
	protected AbstractAction rAction;
	
	/**The child rules (if any) of this rule.*/
	protected HashSet <AbstractRule> Children;
	
	/**The current input.*/
	protected DimensionValueCollection CurrentInput;
	
	/**The default partial match threshold if partial match is turned off.*/
	public static double DEFAULT_PARTIAL_MATCH_OFF = 1;
	/**The default partial match threshold if partial match is turned on.*/
	public static double DEAFUALT_PARTIAL_MATCH_ON = .7;
	/**The threshold the support must pass for the condition to be matched.*/
	public static double GLOBAL_PARTIAL_MATCH_THRESHOLD = DEFAULT_PARTIAL_MATCH_OFF;
	/**The threshold the support must pass for the condition to be matched.*/
	public double PARTIAL_MATCH_THRESHOLD = GLOBAL_PARTIAL_MATCH_THRESHOLD;
	/**How close a condition's support must be to the partial match threshold for it to be considered equal to
	 * the partial match threshold. Needed due to the nature of double precision arithmetic.*/
	public static double GLOBAL_SUPPORT_EPSILON = .0001;


	/**The density (the minimum frequency needed to be kept)*/
	public static double GLOBAL_DENSITY = .01;
	/**The density (the minimum frequency needed to be kept)*/
	public double DENSITY = GLOBAL_DENSITY;
	
	/**Specifies the method to use for calculating utility. The default is to use constants*/
	public static UtilityOptions GLOBAL_UTILITY_OPTION = UtilityOptions.CONSTANT;
	/**Specifies the method to use for calculating utility. The default is to use constants*/
	public UtilityOptions UTILITY_OPTION = GLOBAL_UTILITY_OPTION;
	/**The constant to use for benefit when using the constant option for utility.*/
	public static double GLOBAL_BENEFIT_CONSTANT = 2;
	/**The constant to use for benefit when using the constant option for utility.*/
	public double BENEFIT_CONSTANT = GLOBAL_BENEFIT_CONSTANT;
	/**The constant to use for cost when using the constant option for utility.*/
	public static double GLOBAL_COST_CONSTANT = 1;
	/**The constant to use for benefit when using the constant option for utility.*/
	public double COST_CONSTANT = GLOBAL_COST_CONSTANT;
	/**The cost scaling factor. Used to calculate utility*/
	public static double GLOBAL_V = 1;
	/**The cost scaling factor. Used to calculate utility*/
	public double V = GLOBAL_V;
	
	/**Benefit equation constant c7.*/
	public static double GLOBAL_C7 = 1;
	/**Benefit equation constant c8.*/
	public static double GLOBAL_C8 = 2;
	/**Benefit equation constant c7.*/
	public double C7 = GLOBAL_C7;
	/**Benefit equation constant c8.*/
	public double C8 = GLOBAL_C8;
	
	/**The positive match counter.*/
	protected double PM = 0;
	/**The negative match counter.*/
	protected double NM = 0;
	/**The threshold that must be passed to meet the positive match criterion.*/
	public static double GLOBAL_POSITIVE_MATCH_THRESHOLD = .9;
    /**The threshold that must be passed to meet the positive match criterion.*/
    public double POSITIVE_MATCH_THRESHOLD = GLOBAL_POSITIVE_MATCH_THRESHOLD;
    
    /**The immediate feedback (if given).*/
	private double Feedback;
	
	/**The selection type to use for the selection measure of the rule.*/
	public static SelectionTypes GLOBAL_SELECTION_TYPE = SelectionTypes.UTILITY;
	/**The selection type to use for the selection measure of the rule.*/
	public SelectionTypes SELECTION_TYPE = GLOBAL_SELECTION_TYPE;
	
	/**The time-stamp associated with the last time the rule matched the input.*/
	protected long LastMatch = -1;
	
	/**
	 * Initializes a rule with the condition and action chunks specified.
	 * @param cond The condition for the new rule.
	 * @param act The action for the new rule.
	 * @throws IllegalArgumentException If the condition or action are null.
	 */
	public AbstractRule (GeneralizedConditionChunk cond, AbstractAction act) throws IllegalArgumentException
	{
		if(act == null)
			throw new IllegalArgumentException ("The action of a rule cannot be null!");
		rCondition = cond;
		rAction = act;
	}
	
	/**
	 * Gets the utility. If you are using the equation option for calculating the utility, this method 
	 * assumes that the cost is equal to the response time.
	 * @return The utility.
	 */
	public double getUtility()
	{
		if(UTILITY_OPTION == UtilityOptions.EQUATION)
			return ((C7 + PM)/(C8 + PM + NM)) - (V * getResponseTime());
		else
			return BENEFIT_CONSTANT - (V * COST_CONSTANT);
	}
	
	/**
	 * Gets the utility using the equation option and the specified average response time.
	 * @param AverageRT The average response time used for calculating the cost.
	 * @return The utility.
	 */
	public double getUtility(double AverageRT)
	{
		return ((C7 + PM)/(C8 + PM + NM)) - (V * getResponseTime() / AverageRT);
	}
	
	/**
	 * Gets the support based on the current input.
	 * @param CurrentInput The current input represented as a dimension-value collection.
	 * @return The support for this rule.
	 */
	public double getSupport(DimensionValueCollection CurrentInput)
	{
		if(rCondition != null)
			return rCondition.getStrength(CurrentInput.values()) * WEIGHT;
		else
			return 1;
	}
	
	/**
	 * Gets the condition of the rule.
	 * @return The condition.
	 */
	public GeneralizedConditionChunk getCondition()
	{
		return rCondition;
	}
	
	/**
	 * Gets the action of the rule.
	 * @return The action.
	 */
	public AbstractAction getAction()
	{
		return rAction;
	}
	
    /**
     * Sets the current input to the specified input.
     * This method should be called before the getSupport method is called.
     * @param input The input to set as the current input.
     */
	public void setCurrentInput (DimensionValueCollection input)
	{
		CurrentInput = input;
	}
	
	/**
	 * Checks if the condition is matched given the new input.
	 * @param TimeStamp The current time stamp.
	 * @return True if the condition is matched, otherwise false.
	 */
	public boolean checkEligibility (long TimeStamp)
	{
		if(Math.abs(getSupport(CurrentInput) - PARTIAL_MATCH_THRESHOLD) <= GLOBAL_SUPPORT_EPSILON)
		{
			LastMatch = TimeStamp;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Checks if the condition is matched given the new input.
	 * @return True if the condition is matched, otherwise false.
	 */
	public boolean checkEligibility ()
	{
		if(Math.abs(getSupport(CurrentInput) - PARTIAL_MATCH_THRESHOLD) <= GLOBAL_SUPPORT_EPSILON)
		{
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Checks to see if the condition of the specified rule is covered by this rule and 
	 * that the two rules have the same action.
	 * @param r The rule to check against this rule.
	 * @return True if this rule covers the specified rule, otherwise false.
	 */
	public boolean covers (AbstractRule r)
	{
		if(! r.getAction().equals(rAction))
			return false;
		if(rCondition != null)
			return rCondition.covers(r.getCondition());
		return false;
	}
	
	/**
	 * Checks to see if the specified condition is covered by this rule and that the
	 * specified action is the same as this rule's action. 
	 * @param cond The condition to check against the condition of this rule.
	 * @param act The action to check.
	 * @return True if the condition of this rule covers the specified condition, otherwise false.
	 */
	public boolean covers (GeneralizedConditionChunk cond, AbstractAction act)
	{
		if(! act.equals(rAction))
			return false;
		if(rCondition != null)
			return rCondition.covers(cond);
		return false;
	}
	
	/**
	 * Checks to see if the specified object is a rule and checks to see if the condition and 
	 * action of that rule are equal to this rule.
	 * @param R The object to compare to this rule
	 * @return True if the object is equal to this rule, otherwise false.
	 */
	public boolean equals (Object R)
	{
		if(R == this)
			return true;
		if (!(R instanceof AbstractRule))
			return false;
		if(rCondition != null && rAction.equals(((AbstractRule)R).getAction()) && rCondition.equals(((AbstractRule)R).getCondition()))
			return true;
		else
			return false;
	}
	
	/**
	 * Checks to see if the specified condition and action are equal to the condition and 
	 * action of this rule. 
	 * @param cond The condition to check.
	 * @param act The action to check.
	 * @return True if the condition and action are equal to the specified condition
	 * and action, otherwise false.
	 */
	public boolean equals (GeneralizedConditionChunk cond, AbstractAction act)
	{
		if(cond != null && act != null)
			return cond.equals(rCondition) && 
				act.equals(rAction);
		else
			return false;
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
     * Gets the immediate feedback that was last provided to the rule. This method is only 
     * used if feedback is being provided.
     * @return The feedback.
     */
    public double getFeedback()
    {
    	return Feedback;
    }
    
    /**
     * Sets the immediate feedback for the rule. This method should be called before the
     * updateMatchStatistics method is called. This method is only used if feedback is 
     * being provided.
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
	 * Sets the partial match threshold to the default value when partial match is turned on.
	 * By default partial match is turned off. This method must be called before any instances of a
	 * rule are initialized.
	 */
	public static void turnPartialMatchON ()
	{
		GLOBAL_PARTIAL_MATCH_THRESHOLD = DEAFUALT_PARTIAL_MATCH_ON;
	}
	
	/**
	 * Gets the number of child rules in top level of the child rule tree.
	 * @return The number of child rules.
	 */
	public int getNumChildren ()
	{
		if(Children != null)
			return Children.size();
		else return 0;
	}
	
	/**
	 * Gets a collection of the children of this rule.
	 * @return A collection the children of this rule.
	 */
	public Collection <AbstractRule> getChildren()
	{
		return Children;
	}
	
	/**
	 * Initializes the child rules collection.
	 */
	public void initChildren()
	{
		Children = new HashSet<AbstractRule> ();
	}
	
	/**
	 * Resets the child collection.
	 */
	public void resetChildren()
	{
		if(Children != null)
			Children.clear();
		Children = null;
	}
	
	/**
	 * Copies the rule.
	 * @return A copy of the rule.
	 */
	public abstract AbstractRule clone ();
	
	public String toString()
	{
		String s = "";
		if(rCondition != null)
			s = "Condition:\n" + rCondition.toString();
		else
			s = "Condition: NULL\n";
		s+= "\nAction:\n\t"  + rAction.toString();
		return s;
	}
}
