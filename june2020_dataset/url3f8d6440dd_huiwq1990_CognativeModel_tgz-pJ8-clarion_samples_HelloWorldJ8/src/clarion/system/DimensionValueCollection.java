package clarion.system;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * This class implements a dimension-value collection within CLARION. It extends the LinkedHashMap class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class represents the base collection for representing information in the CLARION Library. It is used
 * and referenced heavily throughout the system. Contained within a dimension-value collection are dimension-value
 * pairs.
 * <p>
 * A dimension-value collection can be used for any of the following:
 * <ul>
 * <li>Defining chunks:</li>
 * <ul><li>Actions</li>
 * <li>Goals</li>
 * <li>Working memory items</li></ul>
 * <li>Sensory information</li>
 * <li>Conditions</li>
 * </ul>
 * <p>
 * Note that while we specify this class as containing "dimension-value pairs", it technically only contains
 * dimension (which in turn contains values).
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractAction</li>
 * <li>AbstractChunk</li>
 * <li>AbstractOutputChunk</li>
 * <li>DimensionlessOutputChunk</li>
 * <li>DriveStrength</li>
 * <li>ExternalAction</li>
 * <li>GeneralizedConditionChunk</li>
 * <li>Goal</li>
 * <li>GoalAction</li>
 * <li>WorkingMemoryAction</li>
 * </ul>
 * @version 6.0.6
 * @author Nick Wilson
 */
public class DimensionValueCollection extends LinkedHashMap <Object, Dimension>{
	private static final long serialVersionUID = 3273236403536852304L;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**
	 * Initializes a dimension-value collection.
	 */
	public DimensionValueCollection ()
	{
		super();
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes a dimension-value collection with the collection of dimensions specified.
	 * @param dims The dimensions for the collection.
	 */
	public DimensionValueCollection (Collection <? extends Dimension> dims)
	{
		super();
		for(Dimension d : dims)
			put(d.getID(),d);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the dimension-value collection with the map of dimensions.
	 * @param map The map of dimensions for the dimension-value collection.
	 */
	public DimensionValueCollection (Map <? extends Object, ? extends Dimension> map)
	{
		super();
		putAll(map);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Gets the number of dimension-value pairs.
	 * @return The number of dimension-value pairs.
	 */
	public int getNumDVPairs()
	{
		int count = 0;
		for(Dimension i : values())
			count += i.size();
		return count;
	}
	
	/**
	 * Puts the dimension into the dimension-value collection as long as the dimension is not already 
	 * in the dimension-value collection. If the dimension is already in the dimension-value 
	 * collection, this method throws an exception. If the specified key is not the ID of the specified
	 * dimension, this method throws an exception.
	 * @param key The key with which the specified dimension is to be associated. This MUST be the ID
	 * of the specified dimension.
	 * @param dim The dimension to add to the dimension-value collection.
	 * @return The result of putting the dimension in the dimension-value collection. This will 
	 * always return null (meaning the dimension did not previously exist in the map). This is 
	 * because you are not allowed to put a dimension into a dimension-value collection that already 
	 * contains that dimension.
	 * @throws IllegalArgumentException If the dimension is already in the dimension-value collection
	 * or the specified key is not the ID of the specified dimension.
	 */
	public Dimension put (Object key, Dimension dim) throws IllegalArgumentException
	{
		if(containsKey(key) || containsValue(dim) || (key == null && dim.getID() != null) || (key != null && !key.equals(dim.getID())))
			throw new IllegalArgumentException ("The specified dimension is already in this " +
					"dimension-value collection or the specified key is not the ID of the " +
					"specified dimension.");
		return super.put(key,dim);
	}
	
	/**
	 * Puts all of the dimensions in the map into the dimension-value collection as long as the 
	 * dimensions are not already in the dimension-value collection. If the specified map extends 
	 * from type AbstractChunk, this method will add the values from any dimensions within that
	 * chunk that are already in this collection to the appropriate dimension within this collection
	 * as long as the value is not already contained within that dimension.
	 * @param map The map of dimensions to add.
	 */
	public void putAll (Map <? extends Object, ? extends Dimension> map)
	{
		if(map instanceof AbstractChunk)
		{
			for(Dimension d : map.values())
			{
				Dimension dd = get(d.getID());
				if(dd != null)
				{
					for(Value v : d.values())
					{
						Value vv = dd.get(v.getID());
						if(vv != null)
						{
							vv.setActivation(v.getActivation());
						}
						else
							dd.put(v.getID(), v);
					}
				}
				else
					put(d.getID(),d);
			}
		}
		else
			for(Map.Entry<? extends Object, ? extends Dimension> e : map.entrySet())
				put(e.getKey(),e.getValue());
	}
	
	/**
	 * Checks to see if the specified object is a dimension-value collection and that all
	 * of the dimensions within the specified collection are equal to the dimensions
	 * in this collection.
	 * @param DVCollection The collection to compare to this collection.
	 * @return True if the two collections are equal, otherwise false.
	 */
	public boolean equals (Object DVCollection)
	{
		if (this == DVCollection)
			return true;
		if (!(DVCollection instanceof DimensionValueCollection))
			return false;
		if (DVCollection == null)
			return false;
		if (((DimensionValueCollection)DVCollection).size() != size())
			return false;
		for (Map.Entry<? extends Object, ? extends Dimension> e : ((DimensionValueCollection)DVCollection).entrySet())
			if(!containsKey(e.getKey()) || !e.getValue().equals(get(e.getValue().getID())))
				return false;
		return true;
	}
	
	/**
	 * Checks to see if the specified object is a dimension-value collection and that all
	 * of the keys within the specified dimension-value collection are specified within this 
	 * collection and vice-versa.
	 * @param DVCollection The collection to compare to this collection.
	 * @return True if this collection contains all of the keys from the specified collection, 
	 * otherwise false.
	 */
	public boolean equalsKeys (Object DVCollection)
	{
		if (this == DVCollection)
			return true;
		if (!(DVCollection instanceof DimensionValueCollection))
			return false;
		if (DVCollection == null)
			return false;
		if (((DimensionValueCollection)DVCollection).size() != size())
			return false;
		for (Object k : ((DimensionValueCollection)DVCollection).keySet())
		{
			if(!this.containsKey(k))
				return false;
		}
		return true;
	}
	
	/**
	 * Gets a collection containing all of the values within the dimension-value collection. The values in the 
	 * collection returned will not be associated with any dimensions. Therefore this method should only be used
	 * if you intend on making changes to / accessing information from all values in the dimension-value collection 
	 * regardless of their dimension. 
	 * <p>
	 * Most often within the CLARION Library this method is used to deliver the values of a dimension-value collection in a vector 
	 * format for the input layer of an implicit module.
	 * @return A collection containing all the values within the dimension-value collection.
	 */
	public Collection <Value> getValueCollection ()
	{
		ArrayList <Value> a = new ArrayList <Value> (getNumDVPairs());
		for(Dimension d : this.values())
			a.addAll(d.values());
		return a;
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the dimension-value collection (including all of it's dimensions).
	 * @return A copy of the dimension.
	 */
	public DimensionValueCollection clone ()
	{
		DimensionValueCollection a = new DimensionValueCollection();
		for (Dimension i : values())
		{
			Dimension d = i.clone();
			a.put(d.getID(), d);
		}
		a.hash = hash;
		return a;
	}
	
	/**
	 * Returns the number of dimensions in the dimension-value collection.
	 */
	public int size ()
	{
		return super.size();
	}
	
	public String toString()
	{
		String s = "";
		for(Iterator <Dimension> i = values().iterator(); i.hasNext();)
		{
			s += "\t" + i.next().toString();
			s+= "\n";
		}
		return s;
	}
}
