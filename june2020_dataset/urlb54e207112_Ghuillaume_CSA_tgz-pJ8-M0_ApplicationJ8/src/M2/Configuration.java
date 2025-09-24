package M2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import M0.Trace;

public class Configuration extends Element {

	protected ArrayList<Composant> components;
	protected ArrayList<Connecteur> connectors;
	protected ArrayList<Attachment> attachments;
	protected ArrayList<Binding> bindings;
	protected ArrayList<ServiceConfig> servicesRequis;
	protected ArrayList<ServiceConfig> servicesFournis;
	protected HashMap<String, PortConfigRequis> portsRequis;
	protected HashMap<String, PortConfigFourni> portsFourni;
	
	private ArrayList<String> tags;
	
	
	public Configuration(String name) {
		super(name, null);
		
		this.components = new ArrayList<Composant>();
		this.connectors = new ArrayList<Connecteur>();
		this.attachments = new ArrayList<Attachment>();
		this.bindings = new ArrayList<Binding>();
		this.servicesRequis = new ArrayList<ServiceConfig>();
		this.servicesFournis = new ArrayList<ServiceConfig>();
		this.portsRequis = new HashMap<String, PortConfigRequis>();
		this.portsFourni = new HashMap<String, PortConfigFourni>();
		this.tags = new ArrayList<String>();
	}
	
	public final void setSubconf(Configuration subconf) {
		// Une configuration n'a pas de sous-configuration
		this.subConfiguration = null;
	}
	
	public final void setParent(Element parent) {
		this.parent = parent;
	}
	
	public final void addComponent(Composant c) {
		if(this.tags.contains(c.getName())) {
			Trace.printError("Tag " + c.getName() + " is not available, component as not been created");
		}
		else {
			this.components.add(c);
			this.tags.add(c.getName());
		}
	}
	
	public final void addConnector(Connecteur c) {
		if(this.tags.contains(c.getName())) {
			Trace.printError("Tag " + c.getName() + " is not available, connector as not been created");
		}
		else {
			this.connectors.add(c);
			this.tags.add(c.getName());
		}
	}
	
	public final void addService(ServiceConfigFourni sc) {
		if(this.tags.contains(sc.getName())) {
			Trace.printError("Tag " + sc.getName() + " is not available, service as not been created");
		}
		else {
			this.servicesFournis.add(sc);
			this.tags.add(sc.getName());
		}
	}
	
	
	public final void addService(ServiceConfigRequis sc) {
		if(this.tags.contains(sc.getName())) {
			Trace.printError("Tag " + sc.getName() + " is not available, service as not been created");
		}
		else {
			this.servicesRequis.add(sc);
			this.tags.add(sc.getName());
		}
	}
	
	public final void addPort(PortConfigFourni pc) {
		if(this.tags.contains(pc.getName())) {
			Trace.printError("Tag " + pc.getName() + " is not available, service as not been created");
		}
		else {
			this.portsFourni.put(pc.getName(), pc);
			this.tags.add(pc.getName());
		}
	}
	
	public final void addPort(PortConfigRequis pc) {
		if(this.tags.contains(pc.getName())) {
			Trace.printError("Tag " + pc.getName() + " is not available, service as not been created");
		}
		else {
			this.portsRequis.put(pc.getName(), pc);
			this.tags.add(pc.getName());
		}
	}
	
	
	// Getters

	public final PortConfigRequis getPortR(String name) {
		return this.portsRequis.get(name);
	}
	
	public final PortConfigFourni getPortF(String name) {
		return this.portsFourni.get(name);
	}
	
	
	public final Composant getComposant(String name) {
		Iterator<Composant> it = this.components.iterator();
		while(it.hasNext()) {
			Composant currentComponent = it.next();
			if(currentComponent.getName().equals(name)) {
				return currentComponent;
			}
		}
		
		return null;
	}
	
	public final Connecteur getConnector(String name) {
		Iterator<Connecteur> it = this.connectors.iterator();
		while(it.hasNext()) {
			Connecteur currentConnector = it.next();
			if(currentConnector.getName().equals(name)) {
				return currentConnector;
			}
		}
		
		return null;
	}
	
	public final Configuration getParentConfig() {
		Configuration parentConfig = null;
		
		if(this.parent != null) {
			if(this.parent.getParent() != null) {
				if(this.parent.getParent() instanceof Configuration) {
					parentConfig = (Configuration)this.parent.getParent();
				}
			}
		}
		
		return parentConfig;
	}
	
	
	// Attachments (2)
	
	public final Attachment addLink(String name, RoleFrom r, PortFourni p) {
		Attachment a = null;
		if(this.tags.contains(name)) {
			Trace.printError("Tag " + name + " is not available, service as not been created");
		}
		else {
			if (r.getConfig().equals(this) && p.getConfig().equals(this)) {
				a = new Attachment(name);
				a.bind(r, p);
				this.attachments.add(a);
				this.tags.add(a.getName());
			}
		}
		return a;
	}
	
	public final Attachment addLink(String name, RoleTo r, PortRequis p) {
		Attachment a = null;
		if(this.tags.contains(name)) {
			Trace.printError("Tag " + name + " is not available, service as not been created");
		}
		else {
			a = new Attachment(name);
			a.bind(r, p);
			this.attachments.add(a);
			this.tags.add(a.getName());
		}
		return a;
	}
	
	
	// Bindings (4)
	
	public final Binding addLink(String name, RoleFrom r, PortConfigFourni p) {
		Binding b = null;

		if(this.tags.contains(name)) {
			Trace.printError("Tag " + name + " is not available, service as not been created");
		}
		else {
			b = new Binding(name);
			b.bind(r, p);
			this.bindings.add(b);
			this.tags.add(b.getName());
		}
		return b;
	}
	
	public final Binding addLink(String name, RoleTo r, PortConfigRequis p) {
		Binding b = null;

		if(this.tags.contains(name)) {
			Trace.printError("Tag " + name + " is not available, service as not been created");
		}
		else {
			b = new Binding(name);
			b.bind(r, p);
			this.bindings.add(b);
			this.tags.add(b.getName());
		}
		return b;
		
	}
	
	public final Binding addLink(String name, PortConfigRequis pc, PortRequis p) {
		Binding b = null;
		
		if(pc == null) {
			Trace.printError("While trying to bind, portConfigRequis is null");
		}
		else if(p == null) {
			Trace.printError("While trying to bind, portRequis is null");
		}
		else if(this.tags.contains(name)) {
			Trace.printError("Tag " + name + " is not available, service as not been created");
		}
		else {
			b = new Binding(name);
			b.bind(pc, p);
			this.bindings.add(b);
			this.tags.add(b.getName());
		}
		return b;
	}
	
	public final Binding addLink(String name, PortConfigFourni pc, PortFourni p) {
		Binding b = null;

		if(this.tags.contains(name)) {
			Trace.printError("Tag " + name + " is not available, service as not been created");
		}
		else {
			b = new Binding(name);
			b.bind(pc, p);
			this.bindings.add(b);
			this.tags.add(b.getName());
		}
		return b;
	}
	
	public final void addLink(Binding b) {
		if(this.tags.contains(b.getName())) {
			Trace.printError("Binding already added or name already picked");
		}
		else {
			this.bindings.add(b);
			this.tags.add(b.getName());
		}
	}
	
	
	// Links management
	
	public final void callAttachments(Interface sender, String message) {

		ArrayList<Interface> receiver = new ArrayList<Interface>();
		
		// Find all receiver in attachments list
		Iterator<Attachment> itAttach = this.attachments.iterator();
		while(itAttach.hasNext()) {
			Interface dest = itAttach.next().getReceiver(sender);
			if(dest != null) {
				receiver.add(dest);
			}
		}
		
		// Activate all receivers
		Iterator<Interface> itIface = receiver.iterator();
		while(itIface.hasNext()) {
			Interface dest = itIface.next();
			dest.activate(message);
		}
	}
	
	public final void callBindings(Interface sender, String message) {

		ArrayList<Interface> receiver = new ArrayList<Interface>();
		
		// Find all receiver in bindings list
		Iterator<Binding> itBind = this.bindings.iterator();
		while(itBind.hasNext()) {
			Interface rec = itBind.next().getReceiver(sender);
			if(rec != null) {

				boolean wrongWay = false;
				
				// si portconfigrequis, les destinataires ne sont que ceux qui sont au niveau du dessous
				if(sender instanceof PortConfigRequis && sender.getConfig().getParent() != null) {
					if(sender.getConfig().getParentConfig().equals(rec.getConfig())) {
						wrongWay = true;
					}
				}
				
				// si portconfigfourni, on ne peut aller que au dessus
				if(sender instanceof PortConfigFourni && rec.getConfig().getParentConfig() != null) {
					if(!sender.getConfig().equals(rec.getConfig().getParentConfig())) {
						wrongWay = true;
					}
				}
				
				// L'activation d'un port requis n'est redirigée vers une config que si elle est de niveau inférieur (le message entre)
				// On interdit donc la redirection vers une config de même niveau
				if(sender.getConfig().equals(rec.getConfig())) {
					if((sender instanceof PortRequis || sender instanceof RoleTo) && rec instanceof PortConfigRequis) {
						wrongWay = true;
					}
				}
				
				// L'activation d'un port fourni n'est redirigée vers une config que si elle est de même niveau (le message sort)
				// On interdit donc la redirection vers une config de niveau inférieur
				else {
					if((sender instanceof PortFourni || sender instanceof RoleFrom) && rec instanceof PortConfigFourni) {
						wrongWay = true;
					}
				}
				
				if(!wrongWay) {
					receiver.add(rec);
				}
			}
		}
		
		// Activate all receivers
		Iterator<Interface> itIface = receiver.iterator();
		while(itIface.hasNext()) {
			itIface.next().activate(message);
		}
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuration other = (Configuration) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}
	
	
	

}
