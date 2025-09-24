package clarion.system;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class implements an action collection within CLARION. It extends the AbstractOutputChunkCollection
 * class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a container for holding actions.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class ActionCollection extends AbstractOutputChunkCollection <AbstractAction>{
	private static final long serialVersionUID = 1896825243166161753L;

	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**
	 * The flag indicating whether or not the "DO_NOTHING_EXTERNAL" action should be returned by the
	 * getExternalActions methods. It is set to false by default and is turned on by the ACS only if
	 * the "DO_NOTHING_EXTERNAL" action is specified within either the output layer of an 
	 * implicit module or a rule that is added to the ACS.
	 */
	protected boolean USE_DO_NOTHING_EXTERNAL = false;
	/**
	 * The flag indicating whether or not the "DO_NOTHING_WM" action should be returned by the
	 * getWMActions methods. It is set to false by default and is turned on by the ACS only if
	 * the "DO_NOTHING_WM" action is specified within either the output layer of an 
	 * implicit module or a rule that is added to the ACS.
	 */
	protected boolean USE_DO_NOTHING_WM = false;
	/**
	 * The flag indicating whether or not the "DO_NOTHING_GOAL" action should be returned by the
	 * getGoalActions methods. It is set to false by default and is turned on by the ACS only if
	 * the "DO_NOTHING_GOAL" action is specified within either the output layer of an 
	 * implicit module or a rule that is added to the ACS.
	 */
	protected boolean USE_DO_NOTHING_GOAL = false;
	
	/**
	 * Initializes an action collection.
	 */
	public ActionCollection ()
	{
		super();
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes an action collection with the collection of actions specified.
	 * @param Actions The actions for the collection.
	 */
	public ActionCollection (Collection <? extends AbstractAction> Actions)
	{
		super(Actions);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the action collection with the map of actions.
	 * @param map The map of actions for the action collection.
	 */
	public ActionCollection (Map <? extends Object, ? extends AbstractAction> map)
	{
		super(map);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Gets the external actions in this collection.
	 * @return The external actions.
	 */
	public Collection <AbstractAction> getExternalActions ()
	{
		LinkedList <AbstractAction> al = new LinkedList<AbstractAction> ();
		for(AbstractAction a : this.values())
		{
			if(a instanceof ExternalAction)
			{
				if(!a.getID().equals("DO_NOTHING_EXTERNAL") || USE_DO_NOTHING_EXTERNAL)
					al.add(a);
			}
		}
		return al;
	}
	
	/**
	 * Gets the working memory actions in this collection.
	 * @return The working memory actions.
	 */
	public Collection <AbstractAction> getWMActions ()
	{
		LinkedList <AbstractAction> al = new LinkedList<AbstractAction> ();
		for(AbstractAction a : this.values())
		{
			if(a instanceof WorkingMemoryAction)
			{
				if(!a.getID().equals("DO_NOTHING_WM") || USE_DO_NOTHING_WM)
					al.add(a);
			}
		}
		return al;
	}
	
	/**
	 * Gets the goal actions in this collection.
	 * @return The goal actions.
	 */
	public Collection <AbstractAction> getGoalActions ()
	{
		LinkedList <AbstractAction> al = new LinkedList<AbstractAction> ();
		for(AbstractAction a : this.values())
		{
			if(a instanceof GoalAction)
			{
				if(!a.getID().equals("DO_NOTHING_GOAL") || USE_DO_NOTHING_GOAL)
					al.add(a);
			}
		}
		return al;
	}
	
	/**
	 * Returns the action collection as a dimension-value collection that contains a
	 * dimension for each unique dimension within the action of the action collection. For all
	 * actions in the action collection that contain the same dimension, the values within that
	 * dimension will all be placed under a single dimension within the 
	 * dimension-value collection that is returned.
	 * <p>
	 * This method is used to provide the actions to the subsystems in a format 
	 * that can be used as input.
	 * @return The action collection as a dimension-value collection.
	 */
	public DimensionValueCollection toDimensionValueCollection ()
	{
		DimensionValueCollection a = new DimensionValueCollection ();
		for(AbstractAction act : values())
		{
			for(Dimension d : act.values())
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
	 * Checks to see if the specified object is an action collection and that all
	 * of the keys within the specified action collection are specified within this 
	 * collection.
	 * @param ActCollection The collection to compare to this collection.
	 * @return True if this collection contains all of the keys from the specified collection, 
	 * otherwise false.
	 */
	public boolean containsKeys (Object ActCollection)
	{
		if (this == ActCollection)
			return true;
		if (!(ActCollection instanceof ActionCollection))
			return false;
		for (Object k : ((ActionCollection)ActCollection).keySet())
		{
			if(!this.containsKey(k))
				return false;
		}
		return true;
	}

	/**
	 * Checks to see if the specified object is an action collection and that all
	 * of the actions within the specified action collection are equal to the actions
	 * in this collection.
	 * @param ActCollection The collection to compare to this collection.
	 * @return True if the two collections are equal, otherwise false.
	 */
	public boolean equals (Object ActCollection)
	{
		if (this == ActCollection)
			return true;
		if (!(ActCollection instanceof ActionCollection))
			return false;
		for (Map.Entry<? extends Object, ? extends AbstractAction> e : ((ActionCollection)ActCollection).entrySet())
			if(!containsKey(e.getKey()) || !containsValue(e.getValue()))
				return false;
		return true;
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the action collection (including all of the actions in the collection).
	 * @return A copy of the action collection.
	 */
	public ActionCollection clone ()
	{
		ActionCollection a = new ActionCollection();
		for (AbstractAction i : values())
		{
			AbstractAction act = i.clone();
			a.put(act.getID(), act);
		}
		a.hash = hash;
		return a;
	}
}
