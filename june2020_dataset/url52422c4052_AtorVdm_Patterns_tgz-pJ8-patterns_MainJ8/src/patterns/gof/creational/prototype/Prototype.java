package patterns.gof.creational.prototype;

import patterns.gof.helpers.Pattern;

public abstract class Prototype implements Pattern {
	public abstract Prototype clone();
}