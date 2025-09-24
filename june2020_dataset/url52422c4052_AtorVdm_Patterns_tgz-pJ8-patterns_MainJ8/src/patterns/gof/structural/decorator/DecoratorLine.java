package patterns.gof.structural.decorator;

public class DecoratorLine extends Decorator {
	public DecoratorLine(Component component) {
		super(component);
	}
	
	@Override
	public void print() {
		DecoratorClient.addOutput("__________");
		super.print();
	}
	
	@Override
	public void newFunction() {
		DecoratorClient.addOutput("..........");
		super.newFunction();
	}
}