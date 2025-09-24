package jatrace;

/** This class is mostly a wrapper for the World and Camera classes and
 *  provides methods to accomplish the actual tracing of rays and subsequent
 *  image rendering. */
public class Tracer
{
	
	protected World world;
	protected Camera camera;
	protected myImage image;
	protected int ppu, width, height;
	private int depth = 8;
	
	/** Initializes new Tracer object to given world and camera. Modification
	 *  of the World and Camera objects after Tracer instantiation can be done
	 *  through the normal World and Camera class methods. */
	public Tracer(World w, Camera c)
	{
		this.setWorld(w);
		this.setCamera(c);
		this.image = null;
	}
	
	/** Initializes new Tracer object with default World and Camera objects.
	 *  References to the World and Camera objects can subsequently be obtained
	 *  through the getWorld() and getCamera() methods.*/
	public Tracer()
	{
		this.setWorld();
		this.setCamera();
		this.image = null;
	}
	
	/** Sets how many reflections are calculated recursively per sample. */
	public Tracer setSampleDepth(int depth)
	{
		this.depth = depth; return this;
	}
	
	/** Calling object will trace within a default world. */
	public Tracer setWorld()
	{
		return this.setWorld(new World());
	}
	
	/** Calling object will trace within the given world. */
	public Tracer setWorld(World w)
	{
		this.world = w;
		return this;
	}
	
	/** Calling object will trace with default Camera settings. */
	public Tracer setCamera()
	{
		return this.setCamera(new Camera());
	}
	
	/** Calling object will trace with given Camera settings.*/
	public Tracer setCamera(Camera c)
	{
		this.camera = c; 
		ppu = c.getPPU();
		width  = (int) ( ppu * c.getWidth()  );
		height = (int) ( ppu * c.getHeight() );
		return this;
	}
	
	/** Calling object will draw the its World object from the perspective of
	 *  its Camera object. This is accomplished by tracing given number of rays
	 *  from the camera origin through each pixel in the image. Dealiasing
	 *  depends on the number of passes per pixel. Depth of Field blur is
	 *  accomplished by adding a origin delta to the Camera object. */
	public Tracer draw(int passes)
	{
		if (world == null)
		{
			this.setWorld();
		}
		if (camera == null)
		{
			this.setCamera();
		}
		
		double passDim = 1.0 / passes;
		
		image = new myImage(width, height);
		
		Color c = new Color();
		
		for (int y = 0; y < height; y++) 
		{
			
			if ( y % 100 == 0 )
			{
				System.out.println("line " + y + " of " + height + "done");
			}
			
			for (int x = 0; x < width; x++)
			{
				c.setRGBA(0.0,0.0,0.0,0.0);
				
				for (int p = 0; p < passes; p++) {
					c = c.add( world.sample(camera.getRay(x,y), depth) );
				}// END PIXEL SAMPLING
				
				c.dim(passDim);
				image.setPixel(x,y,c);
				
			}// END OF SCANLINE
			
			// get time info
			// print line execution time
			
		}// END IMAGE RENDER
		
		return this;
		
	} // END DRAW METHOD
	
	/** Calls draw(8). */
	public Tracer draw()
	{
		return this.draw(8);
	}
	
	/** Writes a drawn image with a default name to disk. */
	public Tracer write()
	{
		String filename = "trace-" + width + "x" + height + ".png";
		return this.write(filename);
	}
	
	/** Writes a drawn image into a file of the given name. Note that this will
	 *  fail fatally on an IOException. See Camera.write(). */
	public Tracer write(String filename)
	{
		System.out.println("Printing to file '" + filename + "'");
		image.write(filename);
		System.out.println("All done!");
		return this;
	}
}

