package jatrace.bodies;

import jatrace.*;

/** A sphere that has been truncated and is thus more-or-less barrel-shaped. */
public class TruncatedSphere extends Sphere
{
	protected Plane topCap, bottomCap;
	protected double cosine = 1.0;
	private Vector topPosition, bottomPosition;
	
	private boolean initLocked()
	{
		if (unlocked) return false;
		else return true;
	}
	
	private boolean unlocked = false;
	private void unlock()
	{
		unlocked = true;
	}	
	
	public TruncatedSphere(Vector p, Vector o, double r, Color c)
	{
		super(p,r,c);
		
		unlock();
		
		setOrientation(o);
		setCosine(0.7);
	}
	
	/** Gets vector normal to Body at given point. */
	public Vector getNormal(Vector point)
	{
		Vector secant = point.sub(position);
		
		//are we on the surface of the sphere?
		if (Math.abs(secant.dot(secant) - RR) < 0.000001)
		{
			return secant.norm();
		}
		
		//we're on one of the end caps then
		else
		{
			
			//return orientation if on the top cap
			if (secant.dot(orientation) > 0.0)
			{
				return orientation.dup();
			}
			
			//return minus orientation if we're on the bottom
			else
			{
				return orientation.minus();
			}
		}
		
	}
	
	
	public double intersection(Ray ray)
	{
		
		Vector rayOrigin = ray.o(), rayDirection = ray.d();
		
		//get distance to planes along ray
		double topDistance = topPosition.sub(rayOrigin).dot(orientation),
			bottomDistance = bottomPosition.sub(rayOrigin).dot(orientation),
			raySpeed = orientation.dot(rayDirection);
		topDistance /= raySpeed;
		bottomDistance /= raySpeed;
		
		//order the plane hits
		double [] planeHits = new double[2];
		planeHits[0] = Math.min(topDistance,bottomDistance);
		planeHits[1] = Math.max(topDistance,bottomDistance);
		
		// no hits if ray goes away from both planes
		if (planeHits[1] < 0.0)
		{
			return -1.0;
		}
		
		//now distance to both sphere hits
		Vector S;
		S = rayOrigin.sub(position);
		
		//get distance to sphere center
		double SS = S.dot(S);
		
		//just don't bother if it's too far away
		if (SS > 1000000.0) return -1.0;
		
		
		//measure angle between ray direction and to sphere center
		double SD = S.dot(rayDirection);
		
		//intersections are at the solutions to a quadratic equation
		
		//get the terms under the radical
		double radical = SD * SD + RR - SS;
		
		//if radical is negative, we have no real solutions.
		if (radical < 0.0) { return -1.0; }
		//otherwise, we get the root of it
		else radical = Math.sqrt(radical);
		
		//get both sphere hits
		double [] sphereHits = new double[] { -1 * SD - radical, -1 * SD + radical };
		//if the farther one is negative, we miss totally
		if (sphereHits[1] < 0.0) { return -1.0; }
		
		//miss if both sphere hits are before the plane hits (or vice versa)
		if (planeHits[1] < sphereHits[0] || sphereHits[1] < planeHits[0])
		{
			return -1.0;
		}
		
		
		/* The lowest hit is not ever correct, but neither is the last one.
		 * Thus, after discounting the lowest hit, our proper intersection is
		 * the first positive of the other type.
		 */
		
		double distance = Math.max(planeHits[0],sphereHits[0]);
		if (distance < 0.0)
		{
			distance = Math.min(planeHits[1],sphereHits[1]);
			
			if (distance < 0.0)
			{
				return -1.0;
			}
		}
		
		return distance;
		
		/*
		if (planeHits[0] < sphereHits[0])
		{
			if (sphereHits[0] > 0.0)
			{
				return sphereHits[0];
			}
			
			else if (sphereHits[1] > 0.0)
			{
				return sphereHits[1];
			}
		}
		
		else
		{
			if (planeHits[0] > 0.0)
			{
				return planeHits[0];
			}
			else if (planeHits[1] > 0.0)
			{
				return planeHits[1];
			}
		}
		
		//If we get here, we're on a very weird edge case and it's safe to miss
		return -1.0;
		
		*/
		
	}
	
	@Override
	public void setOrientation(Vector o)
	{
		super.setOrientation(o);
		
		if ( initLocked() ) return;
		else resetCaps();
		
		//if (!initLocked) { resetCaps(); }
		
	}
	
	/** This sets the "cosine" of the body, which is a measure of the angle
	 *  between the orientation vector and the rim of the planar caps
	 *  (specifically, it is the cosine of that angle). */
	public void setCosine(double cos)
	{
		cosine = cos;
		resetCaps();
	}
	
	private void resetCaps()
	{
		if ( initLocked() )
		{
			System.out.println("Exiting resetCaps() because of lock.");
			return;
		}
		
		topPosition = position.dup();
		topPosition.trans( orientation, cosine * radius );
		//System.out.println("Setting North cap to " + topPosition.toString() );
		
		bottomPosition = position.dup();
		bottomPosition.trans( orientation, -1.0 * cosine * radius );
		//System.out.println("Setting South cap to " + bottomPosition.toString() );
		
		if (topCap == null)
		{
			topCap = new Plane();
		}
		
		if (bottomCap == null)
		{
			bottomCap = new Plane();
		}
		
		topCap.setPosition(topPosition);
		topCap.setNormal(orientation);
		
		bottomCap.setPosition(bottomPosition);
		bottomCap.setNormal(orientation.minus());
	}
	
}
