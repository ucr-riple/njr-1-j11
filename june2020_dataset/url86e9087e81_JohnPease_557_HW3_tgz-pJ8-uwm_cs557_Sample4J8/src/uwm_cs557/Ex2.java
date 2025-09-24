package uwm_cs557;

public class Ex2{

  public static void main( String args[] ){

    try{
      MiniDB miniDB = new MiniDB("db");
      String filename = "foo", valueName = "bar";
      BPlusTree bpt1 = miniDB.createNewBPlusTreeIndex( filename, valueName, 8 );                                                                    
      BPlusTree bpt2 = miniDB.loadExistingIndex( filename, valueName );                                                                    
      bpt2.insertRecord(1,2); 
    }
    catch( MiniDB_Exception ex ){
      MiniDB_Exception.exDie(ex);
    }

  }

}