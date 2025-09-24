package clarion.system;

import java.util.Collection;

/**
 * This class implements the behavioral approach system (BAS) within CLARION.
 * It extends the DriveCollection class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The BAS is located within the MS and contains the drives that are "approach" in nature.
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
public class BehavioralApproachSystem extends DriveCollection {
	private static final long serialVersionUID = -4125897746621120593L;	
	
	/**The gain for the BAS*/
	public static double GLOBAL_GAIN = 1;
	/**The gain for the BAS*/
	public double GAIN = GLOBAL_GAIN;
	
	/**
	 * Initializes the behavioral approach system.
	 */
	public BehavioralApproachSystem ()
	{
		super();
	}
	
	/**
	 * Initializes the behavioral approach system with the collection of drives specified.
	 * @param drives The drives for the collection.
	 */
	public BehavioralApproachSystem (Collection <Drive> drives)
	{
		super(drives);
	}
}
