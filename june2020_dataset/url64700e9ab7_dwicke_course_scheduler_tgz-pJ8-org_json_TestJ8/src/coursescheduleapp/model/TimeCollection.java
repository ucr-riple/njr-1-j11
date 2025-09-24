package coursescheduleapp.model;

import java.util.TreeSet;

public class TimeCollection {

	private TreeSet<TimePeriod> timeCollection;
	
	/**
	 * Constructor creates the object with a timeCollection TreeSet
	 */
	public TimeCollection()
	{
		timeCollection = new TreeSet<TimePeriod>();
	}
	
	/**
	 * Creates a TimeCollection object with the given collection.
	 * @param collection the set of time periods for this TimeCollection
	 */
	public TimeCollection(TreeSet<TimePeriod> collection) 
	{
		timeCollection = collection;
	}
	
	/**
	 * The number of TimePeriods in the collection.
	 * @return
	 */
	public int getNumInCollection()
	{
		return timeCollection.size();
	}
	/**
	 * Gets the Set of TimePeriod objects.
	 * @return
	 */
	public TreeSet<TimePeriod> getCollection()
	{
		return timeCollection;
	}
	
	/**
	 * Sets the Collection with the provided TreeSet
	 * @param t
	 */
	public void setCollection(TreeSet<TimePeriod> t)
	{
		timeCollection = t;
	}
	
	/**
	 * Added a timeperiod to the collection.
	 * @param p The TimePeriod to add.
	 * @return Return true if added false if not.
	 * TimePeriod will not be added if a equal time period
	 * is already present in the collection.
	 */
	public boolean addPeriod(TimePeriod p)
	{
		return timeCollection.add(p);
	}
}
