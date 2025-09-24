package pl.cc.core.cmd;

public class CommandUnknown extends Command {

	public CommandUnknown(String orginalLine, String connectionId) {
		super(orginalLine, connectionId);
	}

	@Override
	public int getType() {
		return CMD_UNKNOWN;
	}

}
