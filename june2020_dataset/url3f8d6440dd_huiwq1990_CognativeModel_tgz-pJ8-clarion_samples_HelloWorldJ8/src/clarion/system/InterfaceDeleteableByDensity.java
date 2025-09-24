package clarion.system;

/**
 * This interface is implemented by classes that are deleteable by density within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will be considered to be deleteable by density (determined
 * using a time stamp) within the CLARION Library and will therefore be periodically be subject to consideration 
 * for deletion.
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>AbstractIRLRule</li>
 * <li>RefineableRule</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface InterfaceDeleteableByDensity extends InterfaceDeleteable {

	/**
	 * Checks to see if an object should be deleted based on the density (determined using the
	 * specified time stamp).
	 * @param TimeStamp The current time stamp used for calculating density.
	 * @return True if deletion should occur, otherwise false.
	 */
	boolean checkDeletionByDensity (long TimeStamp);
}
