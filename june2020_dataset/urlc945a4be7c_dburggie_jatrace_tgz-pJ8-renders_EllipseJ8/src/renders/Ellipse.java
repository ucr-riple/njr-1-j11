package renders;

import jatrace.*;
import jatrace.bodies.*;
import jatrace.skies.*;

public class Ellipse extends GenericRenderer
{
	
	
	
	public Ellipse()
	{
		
		super();
		
		setBaseBrightness(0.6);
		
		setFilenamePrefix("Ellipsoid");
		
		CheckCircle floor = new CheckCircle();
		addBody(floor);
		
		Ellipsoid egg = new Ellipsoid();
		egg.setPosition( new Vector(0.0,1.5,0.0) );
		//egg.setColor( new Color(0.9,0.3,0.3) );
		egg.setReflectivity(0.5);
		egg.setAxisLengths(0.5,1.5,0.75);
		addBody(egg);
		
		setCamFocus(new Vector(0.0,1.25,0.0));
		setCamPosition(new Vector(10.0,1.5,0.0) );
		
	}
	
	public static void main(String [] args)
	{
		
		Ellipse world = new Ellipse();
		world.parseArgs(args);
		world.render();
		
	}
	
}
