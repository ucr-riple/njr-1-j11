package ori.mas.core;

import ori.ogapi.geometry.Surface;
import ori.ogapi.geometry.Shape;
import ori.ogapi.geometry.Point;
import ori.ogapi.util.Iterator;

/**
 * Represents the agent environment surface.
 * <p>Note that this class is only for easier reworking.</p>
 */
public interface Scene extends Surface<Body> {

	/**
	 * Adds a body into the scene.
	 * @param b The body to add.
	 * @return <ul><li><code>true</code> if the scene has been modified,</li>
	 * <li><code>false</code> otherwise.</li></ul>
	 */
	@Override public abstract boolean add(Body b);

	/**
	 * Removes a body from the scene.
	 * @param b The body to remove.
	 * @return <ul><li><code>true</code> if the scene has been modified,</li>
	 * <li><code>false</code> otherwise.</li></ul>
	 */
	@Override public abstract boolean remove(Body b);

	/**
	 * Checks if a body is contained into the scene.
	 * @param b The body to check.
	 * @return <ul><li><code>true</code> if the scene contains the body,</li>
	 * <li><code>false</code> otherwise.</li></ul>
	 */
	@Override public abstract boolean contains(Body b);

	/**
	 * Computes the subscene containing all bodies inside the specific shape.
	 * @param s The shape 'mask'.
	 * @return The corresponding subscene.
	 */
	@Override public abstract Scene getIn(Shape s);

	/**
	 * Computes the subscene containing all bodies which intersect the specific shape.
	 * @param s The shape 'mask'.
	 * @return The corresponding subscene.
	 */
	@Override public abstract Scene getPartlyIn(Shape s);

	/**
	 * Computes the subscene reduced to a single point.
	 * @param p A point.
	 * @return The corresponding subscene.
	 */
	@Override public abstract Scene getAt(Point p);

	/**
	 * Gets an iterator over the bodies.
	 * @return An iterator.
	 */
	@Override public abstract Iterator<Body> iterator();

};

