package uwm_cs557;

public class MiniDB_Block{

  public static final int BLOCK_TYPE_UNKNOWN = 0;
  public static final int BLOCK_TYPE_CATALOG = 1;
  public static final int BLOCK_TYPE_BPT_HEADER = 2;
  public static final int BLOCK_TYPE_BPT_INTERNAL = 3;
  public static final int BLOCK_TYPE_BPT_LEAF = 4;

  public static int NUM_RESERVED_BYTES = 8;

  // buffer of MiniDB_Constants.BLOCK_SIZE bytes
  protected byte buffer[];
  public byte[] getBuffer(){ return buffer; }

  // The ID of this block in the database
  protected final int blockID;
  public int getThisBlockID(){ return blockID; }

  protected final MiniDB miniDB;
  //protected BufMgr bufMgr;
  //public BufMgr getBufMgr(){ return bufMgr; }

  public int offset_blockType(){ return 0; }
  public void setBlockType(int blockType){  writeInt( offset_blockType(), blockType );  }
  public int getBlockType(){ return readInt( offset_blockType() ); }
  public String blockTypeToString(){
    String s = "unrecognized";
    switch( getBlockType() ){
    case BLOCK_TYPE_UNKNOWN: s = "unknown"; break;
    case BLOCK_TYPE_CATALOG: s = "DB catalog"; break;
    case BLOCK_TYPE_BPT_HEADER: s = "B+Tree header"; break;
    case BLOCK_TYPE_BPT_INTERNAL: s = "B+Tree internal"; break;
    case BLOCK_TYPE_BPT_LEAF: s = "B+Tree leaf"; break;
    }
    return s;
  }

  public MiniDB_Block( MiniDB_Block blk ){
    this(  blk.getBuffer(), blk.getThisBlockID(), blk.miniDB );
  }

  public MiniDB_Block( byte buffer[], int blockID, MiniDB miniDB ){
    this.buffer = buffer;
    this.blockID = blockID;
    this.miniDB = miniDB;

    assert( this.buffer.length == MiniDB_Constants.BLOCK_SIZE );
  }

  public void readBytes( byte bufferOut[], int offset ){
    assert( offset + bufferOut.length - 1 < bufferOut.length );
    System.arraycopy( buffer, offset, bufferOut, 0, bufferOut.length );
  }

  public void writeBytes( byte bufferIn[], int offset ){
    assert( offset + bufferIn.length - 1 < buffer.length );
    System.arraycopy( bufferIn, 0, buffer, offset, bufferIn.length );
    miniDB.bufMgr.markDirty( this.blockID );
  }

  public String readString( int offset ){
    return readString(offset, buffer.length - offset );
  }

  /**
   * return the string starting at the offset provided
   * The length of the string is either maxLength or null terminated
   */
  public String readString( int offset, int maxLength ){
    byte bytes[] = new byte[maxLength];
    System.out.println( "maxLength is " + maxLength );
    
    System.arraycopy( buffer, offset, bytes, 0, maxLength );

    int sLen = 0;
    boolean endFound = false;

    for (int i=0; i<maxLength && !endFound; i++){
      if( bytes[i] != '\0' ) sLen++;
      else{
	endFound = true;
      }
    }

    System.out.println( "sLen is " + sLen );
    
    return new String(bytes, 0, sLen);
  }

  public int writeString( int offset, String s ){
    return writeString( offset, s, -1 );
  }

  /**
   * return the number of bytes written
   */
  public int writeString( int offset, String s, int maxNumChars){
    int len;

    if( maxNumChars == -1 ){      len = s.length()+1;    }
    else{      len = Math.max( maxNumChars, s.length() ) + 1;    }

    byte sBytes[] = s.getBytes();
    byte bytesToWrite[] = new byte[len];

    System.arraycopy( sBytes, 0, bytesToWrite, 0, len-1 );
    bytesToWrite[len-1] = '\0';

    writeBytes( bytesToWrite, offset );
    
    return len;
  }

  public short readShort( int offset ){
    return  byteArrayToShort( this.buffer, offset );
  }

  public void writeShort( int offset, short s ){
    assert( offset+1 < buffer.length );

    // LSB
    buffer[offset+0] = (byte)s;                      

    // MSB
    buffer[offset+1] = (byte)( (s & 0xFF00) >>> 8 );

    miniDB.bufMgr.markDirty( blockID );
  }

  public int readInt( int offset ){
    // the bit masks are a hack to force java to interpret each byte as unsigned (logical)
    return
      ((0xFF & buffer[offset+3]) << 24) + 
      ((0xFF & buffer[offset+2]) << 16) +
      ((0xFF & buffer[offset+1]) << 8) + 
      ((0xFF & buffer[offset]));
  }

  public int writeInt( int offset, int value ){
    assert( offset+3 < buffer.length );

    buffer[offset+0] = (byte)value;                      
    buffer[offset+1] = (byte)( (value & 0xFF00) >>> 8 );
    buffer[offset+2] = (byte)( (value & 0xFF0000) >>> 16 );
    buffer[offset+3] = (byte)( (value & 0xFF000000) >>> 24 );

    miniDB.bufMgr.markDirty(this.blockID);
    return 4;
  }

  public long readLong( int offset ){
    // the bit masks are a hack to force java to interpret each byte as unsigned (logical)
    // System.out.println( "readLong(" + offset + ")\n" + toString() );
    return
      ((0xFFl & buffer[offset+7]) << 56) + 
      ((0xFFl & buffer[offset+6]) << 48) +
      ((0xFFl & buffer[offset+5]) << 40) + 
      ((0xFFl & buffer[offset+4]) << 32) +
      ((0xFFl & buffer[offset+3]) << 24) + 
      ((0xFFl & buffer[offset+2]) << 16) +
      ((0xFFl & buffer[offset+1]) << 8) + 
      ((0xFFl & buffer[offset]));
  }

  public int writeLong( int offset, long value ){
    assert( offset+7 < buffer.length );

    buffer[offset+0] = (byte)value;                      
    buffer[offset+1] = (byte)( (value & 0xFF00l) >>> 8 );
    buffer[offset+2] = (byte)( (value & 0xFF0000l) >>> 16 );
    buffer[offset+3] = (byte)( (value & 0xFF000000l) >>> 24 );
    buffer[offset+4] = (byte)( (value & 0xFF00000000l) >>> 32 );
    buffer[offset+5] = (byte)( (value & 0xFF0000000000l) >>> 40 );
    buffer[offset+6] = (byte)( (value & 0xFF000000000000l) >>> 48 );
    buffer[offset+7] = (byte)( (value & 0xFF00000000000000l) >>> 56 );

    if( miniDB != null )  miniDB.bufMgr.markDirty(this.blockID);
    return 8;
  }


  public String toString(){
    String s = "BLOCK_ID " + blockID + "\n";
    s += "BLOCK_TYPE: " + blockTypeToString() + "\n";
    s+=prettyPrintBuffer(this.buffer);
    return s;
  }

  // set all bytes in buffer to value in b
  public void set( byte b ){
    for (int i=0; i<buffer.length; i++) buffer[i] = b;
  }

  /** 
   * Return 16-bit short for 2 consequtive bytes in a byte array
   * 
   * @param lsbIdx : the index of the least significant bit. The MSB index is lsbIdx+1
   */
  private static final short byteArrayToShort(byte bufIn[], int lsbIdx) {
    // the bit masks are a hack to force java to interpret each byte as unsigned (logical)
    int msb = ((0xFF & bufIn[lsbIdx+1]) << 8); 
    int lsb = ((0xFF & bufIn[lsbIdx]));

    return (short)( msb + lsb );
  }

  /** 
   * non-printable characters are displayed as .
   */
  private static String prettyPrintBuffer( byte[] buf ){
    String asHex[] = byteArrayToHexString(buf);
    StringBuffer sb = new StringBuffer( buf.length * 6 );

    int rowSize = 8;
    int rowSizeInChars = rowSize*3;
    String spacer = "   ";
    String currRow = "";
    String currRowS = "";

    for (int i=0; i<asHex.length; i++){

      if( (i > 0 && i % rowSize == 0) ){
	sb.append( currRow + spacer + currRowS + "\n" );
	currRow = "";
	currRowS = "";
      }
      
      currRow += asHex[i] + " ";
      //currRowS += currRowS + ((char)buf[i]);
      String appendand = new String( buf, i, 1 );
      char c = (char)buf[i];
      if( !((c >= 'a' && c <= 'z') ||
	   (c >= 'A' && c <= 'Z') ||
	   (c == ' ' ) ||
	   (c == '\t' )) ) appendand = ".";

      currRowS += appendand;
    };

    // get the last line
    while( currRow.length() < rowSizeInChars ) currRow += " ";
    sb.append( currRow + spacer + currRowS );

    return sb.toString();
  }

  private static String byteToHexString( byte b ){
    String sOut = "";

    sOut = "";
    if( b >= 0 && b < 16 ){ sOut += "0"; }
    sOut += Integer.toHexString(0xFF & b);
    // sOut[i] += b;

    return sOut;
  }

  private static String[] byteArrayToHexString(byte[] b) {

    String sOut[] = new String[b.length];
    for (int i=0; i<sOut.length; i++){
      sOut[i] = byteToHexString(b[i]);
    }

    return sOut;
  }

  public static void main( String args[] ){

    byte buf[] = new byte[ MiniDB_Constants.BLOCK_SIZE];
    int blockID = 1;

    MiniDB_Block blk = new MiniDB_Block(buf, blockID, null);
    
    blk.set((byte)'x');

    blk.writeLong(8,-1);
    blk.writeLong(24,-2);
    blk.writeLong(64,1);
    blk.writeLong(96,2);
    System.out.println( blk.toString() );
    System.out.println( "" + blk.readLong(8) + ", "  +  blk.readLong(24) );
    System.out.println( "" + blk.readLong(64) + ", "  +  blk.readLong(96) );
    System.exit(0);

    boolean testShort = false, testInt = false, testString = true;

    byte bs[] = {'H', 'I', 0, 0, 0};
    String sbs = new String( bs );
    //System.out.println( "String is " + sbs + ", length is " + sbs.length() );
    
    if( testShort ){
      short s1 = (short)1;
      short s2 = (short)99;
      short s3 = (short)7002;

      blk.writeShort(0, s1);
      blk.writeShort(10, s2);
      blk.writeShort(200, s3);

      System.out.println( blk.toString() );
      
      short sOut1 = blk.readShort(0) ;
      short sOut2 = blk.readShort(10) ;
      short sOut3 = blk.readShort(200) ;
      
      System.out.println( "" + sOut1 + " " + sOut2 + " " + sOut3 );
      
      assert( sOut1 == s1 );
      assert( sOut2 == s2 );
      assert( sOut3 == s3 );
    }
    else if(testInt){
      System.out.println( "testing read/write int" );
      int values[] = {0xa, 0xab, 0xFEDC, 0x1234, 0xFEDCBA98};
      int locations[] = {0,10, 15, 32, 36, 40, 44};

      for (int i=0; i<values.length; i++){
	blk.writeInt(locations[i], values[i]);	  
      }

      System.out.println( blk.toString() );

      for (int i=0; i<values.length; i++){
	int v = blk.readInt(locations[i]);
	System.out.println( "" + v + ", " + values[i] );
	assert( v == values[i] );
      }

    }
    else if(testString){
      System.out.println( "testing read/write String" );
      String values[] = {"Hello!", "These are the times"};
      int locations[] = {0,16};

      for (int i=0; i<values.length; i++){
	blk.writeString(locations[i], values[i]);	  
      }

      System.out.println( blk.toString() );

      for (int i=0; i<values.length; i++){
	String s = blk.readString(locations[i]);
	System.out.println( "" + s + ", " + values[i] );
	assert( s.equals(values[i]) );
      }
      
    }

  }


}
