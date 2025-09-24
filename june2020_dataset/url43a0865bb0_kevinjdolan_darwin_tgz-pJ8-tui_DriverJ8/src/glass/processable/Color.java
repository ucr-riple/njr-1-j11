package glass.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class Color extends Processable<java.awt.Color>{
		
	@Override
	public java.awt.Color getValue(Object parameters, Node[] childNodes) {
		Double red = (Double) (childNodes[0].getValue(parameters));
		Double green = (Double) (childNodes[1].getValue(parameters));
		Double blue = (Double) (childNodes[2].getValue(parameters));
		
		return new java.awt.Color((int) (red * 255), (int) (green * 255), (int) (blue * 255));
	}
	
	@Override
	public Processable<java.awt.Color> clone() {
		return new Color();
	}

}
