package bbms;

import java.awt.Color;
import java.awt.MenuBar;
import java.lang.Enum;
import java.awt.Desktop.Action;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JMenuBar;

import jneat.Population;
import clock.Clock;
import clock.ClockThread;
import unit.FitnessTypeEnum;
import unit.OrganismTypeEnum;
import unit.Unit;
import utilities.FIO;
import utilities.MersenneTwister;
import gui.GUIKeyboard;
import gui.GUIMenu;
import gui.GUI_NB;
import hex.*;

public class GlobalFuncs {
	
	public static GUI_NB gui;
	public static HexMap scenMap;
	public static boolean mapInitialized = false;
	public static boolean clockInitialized = false;
	public static int placeUnit = 0;
	public static boolean showShaded = true;	
	public static boolean showVapor = false;
	public static boolean showLOS = true;
	public static boolean showFOW = false;	// Toggles fog of war
	public static boolean showWPs = false;
	
	public static int miniMapSize = 16;
	public static boolean updateVapor = false;
	public static boolean shareTeamFit = true;
	
	public static boolean RotateHull = true;
	public static boolean runningTest = false;
	
	public static boolean displayMiniMap = false;
	public static MiniMapEnum MiniMapType = MiniMapEnum.TERRAIN;
	
	public static double networkResultThreshold = 0.75;
	public static double mutateProbability = 0.05;
	public static int epochInterval = 10;
	
	public static String dirPrefix = "T";
	
	public static double deathPenalty = 1.0;	// Fitness multiplier if the unit is destroyed
	public static FitnessTypeEnum defaultFitType = FitnessTypeEnum.SIMPLE_GREEDY;
	
	public static Vector<unit.Unit> unitList = new Vector<Unit>();
	public static Vector<unit.Unit> friendlyUnitList = new Vector<Unit>();
	public static Vector<unit.Unit> enemyUnitList = new Vector<Unit>();	
	public static Vector<unit.Unit> destroyedUnitList = new Vector<Unit>();
	public static Hex selectedHex = null; 
	public static Unit selectedUnit = null;
	
	public static OrganismTypeEnum defaultOrgType = OrganismTypeEnum.SIMPLE_SINGLE;
	
	public static boolean calcShared = false;
	public static boolean forceTreePlacement = true;
	
	public static Population currentPop = null;	
	public static String tempStr = "";				// Used for some dialog box results
	public static int orgAssignNum = 0;
	public static int maxRunsPerOrg = 1;
	public static int numTests = 1;
	public static int numDifferentTests = 1;
	
	public static Path detailedOutput = null;
	public static Path summaryOutput = null;
	public static Path fullIterOutput = null;
	public static Path targetPop = null;
	public static Path currentTestPath = null;
	public static String outputPrefix = "";
	public static String inputPrefix = "";
	public static double percentPerRun = 0.1;
	public static int currentRunsPerOrg = 0;
	public static int iterationCount = 0;
	public static int numScoutsPer = 0;
	public static int curEpoch = 1;
	public static int maxEpochs = 1;
	public static boolean pauseNewIter = false;
	public static boolean pauseNewEpoch = false;
	public static boolean newEpoch = false;
	public static boolean randCOAEpoch = true;
	
	public static int currentPopEpochNum = 0;
	
	public static int spottedSoFar = 0;
	public static int maxPossibleSpots = 0;
	
	public static double moveRateMult = 10.0;
	
	// Used for scenario creation and execution
	public static int COAIndex = 1;
	public static COA curCOA = null;
	public static Vector<COA> allCOAs = new Vector<COA>();
	
	
	public static Thread GameClock = new Thread(new ClockThread());
	public static boolean runtoEq = false;		// If true, the clock will run at a high rate of speed until equilibrium is reached
	public static int dvTolerance = 0;			// When the vapor DV equals this number, will terminate autorun
	
	public static int visibility = 0;
	
	private static int unitCount = 0;
	
	/** Used to normalize results for sensor input */
	public static double maxSpottedDV = 1.0;	
	public static double maxSpottedDV60 = 1.0;
	public static double maxsingleDV = 1.0;
	
	// Vapor variables
	/** Universal modifier to vapor flow rate */	 
	public static double flowRate = 1.00;
	/** Maximum flow rate of the system - will adjust based on actual system performance */
	public static double flowRateCap = 2.95;
	/** Rate at which the flow rate increases or decreases */
	public static double flowStep = 0.05;
	/** Will increase the flow rate after this many stable ticks */
	public static int flowCheck = 5;
	/** If true, will reduce the flow rate for this tick */
	public static boolean reduceRate = false;
	public static boolean fixSlowRate = false;
	/** Highest flow rate as calculated by sources and sinks*/
	public static int maxDelta = 0;		
	/** Number of ticks since the last source or sink was removed.  
	 * After being stable for ten ticks, the flow rate will increase.
	 * This throttling helps avoid overflow/underflow issues.   */
	public static int ticksStable = 0;
	/** Total amount of vapor on the map */
	public static long totalVapor = 0;
	/** Total vapor delta */
	public static int totalVaporDelta = 0;
	
	// Spotting variables
	public static spotting.SpotRecords allSpots = new spotting.SpotRecords();

	/**
	 * Random number object, uses the Mersenne Twister algorithm, coded in Java by Sean Luke (http://cs.gmu.edu/~sean/research/)
	 */
	public static MersenneTwister randGen = new MersenneTwister();
	
	/**
	 * Uses the Mersenne Twister to generate a random number between a minimum and maximum range (inclusive)
	 * Only uses integers.
	 */
	public static int randRange(int min, int max) {
		int delta = max - min + 1;
		return min + randGen.nextInt(delta);
	}
	
	/**
	 * Uses the Mersenne Twister to generate a random number given a mean and variance.  
	 * Only uses integers.
	 */
	public static int randMean (int mean, int var) {
		int min = mean - var;
		return min + randGen.nextInt(var * 2);
	}
	
	/** 
	 * Uses the Mersenne Twister to generate a random double between 0.0 and 1.0
	 */
	public static float randFloat() {
		return randGen.nextFloat();
	}
	
	/**
	 * Uses the Mersene Twister to return +1 or -1
	 */
    public static int randPosNeg() 
	{  
		 int n = randGen.nextInt();
		 if ((n % 2) == 0) return -1;
		 else return 1;
	}      
    
    /** 
     * Uses the Mersene Twister to return a random number with a Gaussian distribution
     */
    
    public static double randGauss() {
    	return randGen.nextGaussian();
    }
	
	public static void initializeMap (int x, int y, boolean loadMap) {
		scenMap = new HexMap(x, y, loadMap);
		mapInitialized = true;
		unitCount = 0;
		selectedUnit = null;
		selectedHex = null;
		unitList.clear();
		friendlyUnitList.clear();
		enemyUnitList.clear();
		destroyedUnitList.clear();
		
		GUIKeyboard.initializeKeyCommands();
		Clock.SetTime(0, 0, 0);
		GlobalFuncs.ticksStable = 0;
		if (!clockInitialized) {
			GUI_NB.GCO("Starting clock");
			GameClock.start();
		}
		
		currentPop = null;
		orgAssignNum = 0;
		maxPossibleSpots = 0;
		
		COAIndex = 1;
		curCOA = new COA("Default");
		allCOAs = new Vector<COA>();
		allCOAs.addElement(curCOA);
		curCOA.LoadCOA();
				
		GUI_NB.GCO("Generating main map.");
		
		// Update menu to reflect added functionality that comes with a generated map
		GUIMenu menu = (GUIMenu) gui.getJMenuBar();
		menu.removeAll();		
		menu.GenerateMenu();						
		gui.setJMenuBar(menu); 
		
	}
	
	public static String saveMapCharacteristics () {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("# Map and environment characteristics: x size, y size, map view x, map view y, clock time, coa, maxcoa, visibility, friendlyzone, enemyzone\n");
		
		buf.append(scenMap.xDim + ", " + scenMap.yDim + ", ");
		buf.append(gui.GMD.mapDisplayX + ", " + gui.GMD.mapDisplayY + ", ");
		buf.append(Clock.time + ", ");
		buf.append(COAIndex + ", ");
		buf.append(GlobalFuncs.visibility + ", ");
		buf.append(GlobalFuncs.scenMap.friendlyZone + ", ");
		buf.append(GlobalFuncs.scenMap.enemyZone + ", ");
		
		return buf.toString();
	}
	
	public static boolean loadMapCharacteristics (BufferedReader breader) {
		try {
			String readL = breader.readLine();
			
			while (readL != null) {
				if (readL.startsWith("#")) {} 			//GUI_NB.GCO("Comment follows: " + readL);
				else if (readL.contentEquals("")) {} 	//GUI_NB.GCO("Blank line: " + readL);			
				// With the above non-data holding lines stripped out, only valid input will be evaluated here
				else {
					GUI_NB.GCO("Reading string: >" + readL + "<");
					String[] result = readL.split(", ");
					
					int xDim = Integer.parseInt(result[0]);
					int yDim = Integer.parseInt(result[1]);
					
					initializeMap(xDim, yDim, true);
					gui.GMD.mapDisplayX = Integer.parseInt(result[2]);
					gui.GMD.mapDisplayY = Integer.parseInt(result[3]);
					Clock.time = Integer.parseInt(result[4]);
					
					//COAIndex = Integer.parseInt(result[5]);
					COAIndex = 1;
					
					GlobalFuncs.visibility = Integer.parseInt(result[6]);
					
					//FIXME - Remove this!
					GlobalFuncs.visibility = 4;
					
					GlobalFuncs.scenMap.friendlyZone = Integer.parseInt(result[7]);
					GlobalFuncs.scenMap.enemyZone = Integer.parseInt(result[8]);
					
					
					if (result.length > 9) GUI_NB.GCO("ERROR!  Input line for map characteristics is too long!");
					else {
						GUI_NB.GCO("Map characteristics loaded successfully.");
					}
					return true;
				}
				
				readL = breader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	public static void initializeMap (int x, int y) {
		initializeMap(x, y, false);		
	}
	
	/**
	 * If s is less than length, will return a new string with sufficient blank spaces concatanated on to the end
	 * to bring the string up to the desired length.  Returns the original string otherwise.
	 */
	public static String whiteFill (String s, int len) {
		if (s.length() >= len) return s;
		else {
			int whiteFill = len - s.length();
			for (int i = 0; i < whiteFill; i++) {
				s = s.concat(" ");
			}
			
			return s;
		}
	}
	
	/**
	 * Returns the current unit count
	 */
	public static int getUnitCount() {
		return unitCount;
	}
	
	/** Creates a distinct (non-linked) copy of a unit vector */
	public static Vector<Unit> duplicateUnitVec(Vector<Unit> original) {
		Vector<Unit> ret = new Vector<Unit>(original.size());
		
		for (int i = 0; i < original.size(); i++) {
			Unit orig = original.elementAt(i);
			Unit copy = new Unit(orig);
			ret.addElement(copy);
		}
		
		return ret;
	}
	
	/**
	 * Increments the total unit count by one and returns the new value
	 */
	public static int getNewUnitCount() {
		return unitCount += 1;
	}
	
	public static int normalizeAngle(int a) {
		if (a >= 360) return normalizeAngle(a -= 360);
		else if (a < 0) return normalizeAngle(a += 360);
		else return a;
	}
	
	public static double normalizeAngle(double a) {
		if (a >= 360) return normalizeAngle(a -= 360);
		else if (a < 0) return normalizeAngle(a += 360);
		else return a;
	}
	
	/**
	 * Saves the game.
	 */
	public static boolean saveState() {
		if (!GlobalFuncs.mapInitialized) return false;
		String saveFile = "src/saves/save.txt";
		File f = new File(saveFile);
		if (!f.exists()) FIO.newFile(saveFile);
		
		Path p = f.toPath();
		
		FIO.SaveFile(p);				
			
		return true;
	}
	
	public static boolean saveScen(String saveName) {
		if (!GlobalFuncs.mapInitialized) return false;
		
		File f = new File(saveName);
		if (!f.exists()) FIO.newFile(saveName);
		Path p = f.toPath();
		
		FIO.SaveScen(p);
		
		return true;
	}
	
	public static boolean loadState() {
		// TODO: Reset all variables as if you were reinitializing the map
		
		String loadFile = "src/saves/save.txt";
		File f = new File(loadFile);
		if (!f.exists()) return false;
		
		Path p = f.toPath();
		FIO.LoadFile(p);
				
		return true;
	}

}
