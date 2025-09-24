package DeviceGraphicsDisplay;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import Networking.Request;
import Utils.Constants;
import Utils.Location;
import factory.PartType;

/**
 * This class handles drawing of the bin.
 * @author Chris Gebert and Harry Trieu
 */
public class BinGraphicsDisplay extends DeviceGraphicsDisplay {
	// current location of the bin
	Location binLocation;
	// type of part in the bin
	PartType partType;
	// full status of the bin
	boolean isFull;
	
	/**
	 * Constructor
	 * @param loc location of bin
	 * @param pt type of part in the bin
	 */
	public BinGraphicsDisplay (Location loc, PartType pt) {
		binLocation = loc;
		partType = pt;
	}
	
	/**
	 * Sets the location of the bin
	 * @param newLoc the new location of the bin
	 */
	public void setLocation (Location newLoc) {
		binLocation = new Location(newLoc.getX(), newLoc.getY());
	}
	
	/**
	 * @returns the location of the bin
	 */
	public Location getLocation () {
		return binLocation;
	}
	
	/**
	 * @returns the type of part in the bin
	 */
	public PartType getPartType() {
		return partType;
	}
	
	public void setPartType(PartType pt) {
		partType = pt;
	}
	
	/**
	 * Handles drawing of the bin components
	 */

	public void draw(JComponent c, Graphics2D g) {
		drawWithOffset(c, g, 0);
	}
	
	public void drawWithOffset(JComponent c, Graphics2D g, int offset) {
	//	if (isFull)
			g.drawImage(partType.getBinImage(), binLocation.getX() + offset, binLocation.getY(), c);
	//	else
	//		g.drawImage(Constants.BIN_EMPTY_IMAGE, binLocation.getX() + offset, binLocation.getY(), c);
	}
	
	/**
	 * @param f new full status of the bin
	 */
	public void setFull (boolean f) {
		isFull = f;
	}

	/**
	 * Inherited method
	 */
	public void receiveData(Request req) {
		// unused for bin
	}
}
