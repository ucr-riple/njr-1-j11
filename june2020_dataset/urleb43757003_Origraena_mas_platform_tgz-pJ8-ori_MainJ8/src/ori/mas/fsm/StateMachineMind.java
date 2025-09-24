package ori.mas.fsm;

import ori.mas.core.AbstractMind;
import ori.mas.core.Agent;
import ori.mas.core.Actor;
import ori.mas.core.Percept;
import ori.mas.core.Scene;

import ori.mas.percepts.SurfacePercept;

/**
 * State-based mind.
 * <p>
 * This mind uses an internal state machine to determine the next actor to
 * select.
 * It percepts the world in a "as is" way, meaning a Scene instance is used
 * to represent the world.
 * </p>
 * <p>To implement hierarchical state machines, see the SuperState class.</p>
 * @see State
 * @see StateMachine
 * @see SuperState
 */
public class StateMachineMind extends AbstractMind {

	public StateMachineMind() {
		super();
		_stateMachine = new StateMachine();
	}

	public StateMachineMind(State init) {
		super();
		_stateMachine = new StateMachine(init);
	}

	public StateMachineMind(Agent a) {
		super(a);
		_stateMachine = new StateMachine();
	}

	public StateMachineMind(Agent a, State init) {
		super(a);
		_stateMachine = new StateMachine(init);
	}

	@Override
	public void percept(Percept percept) {
		if (percept instanceof SurfacePercept)
			_world = ((SurfacePercept)percept).sceneView();
	}

	@Override
	public Actor nextActor() {
		if (_stateMachine == null)
			return null;
		Actor a = _stateMachine.nextActor(this.agent(),_world);
		//System.out.println("StateMachineMind actor : "+a);
		return a;
	}

	@Override
	public StateMachineMind clone() {
		// TODO state . clone
		return new StateMachineMind(this.agent(),_stateMachine.current());
	}

	private StateMachine _stateMachine;
	private Scene _world;
	
	public State current() {
			return ((StateMachine)(_stateMachine.current())).current();
		}
};
