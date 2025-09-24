package M2;

public abstract class Service extends Interface {

	protected Composant parent;
	
	public Service(String name, Composant parent) {
		super(name);
		this.parent = parent;
	}
	
	public final Composant getParent() {
		return this.parent;
	}
	
	public final Configuration getConfig() {
		return (Configuration)this.parent.getParent();
	}
}
