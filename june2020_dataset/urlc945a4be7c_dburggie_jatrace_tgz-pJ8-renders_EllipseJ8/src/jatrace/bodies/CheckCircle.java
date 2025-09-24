package jatrace.bodies;

import jatrace.*;

public class CheckCircle extends CheckPlane
{
	
	public CheckCircle()
	{
		this(
				new Vector(0.0,0.0,0.0),
				new Vector(0.0,1.0,0.0),
				new Vector(1.0,0.0,1.0),
				5.0
			);
	}
	
	public CheckCircle(
			Vector position, Vector normal, 
			Vector orientation, double radius)
	{
		super(position,normal,orientation);
		setColor(new Color(0.1,0.1,0.1));
		setRadius(radius);
	}
	
	double radius, rSquared;
	public void setRadius(double r)
	{
		radius = r;
		rSquared = r * r;
	}
	
	@Override
	public double intersection(Ray ray)
	{
		double distance = super.intersection(ray);
		if (distance < 0.0)
		{
			return -1.0;
		}
		
		Vector point = ray.follow(distance);
		double distanceFromOrigin = point.trans(position, -1.0).dot(point);
		
		if (distanceFromOrigin < rSquared)
		{
			return distance;
		}
		
		else
		{
			return -1.0;
		}
	}
	
	
}
