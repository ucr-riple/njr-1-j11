package patterns.gof.behavioral.visitor;

import patterns.gof.helpers.Client;

public class VisitorClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		Element[] elements = {new ElementOne(), new ElementTwo(), new ElementThree()};
		
		VisitorOne visOne = new VisitorOne();
		VisitorTwo visTwo = new VisitorTwo();
		
		for (Element el : elements) {
			el.accept(visOne);
		}
		
		for (Element el : elements) {
			el.accept(visTwo);
		}
		
		super.main("Visitor");
	}
}