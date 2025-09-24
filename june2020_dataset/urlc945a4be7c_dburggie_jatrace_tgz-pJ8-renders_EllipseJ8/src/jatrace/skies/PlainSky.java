package jatrace.skies;

import jatrace.*;

public class PlainSky implements Sky
{
	
	public Color color;
	public Vector sun;
	double delta;
	
	public PlainSky(Vector sunDirection, Color skyColor)
	{
		sun = sunDirection.dup().norm();
		color = skyColor.dup();
		delta = 0.0;
	}
	
	public void setDelta(double d)
	{
		if (d < 0.00001)
		{
			delta = 0.0;
		}
		
		else
		{
			delta = d;
		}
	}
	
	
	public Color getColor(Vector direction)
	{
        double exp = Math.pow(Math.max(0.0,direction.dot(sun)), 20.0);
        return color.dup().gamma(1 - exp);
    }
	public Vector [] getLight()
	{
		
		Vector [] array = new Vector [1];
		
		if (delta < 0.0001)
		{
			array[0] = sun.dup();
		}
		
		else
		{
			array[0] = sun.dup().delta(delta).norm();
		}
		
		return array;
	}
	
}
