package ori.mas.actors;

import ori.mas.core.Body;
import ori.mas.core.Properties;

import ori.mas.influences.MultipleInfluence;
import ori.mas.influences.ChangeBodyIntegerPropertyInfluence;
import ori.mas.influences.DeathInfluence;

public class EatActor extends AbstractActor {

	public EatActor(Body b, int feedInc) {
		super(b);
		_feedInc = feedInc;
	}

	@Override
	public MultipleInfluence act() {
		MultipleInfluence result = new MultipleInfluence();
		result.add(new DeathInfluence(this,this.target()));
		result.add(new ChangeBodyIntegerPropertyInfluence(this,this.body(),Properties.FEED,new Integer(_feedInc)));
		return result;
	}

	public void setTarget(Body target) {
		_target = target;
	}

	public Body target() {
		return _target;
	}

	@Override
	public EatActor clone() {
		return new EatActor(this.body(),_feedInc);
	}

	private int _feedInc = 1;
	private Body _target;

};

