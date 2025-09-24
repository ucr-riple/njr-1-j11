package ikrs.yuccasrv.commandline;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import ikrs.typesystem.BasicType;
import ikrs.typesystem.BasicTypeException;
import ikrs.util.AbstractCommand;
import ikrs.util.CaseInsensitiveComparator;
import ikrs.util.Command;
import ikrs.util.CommandStringIncompleteException;
import ikrs.util.DefaultEnvironment;
import ikrs.util.Environment;
import ikrs.util.TreeMapFactory;
import ikrs.yuccasrv.Constants;

/**
 * This is the default LOGLEVEL command implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2013-03-09
 * @version 1.0.0
 **/

public class CommandLogLevel
    extends YuccaCommand {

    public CommandLogLevel() {

	super( "LOGLEVEL" );
    }

   
    //---BEGIN------------------------ AbstractCommand/YuccaCommand implementation ---------------------
    /**
     * This method handles the LOGLEVEL command's params.
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
	
	// The LOGLEVEL command is described as follows:
	// LOGLEVEL [<level>]
	
	if( this.getParamCount() == 0 ) {

	    // this.getFactory().getServer().performLogLevel( null );
	    this.getFactory().getServer().getLogger().log( Level.INFO,
							   "Current log level is: " + this.getFactory().getServer().getLogger().getLevel() );
	    return 0; // implies success

	} else {

	    String str_level = this.getParamAt(0).getString();
	    try {
		
		Level level = Level.parse( str_level.toUpperCase() );
		this.getFactory().getServer().getLogger().setLevel( level );
		this.getFactory().getServer().getLogger().log( Level.INFO,
							       "Log level set to: " + level );
		
		return 0; // implies success

	    } catch( BasicTypeException e ) {
		
		this.getFactory().getServer().getLogger().log( Level.SEVERE,
							       "[Type error] " + e.getMessage() );
		return 101; // implies error
	    } catch( IllegalArgumentException e ) {
		
		this.getFactory().getServer().getLogger().log( Level.SEVERE,
							       "Illegal log level " + str_level );
		return 101; // implies error
	    }
	}

	
    }
    //---END-------------------------- AbstractCommand implementation ---------------------

}