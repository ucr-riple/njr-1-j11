package M2;

import M0.Trace;

public class PortFourni extends Port {

	public PortFourni(String name, Composant parent) {
		super(name, parent);
	}

	@Override
	public final void activate(String message) {
		Trace.printInterfaceActivation(this, message);
		this.getConfig().callAttachments(this, message);
		this.getConfig().callBindings(this, message);
	}
}
