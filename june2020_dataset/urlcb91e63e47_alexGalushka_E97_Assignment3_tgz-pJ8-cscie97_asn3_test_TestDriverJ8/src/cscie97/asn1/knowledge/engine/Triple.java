package cscie97.asn1.knowledge.engine;

/**
 * Represents a unique Triple (Subject, Predicate, Object) within the KnowledgeGraph.
 * A Triple contains 3 references: Subject, Predicate and Object. The Triple is uniquely
 * identified as the concatenation of the identifiers for the associated Subject, Predicate and Object
 * @author APGalush
 *
 */
public class Triple
{
    	
   private Node subject;
   private Predicate predicate;  
   private Node object;
   private String identifier;

  
   Triple (Node subject, Predicate predicate, Node object)
   {
    this.subject = subject;
    this.predicate = predicate;
    this.object = object;    
   }

   /**
    * Returns the Triple identifier
    * @return identifier - type: String
    */
	public String getIdentifier ()
	{
		// consolidate the triple identifier string from subject, predicate and object
		identifier = subject.getIdentifier() + " " + predicate.getIdentifier() + " " + object.getIdentifier();
		return identifier;
	}

/**
 * @return the subject
 */
public Node getSubject() {
	return subject;
}

/**
 * @param subject the subject to set
 */
public void setSubject(Node subject) {
	this.subject = subject;
}

/**
 * @return the predicate
 */
public Predicate getPredicate() {
	return predicate;
}

/**
 * @param predicate the predicate to set
 */
public void setPredicate(Predicate predicate) {
	this.predicate = predicate;
}

/**
 * @return the object
 */
public Node getObject() {
	return object;
}

/**
 * @param object the object to set
 */
public void setObject(Node object) {
	this.object = object;
}
	
	
}
