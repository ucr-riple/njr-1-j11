package darwInvest.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class LoopDouble extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class, Double.class, Double.class, Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwInvest.processable.LoopBoolean((int) (Math.random() * 5));
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}

}
