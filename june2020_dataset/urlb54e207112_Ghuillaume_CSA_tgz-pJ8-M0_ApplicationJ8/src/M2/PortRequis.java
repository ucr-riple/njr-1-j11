package M2;

import M0.Trace;

public class PortRequis extends Port {

	public PortRequis(String name, Composant parent) {
		super(name, parent);
	}
	
	@Override
	public final void activate(String message) {
		Trace.printInterfaceActivation(this, message);
		this.parent.run(this, message);
		this.getConfig().callBindings(this, message);
	}


}
