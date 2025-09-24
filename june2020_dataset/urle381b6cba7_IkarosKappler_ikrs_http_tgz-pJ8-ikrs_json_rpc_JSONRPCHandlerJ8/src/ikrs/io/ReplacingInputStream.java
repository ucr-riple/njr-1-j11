package ikrs.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ikrs.util.ByteArrayComparator;


/**
 * The ReplacingInputStream is an InputStream implementation that allows to
 * specify a byte sequence replacement map which will be applied directly
 * on processing the underlying stream.
 *
 * Imagine you want to read a large text file and want to replace some tokens.
 * Usually you would convert the file contents - a byte sequence - into a
 * String and then call the replace/replaceAll method. If there are n tokens
 * to be replaced you would have to call replace n times.
 *
 * Instead the ReplacingInputStream matches the given byte patterns directly
 * while the bytes are read. It uses a MultiStopMarkInputStream to locate
 * the desired tokens.
 *
 * 
 * Example (pseudo code):
 *    byte[] data = "This %patternA% a %patternB%.".getBytes();
 *    Map<byte[],byte[]> replacementMap = { ( "%patternA%".getBytes() => "is".getBytes() ), 
 *                                          ( "$patternB%".getbytes() => "test".getBytes() )  
 *                                        };
 *    InputStream in = new ReplacingInputStream( data, replacementMap );
 *    
 *  Reading from the stream will then result in the bytes representing:
 *   "This is a test."
 *
 * @author Ikaros Kappler
 * @date 2013-04-11
 * @version 1.0.0
 **/

public class ReplacingInputStream 
    extends InputStream {

    /* A reference to the passed replacement map (no copy). */
    private Map<byte[], byte[]> replacementMap;
    
    /* The MultiStopMarkInputStream to read the tokenized data from. */
    private MultiStopMarkInputStream multiIn;
    
    /* If a key token was found its replacement will be stored in thie buffer for later reading. */
    private ByteArrayInputStream currentReplacementBuffer;
    
    /* A flag indicating if this input stream was closed. */
    private boolean isClosed;

    /**
     * Create a new ReplacingInputStream.
     *
     * Note A: the passed replacement map will not be copied. Concurrent
     * modifications might lead to thread collisions.
     *
     * Note B: the key tokens will be replaced in the order they appear
     *         in the map when using an iterator from the entry set.
     *
     * @param in The underlying input stream, must not be null.
     * @param replacementMap The key token / replacement token map.
     * @throws NullPointerException If the passed input stream is null or the
     *                              replacement map is null or contains any null
     *                              entries.
     **/
    public ReplacingInputStream( InputStream in,
				 Map<byte[],byte[]> replacementMap 
				 ) 
	throws NullPointerException {

	super();

	if( in == null )
	    throw new NullPointerException( "Cannot create ReplacingInputStreams from null-streams." );
	if( replacementMap == null )
	    throw new NullPointerException( "Cannot create ReplacingInputStreams with null-map." );

	// Convert key set to list
	List<byte[]> keyList         = new ArrayList<byte[]>( replacementMap.size() );

	Iterator<Map.Entry<byte[],byte[]>> iter = replacementMap.entrySet().iterator();
	while( iter.hasNext() ) {
	    
	    Map.Entry<byte[],byte[]> entry = iter.next();
	    byte[] key   = entry.getKey();
	    byte[] value = entry.getValue();

	    if( key == null )
		throw new NullPointerException( "Cannot create ReplacingInputStreams with null-keys." );
	    if( value == null )
		throw new NullPointerException( "Cannot create ReplacingInputStreams with null-replacements." );

	    keyList.add( key );
	}

	this.multiIn        = new MultiStopMarkInputStream( in, keyList );
	this.replacementMap = replacementMap;
    }
 
    //--- BEGIN -------------- InputStream implementation -------------------
    public int read()
	throws IOException {
	
	if( this.isClosed )
	    throw new IOException( "Cannot read from a closed stream." );

	int b;

	// Are there still bytes left to read from the latest replacement?
	if( this.currentReplacementBuffer != null ) {
	    b = this.currentReplacementBuffer.read();
	    if( this.currentReplacementBuffer.available() == 0 )
		this.currentReplacementBuffer = null;
	    return b;
	}
	
	// Try to read next byte from the MultiStopMarkInputStream.
	b = this.multiIn.read();
	if( b != -1 )
	    return b; // No stop mark nor EOI reached.
	

	if( this.multiIn.eoiReached() )
	    return -1;  // EOI finally reached. No more bytes available.

	// Got -1 but EOI was not reached -> there must be a stop mark hit
	if( !this.multiIn.stopMarkReached() ) 
	    throw new RuntimeException( "Illegal state: found -1 but not EOI nor stop mark was reached. Cannot continue." );

	byte[] stopMark = this.multiIn.getReachedStopMark(); // Cannot be null
	this.multiIn.continueStream();

	byte[] replacement = this.replacementMap.get( stopMark ); // Cannot be null, too
	this.currentReplacementBuffer = new ByteArrayInputStream( replacement );
	
	return this.read();  // Recursive call.
    }

    public int available() 
	throws IOException {

	if( this.isClosed )
	    return 0;

	if( this.currentReplacementBuffer != null ) 
	    return this.currentReplacementBuffer.available();

	if( this.multiIn.eoiReached() )
	    return 0;
	else
	    return 1;
    }

    public void close() 
	throws IOException {
	
	this.isClosed = true;
	
	// Do NOT forward call to the underlying input stream.
	// Closing given (!) streams is not our concern.
    }
    //--- END ---------------- InputStream implementation -------------------

    /**
     * Determines if this input stream was closed.
     *
     * @return true If the close() method was already called.
     **/
    public boolean isClosed() {
	return this.isClosed;
    }

    /* Just for testing */
    public static void main( String[] argv ) {
	
	try {
	    String[] stopMarks = new String[] {
		"%MARK_0%",
		"%MARK_A%",
		"%MARK_B%",
		"%MARK_C%",
		"%MARK_D%"
	    };
	    String[] replacements = new String[] {
		"Hello",
		"This",
		"very",
		"stream replacement",
		"Goodbye."
	    };
	    String data = 
		stopMarks[0] +
		" World. " + 
		stopMarks[1] + 
		" is a " + 
		stopMarks[2] + 
		" simple " + 
		stopMarks[3] + 
		" test. " +
		stopMarks[4];
	    System.out.println( "data: " + data );


	    Map<byte[],byte[]> replacementMap = new java.util.TreeMap<byte[],byte[]>( new ByteArrayComparator() );
	    System.out.print( "Replacement map: { " );
	    for( int i = 0; i < stopMarks.length; i++ ) {
		replacementMap.put( stopMarks[i].getBytes(java.nio.charset.StandardCharsets.UTF_8.name()), 
				    replacements[i].getBytes(java.nio.charset.StandardCharsets.UTF_8.name())
				    );

		if( i > 0 )
		    System.out.print( ", " );
		System.out.print( "(" + stopMarks[i] + "," + replacements[i] + ")" );
	    }
	    System.out.println( " }" );

	    
	    ReplacingInputStream in = 
		new ReplacingInputStream( new java.io.ByteArrayInputStream(data.getBytes(java.nio.charset.StandardCharsets.UTF_8.name())),
					  replacementMap );

	    int b;
	    while( (b = in.read()) != -1 ) {
		System.out.print( (char)b );
	    }
	    System.out.println( "" );

	    in.close();
	    

	} catch( IOException e ) {
	    e.printStackTrace();
	}
	

    }
}