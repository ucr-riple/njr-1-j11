package ori.mas.influences;

import ori.mas.core.Actor;
import ori.mas.core.Body;
import ori.ogapi.geometry.Point;

public class MovementInfluence extends PhysicalInfluence {

	public MovementInfluence(Point vector) {
		super();
		_vector = vector;
	}

	public MovementInfluence(Actor source, Body target) {
		super(source,target);
	}

	public MovementInfluence(Actor source, Body target, Point vector) {
		super(source,target);
		_vector = vector;
	}

	public void setVector(Point v) {
		_vector = v;
	}

	public Point vector() {
		return _vector;
	}

	private Point _vector;

};

