package ori.mas.core;

import ori.ogapi.util.Iterator;
import ori.ogapi.util.AbstractIterator;
import ori.ogapi.report.Reportable;
import ori.ogapi.report.Reporter;
import ori.ogapi.geometry.Surface;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class World implements Iterable<Agent>,Reportable {

	private Scene _scene;
	private Heart _heart;

	public World(Heart h, Scene s) {
		setHeart(h);
		_scene = s;
	}

	public void setHeart(Heart h) {
		_heart = h;
		_heart.setWorld(this);
	}

	public void setScene(Scene s) {
		_scene = s;
	}
	
	public void tick() {
		for (Agent a : this)
			_heart.submitInfluence(a.tick(this));
		_heart.pulse();
	}

	public Scene scene() {
		return _scene;
	}

	@Override 
	public Iterator<Agent> iterator() {
		return new AgentIterator(this.scene().iterator());
	}

	public boolean remove(Agent a) {
		if ((a.hasBody()) && (_scene.remove(a.body())))
			return true;
		return false;
	}

	public boolean add(Agent a) {
		if (a.hasBody())
			return _scene.add(a.body());
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void reportIn(Reporter out) {
		out.newSection(this.hashCode()+"@World{\n");
		out.incSection();
	//	out.report(this.scene());
		for (Agent a : this) {
			out.report(a);
		}
		out.decSection();
		out.newLine();
		out.report("}");
	}
	
	protected class AgentIterator extends AbstractIterator<Agent> {
		public AgentIterator(Iterator<Body> it) {
			_iterator = it;
		}
		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}
		@Override
		public Agent next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException("no more physical agent body");
			return _iterator.next().agent();
		}
		@Override
		public void remove() throws UnsupportedOperationException,NoSuchElementException {
			_iterator.remove();
		}
		private Iterator<Body> _iterator;
	};

};

