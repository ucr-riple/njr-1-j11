package ori.mas.actors;

import ori.mas.core.Actor;

import ori.ogapi.util.Filter;
import ori.ogapi.util.Selector;
import java.util.Comparator;

public class Actors {

	public static final Filter<Actor> FILTER_MOVEMENT = 
		new Filter<Actor>() {
			@Override
			public boolean filter(Actor a) {
				return (a instanceof MovementActor);
			}
		};
	public static final Filter<Actor> FILTER_EAT =
		new Filter<Actor>() {	
			@Override
			public boolean filter(Actor a) {
				return (a instanceof EatActor);
			}
		};
	
	public static final Comparator<Actor> COMPARATOR_MOVEMENT =
		new Comparator<Actor>() {
			@Override
			public int compare(Actor a1, Actor a2) {
				return ((MovementActor)a1).maxSpeed() - ((MovementActor)a2).maxSpeed();
			}
		};

	public static final Selector<Actor> SELECTOR_MOVEMENT = 
		new Selector<Actor>(FILTER_MOVEMENT,COMPARATOR_MOVEMENT);

	public static Actor selectActor(Filter<Actor> f, Iterable<Actor> actors) {
		for (Actor a : actors) {
			if (f.filter(a))
				return a;
		}
		return null;
	}

	public static MovementActor selectMovementActor(Iterable<Actor> actors) {
		return (MovementActor)selectActor(FILTER_MOVEMENT,actors);
	}

	public static EatActor selectEatActor(Iterable<Actor> actors) {
		return (EatActor)(selectActor(FILTER_EAT,actors));
	}

	public static MovementActor selectMaxSpeedMovementActor(Iterable<Actor> actors) {
		return (MovementActor)(SELECTOR_MOVEMENT.selectFrom(actors));
	}

	private Actors() { }

};
