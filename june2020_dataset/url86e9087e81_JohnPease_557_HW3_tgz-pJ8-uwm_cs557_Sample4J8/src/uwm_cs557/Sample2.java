package uwm_cs557;

import java.util.*;

//
// tests BPlusTree.getAll()
//
public class Sample2{

  public static void main( String args[] ){

    MiniDB db = new MiniDB( "minidb" );
    BufMgr bm = db.getBufMgr();
    int size = 8;
    BPlusTree bpt = null;

    try{
      bpt = db.createNewBPlusTreeIndex( "table", "val", 8 );
      int iters = 50;
      MiniDB_Record insertedRecords[] = new MiniDB_Record[iters];

      // Add some records. It will require splits of leaf nodes but not internal nodes
      for (int i=0; i<iters; i++){
	long value = ((int)Math.pow(-1, i))*i*2;
	int blockID = (i+1)*100;
	bpt.insertRecord(value, blockID);

	MiniDB_Record rec = new MiniDB_Record(blockID,value);
	insertedRecords[i] = rec;

	System.out.println( "Inserted " + rec );
      }

      System.out.println( "\nB+Tree after all values inserted: " );
      System.out.println( bpt.allTreeValuesToString() );
      
      System.out.println( "\n BPlusTree.getAll() \n" );

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

    }
    catch( Exception e ){
      System.out.println( "Exception " + e );
      e.printStackTrace();
      System.exit(0);
    }

    

  }

}