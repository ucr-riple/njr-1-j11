package relop;

import java.util.ArrayList;

/**
 * The simplest of all join algorithms: nested loops (see textbook, 3rd edition,
 * section 14.4.1, page 454).
 */
public class SimpleJoin extends Iterator {
Schema schema;
ArrayList<Tuple> tuples = new ArrayList<Tuple>();
java.util.Iterator<Tuple> iter;
  /**
   * Constructs a join, given the left and right iterators and join predicates
   * (relative to the combined schema).
   */
  public SimpleJoin(Iterator left, Iterator right, Predicate... preds) {
    schema = Schema.join(left.getSchema(), right.getSchema());
    	while(right.hasNext() && left.hasNext()){
    		Tuple l = left.getNext();
    		Tuple r = right.getNext();
    	    boolean failed = false;
    	    	Tuple t = Tuple.join(l,r,schema);
    		    for(Predicate pred : preds){	
    		    	if(!pred.evaluate(t)){
    		    		failed = true;
    		    	}
    		    }
    		    if(!failed){
    		    	tuples.add(t);
    		    }
        }
   // }
    this.iter = tuples.iterator();
    this.setSchema(schema);
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
    //TODO:
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
	  iter = tuples.iterator();
  }

  /**
   * Returns true if the iterator is open; false otherwise.
   */
  public boolean isOpen() {
	  return iter != null;
  }

  /**
   * Closes the iterator, releasing any resources (i.e. pinned pages).
   */
  public void close() {
	  iter = null;
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
    return iter.hasNext();
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {
    return iter.next();
  }

} // public class SimpleJoin extends Iterator
