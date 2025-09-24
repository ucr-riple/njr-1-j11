package clarion.system;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class EpisodicMemory extends AbstractIntermediateModule <EpisodicMemory.InfoStored, DimensionValueCollection> {
	private static final long serialVersionUID = 2715431605982467470L;
	
	public enum InfoStored {CURRENT_STATE, PERFORMED_ACTION, FEEDBACK, NEW_STATE};
	/**The number of slots in episodic memory.*/
	public static int GLOBAL_CAPACITY = 3;
	/**The number of slots in episodic memory.*/
	public int CAPACITY = GLOBAL_CAPACITY;
	
	public EpisodicMemory(CLARION Agent) {
		super(Agent);
	}
	
	/**
	 * Gets the stored information together as a dimension-value collection from 
	 * episodic memory with the specified time stamp.
	 * @param TimeStamp The time stamp of the information to get.
	 * @return A dimension-value collection containing the stored information for the given
	 * time stamp.
	 */
	public DimensionValueCollection get (Long TimeStamp)
	{
		DimensionValueCollection cc = new DimensionValueCollection ();
		for(DimensionValueCollection c : get((Object)TimeStamp).values())
		{
			for(Dimension d : c.values())
			{
				if(cc.containsKey(d.getID()))
				{
					Dimension cd = cc.get(d.getID());
					for(Value v : d.values())
					{
						if(cd.containsKey(v.getID()))
						{
							Value cv = cd.get(v.getID());
							if(v.getActivation() > cv.getActivation())
								cv.setActivation(v.getActivation());
						}
						else
							cd.put(v.getID(), v);
					}
				}
				else
					cc.put(d.getID(),d);
			}
		}
		return cc;
	}

	/**
	 * Gets the specified type of information from episodic memory at the specified time stamp.
	 * @param TimeStamp The time stamp of the information to get.
	 * @return A dimension-value collection containing the stored information for the given
	 * time stamp.
	 */
	public DimensionValueCollection get(Long TimeStamp, InfoStored type) {
		return super.get((Object)TimeStamp).get(type);
	}

	public Collection<DimensionValueCollection> get(InfoStored type) {
		LinkedList <DimensionValueCollection> c = new LinkedList <DimensionValueCollection> ();
		for(EnumMap <InfoStored, DimensionValueCollection> e : values())
		{
			switch(type)
			{
			case PERFORMED_ACTION:
				c.add(e.get(InfoStored.PERFORMED_ACTION));
				break;
			case CURRENT_STATE:
				c.add(e.get(InfoStored.CURRENT_STATE));
				break;
			case NEW_STATE:
				c.add(e.get(InfoStored.NEW_STATE));
				break;
			default:
				c.add(e.get(InfoStored.FEEDBACK));
			}
		}
		return c;
	}
	
	/**
	 * Gets the performed action from episodic memory for the specified time stamp.
	 * @param TimeStamp The time stamp for the performed action to get.
	 * @return The performed action.
	 */
	public AbstractAction getPerformedAction (Long TimeStamp)
	{
		return (AbstractAction)super.get((Object)TimeStamp).get(InfoStored.PERFORMED_ACTION);
	}
	
	/**
	 * Gets the feedback from episodic memory for the specified time stamp.
	 * @param TimeStamp The time stamp of the feedback to get.
	 * @return The feedback for the given time stamp.
	 */
	public double getFeedback (Long TimeStamp)
	{
		return ((DimensionlessOutputChunk)super.get((Object)TimeStamp).get(InfoStored.FEEDBACK)).getActivation();
	}

	public Collection <? extends DimensionValueCollection> getAll ()
	{
		HashSet <DimensionValueCollection> cc = new HashSet <DimensionValueCollection> ();
		if(size() != 0)
		{
			for(EnumMap <EpisodicMemory.InfoStored, DimensionValueCollection> i : values())
			{
				for(DimensionValueCollection c : i.values())
					cc.add(c);
			}
			return cc;
		}
		return null;
	}
	
	public DimensionValueCollection getAllAsDimensionValueCollection ()
	{
		DimensionValueCollection cc = new DimensionValueCollection ();
		if(size() != 0)
		{
			for(EnumMap <EpisodicMemory.InfoStored, DimensionValueCollection> i : values())
			{
				for(DimensionValueCollection c : i.values())
				{
					for(Dimension d : c.values())
					{
						if(cc.containsKey(d.getID()))
						{
							Dimension cd = cc.get(d.getID());
							for(Value v : d.values())
							{
								if(cd.containsKey(v.getID()))
								{
									Value cv = cd.get(v.getID());
									if(v.getActivation() > cv.getActivation())
										cv.setActivation(v.getActivation());
								}
								else
									cd.put(v.getID(), v);
							}
						}
						else
							cc.put(d.getID(),d);
					}
				}
			}
			return cc;
		}
		return null;
	}
	
	public void add(DimensionValueCollection o, Long TimeStamp, InfoStored type) {
		if(size() == CAPACITY && !containsKey(TimeStamp))
		{
			Iterator <Long> i = keySet().iterator();
			Long oldest = i.next();
			while(i.hasNext())
			{
				Long check = i.next();
				if(check < oldest)
					oldest = check;
			}
			remove(oldest);
		}
		EnumMap <InfoStored, DimensionValueCollection> e;
		if(!containsKey(TimeStamp))
			e = new EnumMap <InfoStored,DimensionValueCollection> (InfoStored.class);
		else
			e = super.get((Object)TimeStamp);
		switch(type)
		{
		case PERFORMED_ACTION:
			e.put(InfoStored.PERFORMED_ACTION, o);
			break;
		case CURRENT_STATE:
			e.put(InfoStored.CURRENT_STATE, o);
			break;
		case NEW_STATE:
			e.put(InfoStored.CURRENT_STATE, o);
		}
		if(e.size() > 0)
			put(TimeStamp, e);
	}
	
	public void addFeedback(double feedback, Long TimeStamp) {
		if(size() == CAPACITY && !containsKey(TimeStamp))
		{
			Iterator <Long> i = keySet().iterator();
			Long oldest = i.next();
			while(i.hasNext())
			{
				Long check = i.next();
				if(check < oldest)
					oldest = check;
			}
			remove(oldest);
		}
		EnumMap <InfoStored, DimensionValueCollection> e;
		if(!containsKey(TimeStamp))
			e = new EnumMap <InfoStored,DimensionValueCollection> (InfoStored.class);
		else
			e = super.get((Object)TimeStamp);
		DimensionlessOutputChunk doc = new DimensionlessOutputChunk(InfoStored.FEEDBACK);
		doc.setActivation(feedback);
		e.put(InfoStored.FEEDBACK, doc);
		put(TimeStamp, e);
	}

	/**
	 * Removes a state/action pair from the state/action buffer.
	 * @param TimeStamp The time stamp of the state/action pair to remove.
	 * @return The removed state/action pair.
	 */
	public DimensionValueCollection remove (Long TimeStamp)
	{
		DimensionValueCollection a = get(TimeStamp);
		super.remove((Object)TimeStamp);
		return a;
	}

	/**
	 * Removes the information correlated to the specified dimension-value collection from 
	 * episodic memory. The specified dimension-value collection can be any of the following:
	 * <ul>
	 * <li>An Action - Will remove all information that correlate to the specified action</li>
	 * <li>Current/New State Information - Will remove all information that correlate to the specified
	 * state information</li>
	 * </ul>
	 * @param o The dimension-value collection correlated to the information to remove.
	 */
	public void remove (DimensionValueCollection o, InfoStored type)
	{
		if(type == InfoStored.PERFORMED_ACTION)
		{
			for(Iterator <EnumMap<InfoStored,DimensionValueCollection>> i = values().iterator(); i.hasNext();)
			{
				AbstractAction a = (AbstractAction)i.next().get(InfoStored.PERFORMED_ACTION);
				if(a.equals(o))
				{
					i.remove();
				}
			}
		}
		else if (type == InfoStored.CURRENT_STATE)
		{
			for(Iterator <EnumMap<InfoStored,DimensionValueCollection>> i = values().iterator(); i.hasNext();)
			{
				DimensionValueCollection a = i.next().get(InfoStored.CURRENT_STATE);
				if(a.equals(o))
				{
					i.remove();
				}
			}
		}
		else if (type == InfoStored.NEW_STATE)
		{
			for(Iterator <EnumMap<InfoStored,DimensionValueCollection>> i = values().iterator(); i.hasNext();)
			{
				DimensionValueCollection a = i.next().get(InfoStored.NEW_STATE);
				if(a.equals(o))
				{
					i.remove();
				}
			}			
		}
	}
	
	/**
	 * Attaches the episodic memory to the specified CLARION agent.
	 * @param Agent The agent to which this episodic memory will be attached.
	 */
	protected void attachSelfToAgent (CLARION Agent)
	{
		Agent.attachEpisodicMemory(this);
	}
}
