package glass.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class Color extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class, Double.class, Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new glass.processable.Color();
	}

	@Override
	public Class<?> getReturnType() {
		return java.awt.Color.class;
	}

}
