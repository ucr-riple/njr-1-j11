package ori.mas.fsm;

import ori.mas.core.Agent;
import ori.mas.core.Actor;
import ori.mas.core.Scene;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * A SuperState implements State while extending StateMachine, allowing
 * the user to model hierarchical state machines.
 * @see State
 * @see StateMachine
 */
public class SuperState extends StateMachine implements State {

	public SuperState() {
		super();
	}

	public SuperState(State init) {
		super(init);
	}

	@Override
	public Actor actor(Agent a, Scene world) {
		return this.nextActor(a,world);
	}
	
	@Override
	public Iterator<Transition> iterator() {
		return _transitions.iterator();
	}

	public void addTransition(Transition t) {
		_transitions.add(t);
	}

	public void removeTransition(Transition t) {
		_transitions.remove(t);
	}

	private List<Transition> _transitions = new LinkedList<Transition>();

};

