package jatrace.skies;

import jatrace.*;

/** This class implements a very basic sky that is transparent in the png file
 *  produced, yet still has a sun. */
public class TransparentSky implements Sky
{
	
	Vector sun;
	
	public TransparentSky(Vector s)
	{
		sun = s.dup().norm();
	}
	
	/** Gets the color of the sky: fully transparent fading into full white
	 *  near the sun. */
	@Override
	public Color getColor(Vector direction)
	{
		double alpha;
		alpha = Math.pow( Math.max(0.0, sun.dot(direction) ), 99.0);
		return new Color(1.0,1.0,1.0,alpha);
	}
	
	/** Returns references to the light sources within the sky. */
	@Override
	public Vector [] getLight()
	{
		
		Vector L[] = new Vector [1];
		L[0] = sun.dup().delta(0.05).norm();
		return L;
		
	}
}
