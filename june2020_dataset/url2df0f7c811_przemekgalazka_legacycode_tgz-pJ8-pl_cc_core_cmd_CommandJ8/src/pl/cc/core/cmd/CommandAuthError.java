package pl.cc.core.cmd;

/**
 * Bład logowania/autoryzacji
 * 
 * @since Feb 27, 2008
 */
public class CommandAuthError extends Command { 

	
	public CommandAuthError(String orginalLine) {
		super(orginalLine);
	}

	public static Command factoryInt(String line){
		if (Command.equalsExtended(line, "-ERR Authentication error")){
			return new CommandAuthError(line);
		} else {
			return null;
		}
	}

	@Override
	public int getType() {
		return CMD_OK_LOGGING_ERROR;
	}


}
