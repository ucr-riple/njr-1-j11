package ikrs.httpd.resource;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;

import ikrs.httpd.AuthorizationException;
import ikrs.httpd.ConfigurationException;
import ikrs.httpd.DataFormatException;
import ikrs.httpd.HeaderFormatException;
import ikrs.httpd.PostDataWrapper;
import ikrs.httpd.Resource;
import ikrs.httpd.ResourceAccessor;
import ikrs.httpd.HTTPHandler;
import ikrs.httpd.HTTPHeaders;
import ikrs.httpd.UnsupportedFormatException;

import ikrs.typesystem.BasicType;

/**
 * @autor Ikaros Kappler
 * @date 2012-07-23
 * @version 1.0.0
 **/



public abstract class AbstractResourceAccessor 
    implements ResourceAccessor {


    /**
     * The top level HTTP handler.
     **/
    private HTTPHandler httpHandler;


    /**
     * ... 
     **/
    public AbstractResourceAccessor( HTTPHandler handler ) {
	super();
	
	this.httpHandler = handler;
    }


    /**
     * Get the top level http handler.
     **/
    public HTTPHandler getHTTPHandler() {
	return this.httpHandler;
    }


    /**
     * This method locates the desired resource addressed by the given URI.
     *
     * @throws ResouceMissingException If the specified resource cannot be found.
     * @throws AuthorizationException If the requested resource requires authorization.
     * @throws HeaderFormatException If the passed headers are malformed.
     * @throws DataFormatException If the passed data is malformed.
     * @throws ConfigurationException If the system encounters any server configuration issues.
     * @throws SecurityException If the requested resource is not accessible (forbidden).
     * @throws IOException If any IO errors occur.
     **/
    public abstract Resource locate( URI uri,
				     HTTPHeaders headers,
				     PostDataWrapper postData,
				     Map<String,BasicType> additionalSettings,
				     Map<String,BasicType> optionalReturnSettings,
				     UUID sessionID
				     )
	throws MissingResourceException,
	       AuthorizationException,
	       HeaderFormatException,
	       DataFormatException,
	       UnsupportedFormatException,
	       ConfigurationException,
	       SecurityException,
	       IOException;


}

