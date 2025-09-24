package jatrace.bodies;

import jatrace.*;

/** Describes a mathematical plane, a surface that is infinitely wide with
 *  universal orientation. */
public class Plane extends Body
{
	
	protected Vector position;
	
	/** Instantiates a plane with given positin, normal, and color. Default
	 *  reflectivity is 20%. */	
	public Plane(Vector p, Vector n, Color c)
	{
		super();
		setPosition(p);
		setNormal(n);
		setColor(c);
		//setOrientation( new Vector(1.0,0.0,0.0) );
		setReflectivity(0.2);
		position = getPosition();
	}
	
	/** Sets all the default parameters of a plane. These are: Position 
	 *  (0.0,0.0,0.0); Normal (0.0,1.0,0.0); Reflectivity 0.2; Color gray; */	
	public Plane()
	{
		super();
		position = getPosition();
		setNormal( new Vector(0.0,1.0,0.0) );
		//setOrientation( new Vector(1.0,0.0,0.0) );
		setReflectivity(0.2);
		setColor(new Color(0.3,0.3,0.3));
	}
	
	protected Vector normal;
	/** Sets surface orientation by the vector normal to the plane. */	
	public void setNormal(Vector n)
	{
		normal = n.dup().norm();
	}
	
	/** Gets the surface normal at the given point. */	
	@Override public Vector getNormal(Vector point)
	{
		return normal.dup();
	}
	
	/** Gets distance along ray to intersection with plane. */	
	@Override
	public double intersection(Ray ray)
	{
		
		// check if ray direction is parallel to plane
    	double projD = normal.dot(ray.d());
    	if (Math.abs(projD) < 0.000001)
    	{
    		return -1.0;
    	}
    	
    	// make sure ray is going towards plane
    	double projS = position.sub(ray.o()).dot(normal);
    	/*
    	if (projD * projS < 0.0)
    	{
    		return -1.0;
    	}
    	
    	// return ratio of magnitudes: 
    	//(distance between plane and ray) / (speed ray approaches plane)
    	*/
    	return projS / projD;
    	
	}
}
