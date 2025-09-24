package jatrace.bodies;

import jatrace.*;

public class CheckEllipse extends CheckPlane
{
	
	public CheckEllipse(Vector p, Vector n, Vector o)
	{
		super(p,n,o);
	}
	
	public CheckEllipse()
	{
		super();
	}
	
	private Vector xAxis = new Vector(1.0,0.0,0.0); 
	private Vector yAxis = new Vector(0.0,1.0,0.0);
	
	public final void setAxes(Vector xDir, Vector yDir)
	{
		
		xLength = xDir.len();
		xAxis = xDir.dup().norm();
		yLength = yDir.len();
		yAxis = yDir.dup().norm();
		setNormal(xAxis.cross(yAxis));
		axesSet = true;
		
		setOrientation(xAxis);
	}
	
	private boolean axesSet = false;
	private double xLength, yLength;
	public final void setAxisLengths(double x, double y)
	{
		xLength = x; yLength = y;
	}
	
	@Override
	public double intersection(Ray ray)
	{
		if ( ! axesSet )
		{
			return -1.0;
		}
		
		double distance = super.intersection(ray);
		
		if (distance < 0.0) return distance;
		
		Vector p = ray.follow(distance);
		p.trans(position, -1.0);
		
		double xd = p.dot(xAxis);		
		double yd = p.dot(yAxis);
		
		double distanceInPlane = xd * xd / xLength / xLength;
		distanceInPlane += yd * yd / yLength / yLength;
		
		if (distanceInPlane > 1.0) return -1.0;
		
		return distance;
		
	}
}
