package ikrs.httpd.resource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import ikrs.httpd.HTTPHandler;
import ikrs.io.ReplacingInputStream;
import ikrs.util.CustomLogger;


/**
 * The ReplacingResource extends the BufferedResource and applies a replacement
 * map on the underlying stream.
 *
 * Actually it's just a wrapper to hide the ikrs.util.ReplacingInputStream
 * from the rest of the package.
 *
 * Note: as no one can tell if or how many placeholder will appear in the
 *       stream data, the final stream length it is not predictable.
 *       That's why the whole (!) incoming data needs to be buffered.
 *
 *       Keep that in mind if you want pass large files or infinite streams.
 *
 *
 * @author Ikaros Kappler
 * @date 2013-05-11
 * @version 1.0.0
 **/


public class ReplacingResource 
    extends BufferedResource {


    /**
     * Create a new ReplacingResource.
     *
     * @param handler        The HTTPHandler (may be null if not available).
     * @param logger         A custom logger to write log messages to (must not be null).
     * @param in             The input stream to read the data for buffering from.
     * @param useFairLocks   If set to true the class will use fair read locks (writing isn't
     *                       possible at all with this class).
     * @param replacementMap The map containing the (placeholder replacement) pairs; the map
     *                       may be empty but must not be null.
     * @throws NullPointerException If logger, the input stream, the replacement map is null
     *                              or if the replacement map contains null entries.
     **/
    public ReplacingResource( HTTPHandler handler,
			      CustomLogger logger,
			      InputStream in,
			      boolean useFairLocks,
			      
			      Map<byte[],byte[]> replacementMap ) 
	throws NullPointerException {

	super( handler, logger, new ReplacingInputStream(in, replacementMap), useFairLocks );
	
    }



    /**
     * For testing only.
     **/
    public static void main( String[] argv ) {

	
	String filename = "document_root/system/errors/GenericError.template.html";
	CustomLogger logger = new ikrs.util.DefaultCustomLogger("test");

	Map<byte[],byte[]> replacementMap = new java.util.TreeMap<byte[],byte[]>( new ikrs.util.ByteArrayComparator() );
	try {

	    replacementMap.put( new String("%{STATUS_CODE}%").getBytes(java.nio.charset.StandardCharsets.UTF_8.name()),
				new String("500").getBytes(java.nio.charset.StandardCharsets.UTF_8.name()) 
				);
	    replacementMap.put( new String("%{REASON_PHRASE}%").getBytes(java.nio.charset.StandardCharsets.UTF_8.name()),
				new String("Iternal Server Error").getBytes(java.nio.charset.StandardCharsets.UTF_8.name()) 
				);
	    replacementMap.put( new String("%{ERROR_MESSAGE}%").getBytes(java.nio.charset.StandardCharsets.UTF_8.name()),
				new String("I encountered an internal server error.").getBytes(java.nio.charset.StandardCharsets.UTF_8.name()) 
				);
	    System.out.println( "Replacement map: " + replacementMap.toString() );

	    
	    System.out.println( "Creating file resource from " + filename );
	    ikrs.httpd.Resource fileResource = new FileResource( null,  // no handler available
									  logger,
									  new java.io.File(filename),
									  false // no need to use fair locks here
									  );
	    System.out.println( "Opening file resource ... ");
	    fileResource.open( true );  // open in read-only mode

	    System.out.println( "Creating replacing resource ... ");
	    ikrs.httpd.Resource replacingResource = new ReplacingResource( null,  
									   logger,
									   fileResource.getInputStream(),
									   false,
									   replacementMap
									   );

	    System.out.println( "Opening replacing resource ..." );
	    replacingResource.open( true );

	    System.out.println( "Reading replacing resource ... ");
	    byte[] buffer = new byte[ 256 ];
	    int len;
	    while( (len = replacingResource.getInputStream().read(buffer)) != -1 ) {

		for( int i = 0; i < len; i++ )
		    System.out.print( (char)buffer[i] );

	    }
	    System.out.println( "" );

	    System.out.println( "Closing replacing resource ... ");
	    replacingResource.close();

	    System.out.println( "Closing file resource ... ");
	    fileResource.close();


	} catch( Exception e ) {
	    e.printStackTrace();
	}


    }

}