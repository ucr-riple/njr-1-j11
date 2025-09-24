package ikrs.httpd.resource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Level;

import ikrs.httpd.HTTPHandler;
import ikrs.httpd.ReadOnlyException;
import ikrs.httpd.Resource;
import ikrs.io.BytePositionInputStream;
import ikrs.util.CustomLogger;

/**
 * The interruptable resource does not refer to Thread interruption, but to a Resource that allows to
 * stop the read process at a random point, then resuming it (after the last read byte) and telling
 * the observer the read process would be at byte position 0 (adjusting the old length value, off course).
 *
 * It can be used to read a portion of data from the beginning of the underlying input stream (such as
 * header data), then stopping and - after processing the received data - passing the resource to a different
 * instance telling the byte position would be still at the beginning of the stream.
 *
 * This should be used to avoid copying the rest of the stream into a new buffer resource, but continue
 * reading at the current position.
 *
 * The method for resetting the stream index is the 'resetBytePosition()' method.
 *
 * Note that this class uses the ikrs.io.BytePositionInputStream, which does not support the 'mark()' and
 * 'reset()' methods yet.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-29
 * @version 1.0.0
 **/



public class InterruptableResource 
    extends AbstractResource {

    /**
     * This is the underlying resource.
     **/
    private Resource coreResource;

    /**
     * This is the input stream wrapper used to keep track of the read-position.
     **/
    private BytePositionInputStream bytePositionInputStream;
    


    /**
     * Create a new InterruptableResource from the given Resource.
     *
     * @param logger       A custom logger to write log messages to (must not be null).
     * @param pb           The process builder to use (must not be null).
     * @param useFairLocks If set to true the class will use fair read locks (writing isn't
     *                     possible at all with this class).
     * @throws NullPointerException If logger or pb is null.
     **/
    public InterruptableResource( HTTPHandler handler,
				  CustomLogger logger,
				  Resource resource,
				  boolean useFairLocks ) 
	throws NullPointerException {

	super( handler, logger, useFairLocks );


	if( resource == null )
	    throw new NullPointerException( "Cannot create InterruptableResources from null-resources." );

	this.coreResource = resource;
	
    }

    /**
     * Get the current read position (since initialization or last 'resetBytePosition()' call).
     *
     * @return The current read position (since initialization or last 'resetBytePosition()' call).
     **/
    public long getBytePosition() {
	if( this.bytePositionInputStream == null ) {
	    
	    this.getLogger().log( Level.WARNING,
				  getClass().getName() + ".getBytePosition()",
				  "The getBytePosition() method cannot return any valid position unless the open() method was called! (returning -1)." );
	    return -1L;
	}

	return this.bytePositionInputStream.getBytePosition();
    }

    /**
     * Resets the current byte position to 0.
     *
     * @returnt The old byte position (before reset).
     **/
    public long resetBytePosition() {
	if( this.bytePositionInputStream == null ) {

	    this.getLogger().log( Level.WARNING,
				  getClass().getName() + ".getBytePosition()",
				  "The resetBytePosition() method cannot reset any valid position unless the open() method was called! (returning -1)." );
	    return -1L;
	}

	/*long bytes = this.bytePositionInputStream.resetBytePosition();
	System.out.println( "Data after resetting the byte position ..." );
	int b;
	try {
	    while( (b = this.bytePositionInputStream.read()) != -1 )
		System.out.print( (char)b );
	} catch( IOException e ) { e.printStackTrace(); }
	return bytes;
	*/

	return this.bytePositionInputStream.resetBytePosition();
    }
    

    //--- BEGIN ---------------------------- AbstractResource implementation ------------------------------
    /**
     * This method opens the underlying resource. Don't forget to close.
     *
     * @param readOnly if set to true, the resource will be opened in read-only mode.
     *
     * @throws ReadOnlyException If the underlying resource is read-only in general.
     * @throws IOException If any other IO error occurs.
     * @see isReadOnly()
     **/
    public void open( boolean readOnly )
	throws ReadOnlyException,
	       IOException {

	// As this is an INTERRUPTBALE resource, multiple open() calls are definitely allowed :)
	if( this.isOpen() )
	    return;  

	if( !readOnly )
	    throw new IOException( "The InterruptableResource implementation only supports the read-only mode." );
	
	this.coreResource.open( readOnly );
	this.bytePositionInputStream = new BytePositionInputStream( this.coreResource.getInputStream() );

	// Store last modified date
	this.getMetaData().setLastModified( this.coreResource.getMetaData().getLastModified() );
    }

    /**
     * This method determines if this resource was alerady opened or not.
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isOpen()
	throws IOException {

	return ( this.bytePositionInputStream != null 
		 && this.coreResource.isOpen() );
    }


    /**
     * This method returns true if the underlying resource is read-only (in general).
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isReadOnly()
	throws IOException {

	// The byte position input stream does NOTHING know about write processes, so this
	// resource can only be readable.
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

	if( !this.isOpen() )
	    throw new IOException( "You have to call the InterruptableResource.open(boolean) method before performing getLength()." );
	
	return (this.coreResource.getLength() - this.bytePositionInputStream.getAbsoluteBytePosition());
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

	throw new ReadOnlyException( "The InterruptableResource implementation only supports the read-only mode." );
    }

    /**
     * Get the input stream from this resource.
     *
     * @throws IOException If any IO error occurs.
     **/
    public BytePositionInputStream getInputStream()
	throws IOException {

	if( !this.isOpen() )
	    throw new IOException( "Cannot retrieve resource's input stream. Resouce was not yet opened." );

	return this.bytePositionInputStream;
    }


    /**
     * Closes this resource.
     *
     * @return false if the resource was already closed, true otherwise.
     **/
    public boolean close()
	throws IOException {

	if( this.bytePositionInputStream == null || !this.coreResource.isOpen() )
	    return false;  // Not yet opened or already closed (empty operation)


	// Note: there MUST be the possibility really to close the resource. So this method really closes the resource :)
	this.coreResource.close();
	// Do not set to null!
	// (this would indicate 'not yet opened', but the inputstream's data was already consumed!)

	// It is not clear how the resource backs up to the inputstream; close it with care and expect errors (that should
	// be ignored --- closed is closed).
	try {
	    
	    // This might throw an IOException, because the underlying resource was just closed, too.
	    // (this might be more secure, because other instance might think the byteArrayInputStream is
	    //  sill open).
	    this.bytePositionInputStream.close();

	} catch( IOException e ) {
	    // Ignore
	}

	return true;
    }
    //--- END ------------------------------ AbstractResource implementation ------------------------------

}