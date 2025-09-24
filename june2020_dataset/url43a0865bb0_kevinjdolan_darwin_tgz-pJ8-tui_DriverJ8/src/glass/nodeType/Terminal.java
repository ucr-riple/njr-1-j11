package glass.nodeType;

import darwin.common.Unit;
import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

/**
 * Terminate a drawing sequence
 * @author Kevin Dolan
 */
public class Terminal extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {};
	}

	@Override
	public Processable<?> getProcessable() {
		return new glass.processable.Terminal();
	}

	@Override
	public Class<?> getReturnType() {
		return Unit.class;
	}

}
