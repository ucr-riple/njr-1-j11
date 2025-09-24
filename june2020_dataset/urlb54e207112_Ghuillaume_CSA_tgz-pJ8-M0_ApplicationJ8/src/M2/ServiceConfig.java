package M2;

public abstract class ServiceConfig extends Interface {

	protected Configuration parent;
	
	public ServiceConfig(String name, Configuration config) {
		super(name);
		this.parent = config;
	}
	
	public final Configuration getParent() {
		return this.parent;
	}
	
	public final Configuration getConfig() {
		return this.parent;
	}
}
