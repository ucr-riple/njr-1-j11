package cscie97.asn1.knowledge.engine;

/**
 * The Node class represents instances of Subjects and Objects
 * Note that a single instance of a Node can represent both a Subject and an Object within the Knowledge Graph
 * @author APGalush
 *
 */
public class Node
{
	
	private String identifier;
	
	public Node (String identifier)
	{
		this.identifier = identifier;
	}
	
	/**
	 * Returns the Node identifier
	 * @return identifier - type: String
	 */
    public String getIdentifier ()
    {
    	return identifier;
    }

}
