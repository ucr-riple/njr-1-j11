package relop;

import global.SearchKey;
import heap.HeapFile;
import index.HashIndex;
import global.RID;
import index.BucketScan;

/**
 * Wrapper for bucket scan, an index access method.
 */
public class IndexScan extends Iterator {
	private BucketScan scan;
	private boolean	ifopen;
	private HashIndex index;
	private HeapFile file;
	
  /**
   * Constructs an index scan, given the hash index and schema.
   */
  public IndexScan(Schema schema, HashIndex index, HeapFile file) {
    this.schema = schema;
    this.scan = index.openScan();
    this.ifopen = true;
    this.index = index;
    this.file = file;
    //throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
		System.out.println("One-line explaination");
    //throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
		scan = this.index.openScan();
	  this.ifopen = true;
    //throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Returns true if the iterator is open; false otherwise.
   */
  public boolean isOpen() {
		return this.ifopen;
    //throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Closes the iterator, releasing any resources (i.e. pinned pages).
   */
  public void close() {
		this.scan.close();
	  this.ifopen = false;
    //throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
		if (this.ifopen == false){
		  throw new UnsupportedOperationException("Scan not open");
	  }
	  else{
		  return this.scan.hasNext();
	  }
    //throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {
		if (this.ifopen == false){
		  throw new UnsupportedOperationException("Scan not open");
	  }
	  else{
		  RID rid = this.scan.getNext();
		  Tuple mytuple = new Tuple(this.schema , this.file.selectRecord(rid));
		  return mytuple;
	  }
    //throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Gets the key of the last tuple returned.
   */
  public SearchKey getLastKey() {
		if (this.ifopen == false){
		  throw new UnsupportedOperationException("Scan not open");
	  }
	  else{
		  return this.scan.getLastKey();
	  }
    //throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Returns the hash value for the bucket containing the next tuple, or maximum
   * number of buckets if none.
   */
  public int getNextHash() {
		if (this.ifopen == false){
		  throw new UnsupportedOperationException("Scan not open");
	  }
	  else{
		  return this.scan.getNextHash();
	  }
    //throw new UnsupportedOperationException("Not implemented");
  }
} // public class IndexScan extends Iterator
