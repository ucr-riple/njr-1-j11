package ikrs.httpd;

import java.util.concurrent.ThreadFactory;

import ikrs.util.CustomLogger;

/**
 * This ThreadFactory implementation creates new HTTPServerThreads.
 *
 * @author Ikaros Kappler
 * @date 2012-07-16
 * @version 1.0.0
 **/


public class HTTPServerThreadFactory
    implements ThreadFactory {

    private HTTPHandler handler;
    private CustomLogger logger;

    //private Map<String,HTTPRequestHandler> handlerMap;

    public HTTPServerThreadFactory( HTTPHandler handler,
				    CustomLogger logger 
				    ) {
	this.handler = handler;
	this.logger = logger;
    }
    
    //--- BEGIN ---------------------- ThreadFactory implementation ----------------
    public HTTPServerThread newThread( Runnable r ) {
	
	// Create new thread
	HTTPServerThread t = new HTTPServerThread( this.handler,
						   this.logger,
						   r );

	// Log event
	this.handler.getRuntimeStatistics().reportHTTPRequest();
	
	/*
	return new HTTPServerThread( this.handler,
				     this.logger,
				     r );
	*/
	
	return t;
    }
    //--- END ------------------------ ThreadFactory implementation ----------------

}