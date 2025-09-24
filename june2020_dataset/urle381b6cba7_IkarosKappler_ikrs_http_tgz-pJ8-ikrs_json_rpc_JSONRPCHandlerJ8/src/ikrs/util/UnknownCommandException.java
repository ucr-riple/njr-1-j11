package ikrs.util;

/**
 * This is a small error class to wrap error messages respective unknown (user-entered)
 * text commands.
 *
 * @author Ikaros Kappler
 * @date 2012-05-09
 * @version 1.0.0
 **/

public class UnknownCommandException
    extends Exception {

    private String command;

    public UnknownCommandException( String msg ) {
	this( msg, null );
    }

    public UnknownCommandException( String msg, 
				    String cmd ) {
	super( msg );
	this.command = cmd;
    }

    public String getCommand() {
	return command;
    }
				    

}