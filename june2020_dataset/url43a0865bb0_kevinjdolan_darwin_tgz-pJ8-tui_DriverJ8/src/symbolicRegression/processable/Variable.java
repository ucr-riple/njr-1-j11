package symbolicRegression.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class Variable extends Processable<Double>{

	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		Double value = (Double) parameters;
		return value;
	}
	
	@Override
	public Processable<Double> clone() {
		return new Variable();
	}

}
