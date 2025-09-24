package ikrs.httpd;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import ikrs.util.CustomLogger;

/**
 * This FileHandler interface is meant for HTTP resources that represent executable files (in any way)
 * inside the document root (such as CGI scripts, system commands, executables, ...).
 *
 * As configuration files such as .htaccess allow to define file handlers (AddHandler and SetHandler 
 * directives) there is the need to summarize those handlers together under one general interface.
 *
 *
 * IMPLEMENTATION NOTE: subclasses should implement an empty-argument-list constructor to make the
 *                      class instantiable using the Class.newInstance() method!
 *
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-29
 * @modified 2013-01-07 [new method: requiresExistingFile()]
 * @version 1.0.0
 **/

public interface FileHandler {

    /**
     * Get get FileHandler's global HTTPHandler.
     *
     * Warning: as subclasses might be instantiated using the Class.newInstance() method
     *          in some very unusual cases the returned handler might be null. In that case
     *          be sure you have the handler set before by the use of setHTTPHandler(...).
     *
     * @return The global HTTP handler (if available).
     **/
    public HTTPHandler getHTTPHandler();

    /**
     * Set the global HTTP handler.
     *
     * @param handler The new handler (must not be null).
     * @throws NullPointerException If handler is null.
     **/
    public void setHTTPHandler( HTTPHandler handler )
	throws NullPointerException;


    /**
     * Get the custom logger to use to write log messages.
     *
     * Warning: as subclasses might be instantiated using the Class.newInstance() method
     *          in some very unusual cases the returned logger might be null. In that case
     *          be sure you have the logger set before by the use of setLogger(...).
     *
     * @return The logger to use (if available).
     **/
    public CustomLogger getLogger();


    /**
     * Set the logger to use.
     *
     * @param logger The new logger (must not be null).
     * @throws NullPointerException If logger is null.
     **/
    public void setLogger( CustomLogger logger )
	throws NullPointerException;


    /**
     * Most file handlers operate on existing files that are located inside the local file 
     * system (such as the default handler does for simple file delivery).
     *
     * Some file handlers operate on virtual file systems, which means that the request URI does
     * not necessarily address an existing file but a symbol only the handler may know. 
     *
     * The global HTTP handler needs to know if to throw a MissingResourceException (resulting
     * in a 404) if a requested file does not exists --- or if to ignore that fact and simply 
     * continue.
     *
     * This method tells how to proceed.
     *
     * If your implementation returns true this handler will not be called at all; the request
     * processing will directly stop raising an HTTP status 404.
     *
     * @return true if this file handler definitely requires existing files. The process(...) 
     *              method will never be called if the requested file does not exist in that case.
     **/
    public boolean requiresExistingFile();


    /**
     * The 'process' method is very generic. It depends on the underlying implementation how the passed
     * file should be processed.
     *
     * @param sessionID   The current session's ID.
     * @param headers     The HTTP request headers.
     * @param postData    The HTTP post data; if the method is not HTTP POST the 'postData' should be null
     *                    (or empty).
     * @param file        The requested file itself (inside the local file system).
     * @param requestURI  The requested URI (relative to DOCUMENT_ROOT).
     **/
    public Resource process( UUID sessionID,
			     HTTPHeaders headers,
			     PostDataWrapper postData,
			     File file,
			     URI requestURI )
	throws IOException,
	       HeaderFormatException,
	       DataFormatException,
	       UnsupportedFormatException;


}
