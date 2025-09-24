package darwInvest.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class GetPeriod extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwInvest.processable.GetPeriod((int) (Math.random() * 9));
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