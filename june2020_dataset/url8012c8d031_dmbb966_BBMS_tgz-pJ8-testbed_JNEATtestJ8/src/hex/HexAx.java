package hex;

// Hex object
// Axial coordinate system (X, Y)
// See http://www.redblobgames.com/grids/hexagons/ for more information
public class HexAx {
	
	// Coordinate of the hex object
	int x;
	int y;	
	
	public HexAx(int initialX, int initialY) {
		x = initialX;
		y = initialY;
	}
	
	// -----------------------Distance calculations --------------------------//
	/**
	 * Takes two axial hex coordinates and returns the distance.
	 */
	public static int DistAx(HexAx origin, HexAx target) {
		int origin_z = -origin.x - origin.y;
		int target_z = -target.x - target.y;
		
		return (Math.abs(origin.x - target.x) + 
				Math.abs(origin.y - target.y) + 
				Math.abs(origin_z - target_z)) / 2;
	}
	
	/**
	 * Calculates the distance between this axial hex and another axial hex
	 */
	public int DistFrom(HexAx target) {
		return HexAx.DistAx(this, target);		
	}
	
	
	// -----------------------Azimuth calculations --------------------------//
	
	/**
	 * Calculates the normalized azimuth in degrees between two axial coordinates
	 */
	public static double AzimuthAx(HexAx origin, HexAx target) {
		double result;
		int deltaX = target.x - origin.x;
		int deltaY = target.y - origin.y;
		
		// Converts first to an absolute x/y coordinate system
		// Algorithm from www.redblobgames.com/grids/hexagons
		
		double x = Math.sqrt(3) * (deltaX + (deltaY / 2));
		double y = 1.5 * deltaY;
		
		result = Math.atan2(x, -y) * (180 / Math.PI);
		if (result < 0) result += 360;
		
		return result;
	}
	
	/**
	 * Calculates the normalized azimuth in degrees between this axial hex and another axial hex. 
	 */
	public double AzimuthTo(HexAx target) {
		return HexAx.AzimuthAx(this, target);
	}
	
	// -----------------------Rounding calculations --------------------------//
	
	/**
	 * Given a floating point cube coordinate (not an object but component coordinates), 
	 * rounds each component to the nearest integer and makes adjustments to ensure that x + y + z = 0
	 * Converts the result into an axial coordinate.
	 * Algorithm from www.redblobgames.com/grids/hexagons
	 */
	
	public static HexAx RoundAx (double x, double y, double z)
	{
		return HexCube.RoundCube(x, y, z).ConvertToAx();		
	}
	
	
	// ---------Conversions between floating point and integer coords-----------------//
	
	/**
	 * Takes an axial hex coordinate and returns the result as a 2D floating point number
	 */
	public static HexDouble HexIs (HexAx in)
	{
		return new HexDouble(Math.sqrt(3) * (in.x + (in.y / 2)), 1.5 * in.y);		
	}
	
	public HexDouble ThisHexIs()
	{
		return HexAx.HexIs(this);
	}
	
	/**
	 * Takes a 2D floating point number (HexDouble class) and converts it to an Axial Hex coordinate
	 */
	public static HexAx HexAt (HexDouble in)
	{
		double approxX = in.CalcHexAtX();
		double approxZ = in.CalcHexAtY();
		double approxY = -approxX - approxZ;
		
		return RoundAx(approxX, approxY, approxZ);
	}
	
	
	// -----------------------Hexes between --------------------------//
	
	public static void HexesBetween (HexAx origin, HexAx target)
	{
		int distance = HexAx.DistAx(origin, target);
		
		HexDouble originD = HexAx.HexIs(origin);
		HexDouble targetD = HexAx.HexIs(target);
		double dx = targetD.x - originD.x;
		double dy = targetD.y - originD.y;
		
		// Interpolates at (distance + 1) points along the line
		for (int i = 0; i <= distance; i++)
		{
			double progress = (double)i / (double)distance;
			HexDouble resultD = new HexDouble(originD.x + dx * progress,
											  originD.y + dy * progress);
			HexAx result = HexAx.HexAt(resultD);		
			
			System.out.print("Interpolation " + i + " of " + distance + " is coordinate: ");
			result.DisplayHex();
			System.out.print("     converted to offset: ");
			
			HexOff translated = result.ConvertToOff();
			translated.DisplayHex();
		}
	}
	
	// Hexcasting
	/**
	 * Returns the hex at a certain distance and azimuth from the origin hex
	 */
	public static void HexCast (HexAx origin, double azimuth, int range)
	{
		// Convert into 2D coordinates
		HexDouble originD = HexAx.HexIs(origin);
		double dx = range * Math.sin(Math.toRadians(azimuth)) * Math.sqrt(3);
		double dy = range * Math.cos(Math.toRadians(azimuth)) * -Math.sqrt(3);	// Inverted due to Y-axis increasing as you go south.
		
		HexDouble targetD = new HexDouble(originD.x + dx, originD.y + dy);
		targetD.DisplayHex();
		
		// Converts target hex into the desired coordinate type
		HexAx target = HexAx.HexAt(targetD);
		
		target.DisplayHex();
	}
	
	// -----------------------Conversion to other hex formats --------------------------//
	
	/**
	 * Converts this Axial Hex object into a Cubic Hex object
	 */
	public HexCube ConvertToCube() {				
		return new HexCube(x, -x - y, y);
	}
	
	/**
	 * Converts this Axial Hex object into an odd-r Offset Hex object
	 */
	public HexOff ConvertToOff() {
		// int z = -x - y;
		int z = y;
		int newX = x + (y - (y & 1)) / 2;
		int newY = z;
		
		return new HexOff(newX, newY);
	}
	
	
	// ---------------------- Display functions ------------------------//
	
	/**
	 * Displays this Axial Hex in an (x, y) format.
	 * Includes newline at the end.
	 */
	public void DisplayHex() {
		System.out.print( "(" + x + ", " + y + ")\n" );
	}
	
	/**
	 * Returns a string with the x, y coordinates of this offset hex.
	 * Does not include a newline.
	 */
	public String DisplayHexStr() {
		return "(" + x + ", " + y + ")";
	}
	
	public void DisplayType() {
		System.out.print("Axial hex\n");
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
