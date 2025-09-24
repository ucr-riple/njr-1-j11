package clarion.system;

import java.util.Collection;

/**
 * This class implements the behavioral inhibition system (BIS) within CLARION.
 * It extends the DriveCollection class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The BIS is located within the MS and contains the drives that are "avoidance" in nature.
 * <p>
 * This class should never be instantiated outside of the CLARION library and is simply
 * public to facilitate the changing of the gain parameters (if required).
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.4.5
 * @author Nick Wilson
 */
public class BehavioralInhibitionSystem extends DriveCollection {
	private static final long serialVersionUID = -4662461497862678438L;
	
	/**The gain for the BIS*/
	public static double GLOBAL_GAIN = 1;
	/**The gain for the BIS*/
	public double GAIN = GLOBAL_GAIN;
	
	/**
	 * Initializes the behavioral inhibition system with a default initial capacity and 
	 * load factor.
	 */
	public BehavioralInhibitionSystem ()
	{
		super();
	}
	
	/**
	 * Initializes the behavioral inhibition system with the collection of drives specified.
	 * @param drives The drives for the collection.
	 */
	public BehavioralInhibitionSystem (Collection <Drive> drives)
	{
		super(drives);
	}
}
