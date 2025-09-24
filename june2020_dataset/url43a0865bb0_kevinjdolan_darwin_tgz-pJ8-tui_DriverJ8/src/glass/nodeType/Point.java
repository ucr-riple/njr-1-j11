package glass.nodeType;

import java.awt.geom.Point2D;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class Point extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Double.class, Double.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new glass.processable.Point();
	}

	@Override
	public Class<?> getReturnType() {
		return Point2D.Double.class;
	}

}
