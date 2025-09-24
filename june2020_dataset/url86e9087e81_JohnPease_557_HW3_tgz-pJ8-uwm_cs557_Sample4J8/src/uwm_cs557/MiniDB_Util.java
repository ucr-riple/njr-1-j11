package uwm_cs557;

import java.io.File;

public class MiniDB_Util{

  public static boolean canWait=true; // provides a way to disable waiting

  public static boolean fileOrDirectoryExists( String filename ){

    boolean exists = (new File(filename)).exists();

    return exists;
  }

  public static void waitHere(){
    waitHere("hit a key..." );
  }

  public static void waitHere(String msg)
  {
    System.out.println("");
    System.out.println(msg); 
    if( canWait ){
      try { System.in.read(); } catch(Exception e) {} // Ignore any errors while reading.
      try { System.in.read(); } catch(Exception e) {} // Ignore any errors while reading.
    }
  }

  public static void waitComment(String msg){
    java.util.StringTokenizer st = new java.util.StringTokenizer(msg, " \t\n", true);
    String sWrapped = "";
    int lineLen = 0;
    int crStop = 70;
    while( st.hasMoreTokens() ){
      String tok = st.nextToken();
      
      if( lineLen == 0 && (tok.startsWith( " " ) || tok.startsWith( "\t" )) ){
      }
      else{
	sWrapped += tok;
	lineLen += tok.length();

	int pos = tok.lastIndexOf('\n');

	if( pos != -1){
	  lineLen = tok.length() - pos;
	}

	if( lineLen > crStop ){
	  sWrapped += "\n";
	  lineLen = 0;
	}
      }

    }
    
    waitHere(sWrapped);
  }

  public static void main( String args[] ){
    boolean exists = fileOrDirectoryExists( args[0] );
    System.out.println( args[0] + " " +
			( exists ? "exists" : "does not exist" ) );
  }

}
    
