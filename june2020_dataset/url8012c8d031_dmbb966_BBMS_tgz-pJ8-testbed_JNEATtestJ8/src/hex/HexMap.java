package hex;

import gui.GUI_NB;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Vector;

import clock.Clock;
import terrain.TerrainEnum;
import unit.FitnessTypeEnum;
import unit.OrganismTypeEnum;
import unit.Unit;
import utilities.FIO;
import bbms.GlobalFuncs;

public class HexMap {
	
	private int randPool;
	public int xDim, yDim;
	public Hex[][] hexArray;
	
	/** Column on the map before (less than) which is considered the "friendly zone" - enemy units spotted 
	 * within this zone do not count towards fitness because they have made it through the "recon zone". 
	 * Don't worry about crossing the streams - it won't hurt anything. */
	public int friendlyZone = -1;
	
	/** Column on the map after (greater than) which is considered the "enemy zone" - enemy units spotted 
	 * within this zone do not count towards fitness because there is "no way" in which friendly scouts can
	 * be expected to detect them.  Don't worry about crossing the streams - it won't hurt anything. */
	public int enemyZone = -1;
	
	
	Vector<hex.Hex> shadedHexList;
	Vector<hex.Hex> textHexList;
	public Vector<hex.Hex> vaporSourceList;
	public Vector<hex.Hex> vaporSinkList; 
	public static int chanceTrees = 20;
	public static int chanceHighGrass = 0;
	public static int chanceClear = 80;
	public static int totalWeight = chanceClear + chanceHighGrass + chanceTrees;
	
	public static final int SUBHEX_SIZE = 5000;		// Sub units in one 50m hex
	
	public HexMap(int x, int y, boolean cleanMap) {
		xDim = x;
		yDim = y;
		friendlyZone = 2;
		enemyZone = x - 2;
		hexArray = new Hex[xDim][yDim];
		shadedHexList = new Vector<Hex>();
		textHexList = new Vector<Hex>();
		vaporSourceList = new Vector<Hex>();
		vaporSinkList = new Vector<Hex>();
		if (!cleanMap) GenerateMap();
		else GenerateBlankMap();
	}
	
	public HexMap(int x, int y) {
		this(x, y, true);
	}
	
	/** Returns if the given hex is in the enemy zone*/
	public boolean inEnemyZone(Hex h) {
		return (h.x >= enemyZone);
	}
	
	/** Returns if the given hex is in the friendly zone*/
	public boolean inFriendlyZone(Hex h) {
		return (h.x <= friendlyZone);
	}
	
	public boolean inReconZone(Hex h) {
		return (!inEnemyZone(h) && !inFriendlyZone(h));
	}
	
	public void calcAllVapor() {
		for (int y = 0; y < yDim; y++) {
			for (int x = 0; x < xDim; x++) {
				Hex finger = hexArray[x][y];
				finger.CalcVapor();
			}
		}
	}
	
	
	/** Samples a number of random hexes within the recon zone to determine the average visibility of DV
	 * Stores it in GlobalFuncs to normalize things later on.*/
	public double CalcApproxDVNorm(int samples) {
		double mostDV = 1.0;
		
		for (int i = 0; i < samples; i++) {
			Hex finger = RandomHexReconZone();
			double comparator = OrganismTypeEnum.SenseFlowFOV(finger, FitnessTypeEnum.SIMPLE_GREEDY);
			if (comparator > mostDV) mostDV = comparator;
		}
		return mostDV;
	}
	
	public void UpdateExactDVNorm() {
		double mostDV = 1.0;
		double most60DV = 1.0;
		double mostSingleHex = 1.0;
		
		for (int y = 0; y < yDim; y++) {
			for (int x = 0; x < xDim; x++) {
				Hex finger = getHex(x, y);
				if (inReconZone(finger)) {
					double[] comp60 = OrganismTypeEnum.SenseFlow60(finger, FitnessTypeEnum.SIMPLE_GREEDY);					
					double FOVsum = 0.0;					
					
					for (int i = 0; i < 6; i++) {
						FOVsum += comp60[i];
						if (comp60[i] > most60DV) most60DV = comp60[i];
						//GUI_NB.GCO("Unnormalized flow from " + i + " is " + comp60[i]);
					}
					
					mostDV = Math.max(FOVsum,  mostDV);
					mostSingleHex = Math.max(mostSingleHex, finger.deltaVapor);
				}
			}
		}
		
		GlobalFuncs.maxSpottedDV = mostDV;
		GlobalFuncs.maxSpottedDV60 = most60DV;
		GlobalFuncs.maxsingleDV = mostSingleHex;
		
		GUI_NB.GCO("Calculated exact normalized DV: " + mostDV);
		GUI_NB.GCO("Calculated exact 60 degree normalized DV: " + most60DV);
		GUI_NB.GCO("Calculated exact single hex normalized DV: " + mostSingleHex);
	}
	
	/** Returns a random hex within the Recon Zone of the map, accounting for the force concealment attribute in GlobalFuncs */
	public Hex RandomHexReconZone() {
		int errCount = 0;
		boolean foundIt = false;
		
		Hex finger = null;
		
		while (!foundIt) {
			errCount++;
			int x = GlobalFuncs.randRange(friendlyZone + 1, enemyZone - 1);
			int y = GlobalFuncs.randRange(0, yDim - 1);
			
			finger = getHex(x, y);
			
			if (GlobalFuncs.forceTreePlacement) {
				if (finger.tEnum == TerrainEnum.T_GRASS || finger.tEnum == TerrainEnum.TREES) foundIt = true; 
			} 
			else  if (errCount > 1000) {
				GUI_NB.GCO("ERROR!  Could not find a concealment hex!  Disabling tree forcing for future units.");
				GlobalFuncs.forceTreePlacement = false;
				foundIt = true;
			}
			else foundIt = true;
		}									
		
		return finger;
	}
	
	/** 
	 * Checks vapor sources and sinks to determine the anticipated change for the next turn.
	 */
	public void updateVaporSS() {
		int sinkDV = 0;
		for (int i = 0; i < vaporSinkList.size(); i++) {
			Hex finger = vaporSinkList.elementAt(i);
			int DV = finger.ReturnVaporCalc();
			sinkDV += DV;			 				
		}
		
		int srcDV = 0;		
		for (int i = 0; i < vaporSourceList.size(); i++) {
			Hex finger = vaporSourceList.elementAt(i);
			int DV = finger.ReturnVaporCalc();			
			srcDV += DV;			
		}
		
		GlobalFuncs.maxDelta = Math.max(srcDV, sinkDV);
	}
	
	/**
	 * Predicts a vapor equilibirum for the map (linear interpolation between source and sink)
	 */
	public void predictVaporMap() {
	    long startCycle = System.currentTimeMillis();
	    long endCycle;
	    long durationCycle;
	    
	    for (int y = 0; y < yDim; y++) {
	    	for (int x = 0; x < xDim; x++) {
	    		Hex finger = hexArray[x][y];
	    		finger.PredictVaporLevel();
	    	}
	    }
	    
	    endCycle = System.currentTimeMillis();
	    durationCycle = endCycle - startCycle;
	    GUI_NB.GCO("Vapor prediction complete in " + durationCycle + "ms");
	}
	
	/**
	 * Initializes a standard vapor map with all cells starting with maximum density
	 */
	public void StandardVaporMap() {
	    for (int y = 0; y < yDim; y++) {
	    	for (int x = 0; x < xDim; x++) {
	    		Hex finger = hexArray[x][y];
	    		finger.vapor = 25500;
	    		finger.deltaVapor = 0;
	    	}
	    }
	    
	    GUI_NB.GCO("Vapor levels in all map hexes reset to full density.");
	}
	
	/**
	 * Calculates the flow rate based on the max change at source and sink.
	 * Intended to speed up the gas diffusion process.  Max acceleration is capped at x3
	 * @return
	 */
	public void recalcFlowRate() {
		
		if (GlobalFuncs.fixSlowRate || Math.abs(GlobalFuncs.totalVaporDelta) < 20) {
			GlobalFuncs.flowRate = 1.0;
			return;
		}
		
		if (GlobalFuncs.reduceRate) {
			GlobalFuncs.reduceRate = false;
			GlobalFuncs.flowRate -= GlobalFuncs.flowStep * 5.0;
			if (GlobalFuncs.flowRate < 1.00) GlobalFuncs.flowRate = 1.0;
		}
		else if (GlobalFuncs.ticksStable % GlobalFuncs.flowCheck == GlobalFuncs.flowCheck - 1) {
			if (GlobalFuncs.flowRate < GlobalFuncs.flowRateCap) GlobalFuncs.flowRate += GlobalFuncs.flowStep;						
		}
		
		
	}
	
	public void resetSpotCounts() {
		for (int y = 0; y < yDim; y++) {
			for (int x = 0; x < xDim; x++) {
				hexArray[x][y].numSpots = 1;
			}
		}
	}
	
	public void updateAllVapor() {
		
		long oldTV = GlobalFuncs.totalVapor;
		GlobalFuncs.totalVapor = 0;
		
		for (int y = 0; y < yDim; y++) {
			for (int x = 0; x < xDim; x++) {
				Hex finger = hexArray[x][y];
				finger.UpdateVapor();
			}
		}
		
		GlobalFuncs.totalVaporDelta = (int)(oldTV - GlobalFuncs.totalVapor);		
		
		UpdateSourceSink();
	}
	
	
	public void ShowSideWaypoints(unit.SideEnum side) {		
		// GUI_NB.GCO("Showing all wayspoints on side: " + side.toString());
		// First, clears hex text
		clearTextAll();
				
		// Gets lists of units to work with
		Vector<Unit> unitList = null;
		switch (side) {
		case FRIENDLY:
			unitList = GlobalFuncs.friendlyUnitList;
			GUI_NB.GCO("DEBUG: Unit list has " + unitList.size() + " size compared to " + GlobalFuncs.friendlyUnitList.size());
			break;
		case ENEMY:
			unitList = GlobalFuncs.enemyUnitList;
			break;
		case NEUTRAL:
			GUI_NB.GCO("ERROR!  Neutral units not implemented.");
			return;
		}
		
		Iterator<Unit> itr_unit = unitList.iterator();
		while (itr_unit.hasNext()) {
			Unit finger = itr_unit.next();
			//GUI_NB.GCO("DEBUG: Unit selected is " + finger.callsign);
			
			if (finger.waypointList != null) {
				for (int i = 0; i < finger.waypointList.waypointList.size(); i++) {
					HexOff thisWP = finger.waypointList.waypointList.get(i);
					Hex thisHex = GlobalFuncs.scenMap.getHex(thisWP);
					//GUI_NB.GCO("DEBUG: Looking at WP " + (i + 1) + " at " + thisHex.toString());
					if (GlobalFuncs.displayMiniMap) {
						int xCent = (int) (GlobalFuncs.miniMapSize * (thisHex.x + 2) + (GlobalFuncs.miniMapSize * 0.5) + ((thisHex.y & 1) * (GlobalFuncs.miniMapSize * 0.5)));
						int yCent = (int) (GlobalFuncs.miniMapSize * (thisHex.y + 2) + (GlobalFuncs.miniMapSize * 0.5));
						
						GlobalFuncs.gui.getGraphics().setColor(Color.CYAN);
						GlobalFuncs.gui.getGraphics().drawRect(xCent - 2,  yCent - 2, 4, 4);
					}
					else {
						if (thisHex.hexText.equals("")) {
							setHexText(thisHex, "WP " + (i + 1) + " :1", Color.WHITE);
						} else	// Will only have WPs since we cleared text at the start 
						{
							String curHexText = getHexText(thisHex);
							String[] splitText = curHexText.split(":");
							int stackedWPs = Integer.parseInt(splitText[1]);
							stackedWPs++;
							setHexText(thisHex, "WP " + (i + 1) + " :" + Integer.toString(stackedWPs), Color.WHITE);
						}
					}
				}
			}
		}
	}
	
	public void shadeHex(Hex h, Color c) {
		h.shadedColor = c;
		if (h.shaded) return;
		h.shaded = true;
		shadedHexList.add(h);
	}
	
	public void unshadeHex(Hex h) {
		h.shaded = false;
		shadedHexList.remove(h);
	}
	
	public void setHexText(Hex h, String s, Color c) {
		h.textColor = c;
		h.hexText = s;
		if (h.displayText) return;
		h.displayText = true;
		if (!textHexList.contains(h)) textHexList.add(h);		
	}	
	
	public String getHexText(Hex h) {
		return h.hexText;
	}
	
	public void showHexText(Hex h) {
		setHexText(h, h.hexText, h.textColor);
	}
	
	public void hideHexText(Hex h) {
		h.displayText = false;
		textHexList.remove(h);
	}
	
	public void removeHexText(Hex h) {
		h.displayText = false;
		h.hexText = "";
		h.textColor = Color.WHITE;
		textHexList.remove(h);
	}
	
	public void displayShadedList() {
		gui.GUI_NB.GCO("There are " + shadedHexList.size() + " hexes shaded.");
		for (int i = 0; i < shadedHexList.size(); i++) {
			gui.GUI_NB.GCO("Hex " + i + " at " + shadedHexList.elementAt(i).x + ", " + shadedHexList.elementAt(i).y);
		}
	}
	
	public void unshadeAll() {
		for (int i = shadedHexList.size() - 1; i >= 0; i--) {
			unshadeHex(shadedHexList.elementAt(i));
		}
		bbms.GlobalFuncs.gui.repaint();
	}
	
	public void hideTextAll() {
		for (int i = textHexList.size() - 1; i >= 0; i--) {
			hideHexText(textHexList.elementAt(i));
		}
		bbms.GlobalFuncs.gui.repaint();
	}
	
	public void clearTextAll() {
		for (int i = textHexList.size() - 1; i >= 0; i--) {
			removeHexText(textHexList.elementAt(i));
		}
		bbms.GlobalFuncs.gui.repaint();
	}
	
	public TerrainEnum chooseTerrain(int r) {
		randPool = r;
		if (TerrainEval(randPool, HexMap.chanceClear)) return TerrainEnum.CLEAR;
		if (TerrainEval(randPool, HexMap.chanceHighGrass)) return TerrainEnum.T_GRASS;
		if (TerrainEval(randPool, HexMap.chanceTrees)) return TerrainEnum.TREES;
		return TerrainEnum.INVALID;					
	}
	
	public boolean TerrainEval(int r, int threshold) {
		if (r <= threshold) return true;
		else {
			randPool -= threshold;
			return false;
		}
	}
	
	/**
	 * NOTE: For now, does not change the default elevation from 0
	 */
	public void GenerateMap() {
		for (int y = 0; y < yDim; y++) {
			for (int x = 0; x < xDim; x++) {
				TerrainEnum tType = chooseTerrain(GlobalFuncs.randRange(1, HexMap.totalWeight));
				hexArray[x][y] = new Hex(x, y, tType, 0);
				// hexArray[x][y].GCODisplay();
			}
		}
	}
	
	/**
	 * Generates a map of clear terrain tiles
	 */
	public void GenerateBlankMap() {
		for (int y = 0; y < yDim; y++) {
			for (int x = 0; x < xDim; x++) {
				TerrainEnum tType = TerrainEnum.CLEAR;
				hexArray[x][y] = new Hex(x, y, tType, 0);
			}
		}
	}
	
	public Hex getHex(int getX, int getY) {
		if (getX >= xDim || getX < 0 || getY >= yDim || getY < 0) {
			// GUI_NB.GCO("ERROR: Cannot get hex at " + getX + ", " + getY + " because map dimensions are " + xDim + " x " + yDim);
			return new Hex(getX, getY, TerrainEnum.INVALID, 0);
		}
		
		return hexArray[getX][getY];
	}
	
	public void storeHex(int x, int y, Hex in) {
		hexArray[x][y] = in;
	}
	
	public Hex getHex(HexOff h) {
		return getHex(h.x, h.y);
	}
	
	public int getXDim() {
		return xDim;
	}
	
	public int getYDim() {
		return yDim;
	}

	public static boolean checkMapBounds(int x, int y) {
		if (!GlobalFuncs.mapInitialized) return false;
		
		if (x < 0 || y < 0) return false;
		
		if (x > GlobalFuncs.scenMap.getXDim() || y > GlobalFuncs.scenMap.getYDim()) return false;
		
		return true;
	}
	
	/** Old version - use the one that returns a String in the future. */
	public void saveMap(Path p) {		
		// Stores map characteristics
		FIO.overwriteFile(p, "# Map and environment characteristics: x size, y size, map view x, map view y, clock time");
		FIO.appendFile(p, xDim + ", " + yDim + ", " + 
						GlobalFuncs.gui.GMD.mapDisplayX + ", " + GlobalFuncs.gui.GMD.mapDisplayY + ", " + 
						Clock.time + "\n");

		// Stores hex information 
		FIO.appendFile(p, "# Hex data, stored rows");
		FIO.appendFile(p, "# Format is: TerrainEnumID, elevation, obstacle height, density, obscuration, vapor, deltaVapor, vaporType");
		
		for (int y = 0; y < yDim; y++) {
			FIO.appendFile(p, "\n# Row " + y);
			for (int x = 0; x < xDim; x++) {
				FIO.appendFile(p, hexArray[x][y].saveHex(), false);
			}
		}
		
	}
	
	public boolean loadMap(BufferedReader breader) {
		try {
			String readL = breader.readLine();
			int loadHexX = 0;
			int loadHexY = 0;
			
			while (readL != null) {
				if (readL.startsWith("#")) {} 			//GUI_NB.GCO("Comment follows: " + readL);
				else if (readL.contentEquals("")) {} 	//GUI_NB.GCO("Blank line: " + readL);			
				// With the above non-data holding lines stripped out, only valid input will be evaluated here
				else {
					//GUI_NB.GCO("Reading string: >" + readL + "<");
					
					GlobalFuncs.scenMap.storeHex(loadHexX,  loadHexY,  new Hex(loadHexX, loadHexY, readL));
					loadHexX++;
					if (loadHexX == xDim) {
						loadHexY++;
						loadHexX = 0;
						
						if (loadHexY == yDim) {	
							return true;
						}
					}	
				}
				
				readL = breader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	public String saveMap() {
		StringBuffer buf = new StringBuffer("");
		
		// Store map characteristics
		
		
		
		// Stores hex information
		buf.append("# Hex data, stored in rows.\n");
		buf.append("# Format: TerrainEnum, elevation, obs height, density, obscuration, vapor, deltaVapor, vaporType\n");
		
		for (int y = 0; y < yDim; y++) {
			buf.append("\n# Row " + y + "\n");
			for (int x = 0; x < xDim; x++) {
				buf.append(hexArray[x][y].saveHex());
			}
		}
		
		return buf.toString();
	}
	
	/** Sets all vapor source hexes to maximum and drains all source sink hexes.
	 * 
	 */
	public void UpdateSourceSink() {
		for (int i = 0; i < vaporSourceList.size(); i++) {
			Hex finger = vaporSourceList.elementAt(i);
			finger.vapor = 25500;
		}
		
		for (int i = 0; i < vaporSinkList.size(); i++) {
			Hex finger = vaporSinkList.elementAt(i);
			finger.vapor = 0;
		}
	}


}
