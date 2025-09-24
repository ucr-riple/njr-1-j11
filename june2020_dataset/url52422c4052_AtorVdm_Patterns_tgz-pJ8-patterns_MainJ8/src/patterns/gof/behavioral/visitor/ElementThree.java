package patterns.gof.behavioral.visitor;

public class ElementThree implements Element {
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	public String three() {
		return "element three";
	}
}
