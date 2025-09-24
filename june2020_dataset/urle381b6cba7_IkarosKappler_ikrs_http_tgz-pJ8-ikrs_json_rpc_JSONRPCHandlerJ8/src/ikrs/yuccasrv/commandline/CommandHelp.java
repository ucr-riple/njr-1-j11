package ikrs.yuccasrv.commandline;

import java.util.Iterator;

import ikrs.typesystem.BasicType;
import ikrs.util.AbstractCommand;
import ikrs.util.Command;
import ikrs.util.CommandStringIncompleteException;

/**
 * This is the default HELP command implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-05-09
 * @version 1.0.0
 **/

public class CommandHelp
    extends YuccaCommand {

    private String helpName;
    
    private BasicType[] helpParams;

    /**
     * This class requires to know its command factory; otherwise it
     * would not be able to print the list of available commands.
     **/
    private YuccaCommandFactory yuccaCommandFactory;

    public CommandHelp( YuccaCommandFactory factory,
			String name,
			BasicType[] params ) {

	super( "HELP" );

	this.yuccaCommandFactory = factory;

	this.helpName            = name;
	this.helpParams          = params;
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
	
	// Build info
	/*String[] commands = YuccaCommandFactory.getImplementedCommands();
	StringBuffer b = new StringBuffer( "Available commands: " );
	for( int i = 0; i < commands.length; i++ ) {
	    
	    if( i > 0 )
		b.append( ", " );
	    b.append( commands[i] );

	}
	System.out.println( b.toString() );
	*/

	// Try to locate command
	
	Command desiredCommand = null;

	StringBuffer b = new StringBuffer( "Available commands: " );
	java.util.Set<Command> supportedCommands = this.yuccaCommandFactory.getSupportedCommands();
	//System.out.println( getClass() + " supported commands: " + supportedCommands );

	
	Iterator<Command> iter = supportedCommands.iterator();
	int i = 0;
	while( iter.hasNext() && desiredCommand == null ) {
		
	    Command c = iter.next();

	    //if( c.getName().equalsIgnoreCase(this.helpName) )
	    //	desiredCommand = c;
		
	    if( i > 0 )
		b.append( ", " );
	    b.append( c.getName() );
		
	    i++;
	}
	
	    
	//if( desiredCommand == null 
	//    || desiredCommand.getClass().equals(this.getClass()) ) {
	    
	System.out.println( b.toString() );

	    /* } else { 
	    
	    System.out.println( "Help for '" + this.helpName + "' not yet available (not yet implemented @ " + getClass().getName() + ")." );
	    */

	    // }

	    //}
	

	return 0;
	
    }
    //---END-------------------------- AbstractCommand implementation ---------------------

}