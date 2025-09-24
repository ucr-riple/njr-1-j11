package ori.mas.influences;

import ori.mas.core.Actor;
import ori.mas.core.Body;

public class DeathInfluence extends PhysicalInfluence {

	public DeathInfluence(Body target) {
		super(target);
	}

	public DeathInfluence(Actor source, Body target) {
		super(source,target);
	}

};

