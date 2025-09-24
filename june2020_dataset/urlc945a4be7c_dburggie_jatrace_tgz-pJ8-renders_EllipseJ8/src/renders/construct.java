package renders;

import jatrace.*;
import jatrace.skies.*;
import jatrace.bodies.*;

public class construct
{
	
	public static void main(String [] args)
	{
		
		
		double win_w = 25.0, win_h = 15.0;
		
		int	ppu = 100, 
		    passes = 8,
		    pix_w = (int) (win_w * ppu),
		    pix_h = (int) (win_h * ppu);
		    
		String filename = "Construct-" + pix_w + "x" + pix_h + ".png";
		
		Vector up = new Vector(0.0,1.0,0.0);
		Vector cam_p = new Vector(0.0,15.0,50.0);
		Vector cam_f = new Vector(0.0,5.0,0.0);
		Camera camera = new Camera(cam_p, cam_f, up, win_w, win_h, ppu);
		
		
		White sky = new White();
		
		
		World world = new World();
		world.setSky(sky);
		world.setBaseBrightness(0.9);
		
		
		Plane floor = new Plane(
			new Vector(0.0,0.0,0.0),
			new Vector(0.0,1.0,0.0),
			new Color(0.9,0.9,0.9)
		);
		
		floor.setReflectivity(0.1);
		world.addBody(floor);
		
		Sphere s;
		Color sphereColor = new Color(0.2,0.1,0.1);
		double radius = 1.0;
		Vector [] centers = {
			new Vector(1.0,0.0,0.0).norm().scale(10).trans(up),
			new Vector(0.0,0.0,1.0).norm().scale(10).trans(up),
			new Vector(-1.0,0.0,0.0).norm().scale(10).trans(up),
			new Vector(0.0,0.0,-1.0).norm().scale(10).trans(up),
			new Vector(1.0,0.0,1.0).norm().scale(10).trans(up),
			new Vector(1.0,0.0,-1.0).norm().scale(10).trans(up),
			new Vector(-1.0,0.0,1.0).norm().scale(10).trans(up),
			new Vector(-1.0,0.0,-1.0).norm().scale(10).trans(up),
			new Vector(0.0,1.0,0.0).norm().scale(10).trans(up),
			new Vector(1.0,1.0,0.0).norm().scale(10).trans(up),
			new Vector(-1.0,1.0,0.0).norm().scale(10).trans(up),
			new Vector(0.0,1.0,1.0).norm().scale(10).trans(up),
			new Vector(0.0,1.0,-1.0).norm().scale(10).trans(up)
		};
		
		
		for (Vector v : centers)
		{
			s = new Sphere(v, radius, sphereColor.dup());
			s.setReflectivity(0.4);
			s.setSpecularity(5.0);
			world.addBody(s);
		}
		
		new Tracer(world,camera).draw(passes).write(filename);
		
	}

}
