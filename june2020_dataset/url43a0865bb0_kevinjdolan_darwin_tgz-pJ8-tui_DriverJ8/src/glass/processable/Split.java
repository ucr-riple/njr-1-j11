package glass.processable;

import darwin.common.Unit;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class Split extends Processable<Unit>{
		
	@Override
	public Unit getValue(Object parameters, Node[] childNodes) {
		childNodes[0].getValue(parameters);	
		childNodes[1].getValue(parameters);
		
		return new Unit();
	}
	
	@Override
	public Processable<Unit> clone() {
		return new Split();
	}

}
