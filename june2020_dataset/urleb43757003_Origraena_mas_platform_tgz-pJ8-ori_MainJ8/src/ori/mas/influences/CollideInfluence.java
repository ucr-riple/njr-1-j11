package ori.mas.influences;

import ori.mas.core.Body;
import ori.ogapi.geometry.Point;

public class CollideInfluence extends PhysicalInfluence {

	public CollideInfluence(Body target, Body collidingBody, Point collidingPoint) {
		super(target);
		_collidingPoint = collidingPoint;
	}

	public Point collidingPoint() {
		return _collidingPoint;
	}

	public Body collidingBody() {
		return _collidingBody;
	}

	private Body _collidingBody;
	private Point _collidingPoint;

};

