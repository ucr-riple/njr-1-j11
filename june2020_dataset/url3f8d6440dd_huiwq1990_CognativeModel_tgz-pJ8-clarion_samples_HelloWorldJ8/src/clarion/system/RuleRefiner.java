package clarion.system;

/**
 * This class contains static methods used to handle rule refinement in CLARION.
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of the rule refiner are initialized. The classes within the CLARION Library that
 * currently instantiate a rule refiner are:
 * <ul><li>The ACS</li></ul>
 * @author Nick Wilson
 */
/**
 * This class implements a rule refiner within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class contains static methods used to handle the refinement (generalization and specialization)
 * of any rules that extend the RefineableRule class.
 * <p>
 * The rule refiner has two options for calculating information gain (as part of rule refinement).
 * Those options are:
 * <ul>
 * <li><b>IG(A,B) = Log_base_2(A) - Log_base_2(B)</b></li>
 * <li><b>IG(A) = Log_base_2(A)</b></li>
 * <i>where <b>A,B = (PM + C1)/(PM + NM + C2)</i>
 * </ul>
 * <p>
 * The first option is just the standard formula for calculating information gain. The second method
 * method is the equivalent to: <b>IG(A, Perfect)</b> where "Perfect" is assumed to be a rule that has perfect
 * match statistics (all positive). When a rule is considered "perfect", the log equation that is used
 * to calculate information gain for that rule will approach 0. Therefore this method for calculating
 * information gain needs to only calculate the log equation for the rule A.
 * <p>
 * <b>Classes that currently instantiate a rule extractor are:</b><br>
 * <ul>
 * <li>ACS</li>
 * </ul>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.4
 * @author Nick Wilson
 */
public final class RuleRefiner {
	/**The options that can be used when calculating information gain for IG(C,ALL)*/
	public enum IG_OPTIONS {MATCH_ALL, PERFECT};
	
	/**The option to use when calculation information gain for IG(C,ALL)*/
	public static IG_OPTIONS GLOBAL_IG_OPTION = IG_OPTIONS.MATCH_ALL;
	/**The option to use when calculation information gain for IG(C,ALL)*/
	public IG_OPTIONS IG_OPTION = GLOBAL_IG_OPTION;
	
	/**The threshold IG(C,All) must pass for a rule to be generalized*/
	public static double GLOBAL_GENERALIZATION_THRESHOLD1 = 4;
	/**The threshold IG(C,All) must pass for a rule to be generalized*/
	public double GENERALIZATION_THRESHOLD1 = GLOBAL_GENERALIZATION_THRESHOLD1;
	/**The threshold IG(C',C) must pass for a rule to be generalized*/
	public static double GLOBAL_GENERALIZATION_THRESHOLD2 = 0;
	/**The threshold IG(C',C) must pass for a rule to be generalized*/
	public double GENERALIZATION_THRESHOLD2 = GLOBAL_GENERALIZATION_THRESHOLD2;
	/**The threshold IG(C,All) must be below for a rule to be specialized*/
	public static double GLOBAL_SPECIALIZATION_THRESHOLD1 = 1;
	/**The threshold IG(C,All) must be below for a rule to be specialized*/
	public double SPECIALIZATION_THRESHOLD1 = GLOBAL_SPECIALIZATION_THRESHOLD1;
	/**The threshold IG(C',C) must be below for a rule to be specialized*/
	public static double GLOBAL_SPECIALIZATION_THRESHOLD2 = 0;
	/**The threshold IG(C',C) must be below for a rule to be specialized*/
	public double SPECIALIZATION_THRESHOLD2 = GLOBAL_SPECIALIZATION_THRESHOLD2;
	
	
	/**Constant for Information Gain*/
	public static double GLOBAL_C1 = 1;
	/**Constant for Information Gain*/
	public static double GLOABAL_C2 = 2;
	/**Constant for Information Gain*/
	public double C1 = GLOBAL_C1;
	/**Constant for Information Gain*/
	public double C2 = GLOABAL_C2;
	
	/**
	 * Generalizes the specified rule (if possible).
	 * @param R The rule to Generalize.
	 * @return R if the rule does not meet the criteria for generalization (or if the rule is not
	 * generalizable), or a generalized rule if R meets the criteria for generalization.
	 */
	public AbstractRule generalize (AbstractRule R)
	{
		if(!(R instanceof RefineableRule))
			return R;
		if((IG_OPTION == IG_OPTIONS.MATCH_ALL && 
				(calculateInformationGain(R, ((RefineableRule)R).getMatchAll()) > GENERALIZATION_THRESHOLD1)) ||
			(IG_OPTION == IG_OPTIONS.PERFECT && (calculateInformationGain(R) > GENERALIZATION_THRESHOLD1)))
		{
			AbstractRule r = ((RefineableRule)R).getMaxVariationPlusOne(this);
			if(r == null)
				return R;
			if(calculateInformationGain(r, R) >= GENERALIZATION_THRESHOLD2)
			{
				return r;
			}
		}
		return R;
	}
	
	/**
	 * Specializes the specified rule (if possible).
	 * @param R The rule to specialize.
	 * @return Null if the rule cannot be specialized, R if the rule does not meet the criteria for 
	 * specialization, or a specialized rule if R meets the criteria for specialization.
	 */
	public AbstractRule specialize (AbstractRule R)
	{
		if(!(R instanceof RefineableRule))
			return R;
		if((IG_OPTION == IG_OPTIONS.MATCH_ALL && 
				(calculateInformationGain(R, ((RefineableRule)R).getMatchAll()) < SPECIALIZATION_THRESHOLD1)) ||
			(IG_OPTION == IG_OPTIONS.PERFECT && (calculateInformationGain(R) < SPECIALIZATION_THRESHOLD1)))
		{
			AbstractRule r = ((RefineableRule)R).getMaxVariationMinusOne(this);
			if(r == null)
			{
				return null;
			}
			else if(calculateInformationGain(r, R) > SPECIALIZATION_THRESHOLD2)
			{
				return r;
			}
			else
				return null;
		}
		return R;
	}
	
	/**
	 * Calculates the information gain using the match statistics of two specified rules.
	 * @param A The first rule.
	 * @param B The second rule.
	 * @return The information gain measure.
	 */
	public double calculateInformationGain (AbstractRule A, AbstractRule B)
	{
		double a = Math.log((double)(A.getPM() + C1)/(double)(A.getPM() + A.getNM() + C2))/Math.log(2);
		double b = Math.log((double)(B.getPM() + C1)/(double)(B.getPM() + B.getNM() + C2))/Math.log(2);
		return  a - b;
	}
	
	/**
	 * Calculates the information gain using the match statistics of the specified rule. The result from this
	 * method is the equivalent to: IG(A, Perfect) where "Perfect" is assumed to be a rule that has perfect
	 * match statistics (all positive). When a rule is considered "perfect", the log equation that is used
	 * to calculate information gain for that rule will approach 0. Therefore this method for calculating
	 * information gain needs only calculate the log equation for the rule A.
	 * @param A The rule to use for calculating information gain against the "perfect" rule.
	 * @return The information gain measure.
	 */
	public double calculateInformationGain (AbstractRule A)
	{
		double a = Math.log((double)(A.getPM() + C1)/(double)(A.getPM() + A.getNM() + C2))/Math.log(2);
		return a;
	}
}
