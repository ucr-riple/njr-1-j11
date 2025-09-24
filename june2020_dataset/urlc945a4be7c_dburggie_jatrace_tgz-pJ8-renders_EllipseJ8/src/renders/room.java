package renders;

import jatrace.*;
import jatrace.skies.*;
import jatrace.bodies.*;

public class room
{
	public static void main(String [] args)
	{
		
		double win_w = 4.0, win_h = 2.5;
		
		int	ppu = 320, 
		    passes = 32,
		    depth = 16,
		    pix_w = (int) (win_w * ppu),
		    pix_h = (int) (win_h * ppu);
		
		String filename = "Room-of-Mirrors-" + pix_w + "x" + pix_h + ".png";
		
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
		
		Color wColor = new Color(0.1,0.1,0.1);
		Plane [] walls = {
		      new Plane( new Vector(0.0,0.0,5.45), new Vector(0.0,0.0,-1.0), wColor.dup() ),
		      new Plane( new Vector(0.0,0.0,-5.45), new Vector(0.0,0.0,1.0), wColor.dup() ),
		      new Plane( new Vector(5.45,0.0,0.0), new Vector(-1.0,0.0,0.0), wColor.dup() ),
		      new Plane( new Vector(-5.45,0.0,0.0), new Vector(1.0,0.0,0.0), wColor.dup() ),
		      new Plane( new Vector(0.0,6.0,0.0), new Vector(0.0,-1.0,0.0), wColor.dup() )
		};
		
		for (Plane p : walls)
		{
			p.setReflectivity(0.6);
		}
		
		World world = new World(walls, new Bluesky());
		world.addBody(floor).addBody(ball);
		world.setBaseBrightness(0.9);
		
		Camera cam = new Camera( new Vector(5.0,2.5,5.0), new Vector(0.0,1.0,0.0), win_w, win_h);
		cam.setPPU(ppu);
		
		Tracer t = new Tracer(world, cam);
		t.setSampleDepth(depth).draw(passes).write(filename);
		
	}
}

/*
bodies = [n_wall, s_wall, w_wall, e_wall, ceiling]
for b in bodies:
    b.set_reflectivity(0.6)
bodies.append(ball)
bodies.append(floor)

world = World(bodies).set_base_brightness(0.9)

cam = Camera(Vector(5.0,2.5,5.0), Vector(0.0,1.0,0.0), win_w, win_h)
cam.set_ppu(ppu)

Tracer(world, cam).draw(passes).write(filename)
*/
