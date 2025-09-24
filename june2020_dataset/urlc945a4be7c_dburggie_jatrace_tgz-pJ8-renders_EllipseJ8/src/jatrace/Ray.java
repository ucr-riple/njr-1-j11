package jatrace;

/** This class describes a pair of rays, one to the ray origin, the other, a
 *unit vector, specifying the ray direction. */
public class Ray
{
	protected Vector origin = new Vector(0.0,0.0,0.0),
		direction = new Vector(0.0,1.0,0.0);
	
	/** Default constructor describes ray at origin going up. */
	public Ray()
	{
		origin = new Vector(0.0,0.0,0.0);
		direction = new Vector(0.0,1.0,0.0);
	}
	
	/** Instantiates ray with origin O and direction d. */
	public Ray(Vector o, Vector d)
	{
		this.setOrigin(o);
		this.setDirection(d);
	}
	
	/** Instantiates a Ray object as exact duplicate of the calling object. */
	public Ray dup()
	{
		return new Ray(origin, direction);
	}
	
	/** Sets the ray origin to the given vector. */
	public Ray setOrigin(Vector v)
	{
		origin = v.dup();
		return this;
	}
	
	/** Sets the ray direction to the given vector. This vector need not be
	 *  pre-normalized by the norm() method. */
	public Ray setDirection(Vector v)
	{
		direction.copy(v).norm();
		return this;
	}
	
	/** returns a copy of the ray origin. */
	public Vector o() { return origin.dup(); }
	
	/** Copies origin into given vector. */
	public Vector copyOriginTo(Vector v) { return v.copy(origin); }
	
	/** returns a copy of the ray direction. */
	public Vector d() { return direction.dup(); }
	
	/** Copies ray direction into given vector. */
	public Vector copyDirectionTo(Vector v) { return v.copy(direction); }
	
	/** Returns vector to the point from the ray origin along the ray for the
	 *  given distance. */
	public Vector follow(double distance)
	{
		return origin.dup().trans(direction, distance);
	}
	
	
	/** Reflects the ray direction about the given normal and sets the ray
	 *  origin to the given position. */
	public Ray reflect(Vector position, Vector normal)
	{
		origin = position.dup();
		double dot = direction.dot(normal);
		direction.trans(normal, -2.0 * dot);
		// Once I implement reflection of vectors:
		//direction.reflectAbout(normal);
		return this;
	}
	
	/* Once implemented, this method will allow for ray refraction. 
	
	public Ray refract(Vector position, Vector normal, double T_sine)
	{
		// check for total internal reflection
		if (T_sine > 1.0) return this.reflect(position, normal);
		
		// we know where our new origin is
		this.setOrigin(position);
		
		// new direction is between normal and our original direction
		Vector p = direction.dup().proj(normal).trans(direction, -1.0).scale(1 - T_sine);
		
		// on the off chance we're near zero, don't change the direction
		if (p.len() < 0.0000001) return this;
		
		else return this.setDirection(p);
	}
	*/
}
