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
 * This is the default LISTEN command implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-05-09
 * @version 1.0.0
 **/

public class CommandListen
    extends YuccaCommand {

    public CommandListen() {

	super( "LISTEN" );
    }

   
    //---BEGIN------------------------ AbstractCommand/YuccaCommand implementation ---------------------
    /**
     * This method handles the LISTEN command's params.
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
	
	// The LISTEN listen command is described as follows:
	// LISTEN [-p [protocol]] [-b [backlog]] host port
	
	int i = 0;
	int minNonOptionalParamCount = 2;

	String protocol = null;
	int backlog = -1;
	// Map<String,BasicType> serverSettings = new TreeMap<String,BasicType>( CaseInsensitiveComparator.sharedInstance );
	TreeMapFactory<String,BasicType> mapFactory = new TreeMapFactory<String,BasicType>( CaseInsensitiveComparator.sharedInstance );
	Environment<String,BasicType> serverSettings = new DefaultEnvironment<String,BasicType>( mapFactory );

	while( i < this.getParamCount() && this.getParamAt(i).getString().startsWith("-") ) {
		
		BasicType argument = this.getParamAt(i);

		// Argument value missing?
		if( i+1 >= this.getParamCount() ) {
		    this.getFactory().getServer().getLogger().log( Level.SEVERE, 
								   "Missing argument value for '"+argument.getString()+"'." );
		    return 1; // implies error
		}

		BasicType argumentValue = this.getParamAt( i+1 );
		
		if( argument.getString().equalsIgnoreCase("-p") || argument.getString().equalsIgnoreCase("--protocol") ) {
		    if( argumentValue.getString().startsWith("-") ) {
			this.getFactory().getServer().getLogger().log( Level.SEVERE, 
								       "Missing argument value for '"+argument.getString()+"'." );
			return 2; // implies error
		    } 
		    
		    serverSettings.put( Constants.CONFIG_SERVER_PROTOCOL, argumentValue );
		    i += 2;
		    
		} else if( argument.getString().equalsIgnoreCase("-b") || argument.getString().equalsIgnoreCase("--backlog") ) {
		    if( argumentValue.getString().startsWith("-") ) {
			this.getFactory().getServer().getLogger().log( Level.SEVERE, 
								       "Missing argument value for '"+argument.getString()+"'." );
			return 2; // implies error
		    } 
		    
		    serverSettings.put( Constants.CONFIG_SERVER_BACKLOG, argumentValue );
		    i += 2;

		} else {
		    
		    this.getFactory().getServer().getLogger().log( Level.SEVERE, 
								   "Unknown argument '"+argument.getString()+"'." );
		    return 97; // implies error

		}
		
	}
	/*
	} catch( BasicTypeException e ) {
	    // The entered argument type did not match : (
	    this.getFactory().getServer().getLogger().log( Level.SEVERE, 
							   "Invalid argument value for '"+this.getParamAt(i).getString()+"': " + e.getMessage() );
	    return 98;  // implies error
	    }*/

	/* At this point all optional params beginning with '-' were consumed */
	if( i+minNonOptionalParamCount > this.getParamCount() ) {
	    
	    this.getFactory().getServer().getLogger().log( Level.SEVERE, 
							   "Too few arguments for '"+getName()+"'" );
	    return 99; // implies error

	}

	// OK. Take the last arguments as the required ones
	BasicType host = this.getParamAt( i );
	BasicType port = this.getParamAt( i+1 );
	serverSettings.put( Constants.CONFIG_SERVER_ADDRESS, host );
	serverSettings.put( Constants.CONFIG_SERVER_PORT, port );

	/* Try to execute command through server */
	try {




	    //this.getFactory().getServer().performQuit( forceQuit );
	    this.getFactory().getServer().performListen( host, 
							 port, 
							 serverSettings 
							 );
	    //this.getFactory().getServer().getLogger().log( Level.INFO, 
	    //						   "New server created." );
	    return 0; // implies SUCCESS :)




	} catch( UnknownHostException e ) {
	    
	    this.getFactory().getServer().getLogger().log( Level.SEVERE, 
							   "Unknown host: " + host );
	    return 100; // implies error
	} catch( BasicTypeException e ) {
	    
	    this.getFactory().getServer().getLogger().log( Level.SEVERE,
							   "[Type error] " + e.getMessage() );
	    return 101; // implies error
	} catch( IOException e ) {
	    
	    this.getFactory().getServer().getLogger().log( Level.SEVERE,
							   "[IOException] " + e.getMessage() );
	    return 102; // implies error
	} catch( GeneralSecurityException e ) {
	    
	    this.getFactory().getServer().getLogger().log( Level.SEVERE,
							   "[GeneralSecurityException] " + e.getMessage() );
	    return 102; // implies error
	}
    }
    //---END-------------------------- AbstractCommand implementation ---------------------

}