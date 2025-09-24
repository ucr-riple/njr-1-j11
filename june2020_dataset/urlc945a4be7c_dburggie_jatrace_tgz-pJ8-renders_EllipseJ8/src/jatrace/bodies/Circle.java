package jatrace.bodies;

import jatrace.*;

public class Circle extends Plane
{
	
	public Circle()
	{
		this(
				new Vector(0.0,0.0,0.0), 
				new Vector(0.0,1.0,0.0), 
				new Color(0.1,0.1,0.1), 
				5.0
			);
	}
	
	public Circle(Vector position, Vector normal, Color color, double radius)
	{
		super(position,normal,color);
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
