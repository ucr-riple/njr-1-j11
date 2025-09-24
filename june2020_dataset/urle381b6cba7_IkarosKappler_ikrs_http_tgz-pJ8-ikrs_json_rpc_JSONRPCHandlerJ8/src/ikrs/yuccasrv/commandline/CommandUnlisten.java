package ikrs.yuccasrv.commandline;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import ikrs.typesystem.BasicType;
import ikrs.typesystem.BasicTypeException;
import ikrs.util.AbstractCommand;
import ikrs.util.CaseInsensitiveComparator;
import ikrs.util.Command;
import ikrs.util.CommandStringIncompleteException;
import ikrs.yuccasrv.Constants;

/**
 * This is the default LISTEN command implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-05-09
 * @version 1.0.0
 **/

public class CommandUnlisten
    extends YuccaCommand {

    public CommandUnlisten() {

	super( "UNLISTEN" );
    }

   
    //---BEGIN------------------------ AbstractCommand/YuccaCommand implementation ---------------------
    /**
     * This method handles the LISTEN command's params.
     *
     *
     * Note that this method does _not_ throw any exceptions!
     * Its up to a stored internal command handler to handle exceptions.
     *
     * @return a return code that indicates the execution result. It depends
     *         on the context what the exact meaning of the return code is,
     *         but usually the value 0 (zero) implies success.
     **/
    public int execute() {
	
	// The UNLISTEN listen command is described as follows:
	// UNLISTEN <socketID>
       

	if( this.getParamCount() != 1 ) {
	    this.getFactory().getServer().getLogger().log( Level.SEVERE,
							   "Illegal argument count." );
	    return 1; // implies error
	}

	// OK. Take the last arguments as the required ones
	BasicType uuid = this.getParamAt( 0 );
	
	/* Try to execute command through server */
	try {

	    

	    //this.getFactory().getServer().performQuit( forceQuit );
	    this.getFactory().getServer().performUnlisten( uuid ); 
	    return 0; // implies SUCCESS :)


	} catch( BasicTypeException e ) {
	    
	    this.getFactory().getServer().getLogger().log( Level.SEVERE,
							   "[Type error] " + e.getMessage() );
	    return 10; // implies error
	} catch( IOException e ) {
	    
	    this.getFactory().getServer().getLogger().log( Level.SEVERE,
							   "[IOException] " + e.getMessage() );
	    return 11; // implies error
	}
    }
    //---END-------------------------- AbstractCommand implementation ---------------------

}