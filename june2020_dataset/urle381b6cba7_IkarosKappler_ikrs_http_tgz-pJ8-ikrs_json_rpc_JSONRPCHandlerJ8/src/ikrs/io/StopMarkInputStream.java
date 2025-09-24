package ikrs.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import ikrs.io.CircularFIFO;


/**
 * The StopMarkInputStream is an input stream wrapper class that reads bytes from the
 * underlying inputstream until a given stop-mark (byte sequence) is detected or EOF
 * is reached.
 *
 * Closing the StopMarkInputStream will have no effect to the underlying stream.
 *
 *
 * @authior Ikaros Kappler
 * @date 2012-10-08
 * @modified 2013-04-10 Ikaros Kappler (stopMarkReached() issue fixed).
 * @version 1.0.0
 **/


public class StopMarkInputStream 
    extends InputStream {

    /**
     * The underlying input stream.
     **/
    private InputStream in;

    /**
     * The stop mark sequence.
     **/
    private byte[] stopMark;
    
    /**
     * A buffer to store temp. consumed bytes from the underlying input stream
     * during the matching process (will store up to stopMark.length bytes, not more!).
     **/
    private CircularFIFO buffer;


    /**
     * This flag will be set as soon a the stopMark was fully read. This input stream has
     * reached 'EOF' then.
     **/
    private boolean stopMarkReached = false;

    /**
     * This flag will be set when the close() method is called. The underlying input stream
     * remains unaffected by that method.
     **/
    private boolean isClosed = false;


    /**
     * This flag will be set the first time the underlying input stream reads -1 (EOF).
     **/
    private boolean eoiReached = false;

    /**
     * Constructs a new StopMarkInputStream with the given underlying stream and stop mark.
     * 
     * @param in         The input stream to read from (must not be null).
     * @param stopMark   The stop mark byte sequence (must not be null but may be empty).
     **/
    public StopMarkInputStream( InputStream in,
				byte[] stopMark )
	throws NullPointerException,
	       IllegalArgumentException {

	super();

	if( in == null )
	    throw new NullPointerException( "Cannot create a StopMarkInputStream with a null-stream." );
	if( stopMark == null )
	    throw new NullPointerException( "Cannot create a StopMarkInputStream with a null-mark." );


	this.in        = in;
	this.stopMark  = stopMark;

	// Allocate 'stopMark.length' bytes to ensure the WHOLE stop mark can be read at ANY time.
	this.buffer    = new CircularFIFO( stopMark.length );
    }

    // For testing only!
    //private void reopen() {
    //	this.eoiReached = false;
    //}

    /**
     * The continueStream() method resets the stream once the stop mark was reached.
     * This means that the current buffer will be cleared, the stop mark bytes are 
     * skipped and more bytes can be read from the underlying read (unless EOI was 
     * reached).
     *
     * Actually this method is meant for the use of nested StopMarkInputStreams;
     * once the underlying stream reached its stop mark (equals EOI) the stream
     * can be reset for reading more bytes.
     *
     * Note: this method just calls the protected method continueStream( true ).
     *
     * @return true if - and only if - EOI was not yet reached. Hint: true does not
     *         mean that EOI will not be reached on the next read() call.
     **/
    public boolean continueStream() {
	return this.continueStream( false );
    }

    /**
     * The continueStream() method resets the stream once the stop mark was reached.
     * This means that the current buffer will be cleared, the stop mark bytes are 
     * skipped and more bytes can be read from the underlying read.
     * Unless EOI was reached - OR overrideEOI is set to true.
     *
     * Actually this method is meant for the use of nested StopMarkInputStreams;
     * once the underlying stream reached its stop mark (equals EOI) the stream
     * can be reset for reading more bytes.
     *
     * @param overrideEOI If set to true the next read() call will try to read one
     *        more byte from the underlying stream.
     * @return true if EOI was not yet reached OR overrideOI is set to true. 
     *         Hint: true does not mean that EOI will not be reached on the next 
     *         read() call.
     **/
    protected boolean continueStream( boolean overrideEOI ) {
	if( this.isClosed )
	    return false;

	if( overrideEOI )
	    this.eoiReached = false;
	else if( this.eoiReached )
	    return false;
	if( !this.stopMarkReached )
	    return true;
	
	this.stopMarkReached = false;
	this.eoiReached      = false;
	//if( this.stopMarkReached )
	this.buffer.clear();
	return true;
    }

    /**
     * This method just tells if this input stream was already closed..
     **/
    public boolean isClosed() {
	return this.isClosed;
    }

    /**
     * This method just tells if this input stream already reached the stop mark; reaching the stop mark
     * implies reaching EOF.
     **/
    public boolean stopMarkReached() {
	return this.stopMarkReached;
    }

    
    /**
     * This method just tells if the underlying input stream reached EOI.
     **/
    public boolean eoiReached() {
	return this.eoiReached;
    }



    /**
     * This method checks if the stop mark is reached. During this process up to
     * 'stopMark.length' bytes will be stored into the buffer.
     **/
    private boolean check_stopMarkReached() 
	throws IOException {

	if( this.stopMarkReached || this.isClosed || this.eoiReached )
	    return stopMarkReached;

	
	int i = 0;
	while( i < this.stopMark.length
	       && tryBufferCompare(i)
	       && this.buffer.length() > 0 ) {

	    i++;
	}

	if( i >= this.stopMark.length ) {
	    this.stopMarkReached = true;
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * This method tries to compare the next byte (at the given position inside the stopMark)
     * in the buffer with the stopMark byte itself.
     * If the buffer is currently empty it will read up to n bytes into the buffer to make
     * comparison possible (where n is 'stopMark.length').
     *
     * The method returns true if (and only if) the whole stop mark could be read from the current
     * buffer- position.
     *
     * @param stopMarkPosition A comparison index inside the stopMark: 0 <= stopMarkPosition < stopMark.length.
     **/
    private boolean tryBufferCompare( int stopMarkPosition )
	throws IOException {

	if( this.stopMarkReached || this.isClosed || this.eoiReached )
	    return this.stopMarkReached;

	// ---end-of-recursion---
	if( stopMarkPosition >= this.stopMark.length ) {

	    // This SHOULD NOT happen.
	    return false;

	}


	if( stopMarkPosition < this.buffer.length() ) {
	    
	    // There is enought data in the buffer to compare in-buffer
	    return ( this.buffer.peek(stopMarkPosition) == this.stopMark[stopMarkPosition] );


	} else {

	    // Read one more byte into buffer (if possible), then compare
	    int b = this.in.read();
	    if( b == -1 ) {

		// EOI reached. No comparison possible.
		this.eoiReached = true;
		return false;

	    } else {
	     
		// There is at least one next byte available.
		// Read it, store into buffer and retry comparison.
		this.buffer.write( (byte)(b & 0xFF) );

		// ---recursive-call---
		return tryBufferCompare( stopMarkPosition );

	    }

	}

    }

    //--- BEGIN -------------------- Override InputStream --------------------
    public int available() 
	throws IOException {
	
	if( this.in.available() <= 0 ) {

	    return 0;

	} else {

	    if( this.check_stopMarkReached() ) return 0;
	    else                               return 1;

	}
    }

    public void close() 
	throws IOException {
	
	this.isClosed = true;
	
	// Do NOT forward call to the underlying input stream.
	// Closing given (!) streams is not our concern.
    }

    public void mark( int readlimit ) {
	// NOOP (mark not supported)
    }

    public boolean markSupported() {

	return false;
    }

    public int read() 
	throws IOException {

	
	if( this.isClosed() )
	    throw new IOException( "The StopMarkInputStream was already closed." );

	

	if( this.check_stopMarkReached() ) {
	    //this.stopMarkReached = true;
	    return -1;
	}
	
	if( this.buffer.length() == 0 )
	    return -1; // EOF
	else {
	    
	    // WARNING: the buffer reads a BYTE in [-128 ... 127]!
	    //          But we need an INT in [0 ... 255], otherwise the reader cannot 
	    //          separate the byte value -1 [byte] from EOF -1 [int].
	    byte b = this.buffer.read();
	    return (b & 0xFF);

	}
    }

    /*
    public int read(byte[] b) 
	throws IOException {

	// Do not override
	return super.read( b  );
    }

    
    public int read(byte[] b, int off, int len) 
	throws IOException {

	// Do not override
	return super.read( b, off, len );
    }
    */

    public void reset() 
	throws IOException {
	
	throw new IOException( "The StopMarkInputStream does not supported the reset() method." );

    }

    public long skip( long n ) 
	throws IOException {

	if( this.isClosed )
	    throw new IOException( "This stream was already closed. Cannot skip input." );

	long skipped = this.in.skip( n );

	// Buffer data becomes invalid when skipping!
	this.buffer.clear();

	return skipped;
    }
    //--- END -------------------- Override InputStream --------------------


    public String toString() {
	return "StopMarkInputStream=[ stopMark=\"" + new String(this.stopMark) + "\", coreStream=" + this.in + "]";
    }
    


    public static void main( String[] argv  ) {

	try {

	    String stopMark = "----WebKitFormBoundaryeANQMKoBwsmwQrYZ";
	    System.out.println( "Using stopMark: " + stopMark  );
	    System.out.println( "Reading file until stopMark will be found ... ");
	    java.io.FileInputStream fin = new java.io.FileInputStream( new java.io.File( "document_root_alpha/example.POST_DATA.txt") );
	    StopMarkInputStream in = new StopMarkInputStream( fin,
							      stopMark.getBytes(java.nio.charset.StandardCharsets.UTF_8.name()) );

	    
	    int b;
	    System.out.println( "Reading bytes ..." );
	    int len = 0;
	    while( (b = in.read()) != -1 ) {

		System.out.print( (char)b );
		len++;

	    }

	    System.out.println( "" );

	    in.close();

	    System.out.println( "\n\n\nDONE.\n" );
	    System.out.println( "Cosuming trailing bytes (after the stopMark)... ");

	    while( (b = fin.read()) != -1 )
		System.out.print( (char)b );

	} catch( IOException e ) {

	    e.printStackTrace();

	}

    }
}