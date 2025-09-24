package uwm_cs557;

import java.io.File;

public class DBUtil{

  public static boolean fileOrDirectoryExists( String filename ){

    boolean exists = (new File(filename)).exists();

    return exists;
  }


  public static void main( String args[] ){
    boolean exists = fileOrDirectoryExists( args[0] );
    System.out.println( args[0] + " " +
			( exists ? "exists" : "does not exist" ) );
  }

}
    
