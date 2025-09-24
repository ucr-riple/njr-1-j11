package uwm_cs557;

public class BPlusTreeHeadBlock extends MiniDB_Block{

  private BPlusTree bpt;
  public void setBPlusTree( BPlusTree bpt ){ this.bpt = bpt; }
  public BPlusTree getBPlusTree(){ return bpt; }

  public int offset_rootBlockID(){ return MiniDB_Block.NUM_RESERVED_BYTES; }
  public int offset_firstLeafBlockID(){ return MiniDB_Block.NUM_RESERVED_BYTES + 4; }
  public int offset_indexedFieldSize(){ return MiniDB_Block.NUM_RESERVED_BYTES + 8;}
  public int offset_name(){ return MiniDB_Block.NUM_RESERVED_BYTES + 12;}

  public BPlusTreeHeadBlock(MiniDB_Block blk){
    super(blk);
    this.bpt = null;
  }

  public void setRootBlockID(int rootBlockID){ writeInt( offset_rootBlockID(), rootBlockID ); }
  public int getRootBlockID(){ return readInt( offset_rootBlockID() ); }

  public void setFirstLeafBlockID(int firstLeafBlockID){  writeInt( offset_firstLeafBlockID(), firstLeafBlockID );  }
  public int getFirstLeafBlockID(){  return readInt( offset_firstLeafBlockID() ); }

  public void setIndexedFieldSize( int indexingFieldSize){  writeInt( offset_indexedFieldSize(), indexingFieldSize );  }
  public int getIndexedFieldSize(){  return readInt( offset_indexedFieldSize() ); }

  public void setIndexName(String indexName){ writeString( offset_name(), indexName ); }
  public String getIndexName(){ return readString( offset_name() ); }

}