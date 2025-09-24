package darwin.common.logic.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class IfElse<Type> extends Processable<Type>{

	@SuppressWarnings("unchecked")
	@Override
	public Type getValue(Object parameters, Node[] childNodes) {
		boolean value1 = (Boolean) childNodes[0].getValue(parameters);
		
		if(value1)
			return (Type) childNodes[1].getValue(parameters);
		else
			return (Type) childNodes[2].getValue(parameters);
	}
	
	@Override
	public Processable<Type> clone() {
		return new IfElse<Type>();
	}

}
