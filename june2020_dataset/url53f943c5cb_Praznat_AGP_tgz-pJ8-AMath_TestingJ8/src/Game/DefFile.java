package Game;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DefFile {

	public static int maxfilelength = 200000;
	
	
	
	
	
	
	
    public static String[] readFile (String fn) {
	    File file = new File( fn ); 
	    if ( file.exists() )  {
            try
			{
			    String[] data = new String[maxfilelength];
		        BufferedReader inFile = new BufferedReader(
		            new FileReader( file ) );	
		        int lineNum = 0;
		        String line = inFile.readLine();
		        while ( line != null )
		        {
		        	data[Math.min(maxfilelength-1,lineNum++)] = line;
		            line = inFile.readLine();
		        }
		        inFile.close();
			    String[] dataVector = new String[lineNum];
			    System.arraycopy(data, 0, dataVector, 0, lineNum);
		    	return dataVector;
			}
	        catch (Exception e) { 
	        	System.err.println("File input error:  " + e.getMessage()); 
		    	String[] dataVector = new String[0];
		    	return dataVector; 
	        }
	    }
	    else {
	    	String[] dataVector = new String[0];
	    	return dataVector; 
	    }
    }
}



