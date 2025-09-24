package renders;

import jatrace.*;
import jatrace.skies.*;
import jatrace.bodies.*;

public class SkewRoom
{
	
	public static void main(String [] args)
	{
		
		double win_w = 6.4, win_h = 4.0;
		
		int	ppu = 50, 
		    passes = 8,
		    depth = 8;
		
		if (args.length > 0)
		{
			ppu = Integer.decode(args[0]);
		}
		
		if (args.length > 1)
		{
			passes = Integer.decode(args[1]);
		}
		
		if (args.length > 2)
		{
			depth = Integer.decode(args[2]);
		}
		
		int pix_w = (int) (win_w * ppu),
		    pix_h = (int) (win_h * ppu);
		
		String filename = "Askew-" + pix_w + "x" + pix_h + ".png";
		
		
		Sphere ball = new Sphere(new Vector(0.0,0.2,0.0), 1.0, new Color(0.2,0.1,0.1) );
		ball.setReflectivity(0.6);
		
		CheckPlane floor = new CheckPlane(
			new Vector(0.0,0.0,0.0),
			new Vector(0.0,1.0,0.0),
			new Vector(1.0,0.0,0.0)
		);
		floor.setColor(new Color(0.1,0.1,0.2));
		floor.setColor(new Color(0.99,0.99,0.99));
		floor.setReflectivity(0.0);
		
		
		Color wColor = new Color(0.01,0.01,0.01);
		OneWayPlane [] walls = {
		      new OneWayPlane( new Vector(0.0,0.0, 6.0), new Vector(1.0,0.0,-3.0),  wColor.dup() ),
		      new OneWayPlane( new Vector(0.0,0.0,-6.0), new Vector(-1.0,0.0,3.0),  wColor.dup() ),
		      new OneWayPlane( new Vector( 6.0,0.0,0.0), new Vector(-3.0,0.0,-1.0), wColor.dup() ),
		      new OneWayPlane( new Vector(-6.0,0.0,0.0), new Vector(3.0,0.0,1.0),  wColor.dup() ),
		      new OneWayPlane( new Vector(0.0,6.0,0.0),  new Vector(0.0,-1.0,0.0),   wColor.dup() )
		};
		
		for (OneWayPlane p : walls)
		{
			p.setReflectivity(0.6);
		}
		
		//Sphere ceiling = new Sphere(new Vector(0.0,-50.0,0.0), 56.0, wColor.dup());
		//ceiling.setReflectivity(0.6);
		
		World world = new World(walls, new Bluesky());
		world.addBody(floor).addBody(ball);//.addBody(ceiling);
		world.setBaseBrightness(0.9);
		
		Camera cam = new Camera( new Vector(5.0,2.5,10.0), new Vector(0.0,1.0,0.0), win_w, win_h);
		cam.setPPU(ppu);
		cam.setDelta(0.05);
		
		Tracer t = new Tracer(world, cam);
		t.setSampleDepth(depth).draw(passes).write(filename);
		
	}
}
