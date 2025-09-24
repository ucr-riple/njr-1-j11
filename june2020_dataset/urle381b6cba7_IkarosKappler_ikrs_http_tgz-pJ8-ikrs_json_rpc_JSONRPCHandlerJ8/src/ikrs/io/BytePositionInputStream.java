package ikrs.io;

/**
 * At some points couting input streams are needed.
 * This input stream counts the bytes during reading.
 *
 * The implementation is just a wrapper for an existing input stream, re-implementing the
 * InputStream-methods and counting the read bytes, were required.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-29
 * @version 1.0.0
 **/

import java.io.InputStream;
import java.io.IOException;

public class BytePositionInputStream
    extends InputStream {

    /**
     * The actual underlying InputStream to read from.
     **/
    private InputStream in;

    /**
     * The current byte position in the input stream.
     **/
    private long bytePosition;

    /**
     * This field stores the absolute byte position. It cannot be reset.
     **/
    private long absoluteBytePosition;



    public BytePositionInputStream( InputStream in ) 
	throws NullPointerException {

	super();

	if( in == null )
	    throw new NullPointerException( "Cannot create BytePositionInputStreams from null-streams." );

	this.in = in;
	this.bytePosition = 0L;
	this.absoluteBytePosition = 0L;
    }

    /**
     * This method returns the number of bytes that were already read (unless the 'resetBytePosition()'
     * method was called).
     *
     * @return The number of bytes that were already read.
     **/
    public long getBytePosition() {
	return this.bytePosition;
    }

    /**
     * This method resets the current byte position to 0.
     *
     * @retutn The number of bytes that were already read (since construction or last position reset).
     **/
    public long resetBytePosition() {
	long tmp = this.bytePosition;
	this.bytePosition = 0L;
	return tmp;
    }

    /**
     * This method returns the absolute number of bytes that were already read. The value is not affected
     * by any 'resetBytePosition()' calls.
     *
     * @return The absolute (!) number of bytes that were already read.
     **/
    public long getAbsoluteBytePosition() {
	return this.absoluteBytePosition;
    }

    //--- BEGIN ------------------------- Override Inputstream ------------------------
    public synchronized int available() 
	throws IOException {

	return this.in.available();
    }

    public synchronized void close() 
	throws IOException {
	this.in.close();
    }

    /**
     * The mark-method is a bit tricky! This implementation does NOT support marks and
     * throws an IOException if called.
     **/
    public void mark( int readlimit ) {
	
	// IF you want to implement it, don't forget to change the markSupported() method and the reset() method!
	//throw new IOException( "The class '" + getClass().getName() +"' does not support the mark-method yet. Feel free to implement it." );
	
    }

    public boolean markSupported() {
	return false;	
    }
    
    public synchronized int read() 
	throws IOException {


	int b = this.in.read();
	if( b != -1 ) {
	    
	    this.bytePosition++;  // EOF not yet reached
	    this.absoluteBytePosition++;

	}
	
	return b;
    }

    public synchronized int read(byte[] b) 
	throws IOException {

	int count = this.in.read(b);
	this.bytePosition += count;
	this.absoluteBytePosition += count;
	return count;
    }

    public synchronized int read(byte[] b, int off, int len)
	throws IOException {

	int count = this.in.read( b, off, len );
	this.bytePosition += count;
	this.absoluteBytePosition += count;
	return count;
    }

    public void reset()
	throws IOException {

	throw new IOException( "The class '" + getClass().getName() +"' does not support the mark- nor the reset-method yet. Feel free to implement them." );

    }

    public synchronized long skip( long n ) 
	throws IOException {

	long count = this.in.skip( n );
	this.bytePosition += count;
	this.absoluteBytePosition += count;
	return count;
    }
    //--- BEGIN ------------------------- Override Inputstream ------------------------


    public String toString() {
	return "BytePositionInputStream=[ bytePosition=" + this.bytePosition + ", absoluteBytePosition=" + this.absoluteBytePosition + "]";
    }

}