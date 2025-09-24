package glass.nodeType;

import darwin.common.Unit;
import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

/**
 * Terminate a drawing sequence
 * @author Kevin Dolan
 */
public class Split extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Unit.class, Unit.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new glass.processable.Split();
	}

	@Override
	public Class<?> getReturnType() {
		return Unit.class;
	}

}
