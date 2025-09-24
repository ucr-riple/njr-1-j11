package clarion.system;

/**
 * This class implements a value within CLARION. 
 * It implements the cloneable and comparable interfaces.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The value class is used to define the possible values of a dimension. It is a somewhat meaningless 
 * object unless it is defined within a dimension. A coupling of a dimension and a value together is 
 * called a dimension-value pair and is the base unit in CLARION for representing information.<br>
 * Examples of dimension-value pairs:<br>
 * <ul>
 * <li>(Dimension: Color, Value: Red)</li>
 * <li>(Dimension: Shape, Value: Round)</li>
 * <li>(Dimension: Length, Value: Long)</li>
 * <li>(Dimension: Number, Value: 4)</li>
 * </ul>
 * <p>
 * It is required that you specify a way of identifying the value (eg. name, number, etc) when you 
 * initialize this class. Once this ID has been set, it cannot be changed. This ID is used as the key
 * for this value when it is placed inside a dimension.
 * <p>
 * The activation of a value is 0 by default and (although not required) is usually set to
 * either 0 (the value is not activated) or 1 (the value is activated).<br>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class Value implements Cloneable,Comparable <Value>, InterfaceTracksTime{
	
	/**Identifies the value. A name (in the form of a string) is simply one option for identifying a
	 * value.*/
	protected Object ID;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	protected int hash;
	
	/**The activation of the value.*/
	protected double Activation;
	
	/**The minimum threshold a dimension-value pair activation must be above to be
	 * considered activated.*/
	public static double GLOBAL_MINIMUM_ACTIVATION_THRESHOLD = 0;
	/**The threshold a dimension-value pair must be activated to in order to be considered 
	 * fully activated.*/
	public static double GLOBAL_FULL_ACTIVATION_THRESHOLD = 1;
	/**How close two activations must be in order to be considered equal. Needed due to the nature
	 * of double precision arithmetic.*/
	public static double GLOBAL_ACTIVATION_EPSILON = .0001;
	/**The minimum threshold a dimension-value pair activation must be above to be
	 * considered activated.*/
	public double MINIMUM_ACTIVATION_THRESHOLD = GLOBAL_MINIMUM_ACTIVATION_THRESHOLD;
	/**The threshold a dimension-value pair must be activated to in order to be considered 
	 * fully activated.*/
	public double FULL_ACTIVATION_THRESHOLD = GLOBAL_FULL_ACTIVATION_THRESHOLD;
	
    /**The initalBLA. Used to calculate BLA*/
	public static double GLOBAL_INITIAL_BLA = 0;
	/**Constant c. Used for calculating BLA*/
	public static double GLOBAL_C = 2;
	/**Constant d. Used for calculating BLA*/
	public static double GLOBAL_D = 0.5;
	/**The initalBLA. Used to calculate BLA*/
	public double INITIAL_BLA = GLOBAL_INITIAL_BLA;
	/**Constant c. Used for calculating BLA*/
	public double C = GLOBAL_C;
	/**Constant d. Used for calculating BLA*/
	public double D = GLOBAL_D;
	
	/**Time stamps for each usage of the chunk. Used for calculating BLA. */
	private TimeStampCollection T = new TimeStampCollection ();
	
	/**The latest time stamp.*/
	private Long LatestTimeStamp;
	
	/**
	 * Initializes the value with the minimum activation and sets the ID to the object specified.
	 * @param id The ID of the value.
	 */
	public Value (Object id){
		ID = id;
		Activation = MINIMUM_ACTIVATION_THRESHOLD;
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the value with the activation and ID specified.
	 * @param id The ID of the value.
	 * @param act The activation level.
	 */
	public Value (Object id, double act)
	{
		ID = id;
		Activation = act;
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Gets the ID of this value.
	 * @return The ID of this value.
	 */
	public Object getID ()
	{
		return ID;
	}
	
	/**
	 * Gets the activation level for this value.
	 * @return The activation level.
	 */
	public double getActivation ()
	{
		return Activation;
	}
	
	/**
	 * Sets the activation level for this value.
	 * @param act The activation level to set.
	 */
	public void setActivation (double act)
	{
		Activation = act;
	}
	
	/**
	 * Resets the activation.
	 */
	public void resetActivation ()
	{
		Activation = MINIMUM_ACTIVATION_THRESHOLD;
	}
	
	/**
	 * Checks to see if the activation of the value is greater than the minimum activation threshold.
	 * @return True if the value is at least minimally activated, otherwise false.
	 */
	public boolean isActivated()
	{
		if(Activation - MINIMUM_ACTIVATION_THRESHOLD > GLOBAL_ACTIVATION_EPSILON)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks to see if the activation of the value is greater than or equal to the full activation threshold.
	 * @return True if the value is fully activated, otherwise false.
	 */
	public boolean isFullyActivated()
	{
		if(Activation > FULL_ACTIVATION_THRESHOLD || Math.abs(Activation - FULL_ACTIVATION_THRESHOLD) <= GLOBAL_ACTIVATION_EPSILON)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks to see if the specified value has the same ID as this value.
	 * @param val The value whose ID you want to compare to this value's ID.
	 * @return True if the specified value has the same ID as this value, otherwise false.
	 */
	public boolean equalsID (Object val)
	{
		if(val == this)
			return true;
		if(!(val instanceof Value))
			return false;
		if(ID == null && ((Value)val).ID == null)
			return true;
		if((((Value)val).ID == null && ID != null) || (ID == null && ((Value)val).ID != null))
			return false;
		return ((Value)val).ID.equals(ID);
	}
	
	/**
	 * Checks to see if the specified object is a value and if the ID and activation of the 
	 * specified value is the same as this value.
	 * @param val The object to compare to this value.
	 * @return True if the two values are equal, otherwise false.
	 */
	public boolean equals (Object val)
	{
		if(val == this)
			return true;
		if(!(val instanceof Value))
			return false;
		if(!equalsID(val))
			return false;
		return Math.abs(((Value)val).Activation - Activation) <= GLOBAL_ACTIVATION_EPSILON;
	}
	
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
	 * Adds a time stamp to this value. The time stamp is used for calculating BLA.
	 * @param stamp The time stamp to add.
	 */
	public void addTimeStamp (long stamp)
	{
		T.add(stamp);
		LatestTimeStamp = stamp;
	}
	
	/**
	 * Gets the latest time stamp of this value.
	 * @return The latest time stamp.
	 */
	public Long getLatestTimeStamp ()
	{
		return LatestTimeStamp;
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the value.
	 * @return A copy of the value.
	 */
	public Value clone ()
	{
		Value a = new Value (ID, Activation);
		a.hash = hash;
		a.FULL_ACTIVATION_THRESHOLD = FULL_ACTIVATION_THRESHOLD;
		a.MINIMUM_ACTIVATION_THRESHOLD = MINIMUM_ACTIVATION_THRESHOLD;
		a.INITIAL_BLA = INITIAL_BLA;
		a.C = C;
		a.D = D;
		a.T = T.clone();
		a.LatestTimeStamp = LatestTimeStamp;
		return a;
	}
	
	/**
	 * Compares the activation of this value to the specified value. The two values must have
	 * the same ID to be comparable. If the specified value does not have the same ID as this
	 * value, this method will throw and exception.
	 * @param val The value to compare.
	 * @return A negative integer, 0, or positive integer if the activation of this value is 
	 * less than, equal to, or greater than the activation of the specified value.
	 * @throws ClassCastException if the specified value does not have the same ID as this value.
	 */
	public int compareTo (Value val) throws ClassCastException
	{
		if(!equalsID(val))
			throw new ClassCastException ("Cannot compare the specified value to this value." +
					" The specified value does not have the same ID as this value.");
		if(Math.abs(((Value)val).Activation - Activation) <= GLOBAL_ACTIVATION_EPSILON)
			return 0;
		if(((Value)val).Activation > Activation)
			return -1;
		else
			return 1;
	}
	
	public String toString()
	{
		if(!(ID == null) && !(ID instanceof Value))
			return "Value ID - " + ID.toString() + ": " + Activation;
		return "Value ID - ?: " + Activation;
	}
}
