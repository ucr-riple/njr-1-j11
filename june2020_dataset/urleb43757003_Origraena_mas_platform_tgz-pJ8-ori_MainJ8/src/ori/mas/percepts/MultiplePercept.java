package ori.mas.percepts;

import ori.mas.core.Percept;
import ori.mas.core.Sensor;

import java.util.LinkedList;
import java.util.Iterator;

public class MultiplePercept extends AbstractPercept implements Iterable<Percept> {

	public MultiplePercept() {
		super();
	}

	public MultiplePercept(Sensor source) {
		super(source);
	}

	public void add(Percept i) {
		i.setSource(this.source());
		_percepts.add(i);
	}

	@Override
	public Iterator<Percept> iterator() {
		return _percepts.iterator();
	}
	
	private LinkedList<Percept> _percepts = new LinkedList<Percept>();

};

