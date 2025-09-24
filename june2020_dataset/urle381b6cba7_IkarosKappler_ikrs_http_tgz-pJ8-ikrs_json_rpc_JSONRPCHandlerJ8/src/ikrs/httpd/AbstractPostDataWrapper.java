package ikrs.httpd;

import java.io.InputStream;
import java.io.IOException;
// import java.text.ParseException;

import ikrs.httpd.datatype.FormData;
import ikrs.io.BytePositionInputStream;
import ikrs.util.CustomLogger;


/**
 * @author Ikaros Kappler
 * @date 2012-10-02
 * @version 1.0.0
 **/


public abstract class AbstractPostDataWrapper
   implements PostDataWrapper {


    /**
     * A custom logger to write log messages to.
     **/
    private CustomLogger logger;

    /**
     * The HTTP headers referring to the data to come from the input stream.
     **/
    private HTTPHeaders headers;

    /**
     * The input stream to read the post data from --- wrapped into a byte position inputstream :)
     **/
    private BytePositionInputStream bytePositionInputStream;



    /**
     * Creates a new AbstractPostDataWrapper.
     *
     * @param logger  A custom logger to write log messages to.
     * @param headers The HTTPHeaders (usually read from the input stream before; must not be null).
     * @param in      The input stream to read the post data from (must not be null).
     * @throws NullPointerException If headers or in is null.
     **/
    public AbstractPostDataWrapper( CustomLogger logger,
				    HTTPHeaders headers,
				    InputStream in ) 
	throws NullPointerException {

	super();


	if( headers == null )
	    throw new NullPointerException( "Cannot create post data wrappers with null-headers." );
	if( in == null )
	    throw new NullPointerException( "Cannot create post data wrappers with null-inputstream." );


	this.logger                   = logger;
	this.headers                  = headers;
	this.bytePositionInputStream  = new BytePositionInputStream( in );
    }    


    public CustomLogger getLogger() {
	return this.logger;
    }
    
    //--- BEGIN ---------------------------- PostDataWrapper implementation ----------------------
    /**
     * Get the HTTP headers assiciated with this post data.
     * The returned headers are never null (but may be empty).
     * 
     * @return The HTTP headers assiciated with this post data.
     **/
    public HTTPHeaders getRequestHeaders() {
	return this.headers;
    }

    /**
     * Get the post data's designated content length. 
     * If the content length is unknown or not available the method
     * should return -1;
     *
     * @return The content length (in bytes) or -1 if the lenght is
     *         unknown.
     **/
    public long getContentLength() {
	
	// Just fetch the Content-Length from the header fields.
	return this.headers.getLongValue( HTTPHeaders.NAME_CONTENT_LENGTH );
    }

    /**
     * Get the input stream associated with this post data.
     * Use it to read the data you are interested in; the returned stream
     * is never null.
     *
     * @return The input stream to read the actual post data from.
     **/
    public InputStream getInputStream() {
	return this.bytePositionInputStream;
    }

    /**
     * Get the current read position. 
     * The input stream is backed to a BytePositionInputStream that keeps track
     * of the read position inside the stream. 
     * The position is measured from the first byte of post data (header data 
     * not included).
     *
     * @return The current read position of the post data input stream.
     **/
    public long getBytePosition() {
	return this.bytePositionInputStream.getBytePosition();
    }

    /**
     * ??? ... !!!
     **/
    public abstract FormData readFormData()
	throws IOException,
	       HeaderFormatException,
	       DataFormatException,
	       UnsupportedFormatException;
    //--- END ------------------------------ PostDataWrapper implementation ----------------------

}