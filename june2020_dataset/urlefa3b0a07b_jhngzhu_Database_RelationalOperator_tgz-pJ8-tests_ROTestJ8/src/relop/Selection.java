package relop;

/**
 * The selection operator specifies which tuples to retain under a condition; in
 * Minibase, this condition is simply a set of independent predicates logically
 * connected by OR operators.
 */
public class Selection extends Iterator {
  public Iterator iter;
  public Predicate preds[];
  public boolean openning;
  public boolean isHasNext;
  public Tuple nextTuple;
  /**
   * Constructs a selection, given the underlying iterator and predicates.
   */
  public Selection(Iterator iter, Predicate... preds) {
//    throw new UnsupportedOperationException("Not implemented");
    this.schema=iter.schema;
    this.iter=iter;
    this.preds=preds;
    openning=true;
    isHasNext=false;
    prepareNext();
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
//    throw new UnsupportedOperationException("Not implemented");
    indent(depth);
    System.out.println("This is a Selection Iterator");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
//    throw new UnsupportedOperationException("Not implemented");
    iter.restart();
    openning=true;
    isHasNext=false;
    prepareNext();
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
    preds=null;
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
//    throw new UnsupportedOperationException("Not implemented");
    return isHasNext;
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {
//    throw new UnsupportedOperationException("Not implemented");
    Tuple ret;
    if (isOpen()){
      if(isHasNext){
        ret=nextTuple;
        prepareNext();
        return ret;
      }
      else{
        throw new UnsupportedOperationException("Not has next");
      }
    }
    else{
      throw new UnsupportedOperationException("Not openning");
    }
  }
  private void prepareNext(){
    Tuple tuple;
    boolean flag;
    if (iter.hasNext()){
      tuple=iter.getNext();
      flag=checkPreds(tuple);
      while((!flag) && (iter.hasNext())){
        tuple=iter.getNext();
        flag=checkPreds(tuple);
      }
      if (flag){
        isHasNext=true;
        nextTuple=tuple;
      }
      else{
        isHasNext=false;
        nextTuple=null;
      }
    }
    else{
      isHasNext=false;
      nextTuple=null;
    }
  }
  private boolean checkPreds(Tuple tuple){
    int i;
    for(i=0;i<preds.length;i++){
      if (preds[i].evaluate(tuple)){
        return true;
      }
    }
    return false;
  }
} // public class Selection extends Iterator
