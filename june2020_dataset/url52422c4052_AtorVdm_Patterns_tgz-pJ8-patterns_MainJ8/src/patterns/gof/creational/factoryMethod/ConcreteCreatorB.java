package patterns.gof.creational.factoryMethod;

public class ConcreteCreatorB extends Creator {
	@Override
	public Product factoryMethod() {
		return new ConcreteProductB();
	}
}