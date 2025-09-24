package darwin.common.logic.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class EqualTo extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class, Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwin.common.logic.processable.EqualTo();
	}

	@Override
	public Class<?> getReturnType() {
		return Boolean.class;
	}

}
