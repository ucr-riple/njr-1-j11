package ikrs.util;

/**
 * @author Ikaros Kappler
 * @date 2012-05-07
 * @version 1.0.0
 **/


import ikrs.typesystem.BasicType;

public class DefaultCommand
    extends AbstractCommand
    implements Command,
	       Comparable<Command> {

    public DefaultCommand( String name,
			   BasicType[] params ) {

	super( name, params );
    }

   
    //---BEGIN------------------------ AbstractCommand implementation ---------------------
    /**
     * This is the final execution method - it does nothing but returning 1.
     *
     * Note that this method does _not_ throw any exceptions!
     * Its up to a stored internal command handler to handle exceptions.
     *
     * @return a return code that indicates the execution result. It depends
     *         on the context what the exact meaning of the return code is,
     *         but usually the value 0 (zero) implies success.
     **/
    public int execute() {

	// No execution defined!!!
	/*
	if( this.getName() != null &&
	    ( this.getName().equalsIgnoreCase("QUIT") || this.getName().equalsIgnoreCase("EXIT")) ) {

	    System.exit(0);

	} else {

	    System.out.println( "Command: "+this );

	}
	*/

	return 1;

    }
    //---END-------------------------- AbstractCommand implementation ---------------------

    
    //---BEGIN------------------------ Comparable implementation --------------------------
    public int compareTo( Command c ) {
	return this.getName().compareToIgnoreCase( c.getName() );
    }
    //---END-------------------------- Comparable implementation --------------------------
}