package jatrace;

/** A package that encapsulates the intersection of a ray with a surface (e.g.
 *  surface color, surface orientation, surface type, reflectivity, etc). */
final class myInterface
{
	public boolean isMatte;
	public double exp, distance, reflectivity;
	public Vector poi, normal;
	public Body body;
	public Color color;
	
	/** Default constructor describes "no interface of ray and surface". */
	public myInterface()
	{
		distance = -1.0;
		poi = null;
		normal = null;
		body = null;
		color = null;
		isMatte = false;
		exp = 1.0;
		reflectivity = 0.0;
	}
	
	/** Constructor describes an interface with a surface b at point p, with
	 *  normal n, and c. */
	public myInterface(double d, Vector p, Vector n, Body b, Color c)
	{
		distance = d;
		poi = p;
		normal = n;
		body = b;
		color = c;
		if (body != null)
		{
			isMatte = body.isMatte(poi);
			exp = body.getSpecularity(poi);
			reflectivity = body.getReflectivity(poi);
		}
		else
		{
			isMatte = false;
			exp = 1.0;
			reflectivity = 0.0;
		}
	}
	
	/** Creates an interface that exactly copies the calling object. */
	public myInterface dup()
	{
		return new myInterface(distance, poi, normal, body, color);
	}
	
	/** Resets interface description of "no surface-ray interface". */
	public myInterface reset()
	{
		distance = -1.0;
		poi = null;
		normal = null;
		body = null;
		color = null;
		isMatte = false;
		exp = 1.0;
		reflectivity = 0.0;
		return this;
	}
	
	/** Changes description to the given Body if it's closer. Useful in during
	 *  ray tracing process in World class. */
	public myInterface hit(Body b, double d)
	{
		if (body == null)
		{
			body = b;
			distance = d;
		}
		else if (d < distance)
		{
			body = b;
			distance = d;
		}
		return this;
	}
	
	/** Calculates all the interface parameters of the given ray and the
	 *  closest body passed to hit() method since last interface reset. */
	public myInterface registerHit(Ray r)
	{
		if (body != null && distance > 0.0)
		{
			poi = r.follow(distance);
			normal = body.getNormal(poi);
			color = body.getColor(poi);
			exp = body.getSpecularity(poi);
			reflectivity = body.getReflectivity(poi);
			isMatte = body.isMatte(poi);
		}
		
		return this;
	}
}
