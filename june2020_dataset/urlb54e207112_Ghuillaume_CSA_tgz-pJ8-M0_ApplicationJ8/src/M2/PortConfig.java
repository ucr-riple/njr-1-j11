package M2;

public abstract class PortConfig extends Interface {
	
	protected Configuration parent;

	public PortConfig(String name, Configuration config) {
		super(name);
		this.parent = config;
	}
	
	public final Configuration getConfig() {
		return this.parent;
	}
	
	public final Configuration getParent() {
		return this.parent;
	}
}
