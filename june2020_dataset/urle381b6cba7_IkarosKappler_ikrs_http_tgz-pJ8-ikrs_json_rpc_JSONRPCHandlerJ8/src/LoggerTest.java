

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.util.logging.MemoryHandler;


public class LoggerTest {


    public static void main( String[] argv ) {

	System.out.println( "Creating new logger for this class ..." );
	Logger logger   = Logger.getLogger( "bla" );
	//MemoryHandler handler = new MemoryHandler();
	//handler.setPushLevel( Level.SEVERE );
	//logger.addHandler( handler );
	LogManager.getLogManager().addLogger( logger );

	System.out.println( "Setting level to SEVERE ..." );
	logger.setLevel( Level.SEVERE );

	System.out.println( "Logging an INFO message ... ");
	logger.log( Level.INFO, "An INFO message." );

	System.out.println( "Logging a SEVERE message ... ");
	logger.log( Level.SEVERE, "An SEVERE message." );
	

    }



}