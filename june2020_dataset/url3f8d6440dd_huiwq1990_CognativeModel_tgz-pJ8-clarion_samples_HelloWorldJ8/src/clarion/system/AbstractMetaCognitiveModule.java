package clarion.system;

public abstract class AbstractMetaCognitiveModule {
	
	/**The implicit module used for performing meta-cognition.*/
	protected AbstractImplicitModule ImplicitModule;
	
	/**Default Eligibility. Used for determining eligibility (true by default).*/
	public static boolean GLOBAL_DEFAULT_ELIGIBILITY = true;
	/**Default Eligibility. Used for determining eligibility (true by default).*/
	public boolean DEFAULT_ELIGIBILITY = GLOBAL_DEFAULT_ELIGIBILITY;
	
	/**
	 * Initializes the meta-cognitive module given the implicit module specified.
	 * <p>
	 * The input nodes of the specified implicit module must be specified as Value 
	 * objects that have the same ID as the drives, goals, etc. that you wish to use for 
	 * performing meta-cognition.
	 * @param im The implicit module to use.
	 */
	public AbstractMetaCognitiveModule (AbstractImplicitModule im)
	{
		ImplicitModule = im;
	}
	
	public abstract void performMetaCognition(DimensionValueCollection ModuleInput, long TimeStamp);
	
	/**
	 * Gets the implicit module used by this meta-cognitive module. This method is primarily 
	 * meant for internal purposes. It should NOT be used outside of the CLARION Library for purposes 
	 * other than reporting the internal state.
	 * @return The implicit module used by this module.
	 */
	public AbstractImplicitModule getImplicitModule ()
	{
		return ImplicitModule;
	}
	
	/**
	 * Checks to see if a meta-cognitive module is eligible to be used at a given step. If you wish to implement
	 * an eligibility condition for a meta-cognitive module, this method should be be overridden.
	 * @return The default eligibility (as specified by the DEFAULT_ELIGIBILITY parameter).
	 */
	public boolean checkEligibility()
	{
		return DEFAULT_ELIGIBILITY;
	}
	
	/**
	 * Checks to see if a meta-cognitive module is eligible to be used at a given step. If you wish to implement
	 * an eligibility condition for a meta-cognitive module, this method should be be overridden.
	 * @param stamp The current time stamp.
	 * @return The default eligibility (as specified by the DEFAULT_ELIGIBILITY parameter).
	 */
	public boolean checkEligibility(long stamp)
	{
		return DEFAULT_ELIGIBILITY;
	}
}
