package jatrace;

import jatrace.bodies.Sphere;
import jatrace.skies.Bluesky;

/** This class maintains a list of Body objects and provides methods for
 *  sampling the color of the world along a ray (for the purpose of 3D
 *  rendering). */
public class World
{
	protected LinkedBody bodies;
	protected Sky sky;
	protected Vector lights[];
	protected myInterface i;
	protected double baseBrightness;
	
	private void init()
	{
		i = new myInterface();
		baseBrightness = 0.4;
	}
	
	public World()
	{
		this.init();
	}
	
	/** Initializes object with a linked list of Body objects and a Sky. */
	public World(LinkedBody b, Sky s)
	{
		this.init();
		bodies = b;
		this.setSky(s);
	}
	
	/** Initializes object with array of bodies and a Sky. */
	public World(Body b[], Sky s)
	{
		this.init();
		for ( Body x : b )
		{
			this.addBody(x);
		}
		this.setSky(s);
	}
	
	/** Adds a body to the world. */
	public World addBody(Body b)
	{
		LinkedBody lb = new LinkedBody(b);
		lb.insertBefore(bodies);
		bodies = lb;
		return this;
	}
	
	/** Changes the world's sky to the given. */
	public World setSky(Sky s)
	{
		sky = s;
		return this;
	}
	
	/** Returns an array of vectors towards light sources in the sky. */
	public World getLights()
	{
		lights = sky.getLight();
		return this;
	}
	
	/** Sets the minimum brightness at which objects are rendered. Brightness is
	 *  otherwise a function of how much illumination a point is getting from
	 *  the sky's light sources. */
	public World setBaseBrightness(double b)
	{
		assert (b >= 0.0 && b <= 1.0) : "Set base brightness out of bounds";
		baseBrightness = b;
		return this;
	}
	
	/** Sets closest intersection of ray with bodies in the world into the
	 *  world's interface member. */
	private void trace(Ray ray, Body lastHit)
	{
		i.reset();
		LinkedBody lb = bodies;
		Body b;
		double distance;
		
		while (lb != null)
		{
			b = lb.b();
			distance = b.intersection(ray);
			if (distance > 0.0)
			{
				if (b != lastHit || distance > 0.00001)
				{
					i.hit(b,distance);
				}
			}
			lb = lb.next();
		}
		
		i.registerHit(ray);
	}
	
	/** Finds the closest intersection of the ray with bodies in the world. */
	protected void trace(Ray ray)
	{
		this.trace(ray, null);
	}
	
	/** Finds the illumination a point is receiving. */
	private double shade()
	{
		Vector poi = i.poi;
		Ray ray;
		LinkedBody lb;
		Body b;
		
		int numLights = lights.length;
		double	distance = -1.0,
				brightness = 0.0, 
				lambertian = 0.0, 
				brightnessPerLight = 1.0 / numLights;
		boolean hitSomething;
		
		for (Vector L : lights)
		{
			lambertian = L.dot(i.normal);
			if (lambertian > 0.0)
			{
				hitSomething = false;
				ray = new Ray(i.poi, L);
				lb = bodies;
				while (lb != null)
				{
					b = lb.b();
					lb = lb.next();
					distance = b.intersection(ray);
					
					if (distance > 0.0 && (b != i.body || distance > 0.00001))
					{
						lb = null;
						hitSomething = true;
					}
				}
				if (!hitSomething)
				{
					brightness += brightnessPerLight * lambertian;
				}
			}
		}
		
		brightness = Math.max(brightness, baseBrightness);
		return Math.min(1.0, brightness);
	}
	
	/** creates a specular highlight for matte surfaces. */
	protected Color highlight(double lux, Ray ray)
	{
		lux = Math.max(lux, baseBrightness);
		
		double highlight = 0.0;
		Vector d = ray.reflect(i.poi, i.normal).d();
		for (Vector L : lights)
		{
			highlight = Math.max(highlight, d.dot(L));
		}
		
		highlight = Math.pow(highlight, i.exp);
		return i.color.dim(lux).gamma(1 - highlight);
	}
	
	/** Samples the world along a ray, tracking what body we're reflecting off
	 *  of for numerical stability reasons. */
	public Color sample(Ray ray, int depth, Body lastHit)
	{
		
		this.trace(ray,lastHit);
		
		//return sky color if we didn't hit anything
		if (i.body == null)
		{
			return sky.getColor(ray.d());
		}
		
		//find surface illumnation
		this.getLights();
		double lux = this.shade();
		
		// return a specularly highlighted color for matte object
		if (i.isMatte)
		{
			return this.highlight(lux, ray);
		}
		
		//handle depth
		if (depth == 0)
		{
			return i.color.dim(lux);
		} 
		else
		{
			double cosine = Math.abs(ray.d().dot(i.normal));
			
			double R = i.body.getReflectivity(i.poi);
			double D = 1.0 - R;
			double sPower = R + D * Math.pow(1.0 - cosine, i.exp);
			double tPower = 1.0 - sPower;
			
			ray.reflect(i.poi,i.normal);
			
			Color tColor = i.color.dim(lux).dim(tPower);
			Color sColor = this.sample(ray, depth - 1, i.body).dim(sPower);
			
			return tColor.add(sColor);
		}
	}
	
	/** Samples the world along an array, allowing for the given reflection
	 *  depth. */
	public Color sample(Ray ray, int depth)
	{
		return this.sample(ray, depth, null);
	}
	
	/** Returns the Color (pre multiplied by brightness) along a ray. */
	public Color sample(Ray ray)
	{
		return this.sample(ray, 8, null);
	}
}
