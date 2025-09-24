package clarion.system;

/**
 * This class implements a fixed rule within CLARION. It extends the AbstractRule class.
 * This class is abstract and therefore cannot be instantiated on its own. All user defined 
 * fixed rules MUST extend this class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Fixed rules are rules within CLARION that are more complex than a simple Condition ---> Action pairing.
 * Some examples of fixed rules include:<br>
 * <ul>
 * <li>If two dimension-value pairs within the state are activated, then provide full support for the action
 * chunk that is equal to the product of the two activated dimension-value pairs in the condition.</li>
 * <li>Provide full support for the action chunk that is equal to the sum of all the activated values in the current
 * input.</li>
 * <li>If a given input dimension has any value activated, then provide full/no support for a particular action chunk.</li>
 * <li>etc.</li>
 * </ul>
 * <p>
 * All operations performed by the fixed rule (including matching the condition) must be specified 
 * within the "getSupport" method. The getSupport method returns a value which is equal to the support
 * for the rule. Fixed rules are no different than any other type of rule in terms of selection. They
 * will compete with the other rules for selection based on their support and/or utility.
 * <p>
 * Fixed rules must contain an action chunk. However, because fixed-rules require that the user override the getSupport 
 * method, they do not require a condition chunk be specified. Note, thought, that only 1 condition-less fixed rule is 
 * allowed to be provided to the system for any given action. This is only the case for a fixed rule that does not have 
 * a condition.
 * <p>
 * By convention, fixed rules should NOT edit their actions other than to set the activation or selection measures of the
 * action itself. If you want to set conditions for a fixed rule that effect how actions are performed, you should setup a 
 * different fixed rule for EACH variation of the action that is affected by the conditions. For example:
 * <p>
 * <b><u>INCORRECT</u></b>
 * <i>
 * <ul><li>If a particular value within a particular dimension of the input is activated/de-activated, then activate/de-activate 
 * a value for a particular dimension in the action.</li></ul></i>
 * <p>
 * <b><u>CORRECT</u></b>
 * <i>
 * <ol>
 * <li>Initialize a separate action for each variation of the set of actions your fixed rules will be addressing 
 * (e.g., RESPOND_1, RESPOND_2, etc.)</li>
 * <li>Initialize an instance of your fixed rule for EACH action generated in step 1.
 * <li>In the getSupport method of the fixed rule, setup an algorithm that checks to see if a particular value within a 
 * particular dimension of the input is activated/de-activated. If it is, then return full support (typically 1.0) for the
 * instance of the fixed rule whose action has the desired value activated within the dimension to which you 
 * are interested. Otherwise, if the particular instance of the fixed rule does not have the correct action given the
 * algorithm performed in the getSupport function then return no support (typically 0.0) for that rule.</li>
 * </ol>
 * </i>
 * Note that as an option you can also override the getUtility function. By default the getUtility function
 * for a fixed rule will simply return the value of the DEFAULT_UTILITY constant.
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of any subclasses of this class are initialized. Changing constants in this class will
 * also change those constant values for all classes that extend from this class.
 * @version 6.0.6
 * @author Nick Wilson
 */
public abstract class AbstractFixedRule extends AbstractRule{
	
	/**The default utility for the fixed rule.*/
	public static double GLOBAL_DEFAULT_UTILITY = 1;
	/**The default utility for the fixed rule.*/
	public double DEFAULT_UTILITY = GLOBAL_DEFAULT_UTILITY;
	
	
	/**
	 * Initializes a condition-less fixed rule with the specified action.
	 * <p>
	 * Note that only 1 condition-less fixed rule is allowed for each action. This is only the 
	 * case for a fixed rule that does not have a condition.
	 * @param act The action for the new rule.
	 */
	public AbstractFixedRule (AbstractAction act)
	{
		super(null, act);
	}
	
	/**
	 * Initializes the fixed rule with the specified condition and action. This constructor
	 * is used in cases where you want to create a fixed rule with a standard condition chunk.
	 * @param cond The condition for the new rule.
	 * @param act The action for the new rule.
	 */
	public AbstractFixedRule (GeneralizedConditionChunk cond, AbstractAction act)
	{
		super(cond, act);
	}
	
	/**
	 * Performs the user specified operations for the fixed rule. This method is written by the 
	 * user to handle matching of the current input to the rule as well other operations as 
	 * deemed necessary.
	 * @param CurrentInput The current input.
	 * @return The support for the fixed rule.
	 */
	public abstract double getSupport (DimensionValueCollection CurrentInput);
	
	/**
	 * Gets the utility for this fixed rule. By default the utility of a fixed rule is always set to the
	 * default utility constant. However the user has the option of defining their own utility function by 
	 * simply overriding this method.
	 * @return The utility of the fixed rule.
	 */
	public double getUtility()
	{
		return DEFAULT_UTILITY;
	}
	
	/**
	 * This method is overridden by the user to handle condition matching when comparing
	 * two rules. The general rule of thumb is to have this method simply return false.
	 * @param R The object to compare to this rule.
	 * @return True if the specified object is equal to this rule, otherwise false.
	 */
	public abstract boolean equals (Object R);
	
	/**
	 * This method is overridden by the user to handle the comparing of two
	 * two rules to see if one covers the other. The general rule of thumb is to have this 
	 * method simply return false.
	 * @param R the rule to compare to this rule.
	 * @return True if this rule covers the specified rule, otherwise false.
	 */
	public abstract boolean covers (Object R);
}
