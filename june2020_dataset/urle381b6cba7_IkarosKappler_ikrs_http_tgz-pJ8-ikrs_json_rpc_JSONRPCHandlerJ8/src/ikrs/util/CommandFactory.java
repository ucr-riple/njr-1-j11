package ikrs.util;

import java.text.ParseException;
import java.util.Set;

import ikrs.typesystem.BasicType;

/**
 * This is a factory class to create Command instances.
 *
 * Some CommandFactories might also be capable to parse Commands
 * from a given String.
 *
 * @author Ikaros Kappler
 * @date 2012-05-07
 * @version 1.0.0
 **/


public interface CommandFactory<C extends Command> {

    /**
     * Get a set of all supported commands. This is usually a set of the
     * commands that are understood by this factory plus all commands that
     * are understood by all parent factories (if exist).
     *
     * @return A set containting all commands that are supported by this 
     * factory.
     **/
    public Set<C> getSupportedCommands();

    /**
     * Get the parent factory for this factory.
     * If there is no parent factory present the method returns null.
     *
     * @return The parent factory or null if no such exists.
     **/
    public CommandFactory<C> getParentFactory();

    /**
     * Set the parent factory for this factory.
     * Pass null to clear the parent factory.
     *
     * @param The new parent factory or null to clear.
     **/
    public void setParentFactory( CommandFactory<C> newParent );
    
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
	CommandStringIncompleteException;


    /**
     * Make a new Command with the given name and params.
     *
     * @param name The command's name.
     * @param params The command's params - in BasicType representation.
     * 
     * @return The new command.
     **/
    public C make( String name,
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
	ParseException;
	


}