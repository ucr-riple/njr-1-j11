package darwin.common.logic.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class TrueFalse extends NodeType {
	
	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwin.common.logic.processable.TrueFalse(Math.random() > 0.5);
	}

	@Override
	public Class<?> getReturnType() {
		return Boolean.class;
	}
	
	public boolean isMutable() {
		return true;
	}
}
