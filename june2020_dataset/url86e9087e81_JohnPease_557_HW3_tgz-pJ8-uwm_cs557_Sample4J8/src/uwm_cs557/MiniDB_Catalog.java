package uwm_cs557;

import java.util.Hashtable;

public class MiniDB_Catalog{

  private static int numIndexesOffset = 4;

  private Hashtable indexName2Block = new Hashtable();

  private int numIndexes = 0;

  private int freeOffset = -1;

  private MiniDB_Block headBlock;

  private MiniDB miniDB;

  private MiniDB_Catalog(MiniDB miniDB){
    this.miniDB = miniDB;
  }

  /**
   * set the head block of the catalog
   */
  public void setHeadBlock( MiniDB_Block headBlock ){
    this.headBlock = headBlock;
  }

  private void init(){
    this.numIndexes = headBlock.readInt( numIndexesOffset );

    this.freeOffset = MiniDB_Block.NUM_RESERVED_BYTES; 

    for (int i=0; i<this.numIndexes; i++){
      String idxName = headBlock.readString( this.freeOffset );
      this.freeOffset += idxName.length() + 1;
      int blockID = headBlock.readInt( this.freeOffset );
      this.freeOffset += 4;
	
      //      System.out.println( "Read Index " + (i+1) + " " + idxName + ", " + blockID );

      indexName2Block.put( idxName, blockID );
    }
  }

  public void setNumIndexes(int numIndexes ){
    this.headBlock.writeInt( numIndexesOffset, numIndexes );
    miniDB.bufMgr.markDirty( headBlock.blockID );
  }

  public int getNumIndexes() {
    return this.headBlock.readInt( numIndexesOffset );
  }

  public int getNumberOfFiles(){
    return getNumIndexes(); // currently indexes are the only files
  }

  public void addIndex( String indexName, int blockID ){
    this.freeOffset += headBlock.writeString( this.freeOffset, indexName );
    this.freeOffset += headBlock.writeInt( this.freeOffset, blockID );
    numIndexes++;
    setNumIndexes( numIndexes );

    this.indexName2Block.put( indexName, blockID );
  }

  /** 
   * return the head block ID of a named index or -1 if the index is not in the catalog
   */
  public int getIndexHeadBlockID( String indexName ){
    int headBlockID = -1;

    Integer tmp = (Integer)indexName2Block.get( indexName );
    if( tmp != null ) headBlockID = tmp.intValue();

    return headBlockID;
  }

  /**
   * load an existing catalog object
   */
  public static MiniDB_Catalog createFromExisting( MiniDB_Block headBlock, MiniDB miniDB ){
    MiniDB_Catalog catalog = new MiniDB_Catalog(miniDB);
    catalog.setHeadBlock(headBlock);
    catalog.init();

    System.out.println( "CREATE fRIOM existing" );

    return catalog;
  }

  public static MiniDB_Catalog createNewCatalog( MiniDB_Block headBlock, MiniDB miniDB ){
    MiniDB_Catalog catalog = new MiniDB_Catalog(miniDB);
    
    //headBlock.set( (byte)'x' );
    headBlock.setBlockType( MiniDB_Block.BLOCK_TYPE_CATALOG );

    catalog.setHeadBlock( headBlock );
    catalog.setNumIndexes(0);
    catalog.init();

    return catalog;
  }  

}