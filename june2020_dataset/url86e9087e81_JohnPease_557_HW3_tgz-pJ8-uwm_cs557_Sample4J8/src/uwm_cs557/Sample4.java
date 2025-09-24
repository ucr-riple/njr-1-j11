package uwm_cs557;

import java.util.*;

// test's everything
public class Sample4{
  public static void main( String args[] ){

    MiniDB db = new MiniDB( "foo" );
    BufMgr bm = db.getBufMgr();
    int size = 8;
    BPlusTree bpt = null;

    try{
      bpt = db.createNewBPlusTreeIndex( "table", "val", 8 );
      int iters = 300;
      java.util.Hashtable val2blk = new java.util.Hashtable();
      MiniDB_Record insertedRecords[] = new MiniDB_Record[iters];

      // add a bunch of values
      for (int i=0; i<iters; i++){
	//long value = -i*2;
	long value = ((int)Math.pow(-1, i))*i*2;
	int blockID = (i+1)*100;

	bpt.insertRecord(value, blockID);
	val2blk.put( value, blockID );

	insertedRecords[i] = new MiniDB_Record(blockID, value );

	//
	//System.out.println( "i is " + i + ", Inserted value " + value + ", block " + blockID );
      }

      System.out.println( "After insert" );
      System.out.println( bpt.allTreeValuesToString() );
      

      // get all values
      Arrays.sort( insertedRecords );
      Vector vValues = new Vector();
      Vector vBlkIDs = new Vector();
      bpt.getAll( vValues, vBlkIDs );

      for (int i=0; i<iters; i++){
	Long lVal = (Long)vValues.get(i);
	Integer iblockID = (Integer)vBlkIDs.get(i);
	
	MiniDB_Record recGot = new MiniDB_Record( iblockID, lVal );
	MiniDB_Record recExp = insertedRecords[i];

	System.out.println( "expected " + recExp + ", got " + recGot );
	if( !recGot.equals( recExp ) ){
	  MiniDB_Util.waitHere( "Error" );
	}
      }

      System.exit(0);
      
      // look them up
      for (int i=0; i<iters; i++){
	long value = -i;
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
						
      BPlusTreeNode p[] = bpt.getPathForValue( 19 );
      for (int i=0; i<p.length; i++){
	System.out.println( "hop " + (i+1) + " = " + p[i].getThisBlockID() );
      }
    }
    catch( MiniDB_Exception e ){
      System.out.println( "Exception " + e );
      e.printStackTrace();
      System.exit(0);
    }
    catch( Throwable t ){
      //MiniDB_Constants.waitHere("Exception, dumping DB" );
      try{
	System.out.println( db.allDBBlocksToString() );
	bpt.printAllRecords();
      }
      catch( MiniDB_Exception e ){
	System.out.println( "Another exception" );
      }
      t.printStackTrace();
      //MiniDB_Constants.waitHere("Exception" );
    }
    

  }
}