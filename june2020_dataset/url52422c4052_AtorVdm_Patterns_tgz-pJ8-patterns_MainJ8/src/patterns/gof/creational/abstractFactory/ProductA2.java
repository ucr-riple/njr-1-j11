package patterns.gof.creational.abstractFactory;

public class ProductA2 extends AbstractProductA {
	public ProductA2(String arg) {
		AbstractFactoryClient.addOutput("initialized ProductA2 with arg:" + arg);
	}

	@Override
	public void operationA1() {
		AbstractFactoryClient.addOutput("called operationA1 from ProductA2");
	}

	@Override
	public void operationA2() {
		AbstractFactoryClient.addOutput("called operationA2 from ProductA2");
	}
}