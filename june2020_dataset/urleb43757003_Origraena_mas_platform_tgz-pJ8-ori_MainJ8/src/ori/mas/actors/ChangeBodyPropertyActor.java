package ori.mas.actors;

import ori.mas.core.Body;
import ori.mas.influences.ChangeBodyPropertyInfluence;

import ori.ogapi.util.OperatorPlus;
import java.util.Comparator;

public class ChangeBodyPropertyActor extends BodyTargetActor {

	public ChangeBodyPropertyActor() {
		super();
	}

	public ChangeBodyPropertyActor(Body b) {
		super(b);
	}

	public ChangeBodyPropertyActor(Body b, String property) {
		super(b);
		_property = property;
	}

	public ChangeBodyPropertyActor(Body b, String property, Object value) {
		super(b);
		_property = property;
		_value = value;
	}

	public String property() {
		return _property;
	}

	public Object value() {
		return _value;
	}

	public void setValue(Object value) {
		_value = value;
	}

	@Override
	public ChangeBodyPropertyInfluence act() {
		return new ChangeBodyPropertyInfluence(this,
		                                       this.target(),
		                                       this.property(),
		                                       this.value());
	}

	@Override
	public ChangeBodyPropertyActor clone() {
		return new ChangeBodyPropertyActor(this.body(),
		                                   _property,
		                                   _value);
	}

	private String _property;
	private Object _value;

};

