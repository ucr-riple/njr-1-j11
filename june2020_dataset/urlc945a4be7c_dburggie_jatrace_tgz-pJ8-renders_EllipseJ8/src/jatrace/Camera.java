package jatrace;


/** This class encapsulates the data required to provide a viewing window to 
 *  the user. */
public class Camera
{
	
	//defaults
	private static final int defaultPPU = 100;
	private static final double defaultWidth = 1.0;
	private static final double defaultHeight = 1.0;
	private static final Vector defaultPosition = new Vector(0.0,1.0,10.0);
	private static final Vector defaultFocus = new Vector(0.0,0.0,0.0);
	private static final Vector defaultOrientation = new Vector(0.0,1.0,0.0);
	
	protected Vector position, focus, forward, up, right, corner, xstep, ystep;
	protected int ppu;
	protected double width, height, d;
	protected boolean delta, initLock;
	
	/** Default Constructor */
	public Camera()
	{
		this(defaultPosition,
			defaultFocus,
			defaultOrientation,
			defaultWidth,
			defaultHeight,
			defaultPPU);
		/*
		initLock = true;
		this.setPosition(defaultPosition);
		this.setFocus(defaultFocus);
		this.setOrientation(defaultOrientation);
		this.setWindow(defaultWidth, defaultHeight);
		this.setPPU(defaultPPU);
		delta = false;
		initLock = false;
		*/
	}
	
	/** Here origin is a vector representing the position of the simulated 
	*  camera, and focus refers to the center point of the focal window. The 
	*  w and h parameters refer to the width and height of the focal window 
	*  respectively. Note that the camera orientation will be vertical, or, if
	*  that is impossible, horizontal have "up" be the positive x direction. */
	public Camera(Vector origin, Vector focus, double w, double h)
	{
		this(origin,focus,defaultOrientation,w,h,defaultPPU);
		/*
		initLock = true;
		this.setPosition(origin);
		this.setFocus(focus);
		this.setOrientation(defaultOrientation);
		this.setWindow(w,h);
		this.setPPU(defaultPPU);
		delta = false;
		initLock = false;
		*/
	}
	
	
	/** Note that ppu contains the number of desired pixels per unit within 
	 *  your frame. Actual resolution is a simple multiplication of (width by
	 *  ppu) by (height by ppu). */
	public Camera(Vector origin, Vector focus, Vector up,
		double width, double height, int ppu)
	{
		initLock = true;
		this.setPosition(origin);
		this.setFocus(focus);
		this.setOrientation(up);
		this.setWindow(width,height);
		this.setPPU(ppu);
		delta = false;
		initLock = false;
	}
	
	/** Creates a Camera object that is an exact copy of the calling object. */
	public Camera dup()
	{
		Camera c = new Camera(position,focus,up,width,height,ppu);
		if (delta) c.setDelta(d);
		return c;
	}
	
	/** Resets window and resolution if position or orientation has changed. */
	protected Camera reset()
	{
		initLock = true;
		this.setPosition(position);
		this.setFocus(focus);
		this.setOrientation(up);
		this.setWindow(width,height);
		this.setPPU(ppu);
		initLock = false;
		return this;
	}
	
	/** Resets the amount of camera position delta to zero. */
	public Camera resetDelta()
	{
		delta = false;
		return this;
	}
	
	/** Sets the camera position delta to specified amount. */
	public Camera setDelta(double D)
	{
		//reset if close enough to zero
		if (D < 0.0000001)
		{
			return this.resetDelta();
		}
		
		delta = true;
		d = D;
		return this;
	}
	
	/** Sets camera orientation by specifying what direction you want "up" to
	 *  be. If orientation and camera direction are close to parallel then the
	 *  orientatin becomes ambiguous, so a default orientation is chosen. The
	 *  defaults are <0,1,0> and <1,0,0> in that order. */
	public Camera setOrientation(Vector newUpVector)
	{
		Vector u = newUpVector.dup();
		
		// make sure our forward and up aren't parallel
		// or else Bad Stuff (tm)
		right = forward.cross(u);
		if (right.len() < 0.00001)
		{
			// try to use +y as orientation
			u.setxyz(0.0,1.0,0.0);
			right = forward.cross(u);
			
			if (right.len() < 0.00001)
			{
				//use +x as orientation
				u.setxyz(1.0,0.0,0.0);
				right = forward.cross(u);
			}
		}
		
		right.norm();
		up = right.cross(forward).norm();
		return this;
	}
	
	
	/** Sets camera Pixels Per Unit, a measure of pixel density. Actual image
	 *  resolution depends on the size of the focal window. */
	public Camera setPPU(int p)
	{
		ppu = p;
		xstep = right.dup().scale(1.0 / p);
		ystep = up.dup().scale(-1.0 / p);
		return this;
	}
	
	/** Gets current gixels per unit setting. */
	public int getPPU() { return ppu; }
	
	/** Gets current focal window width setting. */
	public double getWidth() { return width; }
	
	/** Gets current focal window height setting */
	public double getHeight() {return height; }
	
	/** Gets how many pixels wide the image will be with current settings. */
	public int getPixelsWide() { return (int) (ppu * width); }
	
	/** Gets how many pixels tall the image will be with current settings. */
	public int getPixelsTall() { return (int) (ppu * height); }
	
	/** Sets focal window width and height. */
	public Camera setWindow(double w, double h)
	{
		width = w; height = h;
		corner = focus.dup().trans(up, h / 2.0).trans(right, w / -2.0);
		return this;
	}
	
	/** Sets camera focal point. Use of this function changes which direction
	 *  the camera is pointing. The focal window will be centered on this point
	 *  and be perpendicular to the vector from the camera position to the
	 *  focal point. */
	public Camera setFocus(Vector f)
	{
		forward = f.sub(position).norm();
		focus = f.dup();
		if (!initLock) return this.reset();
		else return this;
	}
	
	
	/** Sets camera position. Use of this function will not change the focal
	 *  point, but will change the perspective upon it. */
	public Camera setPosition(Vector p)
	{
		position = p.dup();
		if (!initLock)
		{
			this.setFocus(focus);
			this.reset();
		}
		return this;
	}
	
	/** This funtion returns a ray from the camera position (plus a origin
	 *  delta if one is set) in the direction of a random point within the
	 *  specified pixel. Values outside of the focal window are completely
	 *  valid parameters. */
	public Ray getRay(int x, int y)
	{
		Vector or, di;
		double dx = x + Math.random();
		double dy = y + Math.random();
		
		or = position.dup();
		if (delta) or.delta(d);
		
		di = corner.dup().trans(xstep,dx).trans(ystep,dy).sub(or);
		
		return new Ray(or,di);
	}
}
