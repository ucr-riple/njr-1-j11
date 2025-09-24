package uwm_cs557;

public class MiniDB_example{

  /**
   * This program is an example of using MiniDB. It
   * 1) creates a database named example-db
   * 2) adds a B+Tree index file for the datafile "example-file" and indexing field "example-field"
   *
   * We assume that the DBMS starts from scratch,  example-db.minidb does 
   */
  public static void main( String args[] ){
    String dbName = "example-db";
      
    MiniDB db;  // an instance of the DBMS
    BufMgr bm;  // the buffer manager
    String s; 
    BPlusTree bpt;

    String indexingFile = "example-file";
    String indexedField = "example-field";

    // start the DBMS. If the osfile for the db exists in the current directory
    // the DB blocks are loaded from the file. Otherwise new blocks are allocated
    // 
    db = new MiniDB(dbName);

    s = "dbms instance '" + dbName + "' started. If the file ." + java.io.File.separator + db.osFilename() + " exists " +
      " the db blocks are loaded from a previous instance, otherwise new blocks are allocated.";

    s += "\n\n";
    
    s += "We assume that a new DBMS instance was created. ";
    s += "Next, we'll look at some high-level information about the DB\n";

    MiniDB_Util.waitComment(s);

    System.out.println( db.getInfo() );
    s = "There should only be one block in the DB. This is the catalog head block (blockID=0) \n\n";
    s += "Let's investigate the contents of this block. We'll pin the block and then print its contents with " +
      "MiniDB_Block.toString() \n";
    MiniDB_Util.waitComment(s);

    MiniDB_Block blk = null;

    try{
      // use exception handling because pin_block throws an exception if the
      // buffer manager is full.
      blk = db.bufMgr.pin_block(MiniDB_Constants.CATALOG_BLOCK_ID);

      // print the contents of the block
      System.out.println( blk.toString() );

      s = "We initialize all bytes in all DB blocks to 0xaa. As can be seen above, only " +
	"the first 8 bytes (two 4-byte integers) on the catalog block have been set. The first int holds the block type " +
	"(MiniDB_Constants.BLOCK_TYPE_CATALOG) while the second byte holds the number of files (0 presently)";
      s += "\n\nNext we'll create a B+Tree index file and see how the contents of the catalog changes\n";
      
      MiniDB_Util.waitComment(s);
     
      // Presently, the only supported field size is 8 bytes
      bpt = db.createNewBPlusTreeIndex( indexingFile, indexedField, 8 );
      System.out.println( "********* new B+tree index created **********\n" );
      System.out.println( blk.toString() );

      s = "Notice that now the catalog block has additional information about the new index: in " +
	"particular the name of the index ('example-file#example-field') and the header block for the index file\n";

      s += "\nNow there are 1 file and 3 blocks (the catalog + the header and root of the B+tree index) in the DB:\n\n" +
	db.getInfo();
      MiniDB_Util.waitComment(s);

      s = "B+Tree info\n " +  bpt.toString();
      MiniDB_Util.waitComment(s);

      s = "We can look at the contents of the B+Tree header and root pages in a similar way\n\n";

      int blockID = db.catalog.getIndexHeadBlockID( MiniDB.indexNameKey(indexingFile, indexedField ) );

      MiniDB_Block tmpBlk = db.bufMgr.pin_block( blockID );                // bufMgr provides blocks as type MiniDB_Block
      BPlusTreeHeadBlock bptHeadBlock = new BPlusTreeHeadBlock( tmpBlk );  // convert to specifc block type using c'tor

      tmpBlk = db.bufMgr.pin_block( bptHeadBlock.getRootBlockID() );
      boolean newNode = false; // this is not a new node
      BPlusTreeLeafNode bptRoot = new BPlusTreeLeafNode(tmpBlk, bpt, newNode );   // we know it's a leaf node now because the index is empty

      s += bptHeadBlock.toString() + "\n\n" + bptRoot.toString() + "\n";

      MiniDB_Util.waitComment(s);

      s += "\n\nLast, we'll insert a record to the index for value=0xFFEEDDCCBBAA9988 and data block ID = 0x44332211 " +
	" and see how the leaf node at the root changes\n\n";
      MiniDB_Util.waitComment(s);

      bpt.insertRecord(0xFFEEDDCCBBAA9988l, 0x44332211 );

      s = bptRoot.toString() + "\n";
      s += "\n\nYou can  see above where the record has been placed on the leaf page\n";
      MiniDB_Util.waitComment(s);
    }
    catch( MiniDB_Exception ex ){
      MiniDB_Exception.exDie(ex); // exits
    }

  }

}