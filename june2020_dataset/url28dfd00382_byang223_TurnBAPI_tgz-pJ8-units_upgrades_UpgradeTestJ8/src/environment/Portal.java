package environment;

/**
 * Concrete example of a transient environment
 * @author Lena Jia
 *
 */
public class Portal extends Permanent{

	public Portal(double ratio) {
	    super(ratio);
    }

	@Override
    public String Name() {
	    return "Portal";
    }

	@Override
    public String getImageURL() {
	    return "resources/environment/portal.jpg";
    }
	
	@Override
    public boolean isActive(int turn) {
		return isActive();
    }

}
