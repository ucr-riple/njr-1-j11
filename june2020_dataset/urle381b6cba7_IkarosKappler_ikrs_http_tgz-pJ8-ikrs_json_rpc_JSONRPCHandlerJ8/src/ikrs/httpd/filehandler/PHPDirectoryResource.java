package ikrs.httpd.filehandler; 

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock; 


import ikrs.httpd.CustomUtil;
import ikrs.httpd.DataFormatException;
import ikrs.httpd.FileHandler;
import ikrs.httpd.HeaderFormatException;
import ikrs.httpd.HTTPHeaders;
import ikrs.httpd.HTTPFileFilter;
import ikrs.httpd.HTTPHandler;
import ikrs.httpd.ReadOnlyException;
import ikrs.httpd.Resource;
import ikrs.httpd.UnsupportedFormatException;
import ikrs.httpd.resource.AbstractDirectoryResource;
import ikrs.util.CaseInsensitiveComparator;
import ikrs.util.CustomLogger;
import ikrs.util.MIMEType;

/**
 * This class is DEPRECATED and should not be used any more (security issues).
 *
 *
 * This class is a mixture of ikrs.http.resource.DirectoryResource and ikrs.http.FileHandler.
 *
 * Due to security reasons it is not recommended to use this class any more:
 *  (i)  You do not know what PHP code will really be executed ... system wide!
 *  (ii) A custom directory listing implementation might show htacess or htpasswd or any
 *       other sensitive data the the users (world wide!).
 *
 * And one more reason: PHP is not necesarily installed on all target systems.
 *
 * @author  Ikaros Kappler
 * @date    2012-10-23
 * @version 1.0.0
 *
 * @deprecated
 **/


public class PHPDirectoryResource
    extends AbstractDirectoryResource {

    /**
     * The output format (usually "TXT" or "HTML"). 
     * Later implementations might use subclassing to build different output types.
     **/
    private String outputFormat;
    

    /**
     * Create a new DefaultDirectoryResource.
     *
     * @param logger       A custom logger to write log messages to.
     * @param fileFilter   The file filter to use.
     * @param dir          The directory.
     * @param requestURI   The uri from the request (will be used to avoid printing the absolute file path).
     * @param format       The output format; currently "TXT" and "HTML" are supported (default is "TXT", if format is null). 
     * @param useFairLocks If set to true the created resource will use fair locks.
     **/
    public PHPDirectoryResource( HTTPHandler handler,
				 CustomLogger logger,
				 HTTPFileFilter fileFilter,
				 File dir,
				 URI requestURI,
				 UUID sid,
				 String format,
				 
				 boolean useFairLocks )
	throws NullPointerException {

	super( handler, logger, fileFilter, dir, requestURI, sid, useFairLocks );

	this.outputFormat             = format;
    }


    // --- BEGIN --------------------- AbstractDirectoryResource implementation ------------------------
    /**
     * This method is designated to build the data for the directory listing.
     *
     * Subclasses must implement this method and write the generated data into the given output stream.
     *
     *
     * @param sid The current session's ID.
     * @param out The output stream to write the list data to.
     * @throws IOException If any IO errors occur.
     **/
    public void generateDirectoryListing( UUID sid,
					  OutputStream out )
	throws IOException {

	
	FileHandler phpHandler = this.getHTTPHandler().getFileHandlerByExtension( ".php" );
	
	try {
	    // Create some dummy values

	    HTTPHeaders dummyHeaders = new HTTPHeaders();
	    //URI dummyURI             = new URI();
	    String newPath           = "/system/directory.listing.php";
	    String newQuery          = "requestURI=" + this.getRequestURI().getPath();
	    newQuery += "&absoluteDirectoryPath=" + this.getDirectoryFile().getAbsolutePath();
	    URI dummyURI = new URI( this.getRequestURI().getScheme(), 
				    this.getRequestURI().getAuthority(), // String authority, 
				    newPath, // this.getRequestURI().getPath(),      // String path, 
				    newQuery,   // String query, 
				    this.getRequestURI().getFragment()  
				    );
	    File directory = new File( this.getHTTPHandler().getDocumentRoot(), "system/directory.listing.php" );
	    System.out.println( dummyURI );
	    
	    Resource resource = phpHandler.process( sid,
						    dummyHeaders, // headers,
						    null, // postData,
						    directory, // this.getDirectoryFile(), //file,
						    dummyURI     //requestURI 
						    );
	    resource.open( true ); // Open in read-only mode
	    InputStream in = resource.getInputStream();

	    //int b;
	    //while( (b = in.read()) != -1 )
	    //System.out.println( (char)b );

	    long transferredBytes = CustomUtil.transfer( in, 
							 out, 
							 -1, // Long.MAX_VALUE,
							 256 // buffer size
							 );

	    resource.close();	    
	    
	    //System.out.println( "gak" );
	    //out.write( new String("This is a test.").getBytes() );
	    //out.flush();

	} catch( URISyntaxException e ) {

	    // This should not happen.
	    this.getLogger().log( Level.WARNING,
				  getClass().getName() + ".generateDirectoryListing(...)",
				  "[URISyntaxException] " + e.getMessage() 
				  );
	    throw new IOException( "Failed to generate PHP-based directory listing: " + e.getMessage() );
	} catch( HeaderFormatException e ) {

	    // This should not happen.
	    this.getLogger().log( Level.WARNING,
				  getClass().getName() + ".generateDirectoryListing(...)",
				  "[HeaderFormatException] " + e.getMessage() 
				  );
	    throw new IOException( "Failed to generate PHP-based directory listing: " + e.getMessage() );

	} catch( DataFormatException e ) {

	    // This should not happen.
	    this.getLogger().log( Level.WARNING,
				  getClass().getName() + ".generateDirectoryListing(...)",
				  "[DataormatException] " + e.getMessage() 
				  );
	    throw new IOException( "Failed to generate PHP-based directory listing: " + e.getMessage() );

	} catch( UnsupportedFormatException e ) {

	    // This should not happen.
	    this.getLogger().log( Level.WARNING,
				  getClass().getName() + ".generateDirectoryListing(...)",
				  "[UnsupportedFormatException] " + e.getMessage() 
				  );
	    throw new IOException( "Failed to generate PHP-based directory listing: " + e.getMessage() );
	    
	    }
    }


    /**
     * This method returns the Content-Type this class generates.
     *
     * The returned MIME type must not be null.
     *
     * @return The Content-Type this class generates.
     **/
    public MIMEType getDirectoryListingType() {
	if( this.isHTMLFormat() ) {
	    
	    // Hint: this generator creates XHTML (not HTML).
	    //       The MIME type 'text/html' would be acceptable for most browsers, but
	    //       the correct type is 'application/xhtml+xml'!
	    return new MIMEType( "application/xhtml+xml" ); 

	} else {

	    return new MIMEType( "text/plain" );

	}
    }
    // --- END ----------------------- AbstractDirectoryResource implementation ------------------------
    

    private boolean isHTMLFormat() {
	return this.outputFormat != null 
	    && this.outputFormat.equalsIgnoreCase("HTML");
    }

}
