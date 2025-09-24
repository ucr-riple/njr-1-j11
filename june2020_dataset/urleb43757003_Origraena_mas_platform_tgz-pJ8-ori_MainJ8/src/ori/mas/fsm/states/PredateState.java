package ori.mas.fsm.states;

import ori.mas.core.Agent;
import ori.mas.core.Actor;
import ori.mas.core.Body;
import ori.mas.core.Scene;
import ori.mas.core.Properties;

import ori.ogapi.util.Filter;

import ori.mas.fsm.SuperState;
import ori.mas.fsm.Transition;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * Example of SuperState.
 * <br />
 * This state has three children :
 * <ul><li>Patrol,</li>
 * <li>Follow,</li>
 * <li>Eat.</li></ul>
 * <br />
 * <p>
 * The global agent behaviour is the following, first he patrols.
 * If he found a matching target (which gets a <code>true</code> answer
 * to the internal filter function), he then changes to the follow state.
 * If he loses his target, then he goes back to the patrol state, but if he
 * goes near enough, he changes to the eat state.
 * Once this state is reached, he eats its target (thus, gains some feed
 * level, and kills the target), and goes back to the patrol state.
 * </p>
 */
public class PredateState extends SuperState {

	public PredateState() {
		super();
		_targetFilter = new Filter<Body>() {
			@Override
			public boolean filter(Body b) {
				return true;
			}
		};
		this.preprocess();
	}

	public PredateState(Filter<Body> targetFilter) {
		_targetFilter = targetFilter;
		this.preprocess();
	}

	public void preprocess() {
		PatrolState s1 = new PatrolState();
		FollowState s2 = new FollowState();
		EatState s3 = new EatState();

		Transition s1tos2 = new Transition(s2) {
			@Override
			public boolean isValid(Agent a, Scene w) {
				for (Body b : w) {
					if ((b != a.body()) && (_targetFilter.filter(b))) {
						a.body().set(Properties.TARGET,b);
						return true;
					}
				}
				return false;
			}
		};

		Transition s2tos1 = new Transition(s1) {
			@Override
			public boolean isValid(Agent a, Scene w) {
				Body t = (Body)(a.body().get(Properties.TARGET));
				if ((t == null) || (!(w.contains(t)))) {
					return true;
				}
				return false;
			}
		};

		Transition s2tos3 = new Transition(s3) {
			@Override
			public boolean isValid(Agent a, Scene w) {
				// TODO not bounding rect
				if (w.getPartlyIn(a.body().boundingRect()).contains((Body)(a.body().get(Properties.TARGET)))) {
					return true;
				}
				return false;
			}
			
		};

		Transition s3tos1 = new Transition(s1) {
			@Override
			public boolean isValid(Agent a, Scene w) {
				Body t = (Body)(a.body().get(Properties.TARGET));
				return (t == null);
			}
		};

		Transition s3tos2 = new Transition(s2) {
			@Override
			public boolean isValid(Agent a, Scene w) {
				Body t = (Body)(a.body().get(Properties.TARGET));
				return ((t != null)
				    && (!(w.getPartlyIn(a.body()).contains(t))));
			}
		};

		s1.addTransition(s1tos2);
		s2.addTransition(s2tos1);
		s2.addTransition(s2tos3);
		s3.addTransition(s3tos1);
		s3.addTransition(s3tos2);

		this.setCurrent(s1);
	}

	private Filter<Body> _targetFilter;

};

