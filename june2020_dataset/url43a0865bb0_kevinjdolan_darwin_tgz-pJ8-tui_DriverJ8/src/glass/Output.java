package glass;

import glass.drawing.ShapeSet;
import java.awt.Color;
import javax.swing.JComponent;
import darwin.population.Individual;
import glass.drawing.Picture;

public class Output implements darwin.problemObject.Output {

	@Override
	public JComponent getOutput(Individual individual, Object environment) {
		ShapeSet shapes = new ShapeSet(new Color(255,255,255));
		individual.getTree(0).getValue(shapes);
		return new Picture(shapes);
	}
	
	
}
