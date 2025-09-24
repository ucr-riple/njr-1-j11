package uwm_cs557;

import java.util.*;

//
// tests getSlotForValue();
//
public class Sample1{

  public static void main( String args[] ){

    MiniDB db = new MiniDB( "minidb" );
    BufMgr bm = db.getBufMgr();
    int size = 8;
    BPlusTree bpt = null;

    try{
      bpt = db.createNewBPlusTreeIndex( "table", "val", 8 );
      int iters = 5;

      // Add some values
      for (int i=0; i<iters; i++){
	long value = ((int)Math.pow(-1, i))*i*2;
	int blockID = (i+1)*100;
	bpt.insertRecord(value, blockID);

	MiniDB_Record rec = new MiniDB_Record(blockID,value);
	System.out.println( "Inserted " + rec );
      }

      BPlusTreeLeafNode leaf = (BPlusTreeLeafNode)bpt.getRootNode();
      System.out.println( "records on leaf: " + leaf.recordsToString() );

      // get slots including those for values not in index
      for (int i=-iters*2; i<=iters*2; i++){
	int slot = leaf.getSlotForValue( i );

	System.out.println( "slot for " + i + " is " +slot );
      }

    }
    catch( Exception e ){
      System.out.println( "Exception " + e );
      e.printStackTrace();
      System.exit(0);
    }

    

  }

}