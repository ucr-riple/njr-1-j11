package ikrs.yuccasrv.commandline;

import ikrs.typesystem.BasicType;
import ikrs.util.AbstractCommand;
import ikrs.util.Command;
import ikrs.util.CommandStringIncompleteException;

/**
 * This is the default QUIT command implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-05-09
 * @version 1.0.0
 **/


public class CommandQuit
    extends YuccaCommand {

    public CommandQuit() {

	super( "QUIT" );
    }

   
    //---BEGIN------------------------ AbstractCommand/YuccaCommand implementation ---------------------
    /**
     * This is the final execution method - the default implementation has only one
     * simple effect: if the command name equals "QUIT" or "EXIT" (not case sensitive)
     * the method calls System.exit(0).
     *
     * Otherwise it returns always 1.
     *
     * Your subclasses should override this method.
     *
     * Note that this method does _not_ throw any exceptions!
     * Its up to a stored internal command handler to handle exceptions.
     *
     * @return a return code that indicates the execution result. It depends
     *         on the context what the exact meaning of the return code is,
     *         but usually the value 0 (zero) implies success.
     **/
    public int execute() {
	
	boolean forceQuit = false;

	// Is there a --force param?
	if( this.getParamCount() != 0 ) {
	    if( this.getParamAt(0).getString().equalsIgnoreCase("-f") || this.getParamAt(0).getString().equalsIgnoreCase("--force") )
		forceQuit = true;
	    else
		this.getFactory().getServer().getLogger().warning( "Unrecognized option: "+this.getParamAt(0).getString() );
	}

	this.getFactory().getServer().performQuit( forceQuit );
	return 0;
	
    }
    //---END-------------------------- AbstractCommand implementation ---------------------

}