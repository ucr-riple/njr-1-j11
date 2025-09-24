package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements a goal collection within CLARION. It extends the AbstractOutputChunkCollection
 * class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a container for goals.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class GoalCollection extends AbstractOutputChunkCollection<Goal> {
	private static final long serialVersionUID = 465679730864196253L;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;

	/**
	 * Initializes a goal collection.
	 */
	public GoalCollection ()
	{
		super();
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes a goal collection with the collection of goals specified.
	 * @param Goals The goals for the collection.
	 */
	public GoalCollection (Collection <? extends Goal> Goals)
	{
		super(Goals);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the goal collection with the map of goals.
	 * @param map The map of goals for the goal collection.
	 */
	public GoalCollection (Map <? extends Object, ? extends Goal> map)
	{
		super(map);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Returns the goal collection as a dimension-value collection that contains a
	 * dimension for each unique dimension within the goals of the goal collection. For all
	 * goals in the goal collection that contain the same dimension, the values within that
	 * dimension will all be placed under a single dimension within the 
	 * dimension-value collection that is returned.
	 * <p>
	 * This method is used to provide the goals to the subsystems in a format 
	 * that can be used as input.
	 * @return The goal collection as a dimension-value collection.
	 */
	public DimensionValueCollection toDimensionValueCollection ()
	{
		DimensionValueCollection a = new DimensionValueCollection ();
		for(Goal g : values())
		{
			for(Dimension d : g.values())
			{
				if(!a.containsKey(d.getID()))
					a.put(d.getID(), d);
				else
				{
					for(Value v : d.values())
						a.get(d.getID()).put(v.getID(), v);
				}
			}
		}
		return a;
	}
	
	/**
	 * Checks to see if the specified object is a goal collection and that all
	 * of the keys within the specified goal collection are specified within this 
	 * collection.
	 * @param GCollection The collection to compare to this collection.
	 * @return True if this collection contains all of the keys from the specified collection, 
	 * otherwise false.
	 */
	public boolean containsKeys (Object GCollection)
	{
		if (this == GCollection)
			return true;
		if (!(GCollection instanceof GoalCollection))
			return false;
		for (Object k : ((GoalCollection)GCollection).keySet())
		{
			if(!this.containsKey(k))
				return false;
		}
		return true;
	}

	/**
	 * Checks to see if the specified object is an goal collection and that all
	 * of the goals within the specified goal collection are equal to the goals 
	 * in this collection.
	 * @param GCollection The collection to compare to this collection.
	 * @return True if the two collections are equal, otherwise false.
	 */
	public boolean equals (Object GCollection)
	{
		if (this == GCollection)
			return true;
		if (!(GCollection instanceof GoalCollection))
			return false;
		for (Map.Entry<? extends Object, ? extends Goal> e : ((GoalCollection)GCollection).entrySet())
			if(!containsKey(e.getKey()) || !containsValue(e.getValue()))
				return false;
		return true;
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the goal collection (including all of the goals in the collection).
	 * @return A copy of the goal collection.
	 */
	public GoalCollection clone ()
	{
		GoalCollection a = new GoalCollection();
		for (Goal i : values())
		{
			Goal g = i.clone();
			a.put(g.getID(), g);
		}
		a.hash = hash;
		return a;
	}
}
