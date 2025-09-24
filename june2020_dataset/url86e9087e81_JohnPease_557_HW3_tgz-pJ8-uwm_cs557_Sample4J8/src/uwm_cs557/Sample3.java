package uwm_cs557;

import java.util.*;

//
// tests BPlusTree.lookup()
//
public class Sample3{

  public static void main( String args[] ){

    MiniDB db = new MiniDB( "minidb" );
    BufMgr bm = db.getBufMgr();
    int size = 8;
    BPlusTree bpt = null;

    try{
      bpt = db.createNewBPlusTreeIndex( "table", "val", 8 );
      int iters = 50;
      MiniDB_Record insertedRecords[] = new MiniDB_Record[iters];
      java.util.Hashtable val2blk = new java.util.Hashtable();

      // Add some records. It will require splits of leaf nodes but not internal nodes
      for (int i=0; i<iters; i++){
	long value = ((int)Math.pow(-1, i))*i*2;
	int blockID = (i+1)*100;
	bpt.insertRecord(value, blockID);

	MiniDB_Record rec = new MiniDB_Record(blockID,value);
	insertedRecords[i] = rec;	
	val2blk.put( value, blockID );

	System.out.println( "Inserted " + rec );
      }

      System.out.println( "\nB+Tree after all values inserted: " );
      System.out.println( bpt.allTreeValuesToString() );
      

      // get all values
      for (int i=-iters*2; i<=2*iters; i++){
	long value = i;
	int blkID = bpt.lookup( value );
	
	System.out.print( "lookup(" + value + ")..." );
	if( blkID == -1 ) System.out.print( " NOT in Index " );
	else System.out.print( "on block " + blkID );

	Integer iBlk = (Integer)val2blk.get(value);
	if( iBlk == null ){
	  if( blkID != -1 ){
	    System.out.println( "Expected -1, got " + ", got " + blkID );
	    MiniDB_Util.waitHere();
	  }
	  else{
	    System.out.println( " correct " );
	  }
	}
	else{
	  if( iBlk.intValue() != blkID ){
	    System.out.println( "Expected " + iBlk  + ", got " + blkID );
	    MiniDB_Util.waitHere();
	  }
	  else{
	    System.out.println( " correct " );
	  }
	}
      }

    }
    catch( Exception e ){
      System.out.println( "Exception " + e );
      e.printStackTrace();
      System.exit(0);
    }

    

  }

}