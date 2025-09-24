package patterns.gof.structural.composite;

import java.util.List;
import java.util.ArrayList;

import patterns.gof.helpers.Pattern;

public class Composite implements GraphicalElement, Pattern {
	private List<GraphicalElement> childPolygons = new ArrayList<GraphicalElement>();

	@Override
	public void draw() {
		for(GraphicalElement poly : childPolygons) {
			poly.draw();
		}
		CompositeClient.addOutput("element finished");
	}
	
	public void add(GraphicalElement polygon) {
		childPolygons.add(0, polygon);
	}
	
	public void remove(GraphicalElement polygon) {
		childPolygons.remove(polygon);
	}
}