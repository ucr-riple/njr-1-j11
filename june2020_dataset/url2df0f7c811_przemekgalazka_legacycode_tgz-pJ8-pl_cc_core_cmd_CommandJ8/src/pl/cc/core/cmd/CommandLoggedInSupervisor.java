package pl.cc.core.cmd;

/**
 * Komenda logged in - supervisor jest zautoryzowany.
 * 
 * @since Feb 27, 2008
 */
public class CommandLoggedInSupervisor extends Command { 

	
	public CommandLoggedInSupervisor(String orginalLine) {
		super(orginalLine);
	}

	public static Command factoryInt(String line){
		if (Command.equalsExtended(line, "+OK Welcome supervisor")){
			return new CommandLoggedInSupervisor(line);
		} else {
			return null;
		}
	}

	@Override
	public int getType() {
		return CMD_OK_LOGGING_OK_SUPERVISOR;
	}


}
