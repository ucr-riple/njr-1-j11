package hex;

// Hex object
// Cubic coordinate system (X, Y, Z)
// See http://www.redblobgames.com/grids/hexagons/ for more information
public class HexCube {
	
	// Coordinate of the hex object
	int x;
	int y;	
	int z;
	
	public HexCube(int initialX, int initialY, int initialZ) {
		x = initialX;
		y = initialY;
		z = initialZ;
	}
	
	
	// -----------------------Distance calculations --------------------------//
	/**
	 * Takes two cubic hex coordinates and returns the distance.
	 */
	public static int DistCube(HexCube origin, HexCube target) {
		return (Math.abs(origin.x - target.x) + 
				Math.abs(origin.y - target.y) + 
				Math.abs(origin.z - target.z)) / 2;
	}
	
	/**
	 * Calculates the distance between this axial hex and another axial hex
	 */
	public int DistFrom(HexCube target) {
		return HexCube.DistCube(this, target);		
	}
	
	
	// -----------------------Azimuth calculations --------------------------//
	
	/**
	 * Calculates the normalized azimuth in degrees between two cubic coordinates
	 */
	public static double AzimuthCube(HexCube origin, HexCube target) {		
		int deltaX = target.x - origin.x;
		int deltaY = target.z - origin.z;
		
		// Converts first to an absolute x/y coordinate system
		// Algorithm from www.redblobgames.com/grids/hexagons
		
		double x = Math.sqrt(3) * (deltaX + (deltaY / 2));
		double y = 1.5 * deltaY;
		
		double result = Math.atan2(x, -y) * (180 / Math.PI);
		if (result < 0) result += 360;
		
		return result;
	}
	
	/**
	 * Calculates the normalized azimuth in degrees between this offset hex and another offset hex. 
	 */
	public double AzimuthTo(HexCube target) {
		return HexCube.AzimuthCube(this, target);
	}
	
	// -----------------------Rounding calculations --------------------------//
	
	/**
	 * Given a cube coordinate, rounds each component to the nearest integer and makes adjustments
	 * to ensure that x + y + z = 0
	 * Algorithm from www.redblobgames.com/grids/hexagons
	 */
	
	public static HexCube RoundCube (double x, double y, double z)
	{
		HexCube result = new HexCube(Math.round((float)x), 
									 Math.round((float)y), 
									 Math.round((float)z));
		
		float dx = Math.abs(result.x - (float)x);
		float dy = Math.abs(result.y - (float)y);
		float dz = Math.abs(result.z - (float)z);
		
		if (dx > dy && dx > dz) result.x = -result.y - result.z;
		else if (dy > dz) result.y = -result.x - result.z;
		else result.z = -result.x - result.y;		
		
		return result;		
	}
	
	
	// ---------Conversions between floating point and integer coords-----------------//
	
	/**
	 * Takes a cube hex coordinate and returns the result as a 2D floating point number
	 */
	public static HexDouble HexIs (HexCube in)
	{
		HexAx translate = new HexAx(in.x, in.z);
		return HexAx.HexIs(translate);		
	}
	
	public HexDouble ThisHexIs()
	{
		return HexCube.HexIs(this);
	}
	
	/**
	 * Takes a 2D floating point number (HexDouble class) and converts it to a Cubic Hex coordinate
	 */
	public static HexCube HexAt (HexDouble in)
	{
		double approxX = in.CalcHexAtX();
		double approxZ = in.CalcHexAtY();
		double approxY = -approxX - approxZ;
		
		return RoundCube(approxX, approxY, approxZ);
	}
	
	
	
	
	// -----------------------Conversion to other hex formats --------------------------//
	
	/**
	 * Converts this Cubic Hex object into an Axial Hex object
	 */
	public HexAx ConvertToAx() {				
		return new HexAx(x, z);
	}
	
	/**
	 * Converts this Cubic Hex object into an odd-r Offset Hex object
	 */
	public HexOff ConvertToOff() {
		int newX = x + (z - (z & 1)) / 2;
		
		return new HexOff(newX, z);
	}
	
	// -----------------------Hexes between --------------------------//
	
	public static void HexesBetween (HexCube origin, HexCube target)
	{
		int distance = HexCube.DistCube(origin, target);
		
		HexDouble originD = HexCube.HexIs(origin);
		HexDouble targetD = HexCube.HexIs(target);
		double dx = targetD.x - originD.x;
		double dy = targetD.y - originD.y;
		
		// Interpolates at (distance + 1) points along the line
		for (int i = 0; i <= distance; i++)
		{
			double progress = (double)i / (double)distance;
			HexDouble resultD = new HexDouble(originD.x + dx * progress,
											  originD.y + dy * progress);
			HexCube result = HexCube.HexAt(resultD);		
			
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
	public static void HexCast (HexCube origin, double azimuth, int range)
	{
		// Convert into 2D coordinates
		HexDouble originD = HexCube.HexIs(origin);
		double dx = range * Math.sin(Math.toRadians(azimuth)) * Math.sqrt(3);
		double dy = range * Math.cos(Math.toRadians(azimuth)) * -Math.sqrt(3);	// Inverted due to Y-axis increasing as you go south.
		
		HexDouble targetD = new HexDouble(originD.x + dx, originD.y + dy);
		targetD.DisplayHex();
		
		// Converts target hex into the desired coordinate type
		HexCube target = HexCube.HexAt(targetD);
		
		target.DisplayHex();
	}
	
	
	// ---------------------- Display functions ------------------------//
	
	/**
	 * Displays this Cubic Hex in an (x, y, z) format.
	 * Includes newline at the end.
	 */
	public void DisplayHex() {
		System.out.print( "(" + x + ", " + y + ", " + z + ")\n" );
	}
	
	public void DisplayType() {
		System.out.print("Cubic hex\n");
	}
}
