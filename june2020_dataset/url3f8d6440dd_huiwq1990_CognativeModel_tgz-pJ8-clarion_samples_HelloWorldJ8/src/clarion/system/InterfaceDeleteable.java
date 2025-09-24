package clarion.system;

/**
 * This interface is implemented by classes that are deleteable within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will be considered to be deleteable by the CLARION Library and
 * will therefore be periodically be subject to consideration for deletion.
 * <p>
 * <b>Known Subinterfaces:</b><br>
 * <ul>
 * <li>InterfaceDeleteableByDensity</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface InterfaceDeleteable {
	
	/**
	 * Checks to see if an object should be deleted.
	 * @return True if deletion should occur, otherwise false.
	 */
	boolean checkDeletion ();
}
