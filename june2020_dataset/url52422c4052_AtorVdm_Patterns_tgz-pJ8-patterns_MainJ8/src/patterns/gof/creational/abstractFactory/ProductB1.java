package patterns.gof.creational.abstractFactory;

public class ProductB1 extends AbstractProductB {
	public ProductB1(String arg) {
		AbstractFactoryClient.addOutput("initialized ProductB1 with arg:" + arg);
	}

	@Override
	public void operationB1() {
		AbstractFactoryClient.addOutput("called operationB1 from ProductB1");
	}

	@Override
	public void operationB2() {
		AbstractFactoryClient.addOutput("called operationB2 from ProductB1");
	}
}