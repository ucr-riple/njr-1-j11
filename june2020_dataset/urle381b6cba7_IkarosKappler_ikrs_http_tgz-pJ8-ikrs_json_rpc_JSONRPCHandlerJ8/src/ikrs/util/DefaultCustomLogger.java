package ikrs.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;


/**
 * The custom logger interface wraps the general Logger method to make logging more
 * customizable.
 *
 * @author Ikaros Kappler
 * @date 2012-07-16
 * @version 1.0.0
 **/

public class DefaultCustomLogger
    implements CustomLogger {

    private String loggerName;
    private Logger myLogger;

    public DefaultCustomLogger( String loggerName ) {
	super();

	this.loggerName = loggerName;
	
	// Create new logger	
	this.myLogger = Logger.getLogger(this.loggerName);
	LogManager.getLogManager().addLogger( this.myLogger );
    }

    /**
     * Log a new message.
     **/
    public void log( Level level,
		     String trace,
		     String msg ) {

	LogManager.getLogManager().getLogger( this.loggerName ).log( level, trace + " " + msg );

    }

    public void setLevel( Level level ) {
	LogManager.getLogManager().getLogger( this.loggerName ).setLevel( level );
    }

}