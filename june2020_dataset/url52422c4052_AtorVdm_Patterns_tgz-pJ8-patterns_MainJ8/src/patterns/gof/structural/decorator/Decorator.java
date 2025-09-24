package patterns.gof.structural.decorator;

import patterns.gof.helpers.Pattern;

public abstract class Decorator implements Component, Pattern {
	private Component component;
	
	public Decorator(Component component) {
		this.component = component;
	}
	
	@Override
	public void print() {
		component.print();
	}
	
	public void newFunction() {
		DecoratorClient.addOutput("==========");
	}
}