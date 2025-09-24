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
 * This is the default STATUS command implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-10-30
 * @version 1.0.0
 **/


public class CommandStatus
    extends YuccaCommand {

    public CommandStatus() {

	super( "STATUS" );
    }

   
    //---BEGIN------------------------ AbstractCommand/YuccaCommand implementation ---------------------
    /**
     * This method handles the STATUS command's params.
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
	
	// The STATUS listen command is described as follows:
	// STATUS


	    //this.getFactory().getServer().performQuit( forceQuit );
	    this.getFactory().getServer().performStatus(); 
	    return 0; // implies SUCCESS :)


	    /*} catch( IOException e ) {
	    
	    this.getFactory().getServer().getLogger().log( Level.SEVERE,
							   "[IOException] " + e.getMessage() );
	    return 11; // implies error
	    }*/
    }
    //---END-------------------------- AbstractCommand implementation ---------------------

}