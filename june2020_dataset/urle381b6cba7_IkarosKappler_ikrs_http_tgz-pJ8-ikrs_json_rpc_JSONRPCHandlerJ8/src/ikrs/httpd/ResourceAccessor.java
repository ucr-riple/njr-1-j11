package ikrs.httpd;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;

import ikrs.typesystem.BasicType;

/**
 * @author  Ikaros Kappler
 * @date    2012-07-20
 * @version 1.0.0
 **/


public interface ResourceAccessor {


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
    public Resource locate( URI uri,
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