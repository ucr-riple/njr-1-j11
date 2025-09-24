package jatrace.bodies;

import jatrace.*;

public class TexturedSphere extends Sphere
{
	
	private final static double pi = Math.PI;
	
	private boolean textureLoaded = false;
	private myImage texture;
	private double halfWidth;
	private int fullHeight;
	
	private Vector xAxis, yAxis, zAxis;
	
	private void init()
	{
		setCoordinates(new Vector(0.0,1.0,0.0), new Vector(1.0,0.0,0.0));
		setMatte(false);
		setReflectivity(0.01);
		setSpecularity(20.0);
		textureLoaded = false;
	}
	
	/** Default sphere with no texture. Will not clip with rays until a valid
	 *  texture file has been loaded. */
	public TexturedSphere()
	{
		super();
		init();
	}
	
	/** Initializes new sphere with given position and radius, loading the
	 *  texture at the given path. */
	public TexturedSphere(Vector pos, double rad, String texturePath)
	{
		
		super(pos,rad,new Color(0.3,0.1,0.1));
		init();
		load(texturePath);
		
	}
	
	
	/** Loads image at given path as the sphere's texture. */
	public void load(String texturePath)
	{
		texture = myImage.read(texturePath);
		
		if (texture != null)
		{
			halfWidth = texture.getWidth() / 2.0;
			fullHeight = texture.getHeight();
			textureLoaded = true;
		}
		
		else
		{
			System.out.println("Failed to load texture '" + texturePath + "'.");
			halfWidth = 0.0;
			fullHeight = 0;
			textureLoaded = false;
		}
	}
	
	
	/** Sets the orientation of the textured sphere.
	 *  @param pole the sphere's north pole
	 *  @param meridian a vector on the sphere's prime meridian */
	public void setCoordinates(Vector pole, Vector meridian)
	{
		zAxis = meridian.cross(pole);
		
		if (zAxis.len() < 0.000001)
		{
			System.out.println("You fed parallel vectors to a TexturedSphere.");
			xAxis = new Vector(1.0,0.0,0.0);
			yAxis = new Vector(0.0,1.0,0.0);
			zAxis = new Vector(0.0,0.0,1.0);
		}
		
		else
		{
			zAxis.norm();
			xAxis = pole.cross(zAxis).norm();
			yAxis = zAxis.cross(xAxis).norm();
		}
		
	}
	
	private final static String bounds = "acos is returning out of bounds";
	
	@Override
	public Color getColor(Vector point)
	{
		
		/* We need to find the xpart, the y part of the point. */
		
		Vector v = point.sub(position).scale(1 / radius);
		
		double xDistance = v.dot(xAxis), zDistance = v.dot(zAxis);
		
		Vector projection = xAxis.dup().scale( xDistance );
		projection.trans(zAxis, zDistance).norm();
		
		double south = Math.acos( yAxis.dot(v) ) / pi;
		double eastwest = Math.acos( xAxis.dot(projection) ) / pi;
		
		//x and y are coordinates on the texture
		int x, y;
		
		//check if point is east or west of meridian
		if ( zDistance > 0.0 )
		{
			x = (int) ( halfWidth - halfWidth * eastwest );
		}
		else
		{
			x = (int) ( halfWidth + halfWidth * eastwest );
		}
		
		y = (int) ( fullHeight * south );
		
		return texture.getPixel(x,y);
		
	}
	
	int hitcount, misscount;
	public void displayNumHits()
	{
		System.out.println("Hit by " + hitcount + " rays.");
		System.out.println("Missed by " + misscount + " rays.");
	}
	
	@Override
	public double intersection(Ray ray)
	{
		if ( textureLoaded )
		{
			return super.intersection(ray);
		}
		
		else
		{
			return -1.0;
		}
	}
}
