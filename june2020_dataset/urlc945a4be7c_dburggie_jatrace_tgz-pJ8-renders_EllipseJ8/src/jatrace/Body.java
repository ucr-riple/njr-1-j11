package jatrace;


/**	Provides a standard interface for World to interact with Body */
public abstract class Body
{
	
	// ##### You must override these methods #####
	
	/** Gets vector normal to Body at given point. */
	public abstract Vector getNormal(Vector point);
	
	/** Returns a positive distance along ray to a point of intersection with
	 *  the body ONLY IF an intersection occurs. Any negative value returned
	 *  implies no intersection. However, if no intersection occurs, it is
	 *  standard to return -1.0. */
	public abstract double intersection(Ray ray);
	
	
	
	// ##### You needn't override these methods #####
	
	protected Color color;
	/** Gets color of object at a given point. Default behavior is uniform
	 *  coloring. */
	public Color getColor(Vector point) { return color.dup(); }
	/** Sets color. Override for different than default behavior. */
	public void setColor(Color c) { color = c.dup(); }
	
	
	
	protected Vector position;
	/** Gets position of body. What relation this vector has with the body is up
	 *  to the implementation of the intersection method. */
	public Vector getPosition() { return position.dup(); }
	/** Setsposition of body. What relation this vector has with the body is up
	 *  to the implementation of the intersection method. */
	public void setPosition(Vector p) { position = p.dup(); }
	
	
	
	protected Vector orientation;
	/** Sets the orientation of the body. Many bodies will only need the default
	 *  of a single orientation vector (like a plane), but if more are needed,
	 *  override this and the setOrientation methods. */
	public Vector getOrientation() { return orientation.dup(); }
	/** See documentation for getOrientation. */
	public void setOrientation(Vector o) { orientation = o.dup().norm(); }
	
	
	
	protected double reflectivity;
	/** Returns base amount of light reflected from surface at a point. 
	 *  Must return a value between 0 and 1. */
	public double getReflectivity(Vector point) { return reflectivity; }
	/** Sets standard amount of reflectivity (for uniform reflectivity). Note
	 *  that bodies with matte surfaces ignore this setting. */
	public void setReflectivity(double r) { reflectivity = r; }
	
	
	
	protected double specularity;
	/** For matte surfaces, returns a gamma exponent for creating specular
	 *  highlights. A value of 0.0 will always return white, a value of 1.0
	 *  will not highlight the surface at all. Weird Stuff (tm) might happen
	 *  if values greater than 1.0 are returned. Default implementation is
	 *  uniform shinyness. */
	public double getSpecularity(Vector point) { return specularity; }
	/** Sets specularity (for uniform specularity implementations). Note that
	 *  specularity behaves somewhat differently with a matte surface and a
	 *  spucular surface. For matte surfaces, specularity highlights the surface
	 *  of the body based on where reflections of lightsources would be. For
	 *  specular surfaces, specularity determines how reflectivity changes with
	 *  angle of incidence. For either surface type: low specularity gives more
	 *  reflection, high specularity gives lower reflection. */
	public void setSpecularity(double s) { specularity = s; }
	
	
	
	
	protected boolean matte;
	/** Returns a simple boolean value: Is this surface matte at this point? */
	public boolean isMatte(Vector point) { return matte; }
	/** Default implementation: sets whether body is matte all over or not.
	 *  When implementing a non-uniform finish, it is better to just override
	 *  the isMatte(Vector) method. */
	public void setMatte(boolean m) { matte = m; }
	
	
	/** This default constructor sets default values for the various settings.
	 *  Body will be specular, near-white, reflectivity 30%, position at the
	 *  origin, a specularity of 20.0 and a vertical orientation. */
	public Body()
	{
		this.setMatte(false);
		this.setColor(new Color(0.9,0.9,0.9));
		this.setReflectivity(0.3);
		this.setSpecularity(20.0);
		this.setPosition(new Vector(0.0,0.0,0.0));
		this.setOrientation(new Vector(0.0,1.0,0.0));
	}
	
	
	// As yet unimplemented.
	/* 
	public double getOpacity(Vector point);
	public double getIndex(Vector point);  
	*/
}
