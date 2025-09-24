package ikrs.httpd;

import ikrs.yuccasrv.Yucca;

/**
 * This class is a wrapper for the Yucca main class.
 * The main purpose of this class is to add a customized CommandFactory
 * to yucca's command factory.
 *
 * By doing this the HTTPHandler is capable to add new commands to yucca's
 * command line.
 *
 * 
 * @author Ikaros Kappler
 * @date 2013-01-09
 * @version 1.0.0
 **/


public class Run {


    public static void main( String[] argv ) {

	// Create HTTPd's command factory
	ModuleCommandFactory myCommandFactory = new ModuleCommandFactory();

	// Run a new Yucca instance
	Yucca yucca = Yucca.runYucca( argv );

	// Set the parent command factory
	yucca.getCommandLine().getCommandFactory().setParentFactory( myCommandFactory );

	// Note: yucca is already running in a separate thread here!
    }


}