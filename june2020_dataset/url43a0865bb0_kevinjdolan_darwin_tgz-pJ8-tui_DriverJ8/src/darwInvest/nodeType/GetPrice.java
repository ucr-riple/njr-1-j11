package darwInvest.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class GetPrice extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwInvest.processable.GetPrice((int) (Math.random() * 5));
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}

	@Override
	public boolean isMutable() {
		return true;
	}
	
}