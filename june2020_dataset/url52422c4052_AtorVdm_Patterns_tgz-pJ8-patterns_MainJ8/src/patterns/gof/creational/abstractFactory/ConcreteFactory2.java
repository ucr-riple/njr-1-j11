package patterns.gof.creational.abstractFactory;

public class ConcreteFactory2 extends AbstractFactory {
	@Override
	public AbstractProductA createProductA() {
		return new ProductA2("ArgForProductA2");
	}

	@Override
	public AbstractProductB createProductB() {
		return new ProductB2("ArgForProductB2");
	}
}
