package patterns.gof.behavioral.visitor;

public class ElementTwo implements Element {
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	public String two() {
		return "element two";
	}
}
