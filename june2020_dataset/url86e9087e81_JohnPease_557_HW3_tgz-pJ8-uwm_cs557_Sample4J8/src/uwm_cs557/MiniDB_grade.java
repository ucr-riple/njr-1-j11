package uwm_cs557;

import java.util.*;

public class MiniDB_grade{

  public static String testNames[] = { "",
				       "getMaximumNumberOfRecordsOnBlock()",
				       "hasRoomForAnotherRecord()",
				       "get / set next leaf block ID",
				       "getSlotForValue()",
				       "getAll()",
				       "lookup()",
				       "split()" };

  public static double maxByTest[] = {0,
				      5,
				      5,
				      5,
				      10, 
				      10,
				      10,
				      13.5  };

  public static double maxTotal;
  
  public static int NUM_TESTS = 7;
				       
  static{
    for (int i=1; i<=NUM_TESTS; i++){
      maxTotal += maxByTest[i];
    }
    //maxTotal -= maxByTest[7]; // extra credit
  }

  MiniDB_grade(){
  }

  MiniDB makeNewDB(String name){
    return new MiniDB(name);
  }

  // getMaximumNumberOfRecordsOnBlock
  int test1( ) throws MiniDB_Exception{
    MiniDB db;
    BPlusTree bpt;
    int points = 0, maxPoints = 5;

    Random rng = new Random();

    for( int i = 0; i < 5 ; i++){
      db = makeNewDB("test1");
      int recSize = rng.nextInt(80)+1;
      String indexingFile = "file" + i;
      String indexedField = "field" + i;
      
      bpt = db.createNewBPlusTreeIndex( indexingFile, indexedField, recSize );
      BPlusTreeLeafNode root = (BPlusTreeLeafNode)bpt.getRootNode();
      int reportedSize = root.getMaxNumRecordsOnBlock();
      int correctSize = (int)Math.floor( (MiniDB_Constants.BLOCK_SIZE - root.offset_data()) / (4+recSize) );

      System.out.println( "making index, value " + recSize +  " bytes");
      System.out.println( "Expected " + reportedSize + ", got " + correctSize );

      if( reportedSize == correctSize ) points++;
    }  

    System.out.println( "test 1: " + points + "  of " + maxPoints );

    return points;
  }

  int test2( ) throws MiniDB_Exception{
    MiniDB db, db2;
    BPlusTree bpt;
    int points = 0, maxPoints = 5;

    Random rng = new Random();

    db = makeNewDB("test2.1" );
    //
    int recSize = 50;
    String indexingFile = "file2.";
    String indexedField = "field2.";
      
    BPlusTree bpt50 = db.createNewBPlusTreeIndex( indexingFile, indexedField, 50 );

    BPlusTreeLeafNode root50 = (BPlusTreeLeafNode)bpt50.getRootNode();
      
    if( root50.hasRoomForAnotherRecord() ){ System.out.println( "a) +1" ); points++;  }
    else{ System.out.println( "a) -1" ); }

    root50.setCurrNumRecordsOnBlock(1); 
    if( root50.hasRoomForAnotherRecord() ){ System.out.println( "b) +1" ); points++; }
    else{ System.out.println( "b) -1" );} 

    root50.setCurrNumRecordsOnBlock(2); 
    if( !root50.hasRoomForAnotherRecord() ){ System.out.println( "c) +1" ); points++; }
    else{ System.out.println( "c) -1" );} 

    db2 = makeNewDB("test2.2" );
    BPlusTree bpt70 = db2.createNewBPlusTreeIndex( indexingFile, indexedField, 70 );
    BPlusTreeLeafNode root70 = (BPlusTreeLeafNode)bpt70.getRootNode();
    if( root70.hasRoomForAnotherRecord() ){ System.out.println( "d) +1" ); points++; }
    else{ System.out.println( "d) -1" );} 

    root70.setCurrNumRecordsOnBlock(1);
    if( !root70.hasRoomForAnotherRecord() ){ System.out.println( "e) +1" ); points++; }
    else{ System.out.println( "e) -1" );} 	

    System.out.println( "test 2: " + points + "  of " + maxPoints );

    return points;
  }


  // get / set next leaf block ID
  int test3( ) throws MiniDB_Exception{
    MiniDB db;
    BPlusTree bpt;
    int points = 0, maxPoints = 5;

    Random rng = new Random();

    db = makeNewDB("test3." );
    int recSize = 8;
    String indexingFile = "file3.";
    String indexedField = "field3.";
    bpt = db.createNewBPlusTreeIndex( indexingFile, indexedField, 50 );
    BPlusTreeLeafNode root = (BPlusTreeLeafNode)bpt.getRootNode();

    for (int i=0; i<5; i++){
      int blkID = rng.nextInt();
      root.setNextLeafBlockID( blkID );
      int ret = root.getNextLeafBlockID();

      System.out.println( "expected " + blkID + " got " + blkID );
      if( ret == blkID ) points++;
      else{
	System.out.println( "-1");
      }
    }


    System.out.println( "test 3: " + points + "  of " + maxPoints );

    return points;
  }
  
  int test4( ) throws MiniDB_Exception{
    int points = 0;
    // IN_SOLUTION_START

    MiniDB db = new MiniDB( "minidb.test4" );
    BufMgr bm = db.getBufMgr();
    BPlusTree bpt = db.createNewBPlusTreeIndex( "table", "val", 8 );
    BPlusTreeLeafNode leaf = (BPlusTreeLeafNode)bpt.getRootNode();
    int slot;
      
    slot = leaf.getSlotForValue( 15 );  
    System.out.println( "getSlotForValue( 15 )" );
    System.out.println( "Expect 0, got " + slot );
    if( slot == 0 ){	points++;   }else{ System.out.println( " incorrect" ); }
    
    System.out.println( "insertRecord(500,15)" );
    bpt.insertRecord(500, 15);

    slot = leaf.getSlotForValue(300);  // should be 0
    System.out.println( "getSlotForValue(300)" );
    System.out.println( "Expect 0, got " + slot );
    if( slot == 0 ){	points++;   }else{ System.out.println( " incorrect" ); }

    slot = leaf.getSlotForValue(500);  // should be 0
    System.out.println( "getSlotForValue(500)" );
    System.out.println( "Expect 0, got " + slot );
    if( slot == 0 ){	points++;   }else{ System.out.println( " incorrect" ); }

    slot = leaf.getSlotForValue(700);  // should be 0
    System.out.println( "getSlotForValue(700)" );
    System.out.println( "Expect 1, got " + slot );
    if( slot == 1 ){	points++;   }else{ System.out.println( " incorrect" ); }

    if( points == 3 ){
      long checkLong = leaf.readLong( leaf.offset_valueSlot(0) );
      if( checkLong != 500 ){
	System.err.println( leaf.toString() );
	System.err.println( "check failed in MiniDB_grade, 500 is 0x1F4 in hex" );
	System.exit(0);
      }
    }
    
    // Add some values (all less than 500 );
    long valuesInsert[] = {2, -10, 20, -5, 7};

    long valuesGet[] = {-30, -10, 0, 2, 5, 100000};
    int expectedSlots[] = {0, 0, 2, 2, 3, 6};

    for (int i=0; i<valuesInsert.length; i++){
      long value = valuesInsert[i];

      int blockID = (i+1)*100;
      bpt.insertRecord(value, blockID);
      System.out.println( "insertRecord(" + value + "," + blockID + ")" );
      
      MiniDB_Record rec = new MiniDB_Record(blockID,value);
      System.out.println( "Inserted " + rec );
    }
    
    for (int i=0; i<valuesGet.length; i++){
      System.out.println( "getSlotForValue(" + valuesGet[i] + ")" );
      slot = leaf.getSlotForValue(valuesGet[i]);  
      System.out.println( "Expect " + expectedSlots[i] + ", got " + slot );
      if( expectedSlots[i] == slot ) points++; else{ System.out.println( " incorrect" ); }
    }

    // IN_SOLUTION_END
    return points;
  }

  //
  // getAll()
  int test5() throws MiniDB_Exception{
    int points = 0;
    // IN_SOLUTION_START
    MiniDB db = new MiniDB( "minidb.test5" );
    BufMgr bm = db.getBufMgr();
    int size = 8;
    BPlusTree bpt = db.createNewBPlusTreeIndex( "table", "val", 8 );
    int iters = 50;
    MiniDB_Record insertedRecords[] = new MiniDB_Record[iters];
    Random rng = new Random( 339 );
    int cntCorrect = 0;

    // Add some records. It will require splits of leaf nodes but not internal nodes
    System.out.println( "insert " + iters + " records to create multiple leaf nodes, but no internal nodes" );
    for (int i=0; i<iters; i++){
      long value = (long)rng.nextInt( 10000 );
      int blockID = (i+1)*100;

      bpt.insertRecord(value, blockID);
      
      MiniDB_Record rec = new MiniDB_Record(blockID,value);
      insertedRecords[i] = rec;

      System.out.println( "Inserted " + rec );
    }

    //System.out.println( "\nB+Tree after all values inserted: " );
    //System.out.println( bpt.allTreeValuesToString() );
      
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
	System.out.println(" error" );
      }
      else{
	cntCorrect++;
      }
    }

    points = cntCorrect / 5; // the only way to get all ten points is to be perfect
    //points = (int)Math.ceil( (cntCorrect - 4) / 5.0 ); // the only way to get all ten points is to be perfect
    if( points == 0 && cntCorrect > 0 ) points = 1; // mercy point if any are correct
  
    System.out.println( "" + cntCorrect + " of " + iters + " correct ---> " + points + " points of " + maxByTest[5] );

    if( points == maxByTest[5] ){
      // Check submissions that got all the points that there wasn't any funny business going on.

      int leafBlocks[] = bpt.getLeafBlockIDs();
      int cnt = 0;
      
      for (int i=0; i<leafBlocks.length; i++){
	MiniDB_Block blk = db.bufMgr.pin_block( leafBlocks[i] );
	BPlusTreeLeafNode leafNode =  new BPlusTreeLeafNode( blk, bpt, false );
	
	for (int slotID=0; slotID<leafNode.getCurrNumRecordsOnBlock(); slotID++){
	  long val = leafNode.getValue(slotID);
	  int blkID = leafNode.getBlockID(slotID);
	  MiniDB_Record recGot = new MiniDB_Record( blkID, val );
	  MiniDB_Record recExp = insertedRecords[cnt++];

	  if( recGot.equals( recExp ) ){
	  }
	  else{
	    System.out.println( leafNode.toString() );
	    System.out.println( "leaf node " + i + " of " + leafBlocks.length );
	    System.out.println( "deception test, expected " + recExp + ", got " + recGot );
      	    System.out.println( "decpeption test failed " );
	    System.exit(0);
	  }
	}

      }

      System.out.println( "DTst ok" );
    }


    // IN_SOLUTION_END
    return points;
  }

  //
  // lookup()
  //
  int test6( ) throws MiniDB_Exception{
    int points = 0;
    // IN_SOLUTION_START
    int pointsForNonExisting = 0;

    MiniDB db = new MiniDB( "minidb.test6" );
    BufMgr bm = db.getBufMgr();
    int size = 8;
    BPlusTree bpt =  db.createNewBPlusTreeIndex( "table", "val", 8 );
    java.util.Hashtable val2blk = new java.util.Hashtable();
    Random rng = new Random(3829);
    int blkID;
    long value;
    int numCorrectlyRetrieved = 0;
    int recsToAdd = 40;
    MiniDB_Record insertedRecords[] = new MiniDB_Record[recsToAdd];

    // Add some records. It will require splits of leaf nodes but not internal nodes
    for (int i=0; i<recsToAdd; i++){
      value = (long)rng.nextInt( 10000 );
      blkID = (i+1)*100;
      bpt.insertRecord(value, blkID);

      MiniDB_Record rec = new MiniDB_Record(blkID,value);
      insertedRecords[i] = rec;	
      Object o = val2blk.put( value, blkID );
      if( o != null ){ MiniDB_Util.waitHere( "already? " + value ); }

      System.out.println( "Inserted " + rec );
    }

    System.out.println( "lookup a few values that don't exist in index" );

    blkID = bpt.lookup(-5000); 
    System.out.println( "lookup(-5000)" );
    System.out.println( "Expect -1, got " + blkID );
    if( blkID == -1 ){	pointsForNonExisting++;   }else{ System.out.println( " incorrect" ); }

    long l = 22222222l;
    blkID = bpt.lookup(l); 
    System.out.println( "lookup(" + l + ")" );
    System.out.println( "Expect -1, got " + blkID );
    if( blkID == -1 ){	pointsForNonExisting++;   }else{ System.out.println( " incorrect" ); }

    System.out.println( "\n lookup all the previously inserted values\n" );

    //
    // But first get map of all values correctly in index
    //
    int leafBlocks[] = bpt.getLeafBlockIDs();
    Vector<MiniDB_Record> v = new Vector();
    Hashtable recMap = new Hashtable();

    for (int i=0; i<leafBlocks.length; i++){
      MiniDB_Block blk = db.bufMgr.pin_block( leafBlocks[i] );
      BPlusTreeLeafNode leafNode =  new BPlusTreeLeafNode( blk, bpt, false );
      
      for (int slotID=0; slotID<leafNode.getCurrNumRecordsOnBlock(); slotID++){
	long val = leafNode.getValue(slotID);
	blkID = leafNode.getBlockID(slotID);
	MiniDB_Record recGot = new MiniDB_Record( blkID, val );
	v.add( recGot );
	recMap.put( recGot.value, recGot );
      }
    }

    //
    // get all values
    //
    for (int i=0; i<insertedRecords.length; i++){
      MiniDB_Record insRec = insertedRecords[i];
      value = insRec.value;
      blkID = bpt.lookup( value );
	
      System.out.print( "lookup(" + value + ")..." );
      System.out.println( "Expect " + insRec.blockID + ", got " + blkID );
      if( blkID == insRec.blockID ){
	// correct... make sure the record really is in the index
	numCorrectlyRetrieved++;
	
	MiniDB_Record r = (MiniDB_Record)recMap.get( value );
	if( r == null ){
	  System.out.println( "lookup() deception test, null found for correctly retrieved value " + value );
	  System.err.println( "decpeption test failed " );
	  System.exit(0);
	}
      }
      else{ 
	System.out.println( " incorrect" ); 
      }

    }

    int binSize = (recsToAdd/8);
    int pointsForLookingUpExisting = numCorrectlyRetrieved / binSize ; 

    if( numCorrectlyRetrieved > 0 && pointsForLookingUpExisting == 0 ) pointsForLookingUpExisting = 1; // mercy point

    points = pointsForNonExisting + pointsForLookingUpExisting;
    System.out.println( "" + pointsForNonExisting + " of 2 points " + " for lookup of non-existing" );
    System.out.println( "" + numCorrectlyRetrieved + " of " + recsToAdd + " correct lookups of existing records " +
			" ---> " + pointsForLookingUpExisting + " of 8 points" );
    System.out.println( "  tot for lookup() test is " + points + " of "  + maxByTest[6] );
    
    // IN_SOLUTION_END
    return points;
  }

  //
  // BPlusTreeInternalNode.split()
  //
  double test7( ) throws MiniDB_Exception{
    double points = 0;
    // IN_SOLUTION_START

    MiniDB db = new MiniDB( "minidb.test7" );
    BufMgr bm = db.getBufMgr();
    int size = 8;
    BPlusTree bpt = db.createNewBPlusTreeIndex( "table", "val", 8 );

    int iters = 300;
    java.util.Hashtable val2blk = new java.util.Hashtable();
    MiniDB_Record insertedRecords[] = new MiniDB_Record[iters];

    System.out.println( "At start, " + db.numBlocksInDB() + " blocks " );

    // add a bunch of values
    for (int i=0; i<iters; i++){
      //long value = -i*2;
      long value = ((int)Math.pow(-1, i))*i*2;
      int blockID = (i+1)*100;
      MiniDB_Record rec = new MiniDB_Record(blockID, value);

      bpt.insertRecord(value, blockID);
      val2blk.put( value, blockID );
      
      insertedRecords[i] = new MiniDB_Record(blockID, value );

      System.out.println( "insert " + rec );
      System.err.println( "insert " + i + ") of " + iters + ", " + rec );
      // System.out.println( "After " + (i+1) + ", " + db.numBlocksInDB() + " blocks " );

      //
      //System.out.println( "i is " + i + ", Inserted value " + value + ", block " + blockID );
    }

    System.out.println( "+ 0.5 points for not crashing so far" );
    System.err.println( "+ 0.5 points for not crashing so far" );
    points = 0.5;

    int expectedVal = 84;
    int testVal = db.numBlocksInDB();
    System.out.println( "Number of blocks in DB is " + testVal + ", expected " + expectedVal );
    if( expectedVal == testVal ){  points++; System.out.println( "  +1" );    }else{ System.out.println( " incorrect" );   }

    BPlusTreeNode nodes[] = bpt.getPathForValue( 0 );
    expectedVal = nodes.length;
    testVal = 4;
    System.out.println( "Height of tree is " + testVal + ", expected " + expectedVal );
    if( expectedVal == testVal ){  points++; System.out.println( "  +1" );  }else{ System.out.println( " incorrect" );   }

    MiniDB_Block blk =  bpt.getRootNode();
    expectedVal = MiniDB_Block.BLOCK_TYPE_BPT_INTERNAL;
    testVal = blk.getBlockType();
    System.out.println( "Block type is " + testVal + ", expected " + expectedVal );
    if( expectedVal == testVal ){ System.out.println( "  +1" );  points++;   }else{ System.out.println( " incorrect" );   }

    // get all values
    Arrays.sort( insertedRecords );
    Vector vValues = new Vector();
    Vector vBlkIDs = new Vector();
    bpt.getAll( vValues, vBlkIDs );
    int numCorrect = 0;
    for (int i=0; i<iters; i++){
      Long lVal = (Long)vValues.get(i);
      Integer iblockID = (Integer)vBlkIDs.get(i);
      
      MiniDB_Record recGot = new MiniDB_Record( iblockID, lVal );
      MiniDB_Record recExp = insertedRecords[i];
      
      System.out.println( "expected " + recExp + ", got " + recGot );
      if( recGot.equals( recExp ) ){
	numCorrect++;
      }
      else{
      }
    }

    double pointsPart2 = 10.0* (numCorrect / (double)iters);

    System.out.println( "     " + points + " for structure checks " );
    System.out.println( "   + " + pointsPart2 + " for getting " + numCorrect + " of " + iters + " lookups correct." );

    points += pointsPart2;

    System.out.println( " totl for split() test --> " + points );

    // IN_SOLUTION_END
    return points;
  }

  public double runTest(int testID){
    double pointsEarned = 0;

    System.out.println( "Test " + testID + " : " + testNames[testID] );

    try{
      switch( testID ){
      case 1: pointsEarned = test1(); break;
      case 2: pointsEarned = test2(); break;
      case 3: pointsEarned = test3(); break;
      case 4: pointsEarned = test4(); break;
      case 5: pointsEarned = test5(); break;
      case 6: pointsEarned = test6(); break;
      case 7: pointsEarned = test7(); break;
      default: 
	System.out.println( "Bad test ID " + testID );
	assert(false);
      }
     }
    catch( Exception e ){
      String s = e.toString();
      System.out.println( "Exception:\n " + s );
      e.printStackTrace(System.out);
      e.printStackTrace();
    }
      
    return pointsEarned;
  }

  public static void main( String args[] ){
    MiniDB_Util.canWait = false;
    MiniDB_grade grader = new MiniDB_grade();

    double totalPoints = 0;
    double pointsByTest[] = new double[NUM_TESTS+1];

    System.err.println( "Test starting up" );

    if( args[0].equals( "-all" ) ){
      
      boolean skipTest[] = new boolean[NUM_TESTS+1];
      for (int i=1; i<args.length; i++){
	int t = Integer.parseInt( args[i] );
	skipTest[t] = true;
      }
      
      for (int i=1; i<=NUM_TESTS; i++){
	if( skipTest[i] ){
	  System.out.println( "Skipping Test " + i );
	}
	else{
	  pointsByTest[i] = grader.runTest(i);
	  System.out.println( "\n" );
	  totalPoints += pointsByTest[i];
	}
      }

      System.out.println( "\nSUMMARY" );
      for (int i=1; i<=NUM_TESTS; i++){
	//if( i == NUM_TESTS ) System.out.println( "Extra Credit" );
	System.out.println( " Test" + i + "... " + pointsByTest[i] + " of " + maxByTest[i] );
      }
      System.out.println( " ----------------" );
      System.out.println( " TOTAL --> " + totalPoints + " of " + maxTotal );

    }
    else{
      int test = Integer.parseInt(args[0]);

      double points = 0;

      points = grader.runTest(test);

      System.out.println( "" + points + " points for test " + test );
    }


  }
}