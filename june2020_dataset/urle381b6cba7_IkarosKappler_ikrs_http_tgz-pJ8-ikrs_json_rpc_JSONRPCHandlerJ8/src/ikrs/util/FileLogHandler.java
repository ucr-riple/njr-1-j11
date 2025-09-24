package ikrs.util;

/**
 * The FileLogHandler organizes a set of log files and cycles each to the
 * next file if the max file size limit is reached.
 *
 * The pattern is basically as in java.util.logging.FileHandler:
 *  - / is the local pathname separator.
 *  - %t the system's temp directory.
 *  - %h the value of the user.home property.
 *  - %g a generation number.
 *  - %u a unique number to resolve conflicts.
 *  - %% means %.
 *   
 *
 * Additionally the pattern supports a date component:
 *  - %d
 *
 * The replacing value of '%d' is specified by the date format passed
 * to the constructor.
 *
 *
 * @author Ikaros Kappler
 * @date 2013-05-22
 * @version 1.0.0
 **/

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;


public class FileLogHandler 
    extends StreamHandler
    implements LogHandler {

    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyyMMdd_HH_mm_ss";

    /**
     * The file name pattern.
     **/
    private String pattern;

    /**
     * Indicates if new log handlers should append to existing files.
     **/
    private boolean append;

    /**
     * The max file size in bytes.
     **/
    private int limit;

    
    private DateFormat dateFormat;

    /**
     * The constructor.
     *
     * @param pattern 
     * @param append
     * @param limit 
     **/
    public FileLogHandler( String pattern,
			   boolean append,
			   int limit 
			   ) 
	throws NullPointerException,
	       IllegalArgumentException {
	this( pattern, 
	      append, 
	      limit, 
	      new SimpleDateFormat(FileLogHandler.DEFAULT_DATE_FORMAT_PATTERN) 
	      );
    }

    /**
     * The constructor.
     *
     * @param pattern 
     * @param append
     * @param limit 
     **/
    public FileLogHandler( String pattern,
			   boolean append,
			   int limit,			   
			   DateFormat dateFormat
			   ) 
	throws NullPointerException,
	       IllegalArgumentException {

	super();

	if( pattern == null )
	    throw new NullPointerException( "Cannot create FileLogHandlers with null pattern." );
	if( dateFormat == null )
	    throw new NullPointerException( "Cannot create FileLogHandlers with null date-format." );
	if( limit <= 0 )
	    throw new IllegalArgumentException( "Cannot create FileLoghandler with negative/zero max file limit." );
	
	this.pattern     = pattern;
	this.append      = append;
	this.limit       = limit;
	
	this.dateFormat  = dateFormat;

	// Note there is the 'setOutputStream()' method!
    }

    //--- BEGIN --------------- Handler implementation ------------------------
    public void close() 
	throws SecurityException {
	
	synchronized( this.pattern ) {	    
	    super.close();
	}
    }

    
    public void	flush() 
	throws SecurityException {

	synchronized( this.pattern ) {
	    super.flush();	    
	}
    }

    public void	publish( LogRecord record ) {
	
	synchronized( this.pattern ) {
	    super.publish( record );
	}
    }
    //--- END ----------------- Handler implementation ------------------------
    

    protected File getNextFileInCycle() {
	
	return null;
    }

    public static String getNextFilename( String pattern,
					  DateFormat dateFormat,
					  int uniqueNumber,
					  int groupNumber ) {
	
	String filename = pattern;
	filename = filename.replaceAll( "/", System.getProperty("file.separator") );
	filename = filename.replaceAll( "%t", System.getProperty("java.io.tmpdir") );
	filename = filename.replaceAll( "%u", Integer.toString(uniqueNumber) );
	filename = filename.replaceAll( "%g", Integer.toString(groupNumber) );
	filename = filename.replaceAll( "%h", System.getProperty("user.dir") );

	// This is new
	filename = filename.replaceAll( "%d", dateFormat.format(new Date(System.currentTimeMillis())) );

	// Finally restore all '%'
	filename = filename.replaceAll( "%%", "%" );
	
	return filename;
    }


    public static void main( String[] argv ) {

	System.out.println( "Test run ..." );
	String pattern = "%h/test/testlog.%d_u%u_g%g.log";
	System.out.println( "Using pattern: " + pattern );
	DateFormat dateFormat = new SimpleDateFormat(FileLogHandler.DEFAULT_DATE_FORMAT_PATTERN);

	
	System.out.println( "Generating file name ... ");
	String filename = FileLogHandler.getNextFilename(pattern, dateFormat, 123, 456 );
	System.out.println( "Generated file name: " + filename );
	
	
    }
    

}