package patterns.gof.creational.abstractFactory;

import patterns.gof.helpers.Pattern;

public abstract class AbstractFactory implements Pattern {
	public abstract AbstractProductA createProductA();
	public abstract AbstractProductB createProductB();
}