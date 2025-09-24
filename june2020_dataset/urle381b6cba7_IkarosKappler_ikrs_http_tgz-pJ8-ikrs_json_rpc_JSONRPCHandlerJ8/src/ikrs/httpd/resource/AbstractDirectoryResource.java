package ikrs.httpd.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock; 


import ikrs.httpd.HTTPFileFilter;
import ikrs.httpd.HTTPHandler;
import ikrs.httpd.ReadOnlyException;
import ikrs.httpd.Resource;
import ikrs.util.CaseInsensitiveComparator;
import ikrs.util.CustomLogger;
import ikrs.util.MIMEType;

/**
 * @author   Ikaros Kappler
 * @date     2012-07-20
 * @modified 2012-10-11
 * @version  1.0.0
 **/

public abstract class AbstractDirectoryResource
    extends AbstractResource 
    implements DirectoryResource {

    /**
     * The actual directory (SHOULD be a directory but that's not guaranteed).
     **/
    private File dir;

    /**
     * The URI the directory file was requested through.
     **/
    private URI requestURI;


    /**
     * The current session's ID.
     **/
    private UUID sessionID;

    /**
     * The input stream that will be used as a buffer for the generated contents.
     **/
    private ByteArrayInputStream inputStream;

    /**
     * The length of the generated contents (will be set after content generation).
     **/
    private long length;

    /**
     * The date format to use.
     **/
    private DateFormat dateFormat;

    /**
     * The file filter to use.
     **/
    private HTTPFileFilter fileFilter;
    
    /**
     * Create a new AbstractDirectoryResource.
     *
     * @param logger       A custom logger to write log messages to.
     * @param fileFilter   The file filter to use.
     * @param dir          The directory.
     * @param requestURI   The uri from the request (will be used to avoid printing the absolute file path).
     * @param useFairLocks If set to true the created resource will use fair locks.
     **/
    public AbstractDirectoryResource( HTTPHandler handler,
				      CustomLogger logger,
				      HTTPFileFilter fileFilter,
				      File dir,
				      URI requestURI,
				      UUID sid,
				      boolean useFairLocks ) 
	throws NullPointerException {

	super( handler, logger, useFairLocks );

	if( dir == null )
	    throw new NullPointerException( "Cannot create AbstractDirectoryResource with null-directory." );
       
	if( requestURI == null )
	    throw new NullPointerException( "Cannot create AbstractDirectoryResource with null-URI." );

	this.fileFilter   = fileFilter;
	this.dir          = dir;
	this.requestURI   = requestURI;
	this.sessionID    = sid;

	this.dateFormat   = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    }


    // --- BEGIN --------------------- DirectoryResource pseudo implementation ------------------------
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
    public abstract void generateDirectoryListing( UUID sid,
						   OutputStream out )
	throws IOException;


    /**
     * This method returns the Content-Type this class generates.
     *
     * The returned MIME type must not be null.
     *
     * @return The Content-Type this class generates.
     **/
    public abstract MIMEType getDirectoryListingType();
    // --- END ----------------------- DirectoryResource pseudo implementation ------------------------

    
    /**
     * Get the actual directory to generate the listing for.
     * The returned file SHOULD be a directory but that's not guaranteed; so be prepared
     * for the case the returned file is a regular file, a link or anything else.
     *
     * Security notice: if the returned file is a link it MUST NOT be followed!
     *
     * @return The actual directory to generate the listing for.
     **/
    public File getDirectoryFile() {
	return this.dir;
    }

    /**
     * Get the URI the directory file was requested through.
     *
     * @return The URI the directory file was requested through.
     **/
    public URI getRequestURI() {
	return this.requestURI;
    }

    /**
     * Get the date format to use. The returned object is never null.
     *
     * @return The date format to use.
     **/
    public DateFormat getDateFormat() {
	return this.dateFormat;
    }

    /**
     * Get the file filter to use for directory listing.
     *
     * If the file filter's acceptLising(File)-method does not return true the passed
     * file MUST NOT be printed in the directory list.
     *
     * The returned file filter is never null.
     *
     * @return The file filter to use for directory listing.
     **/
    public HTTPFileFilter getFileFilter() {
	return this.fileFilter;
    }
    
 
    //---BEGIN------------------- AbstractResource implementation ----------------------------
    /**
     * This method determines if this resource was alerady opened or not.
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isOpen()
	throws IOException {

	return (this.inputStream != null);
	
    }


    /**
     * This method opens the underlying resource. Don't forget to close.
     *
     * @param readOnly if set to true, the resource will be opned in read-only mode.
     *
     * @throws ReadOnlyException If the underlying resource is read-only in general.
     * @throws IOException If any other IO error occurs.
     * @see isReadOnly()
     **/
    public void open( boolean readOnly )
	throws ReadOnlyException,
	       IOException {


	if( !readOnly )
	    throw new ReadOnlyException( "This DirectoryResource implementation only supports read-only access at the moment." );

	if( this.inputStream != null ) 	    
	    throw new IOException( "Cannot re-open DirectoryResource; it's already open." );

	if( !this.dir.isDirectory() ) 
	    throw new IOException( "Cannot open DirectoryResource; file '"+this.requestURI.getPath()+"' is not a directory." );


	
	// Print the directory listing into a buffer!
	getLogger().log( Level.INFO,
			 getClass().getName() + ".open(...)",
			 "Going to generate directory listing for '"+this.requestURI.getPath()+"'." );


	// Call the subclasses directory listing generator :)
	ByteArrayOutputStream out = new ByteArrayOutputStream( 1024 );
	this.generateDirectoryListing( this.sessionID, out );
	out.close();

	// Apply the MIME type (also specified in the subclass).
	this.getMetaData().setMIMEType( this.getDirectoryListingType() );
	


	// This initializes the input stream!
	byte[] rawData = out.toByteArray();
	this.inputStream = new ByteArrayInputStream( rawData );
	this.length = rawData.length;


	// Store the current modification date!
	this.getMetaData().setLastModified( new Date(System.currentTimeMillis()) );
	
    }

    /**
     * This method returns true if the underlying resource is read-only (in general).
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isReadOnly()
	throws IOException {
	
	// Byte-array resource might be writable in the future ...
	return true;
    }

    /**
     * This method returns the *actual* length of the underlying resource. This length will
     * be used in the HTTP header fields to specify the transaction length.
     *
     * During read-process (you used the locks, didn't you?) the length MUST NOT change.
     *
     * @return the length of the resource's data in bytes.
     * @throws IOException If any IO error occurs.
     **/
    public long getLength()
	throws IOException {

	if( this.inputStream == null )
	    throw new IOException( "Cannot determine the length of an un-opened resource." );

	return this.length;
    }


    /**
     * Get the output stream to this resource.
     *
     * @throws ReadOnlyException If this resource was opened with the read-only flag set.
     * @throws IOException If any other IO error occurs.
     **/
    public OutputStream getOutputStream()
	throws ReadOnlyException,
	       IOException {

	throw new ReadOnlyException( "This DirectoryResource implementation only supports read-only access at the moment." );

    }

    /**
     * Get the input stream from this resource.
     *
     * @throws IOException If any IO error occurs.
     **/
    public InputStream getInputStream()
	throws IOException {

	if( this.inputStream == null )
	    throw new IOException( "Cannot return an input stream from an un-opened resource." );

	return this.inputStream;
    }


    /**
     * Closes this resource.
     *
     * @return false if the resource was already closed, false otherwise.
     **/
    public boolean close()
	throws IOException {

	if( this.inputStream == null )
	    return false;
	
	this.inputStream = null;
	
	return true;
    }
    //---END--------------------- AbstractResource implementation ----------------------------
    

}
