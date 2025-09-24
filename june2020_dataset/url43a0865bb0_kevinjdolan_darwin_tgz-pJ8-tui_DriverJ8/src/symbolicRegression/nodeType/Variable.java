package symbolicRegression.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class Variable extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {};
	}

	@Override
	public Processable<?> getProcessable() {
		return new symbolicRegression.processable.Variable();
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}

}
