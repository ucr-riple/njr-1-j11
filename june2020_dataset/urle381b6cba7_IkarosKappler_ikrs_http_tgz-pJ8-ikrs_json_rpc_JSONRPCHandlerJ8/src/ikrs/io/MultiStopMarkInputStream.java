package ikrs.io;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The MultiStopMarkInputStream is an enhancement of the normal 
 * StopMarkInputStream class, it supports a whole set of stop marks
 * to be passed, which split the underlying inputstream into tokens
 * each time one of the stop mark as found.
 *
 * The order or the passed stop marks is the order the input sequence
 * will be matched for the marks.
 *
 * @author Ikaros Kappler
 * @date 2013-04-09
 * @version 1.0.0
 **/


public class MultiStopMarkInputStream 
    extends InputStream {

    /* The underlying input stream. */
    private InputStream in;

    /* The current (nested) streams, the upper stream at index 0, the lower at highest index. 
     *
     * Idea: new StopMarkInputStream( new StopMarkInputStream( new StopMarkInputStream(...) ) )
     **/
    private StopMarkInputStream[] currentStreams;

    /* The passed stop marks. */
    private List<byte[]> stopMarks;

    /* An internal flag indicating if EOI was reached. */
    private boolean eoiReached;
    
    /* If any of the stored input streams reached the assigned stop mark this int stores its index. */
    private int stopMarkReachedIndex;
    
    /* Will be set to true if the close() method was reached. */
    private boolean isClosed;
    

    /**
     * Creates a new MultiStopMarkInputStream and creates a copy of the passed stop mark list.
     *
     * @param in The underlying input stream.
     * @param stopMarks A non-empty and non-null list of stop marks to be used. The order of the
     *                  marks is the final detection order, beginning at index 0.
     * @throws NullPointerException If 'in' is null or 'stopMarks' is null or the list contains
     *                              any null items.
     **/
    public MultiStopMarkInputStream( InputStream in,
				     List<byte[]> stopMarks ) 
	throws NullPointerException {

	this( in, stopMarks, true ); // copy list
    }

    /**
     * Creates a new MultiStopMarkInputStream and creates a copy of the passed stop mark list if
     * the 'copyList' param is set to true. 
     *
     * If the stop mark list is empty the stream will behave like a normal InputStream.
     *
     * @param in The underlying input stream.
     * @param stopMarks A non-empty and non-null list of stop marks to be used. The order of the
     *                  marks is the final detection order, beginning at index 0.
     * @param copyList If set to true the constructor will create an internal copy of the passed
     *                 list.
     * @throws NullPointerException If 'in' is null or 'stopMarks' is null or the list contains
     *                              any null items.
     **/
    public MultiStopMarkInputStream( InputStream in,
				     List<byte[]> stopMarks,
				     boolean copyList ) 
	throws NullPointerException {

	super();

	if( in == null )
	    throw new NullPointerException( "Cannot create MultiStopMarkInputStream from null-input." );
	if( stopMarks == null )
	    throw new NullPointerException( "Cannot create MultiStopMarkInputStream with null-list." );
	this.in           = in;	

	// Copy and check entries from the list
	if( copyList ) 
	    this.stopMarks    = new ArrayList<byte[]>( stopMarks.size() );
	// Revert order! 
	// Reason: the stack is built from last to first token, but
	// first token should be detected first.
	for( int i = stopMarks.size()-1; i >= 0; i-- ) {
	    
	    byte[] stopMark = stopMarks.get( i );
	    if( stopMark == null )
		throw new NullPointerException( "Cannot create MultiStopMarkInputStream with null-marks (index " + i + ")." );
	    // Allow empty marks.
	    
	    // Check for duplicates?
	    // ...

	    if( copyList )
		this.stopMarks.add( stopMark );
	}

	if( !copyList )
	    this.stopMarks = stopMarks;
	    

	// Init the stream buffer
	this.currentStreams       = new StopMarkInputStream[ stopMarks.size() ];
	this.stopMarkReachedIndex = -1;
	this.eoiReached           = false;
	this.isClosed             = false;
    }

    //--- BEGIN -------------------- InputStream implementation -------------------
    public int read()
	throws IOException {
	
	if( this.isClosed() )
	    throw new IOException( "Cannot read from a closed stream." );
	
	// No stop marks given?
	if( this.emptyStopMarkSet() )
	    return this.in.read();

	if( this.eoiReached() )
	    return -1;
	
	if( this.stopMarkReached() )
	    return -1;

	// First call?
	if( this.currentStreams[0] == null )
	    initStreamStack();

	// Try to read next byte
	int b = this.currentStreams[0].read();
	if( b == -1 ) {
	    // Find the stream that reached the stop mark
	    this.stopMarkReachedIndex = this.locateReachedStopMarkIndex();
	    
	    if( this.stopMarkReachedIndex == -1 ) {
		// EOI reached!
		this.eoiReached = true;
	    } 
	}

	return b;
    }

    public int available() 
	throws IOException {

	if( this.isClosed )
	    return 0;

	if( this.emptyStopMarkSet() )
	    return this.in.available();

	// First call?
	if( this.currentStreams[0] == null )
	    initStreamStack();

	return this.currentStreams[0].available();
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

    public void reset() 
	throws IOException {
	
	throw new IOException( "The MultiStopMarkInputStream does not supported the reset() method." );

    }

    public long skip( long n ) 
	throws IOException {

	if( this.isClosed )
	    throw new IOException( "This stream was already closed. Cannot skip input." );

	if( this.emptyStopMarkSet() )
	    return this.in.skip( n );

	// First call?
	if( this.currentStreams[0] == null )
	    initStreamStack();

	return this.currentStreams[ 0 ].skip( n );
	/*long skipped = this.in.skip( n );

	// Buffer data becomes invalid when skipping!
	this.buffer.clear();

	return skipped;
	*/
    }
    //--- END ---------------------- InputStream implementation -------------------
    
    /**
     * If the passed stop mark list is empty, this method returns true.
     * The input stream has to behave like a normal InputStream then.
     **/
    private boolean emptyStopMarkSet() {
	return this.currentStreams.length == 0;
    }

    /**
     * Determines whether this input stream was already closed.
     *
     * @return true If the close() method was already called.
     **/
    public boolean isClosed() {
	return this.isClosed;
    }

    /**
     * When a stop mark was reached the underlying stream might not have reached EOI yet.
     * If you call this method the internal stopMarkReached flaf will be cleared and more
     * bytes can be read until the next stop mark or EOI is reached.
     *
     * @return true If the stream was not yet closed or EOI was not yet reached; true indicates
     *              that it is ready to read more bytes, but it's no promise that the next
     *              read() call will not hit EOI.
     **/
    public boolean continueStream() {
	if( this.eoiReached || this.isClosed )
	    return false;
	if( this.stopMarkReachedIndex == -1 )
	    return true;
	
	for( int i = this.stopMarkReachedIndex; i >= 0; i-- ) {
	    // true means: override EOI
	    // because: the underlying stream(s) returned -1 because a stop mark
	    //          was reached; the aboslute EOI possible has not yet been reached.
	    this.currentStreams[i].continueStream( true );  
	}

	this.stopMarkReachedIndex = -1;
	return true;
    }

    /**
     * This method determines if a stop mark was reached.
     * If a stop mark was reached the read() method will return -1 until
     * the continueStream() method was called. This will clear the internal
     * stopMarkReached flag.
     *
     * @return true If any of the passed stop marks was hit.
     **/
    public boolean stopMarkReached() {
	return (this.stopMarkReachedIndex != -1);
    }

    /**
     * If a stop mark was hit this method return its index in the initially passed
     * list. Otherwise -1 is returned.
     *
     * Calling continueStream() will cause this method to return -1 again.
     *
     * @return The index of the reached stop mark (index in the list), IF one was hit.
     * @see stopMarkReached()
     **/
    public int getReachedStopMarkIndex() {
	return this.stopMarkReachedIndex;
    }

    /**
     * If a stop mark was reached this method tell which one it was.
     *
     * @return The stop mark itself, if one was reached; null otherwise.x
     **/
    public byte[] getReachedStopMark() {
	if( this.emptyStopMarkSet() || this.stopMarkReachedIndex == -1 )
	    return null;
	else
	    return this.stopMarks.get( this.stopMarkReachedIndex );
    }

    /**
     * This method tell whether the underlying stream reached EOI.
     *
     * Note: if the read() method returns -1 it does not nesesarily indicate
     *       that EOI was reached. The stream might also have hit a stop mark.
     *
     * @return true If the underlying input stream reached EOI and no more
     *              bytes are available.
     **/
    public boolean eoiReached() {
	return this.eoiReached;
    }

    /**
     * If a stop mark was hit this method determines which StopMarkStream in the
     * stack caused it and returns it index. The index tells which stop mark was
     * reached, look into the passed stop mark list.
     *
     * If no stop mark was reached it will return -1.
     **/
    private int locateReachedStopMarkIndex() {
	for( int i = 0; i < this.currentStreams.length; i++ ) {
	    //System.out.println( "Stop mark " + i + " reached: " + this.currentStreams[i].stopMarkReached() );
	    if( this.currentStreams[i].stopMarkReached() ) {
		//System.out.println( "Stream["+i+"] reached the stop mark: " + new String(this.stopMarks.get(i)) );
		return i;
	    }
	}
	return -1;
    }

    /**
     * If the read(), available() or skip() method is called the first time (only one of them)
     * the call will cause the initStreamStack() method to be called. This method builds
     * up the stream stack :)
     **/
    private void initStreamStack() {

	if( this.emptyStopMarkSet() )
	    return;

	// There is at least one stop mark (list cannot be empty).
	this.currentStreams[this.stopMarks.size()-1] = 
	    new StopMarkInputStream( in, 
				     this.stopMarks.get( this.stopMarks.size()-1 ) 
				     );
	for( int i = this.stopMarks.size()-2; i >= 0; i-- ) {

	    this.currentStreams[i] = new StopMarkInputStream( this.currentStreams[i+1], 
							      this.stopMarks.get(i) 
							      );

	}
    }

    /** Just for testing **/
    public static void main( String[] argv ) {

	try {
	    String[] stopMarks = new String[] {
		"%MARK_0%",
		"%MARK_A%",
		"%MARK_B%",
		"%MARK_C%"
	    };
	    String data = 
		stopMarks[0] +
		"This " + 
		stopMarks[3] + 
		" is a " + 
		stopMarks[2] + 
		" very simple " + 
		stopMarks[1] + 
		" test.";
	    List<byte[]> stopMarkList = new ArrayList<byte[]>( stopMarks.length );
	    System.out.println( "Stop marks: " );
	    for( int i = 0; i < stopMarks.length; i++ ) {
		System.out.println( " [" + i + "] " + stopMarks[i] );
		stopMarkList.add( stopMarks[i].getBytes(java.nio.charset.StandardCharsets.UTF_8.name()) );
	    }
	    System.out.println( "\n" );
	    System.out.println( " data: " + data );
	    
	     

	    MultiStopMarkInputStream in = 
		new MultiStopMarkInputStream( new java.io.ByteArrayInputStream(data.getBytes(java.nio.charset.StandardCharsets.UTF_8.name())),
					      stopMarkList );
	    
	    int i = 0;
	    while( !in.eoiReached() && i < 10 ) {
		int b = -1;
		while( (b = in.read()) != -1 ) {
		    System.out.print( (char)b );
		}
		System.out.println( "" );
		byte[] reachedStopMark = in.getReachedStopMark();
		System.out.println( "Stop mark " + in.getReachedStopMarkIndex() + " found: " + (reachedStopMark==null?"null":new String(reachedStopMark)) );
		
		in.continueStream();
		i++;
	    }


	} catch( IOException e ) {
	    e.printStackTrace();
	}

    }
}