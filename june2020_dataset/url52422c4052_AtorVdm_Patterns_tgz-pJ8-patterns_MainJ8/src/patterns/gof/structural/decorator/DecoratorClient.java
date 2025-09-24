package patterns.gof.structural.decorator;

import patterns.gof.helpers.Client;

public class DecoratorClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		// initial class is decorated in 2 decorators
		Decorator d = new DecoratorLine(new DecoratorStar(new InitialClass()));
		d.print();
		d.newFunction();
		
		super.main("Decorator");
	}
}