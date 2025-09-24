package ori.mas.core;

import ori.ogapi.geometry.Surface;
import ori.ogapi.geometry.Shape;
import ori.ogapi.geometry.Point;
import ori.ogapi.util.Iterator;

/**
 * An <code>AdaptedScene</code> instance is linked to a
 * <code>Surface&lt;Body&gt;</code> instance.
 * <p>
 * This class allows to use a more generic Surface&lt;T&gt; 
 * subclass while implementing the Scene interface.
 * </p>
 */
public class AdaptedScene implements Scene {

	public AdaptedScene(Surface<Body> surface) {
		_surface = surface;
	}

	@Override
	public boolean add(Body b) {
		return _surface.add(b);
	}

	@Override
	public boolean remove(Body b) {
		return _surface.remove(b);
	}

	@Override
	public boolean contains(Body b) {
		return _surface.contains(b);
	}

	@Override
	public Scene getIn(Shape s) {
		return new AdaptedScene(_surface.getIn(s));
	}

	@Override
	public Scene getPartlyIn(Shape s) {
		return new AdaptedScene(_surface.getPartlyIn(s));
	}

	@Override
	public Scene getAt(Point p) {
		return new AdaptedScene(_surface.getAt(p));
	}

	@Override 
	public Iterator<Body> iterator() {
		return _surface.iterator();
	}

	private Surface<Body> _surface;

};

