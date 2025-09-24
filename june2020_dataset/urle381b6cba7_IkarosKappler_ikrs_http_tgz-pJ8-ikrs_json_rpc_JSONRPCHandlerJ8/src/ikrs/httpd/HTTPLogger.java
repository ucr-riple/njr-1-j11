package ikrs.httpd;

/**
 * A listenable extension of the default ikrs.util.CustomLogger class.
 *
 * @author Ikaros Kappler
 * @date 2013-05-08
 * @version 1.0.0
 **/

import java.util.logging.Level;

import ikrs.util.DefaultCustomLogger;


public class HTTPLogger
    extends DefaultCustomLogger {
    
    private HTTPHandler handler;

    public HTTPLogger( String loggerName,
		       HTTPHandler handler ) {
	super( loggerName );

	this.handler = handler;
    }

    private HTTPHandler getHandler() {
	return this.handler;
    }

    //--- BEGIN ---------------- Override DefaultCustomLogger --------------------
    public void log( Level level,
		     String trace,
		     String msg ) {

	// Forward the call the the default logger
	super.log( level, trace, msg );

	// Then add the event to the HTTPD statistics
	if( level.equals(Level.SEVERE) ) {
	    this.getHandler().getRuntimeStatistics().reportSevere();
	} else if( level.equals(Level.WARNING) ) {
	    this.getHandler().getRuntimeStatistics().reportWarning();
	} else if( level.equals(Level.INFO) ) {
	    this.getHandler().getRuntimeStatistics().reportInfo();
	} else if( level.equals(Level.CONFIG) ) {
	    this.getHandler().getRuntimeStatistics().reportConfig();
	} else if( level.equals(Level.FINE) ) {
	    this.getHandler().getRuntimeStatistics().reportFine();
	} else if( level.equals(Level.FINER) ) {
	    this.getHandler().getRuntimeStatistics().reportFiner();
	} else if( level.equals(Level.FINEST) ) {
	    this.getHandler().getRuntimeStatistics().reportFinest();
	}

    }
    //--- END ------------------ Override DefaultCustomLogger --------------------

}

