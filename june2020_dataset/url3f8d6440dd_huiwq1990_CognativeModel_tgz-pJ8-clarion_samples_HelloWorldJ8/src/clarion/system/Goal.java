package clarion.system;

import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * This class implements a goal within CLARION. It extends the AbstractOutputChunk class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A goal acts as a directive within CLARION providing both internal instruction for the subsystems within
 * CLARION as well as aiding more directly (e.g. as input into the ACS).
 * <p>
 * If a goal is activated, then all dimension-value pairs in the goal are also activated. Goals
 * are not required to have dimension-value pairs. However, if you do not specify any dimension-value
 * pairs, the goal cannot be used as input for the subsystems (mainly the ACS).
 * <p>
 * Note that the dimensions of a goal should have different IDs than the dimensions in the sensory information 
 * space otherwise conflicts will arise on the bottom level of the ACS. If a goal has a dimension that has the 
 * same ID as a dimension in the sensory information space, then if the goal is activated, the dimension with 
 * the same ID in the sensory information space will also be activated. The CLARION library has been implemented 
 * in this fashion to prevent the system from crashing in the event that this circumstance occurs.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class Goal extends AbstractOutputChunk{
	private static final long serialVersionUID = 7442017592078221507L;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**The relevance of the drives to this goal.*/
	private LinkedHashMap <Object,Value> Relevance;
	
	/**The sub goals related to this goal. If this goal is activate then all sub goals are also
	 * activated.*/
	private LinkedHashMap <Object,Goal> SubGoals;
	
	/**
	 * Initializes the goal with the ID specified.
	 * @param id The ID of the goal.
	 */
	public Goal (Object id)
	{
		super(id);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the goal with the specified ID and dimensions.
	 * @param id The ID of the goal.
	 * @param dims The dimensions for the goal.
	 */
	public Goal (Object id, Collection <? extends Dimension> dims)
	{
		super(id, dims);
		for(Dimension i : values())
			for(Value j : i.values())
				j.setActivation(j.MINIMUM_ACTIVATION_THRESHOLD);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the goal with the specified ID and map of dimensions.
	 * @param id The ID of the goal.
	 * @param dims The map of dimensions for the goal.
	 */
	public Goal (Object id, Map <? extends Object, ? extends Dimension> dims)
	{
		super(id, dims);
		for(Dimension i : values())
			for(Value j : i.values())
				j.setActivation(j.MINIMUM_ACTIVATION_THRESHOLD);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Puts the dimension into the goal as long as the dimension is not already in the goal. This 
	 * method also sets the activations of all of the values in the dimension to the minimum 
	 * activation threshold (i.e. to the off position). If the dimension is already in the 
	 * goal, this method throws an exception. If the specified key is not the ID of the 
	 * specified dimension, this method throws an exception.
	 * @param key The key with which the specified dimension is to be associated. This MUST be the ID
	 * of the specified dimension.
	 * @param dim The dimension to add to the goal.
	 * @return The result of putting the dimension in the goal. This will 
	 * always return null (meaning the dimension did not previously exist in the map). This is 
	 * because you are not allowed to put a dimension into a goal that already contains that dimension.
	 * @throws IllegalArgumentException If the dimension is already in the goal
	 * or the specified key is not the ID of the specified dimension.
	 */
	public Dimension put (Object key, Dimension dim) throws IllegalArgumentException
	{
		if(containsKey(key) || containsValue(dim) || !key.equals(dim.getID()))
			throw new IllegalArgumentException ("The specified dimension is already in this " +
					"goal or the specified key is not the ID of the specified dimension.");
		for(Value v : dim.values())
			v.setActivation(v.MINIMUM_ACTIVATION_THRESHOLD);
		return super.put(key,dim);
	}
	
	/**
	 * Puts all of the dimensions in the map into the goal as long as the 
	 * dimensions are not already in the goal. This method also sets the activations
	 * of the values in all of the dimensions to the minimum activation threshold (i.e. to the
	 * off position).
	 * @param map The map of dimensions to add.
	 */
	public void putAll (Map <? extends Object,? extends Dimension> map)
	{
		for(Map.Entry<? extends Object, ? extends Dimension> e : map.entrySet())
			put(e.getKey(),e.getValue());
	}
	
	/**
	 * Gets the relevance to the goal of the drive with the specified ID. If the 
	 * drive is not affiliated with this goal, this method returns null.
	 * @param ID The ID of the drive whose relevance you wish to get.
	 * @return The relevance of the drive to the goal. Null if the drive is not affiliated with
	 * this goal.
	 */
	public Value getRelevance (Object ID)
	{
		return Relevance.get(ID);
	}
	
	/**
	 * Sets the relevance to this goal for the drives with IDs equal to the values in the 
	 * specified collection.
	 * <p>
	 * Relevances are specified as a collection of values where the value ID is equal to
	 * the ID of a relevant drive. Only those drives whose relevance has been specified 
	 * will be considered as being relevant to this goal.
	 * @param Rel A collection of relevances.
	 */
	public void setRelevances (Collection <Value> Rel)
	{
		if(Relevance == null)
			Relevance = new LinkedHashMap <Object,Value>();
		for(Value v : Rel)
			Relevance.put(v.getID(), v);
	}
	
	/**
	 * Gets sub goals of this goal. The collection returned is unmodifiable and is 
	 * meant for reporting the internal state only.
	 * @return An unmodifiable collection of the subgoals for this goal.
	 */
	public Collection <Goal> getSubGoals ()
	{
		return Collections.unmodifiableCollection(SubGoals.values());
	}
	
	/**
	 * Sets the sub goals for this goal.
	 * This method should ONLY be run during initialization.
	 * @param subs The array of sub goals.
	 */
	public void setSubGoals(Collection <Goal> subs)
	{
		if(SubGoals == null)
			SubGoals = new LinkedHashMap<Object, Goal> ();
		for(Goal g : subs)
			SubGoals.put(g.getID(), g);
	}
	
	/**
	 * Activates the goal by setting all of the dimension-values in the goal to the 
	 * full activation threshold.
	 */
	public void activateGoal ()
	{
		setActivation(Value.GLOBAL_FULL_ACTIVATION_THRESHOLD);
		for(Dimension i : values())
			for(Value j : i.values())
				j.setActivation(j.FULL_ACTIVATION_THRESHOLD);
		if(SubGoals != null)
		{
			for(Goal g : SubGoals.values())
				g.activateGoal();
		}
	}
	
	/**
	 * Deactivates the goal by setting all of the dimension-values in the goal to the 
	 * minimum activation threshold.
	 */
	public void deactivateGoal ()
	{
		setActivation(Value.GLOBAL_MINIMUM_ACTIVATION_THRESHOLD);
		for(Dimension i : values())
			for(Value j : i.values())
				j.setActivation(j.MINIMUM_ACTIVATION_THRESHOLD);
		if(SubGoals != null)
		{
			for(Goal g : SubGoals.values())
				g.deactivateGoal();
		}
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the goal (including all of it's dimensions).
	 * @return A copy of the goal.
	 */
	public Goal clone() {
		Goal a = new Goal(getID());
		for(Dimension i : values())
		{
			Dimension d = i.clone();
			a.put(d.getID(), d);
		}
		if(Relevance != null)
		{
			a.Relevance = new LinkedHashMap <Object,Value> ();
			for(Value i : Relevance.values())
			{
				Value v = i.clone();
				a.Relevance.put(v.getID(),v);
			}
		}
		if(SubGoals != null)
		{
			a.SubGoals = new LinkedHashMap <Object,Goal> ();
			for(Goal i : SubGoals.values())
			{
				Goal g = i.clone();
				a.SubGoals.put(g.getID(),g);
			}
		}
		a.INITIAL_BLA = INITIAL_BLA;
		a.C = C;
		a.D = D;
		a.T = T.clone();
		a.LatestTimeStamp = LatestTimeStamp;
		a.hash = hash;
		return a;
	}
}
