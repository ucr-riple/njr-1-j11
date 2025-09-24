package patterns.gof.behavioral.visitor;

public interface Visitor {
	public void visit(ElementOne e);
	public void visit(ElementTwo e);
	public void visit(ElementThree e);
}