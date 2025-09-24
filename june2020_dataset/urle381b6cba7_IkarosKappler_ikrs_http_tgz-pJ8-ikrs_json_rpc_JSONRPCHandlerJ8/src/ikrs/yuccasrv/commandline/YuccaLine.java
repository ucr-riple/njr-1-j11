package ikrs.yuccasrv.commandline;

/**
 * This is the serverside command line.
 *
 * @author Ikaros Kappler
 * @date 2012-05-07
 * @version 1.0.0
 **/

import java.io.*;

import ikrs.util.AbstractCommandLine;
import ikrs.util.Command;
import ikrs.util.CommandFactory;
import ikrs.util.DefaultCommandFactory;

public class YuccaLine
    //extends AbstractCommandLine<YuccaCommand> 
    extends AbstractCommandLine<Command> 
    implements Runnable {

    public YuccaLine( CommandFactory<Command> factory ) {
	super( factory, "ycc> " );
    }

    /**
     * @override AbstractCommandLine.run
     **/
    public void run() {
	try {

	    super.runCommandLine();

	} catch( IOException e ) {
	    e.printStackTrace();
	}
    }


    public static void main( String[] argv ) {

	
	YuccaCommandFactory factory = new YuccaCommandFactory( null );
	YuccaLine cl = new YuccaLine( factory );
	cl.run();
	

    }

}