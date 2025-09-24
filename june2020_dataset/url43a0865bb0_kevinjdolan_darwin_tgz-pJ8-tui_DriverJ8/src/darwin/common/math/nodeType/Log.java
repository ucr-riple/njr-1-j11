package darwin.common.math.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class Log extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwin.common.math.processable.Log();
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}

}
