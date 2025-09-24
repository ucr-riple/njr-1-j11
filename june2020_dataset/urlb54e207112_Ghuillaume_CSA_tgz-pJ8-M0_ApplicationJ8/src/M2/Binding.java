package M2;

import M0.Trace;

public class Binding {

	protected String name;
	protected Interface sideA;
	protected Interface sideB;
	
	

	public Binding(String name) {
		this.name = name;
	}

	
	public final void bind(Interface a, Interface b) {
		this.sideA = a;
		this.sideB = b;
	}
	
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public final Interface getReceiver(Interface sender) {
		
		if(this.sideA == null || this.sideB == null) {
			Trace.printError("Binding " + this.name + " isn't well formed");
		}
		else {
			if(sender instanceof PortConfig) {
				if(((PortConfig)sender).getName().equals(this.sideA.getName())) {
					return this.sideB;
				}
				else if(((PortConfig)sender).getName().equals(this.sideB.getName())) {
					return this.sideA;
				}
			}
			else if(sender instanceof Port) {
				if(((Port)sender).getName().equals(this.sideA.getName())) {
					return this.sideB;
				}
				else if(((Port)sender).getName().equals(this.sideB.getName())) {
					return this.sideA;
				}
			}
			else if(sender instanceof Role) {
				if(((Role)sender).getName().equals(this.sideA.getName())) {
					return this.sideB;
				}
				else if(((Role)sender).getName().equals(this.sideB.getName())) {
					return this.sideA;
				}
			}
		}
	
		return null;
	}
	

}
