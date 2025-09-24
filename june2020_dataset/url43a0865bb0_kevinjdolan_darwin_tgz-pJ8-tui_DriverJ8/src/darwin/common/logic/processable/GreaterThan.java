package darwin.common.logic.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class GreaterThan extends Processable<Boolean>{

	@Override
	public Boolean getValue(Object parameters, Node[] childNodes) {
		double value1 = (Double) childNodes[0].getValue(parameters);
		double value2 = (Double) childNodes[1].getValue(parameters);
		return value1 > value2;
	}
	
	@Override
	public Processable<Boolean> clone() {
		return new GreaterThan();
	}

}
