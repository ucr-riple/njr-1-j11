package uwm_cs557;

public class BPlusTreeLeafNode extends BPlusTreeNode{

  public BPlusTreeLeafNode(MiniDB_Block blk, BPlusTree bpt, boolean newNode ){
    super( blk, bpt, true );

    if( newNode ){
      setBlockType( MiniDB_Block.BLOCK_TYPE_BPT_LEAF );
      setNextLeafBlockID(-1);
      setCurrNumRecordsOnBlock(0);
    }

  }

  public int offset_firstBlockPointer(){ return MiniDB_Block.NUM_RESERVED_BYTES + 8; }
  public int offset_data(){ return MiniDB_Block.NUM_RESERVED_BYTES + 8; }
  public int offset_blockIDSlot(int slotID){ return offset_data() + slotID*bpt.get_indexRecordSize(); }
  public int offset_valueSlot(int slotID){ return offset_data() + slotID*bpt.get_indexRecordSize() + MiniDB_Constants.BLK_ID_SIZE; }


  public int offset_nextLeafBlockID(){ 
	  return offset_data() - MiniDB_Constants.BLK_ID_SIZE;
   }

  public void setNextLeafBlockID(int nextBlockID){ 
	  int loc = offset_nextLeafBlockID();
	  writeInt(loc, nextBlockID);
  }

  public int getNextLeafBlockID(){ 
	  
     return readInt(offset_nextLeafBlockID());
  }

  public int getMaxNumRecordsOnBlock(){
  	
	  int sizeFixed = offset_data();
	  int sizeForRecords = MiniDB_Constants.BLOCK_SIZE - sizeFixed;
	  
	  int maxNumRecords = (int)Math.floor(sizeForRecords / bpt.get_indexRecordSize());
  
	  return maxNumRecords;
  }

  public long getValue( int slotID ){
    //int offset = offset_slot(slotID)+MiniDB_Constants.BLK_ID_SIZE;
    int offset = offset_valueSlot(slotID);
    return readLong( offset );
  }

  public void setValue( long value, int slotID ){
    writeLong( offset_valueSlot(slotID), value );
  }

  public int getBlockID( int slotID ){
    int offset = offset_blockIDSlot(slotID);
    return readInt(offset);
  }

  public void setBlockID( int dataBlockID, int slotID ){
    writeInt( offset_blockIDSlot(slotID), dataBlockID );
  }

  /**
   * Split this node into two leaf nodes.
   *
   * 1) add block to the for the right child
   * 2) pin the new block
   * 3) initialize it by setting the next block ID correctly
   * 4) copy all slots >= the pivot to the new node
   */
  public BPlusTreeLeafNode split() throws MiniDB_Exception{
    BPlusTreeLeafNode newNode = null;
    System.out.println( " splitting leaf " + bpt.miniDB.numBlocksInDB() );

    int pivotSlot = (int)Math.floor( getCurrNumRecordsOnBlock() / 2 );
    int newBlockID = bpt.miniDB.addBlockToDB();

    //System.out.println( "Before Split: " + recordsToString() );

    if( newBlockID != -1 ){
      MiniDB_Block blk = bpt.miniDB.bufMgr.pin_block(newBlockID);
      boolean isNewNode = true;
      newNode = new BPlusTreeLeafNode(blk, this.bpt, isNewNode);
      newNode.setNextLeafBlockID(this.getNextLeafBlockID());
      this.setNextLeafBlockID(newBlockID);

      int numSlotsToMove = (getCurrNumRecordsOnBlock() - pivotSlot);
      int copySize = numSlotsToMove * bpt.get_indexRecordSize();

      //System.arraycopy(this.buffer, offset_valueSlot(pivotSlot), newNode.buffer, offset_data(), copySize );
      System.arraycopy(this.buffer, offset_blockIDSlot(pivotSlot), newNode.buffer, offset_data(), copySize );

      this.setCurrNumRecordsOnBlock(getCurrNumRecordsOnBlock()-numSlotsToMove);
      newNode.setCurrNumRecordsOnBlock(numSlotsToMove);
    }

    //System.out.println( "After leaf split\n" );
    //System.out.println( "Left:\n" + this.toString() );
    //System.out.println( "Right:\n" + newNode.toString() );
    //MiniDB_Util.waitHere( "After Split" );

    System.out.println( " leaf split " + bpt.miniDB.numBlocksInDB() );
    return newNode;
  }

  //public void init_new(){
  //  setNextLeafBlockID(-1);
  //  setCurrNumRecordsOnBlock(0);
  // }

  public String summaryToString(){
    String s = "BTreeLeafNode, Block# " + blockID + "\n";
    
    s += "next leaf blockID...... = " + getNextLeafBlockID() + "\n";
    s += "# records.............. = " + getCurrNumRecordsOnBlock() + "\n";
    s += "room for one more record? " + hasRoomForAnotherRecord() + "\n";

    for (int slotID=0; slotID<getCurrNumRecordsOnBlock(); slotID++){
      s += "slot " + slotID + ")... value=" + getValue(slotID) + ", block=" + getBlockID(slotID) + "\n";
    }

    return s;
  }

  public String recordsToString(){
    String s = "" + getThisBlockID() + "[";
    for (int i=0; i<getCurrNumRecordsOnBlock(); i++){
      long value = getValue(i);
      int blk = getBlockID(i);
      if( i!=0 ) s += ", ";

      s += "(" + blk + "^," + value + ")" ;
    }
    
    s += "]";

    return s;
  }


  /** return the current number of children for this node */
  //public int getNumChildren(){ return 0; }

  /**
   * Return the block ID for index record 
   * return
   */
  //public int getBlockIDForValue(int value);

  //public int getMinValue();

  //public int getMaxValue();

  //public boolean isLeafNode(){ return false; }

  /** add a rid to this leaf page */
  //public int add(RecordID rid);

}