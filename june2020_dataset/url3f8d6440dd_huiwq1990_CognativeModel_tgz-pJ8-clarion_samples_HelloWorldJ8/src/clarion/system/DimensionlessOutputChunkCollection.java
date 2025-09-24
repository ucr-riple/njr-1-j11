package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements a dimension-less output chunk collection within CLARION. It extends the
 * AbstractOutputChunkCollection class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a container to hold dimension-less output chunks.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class DimensionlessOutputChunkCollection extends
		AbstractOutputChunkCollection <DimensionlessOutputChunk>{
	private static final long serialVersionUID = -6906366681752067466L;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;

	/**
	 * Initializes a dimension-less output chunk collection.
	 */
	public DimensionlessOutputChunkCollection ()
	{
		super();
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes a dimension-less output chunk collection with the collection of dimension-less output chunks specified.
	 * @param ds The dimension-less output chunks for the collection.
	 */
	public DimensionlessOutputChunkCollection (Collection <? extends DimensionlessOutputChunk> ds)
	{
		super(ds);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the dimension-less output chunk collection with the map of dimension-less output chunks.
	 * @param map The map of dimension-less output chunks for the dimension-less output chunk collection.
	 */
	public DimensionlessOutputChunkCollection (Map <? extends Object, ? extends DimensionlessOutputChunk> map)
	{
		super(map);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Returns the dimension-less output chunk collection as a dimension-value collection 
	 * that contains a single dimension (with a null ID) with values that have the same ID 
	 * and activation as the dimension-less output chunks in this collection.
	 *
	 * @return The dimension-less output chunk collection as a dimension-value collection.
	 */
	public DimensionValueCollection toDimensionValueCollection ()
	{
		DimensionValueCollection a = new DimensionValueCollection ();
		Dimension d = new Dimension (null);
		a.put(d.getID(),d);
		for(DimensionlessOutputChunk o : values())
		{
			Value v = new Value (o.getID(),o.getActivation());
			d.put(v.getID(), v);
		}
		return a;
	}
	
	/**
	 * Checks to see if the specified object is a dimension-less output chunk collection and that all
	 * of the keys within the specified dimension-less output chunk collection are 
	 * specified within this collection.
	 * @param DOCCollection The collection to compare to this collection.
	 * @return True if this collection contains all of the keys from the specified collection, 
	 * otherwise false.
	 */
	public boolean containsKeys (Object DOCCollection)
	{
		if (this == DOCCollection)
			return true;
		if (!(DOCCollection instanceof DimensionlessOutputChunkCollection))
			return false;
		for (Object k : ((DimensionlessOutputChunkCollection)DOCCollection).keySet())
		{
			if(!this.containsKey(k))
				return false;
		}
		return true;
	}

	/**
	 * Checks to see if the specified object is an dimension-less output chunk collection 
	 * and that all of the dimension-less output chunks within the specified dimension-less 
	 * output chunk collection are equal to the dimension-less output chunks in this 
	 * collection.
	 * @param DOCCollection The collection to compare to this collection.
	 * @return True if the two collections are equal, otherwise false.
	 */
	public boolean equals (Object DOCCollection)
	{
		if (this == DOCCollection)
			return true;
		if (!(DOCCollection instanceof DimensionlessOutputChunkCollection))
			return false;
		for (Map.Entry<? extends Object, ? extends DimensionlessOutputChunk> e : ((DimensionlessOutputChunkCollection)DOCCollection).entrySet())
			if(!containsKey(e.getKey()) || !containsValue(e.getValue()))
				return false;
		return true;
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the dimension-less output chunk collection (including all of it's dimension-less output chunks).
	 * @return A copy of the dimension-less output chunk collection.
	 */
	public DimensionlessOutputChunkCollection clone ()
	{
		DimensionlessOutputChunkCollection a = new DimensionlessOutputChunkCollection();
		for (DimensionlessOutputChunk i : values())
		{
			DimensionlessOutputChunk d = i.clone();
			a.put(d.getID(), d);
		}
		a.hash = hash;
		return a;
	}
}
