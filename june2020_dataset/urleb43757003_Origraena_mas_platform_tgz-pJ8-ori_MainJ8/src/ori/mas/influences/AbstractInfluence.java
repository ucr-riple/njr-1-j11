package ori.mas.influences;

import ori.mas.core.Influence;
import ori.mas.core.Actor;

public abstract class AbstractInfluence implements Influence {

	public AbstractInfluence() {
		_sourceActor = null;
	}

	public AbstractInfluence(Actor source) {
		_sourceActor = source;
	}

	@Override
	public Actor sourceActor() {
		return _sourceActor;
	}

	@Override
	public void setSourceActor(Actor a) {
		_sourceActor = a;
	}

	private Actor _sourceActor;

};

