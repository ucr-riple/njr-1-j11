package uwm_cs557;

public class MiniDB_Exception extends Exception{

  static final int DB_FULL = 1;
  static final int BUF_MGR_FULL = 2;

  int exCode;

  public MiniDB_Exception( int exCode ){
    this.exCode = exCode;
  }

  public static void exDie( MiniDB_Exception ex ){
    System.out.println( "Exception!" );
    ex.printStackTrace();
    System.exit(0);
  }

}
