package renders;

import jatrace.*;
import jatrace.bodies.*;
import jatrace.skies.*;

public class Trunk extends World
{
	
	public Camera camera;
	public CheckPlane plane;
	
	public Trunk(double delta, int ppu)
	{
		super();
		
		plane = new CheckPlane();
		addBody(plane);
		
		Vector 
			cam_p = new Vector(0.0,1.0,10.0),
			cam_f = new Vector(0.0,1.0,0.0),
			cam_u = new Vector(0.0,1.0,0.0);
		
		camera = new Camera(cam_p,cam_f,cam_u,4.0,4.0,ppu);
		
		Sky sky = new Bluesky();
		setSky(sky);
		
	}
	
	public void render(int passes)
	{
		
		Tracer tracer = new Tracer(this, camera);
		tracer.draw(passes).write("TrunkRender.png");
		
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
		
		System.out.println("     Focus setting: " + delta  );
		System.out.println("    Pixels setting: " + ppu    );
		System.out.println("    passes setting: " + passes );
		
		Trunk trunk = new Trunk(delta, ppu);
		
		Vector ballp = new Vector(0.0,1.0,0.0);
		double ballr = 1.0;
		Color  ballc = new Color(0.7,0.1,0.4);
		Vector ballo = new Vector(1.0,1.0,1.0);
		
		
		//Sphere ball = new Sphere(ballp,ballr,ballc);
		TruncatedSphere ball = new TruncatedSphere(ballp,ballo,ballr,ballc);
		trunk.addBody(ball);
		
		
		Sphere tiny = new Sphere(ballp,0.2,new Color(0.9,0.9,0.9));
		trunk.addBody(tiny);
		
		trunk.render(passes);
		
	}
	
}
