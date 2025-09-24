package jatrace.skies;

import jatrace.*;

/** A basic implementation of the Sky interface with a blue sky and a single 
 *  bright sun. */
public class Bluesky implements Sky
{
    protected Vector sun;
    protected final Color color = new Color(0.3,0.3,0.9999);
    
    //constructors
	/** Default constructor has its sun in the positive y direction. */
    public Bluesky()
    {
        sun = new Vector(0.0,1.0,0.0);
    }
    
	/** This constructor builds a sky with the sun in the given direction. */
    public Bluesky(Vector direction)
    {
        sun = direction.dup().norm();
    }
    
	/** Gets the color of the sky in the given direction. */
    @Override
    public Color getColor(Vector direction)
    {
        double exp = Math.pow(Math.max(0.0,direction.dot(sun)), 20.0);
        return color.dup().gamma(1 - exp);
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
