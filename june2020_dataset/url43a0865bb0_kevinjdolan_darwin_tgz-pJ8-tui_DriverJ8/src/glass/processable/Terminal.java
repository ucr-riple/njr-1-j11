package glass.processable;

import darwin.common.Unit;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class Terminal extends Processable<Unit>{
		
	@Override
	public Unit getValue(Object parameters, Node[] childNodes) {
		return new Unit();
	}
	
	@Override
	public Processable<Unit> clone() {
		return new Terminal();
	}

}
