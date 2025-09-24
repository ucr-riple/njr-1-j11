package relop;

/**
 * The projection operator extracts columns from a relation; unlike in
 * relational algebra, this operator does NOT eliminate duplicate tuples.
 */
public class Projection extends Iterator {
  public Iterator iter;
  public int fields[];
  public boolean openning;
  public Schema oldSchema;
  /**
   * Constructs a projection, given the underlying iterator and field numbers.
   */
  public Projection(Iterator iter, Integer... fields) {
//    throw new UnsupportedOperationException("Not implemented");
    this.oldSchema=iter.schema;
    this.iter=iter;
    schema=new Schema(fields.length);
    int i;
    int fldno;
    this.fields=new int[fields.length];
    for(i=0;i<fields.length;i++){
      fldno=fields[i].intValue();
      this.fields[i]=fldno;
      schema.initField(i,oldSchema.fieldType(fldno),oldSchema.fieldLength(fldno),oldSchema.fieldName(fldno));
    }
    openning=true;
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
//    throw new UnsupportedOperationException("Not implemented");
    indent(depth);
    System.out.println("This is a Projection Iterator");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
//    throw new UnsupportedOperationException("Not implemented");
    iter.restart();
  }

  /**
   * Returns true if the iterator is open; false otherwise.
   */
  public boolean isOpen() {
//    throw new UnsupportedOperationException("Not implemented");
    return openning;
  }

  /**
   * Closes the iterator, releasing any resources (i.e. pinned pages).
   */
  public void close() {
//    throw new UnsupportedOperationException("Not implemented");
    openning=false;
    iter.close();
    iter=null;
    oldSchema=null;
    schema=null;
   }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
//    throw new UnsupportedOperationException("Not implemented");
    return iter.hasNext();
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {
//    throw new UnsupportedOperationException("Not implemented");
    if (iter.hasNext()){
      Tuple tuple=iter.getNext();
      Tuple newTuple=new Tuple(schema);
      for(int i=0;i<fields.length;i++){
        newTuple.setField(i,tuple.getField(fields[i]));
      }
      return newTuple;
    }
    else{
      throw new UnsupportedOperationException("Not has next");
    }
  }

} // public class Projection extends Iterator
