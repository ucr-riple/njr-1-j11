package cscie97.asn1.knowledge.engine;

/**
 * The Predicate class represents the predicate portion of a Triple.
 * @author APGalush
 *
 */
public class Predicate
{

	private String identifier;
	
	public Predicate (String identifier)
	{
		this.identifier = identifier;
	}
	
	/**
	 * Returns the Predicate identifier
	 * @return identifier - type: String
	 */
    public String getIdentifier ()
    {
    	return identifier;
    }
}
