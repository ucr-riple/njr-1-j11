package pl.cc.core.cmd;

/**
 * Komenda loggedout - wylogowanie się z CCproxy
 * 
 * @since Oct 26, 2008
 */
public class CommandLoggedOut extends Command { 

	
	public CommandLoggedOut(String orginalLine) {
		super(orginalLine);
	}

	public static Command factoryInt(String line){
		if (Command.equalsExtended(line, "+OK hasta la vista")){
			return new CommandLoggedOut(line);
		} else {
			return null;
		}
	}

	@Override
	public int getType() {
		return CMD_OK_LOGGED_OUT;
	}


}
