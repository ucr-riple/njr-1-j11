package darwin.common.math.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class Log extends Processable<Double>{

	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		double value = (Double) childNodes[0].getValue(parameters);
		if(value <= 0)
			return 0.;
		return Math.log(value);
	}
	
	@Override
	public Processable<Double> clone() {
		return new Log();
	}

}
