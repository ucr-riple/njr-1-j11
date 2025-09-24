package clarion.system;

import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.Map;

/**
 * This class implements a drive collection within CLARION. It extends the LinkedHashMap class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a container to hold drives.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>BehavioralApproachSystem</li>
 * <li>BehavioralInhibitionSystem</li>
 * <li>NeitherBehavioralSystem</li>
 * </ul>
 * @version 6.0.4.5
 * @author Nick Wilson
 */
public class DriveCollection extends LinkedHashMap <Object, Drive> {
	private static final long serialVersionUID = 3990147844020757933L;

	/**
	 * Initializes a drive collection with a default initial capacity and load factor.
	 */
	public DriveCollection ()
	{
		super();
	}
	
	/**
	 * Initializes the drive collection with the collection of drives specified.
	 * @param drives The drives for the collection.
	 */
	public DriveCollection (Collection <? extends Drive> drives)
	{
		super();
		for(Drive d : drives)
			put(d.getID(),d);
	}
	
	/**
	 * Initializes the drive collection with the map of drives.
	 * @param map The map of drives for the drive collection.
	 */
	public DriveCollection (Map <? extends Object, ? extends Drive> map)
	{
		super();
		putAll(map);
	}
	
	/**
	 * Gets the drive strength objects associated with the drives in this drive collection. 
	 * @return A collection of drive strength objects associated with the drives in this drive 
	 * collection.
	 */
	public DriveStrengthCollection getDriveStrengths ()
	{
		DriveStrengthCollection ds = new DriveStrengthCollection ();
		for(Drive d : this.values())
			ds.put(d.getID(), d.getDriveStrength());
		return ds;
	}
	
	/**
	 * Puts a drive in the drive collection. If this drive is already in the drive
	 * collection, this method will throw an exception.
	 * @param drive The drive to add.
	 * @return The result of putting the drive in the drive collection. This will always 
	 * return null (meaning the drive did not previously exist in the map). This is 
	 * because you are not allowed to put a drive into a drive collection that already 
	 * contains that drive.
	 * @throws IllegalArgumentException If the drive is already in the drive collection.
	 */
	public Drive put (Object key, Drive drive) throws IllegalArgumentException
	{
		if(containsKey(key) || containsValue(drive) || (key == null && drive != null && drive.getID() != null) || 
				(key != null && drive != null && !key.equals(drive.getID())))
			throw new IllegalArgumentException ("The specified drive is already in this " +
					"drive collection or the specified key is not the ID of the " +
					"specified drive.");
		return super.put(key,drive);
	}
	
	/**
	 * Puts a collection of drives in the drive collection. If any drives are already in the 
	 * drive collection, an exception will be thrown.
	 * @param drives The drives to add.
	 */
	public void putAll (Map <? extends Object, ? extends Drive> drives)
	{
		for(Map.Entry<? extends Object, ? extends Drive> e : drives.entrySet())
			put(e.getKey(),e.getValue());
	}
	
	/**
	 * Returns the number of drives in the drive collection.
	 */
	public int size ()
	{
		return super.size();
	}
}
