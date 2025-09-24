package ikrs.httpd;

import java.io.IOException;
import java.util.Map;
import java.util.MissingResourceException;

import ikrs.httpd.AuthorizationException;
import ikrs.httpd.DataFormatException;
import ikrs.httpd.HeaderFormatException;
import ikrs.typesystem.BasicType;


/**
 * This interface is meant to wrap prepared HTTP reply objects.
 *
 * @author Ikaros Kappler
 * @date 2012-07-16
 * @version 1.0.0
 **/

public interface PreparedHTTPResponse {


    /**
     * This method must fully prepare the HTTP response. It is not acceptable that any steps that can be done
     * during preparation are made during the execute()-process!
     *
     * This means that all required ressources must be acquired (use locks), all headers prepared (by the use
     * of addResponseHeader(String,String) or getResponseHeaders()) and perform all necessary security checks.
     *
     * @param optionalReturnSettings This (optional, means may be null) map can be used to retrieve internal values
     *                               for error recovery.
     *
     * @throws MalformRequestException If the passed HTTP request headers are malformed and cannot be processed.
     * @throws UnsupportedVersionException If the headers' HTTP version is not supported (supported versions are
     *                                     1.0 and 1.1).
     * @throws UnsupportedMethodException If the request method is valid but not supported (status code 405).
     * @throws UnknownMethodException If the headers' method (from the request line) is unknown.
     * @throws ConfigurationException If the was a server configuration issue the server cannot work properly with.
     * @throws MissingResourceException If the requested resource cannot be found.
     * @throws AuthorizationException If the requested resource requires authorization.
     * @throws HeaderFormatException If the passed headers are malformed.
     * @throws DataFormatException If the passed data is malformed.
     * @throws SecurityException If the request cannot be processed due to security reasons.
     * @throws IOException If any IO errors occur.
     **/
    public void prepare( Map<String,BasicType> optionalReturnSettings ) 
	throws MalformedRequestException,
	       UnsupportedVersionException,
	       UnsupportedMethodException,
	       UnknownMethodException,
	       ConfigurationException,
	       MissingResourceException,
	       AuthorizationException,
	       HeaderFormatException,
	       DataFormatException,
	       UnsupportedFormatException,
	       ParametrizedHTTPException,
	       SecurityException,
	       IOException; 

   

    /**
     * This method executes the prepared response; this means that all necessary resources will be accessed,
     * the actual reply built and sent back to the client.
     *
     * Note that all resources already need to be aquired and all security checks to be done _before_ this
     * method is called.
     *
     * @throws IOException If any IO errors occur.
     **/
    public void execute()
	throws IOException;


    /**
     * This method will be called in the final end - even if the execute() method failed.
     *
     * It has to clean up, release resources and all locks!
     **/
    public void dispose();


    /**
     * This method return true if (and only if) this response is already prepared.
     * In the true-case the prepare()-method should not have any effects.
     **/
    public boolean isPrepared();


    /**
     * The method returns true if (and only if) this response was already executed.
     **/
    public boolean isExecuted();


    /**
     * The method returns true if (and only if) this response already disposed.
     **/
    public boolean isDisposed();

}
