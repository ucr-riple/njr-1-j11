package ori.mas.actors;

import ori.mas.core.Actor;
import ori.mas.core.Body;
import ori.mas.core.Influence;

import java.util.List;

public abstract class AbstractActor implements Actor {

	public AbstractActor() {
		_body = null;
	}

	public AbstractActor(Body b) {
		_body = b;
	}

	@Override
	public void setBody(Body b) {
		_body = b;
	}

	protected Body body() {
		return _body;
	}

	@Override public abstract Influence act();

	@Override public abstract AbstractActor clone();

	private Body _body;

};

