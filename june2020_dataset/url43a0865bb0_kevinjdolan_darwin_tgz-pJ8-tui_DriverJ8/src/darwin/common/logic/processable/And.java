package darwin.common.logic.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class And extends Processable<Boolean>{

	@Override
	public Boolean getValue(Object parameters, Node[] childNodes) {
		return (Boolean) childNodes[0].getValue(parameters) 
			&& (Boolean) childNodes[1].getValue(parameters);
	}
	
	@Override
	public Processable<Boolean> clone() {
		return new And();
	}

}
