package environment;

/**
 * Concrete example of a non-weather transient environment  
 * @author lenajia
 * 
 */
public class PoisonGas extends Transient {

	 /**
     * Constructor that sets image ratio size. 
     * @param ratio
     */
	public PoisonGas(double ratio, int start, int end) {
	    super(ratio, start, end);
    }
	
	@Override
    public String Name() {
	    // TODO Auto-generated method stub
	    return "PoisonGas";
    }

	@Override
    public String getImageURL() {
	    // TODO Auto-generated method stub
	    return "resources/environment/poisongas.jpg";
    }

}
