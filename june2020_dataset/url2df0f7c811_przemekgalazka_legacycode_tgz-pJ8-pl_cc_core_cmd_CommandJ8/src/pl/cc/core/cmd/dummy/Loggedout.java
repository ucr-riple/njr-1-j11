package pl.cc.core.cmd.dummy;

import pl.cc.core.cmd.Command;

public class Loggedout extends Command {

	public Loggedout() {
		super("NONE", null);
	}

	@Override
	public int getType() {
		return -1;
	}

}
