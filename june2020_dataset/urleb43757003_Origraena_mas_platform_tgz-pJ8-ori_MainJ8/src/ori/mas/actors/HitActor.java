package ori.mas.actors;

import ori.mas.core.Body;
import ori.mas.core.Properties;

public class HitActor extends ChangeBodyIntegerPropertyActor {

	public HitActor(Body b) {
		super(b,Properties.HEALTH,0,Integer.MAX_VALUE);
	}

	public HitActor(Body b, int max) {
		super(b,Properties.HEALTH,0,max);
	}

	public HitActor(Body b, int min, int max) {
		super(b,Properties.HEALTH,min,max);
	}

};

