package jatrace.skies;

import jatrace.*;

/** This Sky class has an interesting bright horizon. */
public class Horizon implements Sky
{
	protected Vector light;
	protected Color color;
	protected final double exp = 20.0;
	protected final Vector up = new Vector(0.0,1.0,0.0);
	
	/** Default constructor is a blue sky with a vertical sun. */
	public Horizon()
	{
		light = new Vector(0.0,1.0,0.0);
		color = new Color(0.2,0.2,0.9);
	}
	
	/** Constructs a Horizon object with given light direction and color. */
	public Horizon( Vector l, Color c )
	{
		light = l.dup().norm();
		color = c.dup();
	}
	
	/** Gets references to light vectors. */
	@Override
	public Vector [] getLight()
	{
		Vector l[] = { light.dup().delta(0.01) };
		return l;
	}
	
	/** Gets color of sky in given direction. */
	@Override
	public Color getColor(Vector direction)
	{
		double d = Math.max(0.0, Math.max( direction.dot(light), 1 - direction.dot(up) ) );
		return color.dup().gamma( 1 - Math.pow(d, exp) );
	}
}
