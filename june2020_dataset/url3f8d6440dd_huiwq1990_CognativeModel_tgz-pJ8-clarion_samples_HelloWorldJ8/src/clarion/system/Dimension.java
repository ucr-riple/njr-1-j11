package clarion.system;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class implements a dimension within CLARION. It extends the LinkedHashMap class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A dimension is a somewhat meaningless thing unless it is filled with values. A coupling of a 
 * dimension and a value together is called a dimension-value pair and is the base unit 
 * in CLARION for representing information.
 * <p>
 * Examples of dimension-value pairs:
 * <ul>
 * <li>(Dimension: Color, Value: Red)</li>
 * <li>(Dimension: Shape, Value: Round)</li>
 * <li>(Dimension: Length, Value: Long)</li>
 * <li>(Dimension: Number, Value: 4)</li>
 * <li>(Dimension: 4, Value: 0)</li>
 * </ul>
 * It is required that you specify a way of identifying the dimension (eg. name, number, etc) when 
 * you initialize this class. Once this ID has been set, it cannot be changed. This ID is used as the 
 * key for this dimension when it is placed inside a dimension-value collection.
 * @version 6.0.4
 * @author Nick Wilson
 */
public final class Dimension extends LinkedHashMap <Object,Value> implements InterfaceTracksTime{
	private static final long serialVersionUID = 5234149809117814544L;
	
	/**Identifies the dimension. A name (in the form of a string) is simply one option for 
	 * identifying a dimension.*/
	private Object ID;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
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
	 * Initializes the dimension and sets the ID of the dimension to the ID specified.
	 * @param id The ID of the dimension.
	 */
	public Dimension (Object id)
	{
		ID = id;
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the dimension with the specified ID and collection of values.
	 * @param id The ID of the dimension.
	 * @param vals The values for the dimension.
	 */
	public Dimension (Object id, Collection <? extends Value> vals)
	{
		this(id);
		for(Value v : vals)
			put(v.getID(),v);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the dimension with the specified ID and map of values.
	 * @param id The ID of the dimension.
	 * @param map The map of values for the dimension.
	 */
	public Dimension (Object id, Map <? extends Object, ? extends Value> map)
	{
		this(id);
		putAll(map);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Gets the ID of this dimension.
	 * @return The ID of this dimension.
	 */
	public Object getID ()
	{
		return ID;
	}
	
	/**
	 * Gets the number of fully activated values in this dimension.
	 * @return The number of fully activated values in this dimension.
	 */
	public int getNumFullyActivatedVals ()
	{
		int count = 0;
		for(Value i : values())
			if(i.isFullyActivated())
				++count;
		return count;
	}
	
	/**
	 * Gets the number of values in this dimension that are at least minimally activated.
	 * @return The number of activated values in this dimension.
	 */
	public int getNumActivatedVals ()
	{
		int count = 0;
		for(Value i : values())
			if(i.isActivated())
				++count;
		return count;
	}
	
	/**
	 * Checks to see if all of the values in a specified dimension are activated.
	 * @return True if every value in the specified dimension is activated, otherwise
	 * false.
	 */
	public boolean checkMatchAll ()
	{
		for(Value i : values())
		{
			if(!i.isFullyActivated())
				return false;
		}
		return true;
	}
	
	/**
	 * Gets the activation level of the value with the highest activation in this dimension.
	 * @return The activation level of the value with the highest activation.
	 */
	public double getMaxActivation()
	{
		double Max = 0;
		for(Value i : values())
		{
			if(i.getActivation() > Max)
				Max = i.getActivation();
		}
		
		return Max;
	}
	
	/**
	 * Puts the value in the dimension as long as the value is not already in the dimension.
	 * If the value is already in the dimension, this method throws an exception. If the specified
	 * key is not the ID of the specified value, this method throws an exception.
	 * @param key The key with which the specified value is to be associated. This MUST be the ID
	 * of the specified value.
	 * @param val The value to add to the dimension.
	 * @return The result of putting the value in the dimension. This will always return null (meaning
	 * the value did not previously exist in the map). This is because you are not allowed to put a 
	 * value in a dimension that already contains that value.
	 * @throws IllegalArgumentException If the value is already in the dimension or the specified key
	 * is not the ID of the specified value.
	 */
	public Value put (Object key, Value val) throws IllegalArgumentException
	{
		if(containsKey(key) || containsValue(val) || (key == null && val.getID() != null) || (key != null && !key.equals(val.getID())))
			throw new IllegalArgumentException ("The specified value is already in this dimension or " +
					"the specified key is not the ID of the specified value.");
		return super.put(key,val);
	}
	
	/**
	 * Puts all of the values in the map into the dimension as long as the values are not already 
	 * in the dimension. If any values are already in the dimension, this method throws an 
	 * exception. If any of the specified keys are not the ID of their respective value, this method 
	 * throws an exception.
	 * @param map The map of values to add.
	 * @throws IllegalArgumentException If any values are already in the dimension or any of the 
	 * specified keys are not the ID of their respective value.
	 */
	public void putAll (Map <? extends Object, ? extends Value> map)
	{
		for(Map.Entry<? extends Object, ? extends Value> e : map.entrySet())
			put(e.getKey(),e.getValue());
	}
	
	/**
	 * Checks to see if the specified dimension has the same ID as this dimension.
	 * @param dim The dimension whose ID you want to compare to this dimension's ID.
	 * @return True if the specified dimension has the same ID as this dimension, otherwise false.
	 */
	public boolean equalsID (Object dim)
	{
		if(dim == this)
			return true;
		if(!(dim instanceof Dimension))
			return false;
		if(ID == null && ((Dimension)dim).ID == null)
			return true;
		if((((Dimension)dim).ID == null && ID != null) || (ID == null && ((Dimension)dim).ID != null))
			return false;
		return ((Dimension)dim).ID.equals(ID);
	}
	
	/**
	 * Checks to see if the specified object is a dimension and if the ID of 
	 * the specified dimension is the same as this dimension. It also checks to make sure
	 * all of the values within the dimension are equal.
	 * @param dim The object to compare to this dimension.
	 * @return True if the two dimensions are equal, otherwise false.
	 */
	public boolean equals (Object dim)
	{
		if(dim == this)
			return true;
		if(!(dim instanceof Dimension))
			return false;
		if(!equalsID(dim))
			return false;
		for (Map.Entry<? extends Object, ? extends Value> e : ((Dimension)dim).entrySet())
			if(!containsKey(e.getKey()) || !e.getValue().equals(get(e.getValue().getID())))
				return false;
		return true;
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
	 * Adds a time stamp to this dimension. The time stamp is used for calculating BLA.
	 * @param stamp The time stamp to add.
	 */
	public void addTimeStamp (long stamp)
	{
		T.add(stamp);
		LatestTimeStamp = stamp;
	}
	
	/**
	 * Gets the latest time stamp of this dimension.
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
	 * Clones the dimension (including all of it's Values).
	 * @return A copy of the dimension.
	 */
	public Dimension clone ()
	{
		Dimension a = new Dimension(ID);
		for(Value i : values())
		{
			Value v = i.clone();
			a.put(v.getID(),v);
		}
		a.hash = hash;
		a.INITIAL_BLA = INITIAL_BLA;
		a.C = C;
		a.D = D;
		a.T = T.clone();
		a.LatestTimeStamp = LatestTimeStamp;
		return a;
	}
	
	/**
	 * Returns the number of values in the dimension.
	 */
	public int size ()
	{
		return super.size();
	}
	
	public String toString ()
	{
		String s = "";
		boolean ma = false;
		if(ID != null && !(ID instanceof Dimension))
		{
			s = "Dimension ID - " + ID.toString() + ":\n";
			if(checkMatchAll())
				ma = true;
			for(Iterator <Value> i = values().iterator(); i.hasNext();)
			{
				s += "\t\t";
				if(!ma)
					s += i.next().toString();
				else
				{
					Value v = i.next();
					if(v.getID() != null || (v.getID() instanceof Dimension))
						s += "Value ID - "+ v.getID().toString() + ": *";
					else
						s += "Value ID - ?: *";
				}
				if(i.hasNext())
					s+= "\n";
			}
			return s;
		}
		s = "Dimension ID ?:\n";
		if(checkMatchAll())
			ma = true;
		for(Iterator <Value> i = values().iterator(); i.hasNext();)
		{
			s += "\t\t";
			if(!ma)
				s += i.next().toString();
			else
			{
				Value v = i.next();
				if(v.getID() != null || (v.getID() instanceof Dimension))
					s += "Value ID - "+ v.getID().toString() + ": *";
				else
					s += "Value ID - ?: *";
			}
			if(i.hasNext())
				s+= "\n";
		}
		return s;
	}
}