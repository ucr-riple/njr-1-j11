package clarion.system;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class implements a chunk collection within CLARION. It extends the
 * LinkedHashMap class. This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a container to hold chunks of the specified type <C>.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractOutputChunkCollection</li>
 * <li>ActionCollection</li>
 * <li>DimensionlessOutputChunkCollection</li>
 * <li>DriveStrengthCollection</li>
 * <li>GoalCollection</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 * @param <C> The type of chunk being placed in the collection.
 */
public abstract class AbstractChunkCollection <C extends AbstractChunk> extends LinkedHashMap <Object,C>{
	private static final long serialVersionUID = -1584790136328861528L;

	/**
	 * Initializes a chunk collection.
	 */
	public AbstractChunkCollection ()
	{
		super();
	}
	
	/**
	 * Initializes a chunk collection with the collection of chunks specified.
	 * @param chunks The chunks for the collection.
	 */
	public AbstractChunkCollection (Collection <? extends C> chunks)
	{
		super();
		for(C c : chunks) 
			put(c.getID(),c);
	}
	
	/**
	 * Initializes the chunk collection with the map of chunks.
	 * @param map The map of chunks for the chunk collection.
	 */
	public AbstractChunkCollection (Map <? extends Object, ? extends C> map)
	{
		super();
		putAll(map);
	}
	
	/**
	 * Puts the chunk into the chunk collection as long as the chunk is not already 
	 * in the chunk collection. If the chunk is already in the chunk collection, this method throws 
	 * an exception. If the specified key is not the ID of the specified chunk, this method throws an 
	 * exception.
	 * @param key The key with which the specified chunk is to be associated. This MUST be the ID
	 * of the specified chunk.
	 * @param chunk The chunk to add to the chunk collection.
	 * @return The result of putting the chunk in the chunk collection. This will 
	 * always return null (meaning the chunk did not previously exist in the map). This is 
	 * because you are not allowed to put a chunk into a chunk collection that already 
	 * contains that chunk.
	 * @throws IllegalArgumentException If the chunk is already in the chunk collection
	 * or the specified key is not the ID of the specified chunk.
	 */
	public C put (Object key, C chunk) throws IllegalArgumentException
	{
		if(containsKey(key) || containsValue(chunk) || (key == null && chunk.getID() != null) || (key != null && !key.equals(chunk.getID())))
			throw new IllegalArgumentException ("The specified chunk is already in this " +
					"chunk collection or the specified key is not the ID of the " +
					"specified chunk.");
		return super.put(key,chunk);
	}
	
	/**
	 * Puts all of the chunks in the map into the chunk collection as long as the 
	 * chunks are not already in the chunk collection.
	 * @param map The map of chunks to add.
	 */
	public void putAll (Map <? extends Object, ? extends C> map)
	{
		for(Map.Entry<? extends Object, ? extends C> e : map.entrySet())
			put(e.getKey(),e.getValue());
	}
	
	public abstract DimensionValueCollection toDimensionValueCollection ();
	
	public abstract boolean equals (Object ChCollection);
	
	public abstract boolean containsKeys (Object ChCollection);
}
