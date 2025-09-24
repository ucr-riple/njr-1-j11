package ori.mas.actors;

import ori.mas.core.Body;
import ori.mas.core.Influence;

import ori.ogapi.util.OperatorPlus;
import java.util.Comparator;

public abstract class BodyTargetActor extends AbstractActor {

	public BodyTargetActor() {
		super();
	}

	public BodyTargetActor(Body b) {
		super(b);
	}

	public BodyTargetActor(Body b, Body target) {
		super(b);
		_target = target;
	}

	public void setTarget(Body t) {
		_target = t;
	}

	public Body target() {
		return _target;
	}

	@Override public abstract Influence act();

	private Body _target;

};

