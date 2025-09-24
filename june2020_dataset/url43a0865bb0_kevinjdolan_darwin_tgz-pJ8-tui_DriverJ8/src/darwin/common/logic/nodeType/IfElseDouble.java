package darwin.common.logic.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class IfElseDouble extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Boolean.class, Double.class, Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwin.common.logic.processable.IfElse<Double>();
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}

}
