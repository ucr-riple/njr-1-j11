package jatrace.skies;

import jatrace.*;

/** This sky has a texture wrapped around the interior of the sky sphere.
 *  Textures loaded by this class should have an equirectangular transformation
 *  to avoid distortions in the wrapping. */
public class TexturedSky implements Sky
{
	
	private final static double pi = Math.PI;
	protected static final Color background = new Color(0.01,0.01,0.01);
	private static final Vector xAxis = new Vector(1.0,0.0,0.0);
	private static final Vector yAxis = new Vector(0.0,1.0,0.0);
	private static final Vector zAxis = new Vector(0.0,0.0,1.0);
	
	protected myImage texture;
	protected boolean textureLoaded;
	private double halfWidth;
	private int fullHeight;
	
	
	protected Vector sun;
	
	
	private Color highlight( Vector direction, Color color )
	{
        double cosine = Math.max( 0.0, direction.dot(sun) );
        double exponent = Math.pow( cosine, 20.0 );
        return color.gamma(1 - exponent);
	}
	
	@Override
	public Color getColor(Vector direction)
	{
		if ( !textureLoaded )
		{
			return highlight(direction, background.dup());
		}
		
		/* We need to find how far from the north pole we are, then how far
		 * East or West we are. */
		
		// get percent of way south
		double south = Math.acos(yAxis.dot(direction)) / pi;
		
		// project direction onto x-z plane (horizontal plane)
		double xDistance = xAxis.dot(direction),
				zDistance = zAxis.dot(direction);
		
		// get x-part
		Vector projection = xAxis.dup().scale(xDistance);
		
		// add z-part
		projection.trans( zAxis, zDistance).norm();
		
		// now we can get how far east or west we are by dotting with x-axis
		double eastwest = Math.acos( xAxis.dot(projection) ) / pi;
		
		
		// we can get coordinates now by checking with z to see if east or west
		int x, y;
		
		if ( zDistance > 0)
		{
			// going west
			x = (int) (halfWidth - halfWidth * eastwest);
		}
		
		else
		{
			// going east
			x = (int) (halfWidth + halfWidth * eastwest);
		}
		
		y = (int) (south * fullHeight);
		
		Color color = texture.getPixel(x,y);
		
		return highlight(direction, color);
	}
	
	
	
	
	
	private double sunDelta;
	public void setDelta(double d)
	{
		if (d < 0.00001)
		{
			sunDelta = 0.0;
		}
		
		else
		{
			sunDelta = d;
		}
		
	}
	
	public void setSun(Vector sunDirection)
	{
		sun = sunDirection.dup().norm();
	}
	
	@Override
	public Vector [] getLight()
	{
		if (sunDelta < 0.00001)
		{
			return new Vector [] { sun.dup() };
		}
		
		else
		{
			return new Vector [] { sun.dup().delta(sunDelta) };
		}
	}
	
	
	
	
	
	private void init()
	{
		sun = new Vector(0.0,1.0,0.0);
		textureLoaded = false;
		texture = null;
		halfWidth = 0;
		fullHeight = 0;
		sunDelta = 0.0;
	}
	
	/** Default constructor just has a black background color. No texture. */
	public TexturedSky() { init(); }
	
	/** Initializes sky to given direction to sun and texture from the given
	 *  path.
	 *  @param sunDirection vector to sun
	 *  @param texturePath string containing path to sky texture */
	public TexturedSky(Vector sunDirection, String texturePath)
	{
		init();
		setSun(sunDirection);
		load(texturePath);
	}
	
	
	
	
	
	/** Loads image at given path as the sky's texture. */
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
	
	
}
