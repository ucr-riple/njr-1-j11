package darwin.common.math.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class Sine extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwin.common.math.processable.Sine();
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}

}
