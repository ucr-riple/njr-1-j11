package ori.mas.core;

import ori.ogapi.report.Reportable;
import ori.ogapi.report.Reporter;

import ori.ogapi.geometry.AdaptedShape;
import ori.ogapi.geometry.Rectangle;
import ori.ogapi.geometry.Shape;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Body extends AdaptedShape implements Reportable {

	public static final Shape DEFAULT_SHAPE = new Rectangle(0,0,3,3);

	public Body() {
		super(DEFAULT_SHAPE.clone());
	}

	public Body(Shape shape) {
		super(shape);
	}

	public Body(Agent agent, Shape shape) {
		super(shape);
		_agent = agent;
	}

	public Influence act() {
		if (_preparedActor == null)
			return null;
		Influence res = _preparedActor.act();
		_preparedActor = null;
		return res;
	}

	public void prepareActor(Actor actor) {
		if (_actors.contains(actor))
			_preparedActor = actor;
	}

	public List<Percept> sense(World w) {
		List<Percept> res = new LinkedList<Percept>();
		for (Sensor s : _sensors)
			res.add(s.sense(w));
		return res;
	}

	public Agent agent() {
		return _agent;
	}

	public void setAgent(Agent agent) {
		_agent = agent;
	}

	public boolean hasActor() {
		return (_actors != null);
	}

	public LinkedList<Actor> actors() {
		return _actors;
	}

	public boolean hasSensor() {
		return (_sensors != null);
	}

	public void addActor(Actor a) {
		if (!(_actors.contains(a))) {
			a.setBody(this);
			_actors.add(a);
		}
	}

	public void addSensor(Sensor s) {
		if (!(_sensors.contains(s))) {
			s.setBody(this);
			_sensors.add(s);
		}
	}

	public Map<String,Object> properties() {
		return _properties;
	}

	public void setProperties(Map<String,Object> pties) {
		_properties = pties;
	}

	public Object get(String property) {
		if (_properties == null)
			return null;
		return _properties.get(property);
	}

	public Object set(String property, Object value) {
		if (_properties == null)
			_properties = new TreeMap<String,Object>();
		return _properties.put(property,value);
	}

	public boolean has(String property) {
		return (this.get(property) != null);
	}

	@Override
	public void reportIn(Reporter out) {
		out.newSection(hashCode()+"@Body{\n");
		out.incSection();
		out.newSection("center:");
		out.report(this.center());
		out.newSection("shape: ");
		out.report(this.shape());
		out.newSection("sensors: ");
		out.report(_sensors);
		out.newSection("actors: ");
		out.report(_actors);
		out.newSection("prepared: ");
		out.report(_preparedActor);
		out.decSection();
		out.newLine();
		out.report("}");
	}

	@Override
	public Body clone() {
		Body b = new Body(this.shape().clone());
		for (Sensor s : _sensors)
			b.addSensor(s.clone());
		for (Actor a : _actors)
			b.addActor(a);
		for (Map.Entry<String,Object> p : _properties.entrySet())
			b.set(p.getKey(),p.getValue());
		return b;
	}

	private Agent _agent;
	private LinkedList<Sensor> _sensors = new LinkedList<Sensor>();
	private LinkedList<Actor> _actors = new LinkedList<Actor>();
	private Actor _preparedActor = null;
	private Map<String,Object> _properties = null;

};

