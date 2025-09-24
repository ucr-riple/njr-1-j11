package renders;

import jatrace.*;
import jatrace.bodies.*;
import jatrace.skies.*;

public class Texture extends World
{
	
	public TexturedSphere globe;
	public CheckPlane floor;
	public Sky sky;
	public Camera camera;
	
	public Texture()
	{
		super();
		
		setBaseBrightness(0.2);
		
		Vector p = new Vector(0.0,0.0,0.0);
		
		globe = new TexturedSphere(p, 1.0, "img/earth_texture.png");
		addBody(globe);
		
		//floor = new CheckPlane(new Vector(0.0,0.0,0.0), p, new Vector(0.0,0.0,1.0));
		//addBody(floor);
		
		sky = new PlainSky(new Vector (1.0,0.1,1.0), new Color(0.01,0.01,0.01));
		//sky = new Horizon();
		setSky(sky);
		
	}
	
	public void render(Camera camera, int passes)
	{
		
		int width, height;
		width = (int) (camera.getPPU() * camera.getWidth());
		height = (int) (camera.getPPU() * camera.getWidth());
		
		Tracer tracer = new Tracer(this, camera);
		tracer.draw(passes).write("Texture-" + width + "x" + height + ".png");
		
	}
	
	public static void main(String [] args)
	{
		
		double delta = 0.0;
		int ppu = 100;
		int passes = 8;
		
		try
		{
			
			//get delta
			if (args.length > 0)
			{
				delta = Double.parseDouble(args[0]);
			}
			
			//get pixels per unit
			if (args.length > 1)
			{
				ppu = Integer.decode(args[1]);
			}
			
			//get number of passes per pixel
			if (args.length > 2)
			{
				passes = Integer.decode(args[2]);
			}
			
		}
		
		catch (Exception e)
		{
			System.out.println("Error parsing arguments; using defaults.");
			delta = 0.0;
			ppu = 100;
			passes = 8;
		}
		
		
		Vector camP = new Vector(10.0, 3.0, 5.0);
		Vector camF = new Vector(0.0,0.0,0.0);
		Vector camU = new Vector(0.0,1.0,0.0);
		
		Camera camera = new Camera(camP,camF,camU,4.0,4.0,ppu);
		camera.setDelta(delta);
		
		Texture tex = new Texture();
		tex.render(camera, passes);	
		
		
	}
	
	
}
