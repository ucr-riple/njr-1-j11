package ori.mas.percepts;

import ori.mas.core.Percept;
import ori.mas.core.Sensor;

public class AbstractPercept implements Percept {

	public AbstractPercept() {
		_type = TYPE.UNDEFINED;
		_source = null;
	}

	public AbstractPercept(TYPE type) {
		_type = type;
		_source = null;
	}

	public AbstractPercept(Sensor source) {
		_type = TYPE.UNDEFINED;
		_source = source;
	}

	public AbstractPercept(TYPE type, Sensor source) {
		_type = type;
		_source = source;
	}

	@Override
	public TYPE type() {
		return _type;
	}

	@Override
	public Sensor source() {
		return _source;
	}

	@Override
	public void setSource(Sensor value) {
		_source = value;
	}

	TYPE _type;
	Sensor _source;

};

