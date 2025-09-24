package ori.mas.fsm;

import ori.mas.core.Agent;
import ori.mas.core.Scene;

public abstract class Transition {

	public Transition(State s) {
		_state = s;
	}

	public abstract boolean isValid(Agent a, Scene world);

	public State state() {
		return _state;
	}

	private State _state;
	
};
