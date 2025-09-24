package patterns.gof.behavioral.visitor;

public interface Element {
	public void accept(Visitor v);
}