package relop;

import global.RID;
import heap.HeapFile;
import heap.HeapScan;

/**
 * Wrapper for heap file scan, the most basic access method. This "iterator"
 * version takes schema into consideration and generates real tuples.
 */
public class FileScan extends Iterator {
HeapFile file;
HeapScan hs;
boolean closed;
RID lastrid;
  /**
   * Constructs a file scan, given the schema and heap file.
   */
  public FileScan(Schema schema, HeapFile file) {
    this.setSchema(schema);
    this.file = file;
    hs = file.openScan();
    closed = false;
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
	  this.close();
	  hs = file.openScan();
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
	  hs.close();
	  closed = true;
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
	  return hs.hasNext();
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {
	  if(this.hasNext()){
		  lastrid = new RID();
		  return new Tuple(this.getSchema(), hs.getNext(lastrid));
	  }
	  else{
		  //DOUBT: Do Nothing
		  return null;
	  } 
  }

  /**
   * Gets the RID of the last tuple returned.
   */
  public RID getLastRID() {
    return lastrid;
  }

} // public class FileScan extends Iterator
