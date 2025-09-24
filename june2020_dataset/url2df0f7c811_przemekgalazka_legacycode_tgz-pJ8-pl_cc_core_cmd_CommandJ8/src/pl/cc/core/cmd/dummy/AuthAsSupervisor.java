package pl.cc.core.cmd.dummy;

import pl.cc.core.cmd.Command;

public class AuthAsSupervisor extends Command {

	boolean supervisor;

	public AuthAsSupervisor(boolean supervisor) {
		super("NONE", null);
		this.supervisor = supervisor;
	}

	@Override
	public int getType() {
		return -1;
	}

	public boolean isSupervisor() {
		return supervisor;
	}
	
}
