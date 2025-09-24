package patterns.gof.creational.prototype;

public class ConcretePrototype extends Prototype {
	private int a;
	private int b;
	@Override
	public Prototype clone() {
		ConcretePrototype cp = new ConcretePrototype();
		cp.setA(this.a);
		cp.setB(this.b);
		return cp;
	}
	
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
}
