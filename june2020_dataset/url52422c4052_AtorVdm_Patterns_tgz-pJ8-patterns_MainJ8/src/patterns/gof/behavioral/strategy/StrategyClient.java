package patterns.gof.behavioral.strategy;

import patterns.gof.helpers.Client;

public class StrategyClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		Context context;
		
		context = new Context(new AddStrategy());
        addOutput("" + context.useStrategy(3,4));
 
        context.setStrategy(new SubtractStrategy());
        addOutput("" + context.useStrategy(3,4));
		
		super.main("Strategy");
	}
}
