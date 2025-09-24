package cscie97.asn1.knowledge.engine;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * KnowledgeGraph manages the set of active Triples.KnowledgeGraph is a singleton, 
 * meaning there is only one instance of this class.
 * @author APGalush
 *
 */
public class KnowledgeGraph
{
	private Map<String, Node> nodeMap;
	private Map<String, Predicate> predicateMap;
	private Map<String, Triple> tripleMap;
	private Map<String, Set<Triple>> queryMapSet;	
    private static KnowledgeGraph _obj;
    Utilities util;
    private List<int[]> binTable;
    
    private KnowledgeGraph ()
    {
    	nodeMap = new HashMap<String, Node>();
    	predicateMap = new HashMap<String, Predicate>();
    	tripleMap = new HashMap<String, Triple>(); 
    	queryMapSet = new HashMap<String, Set<Triple>>();
    	util = new Utilities();
    	//creating the binary table for 8 possible combinations of triple elements
    	binTable = util.getBinTable ( 7 );
    }
     
    /**
     * A special static method to access the single KnowledgeGraph instance
     * @return _obj - type: KnowledgeGraph
     */
    public static KnowledgeGraph getInstance() 
    {
    	//Checking if the instance is null, then it will create new one and return it
        if (_obj == null)  
        //otherwise it will return previous one.
        {
            _obj = new KnowledgeGraph();
        }
        return _obj;
    }
	
	
   /**
    * Adds a list of Triples to the KnowledgeGraph. It updates the following 
	* associations: nodeMap, tripleMap, queryMapSet, predicateMap to reflect the added Triple.
    * @param listOfTriples - type: List of Triples
    */
	public void importTriples ( List<Triple> listOfTriples )
	{
		Node subject_obj;
		Predicate predicate_obj;
		Node object_obj;
		
		String subject;
		String predicate;
		String object;
		String triple;
		
		// go through the list of triples to populate KnowledgeGraph with input data
		for ( Triple element : listOfTriples )
		{
			triple = element.getIdentifier();
			
			String[] tempResult = triple.split( " " );
			
			subject = tempResult[0];
			subject_obj = new Node ( subject );
			
			object = tempResult[2];
			object_obj = new Node ( object );
			
			predicate = tempResult[1];
			predicate_obj = new Predicate ( predicate );
				
			//stuff input data into different maps
			if ( !nodeMap.containsKey( subject ) )
			{
				nodeMap.put( subject, subject_obj );
			}
			if ( !nodeMap.containsKey( object ) )
			{
				nodeMap.put( object, object_obj );
			}
			
			if ( !predicateMap.containsKey( predicate ) )
			{
				predicateMap.put( predicate, predicate_obj );
			}
			
			if ( !tripleMap.containsKey( triple ) )
			{
				tripleMap.put( triple, element );
			}
			
			if ( !queryMapSet.containsKey( triple ) )
			{
				Set<Triple> setOfTriples = new HashSet<Triple>();
				
				// use binary table representing decimal numbers from 0 to 7 to  create 8 unique combinations of triple elements
				// 1s are used as a flag for replacing the triple element with "?"
				for ( int i = 0; i < binTable.size(); i++)
				{
					// create and store unique combination of triple elements to setOfTriple
					setOfTriples.add( createQueryTriple ( binTable.get(i), subject, predicate, object ) );
				}
				
				queryMapSet.put(triple, setOfTriples);
			}
		}
	}
	
	
	/**
	 * Use the queryMapSet to determine the Triples that match the given Query
	 * @param query - type: Triple; 
	 * @return result - type: Set of Triples 
	 */
	public Set<Triple> executeQuery (Triple query)
	{
		Set<Triple> result = new HashSet<Triple>();
		if ( query != null)
		{
			String queryString = query.getIdentifier();
	
		    for (String key : queryMapSet.keySet()) 
		    {
		    	for (Triple tiple_obj : queryMapSet.get( key ) )
		    	{
		    		if ( queryString.equals(tiple_obj.getIdentifier() ) )
		    		{
		    			result.add( tripleMap.get( key ) );
		    		}
		    	}
		    }
		}
	    return result;
	}
		
	/**
	 * Return a Node Instance for the given node identifier: use the nodeMap to look up the Node; if the Node does not exist, create it 
     * and add it to the nodeMap.
	 * @param identifier - type: String
	 * @return result - type: Node
	 */
	public Node getNode ( String identifier )
	{
		String myIdentifier = identifier.toLowerCase();
		Node result  = nodeMap.get( myIdentifier );
		if (result == null)
		{
			Node myNode = new Node ( myIdentifier ); 
			nodeMap.put( myIdentifier, myNode );
			result  = myNode;
		}
		return result;
	}
	
	/**
	 * Return a Predicate Instance for the given predicate identifier: use the predicateMap to look up the Predicate; if the Predicate does not exist, creates it 
     * and add it to the predicateMap.
	 * @param identifier - type: String
	 * @return result - type: Predicate
	 */
	public Predicate getPredicate ( String identifier )
	{
		String myIdentifier = identifier.toLowerCase();
		Predicate result  = predicateMap.get( myIdentifier );
		if ( result == null )
		{
			result = new Predicate( myIdentifier ); 
			predicateMap.put( myIdentifier, result );
		}
		return result;
	}
	
	/**
	 * Return the Triple instance for the given Object, Predicate and Subject: use the tripleMap to lookup the Triple; if the Triple 
	 * does not exist, creates it and adds it to the tripleMap and update the queryMapSet.   
	 * @param subject - type: Node
	 * @param predicate - type: Predicate
	 * @param object - type: Node
	 * @return result - type: Triple
	 */
	public Triple getTriple ( Node subject, Predicate predicate, Node object )
	{
		String lookup = subject.getIdentifier() + " " + predicate.getIdentifier() + " " + object.getIdentifier();
		Triple result = tripleMap.get( lookup );
		if ( result == null )
		{			
			result = new Triple ( subject, predicate, object  );
			tripleMap.put( lookup, result );
			
			Set<Triple> setOfTriples = new HashSet<Triple>();
			
			for ( int i = 0; i < binTable.size(); i++)
			{
				// create and store unique combination of triple elements to setOfTriple
				setOfTriples.add( createQueryTriple ( binTable.get(i), subject.getIdentifier(), predicate.getIdentifier(), object.getIdentifier() ) );
			}
			
			queryMapSet.put(result.getIdentifier(), setOfTriples);
		}
		
		return result;
			
	}
	
	
	/**
	 * Creates unique query combination (triple) using the binary table and replacement "?" element for each given triple element 
	 * @param binArray - type: int array; binary representation of the decimal number, e.g "7" == "111"
	 * @param subject  - type: String; triple element
	 * @param predicate - type: String; triple element
	 * @param object - type: String; triple element
	 * @return resultTriple - type: Triple; unique query combination (triple)
	 */
	
	private Triple createQueryTriple ( int[] binArray, String subject, String predicate, String object)
	{
		Triple resultTriple;
		Node subject_obj;
		Node object_obj;
		Predicate predicate_obj;
		
		String[] arrayOfElements = {subject, predicate, object};
		
		for (int i = 0; i<binArray.length; i++)
		{
			if (binArray[i] == 1)
			{
				arrayOfElements[i] = "?";
			}
		}
		
		subject_obj = new Node ( arrayOfElements[0] );
		predicate_obj = new Predicate( arrayOfElements[1] );
		object_obj = new Node ( arrayOfElements[2] );
		resultTriple = new Triple ( subject_obj, predicate_obj, object_obj );
		return resultTriple;
	}
	
}
