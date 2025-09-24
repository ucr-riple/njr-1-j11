package uwm_cs557;

public abstract class BPlusTreeNode extends MiniDB_Block{
  
  /** The B+Tree this node is a part of */
  protected BPlusTree bpt;

  /** indicates whether or not this is a leaf node */
  private final boolean isLeafNode;
  public boolean isLeafNode(){ return isLeafNode; }

  /**
   * constructor used both for new blocks and re-loaded blocks. Initialization of 
   * new blocks is done by derived class.
   */
  public BPlusTreeNode( MiniDB_Block blk, BPlusTree bpt, boolean isLeafNode ){
    super( blk );
    this.bpt = bpt;
    this.isLeafNode = isLeafNode;
  }

  // return true if there is room for another (value,block) index record, and false otherwise.
  public final boolean hasRoomForAnotherRecord(){
    return (getMaxNumRecordsOnBlock() > getCurrNumRecordsOnBlock()); 
  }

  public int offset_currNumRecordsOnBlock(){     return MiniDB_Block.NUM_RESERVED_BYTES; }
  public void setCurrNumRecordsOnBlock(int numVals){ writeInt( offset_currNumRecordsOnBlock(), numVals ); }
  public int getCurrNumRecordsOnBlock(){ return readInt( offset_currNumRecordsOnBlock() ); }

  /** 
   * return the number of bytes from the beginning of the block to the beginning of the data region.
   */
  abstract public int offset_data();

  abstract public int offset_valueSlot(int slotID);

  public int offset_record(int slotID){
    return offset_valueSlot(slotID) - MiniDB_Constants.BLK_ID_SIZE;
  }

  /** return the maximum number of (value,block) index records that can be on the block */
  abstract public int getMaxNumRecordsOnBlock();

  /** @return the indexed field value in the slotID. Slots are numbered starting from zero */
  abstract public long getValue(int slotID);
  abstract public void setValue( long value, int slotID );
  abstract public int  getBlockID( int slotID );
  abstract public void setBlockID( int blockID, int slotID );

  /** @return the new (right-node) */
  abstract public BPlusTreeNode split() throws MiniDB_Exception;

  public String summaryToString(){
    return "";
  }
  
  /**
   * @return the minimum of any indexed field value in the node.
   */
  public long getMinValue(){
    return getValue(0);
  }

  /**
   * @return the slot ID of the first slot whose index value is greater than or equal to the value 
   * provided. If no index record on this block has value greater than or equal to value then return the 
   * number of currently occupied slots.
   */
  public int getSlotForValue(long value){
	  for (int i = 0; i < getCurrNumRecordsOnBlock(); ++i) {
		  if (getValue(i) >= value) {
			  return i;
		  }
	  }
	  
	return getCurrNumRecordsOnBlock();
  }

  /** 
   * add an index record to the node 
   * @return true on success
   */
  //abstract public boolean addRecord(long value, int blockID);
  public boolean addRecord(long value, int blockID){
    //System.out.println( "addRecord() " + value + ", " + blockID );
    boolean success = false;

    if( !isLeafNode() ){
      //System.out.println( "Adding record to internal node! " );
      //System.out.println( "addRecord() " + value + ", " + blockID );
      //System.out.println( summaryToString() );
      //MiniDB_Constants.waitHere();
    }

    if( hasRoomForAnotherRecord() ){
      int slotID = getSlotForValue( value );
      int numRecsOnBlock = getCurrNumRecordsOnBlock();

      //System.out.println( "now\n" + toString() );
      //System.out.println( "Inserting into slot " + slotID );
      //System.out.println( "current value in slot is " + getValue(slotID) );
      //System.out.println( "numRecsOnBlock is " + numRecsOnBlock );
      if( (getValue(slotID) != value) && (slotID < numRecsOnBlock) && (numRecsOnBlock > 0) ) {
	//System.out.println( "Shifting" );
	//MiniDB_Util.waitHere("");
	// shift slots >=  slotID since we are inserting a new record 
	// (not changing the value of an existing record)

	//System.arraycopy( this.buffer, offset_slot(slotID), this.buffer, offset_slot(slotID+1), szToShift);
	int srcOffset;
	int destOffset;

	if( isLeafNode() ){
	  srcOffset = offset_record(slotID);
	  destOffset = offset_record(slotID+1);
	}
	else{
	  srcOffset = offset_valueSlot(slotID);
	  destOffset = offset_valueSlot(slotID+1);
	}

	// just shift the whole block, could be more efficient prbly
	int szToShift = MiniDB_Constants.BLOCK_SIZE - destOffset;
	//int szToShift = (numRecsOnBlock - slotID) * bpt.get_indexRecordSize();

	//System.out.println( "pre shift\n" + toString() );
	System.arraycopy( this.buffer, srcOffset, this.buffer, destOffset, szToShift);
	//System.out.println( "post shift\n" + toString() );
	//System.out.println( "Shifted " + szToShift + " bytes starting from " + srcOffset );
      }
      // else MiniDB_Util.waitHere("not shifting");

      // write the record into the slot
      setValue( value, slotID );
      if( isLeafNode ) setBlockID( blockID, slotID );
      else setBlockID( blockID, slotID+1 );
      //writeInt( offset_slot(slotID), blockID );
      //writeLong( offset_slot(slotID)+MiniDB_Constants.BLK_ID_SIZE, value );

      setCurrNumRecordsOnBlock(numRecsOnBlock+1);
    }

    if( !isLeafNode() ){
      //System.out.println( summaryToString() );
      //System.out.println( toString() );
      //System.out.println( "Added " );
    }

    //System.out.println( "after add\n" + toString() );
    //MiniDB_Util.waitHere("leaving addRecord()");

    return success;
  }

  // return list of records. 
  //
  // the blockID of this block is returned followed by the records. Index block pointers have (*'s)
  // and data block pointers have (^'s) postpended:
  //
  // Examples: 
  // internal node on block 2:  2[(30000^,-598), (29800^,-594), (29600^,-590), (29400^,-586), (29200^,-582)]
  // leaf node on block 68:     68(57*,416,59*,432,61*,448,63*,464,65*)
  public abstract String recordsToString();

  //public BPlusTreeNode( byte buffer[], int blockID, BufMgr bufMgr, boolean isLeafNode ){
  //  super( buffer, blockID, bufMgr );
  //  this.isLeafNode = isLeafNode;
  // }

}