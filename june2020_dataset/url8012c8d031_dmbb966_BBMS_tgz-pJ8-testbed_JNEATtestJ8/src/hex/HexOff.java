package hex;

import java.awt.Color;
import java.util.Vector;

import bbms.GlobalFuncs;
import unit.Unit;

// Hex object
// Offset coordinate system (X, Y) using the "odd-r" horizontal layout
// See http://www.redblobgames.com/grids/hexagons/ for more information
public class HexOff {
	
	// Coordinate of the hex object
	int x;
	int y;
	
	
	
	
	public HexOff(int initialX, int initialY) {
		x = initialX;
		y = initialY;
	}
	
	// -----------------------Distance calculations --------------------------//
	/**
	 * Takes two offset hex coordinates and returns the distance.
	 * It does this by converting them to Axial coordinates and calculating the distance that way.
	 */
	public static int DistOff(HexOff origin, HexOff target) {
		HexAx axOrigin = origin.ConvertToAx();
		HexAx axTarget = target.ConvertToAx();	
		
		return axOrigin.DistFrom(axTarget);
	}
	
	/**
	 * Calculates the distance between this offset hex and another offset hex
	 */
	public int DistFrom(HexOff target) {
		return HexOff.DistOff(this, target);		
	}
		
	// -----------------------Azimuth calculations --------------------------//
	
	/**
	 * Calculates the normalized azimuth in degrees between two offset coordinates
	 */
	public static double AzimuthOff(HexOff origin, HexOff target) {
		double result;
		
		// Converts first to an absolute x/y coordinate system
		// Algorithm from www.redblobgames.com/grids/hexagons

		
		double x1 = Math.sqrt(3) * (origin.x + 0.5 * (origin.y & 1));
		double y1 = 1.5 * origin.y;
		
		double x2 = Math.sqrt(3) * (target.x + 0.5 * (target.y & 1));
		double y2 = 1.5 * target.y;
		
		double deltaX = x2 - x1;
		double deltaY = y2 - y1;
				
		result = Math.atan2(deltaX, -deltaY) * (180 / Math.PI);
		if (result < 0) result += 360;
		
		return result;
	}
	
	/**
	 * Calculates the normalized azimuth in degrees between this offset hex and another offset hex. 
	 */
	public double AzimuthTo(HexOff target) {
		return HexOff.AzimuthOff(this, target);
	}
	
	// -----------------------Rounding calculations --------------------------//
	
	/**
	 * Given a floating point cube coordinate (not an object but component coordinates), 
	 * rounds each component to the nearest integer and makes adjustments to ensure that x + y + z = 0
	 * Converts the result into an offset coordinate.
	 * Algorithm from www.redblobgames.com/grids/hexagons
	 */
	
	public static HexOff RoundOff (double x, double y, double z)
	{
		return HexCube.RoundCube(x, y, z).ConvertToOff();		
	}
	
	
	// ---------Conversions between floating point and integer coords-----------------//
	
	/**
	 * Takes an offset hex coordinate and returns the result as a 2D floating point number
	 */
	public static HexDouble HexIs (HexOff in)
	{
		return new HexDouble(Math.sqrt(3) * (in.x + 0.5 * (in.y & 1)), 1.5 * in.y);		
	}
	
	public HexDouble ThisHexIs()
	{
		return HexOff.HexIs(this);
	}
	
	/**
	 * Takes a 2D floating point number (HexDouble class) and converts it to an Offset Hex coordinate
	 */
	public static HexOff HexAt (HexDouble in)
	{
		double approxX = in.CalcHexAtX();
		double approxZ = in.CalcHexAtY();
		double approxY = -approxX - approxZ;
		
		return RoundOff(approxX, approxY, approxZ);
	}
	
	
	
	// -----------------------Conversion to other hex formats --------------------------//
	
	/**
	 * Converts this odd-r Offset Hex object into an Axial Hex object
	 */
	public HexAx ConvertToAx() {
		int newX = x - (y - (y & 1)) / 2;
		
		return new HexAx(newX, y);
	}
	
	/**
	 * Converts this odd-r Offset Hex object into a Cubic Hex object
	 */
	public HexCube ConvertToCube() {
		int newX = x - (y - (y & 1)) / 2;
		
		return new HexCube(newX, -newX - y, y);
	}
	
	
	// -----------------------Hexes between --------------------------//
	
	public static Vector<hex.Hex> HexesBetween (HexOff origin, HexOff target)
	{
		int distance = HexOff.DistOff(origin, target);
		Vector<hex.Hex> hexList = new Vector<Hex>();
		
		HexDouble originD = HexOff.HexIs(origin);
		HexDouble targetD = HexOff.HexIs(target);
		double dx = targetD.x - originD.x;
		double dy = targetD.y - originD.y;
		
		// Interpolates at (distance + 1) points along the line
		for (int i = 0; i <= distance; i++)
		{
			double progress = (double)i / (double)distance;
			HexDouble resultD = new HexDouble(originD.x + dx * progress,
											  originD.y + dy * progress);
			HexOff result = HexOff.HexAt(resultD);
			
			hexList.add(GlobalFuncs.scenMap.getHex(result.x, result.y));
			// bbms.GUI_NB.GCO("Adding hex " + result.x + ", " + result.y + " to hex list");
			// System.out.print("Interpolation " + i + " of " + distance + " is coordinate: ");
			// result.DisplayHex();
		}
		
		return hexList;
	}
	
	public static Vector<hex.Hex> HexRing (int x, int y, int distance) {
		Vector<hex.Hex> hexRing = new Vector<Hex>();
		HexOff origin = new HexOff(x, y);
		// Find starting hex
		HexOff target = HexCast(origin, 270.0, distance);
		Hex finger = GlobalFuncs.scenMap.getHex(target.x, target.y);
		
		if (distance == 0) {
			hexRing.addElement(GlobalFuncs.scenMap.getHex(x, y));
		}
		else {
			for (int direction = 0; direction < 6; direction ++) {
				for (int walk = 0; walk < distance; walk ++) {
					hexRing.addElement(finger);
					target = target.findNeighbor(direction);
					finger = GlobalFuncs.scenMap.getHex(target.x, target.y);
					// GlobalFuncs.scenMap.shadeHex(finger, Color.WHITE);								
					// bbms.GUI_NB.GCO("Add at " + target.DisplayHexStr());
				}
			}
		}
		
		
		
		// GlobalFuncs.gui.repaint();
		
		return hexRing;
	}
	
	// Hexcasting
	/**
	 * Returns the hex at a certain distance and azimuth from the origin hex
	 */
	public static HexOff HexCast (HexOff origin, double azimuth, int range)
	{
		// Convert into 2D coordinates
		HexDouble originD = HexOff.HexIs(origin);
		double dx = range * Math.sin(Math.toRadians(azimuth)) * Math.sqrt(3);
		double dy = range * Math.cos(Math.toRadians(azimuth)) * -Math.sqrt(3);	// Inverted due to Y-axis increasing as you go south.
		
		HexDouble targetD = new HexDouble(originD.x + dx, originD.y + dy);
		// targetD.DisplayHex();
		
		// Converts target hex into the desired coordinate type
		HexOff target = HexOff.HexAt(targetD);
		
		// target.DisplayHex();
		
		return target;
	}
	
	// ---------------------- Neighboring hexes ------------------------//		
	public static int[][][] neighbors = {{{0, -1}, {1, 0}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}},
									{{1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 0}, {0, -1}}};
	
	/**
	 * Given an origin hex and direction, returns the odd-r offset hex object to the appropriate neighbor
	 * Direction 0 is 30 degrees (NE), direction 1 is 90 degrees (E), etc. in a clockwise pattern
	 */
	public static HexOff NeighborOff (HexOff origin, int direction) {
		int parity = origin.y & 1;
		return new HexOff(origin.x + neighbors[parity][direction][0], origin.y + neighbors[parity][direction][1]);		
	}
	
	public HexOff findNeighbor (int direction) {
		return NeighborOff(this, direction);
	}
	
	public HexOff neighborW() {
		return new HexOff(x - 1, y);
	}
	
	public HexOff neighborE() {
		return new HexOff(x + 1, y);	
	}
	
	public HexOff neighborNW() {
		int parity = y & 1;
		if (parity == 1) return new HexOff(x, y-1);
		else return new HexOff(x-1, y-1);		
	}
	
	public HexOff neighborNE() {
		int parity = y & 1;
		if (parity == 1) return new HexOff(x + 1, y - 1);
		else return new HexOff(x, y-1);
	}
	
	public HexOff neighborSW() {
		int parity = y & 1;
		if (parity == 1) return new HexOff(x, y+1);
		else return new HexOff(x-1, y+1);
	}
	
	public HexOff neighborSE() {
		int parity = y & 1;
		if (parity == 1) return new HexOff(x+1, y+1);
		else return new HexOff(x, y+1);
	}

	
	
	// ---------------------- Display functions ------------------------//
	
	/**
	 * Displays this Offset Hex in an (x, y) format.
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
		System.out.print("Offset hex\n");
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
