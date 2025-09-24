package relop;

import java.util.ArrayList;

import externalSort.SortMergeJoin;

/**
 * The selection operator specifies which tuples to retain under a condition; in
 * Minibase, this condition is simply a set of independent predicates logically
 * connected by OR operators.
 */
public class Selection extends Iterator {
ArrayList<Tuple> tuples = new ArrayList<Tuple>();
java.util.Iterator<Tuple> iter;
Schema schema;
  /**
   * Constructs a selection, given the underlying iterator and predicates.
   */
  public Selection(Iterator iter, Predicate... preds) {
    while(iter.hasNext()){
    	boolean failed = false;
    	Tuple t = iter.getNext();
	    for(Predicate pred : preds){	
	    	if(!pred.evaluate(t)){
	    		failed = true;
	    	}
	    }
	    if(!failed){
	    	tuples.add(t);
	    }
    }
    this.iter = tuples.iterator();
    schema = iter.getSchema();
    this.setSchema(schema); 
  }

  public Selection(SortMergeJoin join2, Predicate predicate) {
	  schema = join2.getSchema();
	  iter = join2.iter;
	  while(iter.hasNext()){
		  Tuple t = join2.iter.next();	
		  if(predicate.evaluate(t)){
		    		tuples.add(t);
		  }
	  }
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

} // public class Selection extends Iterator
