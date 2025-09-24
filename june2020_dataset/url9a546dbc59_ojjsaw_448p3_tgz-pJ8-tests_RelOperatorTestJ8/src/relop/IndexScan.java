package relop;

import global.SearchKey;
import heap.HeapFile;
import index.BucketScan;
import index.HashIndex;

/**
 * Wrapper for bucket scan, an index access method.
 */
public class IndexScan extends Iterator {
BucketScan bs;
Schema schema;
HeapFile file;
HashIndex index;
boolean closed;
  /**
   * Constructs an index scan, given the hash index and schema.
   */
  public IndexScan(Schema schema, HashIndex index, HeapFile file) {
	this.schema = schema;
	this.file = file;
	this.index = index;
	bs = index.openScan();
	closed = false;
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
	  bs.close();
	  bs = index.openScan();
  }

  /**
   * Returns true if the iterator is open; false otherwise.
   */
  public boolean isOpen() {
	  return !closed;
  }

  /**
   * Closes the iterator, releasing any resources (i.e. pinned pages).
   */
  public void close() {
	  bs.close();
	  closed = true;
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
    return bs.hasNext();
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {
	  if(this.hasNext()){
		  return new Tuple(schema, file.selectRecord(bs.getNext()));
	  }
	  else{
		  //DOUBT: Do Nothing
		  return null;
	  } 
  }

  /**
   * Gets the key of the last tuple returned.
   */
  public SearchKey getLastKey() {
    //TODO:
	  return null;
  }

  /**
   * Returns the hash value for the bucket containing the next tuple, or maximum
   * number of buckets if none.
   */
  public int getNextHash() {
    return bs.getNextHash();
  }

} // public class IndexScan extends Iterator
