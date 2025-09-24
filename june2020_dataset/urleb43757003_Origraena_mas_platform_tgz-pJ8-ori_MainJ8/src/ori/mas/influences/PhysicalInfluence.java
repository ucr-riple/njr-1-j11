package ori.mas.influences;

import ori.mas.core.Actor;
import ori.mas.core.Body;

public abstract class PhysicalInfluence extends AbstractInfluence {

	public PhysicalInfluence() {
		super();
		_target = null;
	}

	public PhysicalInfluence(Body target) {
		super();
		_target = target;
	}

	public PhysicalInfluence(Actor source) {
		super(source);
		_target = null;
	}

	public PhysicalInfluence(Actor source, Body target) {
		super(source);
		_target = target;
	}

	public Body target() {
		return _target;
	}

	public void setTarget(Body b) {
		_target = b;
	}

	private Body _target;

};

