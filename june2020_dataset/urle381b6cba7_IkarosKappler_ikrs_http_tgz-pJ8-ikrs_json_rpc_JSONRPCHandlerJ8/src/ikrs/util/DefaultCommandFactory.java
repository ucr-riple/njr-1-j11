package ikrs.util;

import java.text.ParseException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import ikrs.typesystem.BasicStringType;
import ikrs.typesystem.BasicType;

/**
 * @author Ikaros Kappler
 * @date 2012-05-07
 * @version 1.0.0
 **/


public class DefaultCommandFactory
    extends AbstractCommandFactory<Command>
    implements CommandFactory<Command> {

    private Set<Command> supportedCommands;

    /**
     * Create a new DefaultCommandFactory with an empty set of supported
     * commands.
     **/
    public DefaultCommandFactory() {
	super();
	
	this.supportedCommands = new TreeSet<Command>();
    }

    protected void addSupportedCommand( Command c ) {
	this.supportedCommands.add( c );
    }

    //---BEGIN----------------------- AbstractCommandFactory implementation -----------------------
    /**
     * Make a new Command with the given name and params.
     *
     * @param name The command's name.
     * @param params The command's params - in BasicType representation.
     * 
     * @return The new command.
     **/
    public Command make( String name,
			 BasicType[] params )
	throws UnknownCommandException,
	CommandStringIncompleteException {

	return new DefaultCommand( name, params );
    }
    
    /**
     * Get a set of all supported commands. This is usually a set of the
     * commands that are understood by this factory plus all commands that
     * are understood by all parent factories (if exist).
     *
     * @return A set containting all commands that are supported by this 
     * factory.
     **/
    public Set<Command> getSupportedCommands() {
	if( this.getParentFactory() == null ) {
	    
	    return Collections.unmodifiableSet( this.supportedCommands );

	} else {

	    Set<Command> sc = new TreeSet<Command>();
	    sc.addAll( this.supportedCommands );
	    sc.addAll( this.getParentFactory().getSupportedCommands() );

	    return sc;
	}
	     
    }
    //---END------------------------- AbstractCommandFactory implementation -----------------------

}