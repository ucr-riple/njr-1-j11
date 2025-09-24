package M2;

import M0.Trace;

public class ServiceFourni extends Service {

	public ServiceFourni(String name, Composant parent) {
		super(name, parent);
	}
	
	public void run(String message) {
		// Pas de comportement par défaut
	}


	@Override
	public final void activate(String message) {
		Trace.printInterfaceActivation(this, message);
		this.run(message);
	}
}
