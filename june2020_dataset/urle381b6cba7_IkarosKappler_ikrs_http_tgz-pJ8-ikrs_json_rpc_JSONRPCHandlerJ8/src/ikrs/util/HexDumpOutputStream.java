package ikrs.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import ikrs.io.CircularFIFO;

/**
 * A simple hexadecimal dump class as an OutputStream.
 *
 * Note: this class is not synchronized.
 *
 * @author Ikaros Kappler
 * @date 2012-10-29
 * @version 1.0.0
 **/


public class HexDumpOutputStream
    extends OutputStream {

    /**
     * The underlying core write (must not be null).
     **/
    private Writer coreWriter;

    /**
     * Block sizes in 'number of bytes' (not number of chars).
     **/
    private int[] blockSizes;

    /**
     * This will be set if the underyling writer was closed.
     **/
    private boolean isClosed;

    /**
     * A buffer to store single chunks of data (lines in the hexdump view).
     **/
    private CircularFIFO buffer;

    /**
     * The global write offset.
     **/
    private long writeOffset;
    
    /**
     * Create a new HexDumpWriter with the given core writer.
     *
     * This constructor will use a default block pattern with 8, 8, 0, 8, 8
     * bytes width.
     *
     *
     * @param out The underlying writer object (must not be null).
     * @throws NullPointerException If the passed stream is null.
     **/
    public HexDumpOutputStream( Writer writer ) 
	throws NullPointerException {

	this( writer, new int[] { 8, 8, 0, 8, 8 } );
    }


    /**
     * Create a new HexDumpWriter with the given core writer.
     *
     * The passed blockSizes-array should contain a set of int elements - each >= 0 -
     * where each specify the byte-size for each block to be printed.
     * Zero-elements (0) are allowed to create spacer columns but the sum of all
     * must not be null.
     *
     * @param out         The underlying writer object (must not be null).
     * @param blockSizes  The desired output block size (in bytes, not in chars).
     * @throws NullPointerException If the passed writer or blockSizes array is null.
     * @throws IllegalArgumentException If the blockSizes array is empty or has no non-zero element sum.
     **/
    public HexDumpOutputStream( Writer writer,
				int[] blockSizes ) 
	throws NullPointerException,
	       IllegalArgumentException {

	super();

	if( writer == null )
	    throw new NullPointerException( "Cannot create HexDumpOutputStream with null core writer." );
	if( blockSizes == null ) 
	    throw new NullPointerException( "Cannot create HexDumpOutputStream with null-blockSizes." );
	if( blockSizes.length == 0 )
	    throw new IllegalArgumentException( "Cannot create HexDumpOutputStream from empty blockSizes-array." );



	this.coreWriter = writer;
	//this.blockSizes = java.util.Arrays.copyOf( blockSizes, blockSizes.length );
	int bufferSize = 0;
	this.blockSizes = new int[ blockSizes.length ];
	for( int i = 0; i < blockSizes.length; i++ ) {

	    this.blockSizes[ i ] = blockSizes[ i ];
	    bufferSize += blockSizes[ i ];

	}


	if( bufferSize <= 0 )
	    throw new IllegalArgumentException( "Cannot create HexDumpOutputStream with empty blocks." );
	
	this.buffer = new CircularFIFO( bufferSize );
	this.writeOffset = 0L;
    }


    /**
     * This method checks if this stream (and the underlying writer) is closed.
     * If closed no more write operations are permitted.
     *
     * @return True if (and only if) this stream was closed.
     **/
    public boolean isClosed() {
	return this.isClosed;
    }

    //--- BEGIN --------------------- Writer implementation --------------------
    public void	close() 
	throws IOException {

	this.isClosed = true;
	this.flush();
	this.coreWriter.close();
    }

    public void flush() 
	throws IOException {

	if( this.buffer.length() == 0 ) {
	    
	    this.coreWriter.flush();
	    return;
	}


	this.writeOffset();
	this.coreWriter.write( "  " );


	int bytesRead = this.writeHexView();
	this.writeOffset += bytesRead;
	// Print a delimiter before readable contents output starts.
	this.coreWriter.write( " " );


	this.printCleanView();
	this.coreWriter.write( "\n" );


	this.coreWriter.flush();
    }

    public void write( int b ) 
	throws IOException {

	if( this.isClosed() )
	    throw new IOException( "Cannot write to a closed HexDumpOutputStream." );

	this.buffer.write( (byte)(b & 0xFF) );

	// Check if the buffer is full (then flush).
	if( this.buffer.isFull() )
	    this.flush();
	    
    }
    //--- END ----------------------- Writer implementation --------------------

    private void writeOffset() 
	throws IOException {

	this.coreWriter.write( "0x" );
	String addr =
	    Integer.toString( (int)( (this.writeOffset>>28) & 0xF ), 16 ) +
	    Integer.toString( (int)( (this.writeOffset>>24) & 0xF ), 16 ) +
	    Integer.toString( (int)( (this.writeOffset>>20) & 0xF ), 16 ) +
	    Integer.toString( (int)( (this.writeOffset>>16) & 0xF ), 16 ) +
	    Integer.toString( (int)( (this.writeOffset>>12) & 0xF ), 16 ) +
	    Integer.toString( (int)( (this.writeOffset>> 8) & 0xF ), 16 ) +
	    Integer.toString( (int)( (this.writeOffset>> 4) & 0xF ), 16 ) +
	    Integer.toString( (int)( (this.writeOffset    ) & 0xF ), 16 );

	
	// Fill the address prefix with zeros.
	for( int i = addr.length(); i < 8; i++ )
	    this.coreWriter.write( "0" );

	// Print the actual address part and a delimiter
	this.coreWriter.write( addr );

    }

    private int writeHexView() 
	throws IOException {

	// Transfer all bytes from the buffer to the underlying writer.
	byte b;
	int i = 0;
	for( int block = 0; block < this.blockSizes.length; block++ ) {

	    int blockSize = this.blockSizes[block];
	    //int inBlock = 0;

	    //while( inBlock < blockSize ) {
	    for( int inBlock = 0; inBlock < blockSize; inBlock++ ) {

		if( i < this.buffer.length() ) {
		    b = this.buffer.peek( i );
		    this.coreWriter.write( Integer.toString( (b>>4) & 0xF, 16 ) );
		    this.coreWriter.write( Integer.toString(  b     & 0xF, 16 ) );	
		    
		    i++;
		} else {
		    
		    // Write fill spaces for the case we reached end-of-file (EOI)
		    this.coreWriter.write( "  " ); // Each byte has two (!) hex chars

		}
		
		//inBlock++;
	    }
	    
	    // Print a block delimiter
	    this.coreWriter.write( " " ); // Space

	}


	return i;
    }

    private void printCleanView() 
	throws IOException {

	byte b;
	char c;
	while( !this.buffer.isEmpty() ) {

	    b = this.buffer.read();
	    c = (char)b;
	    // Only write printable (non control) characters
	    if( ( c > 32  
		
		  // At position 126 is the DEL control char
		  && c < 126 )
		
		||
		(c > 126 && c < 178) ) {
		
		// Print a placeholder
		this.coreWriter.write( c );

	    } else {

		// Print a placeholder
		this.coreWriter.write( "." );

	    }
	}
    }

    public static void main( String[] argv ) {

	if( argv.length == 0 ) {

	    System.err.println( "Specify an input file." );
	    System.exit( 1 );

	}


	try {


	    java.io.InputStream in = new java.io.FileInputStream( new java.io.File(argv[0]) );
	    Writer writer = new java.io.PrintWriter( new java.io.OutputStreamWriter( System.out ) ); 
	    HexDumpOutputStream out = new HexDumpOutputStream( writer );

	    
	    int b;
	    while( (b = in.read()) != -1 ) {
	    
		out.write( b );

	    }

	    in.close();
	    
	    out.flush();

	} catch( IOException e ) {

	    e.printStackTrace();

	}
	    


    }

}