package darwin.common.math.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class Sine extends Processable<Double>{

	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		double value = (Double) childNodes[0].getValue(parameters);
		return Math.sin(value);
	}
	
	@Override
	public Processable<Double> clone() {
		return new Sine();
	}

}
