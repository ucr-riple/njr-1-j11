package ikrs.io;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;


/**
 * The CircularFIFO implements an ... ehm ... yes, a circular FIFO buffer.
 *
 * @authior Ikaros Kappler
 * @date 2012-10-08
 * @version 1.0.0
 **/


public class CircularFIFO {


    /**
     * The actual buffer data.
     **/
    private byte[] buffer;

    /**
     * The current read position [write position is at (front + length) % capacity].
     **/
    private int front;
    
    /**
     * The current buffer data length.
     **/
    private int length;

    /**
     * Creates a new circular FIFO buffer.
     *
     * @param capacity The buffer's capacity (means: up to 'size' bytes can be stored in this buffer).
     **/
    public CircularFIFO( int capacity )
	throws IllegalArgumentException {

	super();

	if( capacity <= 0 )
	    throw new IllegalArgumentException( "Cannot create a circular FIFO buffer with a buffer capacity <= 0 ("+capacity+")." );


	this.buffer = new byte[ capacity ];
    }

    /**
     * Get the whole capacity for this buffer.
     * The capacity is the max. amount of bytes that can be stored inside this buffer.
     *
     * @return The max. number of bytes that can be stored inside this buffer.
     **/
    public int capacity() {
	return this.buffer.length;
    }
    
    /**
     * Get the number of bytes currently stored inside this buffer.
     * 
     * @return The number of bytes currently stored inside tis buffer.
     **/
    public int length() {
	return this.length;
    }

    /**
     * Checks whether this buffer is empty. A buffer is empty if its length equals 0 (zero).
     *
     * No more read operations are allowed in this stats until at least one byte was written.
     * Otherwise the read() methods will raise BufferUnderflowExceptions.
     *
     * @return A boolean value indicating if the buffer is empty.
     **/
    public boolean isEmpty() {
	return this.length() <= 0;
    }


    /**
     * Checks whether this buffer is full. A buffer is full if its length equals its capacity.
     *
     * No more write operations are allowed in this stats until at least one byte was read.
     * Otherwise the write() methods will raise BufferOverflowExceptions.
     *
     * @return A boolean value indicating if the buffer is full.
     **/
    public boolean isFull() {
	return this.length() >= this.capacity();
    }

    /**
     * Read a single byte from this buffer (if available).
     *
     * @return The next byte from the FIFO queue.
     * @throws BufferUnderflowException If this buffer is empty [length()==0].
     **/
    public synchronized byte read() 
	throws BufferUnderflowException {
	
	if( this.length() == 0 )
	    throw new BufferUnderflowException(); //  "The circular FIFO buffer holds no more bytes." );

	byte b = this.buffer[ this.front ];
	this.front  = (this.front + 1) % this.buffer.length;
	this.length--;


	//System.out.println( "Byte read from FIFO: " + b + ", length=" + this.length );

	return b;
    }

    /**
     * This method reads up to 'readLength' bytes from this buffer and stores them inside
     * the given 'buf' array.
     *
     * If 'readLength' exceeds the addressable space inside the 'buf' array (beginning at 'offset').
     * The method stops reading and returns the actual number of bytes that were read.
     *
     * @param buf        The byte buffer to read from.
     * @param offset     The read offset for the input array.
     * @param readLength The max. number of bytes to read from the input array.
     * @return The actual number of bytes that were read.
     **/       
    public synchronized int read( byte[] buf, 
				  int offset, 
				  int readLength ) {

	int i = 0;
	for( ; i < readLength 
		 && i < this.length 
		 && (offset + i) < buf.length; 
	     i++ ) {

	    buf[ offset + i ] = this.buffer[ (this.front + i) % this.buffer.length  ];

	}

	this.length -= i;
	this.front = (this.front + i) % this.buffer.length;

	return i;
    }
    

    /**
     * This method writes the given byte to this buffer.
     * If the buffer is full the method will raise a BufferOverflowException.
     *
     * @param b The byte to write.
     * @throws BufferOverflowException If the buffer is already full and the byte cannot be written.
     **/
    public synchronized void write( byte b ) 
	throws BufferOverflowException {

	if( this.length >= this.buffer.length )
	    throw new BufferOverflowException(); //  "The circular FIFO buffer is full." );
	
	this.buffer[ (this.front + this.length) % this.buffer.length ] = b;
	this.length++;
    }

    
    /**
     * This method writes up tp 'writeLength' bytes bytes to this buffer.
     *
     * If 'writeLength' exceeds the addressable space inside 'buf' (beginning at 'offset') the
     * method stops writing and return the actual number of bytes written.
     *
     * @return The actual number of bytes written to this buffer.
     **/
    public synchronized int write( byte[] buf,
				   int offset,
				   int writeLength ) {

	int i = 0;
	for( ; this.length + i < this.buffer.length
		 && offset + i < buf.length
		 && i < writeLength;
	     i++ ) {

	    this.buffer[ (this.front + this.length + i) % this.buffer.length ] = buf[ offset + i ];

	}
	
	this.length += i;

	return i;
    }

    
    /**
     * This method clears the buffer's data.
     * After calling this method getLength() will return 0.
     *
     **/
    public synchronized void clear() {
	this.front  = 0;
	this.length = 0;
    } 


    /**
     * Get the byte at the given buffer offset (relative).
     * This is not really FIFO style, but might be helpful.
     * 
     * @param offset The byte offset (0 <= offset < this.length()).
     * @return The byte value at the given offset.
     **/
    public byte peek( int offset ) 
	throws IndexOutOfBoundsException {

	if( offset >= this.length )
	    throw new IndexOutOfBoundsException( "Cannt peek at buffer offset " + offset + " (length="+this.length+")." );

	return this.buffer[ (this.front + offset) % this.buffer.length ];
    }


    
    public String toString() {
	return toString( new StringBuffer() ).toString();
    }

    public StringBuffer toString( StringBuffer b ) {
	b.append( "FIFO=[" ).
	    append( "front=" ).append( this.front ).
	    append( ", length=" ).append( this.length ).
	    append( ", capacity=" ).append( this.buffer.length ).
	    append( ", buffer={" );

	for( int i = 0; i < this.length(); i++ ) {
	    
	    if( i > 0 )
		b.append( "," );

	    b.append( this.peek(i) );
	}

	b.append( " } ]" );
	return b;
    }



    public static void main( String[] argv ) {

	//String msg = "Hallo, das ist ein Test.";
	//byte[] data = msg.getBytes();
	

	byte[] data = new byte[256];
	for( int i = 0; i < data.length; i++ ) 
	    data[i] = (byte)i;

	int size = data.length;

	System.out.println( "Creating buffer (size="+ size + ") ..." );
	CircularFIFO buffer = new CircularFIFO( size );
	System.out.println( "buffer=" + buffer );

	
	System.out.println( "Adding message bytes '" + new String(data) + "' ... ");
	for( int i = 0; i < data.length; i++ ) {
	    buffer.write( data[i] );
	}
	System.out.println( "buffer=" + buffer );

	
	System.out.println( "Peeking into the buffer ... " );
	for( int i = 0; i < buffer.length(); i++ )
	    System.out.print( " " + buffer.peek(i) );


	System.out.println( "Reading bytes (A), then re-writing ... " );
	for( int i = 0; i < data.length/2; i++ ) {
	    byte b = buffer.read();
	    System.out.print( " " + b  );
	    buffer.write( b );
	}

	
	System.out.println( "Peeking into the buffer (again)... " );
	for( int i = 0; i < buffer.length(); i++ )
	    System.out.print( " " + buffer.peek(i) );


	System.out.println( "\nReading bytes (B) ... " );
	while( buffer.length() > 0 )
	    System.out.print( " " + buffer.read() );

	System.out.println( "\nDone." );

    }
}