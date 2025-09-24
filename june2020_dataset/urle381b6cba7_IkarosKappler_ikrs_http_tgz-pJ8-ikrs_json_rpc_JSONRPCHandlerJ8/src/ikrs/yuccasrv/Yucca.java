package ikrs.yuccasrv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;


import ikrs.io.fileio.FileCopy;
import ikrs.typesystem.BasicBooleanType;
import ikrs.typesystem.BasicStringType;
import ikrs.typesystem.BasicType;
import ikrs.typesystem.BasicTypeException;
import ikrs.typesystem.BasicUUIDType;
import ikrs.util.Command;
import ikrs.util.AbstractCommandLine;
import ikrs.util.DefaultCommand;
import ikrs.util.DefaultCustomLogger;
import ikrs.util.DefaultEnvironment;
import ikrs.util.Environment;
import ikrs.yuccasrv.commandline.YuccaCommandFactory;
import ikrs.yuccasrv.commandline.YuccaLine;
import ikrs.yuccasrv.socketmngr.BindListener;
import ikrs.yuccasrv.socketmngr.BindManager;
import ikrs.yuccasrv.util.ConfigReader;
import ikrs.yuccasrv.util.YuccaLogFormatter;

/**
 * This is the main class of the Yucca server.
 *
 * @author Ikaros Kappler
 * @date 2012-05-02
 * @version 1.0.0
 **/

public class Yucca 
    extends Thread
    implements BindListener {

    private BindManager
	bindManager;

    private Environment<String,BasicType>
	basicEnvironment;

    private YuccaLine
	yuccaLine;

    private Logger
	logger;

    private Map<UUID,ConnectionHandler>
	singleConnectionDispatcher;


    public Yucca( Environment<String,BasicType> environment ) 
	throws IOException {

	super();

	
	this.basicEnvironment = environment;


	/* Create a new global logger */
	this.logger = Logger.getLogger( Constants.DEFAULT_LOGGER_NAME ); 
	LogManager.getLogManager().addLogger( this.logger );
	
	/* Create a new log file handler that writes the logs into 
	 *{home}/.yuccasrv/logs/yucca_{unique_number}.{generation_number}.log 
	 */
	File loggingDirectory = new File( System.getProperty("user.home") + "/.yuccasrv/logs/" );
	loggingDirectory.mkdirs();
	/* This might throw an IOExeption */
	FileHandler fh = new FileHandler( "%h/.yuccasrv/logs/yucca_%u.%g.log",  // pattern
					  5*1024*1024,          // files should not be bigger than 5MB
					  20,                   // one file per log
					  false                 // do not append
					  );
	fh.setFormatter( new YuccaLogFormatter() );
	this.logger.addHandler( fh );
	/* Create a new stream handler that prints the debug data into System.out */
	this.logger.addHandler( new StreamHandler( System.out,
						   new SimpleFormatter()   // better use an XML formatter?
						   )
				);
	/* Set the default log level */
	this.logger.setLevel( Level.SEVERE );
	
	/* Create ther bind manager with the configured logger! */
	//this.bindManager = new BindManager();
	this.bindManager = new BindManager( new DefaultCustomLogger(Constants.DEFAULT_LOGGER_NAME) );

	/* Create a child logger for the bind manager */
	//Logger bindLogger = Logger.getAnonymousLogger();
	//bindLogger.setParent( this.logger );
	//this.bindManager.setLogger( bindLogger );
	//this.bindManager.setLogger( new DefaultCustomLogger(this.bindManager.getClass().getName()) );

	/* Finally listen for events to report them as FINEST */
	this.bindManager.addBindListener( this );

	this.singleConnectionDispatcher = new TreeMap<UUID,ConnectionHandler>();
    }

    /**
     * As the registration of new listening sockets is non-deterministic (starting new
     * threads) the binding and retrieving of connection handlers should be 
     * thread safe. 
     * Only use this method if you want to access the singleConnectionDispatcher!
     * (it's synchronized)
     *
     * @param handler The handler you want to install for the socket.
     * 
     * @return true if the handler was successfully installed; false if a handler for
     *         the given ID is already bound; the listener set is left unchanged
     *         then.
     **/
    private boolean addConnectionHandlerForSocket( ConnectionHandler handler ) {

	synchronized( this.singleConnectionDispatcher ) {
	    
	    ConnectionHandler oldHandler = this.singleConnectionDispatcher.get( handler.getUUID() );
	    if( oldHandler == null ) {

		// OK, does not yet exist
		this.singleConnectionDispatcher.put( handler.getUUID(), handler );

		return true;
	    } else if( handler == oldHandler ) {

		// No change
		return true;

	    } else {

		this.getLogger().finest( "Failed to set connection handler '"+handler.getUUID()+"' for socket. A different handler for the same socket already exists!" );
		return false;

	    }

	}
	
    }

    /**
     * As the registration of new listening sockets is non-deterministic (starting new
     * threads) the binding and retrieving of connection handlers should be 
     * thread safe. 
     * Only use this method if you want to access the singleConnectionDispatcher!
     * (it's synchronized)
     *
     * @param handlerID The ID you want to get the handler for (must be unique!).
     * 
     * @return the handler itself if the handler was found, null otherwise.
     **/
    private ConnectionHandler getConnectionHandlerByUUID( UUID handlerID ) {

	//System.out.println( "ConnectionHandlders="+this.singleConnectionDispatcher );

	synchronized( this.singleConnectionDispatcher ) {

	    return this.singleConnectionDispatcher.get( handlerID ); 

	}

    }

    /**
     * Get the global logger.
     *
     * This is the same logger as returned by LogManager.getLogger( Constants.DEFAULT_LOGGER_NAME );
     **/
    public Logger getLogger() {
	return this.logger;
    }

    public AbstractCommandLine<Command> getCommandLine() {
	return this.yuccaLine;
    }

    public void performQuit( boolean forceQuit ) {
	// Release all sockets 
	// !!! THIS METHOD HAS NO IMPLEMENTATION YET !!!
	this.logger.info( "Closing server sockets." );
	this.bindManager.closeAllServerSockets();
	this.bindManager.finalize( 5L, java.util.concurrent.TimeUnit.SECONDS );

	// Remove listeners
	// ...

	// Force quit all listeners
	

	// Stop command line
	// ...
	if( this.yuccaLine != null ) {
	    this.logger.info( "Stopping command line." );
	    this.yuccaLine.interrupt();
	}

	System.out.println( "Goodbye." );

	if( forceQuit )
	    System.exit( 0 );
    }

    public void performStatus() {
	
	String statusString = this.bindManager.getStatusString();
	this.getLogger().log( Level.INFO,
			      "Socket manager status:\n " + statusString
			      );
	
    }

    public void performPrintLicense() {
	String licenseFile = "gpl-3.0.txt";
	BufferedReader reader = null;
	try {

	    reader = new BufferedReader( new FileReader( new File(licenseFile) ) );
	    String line = null;
	    while( (line = reader.readLine()) != null ) {

		System.out.println( line );

	    }
	    
	    reader.close();

	} catch( IOException e ) {
	    
	    this.getLogger().log( Level.INFO,
				  "[IOException] Failed to print license: " + e.getMessage()
			      );
	    
	} 
    }

    public void performPrintWarranty() {
	Yucca.printWarranty();
    }

    public static void printWarranty() {
	String licenseFile = "warranty.txt";
	BufferedReader reader = null;
	try {

	    reader = new BufferedReader( new FileReader( new File(licenseFile) ) );
	    String line = null;
	    while( (line = reader.readLine()) != null ) {

		System.out.println( line );

	    }
	    
	    reader.close();

	} catch( IOException e ) {
	    
	    System.err.println( "[IOException] Failed to print warranty: " + e.getMessage() );
	    
	} 
    }

    //---BEGIN------------------------- BindListener ------------------------------
    /**
     * @param source The BindManager that reports the event.
     * @param socketID A unique ID to identify the created socket by the use of
     *                 BindManager.getServer*( socketID ).
     **/
    public void serverCreated( BindManager source,
			       UUID socketID ) {
	
	/* Events coming from different managers than ours have to be ignored */
	if( source != this.bindManager ) {
	    this.getLogger().log( Level.FINE,
			      "The serverCreated-event was triggerd by a foreign bindManager! (ignoring event)"
			      );
	    return;
	}


	this.getLogger().log( Level.INFO,
			      "Server created: " + socketID 
			      );

	this.getLogger().log( Level.FINEST,
			      "Bound " + 
			      source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_ADDRESS).getString() + 
			      ":" + 
			      source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_PORT).getString() +  
			      "/" + 
			      source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_PROTOCOL).getString() + 
			      " [ID="+socketID+"]"
			      );
	
	/* Locate the connection handler to forward this event to */
	BasicType handlerID = source.getServerSettings(socketID).get( Constants.KEY_CONNECTION_HANDLERID );
	if( handlerID == null ) {
	    this.getLogger().log( Level.SEVERE,
				  "Fatal error: the serverCreated-event was triggerd but no handler-ID for the socket is defined! (dropping event)"
				  );
	     return;
	}
	try {
	    ConnectionHandler handler = getConnectionHandlerByUUID( handlerID.getUUID() );
	    if( handler == null ) {
		
		/* Ooops, this must not happen! */
		/* A server socket cannot be created without having a unique ConnectionHandler bound! */
		this.getLogger().log( Level.SEVERE,
				      "Fatal error: the serverCreated-event was triggerd but no ConnectionHandler for the socket is installed! (dropping event)"
				      );
		return;
		
	    }

	    /* Else: handler exists -> forward event */
	    handler.serverCreated( source, socketID );

	} catch( BasicTypeException e ) {
	    this.getLogger().log( Level.SEVERE,
				  "Fatal error (type cast): the serverCreated-event was triggerd but the socket's handlerID is incompatible with UUID! (dropping event)"
				  );
	     return;
	}

    }
    

    /**
     * @param source The BindManager that reports the event.
     * @param socketID The server's unique ID.
     * @param e The reported exception.
     * @param isTraumatic This flag tell if the server socket can still
     *                    be used or if it's (probably) broken and should be
     *                    restarted. In the second case the BindManager will
     *                    automatically close and remove the socket to free
     *                    the resources.
     **/
    public void serverError( BindManager source,
			     UUID socketID,
			     Exception e,
			     boolean isTraumatic ) {
	/* Events coming from different managers than ours have to be ignored */
	if( source != this.bindManager ) {
	    this.getLogger().log( Level.FINE,
			      "The serverError-event was triggerd by a foreign bindManager! (ignoring event)"
			      );
	    return;
	}


	this.getLogger().log( Level.INFO,
			      "Server error: " + socketID 
			      );
	
	/* Locate the connection handler to forward this event to */
	BasicType handlerID = source.getServerSettings(socketID).get( Constants.KEY_CONNECTION_HANDLERID );
	if( handlerID == null ) {
	    this.getLogger().log( Level.SEVERE,
				  "Fatal error: the serverError-event was triggerd but no handler-ID for the socket is defined! (dropping event)"
				  );
	     return;
	}
	try {
	    ConnectionHandler handler = getConnectionHandlerByUUID( handlerID.getUUID() );
	    if( handler == null ) {
		
		/* Ooops, this must not happen! */
		/* A server socket cannot be created without having a unique ConnectionHandler bound! */
		this.getLogger().log( Level.SEVERE,
				      "Fatal error: the serverError-event was triggerd but no ConnectionHandler for the socket is installed! (dropping event)"
				      );
		return;
		
	    }

	    /* Else: handler exists -> forward event */
	    handler.serverError( source, 
				 socketID,
				 e, 
				 isTraumatic);
	    
	} catch( BasicTypeException exc ) {
	    this.getLogger().log( Level.SEVERE,
				  "Fatal error (type cast): the serverError-event was triggerd but the socket's handlerID is incompatible with UUID! (dropping event)"
				  );
	     return;
	}
    }


    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public void serverAcceptedTCPConnection( BindManager source,
					     UUID socketID,
					     Socket sock,
					     ConnectionUserID userID ) {

	/* Events coming from different managers than ours have to be ignored */
	if( source != this.bindManager ) {
	    this.getLogger().log( Level.FINE,
			      "The serverAcceptedTCPConnection-event was triggerd by a foreign bindManager! (ignoring event)"
			      );
	    return;
	}


	this.getLogger().log( Level.FINEST,
			      "Server accepted TCP connection: " + socketID 
			      );
	
	/* Locate the connection handler to forward this event to */
	BasicType  handlerID = source.getServerSettings(socketID).get( Constants.KEY_CONNECTION_HANDLERID );
	if( handlerID == null ) {
	    this.getLogger().log( Level.SEVERE,
				  "Fatal error: the serverAcceptedTCPConnection-event was triggerd but no handler-ID for the socket is defined! (dropping event)"
				  );
	     return;
	}
	try {
	    ConnectionHandler handler = getConnectionHandlerByUUID( handlerID.getUUID() );
	    if( handler == null ) {
		
		/* Ooops, this must not happen! */
		/* A server socket cannot be created without having a unique ConnectionHandler bound! */
		this.getLogger().log( Level.SEVERE,
				      "Fatal error: the serverAcceptedTCPConnection-event was triggerd but no ConnectionHandler for the socket is installed! (dropping event)"
				      );
		return;
		
	    }

	    /* Else: handler exists -> forward event */
	    handler.serverAcceptedTCPConnection( source, 
						 socketID,
						 sock,
						 userID );
	    
	} catch( BasicTypeException e ) {
	    this.getLogger().log( Level.SEVERE,
				  "Fatal error (type cast): the serverAcceptedTCPConnection-event was triggerd but the socket's handlerID is incompatible with UUID! (dropping event)"
				  );
	     return;
	}

    }


    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public void serverAcceptedUDPConnection( BindManager source,
					     UUID socketID,
					     DatagramSocket sock,
					     ConnectionUserID userID ) {
	/* Events coming from different managers than ours have to be ignored */
	if( source != this.bindManager ) {
	    this.getLogger().log( Level.FINE,
			      "The serverAcceptedUDPConnection-event was triggerd by a foreign bindManager! (ignoring event)"
			      );
	    return;
	}


	this.getLogger().log( Level.FINEST,
			      "Server accepted UDP connection: " + socketID 
			      );
	
	/* Locate the connection handler to forward this event to */	
	BasicType handlerID = source.getServerSettings(socketID).get( Constants.KEY_CONNECTION_HANDLERID );
	if( handlerID == null ) {
	     this.getLogger().log( Level.SEVERE,
				  "Fatal error: the serverAcceptedUDPConnection-event was triggerd but no handler-ID for the socket is defined! (dropping event)"
				  );
	     return;
	}
	try {
	    ConnectionHandler handler = getConnectionHandlerByUUID( handlerID.getUUID() );
	    if( handler == null ) {
		
		/* Ooops, this must not happen! */
		/* A server socket cannot be created without having a unique ConnectionHandler bound! */
		this.getLogger().log( Level.SEVERE,
				      "Fatal error: the serverAcceptedUDPConnection-event was triggerd but no ConnectionHandler for the socket is installed! (dropping event)"
				      );
		return;
	    }

	    /* Else: handler exists -> forward event */
	    handler.serverAcceptedUDPConnection( source, 
						 socketID,
						 sock,
						 userID );
		
	} catch( BasicTypeException e ) {
	    this.getLogger().log( Level.SEVERE,
				  "Fatal error (type cast): the serverAcceptedUDPConnection-event was triggerd but the socket's handlerID is incompatible with UUID! (dropping event)"
				  );
	     return;
	}

    }

    
    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     **/
    public void serverClosed( BindManager source,
			      UUID socketID ) {

	/* Events coming from different managers than ours have to be ignored */
	if( source != this.bindManager ) {
	    this.getLogger().log( Level.FINE,
			      "The serverClosed-event was triggerd by a foreign bindManager! (ignoring event)"
			      );
	    return;
	}


	this.getLogger().log( Level.FINEST,
			      "Server closed: " + socketID 
			      );
	
	/* Locate the connection handler to forward this event to */
	BasicType handlerID = source.getServerSettings(socketID).get( Constants.KEY_CONNECTION_HANDLERID );
	if( handlerID == null ) {
	     this.getLogger().log( Level.SEVERE,
				  "Fatal error: the serverClosed-event was triggerd but no handler-ID for the socket is defined! (dropping event)"
				  );
	     return;
	}
	try {
	    ConnectionHandler handler = getConnectionHandlerByUUID( handlerID.getUUID() );
	    if( handler == null ) {
		
		/* Ooops, this must not happen! */
		/* A server socket cannot be created without having a unique ConnectionHandler bound! */
		this.getLogger().log( Level.SEVERE,
				      "Fatal error: the serverClosed-event was triggerd but not ConnectionHandler for the socket is installed! (dropping event)"
				      );
		return;
		
	    }

	    /* Else: handler exists -> forward event */
	    handler.serverClosed( source, 
				  socketID );
	    
	} catch( BasicTypeException e ) {
	    this.getLogger().log( Level.SEVERE,
				  "Fatal error (type cast): the serverClosed-event was triggerd but the socket's handlerID is incompatible with UUID! (dropping event)"
				  );
	     return;
	}

    }

    /**
     * This method will be called if the SocketManager is going to terminate.
     * All associated BindListener MUST terminate within the given time.
     *
     * @param time The time value all dependent child threads have to terminate in.
     * @param unit The time unit.
     **/
    public void finalize( long time,
			  java.util.concurrent.TimeUnit unit ) {

	// Distribute the' finalize' call to all child handlers
	Iterator<ConnectionHandler> iter = singleConnectionDispatcher.values().iterator();
	while( iter.hasNext() ) {

	    ConnectionHandler handler = iter.next();

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".finalize(...) Going to tell ConnectionHandler " + handler  );
	   	    
	    handler.finalize( 5, java.util.concurrent.TimeUnit.SECONDS );
	    
	}
	
    }
    //---END--------------------------- BindListener ------------------------------

    protected void performAutoBind() {
	
	// The main environment represents the <yuccasrv> tag from the XML file
	// -> get <server> children
	List<Environment<String,BasicType>> serverList = this.basicEnvironment.getChildren( "SERVER" );
	//System.out.println( "SERVER entries found: "+serverList.size() );
	for( int i = 0; i < serverList.size(); i++ ) {

	    Environment<String,BasicType> server = serverList.get( i );
	    performAutoBind( server );
	    
	    
	}

    }

    protected void performAutoBind( Environment<String,BasicType> server ) {
	
	// The passed environment represents a <server> tag from the XML file
	// -> get <listen> children
	List<Environment<String,BasicType>> listenList = server.getChildren( "LISTEN" );
	
	// For the case the server uses the same handler instance (!) for each
	// listening socket: prepare a map handlerName -> handlerObject
	Map<String,ConnectionHandler> sharedConnectionHandlers = new TreeMap<String,ConnectionHandler>();
	//System.out.println( "LISTEN entries found: "+listenList.size() );
	for( int i = 0; i < listenList.size(); i++ ) {
	    
	    Environment<String,BasicType> listen = listenList.get( i );

	    // Check if 'autobind' flag is set.
	    BasicType autobind = listen.get( Constants.CONFIG_SERVER_AUTOBIND );
	    if( autobind == null || autobind.getBoolean() ) {

		this.getLogger().log( Level.INFO,
				      "Binding server '" + server.get("name") + "' ..." 
				      );
		performAutoBind( server, 
				 listen,
				 sharedConnectionHandlers );
	    }
	    
	    
	}

    }

    protected boolean performAutoBind( Environment<String,BasicType> server,
				       Environment<String,BasicType> listen,
				       Map<String,ConnectionHandler> sharedConnectionHandlers
				    ) {
	this.getLogger().info( "      binding  " + listen.get("HOST") + ":" + listen.get("PORT") + "/" + listen.get("PROTOCOL") + " ..." );	
	
	/* Retrieve the handlerClasse attribute */
	BasicType wrp_handlerClassName = server.get( Constants.CONFIG_SERVER_HANDLERCLASS );
	if( wrp_handlerClassName == null || wrp_handlerClassName.equals("") ) {

	    this.getLogger().severe( "Failed to bind server '" + server.get(Constants.CONFIG_SERVER_NAME) + "': no "+Constants.CONFIG_SERVER_HANDLERCLASS+" defined!" );
	    return false;

	} 
  

	/* Check if the instance should be re-used */
	BasicType wrp_sharedHandlerInstance = server.get( Constants.CONFIG_SERVER_SHAREDHANDLERINSTANCE );
	boolean useSharedHandlerInstance    = wrp_sharedHandlerInstance.getBoolean();
	String handlerClassName             = wrp_handlerClassName.getString();


	/* Retrieve network address */
	String host = null;
	int port;
	//Environment<String,BasicType> additionalSettings   = new DefaultEnvironment<String,BasicType>();
	Environment<String,BasicType> optionalReturnValues = new DefaultEnvironment<String,BasicType>();
	try {

	    /* Handler classname is set -> locate class */
	    
	    Class<?> handlerClass     = null;
	    ConnectionHandler handler = null;
	    
	    // Re-use last handler (if exists)? 
	    if( !useSharedHandlerInstance 
		|| (handler = sharedConnectionHandlers.get(handlerClassName)) == null ) {

		/* Create a new handler instance */
		handlerClass = Class.forName( handlerClassName );
		handler = (ConnectionHandler)handlerClass.newInstance();
		sharedConnectionHandlers.put( handlerClassName, handler );

	    } else {
		
		// NOOP 
	    }


	    /* Apply the config file (if present) to the connection handler */
	    /* Note: this might throw an InstantiationException */
	    handler.init( server,               // pass the whole config to the handler
			  optionalReturnValues 
			  );


	    /* Fetch connect data */
	    host   = listen.get( "host" ).getString();
	    port   = listen.get( "port" ).getInt();
		
	    InetAddress address = InetAddress.getByName( host );

	    /* Before starting the socket: bind the handler under its ID */
	    /* Hint: the socket is definitely initiated after the call of bind(...) */
	    /* Though it cannot be said if the socket thread is already running or not */
	    /* Listening or not ... in this tiny time interval we already might have missed */
	    /* The fist connect event :( */
	    /* This should be fixed by some synchronization concepts one time  ... */
	    boolean handlerBound = this.addConnectionHandlerForSocket( handler );
	    
	    /* Also store the handler-ID into the server settings */
	    listen.put( Constants.KEY_CONNECTION_HANDLERID, new BasicUUIDType(handler.getUUID()) );

	    /* Hint: this method call will create a new thread! */
	    UUID socketID = this.bindManager.bind( address,
						   port,
						   listen    // bindSettings
						   );


	    if( !handlerBound ) {

		this.getLogger().severe( "Fatal error: the server "+server.get(Constants.CONFIG_SERVER_NAME)+" was successfully started but there is already a handler bound to it's socket!" );
		return false;

	    } else {
	    
		/* Handler was successfully installed */
		return true;

	    }

	} catch( ClassNotFoundException e ) {
	   
	    this.getLogger().severe( "Failed to bind server '" + server.get(Constants.CONFIG_SERVER_NAME) + "': handlerClass '"+handlerClassName+"' not found!" );
	    return false;

	} catch( InstantiationException e ) {

	    this.getLogger().severe( "Failed to bind server '" + server.get(Constants.CONFIG_SERVER_NAME) + "': handlerClass '"+handlerClassName+"' could not be instantiated: "+e.getMessage() );
	    return false;

	} catch( IllegalAccessException e ) {

	    this.getLogger().severe( "Failed to bind server '" + server.get(Constants.CONFIG_SERVER_NAME) + "': handlerClass '"+handlerClassName+"' could not be accessed: "+e.getMessage() );
	    return false;

	} catch( GeneralSecurityException e ) {

	    this.getLogger().severe( "Failed to bind server '" + server.get(Constants.CONFIG_SERVER_NAME) + "' due to general security exception: " + e.getMessage() );
	    return false;  
	    
	} catch( BasicTypeException e ) {

	    //e.printStackTrace();
	    this.getLogger().severe( e.getMessage() ); 
	    return false;
	    
	} catch( UnknownHostException e ) {

	    this.getLogger().severe( "Unknown host: " + host ); 
	    return false;

	} catch( IOException e ) {

	    this.getLogger().severe( "[IOException] " + e.getMessage() );
	    return false;

	}
	
    }

    public void performListen( BasicType host,
			       BasicType port,
			       Environment<String,BasicType> serverSettings ) 
	throws UnknownHostException,
	       BasicTypeException,
	       IOException,
	       GeneralSecurityException {

	
	/* First step: resolve hostname */
	InetAddress address = InetAddress.getByName( host.getString() ); // might throw UnknownHostException
	
	this.bindManager.bind( address,
			       port.getInt(),
			       serverSettings
			       );
	
    }
    
    public void performUnlisten( BasicType socketID ) 
	throws BasicTypeException,
	       IOException {

	//System.out.println( "Converting "+socketID.getClass().getName()+" to UUID: "+socketID );
	UUID uuid = socketID.getUUID();
	this.bindManager.release( uuid ); 
	
    }


    public void run() {

	try {
	    
	    System.out.println( "Starting command line ..." );
	    // The method will block here
	    this.yuccaLine.runCommandLine();

	
	} catch( IOException e ) {

	    e.printStackTrace();
	}


    }


    public static String processCustomizedFilePath( String filePath ) {
	if( filePath == null )
	    return null;

	filePath = filePath.replaceAll( "\\{USER_HOME\\}", System.getProperty("user.home") );
	// ...
	
	return filePath;
    }
	

    private static boolean performFirstRun( File configurationFile ) 
	throws IOException {

	File configurationParentFile = configurationFile.getParentFile();


	System.out.println( "It seems this is the first time you are running yucca." );
	System.out.println( "Yucca needs to create a configuration directory at '" + configurationFile.getPath() + "' and store some config files inside." );
	System.out.print( "Is that okay? " );

	BufferedReader in = new BufferedReader( new InputStreamReader(System.in) );
	String line = null;
	do {
	    
	    System.out.println( "Type 'y' or 'n': " );
	    line = in.readLine();

	} while( !line.equals("y") && !line.equals("n") );

	
	if( line.equals("n") )
	    return false;


	File configurationTemplateDirectory = new File( "_.templates" );
	boolean filesCopied = FileCopy.copy( configurationTemplateDirectory, configurationParentFile );
	
		
	// Configuration directory successfully created?
	return filesCopied;
    }

    private static void printSingleLineBox( String[] lines ) {

	// Find longest line
	String line = null;
	for( int i = 0; i < lines.length; i++ ) {
	    if( line == null || (line != null && lines[i] != null && lines[i].length() > line.length()) )
		line = lines[i];
	}

	if( line == null )
	    return; // No lines at all?!

	// Characters in Unicode
	// Does work :)
	StringBuffer b = new StringBuffer();
	b.append( '\u250C' );  // left upper corner
	for( int i = 0; i < line.length(); i++ )
	    b.append( '\u2500' );  // single horizonal line
	b.append( '\u2510' );  // right upper corner
	b.append( "\n" );
	for( int l = 0; l < lines.length; l++ ) {

	    b.append( '\u2502' );  // single vertical line
	    b.append( lines[l] );
	    for( int k = lines[l].length(); k < line.length(); k++ )
		b.append( " " );
	    b.append( '\u2502' );
	    b.append( "\n" );
	}

	b.append( '\u2514' );  // left lower corner
	for( int i = 0; i < line.length(); i++ )
	    b.append( '\u2500' );  
	b.append( '\u2518' );  // right lower corner
	//b.append( "\n" );
	
	System.out.println( b.toString() );
    }

    public static Yucca runYucca( String[] argv ) {

	//Yucca.printWarranty();
	String[] lines = new String[] {
	    "    ikrs.yucca Copyright (C) 2012 Ikaros Kappler    ",
	    "    This program comes with ABSOLUTELY NO WARRANTY; for details type 'warranty'    ",
	    "    This is free software, and you are welcome to redistribute it    ",
	    "    under certain conditions; type `license' for details.    "
	};
	Yucca.printSingleLineBox( lines );

	/* Convert the command line arguments into a command */
	Command myCall = new DefaultCommand( "yucca",
					     BasicStringType.buildArray( argv, 
									 0, 
									 argv.length )
					     );
	
	/* Init the server*/
	Environment<String,BasicType> serverConfig = null;

	/* Pre-locate config file argument */
	String configFileName = null;
	int i = 0;
	while( i < myCall.getParamCount() 
	       && configFileName == null ) {
	    
	    BasicType argument = myCall.getParamAt(i);
	    BasicType nextArgument = (i+1 < myCall.getParamCount() ? myCall.getParamAt(i+1) : null);

	    if( argument.getString().equals("--config") ) {

		if( nextArgument == null ) {

		    System.err.println( "Config file param missing ('" + argument.getString() + "')." );
		    System.exit( 1 );

		} else {

		    configFileName = nextArgument.getString();

		}

	    }

	}

	Yucca yucca = null;
	// Read config
	try {
	    
	    if( configFileName == null ) {

		// serverConfig = ConfigReader.read( new File(System.getProperty("user.home") + "/.yuccasrv/server.xml") );
		//configFileName = System.getProperty("user.home") + "/.yuccasrv/server.xml";
		configFileName = "{USER_HOME}/.yuccasrv/server.xml";
	    } 



	    // Clean up placeholders in config path
	    configFileName  = Yucca.processCustomizedFilePath( configFileName );
	    
	    File configFile = new File( configFileName );
	    // Is this the first run?
	    if( !configFile.exists() ) {

		boolean yuccaDirectoryCreated = Yucca.performFirstRun( configFile );
		if( !yuccaDirectoryCreated ) {

		    System.err.println( " (!) You decided not to create a yucca configuration in '" + configFileName + "'." );
		    System.err.println( " (!) Yucca is not able to run without this file. Stopping." );
		    System.exit( 1 );

		}

	    }


	    System.out.println( "Loading server config '" + configFileName + "' ..." );
	    serverConfig = ConfigReader.read( new File(configFileName) );


	    // Default config successfully read
	    // --> init server
	    yucca = new Yucca( serverConfig );
	    yucca.getLogger().info( "Config read and Yucca server initiated." );
	    
	    
	} catch( IOException e ) {

	    e.printStackTrace();
	    System.exit( 1 );
	    
	}
	
	

	/* Interprete command line arguments */
	i = 0;
	while( i < myCall.getParamCount() ) {
	    
	    BasicType argument = myCall.getParamAt(i);
	    BasicType nextArgument = (i+1 < myCall.getParamCount() ? myCall.getParamAt(i+1) : null);
	    
	    if( argument.getString().startsWith("-") ) {
		if( argument.getString().equalsIgnoreCase("-c") || argument.getString().equalsIgnoreCase("--commandline") ) {
		    serverConfig.put( Constants.KEY_STARTUP_COMMANDLINE, new BasicBooleanType(true) );
		    i++;

		} else if( argument.getString().equalsIgnoreCase("-l") || argument.getString().equalsIgnoreCase("--loglevel") ) {
		    if( nextArgument == null || nextArgument.getString().startsWith("-") ) {
			yucca.getLogger().severe( "The option "+argument.getString()+" requires a value: SEVERE, WARNING, INFO, CONFIG, FINE, FINER or FINEST; ALL and OFF are also valid values." );
			System.exit( 1 );
		    }
		    
		    serverConfig.put( Constants.KEY_STARTUP_LOGLEVEL, nextArgument );
		    i+=2; 

		} else if( argument.getString().equalsIgnoreCase("--config") ) {

		    i+=2;
		    // Ignore [was previously fetched]

		} else {
		    System.out.println( "Unknown option: "+argument.getString() );
		    i++;

		}
	    } else {
		System.out.println( "Unrecognized option: "+argument.getString() );
		i++;
	    }
	    
	}

	// Apply config
	BasicType elem = serverConfig.get( Constants.KEY_STARTUP_LOGLEVEL );
	if( elem != null ) {

	    try {
		yucca.getLogger().setLevel( Level.parse(elem.getString()) );
		yucca.getLogger().log( Level.INFO,
				       "Log level set to " + yucca.getLogger().getLevel() );
		//System.out.println( "Log level set to "+yucca.getLogger().getLevel() );
	    } catch( IllegalArgumentException e ) {
		yucca.getLogger().log( Level.SEVERE,
				       "The passed log level '"+elem+"' is not valid." );
		//e.printStackTrace();
	    }
	}

	// Perform auto bind 
	System.out.println( "Performing auto bind ..." );
	yucca.performAutoBind();
	System.out.println( "Done." );

	// Start commandline?
	if( serverConfig.get(Constants.KEY_STARTUP_COMMANDLINE) != null 
	    && serverConfig.get(Constants.KEY_STARTUP_COMMANDLINE).getBoolean(false) ) {
	    
	    yucca.yuccaLine = new YuccaLine( new YuccaCommandFactory(yucca) );


	    Thread t = new Thread( yucca );
	    t.start();
	}

	
	return yucca;

    }

    public static void main( String[] argv ) {

	runYucca( argv );

    }

}
