import jatrace.*;
import jatrace.bodies.*;
import jatrace.skies.*;

public class testTracer
{
	public static void main(String [] args)
	{
		//camera setup
		Camera c = new Camera();
		c.setPPU(100);
		c.setPosition( new Vector(0.0,0.0,10.0) );
		c.setFocus( new Vector(0.0,0.0,0.0) );
		c.setWindow(4.0,4.0);
		
		
		//body setup
		Sphere s1 = new Sphere( new Vector(-1.0,0.0,0.0), 1.0, new Color( 0.9,0.3,0.3) );
		s1.setReflectivity(0.4);
		
		Sphere s2 = new Sphere( new Vector(1.0,0.0,0.0), 1.0, new Color( 0.3,0.3,0.3) );
		s2.setReflectivity(0.4);
		
		
		//sky and world setup
		Bluesky bs = new Bluesky();
		World w = new World();
		w.setSky(bs).addBody(s1).addBody(s2);
		w.setBaseBrightness(0.6);
		
		Tracer t =  new Tracer(w, c);
		t.draw().write();
	}
}
