package uwm_cs557;

import java.io.*;

public class MiniDB{

  private String dbName;

  // simiulates the disk space manager and disk drive
  public byte dbBlocks [][];
  public boolean dbBlockUsed[];

  /** 
   * @return the name of the operating system files the database is stored in 
   */
  public String osFilename(){ return dbName + ".minidb"; }

  public static BufMgr bufMgr;
  public BufMgr getBufMgr(){ return bufMgr; }

  public MiniDB_Catalog catalog;
  public MiniDB_Catalog getCatalog(){ return catalog; }

  /**
   * startup a database with name dbName
   */
  public MiniDB(String dbName){
    this.dbName = dbName;
    this.bufMgr = new BufMgr(this);

    try{
      
      if( MiniDB_Util.fileOrDirectoryExists( osFilename() ) ){
	// load DB from file
	restoreDBFromOSFile();
	
	MiniDB_Block catHeadBlock = bufMgr.pin_block( MiniDB_Constants.CATALOG_BLOCK_ID );
	this.catalog = MiniDB_Catalog.createFromExisting( catHeadBlock, this );
      }
      else{
	// create DB from scratch
	this.dbBlocks = new byte[ MiniDB_Constants.DB_MAX_BLOCKS ][ MiniDB_Constants.BLOCK_SIZE ];
	this.dbBlockUsed = new boolean[ MiniDB_Constants.DB_MAX_BLOCKS ];

	for (int i=0; i<MiniDB_Constants.DB_MAX_BLOCKS; i++){
	  for (int j=0; j< MiniDB_Constants.BLOCK_SIZE ; j++){
	    this.dbBlocks[i][j] = (byte)0xaa;
	  }
	}
	
	// create the catalog
	int blockID = addBlockToDB();
	assert( blockID == MiniDB_Constants.CATALOG_BLOCK_ID );
	MiniDB_Block catHeadBlock = bufMgr.pin_block( MiniDB_Constants.CATALOG_BLOCK_ID );
	this.catalog = MiniDB_Catalog.createNewCatalog(catHeadBlock, this);
	bufMgr.markDirty( catHeadBlock.blockID );
      }
      
    }
    catch(MiniDB_Exception ex){
      // bad news
      System.out.println( "Exception " + ex );
      ex.printStackTrace();
      System.exit(0);
    }

  }

  private void restoreDBFromOSFile(){
    try {
      FileInputStream fin = new FileInputStream(osFilename());
      ObjectInputStream ois = new ObjectInputStream(fin);
      this.dbBlocks = (byte[][]) ois.readObject();
      this.dbBlockUsed = (boolean[]) ois.readObject();
      ois.close();
    }
    catch (Exception e) { e.printStackTrace(); }
  }

  /**
   * Allocates a new block to the DB. The new block is *not* brought into the buffer manager. A subsequent
   * call to pin_block is need to being the block into the DB.
   * @return The dbBlockID of the new block is returned or -1 if there no room for an addtional block.
   */ 
  public int addBlockToDB( ) throws MiniDB_Exception{
    int newBlockID = (int)-1;

    for (int i=0; i<MiniDB_Constants.DB_MAX_BLOCKS && newBlockID==-1; i++){
      if( !dbBlockUsed[i] ){
	newBlockID = (int)i;
	dbBlockUsed[i] = true;
      }
    }

    if( newBlockID == -1 ) throw new MiniDB_Exception( MiniDB_Exception.DB_FULL );

    //MiniDB.log( "BufMgr.addBlockToDB, new block ID = " + newBlockID );

    return newBlockID;
  }


  public int numBlocksInDB(){
    int cnt = 0;
    for (int i=0; i<MiniDB_Constants.DB_MAX_BLOCKS; i++) if( dbBlockUsed[i] ) cnt++;

    return cnt;
  }


  protected static String indexNameKey(  String indexedFilename, String fieldName ){
    return indexedFilename + "#" + fieldName;
  }

  public BPlusTree loadExistingIndex( String indexedFilename,
				      String indexedFieldname ) throws MiniDB_Exception{
    BPlusTree bpt = null;
    BPlusTreeHeadBlock indexHeadBlock = null;

    int headBlockID = catalog.getIndexHeadBlockID( indexNameKey(indexedFilename, indexedFieldname) );
    MiniDB_Block blk = bufMgr.pin_block( headBlockID );

    if( blk != null ){
      bpt = new BPlusTree(this);
      indexHeadBlock = new BPlusTreeHeadBlock( blk );
      bpt.init_existing( indexedFilename, indexedFieldname, indexHeadBlock );
    }
    else{
      // something went wrong. no room in buffer mgr?
    }
    
    return bpt;
  }

  public BPlusTree createNewBPlusTreeIndex( String indexedFileName,
					    String indexedFieldName, 
					    int indexedFieldSize) throws MiniDB_Exception{
    BPlusTreeHeadBlock indexHeadBlock = null;
    BPlusTree bpt = null;
    String indexName = indexNameKey(indexedFileName, indexedFieldName);
    int headBlockID = catalog.getIndexHeadBlockID( indexName );

    if( headBlockID != -1 ){
      // There is already an index on this field in the DB
    }
    else{
      // add a block to the DB for the head block
      headBlockID = this.addBlockToDB(); 

      if( headBlockID != -1 ){
	MiniDB_Block block = bufMgr.pin_block( headBlockID );
	
	if( block == null ){
	  // no room in the buffer pool. Should delete the head block
	}
	else{
	  indexHeadBlock = new BPlusTreeHeadBlock( block );
	  indexHeadBlock.setBlockType( MiniDB_Block.BLOCK_TYPE_BPT_HEADER );
	  catalog.addIndex( indexName, headBlockID );
	  
	  bpt = new BPlusTree(this);
	  bpt.init_new(indexedFileName, 
		       indexedFieldName, 
		       indexedFieldSize, 
		       indexHeadBlock );

	}
      }
      else{
	// no room in the database
      }
    }

    return bpt;
  }

  public void writeDBToOSFile(){

    bufMgr.flushDirty();

    try {
      FileOutputStream fout = new FileOutputStream(osFilename());
      ObjectOutputStream oos = new ObjectOutputStream(fout);
      oos.writeObject(dbBlocks);
      oos.writeObject(dbBlockUsed);
      oos.close();
    }
    catch (Exception e) { e.printStackTrace(); }
  }

  public String allDBBlocksToString() throws MiniDB_Exception{
    String s = "";

    for (int i=0; i<MiniDB_Constants.DB_MAX_BLOCKS; i++){
      if( dbBlockUsed[i] ){
	MiniDB_Block blk = bufMgr.pin_block(i);
	s += blk.toString() + "\n\n";
	bufMgr.unpin_block(i);
      }

    }

    return s;
  }

  public String getInfo(){
    String s = "info for dbms instance '" + dbName + "':\n";

    s += "Max number of blocks:        " + ( MiniDB_Constants.DB_MAX_BLOCKS) + "\n";
    s += "Number of allocated blocks:  " + numBlocksInDB() + "\n";
    s += "Number of unallcated blocks: " + ( MiniDB_Constants.DB_MAX_BLOCKS - numBlocksInDB() ) + "\n";
    s += "Number of files:             " + catalog.getNumberOfFiles() + "\n";

    return s;
  }

  public static String log( String s ){
    System.err.println( s );
    return s;
  }

  public static void main( String args[] ){

  }



}

