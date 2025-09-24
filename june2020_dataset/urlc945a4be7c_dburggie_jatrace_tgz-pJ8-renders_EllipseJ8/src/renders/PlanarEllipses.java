package renders;

import jatrace.*;
import jatrace.bodies.*;

public class PlanarEllipses extends GenericRenderer
{
	
	private static final Vector xAxis = new Vector(1.0,0.0,0.0);
	private static final Vector yAxis = new Vector(0.0,1.0,0.0);
	private static final Vector zAxis = new Vector(0.0,0.0,1.0);
	
	public PlanarEllipses()
	{
		
		super();
		
		setBaseBrightness(0.6);
		
		setFilenamePrefix("PlanarEllipseTest");
		
		jatrace.bodies.Ellipse oval = new jatrace.bodies.Ellipse( new Vector(0.0,0.0,-2.0), new Vector(1.0,0.0,0.0), new Color(0.9,0.2,0.2) );
		oval.setAxes(yAxis.dup().scale(2.0), zAxis.dup().scale(1.25) );
		oval.setReflectivity(0.5);
		addBody(oval);
		
		CheckEllipse choval = new CheckEllipse( new Vector(0.0,0.0,2.0), new Vector(1.0,0.0,0.0), new Vector(0.0,1.0,0.0) );
		choval.setAxes(yAxis.dup().scale(2.0), zAxis.dup().scale(1.25) );
		choval.setReflectivity(0.5);
		addBody(choval);
		
		Sphere ball = new Sphere(new Vector(15.0,0.0,0.0), 4.0, new Color(0.2,0.9,0.2) );
		addBody(ball);
		
		setCamFocus(new Vector(0.0,0.0,0.0));
		setCamPosition(new Vector(10.0,1.5,0.0) );
		setWindow(8.0,8.0);
		
	}
	
	public static void main(String [] args)
	{
		
		PlanarEllipses world = new PlanarEllipses();
		world.parseArgs(args);
		world.render();
		
	}
}
