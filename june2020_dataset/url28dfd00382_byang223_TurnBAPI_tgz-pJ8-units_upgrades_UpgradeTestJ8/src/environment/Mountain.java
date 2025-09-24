package environment;

/**
 * Concrete example of a permanent environment
 * @author lenajia
 *
 */
public class Mountain extends Permanent{

	 /**
     * Constructor for mountain environments. 
     * @param ratio
     */
	public Mountain(double ratio) {
	    super(ratio);
    }

	@Override
    public String Name() {
	    // TODO Auto-generated method stub
	    return "Mountain";
    }

	@Override
    public String getImageURL() {
	    // TODO Auto-generated method stub
	    return "resources/environment/mountain.png";
    }
}
