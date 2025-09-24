package jatrace.bodies;

import jatrace.*;

public class OneWayPlane extends Plane
{
	
	//Vector n;
		
	/** Instantiates a plane with given positin, normal, and color. */	
	public OneWayPlane(Vector p, Vector n, Color c)
	{
		super(p,n,c);
		//n = super.getNormal(new Vector(0.0,0.0,0.0));
	}
	
	/** Instantiates a default plane (gray, at origin, pointing up). */	
	public OneWayPlane()
	{
		super();
		//n = super.getNormal(new Vector(0.0,0.0,0.0));
	}
	
	
	@Override
	public double intersection(Ray ray)
	{
		Vector d = ray.d();
		
		//if (n.dot(d) > 0.0)
		if (super.normal.dot(d) > 0.0)
		{
			return -1.0;
		}
		
		else
		{
			return super.intersection(ray);
		}
	}
	
}
