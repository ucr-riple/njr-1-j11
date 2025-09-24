package patterns.gof.creational.abstractFactory;

public class ConcreteFactory1 extends AbstractFactory {
	@Override
	public AbstractProductA createProductA() {
		return new ProductA1("ArgForProductA1");
	}

	@Override
	public AbstractProductB createProductB() {
		return new ProductB1("ArgForProductB1");
	}
}