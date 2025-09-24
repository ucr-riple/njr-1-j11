package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements a generic chunk collection within CLARION. 
 * It extends the AbstractChunkCollection class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a container to hold any type of generic chunk (except generalized condition chunks).
 * @version 6.0.4.5
 * @author Nick Wilson
 */
public class GenericChunkCollection extends AbstractChunkCollection<AbstractChunk> {
	private static final long serialVersionUID = 465679730864196253L;

	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**
	 * Initializes a generic working memory collection.
	 */
	public GenericChunkCollection ()
	{
		super();
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes a generic chunk collection with the collection of 
	 * generic chunks specified.
	 * @param c The generic chunks for the collection.
	 */
	public GenericChunkCollection (Collection <? extends AbstractChunk> c)
	{
		super(c);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the generic chunk collection with the map of generic
	 * chunks.
	 * @param map The map of generic chunks for the collection.
	 */
	public GenericChunkCollection (Map <? extends Object, ? extends AbstractChunk> map)
	{
		super(map);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Returns the generic chunk collection as a dimension-value collection that 
	 * contains a dimension for each unique dimension within the chunks of the collection. For all
	 * chunks in the collection that contain the same dimension, the values within that
	 * dimension will all be placed under a single dimension within the dimension-value collection 
	 * that is returned. If multiple chunks in the collection have the same dimension-value pair, the
	 * activation for that dimension-value pair will be set to the value of the chunk with the 
	 * highest activation for that dimension-value pair.
	 * <p>
	 * This method is used to provide the generic chunks to the subsystems in a 
	 * format that can be used as input.
	 * @return The generic chunk collection represented as a dimension-value collection.
	 */
	public DimensionValueCollection toDimensionValueCollection ()
	{
		DimensionValueCollection a = new DimensionValueCollection ();
		for(AbstractChunk c : values())
		{
			for(Dimension d : c.values())
			{
				if(!a.containsKey(d.getID()))
					a.put(d.getID(), d);
				else
				{
					for(Value v : d.values())
					{
						Value dv = a.get(d.getID()).get(v.getID());
						if(dv == null)
							a.get(d.getID()).put(v.getID(), v);
						else if (dv.getActivation() < v.getActivation())
						{
							Dimension dd = a.get(d.getID()).clone();
							dd.remove(v.getID());
							dd.put(v.getID(), v);
							a.remove(dd.getID());
							a.put(dd.getID(), dd);
						}
					}
				}
			}
		}
		return a;
	}
	
	/**
	 * Checks to see if the specified object is a generic chunk collection 
	 * and that all of the keys within the specified collection are specified within this 
	 * collection.
	 * @param CCollection The collection to compare to this collection.
	 * @return True if this collection contains all of the keys from the specified collection, 
	 * otherwise false.
	 */
	public boolean containsKeys (Object CCollection)
	{
		if (this == CCollection)
			return true;
		if (!(CCollection instanceof GenericChunkCollection))
			return false;
		for (Object k : ((GenericChunkCollection)CCollection).keySet())
		{
			if(!this.containsKey(k))
				return false;
		}
		return true;
	}

	/**
	 * Checks to see if the specified object is a generic chunk collection and that
	 * all of the chunks within the specified collection are equal to the chunks in this collection.
	 * @param CCollection The collection to compare to this collection.
	 * @return True if the two collections are equal, otherwise false.
	 */
	public boolean equals (Object CCollection)
	{
		if (this == CCollection)
			return true;
		if (!(CCollection instanceof GenericChunkCollection))
			return false;
		for (Map.Entry<? extends Object, ? extends AbstractChunk> e : ((GenericChunkCollection)CCollection).entrySet())
			if(!containsKey(e.getKey()) || !containsValue(e.getValue()))
				return false;
		return true;
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the generic chunk collection (including all of it's chunks).
	 * @return A copy of the generic chunk collection.
	 */
	public GenericChunkCollection clone ()
	{
		GenericChunkCollection a = new GenericChunkCollection();
		for (AbstractChunk i : values())
		{
			AbstractChunk c = i.clone();
			a.put(c.getID(), c);
		}
		a.hash = hash;
		return a;
	}
}
