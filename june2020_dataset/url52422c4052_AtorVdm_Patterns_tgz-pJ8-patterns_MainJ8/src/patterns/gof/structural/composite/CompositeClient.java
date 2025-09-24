package patterns.gof.structural.composite;

import patterns.gof.helpers.Client;

public class CompositeClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		Composite picture = new Composite();
		Composite element1 = new Composite();
		Composite element2 = new Composite();
		
		element1.add(new Line());
		element1.add(new Circle());
		element1.add(new Circle());
		element2.add(new Line());
		element2.add(new Line());
		element2.add(new Line());
		picture.add(new Circle());
		picture.add(element1);
		picture.add(element2);
		
		picture.draw();
		
		super.main("Composite");
	}
}