package ori.mas.fsm.states;

import ori.mas.core.Agent;
import ori.mas.core.Actor;
import ori.mas.core.Scene;

import ori.mas.fsm.State;
import ori.mas.fsm.Transition;

import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public abstract class AbstractState implements State {

	public AbstractState() {
		_transitions = new LinkedList<Transition>();
	}

	public AbstractState(List<Transition> transitions) {
		_transitions = transitions;
	}

	@Override abstract
	public Actor actor(Agent a, Scene world);

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

	private List<Transition> _transitions;
	
	public static final Random _random = new Random();
	public static int random(int min, int max) {
		int r = _random.nextInt();
		r = (r % (max - min));
		if (r < 0)
			r += (max - min);
		r += min;
		//System.out.println("random (min="+min+";max="+max+";r="+r);
		return r;
	}

};

