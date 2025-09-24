package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements an abstract output chunk collection within CLARION. It extends the AbstractChunkCollection class.
 * This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a container to hold output chunks of the specified type <C>.
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
 * @param <C> The type of output chunk being placed in the collection.
 */
public abstract class AbstractOutputChunkCollection <C extends AbstractOutputChunk> extends AbstractChunkCollection <C>{
	private static final long serialVersionUID = -4688583199277962002L;

	/**
	 * Initializes an output chunk collection.
	 */
	public AbstractOutputChunkCollection ()
	{
		super();
	}
	
	/**
	 * Initializes an output chunk collection with the collection of output chunks specified.
	 * @param outputchunks The output chunks for the collection.
	 */
	public AbstractOutputChunkCollection (Collection <? extends C> outputchunks)
	{
		super(outputchunks);
	}
	
	/**
	 * Initializes the output chunk collection with the map of output chunks.
	 * @param map The map of output chunks for the output chunk collection.
	 */
	public AbstractOutputChunkCollection (Map <? extends Object, ? extends C> map)
	{
		super(map);
	}
	
	public abstract AbstractOutputChunkCollection<? extends C> clone();
}
