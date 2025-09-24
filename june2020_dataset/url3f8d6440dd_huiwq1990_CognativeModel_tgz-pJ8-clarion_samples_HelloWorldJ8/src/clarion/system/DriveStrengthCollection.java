package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements a drive strength collection within CLARION. 
 * It extends the AbstractOutputChunkCollection class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a container to hold drive strengths.
 * @version 6.0.4.5
 * @author Nick Wilson
 */
public class DriveStrengthCollection extends AbstractOutputChunkCollection<DriveStrength> {
	private static final long serialVersionUID = 465679730864196253L;

	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**
	 * Initializes a drive strength collection.
	 */
	public DriveStrengthCollection ()
	{
		super();
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes a drive strength collection with the collection of drive strengths specified.
	 * @param ds The drive strengths for the collection.
	 */
	public DriveStrengthCollection (Collection <? extends DriveStrength> ds)
	{
		super(ds);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the drive strength collection with the map of drive strengths.
	 * @param map The map of drive strengths for the drive strength collection.
	 */
	public DriveStrengthCollection (Map <? extends Object, ? extends DriveStrength> map)
	{
		super(map);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Returns the drive strength collection as a dimension-value collection that contains a
	 * single dimension, "DRIVE_STRENGTHS", with values that have the same ID and activation as
	 * the drive strengths in this collection.
	 * <p>
	 * This method is used to provide the modules in the MCS with drive strengths in a format 
	 * that can be input into an implicit module.
	 * @return The drive strength collection as a dimension-value collection.
	 */
	public DimensionValueCollection toDimensionValueCollection ()
	{
		DimensionValueCollection a = new DimensionValueCollection ();
		Dimension d = new Dimension ("DRIVE_STRENGTHS");
		a.put(d.getID(),d);
		for(DriveStrength ds : values())
		{
			Value v = new Value (ds.getID(),ds.getActivation());
			d.put(v.getID(), v);
		}
		return a;
	}
	
	/**
	 * Checks to see if the specified object is a drive strength collection and that all
	 * of the keys within the specified drive strength collection are specified within this 
	 * collection.
	 * @param DSCollection The collection to compare to this collection.
	 * @return True if this collection contains all of the keys from the specified collection, 
	 * otherwise false.
	 */
	public boolean containsKeys (Object DSCollection)
	{
		if (this == DSCollection)
			return true;
		if (!(DSCollection instanceof DriveStrengthCollection))
			return false;
		for (Object k : ((DriveStrengthCollection)DSCollection).keySet())
		{
			if(!this.containsKey(k))
				return false;
		}
		return true;
	}

	/**
	 * Checks to see if the specified object is a drive strength collection and that all
	 * of the drive strengths within the specified drive strength collection are equal to the drive 
	 * strengths in this collection.
	 * @param DSCollection The collection to compare to this collection.
	 * @return True if the two collections are equal, otherwise false.
	 */
	public boolean equals (Object DSCollection)
	{
		if (this == DSCollection)
			return true;
		if (!(DSCollection instanceof DriveStrengthCollection))
			return false;
		for (Map.Entry<? extends Object, ? extends DriveStrength> e : ((DriveStrengthCollection)DSCollection).entrySet())
			if(!containsKey(e.getKey()) || !containsValue(e.getValue()))
				return false;
		return true;
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the drive strength collection (including all of it's drive strengths).
	 * @return A copy of the drive strength collection.
	 */
	public DriveStrengthCollection clone ()
	{
		DriveStrengthCollection a = new DriveStrengthCollection();
		for (DriveStrength i : values())
		{
			DriveStrength d = i.clone();
			a.put(d.getID(), d);
		}
		a.hash = hash;
		return a;
	}
}
