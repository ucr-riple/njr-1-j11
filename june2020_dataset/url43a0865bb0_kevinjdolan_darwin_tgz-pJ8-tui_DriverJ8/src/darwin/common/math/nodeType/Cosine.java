package darwin.common.math.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class Cosine extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwin.common.math.processable.Cosine();
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}

}
