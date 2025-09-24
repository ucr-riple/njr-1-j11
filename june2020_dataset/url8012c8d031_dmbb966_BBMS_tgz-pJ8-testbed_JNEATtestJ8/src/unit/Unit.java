package unit;

import gui.GUI_NB;
import hex.Hex;
import hex.HexAx;
import hex.HexMap;
import hex.HexOff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Formatter;
import java.util.Locale;
import java.util.Vector;

import javax.imageio.ImageIO;

import jneat.Organism;
import clock.Clock;
import clock.ClockControl;
import clock.ClockThread;
import terrain.TerrainEnum;
import terrain.TerrainType;
import bbms.GlobalFuncs;

public class Unit {

	public hex.Hex location;
	public hex.HexOff HullOffset = new hex.HexOff(0, 0);
	public hex.HexOff TurretOffset = new hex.HexOff(0, 0);
	public hex.HexOff TurretRing = new hex.HexOff(0, 0);
	
	/** Neural net used for determining placement of the unit*/
	public Organism org = null;
	public int orgGenome = -1;				// Used only when loading units, due to the order the save file is built.
	
	/** Determines the sensor configuration of this network - affects how the sensors are fed. */
	public OrganismTypeEnum orgType = OrganismTypeEnum.SIMPLE_SINGLE;
	
	/** Determines how the fitness for the neural network is evaluated */
	public FitnessTypeEnum fitType = GlobalFuncs.defaultFitType;
	
	/** Fitness for this current iteration.  Will update NN fitness at the end of the scenario. */
	public double curFitness = -1.0;
	
	/** Number of spots this unit is credited with over the course of the scenario.  Criteria are that the enemy unit
	 * must be spotted by this unit while the enemy unit is in the "Recon Zone" of the map.  Partial credit may
	 * be awarded depending on which fitness function is being used.  The function that updates this number is found
	 * in FitnessTypeEnum.*/
	public double spotCredits = 0.0;
	
	public boolean destroyed = false;
	public boolean emplaced = false;		// When true, teleporting will reset shared spotting
	
	public double hullOrientation;
	public double turretOrientation;	// Relative to the hull
	
	public int movePoints = 0;
	public int moveRate = 0;	// Scale of 0-100 with 100 = 100% movement speed
	public MoveClass moveMode = MoveClass.FOOT;
	
	public int subHexDirection = 1;		// Axial direction the unit is traveling in, range is 1-6
	public int subHexLocation = 0;		// 0 is the center of the hex.  HexMap defines the maximum size.
	
	public boolean spotted = false;		// Has the unit been seen by the enemy?
		
	public WaypointList waypointList = new WaypointList();
	
	public Unit target;
	// public boolean trackTarget;
	
	// Used for determining hull and turret graphic files
	public String type;
	public String callsign;
	
	// Unique number
	public int unitID;
	
	// What side its on
	public SideEnum side;
	
	public void AddtoSide(SideEnum side) {
		this.side = side;
		
		switch (side) {
		case ENEMY:
			GlobalFuncs.enemyUnitList.addElement(this);				
			break;
		case FRIENDLY:
			GlobalFuncs.friendlyUnitList.addElement(this);
			spotted = true;
			break;
		case NEUTRAL:
			break;
		}		
	}
	
	public void RemoveUnit() {
		// if (emplaced) RemoveSharedSpotting();
		GlobalFuncs.unitList.removeElement(this);
		RemovefromSide(this.side);
		if (GlobalFuncs.selectedUnit == this) GlobalFuncs.selectedUnit = null;
		this.destroyed = true;
		GlobalFuncs.gui.repaint();
	}
	
	public void RemovefromSide(SideEnum side) {
		// Remove from current side lists
				switch (side) {
				case ENEMY:
					GlobalFuncs.enemyUnitList.remove(this);
					break;
				case FRIENDLY:
					GlobalFuncs.friendlyUnitList.remove(this);
					break;
				case NEUTRAL:
					break;
				}		
	}
	
	public void ChangeSide(SideEnum side) {
		RemovefromSide(side);
		AddtoSide(side);
		}
	
	public void RefreshUnitInfo() {
		
		if (type.matches("M1A2")) {			
			HullOffset = new hex.HexOff(27, 12);
			TurretRing = new hex.HexOff(30 - HullOffset.getX(), 12 - HullOffset.getY());
			TurretOffset = new hex.HexOff(25, 11);
			movePoints = 22;
			moveRate = 100;
			moveMode = MoveClass.TRACK;
		} else if (type.matches("T-72")) {
			HullOffset = new hex.HexOff(25, 12);
			TurretRing = new hex.HexOff(28 - HullOffset.getX(), 11 - HullOffset.getY());
			TurretOffset = new hex.HexOff(13, 10);
			movePoints = 20;
			moveRate = 100;
			moveMode = MoveClass.TRACK;			
		} else {
			//GUI_NB.GCO("ERROR: Could not identify unit type!  Type is >" + type + "<");
		}
			
	}
	
	/** Used when copying COAs */
	public Unit (Unit u) {
		location = u.location;
		side = u.side;
		type = u.type;
		target = null;
		
		spotted = false;
		
		hullOrientation = u.hullOrientation;
		turretOrientation = u.turretOrientation;
		waypointList = new WaypointList(u.waypointList);
		
		callsign = u.callsign;
		unitID = u.unitID;
		
		orgType = u.orgType;
		
		RefreshUnitInfo();
		
		// Doesn't need to worry about unit lists since on loadCOA, it automatically does this		
		
		
		
	}
	
	public Unit (hex.Hex locn, SideEnum s, String givenType, String givenCallsign, double hullOrient, double turretOrient, WaypointList wpL, boolean spot) {
		location = locn;
		side = s;
		type = givenType;
		target = null;
		// trackTarget = true;
		
		spotted = spot;
		
		hullOrientation = hullOrient;
		turretOrientation = turretOrient;
		if (wpL != null) waypointList = wpL;
		
		callsign = givenCallsign;		
		unitID = GlobalFuncs.getNewUnitCount();
		
		orgType = GlobalFuncs.defaultOrgType;
		
		RefreshUnitInfo();
			
		GlobalFuncs.unitList.addElement(this);
		switch (s) {
		case FRIENDLY:
			GlobalFuncs.friendlyUnitList.addElement(this);
			break;
		case ENEMY:
			GlobalFuncs.enemyUnitList.addElement(this);
			break;
		case NEUTRAL:
			break;
		}
	}
		
	public String DispUnitInfo() {
		return "Unit " + unitID + " is type: " + type + ", callsign " + callsign + " at location " + 
					location.x + ", " + location.y + " on side " + side + "\n with turret and hull orientation " + 
				String.format("%.2f", turretOrientation) + " and " + String.format("%.2f", hullOrientation) + 
				"\n Hull offsets: " + HullOffset.getX() + " and " + HullOffset.getY();
	}
	
	public String DispSubHexMovement() {
		return subHexDirection + " / " + subHexLocation;
	}
	
	public void DrawUnit(int xi, int yi, Graphics g) {
		if (GlobalFuncs.showFOW && !spotted) return;
		
		// Loads the appropriate hex icon
		String hullPath = "src/unit/graphics/" + type + "_H.png";
		String turretPath = "src/unit/graphics/" + type + "_T.png";
		
		// GUI_NB.GCO("Hull: " + hullPath + " || Turret: " + turretPath);
		
		File hullFile = new File(hullPath);
		File turretFile = new File(turretPath);
		try {
			Image img = ImageIO.read(hullFile);
			Graphics2D g2d = (Graphics2D)g;
			AffineTransform backup = g2d.getTransform();
			AffineTransform trans = new AffineTransform();
			trans.rotate(Math.toRadians(hullOrientation - 90), xi, yi);		// Translates cardinal degrees to that of the coord system
			g2d.transform(trans);
			g2d.drawImage(img,  xi - HullOffset.getX(),  yi - HullOffset.getY(), null);
			g2d.setTransform(backup);
			
			
			// g.drawImage(img,  xi - HullOffset.getX(),  yi - HullOffset.getY(), null);
			
			// No translation needed since turret orientation is relative to the hull direction, 0 degrees = 12 oclock
			trans.rotate(Math.toRadians(turretOrientation), xi + TurretRing.getX(), yi + TurretRing.getY());
			g2d.transform(trans);
			img = ImageIO.read(turretFile);
			g2d.drawImage(img,  xi - TurretOffset.getX() + TurretRing.getX(),  
					yi - TurretOffset.getY() + TurretRing.getY(),  null);
			
			g2d.setTransform(backup);
			
		} catch (IOException ie) {
			System.out.println(ie.getMessage());
			GUI_NB.GCO(ie.getMessage());
		}				
	}
	
	public double getAzimuth(int x1, int y1, int x2, int y2) {
		HexOff currentHex = new HexOff(x1, y1);
		HexOff targetHex = new HexOff(x2, y2);
		return HexOff.AzimuthOff(currentHex,  targetHex);
	}
	
	/**
	 * Orients the hull to the specified hex; if there is no target, the turret remains in its current position.
	 * If the unit has a target, the turret will remain on the target
	 */
	public void OrientHullTo(int x, int y) {		
		hullOrientation = getAzimuth(location.x, location.y, x, y);
		if (target != null) {			
			turretOrientation = getAzimuth(location.x, location.y, target.location.x, target.location.y) - hullOrientation;
		}
	}
	
	/**
	 * Orients the turret to the specified hex and leaves the hull in place.  Takes the turret off the selected target,
	 * if applicable.
	 */
	public void OrientTurretTo(int x, int y) {
		turretOrientation = getAzimuth(location.x, location.y, x, y) - hullOrientation;
		// trackTarget = false;
	}	
	
	/**
	 * Orients the turret to the specified target
	 */
	public void OrientTurretToTarget() {
		if (target != null) {
			OrientTurretTo(target.location.x, target.location.y);			
		}		
	}
	
	
	/** Displays the LOS between the origin and destination hexes.
	 * If "clear" is true, will clear all shaded hexes before drawing. */
	public static void DisplayLOSBetween(Hex origin, Hex destination, boolean clear) {
		HexOff currentHex = new HexOff(origin.x, origin.y);
		HexOff targetHex = new HexOff(destination.x, destination.y);
		Vector<hex.Hex> hexList = HexOff.HexesBetween(currentHex, targetHex);
		if (hexList.size() > GlobalFuncs.visibility + 1) return;
		
		if (clear) GlobalFuncs.scenMap.unshadeAll();
				
		int visibility = 0;
		// Does not count the hex the unit itself is in, hence, i starts at 1
		for (int i = 1; i < hexList.size(); i++) {
			Hex h = hexList.elementAt(i);			
			if (visibility <= 15) {
				GlobalFuncs.scenMap.shadeHex(h,  Color.GREEN);							
			} else if (visibility <= 30) {
				GlobalFuncs.scenMap.shadeHex(h, Color.YELLOW);
			} else GlobalFuncs.scenMap.shadeHex(h, Color.RED);						
			// GUI_NB.GCO("Setting hex " + h.x + ", " + h.y + " to shaded with visibility " + visibility);
			visibility += h.density;
		}
		GlobalFuncs.gui.repaint();		
	}
	
	/**
	 * Displays the LOS from the current unit to a given x, y coordinate.
	 * If "clear" is set to true, clears all shaded hexes before drawing.
	 */
	public void DisplayLOSTo(int x, int y, boolean clear) {
		DisplayLOSBetween(this.location, GlobalFuncs.scenMap.getHex(x, y), clear);
	}
	
	/** Returns whether or not there is LOS between the two hexes */
	public static boolean LOSBetween(Hex a, Hex b) {
		HexOff currentHex = a.toHO();
		HexOff targetHex = b.toHO();
		Vector<hex.Hex> hexList = HexOff.HexesBetween(currentHex, targetHex);
		
		// Exceeds maximum viewing distance
		if (hexList.size() > GlobalFuncs.visibility + 1) return false;
		
		int visibility = 0;
		// Does not count the hex the unit itself is in, hence, i starts at 1
		for (int i = 1; i < hexList.size(); i++) {
			Hex h = hexList.elementAt(i);			
			if (visibility > 30) return false;		
			if (h.tEnum == TerrainEnum.INVALID) return false;
			
			visibility += h.density;
		}
		
		return true;
	}
	
	public boolean HasLOSTo(int x, int y) {
		Hex a = this.location;
		Hex b = GlobalFuncs.scenMap.getHex(x, y);
		
		return LOSBetween(a, b);				
	}
	
	public void DisplayLOSToTarget() {
		DisplayLOSTo(target.location.x, target.location.y, true);
	}
	
	public void DisplayLOSToEnemies() {
		Vector<unit.Unit> enemyList;
		Unit selected;
		
		if (this.side == SideEnum.ENEMY) enemyList = GlobalFuncs.friendlyUnitList;
		else enemyList = GlobalFuncs.enemyUnitList;
		
		if (enemyList.size() == 0) GUI_NB.GCO("ERROR!  There are no opposing units on the map!");
		else {
			for (int i = 0; i < enemyList.size(); i++) {
				selected = enemyList.elementAt(i);
				DisplayLOSTo(selected.location.x, selected.location.y, false);
				// GUI_NB.GCO(currentTarget.DispUnitInfo());
			}
		}
	}
	
	public void FindLOSToEnemies() {
		Vector<unit.Unit> enemyList;
		Unit selected;
		
		if (this.side == SideEnum.ENEMY) enemyList = GlobalFuncs.friendlyUnitList;
		else enemyList = GlobalFuncs.enemyUnitList;
		
		
		
		if (enemyList.size() != 0) {
			for (int i = 0; i < enemyList.size(); i++) {
				selected = enemyList.elementAt(i);
				if (HasLOSTo(selected.location.x, selected.location.y)) {
					spotting.SpotReport SPOTREP = new spotting.SpotReport(Clock.time, this, selected, selected.location.toHO());
					GlobalFuncs.allSpots.addReport(SPOTREP);
					selected.spotted = true;
					// GUI_NB.GCO("Added spot record: " + SPOTREP.displaySPOTREP());
					// GUI_NB.GCO("Checking record: " + GlobalFuncs.allSpots.getReport(GlobalFuncs.allSpots.records.size() - 1).displaySPOTREP());
					
					if (GlobalFuncs.showLOS) DisplayLOSTo(selected.location.x, selected.location.y, false);
					// GUI_NB.GCO(callsign + " has LOS to " + selected.callsign + " at time " + Clock.time);										
				}				
			}
		}				
	}
	
	public static void DisplayLOSToRange(Hex origin, int range) {
		Vector<Hex> ring = HexOff.HexRing(origin.x, origin.y, range);
		
		GlobalFuncs.scenMap.unshadeAll();
		
		for (int i = 0; i < ring.size(); i++) {
			Hex finger = ring.elementAt(i);
			DisplayLOSBetween(origin, finger, false);
		}		
	}
	
	public void DisplayLOSToRange(int range) {
		DisplayLOSToRange(this.location, range);
	}
	
	public static Vector<Hex> GetLOSToRange(Hex origin, int range) {
		Vector<Hex> ring = HexOff.HexRing(origin.x,  origin.y,  range);
		Vector<Hex> output = new Vector<Hex>();
		//GUI_NB.GCO("Init GetLOSToRange.  Range is " + range);
		
		for (int dist = 0; dist <= range; dist++) {
			ring = HexOff.HexRing(origin.x,  origin.y,  dist);
			
			//GUI_NB.GCO("Outer loop: Ring size is " + ring.size());
			
			for (int i = 0; i < ring.size(); i++) {
				Hex finger = ring.elementAt(i);
				
				if (LOSBetween(origin, finger)) output.addElement(finger);
			}			
		}
	
		return output;		
	}
	
	public void UpdateSharedSpotting() {
		Vector<Hex> hexList = GetLOSToRange(location, GlobalFuncs.visibility);
		
		for (int i = 0; i < hexList.size(); i++) {
			Hex finger = hexList.elementAt(i);
			finger.numSpots++;
		}
	}
	
	public void RemoveSharedSpotting() {
		Vector<Hex> hexList = GetLOSToRange(location, GlobalFuncs.visibility);
		
		for (int i = 0; i < hexList.size(); i++) {
			Hex finger = hexList.elementAt(i);
			finger.numSpots--;
			
			if (finger.numSpots < 1) {
				GUI_NB.GCO("ERROR!  Attempting to reduce number of spots below one.");
				finger.numSpots = 1;
			}
		}		
	}
	
	public Vector<Hex> GetLOSToRange(int range) {
		return GetLOSToRange(this.location, range);
	}
	
	public void DisplayWaypoints() {
		GlobalFuncs.scenMap.clearTextAll();
		for (int i = 0; i < waypointList.waypointList.size(); i++) {
			HexOff staticFinger = waypointList.waypointList.get(i);
			Hex finger = GlobalFuncs.scenMap.getHex(staticFinger.getX(), staticFinger.getY());
			
			GlobalFuncs.scenMap.setHexText(finger, "WP " + (i + 1), Color.WHITE);
		}
	}
	
	/**
	 * Moves the unit one hex in the direction specified.
	 * Direction is an integer with 1 = 30 degrees, 2 = 90 degrees, etc. in a clockwise pattern
	 * @param direction
	 */
	public void MoveUnit(int direction) {
		if (direction < 0 || direction > 5) return;
		
		HexOff newLoc = HexOff.NeighborOff(location.toHO(), direction);
		Hex destination = GlobalFuncs.scenMap.getHex(newLoc.getX(), newLoc.getY());
		
		// GUI_NB.GCO("Current loc: (" + location.x + ", " + location.y + ").  Dest loc: (" + newLoc.getX() + ", " + newLoc.getY() + ")");
		
		if (destination.tEnum != TerrainEnum.INVALID) {
			// Orients vehicle to destination hex
			OrientHullTo(destination.x, destination.y);
			
			// TODO There will be a bug if two units occupy the same space simultaneously, but it isn't debilitating.
			// Will destroy friendly units...
			Unit oldUnit = destination.HexUnit;
			if (oldUnit != null) {
				if (oldUnit.side == SideEnum.FRIENDLY) {
					GlobalFuncs.destroyedUnitList.addElement(oldUnit);
					oldUnit.RemoveUnit();
					GlobalFuncs.unitList.removeElement(oldUnit);
				}
				else return;		// Won't run over a friendly unit								
			}
				
			// Moves vehicle
			location.HexUnit = null;
			location = destination;
			location.HexUnit = this;
		}	
	}
	
	/**
	 * Moves the unit according to its move rate and the terrain in the direction specified.
	 * Direction is an integer with 1 = 30 degrees, 2 = 90 degrees, etc. in a clockwise pattern.
	 * Automatically resets unit to undetected state.
	 * @param direction
	 */	
	public void MoveUnitSubHex(int direction) {
		if (direction < 0 || direction > 5) return;

		
		if (subHexLocation == 0) {
			//GUI_NB.GCO("At center of hex.  Orienting to desired direction.");
			subHexDirection = direction;
		}
		
		// Checks to see if direction is compatible
		if (direction % 3 == subHexDirection % 3) {			
			if (direction == subHexDirection) {
				//GUI_NB.GCO("Directions are identical.  Moving.");
				subHexLocation += CalcMoveRate();				
				if (subHexLocation > HexMap.SUBHEX_SIZE) {
					int overshoot = subHexLocation - HexMap.SUBHEX_SIZE;
					MoveUnit(direction);
					subHexDirection += 3;
					if (subHexDirection > 6) subHexDirection -= 6;
					subHexLocation = HexMap.SUBHEX_SIZE - overshoot;
				}
			}
			else {
				//GUI_NB.GCO("Directions are opposite.  Moving to the center.");
				subHexLocation -= CalcMoveRate();
				if (subHexLocation < 0) {
					subHexLocation *= -1;
					subHexDirection += 3;
					if (subHexDirection > 6) subHexDirection -= 6;
				}
			}
		} else {
			//GUI_NB.GCO("Directions are incompatible.  Continuing move to center and will halt.");
			subHexLocation -= CalcMoveRate();
			if (subHexLocation < 0) subHexLocation = 0;
		}
		
	}
	
	/**
	 * Moves the unit one hex towards the waypoint
	 */
	public void MoveToWaypoint() {
		if (side != SideEnum.FRIENDLY) spotted = false;
		if (waypointList.getFirstWaypoint().getX() < 0) return;		// Invalid waypoint or no waypoint set
		
		HexAx nextWP = waypointList.getFirstWaypoint().ConvertToAx();		
		HexAx currentLoc = location.toHO().ConvertToAx();		
		
		int deltaX = nextWP.getX() - currentLoc.getX();
		int deltaY = nextWP.getY() - currentLoc.getY();
		
		// GUI_NB.GCO("Loc: " + currentLoc.getX() + ", " + currentLoc.getY() + 
		//		" Dest: " + nextWP.getX() + ", " + nextWP.getY() + "  || Delta: " + deltaX + ", " + deltaY);
		
		int moveDirection = -1;
		
		if (Math.abs(deltaX) == Math.abs(deltaY)) {
			// Move direction will be either 0 (NE) or 3 (SW)
			if (deltaX > deltaY) moveDirection = 0;
			if (deltaX < deltaY) moveDirection = 3;
			if (deltaX == deltaY) {
				if (deltaX > 0) moveDirection = 1;
				else moveDirection = 4;
			}
		}
		else if (Math.abs(deltaX) > Math.abs(deltaY)){
			if (deltaX > 0) moveDirection = 1;
			else moveDirection = 4;
		}
		else {
			if (deltaY > 0) moveDirection = 2;
			else moveDirection = 5;
		}
					
		// GUI_NB.GCO("Move dir: " + moveDirection);
		if (moveDirection < 0) return;
		
		// --------------------------------------------------------
		MoveUnit(moveDirection);				
		//MoveUnitSubHex(moveDirection);
		
		if (location.toHO().ConvertToAx().getX() == nextWP.getX() && location.toHO().ConvertToAx().getY() == nextWP.getY()) {
			// GUI_NB.GCO("Waypoint reached.");
			waypointList.removeFirstWaypoint();			
		}
	}
	
	/** Magically teleports the unit to the desired hex */
	public void TeleportTo(Hex h) {
		Hex currentLoc = this.location;
		if (h.HexUnit != null) {
			GUI_NB.GCO("ERROR!  Cannot move unit to desired hex, since that hex is occupied.");
			return;
		}
		
		if (emplaced) {
			RemoveSharedSpotting();
		}
		
		currentLoc.HexUnit = null;
		h.HexUnit = this;
		this.location = h;
		
		if (emplaced) UpdateSharedSpotting();
		
		// GUI_NB.GCO("Unit moved successfully.");
	}
	
	public String SaveUnit() {						
		StringBuffer out = new StringBuffer("");
		
		out.append(unitID + ", ");
		out.append(callsign + ", ");
		out.append(location.x + ", " + location.y + ", ");
		out.append(String.format("%.2f", hullOrientation) + ", ");
		out.append(String.format("%.2f", turretOrientation) + ", ");
		out.append(type + ", ");
		out.append(side + ", ");
		out.append(spotted + ", ");
		
		if (org == null) out.append("-1, ");	// No organism attached
		else out.append(org.genome.genome_id + ", ");
		
		out.append(orgType + ", ");
		out.append(fitType + ", ");
		out.append(curFitness + ", ");
		
		out.append(waypointList.saveWaypoints());
		
		return out.toString();
	}
	
	public Unit(String readL) {
		this(new Hex(-1, -1, TerrainEnum.INVALID, 0), SideEnum.NEUTRAL, "X", "X", 0.0, 0.0, null, false);
		RemovefromSide(side);
		
		String[] result = readL.split(", ");
		
		unitID = Integer.parseInt(result[0]);
		callsign = result[1];
		
		location = GlobalFuncs.scenMap.getHex(Integer.parseInt(result[2]), Integer.parseInt(result[3]));
		hullOrientation = Double.parseDouble(result[4]);
		turretOrientation = Double.parseDouble(result[5]);
		type = result[6];
		side = SideEnum.valueOf(result[7]);
		AddtoSide(side);
		
		spotted = Boolean.parseBoolean(result[8]);
		orgGenome = Integer.parseInt(result[9]);
		orgType = OrganismTypeEnum.valueOf(result[10]);
		fitType = FitnessTypeEnum.valueOf(result[11]);
		curFitness = Double.parseDouble(result[12]);
		
		waypointList = new WaypointList(result, 13);
		
		
		RefreshUnitInfo();
		// GUI_NB.GCO("Unit speed and move rate: " + this.moveRate + " and " + this.movePoints);
	}
	
	public String SaveTarget() {
		String tgtStr = "_";
		if (target != null) tgtStr = String.format("%d", target.unitID);
		
		String output = unitID + ", " + tgtStr;
		
		return output;
	}
	
	public int CalcMoveRate() {
		int moveCost = location.tType.getMoveCost(moveMode);
		// GUI_NB.GCO("Move calc: MP: " + movePoints + " * MR: " + moveRate + " with clock step " + ClockControl.CLOCK_STEP + "with move cost: " + moveCost);
		return (int) ((HexMap.SUBHEX_SIZE * movePoints * moveRate * ClockControl.CLOCK_STEP * GlobalFuncs.moveRateMult) / (100 * 60 * moveCost * 1000));
	}
	
	/** Activates the sensors for its given type and returns the output of the network */
	public double UseSensors(Hex prospective) {
		
		double sensorInput = 0.0;
		
		switch (orgType) {
		
		case SIMPLE_SINGLE:
			sensorInput = OrganismTypeEnum.NormalizedSenseFlowSingle(prospective, fitType);
			
			org.net.inputs.firstElement().LoadSensor(sensorInput);
			org.net.ActivateNetwork();
			
			return org.net.outputs.firstElement().getActivation();
			
		case SIMPLE_DUAL:
			sensorInput = OrganismTypeEnum.NormalizedSenseFlowSingle(prospective, fitType);			
			org.net.inputs.elementAt(0).LoadSensor(sensorInput);
			
			sensorInput = OrganismTypeEnum.SenseFlowLocation(prospective, fitType);
			org.net.inputs.elementAt(1).LoadSensor(sensorInput);
			org.net.ActivateNetwork();
			
			return org.net.outputs.firstElement().getActivation();
			
		case SIX_DIRECTIONAL:
			double[] sensorFaces = OrganismTypeEnum.SenseFlow60(prospective, fitType);
			double thisHex = OrganismTypeEnum.SenseFlowLocation(prospective, fitType);
			
			for (int i = 0; i < 6; i++) {
				org.net.inputs.elementAt(i).LoadSensor(sensorFaces[i]);
			}
			org.net.inputs.elementAt(6).LoadSensor(thisHex);
			org.net.ActivateNetwork();
			
			return org.net.outputs.firstElement().getActivation();						
		}
		
		return 0.0;
	}
	
	
	 
}
