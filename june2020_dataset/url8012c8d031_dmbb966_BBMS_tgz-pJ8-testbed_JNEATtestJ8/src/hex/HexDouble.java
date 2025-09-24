package hex;

// Hex object
// Two-dimensional coordinate system with floating points (X, Y)
// Does not represent a particular type of 2D hex coordinate since this is only used for interim calculations
// See http://www.redblobgames.com/grids/hexagons/ for more information
public class HexDouble {
	
	// Coordinate of the hex object
	double x;
	double y;		
	
	public HexDouble(double initialX, double initialY) {
		x = initialX;
		y = initialY;		
	}
	
	// Functions used by HexAt calculations in their respective Hex packages
	public double CalcHexAtX() {
		return (1.0/3.0) * Math.sqrt(3) * x - (1.0/3.0 * y);
	}
	
	public double CalcHexAtY() {
		return (2.0/3.0) * y;
	}
	
	
	
	// ---------------------- Display functions ------------------------//
	
	/**
	 * Displays this floating point in an (x, y) format.
	 * Includes newline at the end.
	 */
	public void DisplayHex() {
		System.out.print( "(" + x + ", " + y + ")\n" );
	}
	
	public void DisplayType() {
		System.out.print("Floating point hex\n");
	}
}
