package ikrs.httpd;

import ikrs.typesystem.BasicType;
import ikrs.util.AbstractCommand;
import ikrs.util.DefaultCommandFactory;
import ikrs.util.Command;
import ikrs.util.UnknownCommandException;
import ikrs.util.CommandStringIncompleteException;


/**
 * Problem: the ModuleCommand/~Factory would require an HTTPHandler instance to be passed on
 *          system start. But the system instantiates the HTTPHandler at a later point by
 *          added a new handler to the yucca server.
 *          So the factory requires the handler on instantiation and vice versa.
 *
 * To solve this problem the handler stores the first instance that was created in a static 
 * attribute. When called the ModuleCommand tries to access this field.
 *
 * Warning: this only works if HTTP config uses a shared instance for _all_ listening ports.
 *          See the 'sharedHandlerInstance' attribute in the ikrs.httpd.conf file (server
 *          node).
 *          If the 'sharedHandlerInstance' is not set Yucca will create different handler
 *          instances for each 'server' tag. In this case the command will only work
 *          for the first handler instance created.
 *
 * @author Ikaros Kappler
 * @date 2013-01-09
 * @modified 2013-04-17 Ikaros Kappler (shared handler instance added).
 * @version 1.0.0
 **/


public class ModuleCommandFactory
    extends DefaultCommandFactory {

    public ModuleCommandFactory() {
	
	super();

	this.addSupportedCommand( new ModuleCommand( "HTTPD",
						     null,  // params
						     0      // offset
						     )
				  );
						   

    }

    //protected HTTPHandler getHandler() {
    //	return this.handler;
    //}

    //--- BEGIN --------------------- AbstractCommandFactory implementation ------------------
    /**
     * Make a new Command with the given name and params.
     *
     * @param name The command's name.
     * @param params The command's params - in BasicType representation.
     * 
     * @return The new command.
     **/
    public ModuleCommand make( String name,
			       BasicType[] params )
	throws UnknownCommandException,
	       CommandStringIncompleteException {

	
	//return null;
	if( name == null )
	    throw new NullPointerException( "Command must not be null." );
	
	if( name.equalsIgnoreCase("HTTP") || name.equalsIgnoreCase("HTTPD") ) {

	    if( params.length == 0 )
		throw new CommandStringIncompleteException( "Command " + name + " requires at least one argument: STATUS" );

	    return new ModuleCommand( params[0].getString(),
				     params,
				     1 );

	} else {
	
	    throw new UnknownCommandException( "Unknown yucca/httpd command." );

	}
    }
    //--- END ----------------------- AbstractCommandFactory implementation ------------------



}