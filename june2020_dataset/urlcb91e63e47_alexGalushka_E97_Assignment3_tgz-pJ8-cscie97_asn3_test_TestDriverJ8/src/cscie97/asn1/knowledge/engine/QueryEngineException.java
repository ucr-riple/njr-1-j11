package cscie97.asn1.knowledge.engine;

/**
 * QueryEngineException extending Exception class, it is used for all query associated exceptions
 * @author APGalush
 *
 */
public class QueryEngineException extends Exception
{
	private static final long serialVersionUID = -3516534599247815129L;
	
	public QueryEngineException()
	{ 
		super();
	}
	public QueryEngineException(String message) 
	{
		super(message);
	}

}
