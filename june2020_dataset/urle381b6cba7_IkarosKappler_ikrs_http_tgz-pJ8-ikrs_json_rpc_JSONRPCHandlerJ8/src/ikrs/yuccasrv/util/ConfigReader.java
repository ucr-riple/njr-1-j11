package ikrs.yuccasrv.util;


import java.io.File;
import java.io.IOException;

import ikrs.typesystem.BasicType;
import ikrs.util.*;

/**
 * This XML reader was inspired by
 * http://www.java-tips.org/java-se-tips/javax.xml.parsers/how-to-read-xml-file-in-java.html
 *
 * @author Ikaros Kappler
 * @date 2012-04-24
 * @version 1.0.0
 **/


public class ConfigReader {
    
    /**
     * This wrapper method just calls the XMLEnvironmentReader that reads
     * the whole XML file into a nested environment.
     *
     * Text nodes will be kept.
     *
     * @param file The XML file you want to read.
     **/
    public static Environment<String,BasicType> read( File file )
	throws IOException {
	
	return XMLEnvironmentReader.read( file,
					  false   // dropTextNodes?
					  );
	
    }

    public static void main( String argv[] ) {
	try {
	    read( new File( argv[0] ) );
	} catch( IOException e ) {
	    e.printStackTrace();
	}
    }
}