
import jatrace.*;
import jatrace.bodies.*;
import jatrace.skies.*;

public class windowtest {
	
	public static void main(String [] args)
	{
		CheckPlane floor = new CheckPlane(new Vector(0.0,0.0,0.0), new Vector(0.0,1.0,0.0), new Vector(1.0,0.0,0.0));
		Sphere origin = new Sphere(new Vector(0.0,0.0,0.0),1.0, new Color(0.99,0.01,0.01));
		origin.setReflectivity(0.1);
		Sphere corner = new Sphere(new Vector(-5.0,0.0,5.0), 1.0, new Color(0.01,0.99,0.01));
		corner.setReflectivity(0.1);
		
		Vector cam_o = new Vector(0.0,10.0,0.0);
		Vector cam_f = new Vector(0.0,0.0,0.0);
		Vector cam_u = new Vector(0.0,0.0,1.0);
		double width = 10.0, height = 10.0;
		int ppu = 25, passes = 1;
		Camera cam = new Camera(cam_o, cam_f, cam_u, width, height, ppu);
		
		World world = new World();
		world.setBaseBrightness(0.8);
		world.addBody(floor);
		world.addBody(origin);
		world.addBody(corner);
		world.setSky(new Bluesky());
		
		Tracer t = new Tracer(world, cam).draw(passes).write("grid-10x10.png");
		
	}
}
