package ori.mas.fsm.states;

import ori.mas.core.Agent;
import ori.mas.core.Actor;
import ori.mas.core.Body;
import ori.mas.core.Scene;
import ori.mas.core.Properties;

import ori.mas.actors.Actors;
import ori.mas.actors.MovementActor;

import ori.mas.fsm.Transition;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * Requirements : "target" property must be set.
 */
public class FollowState extends AbstractState {

	public FollowState() {
		super();
	}

	public FollowState(List<Transition> transitions) {
		super(transitions);
	}

	@Override 
	public Actor actor(Agent a, Scene world) {
		//System.out.println("followstate");
		MovementActor actor = Actors.selectMaxSpeedMovementActor(a.body().actors());
		if (actor == null) {
			System.out.println("no actor");
			return null;
		}
		actor.setHead((Body)(a.body().get(Properties.TARGET)));
		return actor;
	}

};

