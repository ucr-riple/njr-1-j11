package terminal.tools;

import java.io.*;
import java.util.Properties;

public class PropTools {

	 /**
     * Load a Properties File
     * @param propsFile
     * @return Properties
     * @throws java.io.IOException
     */
    public static Properties load(String propsFile)
    {
    	Properties props = new Properties();
        FileInputStream fis;
		try {
			fis = new FileInputStream(new File(propsFile));
			props.load(fis);    
		    fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        return props;
    }
    
    public static String getPropFile()
    {
    	String propFile=""; 
		propFile = "../service.properties";
		 
		return propFile;
    }
	
    public static void setProperty(String propertyName, String propertyValue)
    {
    	String propFile = PropTools.getPropFile();
    	Properties prop = PropTools.load(propFile);
    	
    	prop.setProperty(propertyName, propertyValue);
    	
    	try 
    	{ 
    		FileOutputStream sF=new FileOutputStream( propFile );
    		prop.store(sF, "");
    	} 
    	catch(IOException ex )
    	{ 
    		ex.printStackTrace();
    	}
    }
    
    static public File getFileHandler(String file)
	{
		try { 
			File aFile = new File(file); 
			
			if (aFile == null || !aFile.exists()) {
				boolean success = aFile.createNewFile(); 
				if (success) 
					aFile = new File(file); 
				else  
					return null;
				 
		    }
		   
		    if (!aFile.canWrite()) {
		      return null;
		    }
		    
		    return aFile;
		} 
		catch (IOException e) 
		{ 
			return null;
		}
	}
    
    static public String readFile(String file) 
	{
	    File aFile = getFileHandler(file);
	    
	    if (aFile == null) {
	    	return null;
	    }
	    
	    StringBuilder contents = new StringBuilder();
	    try 
	    {
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	      try 
	      {
	        String line = null;
	        while (( line = input.readLine()) != null)
	        {
	          contents.append(line);
	        }
		  }
		  finally 
		  {
		  	input.close();
		  }
	    }
	    catch (IOException ex)
	    {
	    	return "";
	    }
	    
	    return contents.toString();
	}
    
}
