package glass.nodeType;

import java.awt.geom.Point2D;

import darwin.common.Unit;
import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class DrawCircle extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {Point2D.Double.class, Double.class, 
				java.awt.Color.class, Double.class, Unit.class};
	}

	@Override
	public Processable<?> getProcessable() {
		return new glass.processable.DrawCircle();
	}

	@Override
	public Class<?> getReturnType() {
		return Unit.class;
	}

}
