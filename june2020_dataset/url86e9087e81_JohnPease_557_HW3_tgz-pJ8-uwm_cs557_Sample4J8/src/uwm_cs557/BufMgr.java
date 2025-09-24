package uwm_cs557;

public class BufMgr{

  // variables related to the buffer manager
  private byte frames[][];
  private int pinCount[];
  private boolean dirty[];
  private int blockIDInFrame[];
  
  private final MiniDB miniDB;

  /**
   * Construct buffer manager for a named DBMS. 
   * 
   * If the osFilename() for the DBMS exists, DB blocks are set from the file. Otherwise
   * a new database with on DB blocks is created.
   */
  public BufMgr( MiniDB miniDB ){
    this.miniDB = miniDB;

    this.frames         = new byte[ MiniDB_Constants.BUF_MGR_NUM_FRAMES ][ MiniDB_Constants.BLOCK_SIZE ];
    this.pinCount       = new int[MiniDB_Constants.BUF_MGR_NUM_FRAMES];
    this.dirty          = new boolean[MiniDB_Constants.BUF_MGR_NUM_FRAMES];
    this.blockIDInFrame = new int[MiniDB_Constants.BUF_MGR_NUM_FRAMES];

    for (int i=0; i<MiniDB_Constants.BUF_MGR_NUM_FRAMES; i++) this.blockIDInFrame[i] = -1;
  }

  /**
   * Indicates that the data on blockID in buffer manager is different than in the DB.
   *
   * @return <code>true</code> on success or <code>false</code> if blockID is not in the buffer pool.
   */
  public boolean markDirty( int blockID ){
    int frameID = findFrameForBlock( blockID );
    boolean returnVal = false;

    if( frameID != -1 ){
      dirty[frameID] = true;
      returnVal = true;
    }

    return returnVal;
  }

  //
  // return DB block with given ID or null if buffer manager is full.
  //
  public MiniDB_Block pin_block( int blockID ) throws MiniDB_Exception{
    //MiniDB.log( "BEGIN BufMgr.pin_block(" + blockID + ")" );

    int frameID = findFrameForBlock( blockID );
    MiniDB_Block blockOut = null;

    if( frameID == -1 ){
      // block is not in buffer mgr

      // get a frame for the incoming block (it also writes frame to DB if its dirty)
      frameID = getFrameForReplacement();
      assert( pinCount[frameID] == 0 );

      if( frameID != -1 ){
	readBlock( frameID, blockID );
	dirty[frameID] = false;
	blockOut = new MiniDB_Block( frames[frameID], blockID, miniDB );
	pinCount[frameID]=1;
	blockIDInFrame[frameID] = blockID;
      }

    }
    else{
      // frame already in buffer manager
      blockOut = new MiniDB_Block( frames[frameID], blockID, miniDB );
      pinCount[frameID]++;
    }

    //MiniDB.log( "frameID is " + frameID );
    //MiniDB.log( "END BufMgr.pin_block(" + blockID + ")" );

    if( blockOut == null ) throw new MiniDB_Exception( MiniDB_Exception.BUF_MGR_FULL );

    return blockOut;
  }

  public void unpin_block( int blockID ){
    int frameID = findFrameForBlock( blockID );
    assert( frameID >= 0 );
    assert( this.pinCount[frameID] > 0 );
    this.pinCount[frameID]--;
  }

  // return frameID of frame that holds the specified database blockID
  // return -1 if the block is not in any frame
  private int findFrameForBlock( int blockID ){
    int frameID = -1;

    for (int i=0; i<MiniDB_Constants.BUF_MGR_NUM_FRAMES && frameID==-1; i++){
      if( this.blockIDInFrame[i] == blockID ) frameID = i;
    }

    return frameID;
  }

  /**
   * find a frame that can be replaced, and if the frame is dirty, write its block to the DB
   */
  private int getFrameForReplacement(){
    int frameID = -1;

    for (int i=0; i<this.frames.length && frameID == -1; i++){
      if( this.pinCount[i] == 0 ) frameID = i;
    }

    // System.out.println( "Replacement frame is " + frameID );

    if( frameID != -1 && dirty[frameID] ){
      writeBlock(frameID);
    }

    return frameID;
  }

  //
  // read block from DB to frame in buffer manager.
  // 
  private void readBlock( int frameID, int dbBlockID ){
    assert( this.pinCount[frameID] <= 0 );

    System.arraycopy( miniDB.dbBlocks[dbBlockID], 0, this.frames[frameID], 0, MiniDB_Constants.BLOCK_SIZE );
  }

  //
  // read block from buffer manager to DB
  // 
  private void writeBlock( int frameID ){
    System.arraycopy( this.frames[frameID], 0, miniDB.dbBlocks[this.blockIDInFrame[frameID]], 0, MiniDB_Constants.BLOCK_SIZE );
  }

  public void flushDirty(){
    // flush dirty blocks
    for (int i=0; i<this.frames.length; i++){
      if( dirty[i] ){
	writeBlock(i);
      }
    }
  }


  
  public String toString(){
    String s = "";
    
    s += "Number of blocks in DB: " + miniDB.numBlocksInDB();

    return s;
  }

  public String bufMgrStateToString(){
    String s = "";

    for (int i=0; i<frames.length; i++){
      if( blockIDInFrame[i] != -1 ){
	s += "frame " + i + ", db block " + blockIDInFrame[i] + ", pin count " + pinCount[i] + ", " + dirty[i] + "\n";
      }
    }

    return s;
  }

  public static void main( String args[] ){
  }

}