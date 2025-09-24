package ikrs.yuccasrv;

/**
 * The YUCCA constants.
 *
 * @author Ikaros Kappler
 * @date 2012-03-25
 * @modified 2012-11-28 Ikaros Kappler; SSL constants.
 * @modified 2013-07-25 Ikaros Kappler (fixed the log handler settings).
 * @version 1.0.1
 **/

public class Constants {

    public static final String VERSION                               = "1.0.1.beta";

    public static final String KEY_ID                                = "id";
    public static final String KEY_CONNECTION_COUNT                  = "connection_count";

    public static final String KEY_CONNECTION_HANDLERID              = "CONNECTION_HANDLER.ID";

    public static final String KEY_STARTUP_COMMANDLINE               = "STARTUP_COMMANDLINE";
    public static final String KEY_STARTUP_LOGLEVEL                  = "STARTUP_LOGLEVEL";

    public static final String KEY_PROTOCOL                          = "PROTOCOL";  
     
    public static final String NAME_PROTOCOL_TCP                     = "TCP";
    public static final String NAME_PROTOCOL_UDP                     = "UDP";

    public static final String CONFIG_SERVER_NAME                    = "name";
    public static final String CONFIG_SERVER_HANDLERCLASS            = "handlerClass";
    public static final String CONFIG_SERVER_ADDRESS                 = "address";
    public static final String CONFIG_SERVER_PORT                    = "port";
    public static final String CONFIG_SERVER_PROTOCOL                = "protocol";
    public static final String CONFIG_SERVER_BACKLOG                 = "backlog";
    public static final String CONFIG_SERVER_AUTOBIND                = "autobind";
    public static final String CONFIG_SERVER_SSL                     = "ssl";
    public static final String CONFIG_SERVER_SHAREDHANDLERINSTANCE   = "sharedHandlerInstance";

    public static final String CONFIG_SERVER_LISTEN_PROPERTY         = "property";
    public static final String CONFIG_SERVER_LISTEN_PROPERTY_NAME    = "name";
    public static final String CONFIG_SERVER_LISTEN_PROPERTY_VALUE   = "value";

    public static final String CONFIG_SERVER_SSL_KEYSTORE            = "javax.net.ssl.keyStore";
    public static final String CONFIG_SERVER_SSL_KEYSTOREPASSWORD    = "javax.net.ssl.keyStorePassword";
    public static final String CONFIG_SERVER_SSL_KEYSTORETYPE        = "javax.net.ssl.keyStoreType";

    public static final String CONFIG_SERVER_SSL_TRUSTSTORE          = "javax.net.ssl.trustStore";
    public static final String CONFIG_SERVER_SSL_TRUSTSTOREPASSWORD  = "javax.net.ssl.trustStorePassword";
    public static final String CONFIG_SERVER_SSL_TRUSTSTORETYPE      = "javax.net.ssl.trustStoreType";

    public static final String CONFIG_SERVER_SSL_NEEDCLIENTAUTH      = "needClientAuth";
    public static final String CONFIG_SERVER_SSL_WANTCLIENTAUTH      = "wantClientAuth";
    




    /* This is the logger name all components should use to get the global logger via LogManager.getLogger(String) */
    public static final String DEFAULT_LOGGER_NAME                   = "DEFAULT_YUCCA_LOGGER";

    

}