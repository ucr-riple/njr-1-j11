package ikrs.util;

import java.util.logging.Level;

/**
 * The custom logger interface wraps the general Logger method to make logging more
 * customizable.
 *
 * @author Ikaros Kappler
 * @date 2012-07-16
 * @version 1.0.0
 **/

public interface CustomLogger {


    /**
     * Log a new message.
     **/
    public void log( Level level,
		     String trace,
		     String msg );

    public void setLevel( Level level );

}