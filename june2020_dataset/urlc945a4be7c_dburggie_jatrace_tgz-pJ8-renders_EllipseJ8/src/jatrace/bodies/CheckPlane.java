package jatrace.bodies;

import jatrace.*;
import jatrace.bodies.Plane;

/** This class extends the Plane class with a checkered, two color surface
 *  texture. */
public class CheckPlane extends Plane
{
	
	/** Creates a new object with given origin and surface normal. The
	 *  orientation parameter is used to determine both the layout of the
	 *  surface checkers and their size (for unit checkers, orientation should
	 *  be a point on the plane 1 unit from the origin). */
	public CheckPlane( Vector p, Vector n, Vector o )
	{
		super(p,n,new Color());
		setOrientation(o);
		setColor( new Color(0.01,0.01,0.01), new Color(0.99,0.99,0.99) );
	}
	
	public CheckPlane()
	{
		super();
		setColor( new Color(0.01,0.01,0.01), new Color(0.99,0.99,0.99) );
		setOrientation(new Vector(1.0,0.0,0.0));
	}
	
	
	//dealing with orientation
	protected Vector oX, oY;
	/** Sets object's checker orientation. */
	@Override
	public void setOrientation(Vector o)
	{
		super.setOrientation(o);
		if (position != null && normal != null)
		{
			oX = o.dup().norm();
			oY = normal.cross(oX).norm();
			oX = oY.cross(normal).norm();
		}
	}
	
	
	//dealing with color
	protected Color c1, c2;
	
	/** Since this object is two-colored, this function with one color parameter
	 *  treats the colors as a queue, popping the first color and pushing the
	 *  parameter onto the end of the queue. */
	@Override public void setColor(Color c) 
	{
		c2 = c1; c1 = c.dup();
	}
	
	/** Sets both colors of the surface. */
	public void setColor(Color a, Color b) 
	{
		c1 = a.dup(); c2 = b.dup();
	}
	
	/** Gets color of surface at the given point. */
	@Override
	public Color getColor(Vector point)
	{
		Vector d = point.sub(position);
		
		double	lenx = Math.floor( d.dot(oX) ),
				leny = Math.floor( d.dot(oY) );
		
		//int ModularDistance = Math.abs( (int) (lenx + leny) ) % 2;
		if ( 0 == Math.abs( (int) (lenx + leny) ) % 2)
		{
			return c1.dup();
		}
		else
		{
			return c2.dup();
		}
	}
	
	
}
