package ori.mas.influences;

import ori.mas.core.Agent;
import ori.mas.core.Actor;

public class BirthInfluence extends AbstractInfluence {

	public BirthInfluence(Agent a) {
		super();
		_agent = a;
	}

	public BirthInfluence(Actor sourceActor, Agent a) {
		super(sourceActor);
		_agent = a;
	}

	public Agent agent() {
		return _agent;
	}

	private Agent _agent;

};

