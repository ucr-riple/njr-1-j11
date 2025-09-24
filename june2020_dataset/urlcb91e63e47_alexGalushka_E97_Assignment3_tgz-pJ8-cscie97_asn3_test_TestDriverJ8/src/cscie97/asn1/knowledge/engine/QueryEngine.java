package cscie97.asn1.knowledge.engine;

import java.util.Set;

/**
 * The QueryEngine class supports the execution of Knowledge Graph queries. 
 * @author APGalush
 *
 */
public class QueryEngine
{
	
	/**
	 * Executes a single query on the knowledge graph, checks for non null and well formed query string, throws QueryEngineException
     * on error. 
	 * @param query - type: String
	 * @return 
	 * @throws QueryEngineException
	 */
	public Set<Triple> executeQuery ( String query ) throws QueryEngineException
	{
		Set<Triple> setOfTriples;
		if ( query.equals( null ) )
		{
			throw new QueryEngineException( "Query is null" );
		}
		
		String[] tempResult = query.split( " " );
		//make sure the query is correct
		if ( tempResult.length == 3 )
		{
			Node subject_obj = new Node ( tempResult[0] );
			Predicate predicate_obj = new Predicate( tempResult[1] );
			Node object_obj = new Node ( tempResult[2] );
			Triple queryTriple = new Triple ( subject_obj, predicate_obj, object_obj );

			setOfTriples = KnowledgeGraph.getInstance().executeQuery( queryTriple ) ;
		}
		else
		{
			throw new QueryEngineException( "Incorrect query" );
		}
		
		return setOfTriples;
	}
	

}
