package pl.cc.core.cmd;

/**
 * Komenda logged in - agent jest zautoryzowany.
 * 
 * @since Feb 27, 2008
 */
public class CommandLoggedIn extends Command { 

	
	public CommandLoggedIn(String orginalLine) {
		super(orginalLine);
	}


  @Override
	public int getType() {
		return CMD_OK_LOGGING_OK;
	}


}
