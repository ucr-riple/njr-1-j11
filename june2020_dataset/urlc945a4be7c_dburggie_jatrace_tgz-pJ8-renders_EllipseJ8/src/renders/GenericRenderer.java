package renders;

import jatrace.*;
import jatrace.skies.*;

public class GenericRenderer extends World
{
	
	private int ppu, passes;
	public final void setPPU(int ppu) { this.ppu = ppu; }
	public final void setPasses(int passes) { this.passes = passes; }
	
	private double delta;
	public final void setDelta(double d)
	{
		if (d < 0.000001)
		{
			delta = 0.0;
		}
		
		else
		{
			delta = d;
		}
	}
	
	public void parseArgs(String [] args)
	{
		
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
		
	}
	
	Vector camP, camF, camUp;
	double winW, winH;
	int pixW, pixH;
	String filename;
	
	public final void setFilenamePrefix(String prefix)
	{
		filename = prefix;
	}
	
	public final void setCamPosition(Vector position)
	{
		camP = position.dup();
	}
	
	public final void setCamFocus(Vector focalPoint)
	{
		camF = focalPoint.dup();
	}
	
	public final void setCamUp(Vector up)
	{
		camUp = up.dup();
	}
	
	public final void setWindow(double width, double height)
	{
		winW = width; winH = height;
	}
	
	private void updatePixelCount()
	{
		pixW = (int) (winW * ppu);
		pixH = (int) (winH * ppu);
	}
	
	public void render()
	{
		
		updatePixelCount();
		
		Camera camera = new Camera(camP,camF,camUp,winW,winH,ppu);
		camera.setDelta(delta);
		
		Tracer tracer = new Tracer(this,camera);
		
		tracer.draw(passes).write(filename + "-" + pixW + "x" + pixH + ".png");
		
	}
	
	public final void setDefaults()
	{
		
		setSky(new Bluesky( new Vector(1.0,1.0,1.0) ) );
		
		setCamUp( new Vector(0.0,1.0,0.0) );
		setCamFocus(new Vector()); //zero vector
		setCamPosition(new Vector(10.0,1.0,0.0));
		setWindow(4.0,4.0);
		setFilenamePrefix("generic");
		ppu = 100;
		delta = 0.0;
		passes = 8;
	}
	
	public GenericRenderer()
	{
		
		setDefaults();
		
	}
	
	
}
