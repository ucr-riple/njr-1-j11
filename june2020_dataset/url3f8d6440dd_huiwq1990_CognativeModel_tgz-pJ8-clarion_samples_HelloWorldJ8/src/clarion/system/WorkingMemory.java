package clarion.system;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class implements the working memory within CLARION. It extends the AbstractIntermediateModule class.
 * Conceptually, this system exists between the ACS and the NACS. However,
 * implementationally it is logical to specify it as an intermediate module since it is used
 * by several subsystems. Therefore, in the CLARION Library, the working memory is contained within the
 * CLARION class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class contains methods for adding and removing items from the working memory. The working memory is used
 * primarily by the ACS, but it can be used to facilitate interactions between the ACS and the NACS.
 * <p>
 * Working memory can contain any type of chunk. Also, unlike a goal, the dimensions of a working memory chunk 
 * can have the same IDs as the dimensions in the sensory information space. This equates to "remembering" or
 * "visualizing" something within the sensory information space and recalling it for later use.
 * <p>
 * Currently this is simply a container for the working memory slots. Any manipulations on the chunks 
 * in working memory must be manually specified.
 * <p>
 * It is EXTREMELY important that you only manipulate the working memory using the add and remove
 * methods that have been specifically defined by this class.
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class WorkingMemory extends AbstractIntermediateModule <WorkingMemory.InfoStored, GenericChunkCollection>{
	private static final long serialVersionUID = 3109928395636005779L;
	
	public enum InfoStored {TO_NACS, FROM_NACS, GENERIC_CHUNKS};
	/**The number of slots in working memory.*/
	public static int GLOBAL_CAPACITY = 7;
	/**The number of slots in working memory.*/
	public int CAPACITY = GLOBAL_CAPACITY;
	
	/**
	 * Initializes the working memory. During initialization this instance of the working
	 * memory will attach itself to the CLARION agent you specify.
	 * @param Agent The agent to which the working memory is being attached.
	 */
	public WorkingMemory (CLARION Agent) 
	{
		super(Agent);
	}
	
	/**
	 * Adds a chunk to working memory. If you are trying to add a chunk but the working memory
	 * is full, this method will throw an exception.
	 * @param C The chunk to add.
	 * @param TimeStamp The current time stamp.
	 * @param The type of chunk being placed in working memory.
	 * @throws FullContainerException If working memory is full.
	 */
	public void add (AbstractChunk C, Long TimeStamp, InfoStored type) throws FullContainerException
	{
		if(size() < CAPACITY)
		{
			EnumMap <InfoStored, GenericChunkCollection> e;
			if(!containsKey(TimeStamp))
				e = new EnumMap <InfoStored, GenericChunkCollection> (InfoStored.class);
			else
				e = super.get((Object)TimeStamp);
			switch(type)
			{
			case TO_NACS:
				if(!e.containsKey(InfoStored.TO_NACS))
				{
					GenericChunkCollection cc = new GenericChunkCollection ();
					cc.put(C.getID(), C);
					e.put(InfoStored.TO_NACS, cc);
				}
				else
				{
					GenericChunkCollection cc = e.get(InfoStored.TO_NACS);
					cc.remove(C.getID());
					cc.put(C.getID(), C);
					e.put(InfoStored.TO_NACS, cc);
				}
				break;
			case FROM_NACS:
				if(!e.containsKey(InfoStored.FROM_NACS))
				{
					GenericChunkCollection cc = new GenericChunkCollection ();
					cc.put(C.getID(), C);
					e.put(InfoStored.FROM_NACS, cc);
				}
				else
				{
					GenericChunkCollection cc = e.get(InfoStored.FROM_NACS);
					cc.remove(C.getID());
					cc.put(C.getID(), C);
					e.put(InfoStored.FROM_NACS, cc);
				}
				break;
			case GENERIC_CHUNKS:
				if(!e.containsKey(InfoStored.GENERIC_CHUNKS))
				{
					GenericChunkCollection cc = new GenericChunkCollection ();
					cc.put(C.getID(), C);
					e.put(InfoStored.GENERIC_CHUNKS, cc);
				}
				else
				{
					GenericChunkCollection cc = e.get(InfoStored.GENERIC_CHUNKS);
					cc.remove(C.getID());
					cc.put(C.getID(), C);
					e.put(InfoStored.GENERIC_CHUNKS, cc);
				}
				break;
			}
			if(e.size() > 0)
			{
				put(TimeStamp, e);
				C.addTimeStamp(TimeStamp);
			}
		}
		else
			throw new FullContainerException();
	}
	
	/**
	 * Adds a collection of chunks to working memory.
	 * @param C The collection of chunks to add.
	 * @param TimeStamp The current time stamp.
	 * @param The type of chunks being placed in working memory.
	 */
	public void add (GenericChunkCollection C, Long TimeStamp, InfoStored type)
	{
		for(AbstractChunk c : C.values())
			add(c, TimeStamp, type);
	}
	
	/**
	 * Removes all of the chunks from working memory that were added at the specified time stamp.
	 * @param TimeStamp The time stamp of the chunks to remove.
	 * @return The removed chunks.
	 */
	public GenericChunkCollection remove (Long TimeStamp)
	{
		HashMap <Object, AbstractChunk> cc = new HashMap <Object, AbstractChunk> ();
		for(GenericChunkCollection c: super.remove((Object)TimeStamp).values())
			cc.putAll(c);
		return new GenericChunkCollection(cc);
	}
	
	/**
	 * Removes any/all instances of the specified chunk from working memory.
	 * @param C The chunk to remove.
	 */
	public void remove (AbstractChunk C, InfoStored type)
	{
		for(EnumMap<InfoStored, GenericChunkCollection> e : values())
		{
			for(GenericChunkCollection c : e.values())
			{
				c.remove(C);
			}
		}
	}
	
	/**
	 * Removes the specified collection of chunks from working memory.
	 * @param C The collection of chunks to remove.
	 */
	public void remove (GenericChunkCollection C, InfoStored type)
	{
		for(AbstractChunk c : C.values())
		{
			remove(c, type);
		}
	}
	
	/**
	 * Gets the collection of chunks from working memory that were added at the specified time stamp.
	 * @param TimeStamp The time stamp of the chunks to get.
	 * @return The collection of chunks that were added to working memory at the specified time stamp.
	 */
	public GenericChunkCollection get (Long TimeStamp)
	{
		GenericChunkCollection cc = new GenericChunkCollection ();
		for(GenericChunkCollection c : get((Object)TimeStamp).values())
		{
			for(AbstractChunk a : c.values())
			{
				if(!cc.containsKey(a.getID()))
					cc.put(a.getID(), a);
			}
		}
		return cc;
	}
	
	/**
	 * Gets the collection of chunks of the specified type that were added to working memory 
	 * at the specified time stamp.
	 * @param TimeStamp The time stamp of the chunks to get.
	 * @return A collection of chunks.
	 */
	public GenericChunkCollection get(Long TimeStamp, InfoStored type) {
		return get((Object)TimeStamp).get(type);
	}

	public Collection<GenericChunkCollection> get(InfoStored type) {
		LinkedList <GenericChunkCollection> c = new LinkedList <GenericChunkCollection> ();
		for(EnumMap <InfoStored, GenericChunkCollection> e : values())
		{
			switch(type)
			{
			case TO_NACS:
				c.add(e.get(InfoStored.TO_NACS));
				break;
			case FROM_NACS:
				c.add(e.get(InfoStored.FROM_NACS));
				break;
			case GENERIC_CHUNKS:
				c.add(e.get(InfoStored.GENERIC_CHUNKS));
				break;
			}
		}
		return c;
	}
	
	public Collection <GenericChunkCollection> getAll ()
	{
		HashSet <GenericChunkCollection> cc = new HashSet <GenericChunkCollection> ();
		if(size() != 0)
		{
			for(EnumMap <InfoStored, GenericChunkCollection> i : values())
			{
				for(GenericChunkCollection c : i.values())
					cc.add(c);
			}
			return cc;
		}
		return null;
	}
	
	public int size ()
	{
		int count = 0;
		for(EnumMap<InfoStored, GenericChunkCollection> e : this.values())
		{
			for(GenericChunkCollection c : e.values())
			{
				count += c.size();
			}
		}
		return count;
	}
	
	/**
	 * Attaches the working memory to the specified CLARION agent.
	 * @param Agent The agent to which this working memory will be attached.
	 */
	protected void attachSelfToAgent (CLARION Agent)
	{
		Agent.attachWorkingMemory(this);
	}
}
