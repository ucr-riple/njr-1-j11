package ikrs.httpd.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock; 


import ikrs.httpd.HTTPHandler;
import ikrs.httpd.ReadOnlyException;
import ikrs.httpd.Resource;

import ikrs.util.CustomLogger;
import ikrs.util.MIMEType;

/**
 * @author  Ikaros Kappler
 * @date    2012-07-20
 * @version 1.0.0
 **/


public class ByteArrayResource
    extends AbstractResource
    implements Resource {

    
    private byte[] data;
    private int offset;
    private int length;
    private ByteArrayInputStream inputStream;
    
    /**
     * Create a new ByteArrayResource.
     **/
    public ByteArrayResource( HTTPHandler handler,
			      CustomLogger logger,
			      byte[] data,
			      boolean useFairLocks ) 
	throws NullPointerException {
	
	this( handler, logger, data, 0, data.length, useFairLocks );
    }

    /**
     * Create a new ByteArrayResource.
     **/
    public ByteArrayResource( HTTPHandler handler,
			      CustomLogger logger,
			      byte[] data,
			      int offset,
			      int length,
			      boolean useFairLocks ) 
    throws NullPointerException {

	super( handler, logger, useFairLocks );

	if( data == null )
	    throw new NullPointerException( "Cannot create ByteArrayResource from null-array." );

	this.data = data;
	this.offset = offset;
	this.length = length;
	//this.inputStream = new ByteArrayInputStream( data, offset, length );

	
	this.getMetaData().setMIMEType( new MIMEType("application/octet-stream") );
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
	    throw new ReadOnlyException( "This ByteArrayResource implementation only supports read-only access at the moment." );

	if( this.inputStream != null ) 	    
	    throw new IOException( "Cannot re-open ByteArrayResource; it's already open." );


	// This initializes the input stream!
	this.inputStream = new ByteArrayInputStream( this.data, this.offset, this.length );
	
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

	throw new ReadOnlyException( "This ByteArrayResource implementation only supports read-only access at the moment." );

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
