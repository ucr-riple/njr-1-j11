package pl.cc.core.cmd.dummy;

import pl.cc.core.cmd.Command;

public class AuthFailed extends Command {

	public AuthFailed() {
		super("NONE", null);
	}

	@Override
	public int getType() {
		return -1;
	}

}
