package pl.cc.core.cmd;

public class CommandPong extends Command {

	public CommandPong(String orginalLine) {
		super(orginalLine);
	}

	public static Command factoryInt(String line){
		if (Command.equalsExtended(line, "+okpong")){
			return new CommandPong(line);
		} else {
			return null;
		}
	}
	
	@Override
	public int getType() {
		return CMD_PONG;
	}
}
