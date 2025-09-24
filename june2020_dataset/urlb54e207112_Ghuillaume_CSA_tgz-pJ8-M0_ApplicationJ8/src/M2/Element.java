package M2;

public abstract class Element {
	
	protected String name;
	
	protected Configuration subConfiguration;
	protected Element parent;
	
	public Element(String name, Element parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public void setSubconf(Configuration subconf) {
		this.subConfiguration = subconf;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Element getParent() {
		return this.parent;
	}
}
