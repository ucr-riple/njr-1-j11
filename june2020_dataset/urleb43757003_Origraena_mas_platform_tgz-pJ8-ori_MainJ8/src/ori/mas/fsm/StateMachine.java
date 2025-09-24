package ori.mas.fsm;

import ori.mas.core.Agent;
import ori.mas.core.Actor;
import ori.mas.core.Scene;

public class StateMachine {

	public StateMachine() {
		_current = null;
	}

	public StateMachine(State init) {
		_current = init;
	}

	/**
	 * Current state setter.
	 * <p>Externally use with caution !</p>
	 * @param state The new state to set.
	 */
	public void setCurrent(State state) {
		_current = state;
	}

	/**
	 * Current state getter.
	 * @return The current state.
	 */
	public State current() {
		return _current;
	}

	/**
	 * Computes the next agent actor.
	 * <p>
	 * First, all current transitions validity is checked.
	 * If a transition is valid, then its next state is set instead of the current.
	 * Finally the state is executed with the current parameters.
	 * </p>
	 * @param a The agent to be considered.
	 * @param world The local scene representation of the agent.
	 * @return The next selected actor.
	 */
	public Actor nextActor(Agent a, Scene world) {
		for (Transition t : _current) {
			if (t.isValid(a,world)) {
				_current = t.state();
				break;
			}
		}
		return _current.actor(a,world);
	}

	private State _current;
	
};

