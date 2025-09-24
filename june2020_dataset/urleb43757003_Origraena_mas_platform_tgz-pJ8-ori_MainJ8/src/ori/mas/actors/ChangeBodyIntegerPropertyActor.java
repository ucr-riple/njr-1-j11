package ori.mas.actors;

import ori.mas.core.Body;
import ori.mas.influences.ChangeBodyIntegerPropertyInfluence;

import ori.ogapi.util.OperatorPlus;
import java.util.Comparator;

public class ChangeBodyIntegerPropertyActor extends BodyTargetActor {

	public ChangeBodyIntegerPropertyActor(String property) {
		super();
		_property = property;
	}

	public ChangeBodyIntegerPropertyActor(Body b, String property) {
		super(b);
		_property = property;
		_minValue = Integer.MIN_VALUE;
		_maxValue = Integer.MAX_VALUE;
		_value = 0;
	}

	public ChangeBodyIntegerPropertyActor(Body b, String property, int min, int max) {
		super(b);
		_property = property;
		_minValue = min;
		_maxValue = max;
		_value = min;
	}

	public Integer value() {
		return new Integer(_value);
	}

	public void setValue(int value) {
		_value = value;
		if (_value < _minValue)
			_value = _minValue;
		else if (_value > _maxValue)
			_value = _maxValue;
	}

	public void setMinValue(int min) {
		_minValue = min;
		if (_value < min)
			_value = min;
	}

	public int minValue() {
		return _minValue;
	}

	public void setMaxValue(int max) {
		_maxValue = max;
		if (_value > max)
			_value = max;
	}

	public int maxValue() {
		return _maxValue;
	}

	public String property() {
		return _property;
	}

	@Override
	public ChangeBodyIntegerPropertyInfluence act() {
		return new ChangeBodyIntegerPropertyInfluence(this,
		                                              this.target(),
		                                              this.property(),
		                                              this.value());
	}

	@Override
	public ChangeBodyIntegerPropertyActor clone() {
		return new ChangeBodyIntegerPropertyActor(this.body(),
		                                          _property,
		                                          _minValue,
		                                          _maxValue);
	}

	private int _value;
	private int _minValue;
	private int _maxValue;

	private String _property;

};

