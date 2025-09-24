package M2;

import M0.Trace;

public class RoleTo extends Role {

	public RoleTo(String name, Connecteur parent) {
		super(name, parent);
	}

	@Override
	public final void activate(String message) {
		Trace.printInterfaceActivation(this, message);
		this.getConfig().callBindings(this, message);
		this.getConfig().callAttachments(this, message);
	}
}
