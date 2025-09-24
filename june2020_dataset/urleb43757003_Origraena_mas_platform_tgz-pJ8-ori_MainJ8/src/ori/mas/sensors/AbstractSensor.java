package ori.mas.sensors;

import ori.mas.core.Body;
import ori.mas.core.Percept;
import ori.mas.core.Sensor;
import ori.mas.core.World;

public abstract class AbstractSensor implements Sensor {

	public AbstractSensor() {
		_body = null;
	}

	public AbstractSensor(Body b) {
		_body = b;
	}

	@Override
	public void setBody(Body b) {
		_body = b;
	}

	protected Body body() {
		return _body;
	}

	@Override public abstract Percept sense(World w);
	@Override public abstract AbstractSensor clone();

	private Body _body;

};

