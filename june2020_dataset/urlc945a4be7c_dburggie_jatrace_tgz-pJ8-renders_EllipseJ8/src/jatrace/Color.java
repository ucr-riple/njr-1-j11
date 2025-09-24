package jatrace;

/** This class provides many useful methods for dealing with colors while
 *  providing a high degree of color fidelity. */
public class Color
{
	protected double red, green, blue, alpha;
	
	
	/** Default constructor is true black. */
	public Color()
	{
		red = 0.0; green = 0.0; blue = 0.0; alpha = 1.0;
	}
	
	public Color(int r, int g, int b, int a)
	{
		red = r / 256.0;
		green = g / 256.0;
		blue = b / 256.0;
		alpha = a / 256.0;
	}
	
	/** Initialize Color object to given rgb values. */
	public Color(double r, double g, double b)
	{
		red = r; green = g; blue = b; alpha = 1.0;
	}
	
	
	/** I really recommend against using an alpha channel. Alpha support is
	 *  unimplemented as yet with the rest of this package. */
	public Color(double r, double g, double b, double a)
	{
		red = r; green = g; blue = b; alpha = a;
	}
	
	/** Returns new Color instance that exactly copies the caller. */
	public Color dup()
	{
		return new Color(red, green, blue, alpha);
	}
	
	/** Copies the rgba values from the parameter into the caller. */
	public Color copy(Color c)
	{
		red = c.red; green = c.green; blue = c.blue; alpha = c.alpha;
		return this;
	}
	
	/** Adds the rgba values of the parameter to the caller. Be carefull with
	 *  this, values less than 0.0 or greater than 1.0 can cause issues. I
	 *  recommend calling the dim() method to account for this. */
	public Color add(Color c)
	{
		red += c.red; green += c.green; blue += c.blue; alpha += c.alpha;
		return this;
	}
	
	/** Returns an integer array so the rgba values will fit into bytes.  */
	public int [] p()
	{
		int [] i = new int [4];
		i[0] = Math.max(0, Math.min(255, (int) (256 * red  ) ) );
		i[1] = Math.max(0, Math.min(255, (int) (256 * green) ) );
		i[2] = Math.max(0, Math.min(255, (int) (256 * blue ) ) );
		i[3] = Math.max(0, Math.min(255, (int) (256 * alpha) ) );
		//i[3] = 255;
		return i;
	}
	
	public String toString()
	{
		int [] smp = p();
		return "" + smp[0] + ":" + smp[1] + ":" + smp[2] + ":" + smp[3];
	}
	
	public boolean equals(Color c)
	{
		int [] a = p();
		int [] b = c.p();
		
		for (int i = 0; i < 4; i++)
		{
			if (a[i]  != b[i])
			{
				return false;
			}
		}
		
		return true;
	}
	
	/** Sets rgb values to desired parameters. Note that values below 0.0 and
	 *  above 1.0, though technically safe, could be a source of bugs. These
	 *  values would correspond to over and under saturated colors. */
	public Color setRGB( double r, double g, double b )
	{
		red = r; green = g; blue = b;
		return this;
	}
	
	/** See notice for setRGB method. */
	public Color setRGBA( double r, double g, double b, double a)
	{
		red = r; green = g; blue = b; alpha = a;
		return this;
	}
	
	public Color setRGBA( int r, int g, int b, int a)
	{
		red = r / 256.0;
		green = g / 256.0;
		blue = b / 256.0;
		alpha = a / 256.0;
		return this;
	}
	
	/** See notice for setRGB method. */
	public Color setalpha( double a )
	{
		alpha = a; return this;
	}
	
	/** Dims the color by the specified scalar. Negative values and values
	 *  greater than 1.0 will probably cause Weird Things (tm), but are
	 *  supported for edge cases. Be cautious. */
	public Color dim(double s)
	{
		red *= s; green *= s; blue *= s; return this;
	}
	
	/** If given sample is subminimally or supermaximally saturated, returns
	 *  the true minimum or maximum sample values. */
	private static double makeSafe(double sample)
	{
		if (sample > 1.0) { return 1.0; }
		if (sample < 0.0) { return 0.0; }
		return sample;
	}
	
	/** Raises sample values to the specified exponent. Useful in brightening
	 *  (values less than one) and darkening (values greater than one) images
	 *  in a fairly realistic manner. Negative values have an unknown - if 
	 *  any - utility. Note: calling this function will reset sample values
	 *  outside of 0.0 to 1.0 to true black or true white respectively. */
	public Color gamma(double e)
	{
		red = Math.pow( makeSafe(red), e );
		green = Math.pow( makeSafe(green), e );
		blue= Math.pow( makeSafe(blue),e );
		return this;
	}
	
	public double getRed()   { return red;   }
	public double getGreen() { return green; }
	public double getBlue()  { return blue;  }
	
	// ########## Below are all ARGB color model methods ##########
	
	public int toARGB()
	{
		int [] samples = p();
		int argb = 0x00;
		argb |= (0x0ff & samples[2]);
		argb |= (0x0ff & samples[1]) << 8;
		argb |= (0x0ff & samples[0]) << 16;
		argb |= (0x0ff & samples[3]) << 24;
		return argb;
	}
	
	public Color copyARGB(int argb)
	{
		int r,g,b,a;
		b = (0x0ff & ( argb >>>  0 ));
		g = (0x0ff & ( argb >>>  8 ));
		r = (0x0ff & ( argb >>> 16 ));
		a = (0x0ff & ( argb >>> 24 ));
		setRGBA(r,g,b,a);
		return this;
	}
	
	public static Color fromARGB(int argb)
	{
		int r,g,b,a;
		b = (0x0ff & ( argb >>>  0 ));
		g = (0x0ff & ( argb >>>  8 ));
		r = (0x0ff & ( argb >>> 16 ));
		a = (0x0ff & ( argb >>> 24 ));
		return new Color(r,g,b,a);
	}
}
