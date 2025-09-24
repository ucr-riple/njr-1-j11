package patterns.gof.creational.abstractFactory;

public class FactoryMaker {
	public static AbstractFactory getFactory(int factoryTag) {
		switch(factoryTag) {
			case 1: return new ConcreteFactory1();
			case 2: return new ConcreteFactory2();
		}
		return null;
	}
}
