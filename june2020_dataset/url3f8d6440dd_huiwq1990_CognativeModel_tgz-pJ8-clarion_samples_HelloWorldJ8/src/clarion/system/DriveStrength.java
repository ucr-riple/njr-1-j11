package clarion.system;

/**
 * This class implements a drive strength within CLARION.
 * It extends the DimensionlessOutputChunk class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A drive strength is used within drives in the output layer of an implicit module. A drive strength 
 * MUST have the same ID as the drive to which it is associated.
 * <p>
 * While this class still contains all the functionality of a chunk, only the activation field is used.
 * Therefore it is not necessary to define any dimension-value pairs for a dimension-less output chunk.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class DriveStrength extends DimensionlessOutputChunk {
	private static final long serialVersionUID = 547914416973551411L;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**
	 * Initializes the drive strength to be associated with the drive with the specified ID.
	 * @param id The ID of the drive to which this drive strength is to be associated.
	 */
	public DriveStrength (Object id)
	{
		super(id);
		hash = System.identityHashCode(this);
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the drive strength.
	 * @return A copy of the drive strength.
	 */
	public DriveStrength clone() {
		DriveStrength a = new DriveStrength(getID());
		a.INITIAL_BLA = INITIAL_BLA;
		a.C = C;
		a.D = D;
		a.T = T.clone();
		a.LatestTimeStamp = LatestTimeStamp;
		a.hash = hash;
		return a;
	}
	
	public String toString ()
	{
		return getID().toString() + ": " + getActivation() + "\n";
	}
}
