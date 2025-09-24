package jatrace.skies;

import jatrace.*;

/** This Sky class has a very white color */
public class White implements Sky
{
	protected Vector sun;
	protected final Color color = new Color(0.999,0.999,0.999);
	
	/** Constructs a new White sky object. */
	public White()
	{
		sun = new Vector(0.0,1.0,0.0);
	}
	
	/** Gets color of sky in given direction. */
	@Override
	public Color getColor(Vector direction)
	{
		//double gamma = Math.pow(1 - direction.dot(sun), 99.0);
		return color.dup()/*.gamma(gamma)*/;
	}
	
	/** Gets references to light vectors. */
	@Override
	public Vector [] getLight()
	{
		Vector [] L = { new Vector(0.0,1.0,0.0) };
		return L;
	}
}
