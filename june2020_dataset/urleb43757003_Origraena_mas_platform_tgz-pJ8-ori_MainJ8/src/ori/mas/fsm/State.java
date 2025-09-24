package ori.mas.fsm;

import ori.mas.core.Agent;
import ori.mas.core.Actor;
import ori.mas.core.Scene;

import java.util.Iterator;

/**
 * A state is defined by a method selecting the next actor, and by
 * an iterator over its transitions.
 * @see Transition
 */
public interface State extends Iterable<Transition> {

	/**
	 * Computes the next action, ie choose an actor from
	 * <code>agent.body().actors()</code>.
	 * @param agent The agent to be considered.
	 * @param world The local perception of the world by the agent.
	 * @return The selected actor for the next tick.
	 */
	public Actor actor(Agent agent, Scene world);
	@Override public Iterator<Transition> iterator();

};

