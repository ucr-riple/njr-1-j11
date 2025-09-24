package ikrs.httpd.resource;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock; 

import ikrs.httpd.ContentRange;
import ikrs.httpd.HTTPHandler;
import ikrs.httpd.ReadOnlyException;
import ikrs.httpd.Resource;
import ikrs.httpd.ResourceMetaData;
import ikrs.util.CustomLogger;

import ikrs.io.ReadLimitInputStream;
import ikrs.io.fileio.htaccess.HypertextAccessFile;

/**
 * The RangedResource class is a wrapper class that allows to 'cut off' some
 * bytes at the beginning and/or at the end of an existing resource.
 *
 * The amount of bytes to be dropped is specified by a ContentRange instance
 * passed to the constructor.
 *
 * Actually the RangedResource uses a custom getInputStream() replacement
 * with a nested ReadLimit- (to drop trailing bytes) and BytePosition-InputStream 
 * (to drop leading bytes).
 *
 *
 * @author Ikaros Kappler
 * @date 2013-02-27
 * @version 1.0.0
 **/


public class RangedResource
    extends ResourceDelegation {

    private ContentRange contentRange;

    private ReadLimitInputStream readLimitInputStream;

    public RangedResource( Resource resource,
			   ContentRange range,
			   
			   HTTPHandler handler,
			   CustomLogger logger ) 
	throws NullPointerException,
	       IndexOutOfBoundsException {

	super( resource,
	       
	       handler,
	       logger
	       );

	if( range == null )
	    throw new NullPointerException( "Cannot create a RangedResource with null-ContentRange." );

	this.contentRange            = range;
	this.readLimitInputStream    = null;         // will be initialized on open(...)
    }


    //--- BEGIN --------------------- Override ResourceDelegation -------------------------
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
	throws IOException {

	// Open the core resource first!
	// (otherwise we will not be able to access the input stream)
	super.open( readOnly );
      
	// No need to store the dat of last modification:
	//  both resources share the same meta data instance.
    }

    /**
     * Get the input stream from this resource.
     *
     * @throws IOException If any IO error occurs.
     **/
    public InputStream getInputStream()
	throws IOException {

	if( !this.isOpen() )
	    throw new IOException( "Cannot retrieve resource's input stream. Resouce was not yet opened." );

	if( this.readLimitInputStream == null ) {

	    if( this.contentRange.getLastBytePosition() >= this.getCoreResource().getLength() )
		throw new IOException( "Failed to initialize the RangedResource's input stream; the passed Content-Range " + this.contentRange.toString() + " is out of bounds!" );

	    // Init the stream(s)!
	    InputStream tmpIn = this.getCoreResource().getInputStream();
	    if( this.contentRange.getFirstBytePosition() > 0 ) {
		
		// Skip n bytes at the beginning
		tmpIn.skip( this.contentRange.getFirstBytePosition() );

	    }

	    this.readLimitInputStream = new ReadLimitInputStream( tmpIn,
								  this.contentRange.calculateLength()
								  );

	}



	return this.readLimitInputStream;
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

	// The new length is now specified by the passed content range!
	return this.contentRange.calculateLength();
    }

    //--- END ----------------------- Override ResourceDelegation -------------------------


    /**
     * This method is for testing only.
     **/
    public static void main( String[] argv ) {

	try {

	    System.out.println( "Creating file and content range ..." );
	    java.io.File file = new java.io.File( "document_root/README.txt" );
	    ContentRange range = new ContentRange( ContentRange.NAME_BYTESUNIT_BYTES,
						   7L,   // skip the first 8 bytes ... (begin at index 7)
						   15L,  // ... and read up to 8 bytes from the resource (end at index 15).
						   -1    // unknown resource size
						   );
					     
	    System.out.println( "Creating RangedResource ...." );
	    Resource resource = new RangedResource( new FileResource( null,   // no handler available here
								      null,   // and also no logger
								      file,   // the input file
								      false   // no need to use fair locks here
								      ),
						    range,
						    null,     // no handler, too
						    null      // and no logger, too.
						    );

	    // Try to read the 16 bytes from the resource
	    System.out.println( "Opening resource ..." );
	    resource.open( true ); // open in read-only mode
	    

	    System.out.println( "Try to read the " + range.calculateLength() + " bytes from the resource ... ");
	    byte[] buffer = new byte[ 8 ];
	    int len;
	    int readLength = 0;
	    while( (len = resource.getInputStream().read(buffer,0,buffer.length)) != -1 ) {

		System.out.println( "\nread " + len + " more byte(s)" );

		for( int i = 0; i < len; i++ )
		    System.out.print( (char)buffer[i] );

		readLength += len;
	    }
	    System.out.println( "" );
	    System.out.println( "" + readLength + " byte(s) read." );
	    

	    System.out.println( "Closing resource ... ");
	    resource.close();


	} catch( Exception e ) {

	    e.printStackTrace();

	}

    }

}