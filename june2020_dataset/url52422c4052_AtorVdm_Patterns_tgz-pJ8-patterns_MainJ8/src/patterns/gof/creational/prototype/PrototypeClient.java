package patterns.gof.creational.prototype;

import patterns.gof.helpers.Client;

public class PrototypeClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		ConcretePrototype cp1 = new ConcretePrototype();
	    cp1.setA(5);
	    cp1.setB(-1);
	    
	    // using prototype to create new object
	    ConcretePrototype cp2 = (ConcretePrototype) cp1.clone();
	    // creating new prototype from existing one
	    cp2.setA(cp1.getA() + 79);
	    cp2.setB(cp1.getB() + 92);
	    
	    ConcretePrototype cp3 = (ConcretePrototype) cp2.clone();
	    
	    addOutput(cp1.getA() + ", " + cp1.getB());
	    addOutput(cp3.getA() + ", " + cp3.getB());
	    
	    super.main("Prototype");
	}
}