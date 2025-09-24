package ikrs.httpd;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import ikrs.io.fileio.inifile.IniFile;
import ikrs.typesystem.BasicType;
import ikrs.util.CaseInsensitiveComparator;
import ikrs.util.CustomLogger;
import ikrs.util.DefaultEnvironmentFactory;
import ikrs.util.Environment;
import ikrs.util.TreeMapFactory;

/**
 * The HTTP configuration file(s) have become pretty large so the config handling is
 * now located in an extra class.
 *
 * @author Ikaros Kappler
 * @date 2012-12-11
 * @version 1.0.0
 **/



public class HTTPConfiguration {

    /**
     * The HTTP handler.
     **/
    private HTTPHandler handler;

    /**
     * The custom logger.
     **/
    private CustomLogger logger;
    

    /**
     * Creates a new HTTPConfiguration handler.
     *
     * @param handler The HTTP handler to apply the configuration settings to.
     * @param logger  A custom logger to use to write log messages.
     * @throws NullPointerException If the handler is null.
     **/
    public HTTPConfiguration( HTTPHandler handler,
			      CustomLogger logger ) 
	throws NullPointerException {

	super();


	if( handler == null )
	    throw new NullPointerException( "Cannot create a HTTPConfiguration for a null handler." );
	
	this.handler = handler;
	this.logger = logger;

    }

    /**
     * Get the HTTP handler for this configuration.
     *
     * @return The HTTP handler for this configuration.
     **/
    protected HTTPHandler getHandler() {
	return this.handler;
    }

    /**
     * Get the logger for this configuration. If the passed logger (see constructor) is null
     * the method will return this HTTPHandler's logger.
     *
     * @return The logger for this configuration.
     **/
    protected CustomLogger getLogger() {
	if( this.logger == null )
	    return this.getHandler().getLogger();
	else
	    return this.logger;
    }

    /**
     * This method reads the passed HTTP settings from the cnfiguration environment and applies them
     * to the HTTP handler. If the passed settings environment is null the method does nothing.<br/>
     * <br/>
     * The passed configuration comes from an XML configuration file parsed by the yucca server.<br/>
     * The structure is like this:<br/>
     * <br/>
     * &lt;server name="My HTTP Server" handlerClass="ikrs.http.HTTPHandler"&gt;<br/>
     *   &lt;httpConfig&gt;<br/>
     *     &lt;httpSettings configFile=".yuccasrv/ikrs.httpd.conf" /&gt;</br>
     *     &lt;fileHandlers configFile=".yuccasrv/filehandlers.ini" /&gt;</br>
     *     ...<br/>
     *   &lt;/httpConfig&gt;<br/>
     *   ...<br/>
     * &lt;/server&gt;<br/>
     * <br/>
     *
     * @param httpSettings The HTTP settings to apply.
     * @throws ConfigurationException If the passed settings contain any malformed or bad entries or is
     *                                incomplete. Note that earlier settings might have already been applied
     *                                and the handler is only partially configured when this exception
     *                                was thrown.
     **/
    public void applyConfiguration( Environment<String,BasicType> serverConfiguration ) 
	throws ConfigurationException,
	       IOException {
	
	// Try to load HTTP config node (if exists)
	if( serverConfiguration == null ) {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".applyConfiguration(...)",
				  "No param set available for applyConfiguration(): is null. No config loaded." );
	    return;
	}
	  
	
	Environment<String,BasicType> httpConfig = serverConfiguration.getChild( Constants.KEY_HTTPCONFIG );
	if( httpConfig == null ) {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".applyConfiguration(...)",
				  "Param set for applyConfiguration() has no '" + Constants.KEY_HTTPCONFIG + "' node. No config loaded." );
	    return;

	}

  
	this.applyHTTPSettings( httpConfig );
	this.applyFileHandlers( httpConfig );
	
    }

    
    /**
     *
     **/
    private void applyHTTPSettings( Environment<String,BasicType> httpConfig ) 
	throws ConfigurationException,
	       IOException {


	Environment<String,BasicType> httpSettings = httpConfig.getChild( Constants.KEY_HTTPCONFIG_SETTINGS );
	if( httpSettings == null ) {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".applyHTTPSettings(...)",
				  "Param set for applyHTTPSettings() has no '" + Constants.KEY_HTTPCONFIG_SETTINGS + "' node. No settings loaded." );
	    return;

	}


	BasicType wrp_configFilename = httpSettings.get( Constants.KEY_HTTPCONFIG_SETTINGS_FILE );
	if( wrp_configFilename == null ) {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".applyHTTPSettings(...)",
				  "HTTP settings have no '" + Constants.KEY_HTTPCONFIG_SETTINGS_FILE + "' entity. No config file loaded." );
	    return;

	}

	String configFilename = CustomUtil.processCustomizedFilePath( wrp_configFilename.getString() );
	if( configFilename == null || configFilename.length() == 0 ) {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".applyHTTPSettings(...)",
				  "HTTP settings entity '" + Constants.KEY_HTTPCONFIG_SETTINGS_FILE + "' is empty. No config file loaded." );
	    return;

	}
		

	this.getLogger().log( Level.INFO,
			      getClass().getName() + ".applyHTTPSettings(...)",
			      "Loading HTTP config file '" + configFilename+ "' ... " );
	// Load config 
	this.loadConfigurationFile( configFilename );
    }


    private void loadConfigurationFile( String filename ) 
	throws ConfigurationException,
	       IOException {

	
	File file = new File(filename);
	if( !file.exists() || !file.isFile() )
	    throw new ConfigurationException( "Regular file '" + filename + "' not found." );

	
	// This would be the alternative way: create a new environment and copy desired settings.
	//Comparator<String> keyComparator = CaseInsensitiveComparator.sharedInstance;
	//IniFile iniFile = new IniFile( new DefaultEnvironmentFactory<String,BasicType>( new TreeMapFactory<String,BasicType>( keyComparator ) ) );
	IniFile iniFile = new IniFile();

	// Don't use a factory/new environment here. Read the ini settings directly into the global environment.
	// This might throw an IOException
	iniFile.read( new File(filename),
		      this.getHandler().getGlobalConfiguration()   // Read directly into globals
		      );

	this.configureDocumentRoot();
	this.configureSessionTimeout();
	this.configureErrorDocuments();
	this.configureCGIHeaderMap();

	// The settings are now already applied :)
	this.getLogger().log( Level.INFO, 
			      getClass().getName() + "loadConfigurationFile(...)",
			      "Config file loaded." );
	

    }

    


    private void configureDocumentRoot() {

	// Afterwards try to apply new document root
	BasicType wrp_documentRoot = this.getHandler().getGlobalConfiguration().get( Constants.CKEY_HTTPCONFIG_DOCUMENT_ROOT );
	if( wrp_documentRoot != null ) {

	    String documentRoot = CustomUtil.processCustomizedFilePath( wrp_documentRoot.getString() );
	    this.getLogger().log( Level.INFO, 
			      getClass().getName() + "loadConfigurationFile(...)",
			      "Set up DOCUMENT_ROOT to '" + documentRoot + "' ..." );
	    this.getHandler().setDocumentRoot( new File(documentRoot) );

	}
    }

    private void configureSessionTimeout() {

	BasicType wrp_sessionTimeout = this.getHandler().getGlobalConfiguration().get( Constants.CKEY_HTTPCONFIG_SESSION_TIMEOUT );
	if( wrp_sessionTimeout != null ) {
	    
	    Integer sessionTimeout = wrp_sessionTimeout.getInt();
	    this.getLogger().log( Level.INFO, 
			      getClass().getName() + "loadConfigurationFile(...)",
			      "Set up SESSION_TIMEOUT to '" + sessionTimeout + "' ..." );
	    this.getHandler().getSessionManager().setSessionTimeout( sessionTimeout.intValue() );

	}

    }

    private void configureErrorDocuments() {

	// Find default ERROR_DOCUMENT settings
	for( int statusCode = 300; statusCode < 599; statusCode++ ) {

	    // Build key.
	    String ckey = Constants.CKEY_HTTPCONFIG_ERROR_DOCUMENT_BASE.replaceAll( "\\{STATUS_CODE\\}", 
										    Integer.toString(statusCode) );

	    // Try to locate key
	    BasicType wrp_errorDocumentURI = this.getHandler().getGlobalConfiguration().get( ckey );
	    if( wrp_errorDocumentURI == null )
		continue;

	    try {
		
		String strURI = wrp_errorDocumentURI.getString(null);
		if( strURI == null ) {
		    this.getHandler().getLogger().log( Level.SEVERE,
						       getClass().getName() + ".configureErrorDocuments(...)",
						       "Failed to init default error document map for key " + ckey + ": value is null." ); 
		} else {
		    this.getHandler().getErrorDocumentMap().put( new Integer(statusCode),
								 new URI(strURI) );
		    this.getHandler().getLogger().log( Level.INFO,
						       getClass().getName() + ".configureErrorDocuments(...)",
						       "Error document for " + statusCode + " set to " + strURI ); 
		}

	    } catch( java.net.URISyntaxException e ) {

		this.getHandler().getLogger().log( Level.SEVERE,
						   getClass().getName() + ".configureErrorDocuments(...)",
						   "[URISyntaxException] Failed to init default error document map for key " + ckey + " '" + wrp_errorDocumentURI.getString() + "': " + e.getMessage() );

	    }

	}


    }

    private void configureCGIHeaderMap() {

	// Find the child environment for the 'cgi_map_headers' section
	Environment<String,BasicType> cgiHeaderMap = 
	    this.getHandler().getGlobalConfiguration().getChild( Constants.CKEY_HTTPCONFIG_SECTION_CGI_MAP_HEADERS );
	if( cgiHeaderMap == null ) {

	     this.getLogger().log( Level.WARNING, 
			      getClass().getName() + ".configureCGIHeaderMap(...)",
			      "The HTTP config has no '" + Constants.CKEY_HTTPCONFIG_SECTION_CGI_MAP_HEADERS + "' section. No headers will be mapped to the CGI enviroment." );
	     return;

	}

	Iterator<Map.Entry<String,BasicType>> iter = cgiHeaderMap.entrySet().iterator();
	while( iter.hasNext() ) {

	    Map.Entry<String,BasicType> entry = iter.next();
	    if( entry.getKey() == null 
		|| entry.getKey().length() == 0 
		|| entry.getValue() == null 
		|| !entry.getValue().getBoolean() ) {

		continue;
	    }

	    this.getHandler().getCGIMapHeadersSet().add( entry.getKey() );

	}

	this.getLogger().log( Level.INFO, 
			      getClass().getName() + ".configureCGIHeaderMap(...)",
			      "Set up cgiHeaderMap: " + this.getHandler().getCGIMapHeadersSet() );
	/*
	Set<String> includeHeaderSet = new TreeSet<String>( CaseInsensitiveComparator.sharedInstance );
	    
	BasicType wrp_documentRoot = this.getHandler().getGlobalConfiguration().get( Constants.CKEY_HTTPCONFIG_DOCUMENT_ROOT );
	if( wrp_documentRoot != null ) {

	    String documentRoot = CustomUtil.processCustomizedFilePath( wrp_documentRoot.getString() );
	    this.getLogger().log( Level.INFO, 
			      getClass().getName() + ".loadConfigurationFile(...)",
			      "Set up DOCUMENT_ROOT to '" + documentRoot + "' ..." );
	    this.getHandler().setDocumentRoot( new File(documentRoot) );

	}
	*/
    }


    private void applyFileHandlers( Environment<String,BasicType> httpConfig ) 
	throws ConfigurationException {


	Environment<String,BasicType> httpSettings = httpConfig.getChild( Constants.KEY_HTTPCONFIG_FILEHANDLERS );
	if( httpSettings == null ) {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".applyFileHandlers(...)",
				  "Param set for applyFileHandlers() has no '" + Constants.KEY_HTTPCONFIG_FILEHANDLERS + "' node. No settings loaded." );
	    return;

	}


	BasicType wrp_configFilename = httpSettings.get( Constants.KEY_HTTPCONFIG_FILEHANDLERS_FILE );
	if( wrp_configFilename == null ) {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".applyFileHandlers(...)",
				  "HTTP settings have no '" + Constants.KEY_HTTPCONFIG_FILEHANDLERS_FILE + "' entity. No config file loaded." );
	    return;

	}

	String configFilename = CustomUtil.processCustomizedFilePath( wrp_configFilename.getString() );
	if( configFilename == null || configFilename.length() == 0 ) {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".applyFileHandlers(...)",
				  "HTTP settings entity '" + Constants.KEY_HTTPCONFIG_FILEHANDLERS_FILE + "' is empty. No config file loaded." );
	    return;

	}
		

	this.getLogger().log( Level.INFO,
			      getClass().getName() + ".applyFileHandlers(...)",
			      "Loading file handlers from file '" + configFilename+ "' ... " );
	// Load config 
	this.getHandler().initFileHandlers( new File(configFilename) );

    }

}