package jatrace;

/** Provides methods useful for doing Vector-Math within 3-Space. */
public class Vector
{
	
	protected double x, y, z;
	
	/** Default Vector is the origin: [0.0, 0.0, 0.0] */
	public Vector()
	{
		x = 0.0; y = 0.0; z = 0.0;
	}
	
	/** Initializes Vector to given xyz coordinates. */
	public Vector( double X, double Y, double Z )
	{
		x = X; y = Y; z = Z;
	}
	
	/** Creates new Vector object as exact copy of the caller. */
	public Vector dup()
	{
		return new Vector(x,y,z);
	}
	
	/** Copies given object into calling object */
	public Vector copy(Vector v)
	{
		x = v.x; y = v.y; z = v.z;
		return this;
	}
	
	/** Sets xyz coordinates of calling object to those given. */
	public Vector setxyz(double X, double Y, double Z)
	{
		x = X; y = Y; z = Z;
		return this;
	}
	
	/** Performs a translation of calling vector by the given vector:
	 *  essentially vector addition. */
	public Vector trans( Vector v )
	{
		x += v.x; y += v.y; z += v.z;
		return this;
	}
	
	/** Performs a translation of calling vector by the given vector
	 *  premultiplied by a scalar. This has many more applications that it would
	 *  appear. */
	public Vector trans( Vector v, double s )
	{
		x += s * v.x;
		y += s * v.y;
		z += s * v.z;
		return this;
	}
	
	/** Translates the calling vector by the given xyz values. */
	public Vector trans( double dx, double dy, double dz )
	{
		x += dx; y += dy; z += dz;
		return this;
	}
	
	/** Returns the dot product of calling object with given vector. This 
	 *  operation is associative (barring error from floating point math). */
	public double dot(Vector v)
	{
		return x * v.x + y * v.y + z * v.z;
	}
	
	/** Performs cross product of the two vectors. The output vector will be
	 *  perpendicular to both with magnitude equal to |v| x |w| x sin(D) where D
	 *  is the angle between them. Thus, parallel vectors produce a zero cross
	 *  product. */
	public static Vector cross(Vector v, Vector w)
	{
		return new Vector(
			v.y * w.z - v.z * w.y,
			v.z * w.x - v.x * w.z,
			v.x * w.y - v.y * w.x
		);
	}
	
	/** A.cross(B) equivilent to Vector.cross(A,B). */
	public Vector cross(Vector v)
	{
		return new Vector(
			y * v.z - z * v.y, 
			z * v.x - x * v.z,
			x * v.y - y * v.x 
		);
	}
	
	/** Scales the vector by the given scalar. */
	public Vector scale(double s)
	{
		x *= s; y *= s; z *= s;
		return this;
	}
	
	/** Returns a new object that is equal to the calling vector minus
	 *  the passed vector. */
	public Vector sub(Vector v)
	{
		return new Vector(x - v.x, y - v.y, z - v.z);
	}
	
	/** Returns the length of the calling vector. With non-NaN coordinates, this
	 *  will always be positive. */
	public double len()
	{
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	/** Projects the calling vector onto the passed vector. */
	public Vector proj(Vector v)
	{
		double dot = this.dot(v);
		return this.copy(v).scale(dot);
	}
	
	/** Normalizes the calling vector to length 1.0. */
	public Vector norm()
	{
		double l = this.len();
		if (l < 0.0000001)
		{
			System.out.println("Tried to normalize a zero Vectoror.");
			return this.setxyz(0.0,1.0,0.0);
		}
		x /= l; y /= l; z /= l;
		return this;
	}
	
	/** Adds a vector in a random direction with length AT MOST d to the calling
	 *  vector. All vectors within the ball of radius d about the end point of
	 *  the calling vector are equally likely results of this method. */
	public Vector delta(double d)
	{
		double dx,dy,dz;
		do {
			dx = 2.0 * Math.random() - 1.0;
			dy = 2.0 * Math.random() - 1.0;
			dz = 2.0 * Math.random() - 1.0;
		} while (dx * dx + dy * dy + dz * dz < 1.0);
		
		return this.trans(dx * d, dy * d, dz * d);
	}
	
	
	private static double round(double d)
	{
		return Math.round(1000 * d) / 1000.0;
	}
	/** Returns the vector as a String in the form "x: _ y: _ z: _". */
	public String toString()
	{
		String s = "x: ";
		s += round(x);
		s += " y: ";
		s += round(y);
		s += " z: ";
		s += round(z);
		return s;
	}
	
	/** Returns a new Vector object that is in the opposite direction. */
	public final Vector minus() { return dup().scale(-1.0); }
	
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	
}
