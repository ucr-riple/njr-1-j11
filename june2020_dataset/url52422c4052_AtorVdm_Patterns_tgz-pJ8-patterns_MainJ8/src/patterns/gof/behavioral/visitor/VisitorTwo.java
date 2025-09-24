package patterns.gof.behavioral.visitor;

public class VisitorTwo implements Visitor {
	@Override
	public void visit(ElementOne e) {
		VisitorClient.addOutput("visitor two - " + e.one());
	}
	
	@Override
	public void visit(ElementTwo e) {
		VisitorClient.addOutput("visitor two - " + e.two());
	}
	
	@Override
	public void visit(ElementThree e) {
		VisitorClient.addOutput("visitor two - " + e.three());
	}
}