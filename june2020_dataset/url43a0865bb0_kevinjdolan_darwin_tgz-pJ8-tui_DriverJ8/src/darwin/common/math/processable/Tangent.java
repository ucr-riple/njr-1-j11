package darwin.common.math.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class Tangent extends Processable<Double>{

	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		double value = (Double) childNodes[0].getValue(parameters);
		double bottom = Math.cos(value);
		if(bottom == 0)
			return 0.;
		double top = Math.sin(value);
		return top / bottom;
	}
	
	@Override
	public Processable<Double> clone() {
		return new Tangent();
	}

}
