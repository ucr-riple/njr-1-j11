package patterns.gof.creational.abstractFactory;

public class ProductA1 extends AbstractProductA {
	public ProductA1(String arg) {
		AbstractFactoryClient.addOutput("initialized ProductA1 with arg:" + arg);
	}

	@Override
	public void operationA1() {
		AbstractFactoryClient.addOutput("called operationA1 from ProductA1");
	}

	@Override
	public void operationA2() {
		AbstractFactoryClient.addOutput("called operationA2 from ProductA1");
	}
}