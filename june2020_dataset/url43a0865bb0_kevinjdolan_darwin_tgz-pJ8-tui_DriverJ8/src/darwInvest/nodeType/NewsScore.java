package darwInvest.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class NewsScore extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class, Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new darwInvest.processable.NewsScore();
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}
	
}