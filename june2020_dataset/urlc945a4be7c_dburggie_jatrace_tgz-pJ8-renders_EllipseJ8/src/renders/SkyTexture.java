package renders;

import jatrace.*;
import jatrace.bodies.*;
import jatrace.skies.*;

public class SkyTexture extends World
{
	
	public Sphere floor;
	public Sphere globe;
	public TexturedSky sky;
	public Camera camera;
	
	public SkyTexture()
	{
		super();
		
		Vector origin = new Vector(0.0,0.0,0.0);
		Color color = new Color(0.3,0.3,0.3);
		Vector up = new Vector(0.0,1.0,0.0);
		
		setBaseBrightness(0.2);
		
		
		floor = new Sphere(new Vector(-7.5,0.0,0.0), 6.5, color);
		floor.setReflectivity(0.8);
		addBody(floor);
		
		globe = new Sphere(origin, 1.0,  color);
		globe.setReflectivity(0.8);
		addBody(globe);
		
		sky = new TexturedSky();
		sky.setSun(new Vector(1.0,0.1,1.0));
		sky.load("img/starfield_texture.png");
		setSky(sky);
		
	}
	
	public void render(Camera camera, int passes)
	{
		
		int width, height;
		width = (int) (camera.getPPU() * camera.getWidth());
		height = (int) (camera.getPPU() * camera.getWidth());
		
		Tracer tracer = new Tracer(this, camera);
		tracer.draw(passes).write("SkyTexture-" + width + "x" + height + ".png");
		
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
		
		
		Vector camP = new Vector(10.0, 2.0, 2.0);
		Vector camF = new Vector(0.0,0.0,0.0);
		Vector camU = new Vector(0.0,1.0,0.0);
		
		Camera camera = new Camera(camP,camF,camU,4.0,4.0,ppu);
		camera.setDelta(delta);
		
		SkyTexture world = new SkyTexture();
		world.render(camera, passes);	
		
		
	}
	
	
}
