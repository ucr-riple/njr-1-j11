package clarion.system;

/**
 * This class implements an IRL rule within CLARION. It extends the RefineableRule class.
 * This class is abstract and therefore cannot be instantiated on its own. All user defined 
 * IRL rules MUST extend this class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * IRL (Independent Rule Learning) rules are rules within CLARION that are more complex than simple 
 * Condition ---> Action pairings but are not as complex as fixed rules. Their complexity mostly lies in how 
 * conditions are matched.
 * <p>
 * Some examples of IRL rules include:<br>
 * <ul>
 * <li>If one value of a dimension-value pair in the state is activated then another dimension-value pair 
 * in another dimension must also be activated.</li>
 * <li>If none of the dimension-value pairs of one dimension are activated, then none of the dimension-value
 * pairs of another dimension can be activated. </li>
 * <li>If a given dimension will accept any activated dimension-value pair, then another dimension cannot
 * accept a certain dimension-value pair.</li>
 * <li>Etc.</li>
 * </ul>
 * IRL rules MUST contain both an action and condition. They are very similar in nature
 * to RER rules (in that they can be refined and deleted) except that their condition matching
 * operations are user defined.
 * <p>
 * All condition matching operations performed by the IRL rule must be specified 
 * within the "getSupport" method. The getSupport method returns a value which is equal to the support
 * for the rule. IRL rules are no different than any other type of rule in terms of selection.
 * They will compete with all other rules for selection based on their support and utility.
 * @version 6.0.4
 * @author Nick Wilson
 */
public abstract class AbstractIRLRule extends RefineableRule{
	
	/**
	 * Initializes an IRL rule with the condition and action specified .
	 * @param cond The condition of the rule.
	 * @param act The action of the rule.
	 */
	public AbstractIRLRule (GeneralizedConditionChunk cond, AbstractAction act)
	{
		super(cond,act);
	}
	
	/**
	 * Determines the rule support for the IRL rule. This method must be specified 
	 * by the user to handle matching of the current input to the rule.
	 * @param CurrentInput The current input.
	 * @return The support for the IRL rule.
	 */
	public abstract double getSupport (DimensionValueCollection CurrentInput);
}
