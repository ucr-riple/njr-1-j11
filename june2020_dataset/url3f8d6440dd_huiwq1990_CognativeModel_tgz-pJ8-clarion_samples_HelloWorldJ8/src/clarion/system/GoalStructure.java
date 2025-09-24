package clarion.system;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class implements the goal structure within CLARION. It extends the AbstractIntermediateModule class.
 * Conceptually, this system exists on the top level of the MS. However,
 * implementationally it is logical to specify it as an intermediate module since it is used
 * by several subsystems. Therefore, in the CLARION Library, the goal structure is contained within the
 * CLARION class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The goal structure's main function is to act as a container for the goals within CLARION. In addition to 
 * containing methods for adding and removing items from the goal list, it also keeps track of all of the
 * possible goals that can be set and has a method for getting the current goal.
 * <p>
 * It is EXTREMELY important that you only manipulate the goal structure using the add and remove
 * methods that have been specifically defined by this class.
 * <p>
 * Currently this is simply a container for the goal list.
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class GoalStructure extends AbstractIntermediateModule <GoalStructure.InfoStored, Goal>{
	private static final long serialVersionUID = -3073724626285378374L;
	
	public enum InfoStored {GOAL};
	/**The number of slots in the goal list.*/
	public static int GLOBAL_CAPACITY = 7;
	/**The number of slots in the goal list.*/
	public int CAPACITY = GLOBAL_CAPACITY;
	/**All of the possible Goals that can be added to the goal structure.*/
	private GoalCollection PossibleGoals;
	
	/**
	 * Initializes the goal structure. During initialization this instance of the goal
	 * structure will attach itself to the CLARION agent you specify.
	 * @param Agent The agent to which the goal structure is being attached.
	 */
	public GoalStructure (CLARION Agent) 
	{
		super(Agent);
	}
	
	public Collection <Goal> getAll ()
	{
		HashSet <Goal> g = new HashSet <Goal> ();
		if(size() != 0)
		{
			for(EnumMap <GoalStructure.InfoStored, Goal> i : values())
			{
				for(Goal c : i.values())
					g.add(c);
			}
			return g;
		}
		return null;
	}
	
	/**
	 * Gets the current goal. The current goal is the goal in the list with the most recent time stamp.
	 * If no goals are in the goal list, this method returns null.
	 * @return The current goal. Null if no goals are in the list.
	 */
	public Goal getCurrentGoal ()
	{
		if(size() != 0)
		{
			Iterator <Long> i = keySet().iterator();
			Long current = i.next();
			while(i.hasNext())
			{
				Long check = i.next();
				if(check > current)
					current = check;
			}
			return get((Object)current).get(InfoStored.GOAL);
		}
		return null;
	}
	
	/**
	 * Gets the goal from the goal structure with the specified time stamp.
	 * @param TimeStamp The time stamp of the goal to get.
	 * @return The goal.
	 */
	public Goal get (Long TimeStamp)
	{
		return super.get((Object)TimeStamp).get(InfoStored.GOAL);
	}
	
	/**
	 * Gets the goal from the goal structure that was added at the specified time stamp.
	 * @param TimeStamp The time stamp of the goal to get.
	 * @return The goal that was added to the goal structure at the given time stamp.
	 */
	public Goal get(Long TimeStamp, InfoStored type) {
		return super.get((Object)TimeStamp).get(type);
	}

	public Collection<Goal> get(InfoStored type) {
		LinkedList <Goal> c = new LinkedList <Goal> ();
		for(EnumMap <InfoStored, Goal> e : values())
		{
				c.add(e.get(InfoStored.GOAL));
		}
		return c;
	}
	
	/**
	 * Adds a goal to the goal list. If you are trying to add a goal but the goal structure
	 * is full, this method will throw an exception.
	 * <p>
	 * If you want to add a new goal to the list of possible goals in the goal structure, call the 
	 * addPossibleGoal method.
	 * @param G The goal to add.
	 * @param TimeStamp The current time stamp.
	 * @throws FullContainerException If the goal structure is full.
	 */
	public void add (Goal G, Long TimeStamp) throws FullContainerException
	{
		if(size() < CAPACITY)
		{
			EnumMap <InfoStored, Goal> e;
			if(!containsKey(TimeStamp))
				e = new EnumMap <InfoStored,Goal> (InfoStored.class);
			else
				e = super.get((Object)TimeStamp);
			e.put(InfoStored.GOAL, G);
			put(TimeStamp, e);
			G.activateGoal();
			G.addTimeStamp(TimeStamp);
		}
		else
			throw new FullContainerException();
	}
	
	/**
	 * Adds a goal to the goal list. If you are trying to add a goal but the goal structure
	 * is full, this method will throw an exception.
	 * <p>
	 * If you want to add a new goal to the list of possible goals in the goal structure, call the 
	 * addPossibleGoal method.
	 * @param G The goal to add.
	 * @param TimeStamp The current time stamp.
	 * @param GOAL Specify "GoalStructure.InfoStored.GOAL" here.
	 * @throws FullContainerException If the goal structure is full.
	 */
	public void add (Goal G, Long TimeStamp, InfoStored GOAL) throws FullContainerException
	{
		if(size() < CAPACITY)
		{
			EnumMap <InfoStored, Goal> e;
			if(!containsKey(TimeStamp))
				e = new EnumMap <InfoStored,Goal> (InfoStored.class);
			else
				e = super.get((Object)TimeStamp);
			e.put(InfoStored.GOAL, G);
			put(TimeStamp, e);
			G.activateGoal();
			G.addTimeStamp(TimeStamp);
		}
		else
			throw new FullContainerException();
	}
	
	/**
	 * Removes a goal from the goal list.
	 * @param TimeStamp The time stamp of the goal to remove.
	 * @return The removed goal.
	 */
	public Goal remove (Long TimeStamp)
	{
		Goal g = super.remove((Object)TimeStamp).get(InfoStored.GOAL);
		g.deactivateGoal();
		return g;
	}
	
	/**
	 * Removes the goal object from the goal list.
	 * @param G The goal to remove.
	 */
	public void remove (Goal G)
	{
		for(Iterator <EnumMap<InfoStored,Goal>> i = values().iterator(); i.hasNext();)
		{
			Goal g = (Goal)i.next().get(InfoStored.GOAL);
			if(g.equals(G))
			{
				i.remove();
			}
		}
		G.deactivateGoal();
	}
	
	/**
	 * Removes the goal object from the goal list.
	 * @param G The goal to remove.
	 * @param GOAL Specify "GoalStructure.InfoStored.GOAL" here.
	 */
	public void remove (Goal G, InfoStored GOAL)
	{
		for(Iterator <EnumMap<InfoStored,Goal>> i = values().iterator(); i.hasNext();)
		{
			Goal g = (Goal)i.next().get(InfoStored.GOAL);
			if(g.equals(G))
			{
				i.remove();
			}
		}
		G.deactivateGoal();
	}
	
	/**
	 * Gets the number of possible goals.
	 * @return The number of possible goals.
	 */
	public int getNumPossibleGoals ()
	{
		if(PossibleGoals != null)
			return  PossibleGoals.size();
		else
			return 0;
	}
	
	/**
	 * Gets the collection of the possible goals. This method returns the ACTUAL collection of 
	 * possible goals and therefore should be accessed and used with caution.
	 * <p>
	 * This method should ONLY be used for initialization and reporting purposes.
	 * @return The collection of goals.
	 */
	public GoalCollection getPossibleGoals ()
	{
		if(PossibleGoals != null)
			return PossibleGoals;
		return null;
	}
	
	/**
	 * Adds a goal to the list of possible goals. If the goal is already in the list of 
	 * possible goals, it will not be added.
	 * <p>
	 * Remember that the input layer of an implicit module or rule in the ACS must contain nodes for all dimension-value pairs 
	 * contained within any of the possible goals you wish to have as input into the network or rule condition.
	 * @param G The possible goal to add.
	 * @return True if the goal was successfully added.
	 */
	public boolean addPossibleGoal (Goal G)
	{
		if(PossibleGoals == null)
		{
			PossibleGoals = new GoalCollection ();
		}
		
		if(!PossibleGoals.containsKey(G.getID()))
		{
			Goal g = G.clone();
			updateInputSpace(g.values());
			PossibleGoals.put(g.getID(),g);
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a collection of goals to the list of possible goals. If a goal from the collection 
	 * is already in the list of possible goals, it will not be added.
	 * <p>
	 * Remember that the input layer of an implicit module or rule in the ACS must contain nodes for all dimension-value pairs 
	 * contained within any of the possible goals you wish to have as input into the network or rule condition.
	 * @param Goals The collection of possible goals to add.
	 * @return True if any of the goals were successfully added.
	 */
	public boolean addPossibleGoals (Collection <Goal> Goals)
	{
		boolean rettrue = false;
		for(Goal g : Goals)
		{
			if(addPossibleGoal(g))
				rettrue = true;
		}
		return rettrue;
	}
	
	/**
	 * Attaches the goal structure to the specified CLARION agent.
	 * @param Agent The agent to wish this goal structure will be attached.
	 */
	protected void attachSelfToAgent (CLARION Agent)
	{
		Agent.attachGoalStructure(this);
	}
}
