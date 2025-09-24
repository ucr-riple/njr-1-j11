package uwm_cs557;

import java.util.Vector;

public class BPlusTree{

  public final MiniDB miniDB;

  private String indexedFileName, indexedFieldName;
  public String getIndexedFileName(){  return indexedFileName; }
  public String getIndexedFieldName(){ return indexedFieldName; }
  protected int getIndexedFieldSize(){ return headBlock.getIndexedFieldSize(); }

  private BPlusTreeHeadBlock headBlock;
  public BPlusTreeHeadBlock getHeadBlock(){ return headBlock; }

  private BPlusTreeNode rootNode;

  public BPlusTreeNode getRootNode() throws MiniDB_Exception
  { miniDB.bufMgr.pin_block(rootNode.getThisBlockID()); return rootNode; }

  // create an empty B+Tree
  public BPlusTree(MiniDB db){this.miniDB = db;};

  /** 
   * @return the number of bytes in an index record of (value,blockID) */
  public final int get_indexRecordSize(){
    return MiniDB_Constants.BLK_ID_SIZE + this.getIndexedFieldSize();
  }


  // initialize
  public void init_existing(String indexedFileName, 
			    String indexedFieldName,
			    BPlusTreeHeadBlock headBlock){
    this.indexedFileName = indexedFileName;
    this.indexedFieldName = indexedFieldName;
    this.headBlock = headBlock;
  }

  /**
   * create a new index.
   */
  public void init_new(String indexedFileName, 
		       String indexedFieldName, 
		       int indexedFieldSize, 
		       BPlusTreeHeadBlock headBlock ) throws MiniDB_Exception{
    this.indexedFileName = indexedFileName;
    this.indexedFieldName = indexedFieldName;
    this.headBlock = headBlock;

    int rootBlockID = miniDB.addBlockToDB();

    // root node and first leaf are the same at the start
    this.headBlock.setRootBlockID(rootBlockID);
    this.headBlock.setFirstLeafBlockID(rootBlockID);
    this.headBlock.setIndexedFieldSize(indexedFieldSize);

    MiniDB_Block blk = miniDB.bufMgr.pin_block( rootBlockID );
    blk.setBlockType( MiniDB_Block.BLOCK_TYPE_BPT_LEAF );
    if( blk != null ){
      boolean isNewNode = true;
      BPlusTreeLeafNode leafNode = new BPlusTreeLeafNode(blk, this, isNewNode);
      this.rootNode = leafNode;
      //this.rootNode.initialize();
      ((BPlusTreeLeafNode)this.rootNode).setNextLeafBlockID(-1);
      //System.out.println( rootNode.toString() );
      //MiniDB_Constants.waitHere( "Just set next block to -1" );
    }

    //this.headBlock.writeString( 0, "head block of " + indexedFileName + ", " + indexedFieldName );
  }

  /**
   * the pin_count of all nodes on the path will increase by one (including the root)
   */
  public BPlusTreeNode[] getPathForValue( long value ) throws MiniDB_Exception{
    Vector vPath = new Vector();
    BPlusTreeNode path[];
    BPlusTreeNode currNode = rootNode;
    
    miniDB.bufMgr.pin_block( rootNode.getThisBlockID() );

    /*
    System.out.println( "********************" );
    System.out.println( currNode.toString() );
    System.out.println( currNode.summaryToString() );
    System.out.println( "Getting path, root is " + currNode.getThisBlockID() + " is Leaf: " + currNode.isLeafNode() );
    MiniDB_Constants.waitHere( "" );
    */

    while( !currNode.isLeafNode() ){
      ///      System.out.println( "in getPath NOT LEAF" );      MiniDB_Constants.waitHere( "" );

      vPath.add( currNode );
      currNode = ((BPlusTreeInternalNode)currNode).getChildNodeForValue( value );
    }
    
    vPath.add( currNode );

    path = new BPlusTreeNode[vPath.size()];
    vPath.copyInto(path);

    return path;
  }

  public void getAll( Vector<Long> vValuesOut, Vector<Integer> vBlockIDsOut ) throws MiniDB_Exception{
    //
    // Implement.
    //
	  //TODO
    //assert(false);
	  int leafBlockIDs[] = getLeafBlockIDs();
	    
	  for (int i=0; i<leafBlockIDs.length; i++){
	      boolean isNewNode = false;
	      BPlusTreeLeafNode leaf = new BPlusTreeLeafNode( miniDB.bufMgr.pin_block(leafBlockIDs[i]), this, isNewNode );
	
	      for (int j = 0; j < leaf.getCurrNumRecordsOnBlock(); ++j) {
	    	  vBlockIDsOut.add(leaf.getBlockID(j));
	    	  vValuesOut.add(leaf.getValue(j));
	      }
      }
	  
  }

  public int lookup( long value ) throws MiniDB_Exception{
     int blkID = -1;
     //
     // Implement. Hint: use getPathForValue()
     //
	  //TODO
     Vector<Integer> vBlockIDsOut = new Vector<Integer>();
     Vector<Long> vValuesOut = new Vector<Long>();
     
     getAll(vValuesOut, vBlockIDsOut);
     
     for (int i = 0; i < vValuesOut.size(); ++i) {
    	 if (vValuesOut.get(i) == value) {
    		 return vBlockIDsOut.get(i);
    	 }
     }
     
    //assert(false);
    return blkID;
  }

  /**
   * Insert an index record.
   * value   : a data value in the indexed file
   * blockID : the db block for the record with indexing field equal to value
   * @return true if the record is inserted successfully.
   *
   * Insert a (value,blockID) index record in the proper leaf node. If the leaf node
   * is full, a new leaf node is created and its records are split across the two
   * nodes. The new leaf node is the "right-neighbor" of the original node (the "left-neighbor").
   * The left-neighbor holds the smaller half of the index records and the right-neighbor holds
   * the larger half. The (value, block) record is then inserted onto either left- or 
   * right-neighbor, as appropriate.
   *
   * If a node is split, a new (minValue, rnBlockID) index record is created and copied up to the parent, 
   * Here rnBlockID refers to the blockID of the right-neighbor and minValue is the 
   * minimum value of its records. Splitting and copying up continues until either a record 
   * gets inserted into a node without splitting, or the root is split. If the root is split, 
   * a new internal leaf node is created that becomes the parent of the old root and its newly
   * created right-neighbor.
   */
  public boolean insertRecord( long value, int blockID ) throws MiniDB_Exception {
    boolean success = false;                             // return value
    BPlusTreeNode path[] = getPathForValue(value);       // blocks (nodes) from root to leaf

    //System.out.println( "BPlusTree : insertRecord (" + blockID + "*," + value + ")" );

    // flag that tells us when to stop moving up levels
    boolean insertedOnNonFullPage = false;               

    // the next value and blockID to insert
    long nextValueToInsert = value;
    int nextBlockToInsert = blockID;
    int currLevel = path.length - 1; 

    boolean errorEncountered = false;

    do{
      BPlusTreeNode currNode = path[currLevel];

      if( currNode.hasRoomForAnotherRecord() ){
	currNode.addRecord(nextValueToInsert, nextBlockToInsert);
	insertedOnNonFullPage = true;
	success = true;
      }
      else{
	BPlusTreeNode rightNode = currNode.split();

	// add the record to either the original node or the new node
	if( nextValueToInsert >= rightNode.getMinValue() ){
	  rightNode.addRecord(nextValueToInsert, nextBlockToInsert);
	}
	else{
	  currNode.addRecord(nextValueToInsert, nextBlockToInsert);
	}

	//System.out.println( "Left Node " + currNode.recordsToString() );
	//System.out.println( "Right Node " + rightNode.recordsToString() );
	//MiniDB_Util.waitHere( "SPLIT" );

	if( currLevel == 0 ){
	  // the root was split, create a new root by adding a level
	  if( !currNode.isLeafNode() ) System.out.println( this.allTreeValuesToString() );

	  System.out.println( " adding level" );
	  int newBlockID = miniDB.addBlockToDB();
	  MiniDB_Block blk = miniDB.bufMgr.pin_block(newBlockID);
	  //System.out.println( "Root blk = " + newBlockID );
	  //BPlusTreeInternalNode newRoot = new BPlusTreeInternalNode(blk, this, currNode, rightNode);

	  BPlusTreeInternalNode newRoot = new BPlusTreeInternalNode(blk, this, true );
	  newRoot.setBlockID( currNode.getThisBlockID(), 0 );

	  if( currNode.isLeafNode() )  newRoot.setValue( rightNode.getMinValue(), 0 );
	  else newRoot.setValue( BPlusTreeInternalNode.pushedUpValue, 0 );

	  newRoot.setBlockID( rightNode.getThisBlockID(), 1 );
	  newRoot.setCurrNumRecordsOnBlock(1);
    
	  //miniDB.bufMgr.unpin_block(newBlockID);
	  success = true;

	  // make changes locally and in the DB to reflect the change in root
	  headBlock.setRootBlockID(newRoot.getThisBlockID());
	  this.rootNode = newRoot;

	  //System.out.println( newRoot.toString() );
	  //System.out.println( newRoot.summaryToString() );
	  //System.out.println( "Root values:   " + newRoot.recordsToString() );
	  //System.out.println( "Root children: " + newRoot.childBlockIDsToString() );
	  //System.out.println( "^^^^^^^^ new root ^^^^^^^" );
	  //if( !currNode.isLeafNode() )  System.out.println( "^^^^^^^^ ABOVE INTERNAL ^^^^^^^" );
	  //MiniDB_Util.waitHere();	  
	  //System.out.println( miniDB.allDBBlocksToString() );
	  //MiniDB_Util.waitHere();	
	  if( !currNode.isLeafNode() ){
	    //System.out.println( this.allTreeValuesToString() );
	    //MiniDB_Util.waitHere();	 
	  } 
	}
	else{  // push up

	  if( currNode.isLeafNode() ){
	    nextValueToInsert = rightNode.getMinValue();
	    nextBlockToInsert = rightNode.getThisBlockID();
	  }
	  else{
	    nextValueToInsert = BPlusTreeInternalNode.pushedUpValue;
	    nextBlockToInsert = rightNode.getThisBlockID();

	    //System.out.println( "Moving up: (" + nextBlockToInsert + "*," + nextValueToInsert + ")" );
	    //MiniDB_Util.waitHere();	  
	  }
	}

	miniDB.bufMgr.unpin_block( rightNode.getThisBlockID() );
      }

      currLevel--;
    }while(!success);

    // decrement the pin count for all the blocks on the original path
    for (int i=0; i<path.length; i++){
      miniDB.bufMgr.unpin_block( path[i].getThisBlockID() );
    }

    //System.out.println( "After split "  );
    // System.out.println( leafNode.summaryToString() );
    //System.out.println( rightNode.summaryToString() );
    // MiniDB_Constants.waitHere("SPLIT");

    return success;
  }
  
  public void printAllRecords() throws MiniDB_Exception{
    int leafBlockIDs[] = getLeafBlockIDs();
    
    for (int i=0; i<leafBlockIDs.length; i++){
      boolean isNewNode = false;
      BPlusTreeLeafNode leaf = new BPlusTreeLeafNode( miniDB.bufMgr.pin_block(leafBlockIDs[i]), this, isNewNode );

      System.out.println( leaf.summaryToString() );
    }
  }

  public int[] getLeafBlockIDs() throws MiniDB_Exception{
    Vector v = new Vector();
    int blockIDs[] = null;

    int nextBlockID = headBlock.getFirstLeafBlockID();

    while( nextBlockID != -1 ){
      boolean isNewNode = false;
      BPlusTreeLeafNode leaf = new BPlusTreeLeafNode( miniDB.bufMgr.pin_block(nextBlockID), this, isNewNode );
      v.add( leaf.getThisBlockID() );
      miniDB.bufMgr.unpin_block(nextBlockID);

      nextBlockID = leaf.getNextLeafBlockID();
    }

    blockIDs = new int[v.size()];
    for (int i=0; i<blockIDs.length; i++) blockIDs[i] = ((Integer)v.get(i)).intValue();

    return blockIDs;
  }

  public String allTreeValuesToString() throws MiniDB_Exception{
    String s = "";
    int level = 0;
    Vector vNodesInCurrLevel = new Vector();
    Vector vNodesInNextLevel;

    vNodesInCurrLevel.add(getRootNode());
    boolean atLeaf = false;
    BPlusTreeNode currNode = null;

    while(!atLeaf){
      vNodesInNextLevel = new Vector();
      s += "Level " + level + "\n";

      //System.out.println( "Level " + level + "\n" );

      for (int i=0; i<vNodesInCurrLevel.size(); i++){
	currNode = ((BPlusTreeNode)vNodesInCurrLevel.get(i));
	
	s += currNode.recordsToString() + "\n";
	
	if( currNode.isLeafNode() ){
	  atLeaf = true;
	}
	else{
	  Vector children = ((BPlusTreeInternalNode)currNode).children();
	  for (int cIdx=0; cIdx<children.size(); cIdx++){
	    int childBlockID = ((Integer)children.get(cIdx)).intValue();
	    //System.out.println( "Child Block is " + childBlockID );

	    MiniDB_Block blk = miniDB.bufMgr.pin_block(childBlockID);
	    BPlusTreeNode childNode = null;

	    if( blk.getBlockType() == MiniDB_Block.BLOCK_TYPE_BPT_LEAF ){
	      boolean isNewNode = false;
	      //System.out.println( "Child Block is " + childBlockID );
	      childNode = new BPlusTreeLeafNode(blk,this,false);
	    }
	    else{
	      assert( blk.getBlockType() == MiniDB_Block.BLOCK_TYPE_BPT_INTERNAL );
	      childNode = new BPlusTreeInternalNode(blk,this);
	    }

	    vNodesInNextLevel.add( childNode );
	    //System.out.println( "size of next level list = " + vNodesInNextLevel.size() );
	  }
	}

	miniDB.bufMgr.unpin_block( currNode.getThisBlockID() );
      }

      vNodesInCurrLevel = vNodesInNextLevel;
      s += "\n";

      level++;
    }

    return s;
  }

  public String toString(){
    String s = "indexed filename=" + getIndexedFileName() + ", indexing field=" + getIndexedFieldName() +
      ", indexed field size=" + getIndexedFieldSize() + " bytes";
    return s;
  }

  /** Delete an index record */
  //public int deleteRecord(byte value[], int blockID ){ return -1; }

  /** (blockID, slotID) pairs */
  //public int search(byte value[]){ return -1; }

}