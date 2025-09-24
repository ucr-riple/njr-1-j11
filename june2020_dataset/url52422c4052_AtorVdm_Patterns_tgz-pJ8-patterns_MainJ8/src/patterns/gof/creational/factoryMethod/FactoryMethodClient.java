package patterns.gof.creational.factoryMethod;

import patterns.gof.helpers.Client;

public class FactoryMethodClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		Creator[] creators = {new ConcreteCreatorA(), new ConcreteCreatorB()};

		for (Creator creator : creators) {
	    	Product product = creator.factoryMethod();
	    	addOutput(product.getClass().getSimpleName());
	    }
		
		super.main("Factory Method");
	}
}