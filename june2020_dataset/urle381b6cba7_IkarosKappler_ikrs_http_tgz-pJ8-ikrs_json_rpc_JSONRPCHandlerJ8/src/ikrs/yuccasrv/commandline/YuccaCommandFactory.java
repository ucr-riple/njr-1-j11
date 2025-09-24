package ikrs.yuccasrv.commandline;

/**
 * @author Ikaros Kappler
 * @date 2012-05-07
 * @version 1.0.0
 **/

import java.text.ParseException;


import ikrs.typesystem.BasicType;
import ikrs.util.DefaultCommandFactory;
import ikrs.util.Command;
import ikrs.util.CommandFactory;
import ikrs.util.CommandStringIncompleteException;
import ikrs.util.DefaultCommand;
import ikrs.util.UnknownCommandException;
import ikrs.yuccasrv.Yucca;

public class YuccaCommandFactory
    extends DefaultCommandFactory
    implements CommandFactory<Command> {


    /* The actual yucca server */
    private Yucca
	server;

    /**
     * The constructor.
     *
     * The server instanced will be delegated to all commands that are made with this factory.
     *
     * @param server If available pass the server instance; if not available, just pass null.
     *
     **/
    public YuccaCommandFactory( Yucca server ) {
	super();

	this.server = server;

	
	this.addSupportedCommand( new CommandHelp( this, null, null ) );   // The help command needs to know its factory
	this.addSupportedCommand( new CommandLicense() );
	this.addSupportedCommand( new CommandListen() );
	this.addSupportedCommand( new CommandLogLevel() );
	this.addSupportedCommand( new CommandQuit() );
	this.addSupportedCommand( new CommandStatus() );
	this.addSupportedCommand( new CommandUnlisten() );
	this.addSupportedCommand( new CommandVersion() );
	this.addSupportedCommand( new CommandWarranty() );
    }

    /**
     * Get the factories server instance.
     *
     * Warning: it is not guaranteed that the server is set; in this case the method returns null.
     *
     * @return The YuccaServer instance or null if not set.
     **/
    protected Yucca getServer() {
	return this.server;
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

	YuccaCommand cmd = null;
	if( this.getParentFactory() != null ) {
	    try {
		return this.getParentFactory().make( name, params );
	    } catch( UnknownCommandException e ) {
		// NOOP (try to recognize other known commands)
	    }
	} 

	if( name.equalsIgnoreCase("LISTEN") || name.equalsIgnoreCase("BIND") ) 
	    cmd = new CommandListen();
	else if( name.equalsIgnoreCase("HELP") || name.equalsIgnoreCase("?") ) 
	    cmd = new CommandHelp( this, name, params );    // The help command needs to know its factory
	else if( name.equalsIgnoreCase("QUIT") || name.equalsIgnoreCase("EXIT") ) 
	    cmd = new CommandQuit();
	else if( name.equalsIgnoreCase("UNLISTEN") || name.equalsIgnoreCase("RELEASE") || name.equalsIgnoreCase("UNBIND") ) 
	    cmd = new CommandUnlisten();
	else if( name.equalsIgnoreCase("LICENSE") ) 
	    cmd = new CommandLicense();
	else if( name.equalsIgnoreCase("LOGLEVEL") ) 
	    cmd = new CommandLogLevel();
	else if( name.equalsIgnoreCase("WARRANTY") ) 
	    cmd = new CommandWarranty();
	else if( name.equalsIgnoreCase("STATUS") ) 
	    cmd = new CommandStatus();
	else if( name.equalsIgnoreCase("VERSION") ) 
	    cmd = new CommandVersion();
	else
	    throw new UnknownCommandException( "Unknown command: '"+name+"'.", name );


	cmd.setName( name );
	cmd.setParams( params );
	cmd.setFactory( this );

	return cmd;
    }
    //---END------------------------- AbstractCommandFactory implementation -----------------------

    /**
     * This method should NOT be used any more. It was replaced by 
     * CommandFactory.getSupportedCommands().
     *
     * @deprecated
     **/
    public static String[] getImplementedCommands() {
	return new String[] {
	    "HELP", "LICENSE", "LISTEN", "STATUS", "UNLISTEN", "WARRANTY", "QUIT"
	};
    }
    
    
}