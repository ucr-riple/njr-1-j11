package M2;

import M0.Trace;

public class PortConfigRequis extends PortConfig {

	public PortConfigRequis(String name, Configuration config) {
		super(name, config);
	}

	@Override
	public final void activate(String message) {
		Trace.printInterfaceActivation(this, message);
		this.getConfig().callBindings(this, message);
	}
}
