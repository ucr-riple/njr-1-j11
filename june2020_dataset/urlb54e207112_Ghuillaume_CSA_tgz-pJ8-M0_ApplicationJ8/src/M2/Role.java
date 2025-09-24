package M2;

public abstract class Role extends Interface {

	protected Connecteur parent;
	
	public Role(String name, Connecteur parent) {
		super(name);
		this.parent = parent;
	}
	
	public final Connecteur getParent() {
		return this.parent;
	}
	
	public final Configuration getConfig() {
		return (Configuration)this.parent.getParent();
	}
}
