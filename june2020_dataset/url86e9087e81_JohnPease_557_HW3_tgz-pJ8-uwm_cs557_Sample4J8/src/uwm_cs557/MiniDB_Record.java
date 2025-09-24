package uwm_cs557;


public class MiniDB_Record implements Comparable{

  Long value;
  Integer blockID;

  public MiniDB_Record( int blockID, long value ){
    this.value = new Long(value);
    this.blockID = new Integer(blockID);
  }

  public int compareTo( Object o1 ){
    MiniDB_Record that = (MiniDB_Record)o1;

    int ret = this.value.compareTo( that.value );

    if( ret == 0 ) ret = this.blockID.compareTo( that.blockID );

    return ret;
  }
 
  public boolean equals( Object o ){
    MiniDB_Record that = (MiniDB_Record)o;

    return this.compareTo( that ) == 0;
  }

  public String toString(){
    String s = "(" + blockID + "*," + value + " [0x" + Long.toHexString(value) + "] )";
    return s;
  }

}