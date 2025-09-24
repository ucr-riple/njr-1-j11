package ori.mas.core;

import java.util.List;

public abstract class AbstractMind implements Mind {

	public AbstractMind() { }

	public AbstractMind(Agent a) {
		_agent = a;
	}

	@Override abstract
	public void percept(Percept percept);

	@Override
	public void percept(Iterable<Percept> percepts) {
		for (Percept p : percepts)
			this.percept(p);
	}

	@Override abstract
	public Actor nextActor();

	@Override
	public Agent agent() {
		return _agent;
	}

	@Override
	public void setAgent(Agent a) {
		_agent = a;
	}

	@Override abstract
	public AbstractMind clone();

	private Agent _agent;
};

