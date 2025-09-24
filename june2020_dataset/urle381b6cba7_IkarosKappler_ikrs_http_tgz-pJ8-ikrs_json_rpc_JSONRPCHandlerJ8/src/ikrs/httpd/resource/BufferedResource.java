package ikrs.httpd.resource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import ikrs.httpd.HTTPHandler;
import ikrs.httpd.ReadOnlyException;
import ikrs.util.CustomLogger;


/**
 * There are cases when it is not predictable how many bytes a data source will produce until
 * the input stream has reached EOF.
 * But for HTTP resources it is not acceptable to return an unprecise or aproximate value for
 * Resource.getLength() because this value will be stored in the HTTP resonse's header data
 * for the 'Content-Length' field (as soon as the data output started the header field was 
 * sent and cannot be modified any more)!
 *
 * For those unpredictable cases we need buffered resources that load the _complete_ data into
 * an internal buffer to gain the exact number of bytes.
 *
 * Handle with care! Some underlying resources might hold a large number of bytes which might
 * cause out-of-memory errors or stack overflows when this BufferedResource tries to allocate
 * enought memory to store the data.
 
 *
 * @author Ikaros Kappler
 * @date 2012-09-29
 * @version 1.0.0
 **/


public class BufferedResource 
    extends AbstractResource {

    /**
     * The passed input stream (must not be null).
     **/
    private InputStream in;

    /**
     * This will be the underlying resource holding all my bytes :)
     **/
    private ByteArrayResource byteArrayResource;
    


    /**
     * Create a new BufferedResource.
     *
     * @param handler      The HTTPHandler (may be null if not available).
     * @param logger       A custom logger to write log messages to (must not be null).
     * @param in           The input stream to read the data for buffering from.
     * @param useFairLocks If set to true the class will use fair read locks (writing isn't
     *                     possible at all with this class).
     * @throws NullPointerException If logger or the input stream is null.
     **/
    public BufferedResource( HTTPHandler handler,
			     CustomLogger logger,
			     InputStream in,
			     boolean useFairLocks ) 
	throws NullPointerException {

	super( handler, logger, useFairLocks );


	if( in == null )
	    throw new NullPointerException( "Cannot create BufferedResources from null-input-stream)." );

	this.in = in;
	
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

	// Resource cann be double-opened!
	if( this.isOpen() ) 
	    throw new IOException( "Processable resources cannot be double opened." );

	if( !readOnly )
	    throw new ReadOnlyException( "This BufferedResource implementation only supports read-only access." );

	
	// Read the complete data from the input stream into the byte buffer
	ByteArrayOutputStream byteOut = new ByteArrayOutputStream( 1024 );  // add initial buffer size as constructor param?
	byte[] buffer = new byte[ 1024 ]; // Use different buffer sizes?
	int length;


	// Note: InputStream.read(byte[]) returns -1 as soon as EOF is reached.
	while( (length = this.in.read(buffer)) > 0 ) {

	    //for( int i = 0; i < length; i++ )
	    //System.out.print( (char)buffer[i] );
	    
	    // Push data into ByteArrayOutputStream
	    byteOut.write( buffer, 0, length );

	}

	// Finally 'convert' the ByteOutputStream into a ByteArrayResource
	this.byteArrayResource = new ByteArrayResource( this.getHTTPHandler(),
							this.getLogger(), 
							byteOut.toByteArray(),
							false    // It is not necessary to use fair locks here because this wrapped Resource is private
							);
	byteOut.close();

	// Don't forget the underlying Resource, too!
	this.byteArrayResource.open( true );  // Open in readOnly mode (the whole implementation is read-only)

	// Done. Do NOT close input stream (that is the task of the instance which opened it!)

    }

    /**
     * This method determines if this resource was alerady opened or not.
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isOpen()
	throws IOException {

	return ( this.byteArrayResource != null 
		 && this.byteArrayResource.isOpen()
		 );
    }


    /**
     * This method returns true if the underlying resource is read-only (in general).
     *
     * @throws IOException If any IO error occurs.
     **/
    public boolean isReadOnly()
	throws IOException {

	// Processable resources are always read-only.
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

	return this.byteArrayResource.getLength();
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

	throw new ReadOnlyException( "Buffered resources are read-only. They have no output stream!" );
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

	return this.byteArrayResource.getInputStream();
    }


    /**
     * Closes this resource.
     *
     * @return false if the resource was already closed, true otherwise.
     **/
    public boolean close()
	throws IOException {

	if( this.byteArrayResource == null || !this.byteArrayResource.isOpen() )
	    return false;  // Not yet opened or already closed (empty operation)


	this.byteArrayResource.close();
	// Do not set to null!
	// (this would indicate 'not yet opened', but the inputstream's data was already consumed!)

	return true;
    }
    //--- END ------------------------------ AbstractResource implementation ------------------------------

}