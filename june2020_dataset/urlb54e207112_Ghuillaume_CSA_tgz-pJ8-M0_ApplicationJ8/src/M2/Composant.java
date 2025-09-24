package M2;

import java.util.HashMap;



public class Composant extends Element {

	protected HashMap<String,ServiceRequis> servicesR;
	protected HashMap<String,ServiceFourni> servicesF;
	protected HashMap<String,PortRequis> portsR;
	protected HashMap<String,PortFourni> portsF;
	

	public Composant(String name, Configuration parent) {
		super(name, parent);
		parent.addComponent(this);
		this.servicesR = new HashMap<String,ServiceRequis>();
		this.servicesF = new HashMap<String,ServiceFourni>();
		this.portsR = new HashMap<String,PortRequis>();
		this.portsF = new HashMap<String,PortFourni>();
	}

	public void addService(ServiceFourni s) {
		this.servicesF.put(s.getName(), s);
	}

	public void addService(ServiceRequis s) {
		this.servicesR.put(s.getName(), s);
	}
	
	public void addPort(PortFourni p) {
		this.portsF.put(p.getName(), p);
	}
	
	public void addPort(PortRequis p) {
		this.portsR.put(p.getName(), p);
	}

	public final PortRequis getPortR(String name) {
		return this.portsR.get(name);
	}
	
	public final PortFourni getPortF(String name) {
		return this.portsF.get(name);
	}

	public final ServiceRequis getServiceR(String name) {
		return this.servicesR.get(name);
	}

	public final ServiceFourni getServiceF(String name) {
		return this.servicesF.get(name);
	}
	
	
	// run
	
	public void run(Port sender, String message) {
		// Pas de traitement par défaut
	}

}
