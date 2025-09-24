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
 * This is the default VERSION command implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2013-03-09
 * @version 1.0.0
 **/


public class CommandVersion
    extends YuccaCommand {

    public CommandVersion() {

	super( "VERSION" );
    }

   
    //---BEGIN------------------------ AbstractCommand/YuccaCommand implementation ---------------------
    /**
     * This method handles the VERSION command's params.
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
	
	// The VERSION command is described as follows:
	// VERSION
	

	this.getFactory().getServer().getLogger().log( Level.INFO,
						       "Current version is: " + Constants.VERSION );
	return 0; // implies success
	

	
    }
    //---END-------------------------- AbstractCommand implementation ---------------------

}