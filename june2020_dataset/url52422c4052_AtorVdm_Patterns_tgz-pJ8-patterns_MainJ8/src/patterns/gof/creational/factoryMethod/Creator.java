package patterns.gof.creational.factoryMethod;

import patterns.gof.helpers.Pattern;

public abstract class Creator implements Pattern {
	public abstract Product factoryMethod();
}