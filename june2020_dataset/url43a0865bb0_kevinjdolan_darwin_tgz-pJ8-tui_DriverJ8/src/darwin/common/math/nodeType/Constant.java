package darwin.common.math.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public abstract class Constant extends NodeType {
	
	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {};
	}

	@Override
	public Processable<?> getProcessable() {
		double max = getMax();
		double min = getMin();
		double range = max - min;
		double rand = Math.random() * range + min;
		return new darwin.common.math.processable.Constant(rand);
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}
	
	public boolean isMutable() {
		return true;
	}

	public abstract double getMax();
	public abstract double getMin();
}
