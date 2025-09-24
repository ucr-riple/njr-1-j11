package ori.mas.fsm.states;

import ori.mas.core.Agent;
import ori.mas.core.Actor;
import ori.mas.core.Scene;

import ori.mas.actors.Actors;
import ori.mas.actors.MovementActor;

import ori.mas.fsm.Transition;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * Walks randomly.
 */
public class PatrolState extends AbstractState {

	public PatrolState() {
		super();
	}

	public PatrolState(List<Transition> transitions) {
		super(transitions);
	}

	@Override 
	public Actor actor(Agent a, Scene world) {
		//System.out.println("patrolstate");
		MovementActor actor = Actors.selectMaxSpeedMovementActor(a.body().actors());
		if (actor == null) {
			System.out.println("no actor");
			return null;
		}
		_angle = _angle + random(_minAngle,_maxAngle);
		actor.setAngle(_angle);
		actor.setSpeed(actor.maxSpeed());
		return actor;
	}

	private int _angle = 0;
	private int _minAngle = -30;
	private int _maxAngle = 30;

};

