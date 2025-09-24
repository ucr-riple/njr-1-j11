package ikrs.yuccasrv.util;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * This is a custom Logger subclass that generate more verbose log messages than
 * the SimpleFormatter does.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-05-09
 * @version 1.0.0
 **/


public class YuccaLogFormatter
    extends SimpleFormatter {


    /*public String formatMessage( LogRecord record ) {
	
      }*/
    
    public String format( LogRecord record ) {
	String tmp = 
	    "" + record.getMillis() + 
	    " | " + record.getLevel() + 
	    " | " + record.getSourceClassName() + 
	    " | " + record.getSourceMethodName() + 
	    " | " + record.getMessage();

	if( record.getThrown() != null ) 
	    tmp += " | " + record.getThrown().getMessage();

	tmp += "\n";

	return tmp;
    }

}