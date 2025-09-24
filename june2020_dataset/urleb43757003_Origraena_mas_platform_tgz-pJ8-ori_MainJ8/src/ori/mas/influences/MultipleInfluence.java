package ori.mas.influences;

import ori.mas.core.Actor;
import ori.mas.core.Influence;

import java.util.LinkedList;
import java.util.Iterator;

public class MultipleInfluence extends AbstractInfluence implements Iterable<Influence> {

	public MultipleInfluence() {
		super();
	}

	public MultipleInfluence(Actor source) {
		super(source);
	}

	public void add(Influence i) {
		i.setSourceActor(this.sourceActor());
		_influences.add(i);
	}

	@Override
	public Iterator<Influence> iterator() {
		return _influences.iterator();
	}
	
	private LinkedList<Influence> _influences = new LinkedList<Influence>();

};

