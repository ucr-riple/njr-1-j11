package relop;

/**
 * A simpler version of Sort-Merge Join (see textbook, 3rd edition,
 * section 14.4.2, page 454).
 */
public class SimpleSortMergeJoin extends Iterator {
  public Iterator left;
  public Iterator right;
  public Predicate preds[];
  public boolean openning;
  public boolean isHasNext;
  public Tuple nextTuple;
  public Tuple leftTuple[];
  public Tuple rightTuple[];
  public int leftind;
  public int rightind;
  public int leftbase;
  public int rightbase;
  public int leftfldno;
  public int rightfldno;

  /**
   * Constructs a join, given the left and right iterators and join predicates
   * (relative to the combined schema).
   */
  public SimpleSortMergeJoin(Iterator left, Iterator right, Predicate... preds) {
//    throw new UnsupportedOperationException("Not implemented");
    this.schema=Schema.join(left.schema,right.schema);
    this.left=left;
    this.right=right;
    this.preds=preds;
    leftfldno=((Integer)preds[0].left).intValue();
    rightfldno=((Integer)preds[0].right).intValue()-left.schema.getCount();
//    System.out.printf("<%d,%d>\n",leftfldno,rightfldno);
    openning=true;
    isHasNext=false;
//Read all in memory;
    leftTuple=readFromIter(left);
    rightTuple=readFromIter(right);
    sortTuples(leftTuple,leftfldno);
    sortTuples(rightTuple,rightfldno);
    leftind=0;
    rightind=0;
    leftbase=0;
    rightbase=0;
    prepareNext();
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
//    throw new UnsupportedOperationException("Not implemented");
    indent(depth);
    System.out.println("This is a SimpleSortMergeJoin Iterator");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
//    throw new UnsupportedOperationException("Not implemented");
    left.restart();
    right.restart();
    leftTuple=null;
    rightTuple=null;
    openning=true;
    isHasNext=false;
    leftTuple=readFromIter(left);
    rightTuple=readFromIter(right);
    sortTuples(leftTuple,leftfldno);
    sortTuples(rightTuple,rightfldno);
    leftind=0;
    rightind=0;
    leftbase=0;
    rightbase=0;
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
    leftTuple=null;
    rightTuple=null;
    left.close();
    right.close();
    isHasNext=false;
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

  private Tuple[] readFromIter(Iterator iter){
    Tuple tuple[]=new Tuple[10];
    Tuple tmp;
    int curlen=10;
    int curnum=0;
    while(iter.hasNext()){
      tmp=iter.getNext();
      if (curnum>=curlen){
        Tuple expandTuple[]=new Tuple[curlen*2];
        for(int i=0;i<curnum;i++){
          expandTuple[i]=tuple[i];
        }
        tuple=expandTuple;
        curlen=curlen*2;
      }
      tuple[curnum]=tmp;
      curnum++;
    }
    Tuple retTuple[]=new Tuple[curnum];
    for(int i=0;i<curnum;i++){
      retTuple[i]=tuple[i];
    }
    return retTuple;
  }
/*  
  private void prepareNext(){
    boolean flag=false;
    Tuple tuple=null;
    while((!flag) &&(leftind<leftTuple.length)){
      tuple=Tuple.join(leftTuple[leftind],rightTuple[rightind],schema);
      flag=checkPreds(tuple);
      rightind++;
      if (rightind>=rightTuple.length){
        rightind=0;
        leftind++;
      }
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
*/
  private void prepareNext(){
    boolean flag=false;
    Tuple tuple=null;
    String si;
    String sj;
    while((!flag) &&(leftind<leftTuple.length) && (rightind<rightTuple.length)){
      tuple=Tuple.join(leftTuple[leftind],rightTuple[rightind],schema);
      flag=checkPreds(tuple);
      si=leftTuple[leftind].getField(leftfldno).toString();
      sj=rightTuple[rightind].getField(rightfldno).toString();
      if (si.compareTo(sj)<0){ //shift left
        leftind++;leftbase=leftind;
        rightind=rightbase;
      }
      else if (si.compareTo(sj)>0){  //shift right
        rightind++;rightbase=rightind;
        leftind=leftbase;
      }
      else{
        if (leftind<leftTuple.length-1){
          leftind++;
          rightind=rightbase;
        }
        else{
          rightind++;rightbase=rightind;
          leftind=leftbase;
        }
      }
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
  private void sortTuples(Tuple tuple[],int fldno){
    int i;
    int j;
    String si;
    String sj;
    Tuple tmp;
//    System.out.printf("in Sort Tuples <%d,%d>\n",tuple.length,fldno);
    for(i=0;i<tuple.length;i++){
      for(j=i+1;j<tuple.length;j++){
        si=tuple[i].getField(fldno).toString();
        sj=tuple[j].getField(fldno).toString();
        if (si.compareTo(sj)>0){
          tmp=tuple[i];
          tuple[i]=tuple[j];
          tuple[j]=tmp;
        }
      }
    }
//    for(i=0;i<tuple.length;i++){
//      tuple[i].print();
//    }
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

} // public class SimpleJoinSimpleSortMergeJoin extends Iterator
