package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements a chunk within CLARION. It extends the DimensionValueCollection class.
 * This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * In it's most basic form, a chunk is essentially just a dimension-value collection that contains an ID. It is
 * a representational object used most often by the top level of the various CLARION subsystems. In addition, some
 * chunks may be linked to the output side of various explicit and implicit modules and can be used to pass pertinent 
 * information and instructions between the subsystems.
 * <p>
 * It is required that you specify a way of identifying the chunk (eg. name, number, etc) when 
 * you initialize this class, although there are no restrictions as to the type of identification you use. 
 * Once this ID is set, it cannot be changed. This ID is used as the key for this chunk when it is placed inside a map.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractAction</li>
 * <li>AbstractOutputChunk</li>
 * <li>DimensionlessOutputChunk</li>
 * <li>DriveStrength</li>
 * <li>ExternalAction</li>
 * <li>Goal</li>
 * <li>GoalAction</li>
 * <li>WorkingMemoryAction</li>
 * </ul>
 * @version 6.0.6
 * @author Nick Wilson
 */
public abstract class AbstractChunk extends DimensionValueCollection {
	private static final long serialVersionUID = -1327318705390162332L;

	/**Identifies the chunk. A name (in the form of a string) is simply one option for identifying a
	 * chunk.*/
	protected Object ID;	
	
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
	protected TimeStampCollection T = new TimeStampCollection ();
	
	/**The latest time stamp.*/
	protected Long LatestTimeStamp;
	
	/**
	 * Initializes the chunk with the ID specified.
	 * @param id The ID of the chunk.
	 */
	public AbstractChunk (Object id)
	{
		ID = id;
	}
	
	/**
	 * Initializes the chunk with the specified ID and dimensions.
	 * @param id The ID of the chunk.
	 * @param dims The dimensions for the chunk.
	 */
	public AbstractChunk (Object id, Collection <? extends Dimension> dims)
	{
		super(dims);
		ID = id;
	}
	
	/**
	 * Initializes the chunk with the specified ID and map of dimensions.
	 * @param id The ID of the chunk.
	 * @param dims The map of dimensions for the chunk.
	 */
	public AbstractChunk (Object id, Map <? extends Object, ? extends Dimension> dims)
	{
		super(dims);
		ID = id;
	}
	
	/**
	 * Gets the ID of this chunk.
	 * @return The ID of this chunk.
	 */
	public Object getID ()
	{
		return ID;
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
	 * Adds a time stamp to this dimension-value collection. The time stamp is used for calculating BLA.
	 * @param stamp The time stamp to add.
	 */
	public void addTimeStamp (long stamp)
	{
		T.add(stamp);
		LatestTimeStamp = stamp;
	}
	
	/**
	 * Gets the latest time stamp of this dimension-value collection.
	 * @return The latest time stamp.
	 */
	public Long getLatestTimeStamp ()
	{
		return LatestTimeStamp;
	}
	
	/**
	 * Checks to see if the specified chunk has the same ID as this chunk.
	 * @param chunk The chunk whose ID you want to compare to this chunk's ID.
	 * @return True if the specified chunk has the same ID as this chunk, otherwise false.
	 */
	public boolean equalsID (Object chunk)
	{
		if(chunk == this)
			return true;
		if(!(chunk instanceof AbstractChunk))
			return false;
		if(ID == null && ((AbstractChunk)chunk).ID == null)
			return true;
		if((((AbstractChunk)chunk).ID == null && ID != null) || (ID == null && ((AbstractChunk)chunk).ID != null))
			return false;
		return ((AbstractChunk)chunk).ID.equals(ID);
	}
	
	/**
	 * Checks to see if the specified object is a chunk and if the ID of 
	 * the specified chunk is the same as this chunk. It also checks to make sure
	 * all of the dimensions within the chunk are equal.
	 * @param chunk The object to compare to this chunk.
	 * @return True if the two chunks are equal, otherwise false.
	 */
	public boolean equals (Object chunk)
	{
		if(chunk == this)
			return true;
		if(!(chunk instanceof AbstractChunk))
			return false;
		if(!equalsID(chunk))
			return false;
		for (Map.Entry<? extends Object, ? extends Dimension> e : ((AbstractChunk)chunk).entrySet())
			if(!containsKey(e.getKey()) || !containsValue(e.getValue()))
				return false;
		return true;
	}
	
	public abstract AbstractChunk clone ();
	
	public String toString()
	{
		if(!(ID == null) && !(ID instanceof AbstractChunk))
			return "Chunk ID - " + ID.toString() + ":\n" + super.toString();
		return "Chunk ID - ?:\n" + super.toString();
	}
}
