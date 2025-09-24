package darwin.common.math.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class Subtract extends Processable<Double> {

	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		double value1 = (Double) childNodes[0].getValue(parameters);
		double value2 = (Double) childNodes[1].getValue(parameters);
		return value1 - value2;
	}
	
	@Override
	public Processable<Double> clone() {
		return new Subtract();
	}

}
