package relop;

import global.SearchKey;
import global.RID;
import heap.HeapFile;
import heap.*;
import index.HashIndex;
import index.*;

/**
 * Wrapper for hash scan, an index access method.
 */
public class KeyScan extends Iterator {
  public HeapFile heapFile;
  public HashScan hashScan;
  public HashIndex hashIndex;
  public SearchKey key;
  boolean openning;
  RID lastRid;
  /**
   * Constructs an index scan, given the hash index and schema.
   */
  public KeyScan(Schema schema, HashIndex index, SearchKey key, HeapFile file) {
//    throw new UnsupportedOperationException("Not implemented");
    this.schema=schema;
    this.key=key;
    hashScan=index.openScan(key);
    heapFile=file;
    hashIndex=index;
    openning=true;
    lastRid=null;   
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
//    throw new UnsupportedOperationException("Not implemented");
    indent(depth);
    System.out.println("This is a KeyScan Iterator");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
//    throw new UnsupportedOperationException("Not implemented");
    if (isOpen()){
      hashScan.close();
      hashScan=hashIndex.openScan(this.key);
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
    hashScan.close();
    openning=false;
    hashScan=null;
    lastRid=null;
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
//    throw new UnsupportedOperationException("Not implemented");
    if (isOpen()){
      return hashScan.hasNext();
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
      lastRid=hashScan.getNext();
      data=heapFile.selectRecord(lastRid);
      return new Tuple(this.schema,data);
    }
    else{
      throw new UnsupportedOperationException("Not openning");
    }

  }

} // public class KeyScan extends Iterator
