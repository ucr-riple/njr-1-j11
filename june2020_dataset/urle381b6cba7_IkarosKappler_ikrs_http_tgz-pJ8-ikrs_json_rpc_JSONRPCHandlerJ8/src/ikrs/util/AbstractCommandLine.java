package ikrs.util;

/**
 * @author Ikaros Kappler
 * @date 2012-05-03
 * @version 1.0.0
 **/

import java.io.*;
import java.text.ParseException;

public class AbstractCommandLine<C extends Command>
    extends Thread {

    private String[] ringBuffer;
    private int bufferLength;
    private int topOfBuffer;
    private int bufferPointer;

    private String prompt;

    private CommandFactory<C>
	commandFactory;


    private BufferedReader bufferedReader;

    /* There is a problem when this command line is interrupted without being started as
     * a thread: the interrupted-flag cannot be set.
     *
     * So we need an additional flag being set when running in non-threaded mode.
     */
    private boolean lineIsInterrupted;

    public AbstractCommandLine( CommandFactory<C> factory ) {
	this( factory, "$>" );
    }
	
    
    public AbstractCommandLine( CommandFactory<C> factory,
				String prompt 
				) {
	super();

		
	
	this.ringBuffer     = new String[ 64 ];
	this.bufferLength   = 0;
	this.topOfBuffer    = 0;
	this.bufferPointer  = 0;

	this.prompt = prompt;

	this.commandFactory = factory;
    }

    /**
     * Get the command factory for this command line.
     *
     * @return The command factory for this command line.
     **/
    public CommandFactory<C> getCommandFactory() {
	return this.commandFactory;
    }

    /**
     * @override Thread.run
     **/
    public void run() {
	if( this.bufferedReader != null )
	    throw new RuntimeException( "This Thread is already running and cannot be stared twice!" );

	try {

	    runCommandLine();

	} catch( IOException e ) {
	    e.printStackTrace();
	}
    }

    /**
     * This method should be used to start the listening process.
     * The method will block.
     **/
    public void runCommandLine() 
	throws IOException {

	this.bufferedReader = new BufferedReader( new InputStreamReader(System.in) );

	String line;
	do {
	    System.out.print( this.prompt );
	    
	    try {

		line = bufferedReader.readLine();

	    } catch( IOException e ) {
		//System.out.println( "isInterrupted()="+isInterrupted()+", lineIsInterrupted="+this.lineIsInterrupted );

		if( this.lineIsInterrupted || this.isInterrupted() )
		    return; // The thread was interupted which caused the IOException indirectly
		else
		    throw e; // A 'regular' IOException -> report

	    }



	    if( line == null )
		return;
	    //System.out.println( "Line Read: " + line );

	    line = line.trim();
	    if( line.length() == 0 )
		continue;

	    
	    // Add line to ring buffer
	    this.topOfBuffer = (this.topOfBuffer + 1) % this.ringBuffer.length;
	    this.ringBuffer[ this.topOfBuffer ] = line;

	    
	    // Parse string into command
	    try {
		Command cmd = this.commandFactory.parse( line );
		//System.out.println( "Command: "+cmd );
		
		// Execute command
		int returnCode = cmd.execute();
		

	    } catch( UnsupportedOperationException e ) {
		e.printStackTrace();
		return;

	    } catch( UnknownCommandException e ) {
		System.out.println( e.getMessage() );

	    } catch( CommandStringIncompleteException e ) {
		System.out.println( e.getMessage() );
		
	    } catch( ParseException e ) {
		System.out.println( e.getMessage() );
		
	    }
		

	} while( true );

    }


    public void interrupt() {
	// First: interrupt the thread
	super.interrupt();

	// Set private interrupted flag for the case this command line is not running in a thread
	this.lineIsInterrupted = true;

	//System.out.println( "Interrupted ... "+this.isInterrupted() );

	// Second: reset the BufferedReader; as long as no mark was set (!) the Reader
	// will raise an IOException then and the while loop should terminate.
	try {

	    //this.bufferedReader.reset();
	    this.bufferedReader.close();

	} catch( IOException e ) {

	    // Drop exception (it's expected!)

	}
    }


    public static void main( String[] argv ) {

	CommandFactory<Command> factory = new DefaultCommandFactory();
	AbstractCommandLine<Command> cl = new AbstractCommandLine<Command>( factory );

	try {
	    cl.runCommandLine();
	} catch( IOException e ) {
	    e.printStackTrace();
	}

    }

}