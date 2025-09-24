package patterns.gof.behavioral.visitor;

public class VisitorOne implements Visitor {
	@Override
	public void visit(ElementOne e) {
		VisitorClient.addOutput("visitor one - " + e.one());
	}
	
	@Override
	public void visit(ElementTwo e) {
		VisitorClient.addOutput("visitor one - " + e.two());
	}
	
	@Override
	public void visit(ElementThree e) {
		VisitorClient.addOutput("visitor one - " + e.three());
	}
}