package relop;

import global.RID;
import heap.HeapFile;
import heap.*;

/**
 * Wrapper for heap file scan, the most basic access method. This "iterator"
 * version takes schema into consideration and generates real tuples.
 */
public class FileScan extends Iterator {
  HeapScan heapScan;
  HeapFile heapFile;
  boolean openning;
  RID lastRid;
  /**
   * Constructs a file scan, given the schema and heap file.
   */
  public FileScan(Schema schema, HeapFile file) {
//    throw new UnsupportedOperationException("Not implemented");
    this.schema=schema;
    heapScan=file.openScan();
    heapFile=file;
    openning=true;
    lastRid=new RID();
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
//    throw new UnsupportedOperationException("Not implemented");
    indent(depth);
    System.out.println("This is a FileScan Iterator");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
//    throw new UnsupportedOperationException("Not implemented");
    if (isOpen()){
      heapScan.close();
      heapScan=heapFile.openScan();
      openning=true;
    }
    else{
      throw new UnsupportedOperationException("Not implemented");
    }
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
    heapScan.close();
    openning=false;
    heapScan=null;
    lastRid=null;
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
//    throw new UnsupportedOperationException("Not implemented");
    if (isOpen()){
      return heapScan.hasNext();
    }
    else{
      throw new UnsupportedOperationException("Not openning");
    }
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {
//    throw new UnsupportedOperationException("Not implemented");
    byte[] data;
    if (isOpen()){
      data=heapScan.getNext(lastRid);
      return new Tuple(this.schema,data);
    }
    else{
      throw new UnsupportedOperationException("Not openning");
    }

  }

  /**
   * Gets the RID of the last tuple returned.
   */
  public RID getLastRID() {
//    throw new UnsupportedOperationException("Not implemented");
    return lastRid;
  }

} // public class FileScan extends Iterator
