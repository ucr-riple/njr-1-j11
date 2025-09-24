package darwin.common.logic.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class IfElseBoolean extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Boolean.class, Boolean.class, Boolean.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwin.common.logic.processable.IfElse<Boolean>();
	}

	@Override
	public Class<?> getReturnType() {
		return Boolean.class;
	}

}
