package ikrs.yuccasrv.commandline;

/**
 * @author Ikaros Kappler
 * @date 2012-05-07
 * @version 1.0.0
 **/


import ikrs.typesystem.BasicType;
import ikrs.util.DefaultCommand;
import ikrs.util.Command;
import ikrs.util.CommandStringIncompleteException;

public class YuccaCommand
    extends DefaultCommand
    implements Command {

    private YuccaCommandFactory
	myFactory;

    protected YuccaCommand( String name ) {
	super( name, null );
    }

    public YuccaCommand( YuccaCommandFactory myFactory,
			 String name,
			 BasicType[] params ) {

	super( name, params );

	this.myFactory = myFactory;
    }

    protected void setFactory( YuccaCommandFactory factory ) {
	this.myFactory = factory;
    }

    protected YuccaCommandFactory getFactory() {
	return this.myFactory;
    }

    /* This method just 'refreshes' the access privileges so that other classes
     * in this package are allowed to access the AbstractCommand.setName() method.
     */
    protected void setName( String name ) {
	super.setName( name );
    }

    /* This method just 'refreshes' the access privileges so that other classes
     * in this package are allowed to access the AbstractCommand.setParams() method.
     */
    protected void setParams( BasicType[] params ) {
	super.setParams( params );
    }
    
   
    //---BEGIN------------------------ AbstractCommand implementation ---------------------
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
	
	this.getFactory().getServer().getLogger().finest( "NOOP" );
	return 0;

    }
    //---END-------------------------- AbstractCommand implementation ---------------------

}