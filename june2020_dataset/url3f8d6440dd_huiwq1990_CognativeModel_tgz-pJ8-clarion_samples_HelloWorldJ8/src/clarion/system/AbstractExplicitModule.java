package clarion.system;

/**
 * This class implements an explicit module within CLARION.
 * This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that extends an abstract explicit module can be used in the top level of the CLARION subsystems. This class
 * acts as the base foundation for building explicit modules to use in the top level. All modules that are used within the
 * top level of CLARION MUST extend from this class.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractFixedRule</li>
 * <li>AbstractIRLRule</li>
 * <li>AbstractRule</li>
 * <li>RefineableRule</li>
 * </ul>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of any subclasses of this class are initialized. Changing constants in this class will
 * also change those constant values for all classes that extend from this class.
 * @version 6.0.6
 * @author Nick Wilson
 */
public abstract class AbstractExplicitModule{
	
	/**The initalBLA. Used to calculate BLA*/
	public static double GLOBAL_INITIAL_BLA = 0;
	/**The initalBLA. Used to calculate BLA*/
	public double INITIAL_BLA = GLOBAL_INITIAL_BLA;
	/**Time stamps for each usage. Used for calculating BLA.*/
	protected TimeStampCollection T = new TimeStampCollection ();
	/**The latest time stamp.*/
	protected Long LatestTimeStamp;
	/**Constant c. Used for calculating BLA*/
	public static double GLOBAL_C = 2;
	/**Constant d. Used for calculating BLA*/
	public static double GLOBAL_D = 0.5;
	/**The weight. Used to calculate support.*/
	public static double GLOBAL_WEIGHT = 1;
	/**Constant c. Used for calculating BLA*/
	public double C = GLOBAL_C;
	/**Constant d. Used for calculating BLA*/
	public double D = GLOBAL_D;
	/**The weight. Used to calculate support.*/
	public double WEIGHT = GLOBAL_WEIGHT;
	
	/**Perception time. Used for calculating response time.*/
	public static double GLOBAL_PERCEPTION_TIME = .200;
	/**Perception time. Used for calculating response time.*/
	public double PERCEPTION_TIME = GLOBAL_PERCEPTION_TIME;
	/**Decision time. Used for calculating response time.*/
	public static double GLOBAL_DECISION_TIME = .350;
	/**Decision time. Used for calculating response time.*/
	public double DECISION_TIME = GLOBAL_DECISION_TIME;
	/**Actuation time. Used for calculating response time.*/
	public static double GLOBAL_ACTUATION_TIME = .500;
	/**Actuation time. Used for calculating response time.*/
	public double ACTUATION_TIME = GLOBAL_ACTUATION_TIME;
	/**Default Eligibility. Used for determining eligibility (true by default).*/
	public static boolean GLOBAL_DEFAULT_ELIGIBILITY = true;
	/**Default Eligibility. Used for determining eligibility (true by default).*/
	public boolean DEFAULT_ELIGIBILITY = GLOBAL_DEFAULT_ELIGIBILITY;
	
	/**
	 * Gets the BLA based on the current time stamp.
	 * @param TimeStamp The current time step.
	 * @return The BLA.
	 */
	public double getBLA(long TimeStamp)
	{
		double BLA = 0;
		for(long i : T)
		{
			BLA += Math.pow(TimeStamp - i,-D);
		}
		BLA *= C;
		return BLA + INITIAL_BLA;
	}
	
	/**
	 * Gets the normalized BLA based on the current time stamp. Normalization is performed
	 * by translating the BLA using a standard sigmoid function.<br>
	 * In other words:<br>
	 * <b><i>Normalized_BLA = 1 / (1 + e^-BLA)</i></b>
	 * @param TimeStamp The current time step.
	 * @return The BLA.
	 */
	public double getNormalizedBLA(long TimeStamp)
	{
		return 1/(1 + Math.exp(-getBLA(TimeStamp)));
	}
	
	/**
	 * Adds a time stamp to this rule. The time stamp is used for calculating BLA.
	 * @param stamp The time stamp to add.
	 */
	public void addTimeStamp (long stamp)
	{
		T.add(stamp);
		LatestTimeStamp = stamp;
	}
	
	/**
	 * Gets the latest time stamp of this explicit module.
	 * @return The latest time stamp.
	 */
	public Long getLatestTimeStamp ()
	{
		return LatestTimeStamp;
	}
	
	/**
	 * Gets the response time.
	 * @return The response time.
	 */
	public double getResponseTime ()
    {
    	return PERCEPTION_TIME + DECISION_TIME + ACTUATION_TIME;
    }
	
	/**
	 * Checks to see if an explicit module is eligible to be used at a given step. If you wish to implement
	 * an eligibility condition for an explicit module, this method should be be overridden.
	 * @return The default eligibility (as specified by the DEFAULT_ELIGIBILITY parameter).
	 */
	public boolean checkEligibility()
	{
		return DEFAULT_ELIGIBILITY;
	}
	
	/**
	 * Checks to see if an explicit module is eligible to be used at a given step. If you wish to implement
	 * an eligibility condition for an explicit module, this method should be be overridden.
	 * @param stamp The current time stamp.
	 * @return The default eligibility (as specified by the DEFAULT_ELIGIBILITY parameter).
	 */
	public boolean checkEligibility(long stamp)
	{
		return DEFAULT_ELIGIBILITY;
	}
	
	public abstract double getUtility();
	
	public abstract double getSupport(DimensionValueCollection CurrentInput);
	
	public abstract boolean equals (Object R);
}
