package darwin.common.logic.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class Or extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Boolean.class, Boolean.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwin.common.logic.processable.Or();
	}

	@Override
	public Class<?> getReturnType() {
		return Boolean.class;
	}

}
