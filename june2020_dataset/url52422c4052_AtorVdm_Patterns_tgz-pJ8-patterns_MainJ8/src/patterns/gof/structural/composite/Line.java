package patterns.gof.structural.composite;

public class Line implements GraphicalElement {
	@Override
	public void draw() {
		CompositeClient.addOutput("line drawn");
	}
}