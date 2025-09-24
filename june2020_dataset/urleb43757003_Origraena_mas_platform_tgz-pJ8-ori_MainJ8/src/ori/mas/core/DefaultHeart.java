package ori.mas.core;

//TODO
import ori.mas.influences.*;

import ori.ogapi.geometry.*;

import ori.ogapi.util.OperatorPlus;
import ori.ogapi.lists.Fifo;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

/**
 * Default heart implementation.
 * <p>For now, this class is used for testing purposes.</p>
 */
public class DefaultHeart implements Heart {

	public DefaultHeart() {
		_world = null;
	}

	public DefaultHeart(World w) {
		_world = w;
	}

	@Override
	public void setWorld(World w) {
		_world = w;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * In this implementation, no influence sum is done.
	 * All submitted influences are applied in the order they have been added.
	 * A classic reimplementing subclass will only call <code>super.pulse()</code> and
	 * then add its specific behaviour, while a more complex heart will completely
	 * reimplement it.
	 * </p>
	 */
	@Override
	public List<Influence> pulse() {
		//System.out.println("Heart.pulse()");
		List<Influence> madeInfluences = new Fifo<Influence>();
		Influence i;
		while (!(_influences.isEmpty())) {
			i = _influences.pop();
			//System.out.println("\tbefore makeInfluence: "+i);
			i = this.makeInfluence(i);
			//System.out.println("\tafter makeInfluence: "+i);
			if (i != null)
				madeInfluences.add(i);
		}
		//System.out.println("\tEmpty!!!");
		return madeInfluences;
	}

	@Override 
	public void submitInfluence(Influence i) {
		//System.out.println("Heart.submitInfluence(i)");
		//System.out.println("\ti = "+i);
		if (i != null)
			_influences.add(i);
	}

	protected void submitPrioritaryInfluence(Influence i) {
		_influences.addFirst(i);
	}

	protected LinkedList<Influence> influences() {
		return _influences;
	}

	/**
	 * Applies an influence and returns the modified influence.
	 * <p>An influence may be modify if the heart desires it. For example, a movement
	 * influence will be modify to stop on the first colliding object from the source to
	 * its destination.
	 * The returned instance will then contain the new destination point (which surely be
	 * the colliding point).</p>
	 * <p>An influence application may submit new influences. In the previous example, a
	 * colliding influence will also be submitted to the heart by itself.
	 * </p>
	 * <p>
	 * The default implementation does nothing and returns <code>null</code>.
	 * </p>
	 * @param i The influence to apply.
	 * @return The influence which has really been applied (may be <code>null</code>).
	 */
	protected Influence makeInfluence(Influence i) {
		if (i instanceof MultipleInfluence) {
			for (Influence j : (MultipleInfluence)i)
				submitPrioritaryInfluence(j);
			return i;
		}
		if (i instanceof PhysicalInfluence)
			return this.makeInfluence((PhysicalInfluence)i);
		if (i instanceof BirthInfluence)
			return this.makeInfluence((BirthInfluence)i);
		return null;
	}

	protected Influence makeInfluence(PhysicalInfluence i) {
		if (i.target() == null)
			return null;
		if (i instanceof MovementInfluence)
			return this.makeInfluence((MovementInfluence)i);
		if (i instanceof CollideInfluence)
			return this.makeInfluence((CollideInfluence)i);
		if (i instanceof ChangeBodyPropertyInfluence)
			return this.makeInfluence((ChangeBodyPropertyInfluence)i);
		if (i instanceof DeathInfluence)
			return this.makeInfluence((DeathInfluence)i);
		return null;
	}

	protected Influence makeInfluence(MovementInfluence i) {
		Body target = i.target();
		if (target == null)
			return null;
		Point vector = i.vector();
		if (vector == null)
			return null;
		List<Point> tmp;
		// TODO set comparator
		Segment wayLine;
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (Point p : target.shape().boundingShape().points()) {
			if (p.x < minX)
				minX = p.x;
			if (p.y < minY)
				minY = p.y;
			if (p.x > maxX)
				maxX = p.x;
			if (p.y > maxY)
				maxY = p.y;
			if (p.x + vector.x < minX)
				minX = p.x + vector.x;
			if (p.y + vector.y < minY)
				minY = p.y + vector.y;
			if (p.x + vector.x > maxX)
				maxX = p.x + vector.x;
			if (p.y + vector.y > maxY)
				maxY = p.y + vector.y;
		}
		Point min,max;
		min = new Point(minX,minY);
		max = new Point(maxX,maxY);

		Surface<Body> s = _world.scene().getPartlyIn(new Rectangle(min,max));
		Body collidingBody = null;
		Point collidingPoint = null;
		int d = Integer.MAX_VALUE;

		for (Point p : target.shape().boundingShape().points()) {
			wayLine = new Segment(p.x,p.y,p.x+vector.x,p.y+vector.y);
			for (Body b : s) {
				if ((b != target) && (this.mayCollide(target,b))) {
					tmp = Shapes.collidePoints(wayLine,b);
					for (Point p2 : tmp) {
						if (p2.euclidian(p) < d) {
							d = p2.euclidian(p);
							collidingBody = b;
							collidingPoint = p2;
							vector.x = p2.x;
							vector.y = p2.y;
							vector.minus(p);
						}
					}
				}
			}
		}
		
		this.callbackMove(i);
		if (collidingBody != null)
			this.submitPrioritaryInfluence(new CollideInfluence(target,collidingBody,collidingPoint));
		return i;
	}

	protected Influence makeInfluence(CollideInfluence i) {
		//System.out.println("colliding : "+i);
		return null;
	}

	protected Influence makeInfluence(ChangeBodyPropertyInfluence i) {
		Object v = i.value();
		OperatorPlus op = i.operator();
		if (op != null) {
			Object c = i.target().get(i.property());
			v = op.exec(c,v);
		}
		Comparator cmp = i.comparator();
		Object min = i.target().get(i.property()+Properties.SUFFIX_MIN);
		Object max = i.target().get(i.property()+Properties.SUFFIX_MAX);
		int limit = 0;
		if ((min != null) || (max != null)) {
			if (cmp != null) {
				if ((min != null) && (cmp.compare(v,min) < 0)) {
					v = min;
					limit = -1;
				}
				else if ((max != null) && (cmp.compare(v,max) > 0)) {
					v = max;
					limit = 1;
				}
			}
			else if (v instanceof Comparable) {
				if ((((Comparable)min) != null) && (((Comparable)v).compareTo((Comparable)min) < 0)) {
					v = min;
					limit = -1;
				}
				else if ((((Comparable)max) != null) && (((Comparable)v).compareTo((Comparable)max) > 0)) {
					v = max;
					limit = 1;
				}
			}
		}
		i.setValue(v);
		callbackChangeBodyProperty(i);
		if (limit < 0)
			onPropertyMinReached(i.target(),i.property());
		else if (limit > 0)
			onPropertyMaxReached(i.target(),i.property());
		return i;
	}

	protected Influence makeInfluence(DeathInfluence i) {
		//System.out.println("death!");
		_world.remove(i.target().agent());
		return i;
	}

	protected Influence makeInfluence(BirthInfluence i) {
		Agent a = i.agent();
		if (this.isValid(a)) {
			_world.add(a);
			return i;
		}
		return null;
	}

	protected void callbackChangeBodyProperty(ChangeBodyPropertyInfluence i) {
		//System.out.println("callbackChangeBodyProperty()");
		//System.out.println("\ti.target = "+i.target());
		//System.out.println("\ti.property = "+i.property());
		//System.out.println("\ti.value = "+i.value());
		i.target().set(i.property(),i.value());
	}

	protected void callbackMove(MovementInfluence i) {
		//System.out.println("callBackMove()");
		//System.out.println("\ti.target = "+i.target());
		//System.out.println("\ti.vector = "+i.vector());
		i.target().translate(i.vector());
	}

	protected void onPropertyMinReached(Body target, String property) {
		if (property.equals(Properties.HEALTH))
			this.submitPrioritaryInfluence(new DeathInfluence(target));
	}

	protected void onPropertyMaxReached(Body target, String property) {

	}

	protected boolean mayCollide(Body b1, Body b2) {
		return true;
	}

	protected boolean isValid(Agent a) {
		// checks validity
		return (a.hasBody());
	}

	private World _world;
	private LinkedList<Influence> _influences = new LinkedList<Influence>();

};

