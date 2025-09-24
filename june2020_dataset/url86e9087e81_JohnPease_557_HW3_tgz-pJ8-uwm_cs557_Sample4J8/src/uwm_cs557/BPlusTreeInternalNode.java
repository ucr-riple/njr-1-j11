package uwm_cs557;

import java.util.*;

public class BPlusTreeInternalNode extends BPlusTreeNode{

  public static long pushedUpValue;

  
  public String recordsToString(){
    String s = "" + getThisBlockID() + "(";
    for (int i=0; i<getCurrNumRecordsOnBlock(); i++){
      long value = getValue(i);
      int blk = getBlockID(i);
      s += blk + "*," + value +",";
    }

    // last block pointer in a record on its own
    s +=  getBlockID(getCurrNumRecordsOnBlock())+"*)";

    return s;
  }


  /**
   * construct a new root node
   */
  public BPlusTreeInternalNode(MiniDB_Block blk, BPlusTree bpt, BPlusTreeNode leftChild, BPlusTreeNode rightChild ){
    super( blk, bpt, false );

    //System.out.println( "leftchild : " + leftChild.getThisBlockID() );
    //System.out.println( "rightchild : " + rightChild.getThisBlockID() );

    this.setBlockType( MiniDB_Block.BLOCK_TYPE_BPT_INTERNAL );
    this.setBlockID( leftChild.getThisBlockID(), 0 );
    this.setValue( rightChild.getMinValue(), 0 );
    this.setBlockID( rightChild.getThisBlockID(), 1 );
    this.setCurrNumRecordsOnBlock(1);
  }

  public BPlusTreeInternalNode(MiniDB_Block blk, BPlusTree bpt ){
    this( blk, bpt, false );
  }

  public BPlusTreeInternalNode(MiniDB_Block blk, BPlusTree bpt, boolean isNew ){
    super( blk, bpt, false );

    if(isNew){
      this.setBlockType( MiniDB_Block.BLOCK_TYPE_BPT_INTERNAL );
      this.setCurrNumRecordsOnBlock(1);
    }
  }

  /*
  public String childBlockIDsToString(){
    String s = "" + getThisBlockID() + "[";
    for (int i=0; i<=getCurrNumRecordsOnBlock(); i++){
      int blkID = getChildBlockID(i);
      if( i==0 )  s += blkID;
      else  s += ","+blkID;
      s += "*";
    }
    
    s += "]";

    return s;
  }
  */

  public Vector children(){
    Vector v = new Vector();

    for (int i=0; i<getCurrNumRecordsOnBlock()+1; i++){
      v.add( readInt(offset_forChildBlock(i)) );
    }

    return v;
  }



  /**
   * Split this node into two internal nodes.
   *
   * 1) add block to the for the right child
   * 2) pin the new block
   * 3) initialize it by setting the next block ID correctly
   * 4) copy all slots >= the pivot to the new node
   
   * slots on an internal page are numbered from 0 to p
   * 
   * a block with p value slots has p+1 block ptr slots   
   */
  public BPlusTreeInternalNode split() throws MiniDB_Exception{

    BPlusTreeInternalNode newNode = null;

    int newBlockID = bpt.miniDB.addBlockToDB();
    int pivotSlot = (int)Math.floor( getCurrNumRecordsOnBlock() / 2 );
    
    if( newBlockID != -1 ){
        MiniDB_Block blk = bpt.miniDB.bufMgr.pin_block(newBlockID);
        boolean isNewNode = true;
        newNode = new BPlusTreeInternalNode(blk, this.bpt, isNewNode);

        int numSlotsToMove = (getCurrNumRecordsOnBlock() - pivotSlot);
        int copySize = numSlotsToMove * bpt.get_indexRecordSize() + MiniDB_Constants.BLK_ID_SIZE;
        
        System.arraycopy(this.buffer, offset_forChildBlock(pivotSlot), newNode.buffer, offset_data(), copySize );
        
        this.setCurrNumRecordsOnBlock(getCurrNumRecordsOnBlock()-numSlotsToMove);
        newNode.setCurrNumRecordsOnBlock(numSlotsToMove);
        
        this.pushedUpValue = getValue(0);
      }

      System.out.println( " leaf split " + bpt.miniDB.numBlocksInDB() );
      return newNode;
  }

  /** return the current number of children for this node */
  public int getNumChildren(){ return getCurrNumRecordsOnBlock() + 1; }
 
  public int offset_data(){ return MiniDB_Block.NUM_RESERVED_BYTES + 8; }

  public int offset_valueSlot(int slotID){ return offset_data() + slotID*(bpt.get_indexRecordSize())+MiniDB_Constants.BLK_ID_SIZE;  }

  private int offset_forChildBlock(int slotID){  return offset_data() + slotID*(bpt.get_indexRecordSize()); }

  public int getChildBlockID(int slotID){ return readInt( offset_forChildBlock(slotID) ); }

  public long getValue( int slotID ){
    int offset = offset_valueSlot(slotID);
    return readLong( offset );
  }

  public void setValue( long value, int slotID ){
    writeLong( offset_valueSlot(slotID), value );
  }

  public int getBlockID( int slotID ){
    int offset = offset_forChildBlock(slotID);
    return readInt( offset );
  }
  
  public void setBlockID( int childBlockID, int slotID ){
    writeInt( offset_forChildBlock(slotID), childBlockID );
  }
  
  /**
   * on an internal nodes, we interpret the number of records to be the number of values. That is,
   * the number of records on a block is one less than the number blockIDs or children on that 
   * block.
   */
  public int getMaxNumRecordsOnBlock(){
    int szFixed = offset_data()+MiniDB_Constants.BLK_ID_SIZE;  
    int szForRecords = MiniDB_Constants.BLOCK_SIZE - szFixed;
    int maxNumRecords = (int)Math.floor(szForRecords / bpt.get_indexRecordSize() );

    return maxNumRecords;
  }

  /**
   * return the correct child node for search value
   */
  public BPlusTreeNode getChildNodeForValue(long value) throws MiniDB_Exception{
    int slotID = 0;
    int numValuesOnBlock = getCurrNumRecordsOnBlock();
    BPlusTreeNode childNode = null;
    int childBlockID = -1;

    while( slotID < numValuesOnBlock && childBlockID == -1 ){
      if( getValue(slotID) > value ){
	childBlockID = getBlockID(slotID);
      }
      slotID++;
    }

    if( childBlockID == -1 ){
      slotID = numValuesOnBlock;
      childBlockID = getBlockID(slotID);
    }

    MiniDB_Block blk = bpt.miniDB.bufMgr.pin_block(childBlockID);

    if( blk.getBlockType() == MiniDB_Block.BLOCK_TYPE_BPT_LEAF ){
      boolean isNewNode = false;
      childNode = new BPlusTreeLeafNode(blk,this.bpt,isNewNode);
    }
    else{
      assert( blk.getBlockType() == MiniDB_Block.BLOCK_TYPE_BPT_INTERNAL );
      childNode = new BPlusTreeInternalNode(blk,this.bpt);
    }

    return childNode;
  }

  public String summaryToString(){
    String s = "BPlusTreeInternalNode, BlockID " + getThisBlockID() + "\n";
    s += "" + getCurrNumRecordsOnBlock() + ", values block\n";
    s += "minValue = " + getMinValue() + "\n";
    s += "isLeaf = " + isLeafNode() + "\n";

    for (int i=0; i<getCurrNumRecordsOnBlock(); i++){
      int childBlk = getBlockID(i);
      long value = getValue(i);
      s += "child blk " + i + "=" + childBlk + ", value=" + value + "\n";
    }

    s += "child blk " + getCurrNumRecordsOnBlock() + "=" + getBlockID(getCurrNumRecordsOnBlock()) + "\n";
  
    return s;
  }

}