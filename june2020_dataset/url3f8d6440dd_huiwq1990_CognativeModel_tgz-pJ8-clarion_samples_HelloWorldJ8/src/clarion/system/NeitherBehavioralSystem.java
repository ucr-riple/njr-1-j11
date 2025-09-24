package clarion.system;

import java.util.Collection;

/**
 * This class implements the system of drives that belong to neither behavioral system within the MS. 
 * It extends a drive drive collection. This class should never be instantiated outside of the CLARION library and 
 * is simply public to facilitate the changing of the gain parameters (if required).
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of the MS are initialized.
 * @author Nick Wilson
 */
/**
 * This class implements the system of drives that belong to neither behavioral system within CLARION.
 * It extends the DriveCollection class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The "neither" behavioral system is located within the MS and contains the drives that are neither "approach" nor
 * "avoidance" in nature.
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
public class NeitherBehavioralSystem extends DriveCollection {
	private static final long serialVersionUID = -6596201418243898064L;
	
	/**The system gain for the drives that belong to neither behavioral system.*/
	public static double GLOBAL_GAIN = 1;
	/**The system gain for the drives that belong to neither behavioral system.*/
	public double GAIN = GLOBAL_GAIN;
	
	/**
	 * Initializes a system to hold drives that do not belong to a behavioral system.
	 */
	public NeitherBehavioralSystem ()
	{
		super();
	}
	
	/**
	 * Initializes a system to hold drives that do not belong to a behavioral system
	 * with the collection of drives specified.
	 * @param drives The drives for the collection.
	 */
	public NeitherBehavioralSystem (Collection <Drive> drives)
	{
		super(drives);
	}
}
