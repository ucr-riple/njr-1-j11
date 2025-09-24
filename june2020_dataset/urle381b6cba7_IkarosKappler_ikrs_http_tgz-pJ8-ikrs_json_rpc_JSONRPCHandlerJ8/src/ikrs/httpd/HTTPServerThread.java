package ikrs.httpd;


import java.util.logging.LogManager;
import java.util.logging.Level;

import ikrs.util.CustomLogger;

/**
 * @author Ikaros Kappler
 * @date 2012-07-16
 * @version 1.0.0
 **/


public class HTTPServerThread 
    extends Thread
    implements Runnable {



    private HTTPHandler httpHandler;

    private CustomLogger logger;

    private Runnable runnable;

    public HTTPServerThread( HTTPHandler httpHandler,
			     CustomLogger logger,
			     
			     Runnable r
			     ) {
	super();

	this.httpHandler = httpHandler;
	this.logger = logger;
	this.runnable = r;
    }


    public void run() {

	// LogManager.get( Constants.NAME_DEFAULT_LOGGER ).log( Level.INFO,
	this.logger.log( Level.INFO,
			 getClass().getName(),
			 "Started." );

	// Delegate the call to the given Runnable
	this.runnable.run();
			

	this.logger.log( Level.INFO,
			 getClass().getName(),
			 "Finished." );
	
    }



}