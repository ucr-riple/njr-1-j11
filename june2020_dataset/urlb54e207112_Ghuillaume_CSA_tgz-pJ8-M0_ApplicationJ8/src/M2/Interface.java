package M2;


public abstract class Interface {

	protected String name;
	
	public Interface(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public abstract Configuration getConfig();
	
	public abstract Element getParent();
	
	public abstract void activate(String message);
}
