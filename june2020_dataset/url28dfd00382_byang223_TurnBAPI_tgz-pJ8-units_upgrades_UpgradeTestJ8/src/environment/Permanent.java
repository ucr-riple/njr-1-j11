package environment;

/**
 * Type of environment that is permanently on the map
 * @author lenajia
 *
 */
public abstract class Permanent extends Environment implements java.io.Serializable {

	 /**
     * Constructor for permanent environments. Set image-to-tile ratio size. 
     * @param ratio
     */
	public Permanent(double ratio) {
	    super(ratio);
    }

	
	/** Always returns true (environment is always active)
	 */
	public boolean isActive(int turn){
		return true;
	}
	
	
}
