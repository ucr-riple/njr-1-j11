package ikrs.util;

import java.text.ParseException;
import java.util.Set;

import ikrs.typesystem.BasicStringType;
import ikrs.typesystem.BasicType;

/**
 * @author Ikaros Kappler
 * @date 2012-05-07
 * @version 1.0.0
 **/


public abstract class AbstractCommandFactory<C extends Command>
    implements CommandFactory<C> {

    /**
     * The parent factory (is null if not exists).
     **/
    private CommandFactory<C> parentFactory;


    /**
     * Create a new command factory.
     **/
    public AbstractCommandFactory() {
	super();

	this.parentFactory = null;
    }
    

    //---BEGIN----------------------- CommandFactory implementation -----------------------

    /**
     * Get a set of all supported commands. This is usually a set of the
     * commands that are understood by this factory plus all commands that
     * are understood by all parent factories (if exist).
     *
     * @return A set containting all commands that are supported by this 
     * factory.
     **/
    public abstract Set<C> getSupportedCommands();

    /**
     * Get the parent factory for this factory.
     * If there is no parent factory present the method returns null.
     *
     * @return The parent factory or null if no such exists.
     **/
    public CommandFactory<C> getParentFactory() {
	return this.parentFactory;
    }

    /**
     * Set the parent factory for this factory.
     * Pass null to clear the parent factory.
     *
     * @param The new parent factory or null to clear.
     **/
    public void setParentFactory( CommandFactory<C> newParent ) {
	this.parentFactory = newParent;
    }


    /**
     * Make a new Command with the given name and params.
     *
     * @param name The command's name.
     * @param params The command's params - in string representation.
     * 
     * @return The new command.
     **/
    public C make( String name,
		   String[] params )
	throws UnknownCommandException,
	CommandStringIncompleteException {
	
	// Build BasicType array
	BasicType[] b = BasicStringType.buildArray( params );
	
	return this.make( name, b );
    }


    /**
     * Make a new Command with the given name and params.
     *
     * @param name The command's name.
     * @param params The command's params - in BasicType representation.
     * 
     * @return The new command.
     **/
    public abstract C make( String name,
			    BasicType[] params )
	throws UnknownCommandException,
	CommandStringIncompleteException;

    
    /**
     * Make a new Command by parsing it from a string.
     *
     * Some command classes might not support this method as the parsing process would
     * be too complex. In this case an UnsupportedOperationException is thrown.
     *
     *
     * @param str The string to parse the command from.
     * 
     * @return The new command.
     * @throws UnsupportedOperationException if the underlying command implementation
     *                                       does not support parsing.
     * @throws CommandStringIncompleteException If the given String lacks some data at the end.
     * @throws ParseException If the given String is malformed.
     **/
    public C parse( String str )
	throws UnsupportedOperationException,
	UnknownCommandException,
	CommandStringIncompleteException,
	ParseException {
	
	//throw new UnsupportedOperationException( "Parser not yet implemented." );
	String params[] = str.split( "\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)+" );
	//for( int i = 0; i < params.length; i++ ) 
	//System.out.println( "--- "+params[i] );
	
	
	return make( params[0],  // Use first 'param' as name
		     params,    
		     1,          // Drop first element from the 'param' list
		     params.length
		     );
	
	
    }
    //---END------------------------- CommandFactory implementation -----------------------


    /**
     * This is a slight modifiaction of the make( String, String[] ) method; but it accepts
     * some bounds for the array.
     **/
    public C make( String name,
		   String[] params,
		   int start,
		   int end )
	throws UnknownCommandException,
	CommandStringIncompleteException {

	// Build BasicType array
	BasicType[] b = BasicStringType.buildArray( params, start, end );
	
	return this.make( name, b );
    }

}