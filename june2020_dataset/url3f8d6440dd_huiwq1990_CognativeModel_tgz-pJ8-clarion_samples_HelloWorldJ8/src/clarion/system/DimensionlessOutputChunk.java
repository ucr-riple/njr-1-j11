package clarion.system;

/**
 * This class implements a dimension-less output chunk within CLARION.
 * It extends the AbstractOutputChunk class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A dimension-less output chunk is a specific type of output chunk that can be set as a node in the output layer of an
 * implicit module, however does not require (or make use of) dimensions. Like a normal output chunk, a dimension-less
 * output chunk has an activation that can be used to store pertinent activation information, but unlike output chunks
 * such as actions or goals, a dimension-less output chunk has does not have dimensions.
 * <p>
 * In the CLARION Library, the dimension-less output chunks is most often used in the MCS to keep track of things like the 
 * selection measures for both the top and bottom level of the CLARION subsystems, which is used for combination and/or 
 * stochastic selection. In addition, it is also used by the drives in the MS for drive strengths.
 * <p>
 * While this class still contains all the functionality of a chunk, only the activation field is used.
 * Therefore it is not necessary to define any dimension-value pairs for a dimension-less output chunk.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>DriveStrength</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public class DimensionlessOutputChunk extends AbstractOutputChunk {
	private static final long serialVersionUID = -9040071390692158062L;

	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**
	 * Initializes the dimension-less output chunk with the ID specified.
	 * @param id The ID of the output chunk.
	 */
	public DimensionlessOutputChunk (Object id)
	{
		super(id);
		hash = System.identityHashCode(this);
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the dimension-less output chunk.
	 * @return A copy of the dimension-less output chunk.
	 */
	public DimensionlessOutputChunk clone() {
		DimensionlessOutputChunk a = new DimensionlessOutputChunk (getID());
		a.INITIAL_BLA = INITIAL_BLA;
		a.C = C;
		a.D = D;
		a.T = T.clone();
		a.LatestTimeStamp = LatestTimeStamp;
		a.hash = hash;
		return a;
	}
}
