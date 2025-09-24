package relop;


import global.AttrOperator;
import global.AttrType;
import global.RID;
import global.SearchKey;
import heap.HeapFile;
import index.HashIndex;
import relop.FileScan;
import relop.HashJoin;
import relop.IndexScan;
import relop.KeyScan;
import relop.Predicate;
import relop.Projection;
import relop.Schema;
import relop.Selection;
import relop.SimpleSortMergeJoin;
import relop.Tuple;

/**
 * Implements the hash-based join algorithm described in section 14.4.3 of the
 * textbook (3rd edition; see pages 463 to 464). HashIndex is used to partition
 * the tuples into buckets, and HashTableDup is used to store a partition in
 * memory during the matching phase.
 */
public class HashJoin extends Iterator {
  public final static int NUM_BUCKETS=5;
  public static int hfid=0;
  public Iterator left;
  public Iterator right;
  public boolean openning;
  public boolean isHasNext;
  public Tuple nextTuple;
  public Tuple leftTuple;
  public Tuple rightTuple;
  public boolean isFirstCall;
  public Predicate pred;
  public HeapFile lefthf[];
  public HeapFile righthf[];
  public FileScan leftscan[];
  public FileScan rightscan[];
  public FileScan curright;
  public HashTableDup hashTableDup;
  public int rcol;
  public int lcol;
  public int ind;
  public int leftind;
  public int rightind;
  public Tuple ltuples[];

  /**
   * Constructs a hash join, given the left and right iterators and which
   * columns to match (relative to their individual schemas).
   */

  public HashJoin(Iterator left, Iterator right, Integer lcol, Integer rcol) {
//    throw new UnsupportedOperationException("Not implemented");
    lefthf=getHeapFile(left,lcol);
    righthf=getHeapFile(right,rcol);
    leftscan=getFileScan(lefthf,left.schema);
    rightscan=getFileScan(righthf,right.schema);
    this.left=left;
    this.right=right;
    this.lcol=lcol;
    this.rcol=rcol;
    this.schema=Schema.join(left.schema,right.schema);
    pred=getPred(left.schema,right.schema,lcol,rcol);
    openning=true;
    isHasNext=false;
    nextTuple=null;
    leftTuple=null;
    rightTuple=null;
    isFirstCall=true;
    prepareNext();
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
//    throw new UnsupportedOperationException("Not implemented");
    indent(depth);
    System.out.println("This is a HashJoin Iterator");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
//    throw new UnsupportedOperationException("Not implemented");
    //I dont know how to do this yet.
    left.restart();
    right.restart();
    for(int i=0;i<NUM_BUCKETS;i++){
      leftscan[i].close();
      rightscan[i].close();
      lefthf[i].deleteFile();
      righthf[i].deleteFile();
    }
    lefthf=getHeapFile(left,lcol);
    righthf=getHeapFile(right,rcol);
    leftscan=getFileScan(lefthf,left.schema);
    rightscan=getFileScan(righthf,right.schema);

    leftTuple=null;
    rightTuple=null;
    isFirstCall=true;
    isHasNext=false;
    nextTuple=null;
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
    for(int i=0;i<NUM_BUCKETS;i++){
      leftscan[i].close();
      rightscan[i].close();
      lefthf[i].deleteFile();
      righthf[i].deleteFile();
    }
    this.left.close();
    this.right.close();
    this.left=null;
    this.right=null;
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
    boolean flag=false;
    Tuple tuple=null;
    Tuple tmpTuple;
    SearchKey searchKey;
    if (isFirstCall){
      isFirstCall=false;
      hashTableDup=new HashTableDup();
      leftind=0;
      ind=0;
      ltuples=new Tuple[0];
      while(leftscan[leftind].hasNext()){
        tmpTuple=leftscan[leftind].getNext();
        searchKey=getSearchKey(tmpTuple,left.schema,lcol);
        hashTableDup.add(searchKey,tmpTuple);
      }
      curright=rightscan[leftind];
      leftind++;
    }
    while((!flag) && ((leftind<NUM_BUCKETS) || (curright.hasNext()) || (ind<ltuples.length))){
      if (ind<ltuples.length){
        leftTuple=ltuples[ind];
        ind++;
        tuple=Tuple.join(leftTuple,rightTuple,this.schema);
        flag=true; //this must be true!
      }
      while((!flag)&&(curright.hasNext())){
        rightTuple=curright.getNext();
//System.out.print("right tuple:");
//rightTuple.print();
        searchKey=getSearchKey(rightTuple,right.schema,rcol);
        ltuples=hashTableDup.getAll(searchKey);
        if (ltuples!=null){ //>0 or null?

//System.out.print("true!left tuple:");
          flag=true;
          ind=0;
          leftTuple=ltuples[ind];
//leftTuple.print();
          ind++;
          tuple=Tuple.join(leftTuple,rightTuple,this.schema);
          flag=true; //this must be true!
        }
      }
      if ((!curright.hasNext()) && (leftind<NUM_BUCKETS)){//move to next bucket
        hashTableDup=new HashTableDup();
        while(leftscan[leftind].hasNext()){
          tmpTuple=leftscan[leftind].getNext();
          searchKey=getSearchKey(tmpTuple,left.schema,lcol);
          hashTableDup.add(searchKey,tmpTuple);
        }
        curright=rightscan[leftind];
        leftind++;
        ind=0;
        ltuples=new Tuple[0];
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
  private Predicate getPred(Schema s1, Schema s2, int l1, int l2){
    Predicate ret=null;
    int type=s1.fieldType(l1);
    int offset=s1.getCount();
    if (type==AttrType.INTEGER){
//System.out.printf("Pred:int, <%d>,<%d+%d>\n",l1,l2,offset);
//      ret=new Predicate(AttrOperator.EQ,AttrType.INTEGER,(int)l1,AttrType.INTEGER,(int)(l2+offset));
        ret=new Predicate(AttrOperator.EQ,AttrType.FIELDNO,l1,AttrType.FIELDNO,(l2+offset));
    }
    else if(type==AttrType.FLOAT){
      ret=new Predicate(AttrOperator.EQ,AttrType.FLOAT,l1,AttrType.FLOAT,l2+offset);
    }
    else if(type==AttrType.STRING){
      ret=new Predicate(AttrOperator.EQ,AttrType.STRING,l1,AttrType.STRING,l2+offset);
    }
    else{
      System.out.println("undefined search key type");
    }
    return ret;
  }
  private HeapFile[] getHeapFile(Iterator iter,int fldno){
    HeapFile hf[]=new HeapFile[NUM_BUCKETS];
    for(int i=0;i<NUM_BUCKETS;i++){
      hfid=hfid+1;
    String tmpName=((Integer)hfid).toString();
//System.out.println(tmpName);
      hf[i]=new HeapFile(tmpName);
    }
    Tuple tuple=null;
    int key;
    int ind;
    while(iter.hasNext()){
      tuple=iter.getNext();
      key=tuple.getIntFld(fldno);
      ind=key % NUM_BUCKETS;
      hf[ind].insertRecord(tuple.getData());
    }
    iter.restart();
    return hf;
  }
  private FileScan[] getFileScan(HeapFile hf[], Schema schema){
    FileScan fs[]=new FileScan[NUM_BUCKETS];
    for(int i=0;i<NUM_BUCKETS;i++){
      fs[i]=new FileScan(schema,hf[i]);
//      fs[i].execute();
    }
    return fs;
  }
  private SearchKey getSearchKey(Tuple tuple, Schema schema, int fldno){
    int type=schema.fieldType(fldno);
    SearchKey searchKey=null;
    if (type==AttrType.INTEGER){
      Integer field;
      field=tuple.getIntFld(fldno);
      searchKey=new SearchKey(field);
    }
    else if(type==AttrType.FLOAT){
      Float field;
      field=tuple.getFloatFld(fldno);
      searchKey=new SearchKey(field);
    }
    else if(type==AttrType.STRING){
      String field;
      field=tuple.getStringFld(fldno);
      searchKey=new SearchKey(field);
    }
    else{
      System.out.println("undefined search key type");
    }
    return searchKey;
  }

} // public class HashJoin extends Iterator
