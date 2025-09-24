package imageCompression;

import java.awt.image.BufferedImage;
import javax.swing.JComponent;

import darwin.population.Individual;

public class Output implements darwin.problemObject.Output {

	@Override
	public JComponent getOutput(Individual individual, Object environment) {
		return new Picture(individual, (BufferedImage) environment);
	}
	
	
}
