package patterns.gof.behavioral.visitor;

public class ElementOne implements Element {
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	public String one() {
		return "element one";
	}
}