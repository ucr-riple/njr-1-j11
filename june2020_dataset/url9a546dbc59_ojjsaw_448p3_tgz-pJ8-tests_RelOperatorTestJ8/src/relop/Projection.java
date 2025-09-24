package relop;

/**
 * The projection operator extracts columns from a relation; unlike in
 * relational algebra, this operator does NOT eliminate duplicate tuples.
 */
public class Projection extends Iterator {
	Iterator iter;
	Integer[] fields;
  /**
   * Constructs a projection, given the underlying iterator and field numbers.
   */
  public Projection(Iterator iter, Integer... fields) {
	  this.iter = iter;
	  this.fields = fields;
	  this.setSchema(new Schema(fields.length));
	    for(int i = 0; i < fields.length; i++){
	    	this.getSchema().initField(i, iter.getSchema(), fields[i]);
	    }
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
    iter.restart();
  }

  /**
   * Returns true if the iterator is open; false otherwise.
   */
  public boolean isOpen() {
    return iter.isOpen();
  }

  /**
   * Closes the iterator, releasing any resources (i.e. pinned pages).
   */
  public void close() {
    iter.close();
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
	if(!this.hasNext()){
		throw new IllegalStateException();
	}
    Tuple t = iter.getNext(); 
    Tuple newtuple = new Tuple(this.getSchema());
    for(int i = 0; i < fields.length; i++){
    	newtuple.setField(i, t.getField(fields[i]));
    }
    return newtuple;
  }

} // public class Projection extends Iterator
