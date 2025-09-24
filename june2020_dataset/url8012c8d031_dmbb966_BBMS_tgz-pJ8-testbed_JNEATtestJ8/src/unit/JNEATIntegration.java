package unit;

import java.io.File;
import java.nio.file.Path;
import java.util.Vector;

import terrain.TerrainEnum;
import utilities.FIO;
import clock.Clock;
import clock.ClockControl;
import jneat.Organism;
import jneat.Population;
import bbms.GlobalFuncs;
import gui.DialogFileName;
import gui.DialogLoadScen;
import gui.GUI_NB;
import hex.Hex;

public class JNEATIntegration {
	
	static double death_sum = 0.0;
	static int death_count = 0;
	static double epoch_spotted = 0;
	static int epoch_possibleSpot = 0;
	
	static int testNumber = 0;
	
	static double teamPerformance = 0.0;
	
	public static void EndofTest() {
		ClockControl.SetPaused(true);
		
		for (int i = 0; i < GlobalFuncs.friendlyUnitList.size(); i++) {
			death_count++;			
		}
		
		// GUI_NB.GCO(":::DESTROYED UNITS:::");
		
		for (int i = 0; i < GlobalFuncs.destroyedUnitList.size(); i++) {										
			death_sum++;			
			death_count++;
		}
		
		// GUI_NB.GCO("Spotted: " + GlobalFuncs.spottedSoFar + " out of " + GlobalFuncs.maxPossibleSpots);
		GlobalFuncs.iterationCount++;
		
		epoch_spotted += GlobalFuncs.spottedSoFar;
		epoch_possibleSpot += GlobalFuncs.maxPossibleSpots;
		
		if (GlobalFuncs.iterationCount > GlobalFuncs.numTests) {
			PrintTestSummary();
			GlobalFuncs.COAIndex++;
			GlobalFuncs.iterationCount = 0;
			death_count = 0;
			death_sum = 0.0;
			epoch_spotted = 0.0;
			epoch_possibleSpot = 0;
		}
							
		if (GlobalFuncs.COAIndex > GlobalFuncs.allCOAs.size()) {
			
			GlobalFuncs.currentPopEpochNum += GlobalFuncs.epochInterval;
					
			
			if (GlobalFuncs.currentPopEpochNum > 50) {
				GlobalFuncs.currentPopEpochNum = 1;
				testNumber++;
				RunNextTest();
				return;
			}
			
			PopTestNewEpoch(GlobalFuncs.currentPopEpochNum);
					
		}
		else {
			if (GlobalFuncs.pauseNewIter || (GlobalFuncs.pauseNewEpoch && GlobalFuncs.newEpoch)) {
				// Pause at these iterations; since test already paused, will do so
			}
			else {
				if (GlobalFuncs.curEpoch > GlobalFuncs.maxEpochs) {
					// Pause when max epochs exceeded
				}
				else {
					ClockControl.SetPaused(false);	// Unpauses the test
				}
			}
			
			GlobalFuncs.spottedSoFar = 0;
			GlobalFuncs.maxPossibleSpots = 0;
			Clock.time = 0;
			
			if (GlobalFuncs.calcShared) GlobalFuncs.scenMap.resetSpotCounts();
			TestIterationSetup(GlobalFuncs.numScoutsPer);
		}
	}
	
	// NOTE: Start of scenario is found in FIO
	public static void EndofScenario() {
		ClockControl.SetPaused(true);
		
		teamPerformance = (GlobalFuncs.spottedSoFar / GlobalFuncs.maxPossibleSpots);
		
		for (int i = 0; i < GlobalFuncs.friendlyUnitList.size(); i++) {
			Unit finger = GlobalFuncs.friendlyUnitList.elementAt(i);
			finger.org.AverageFitness(finger.fitType.EvaluateFitness(finger));
			GUI_NB.GCO("Unit " + finger.callsign + " has fitness " + finger.org.fitness);
			death_count++;			
		}
		
		// GUI_NB.GCO(":::DESTROYED UNITS:::");
		
		for (int i = 0; i < GlobalFuncs.destroyedUnitList.size(); i++) {
			Unit finger = GlobalFuncs.destroyedUnitList.elementAt(i);

			// Destroyed units have a fitness multiplier applied to them, on top of the fitness lost by no longer spotting.
			finger.org.AverageFitness(finger.fitType.EvaluateFitness(finger) * GlobalFuncs.deathPenalty);
			GUI_NB.GCO("Unit " + finger.callsign + " has fitness " + finger.org.fitness);
			death_sum++;			
			death_count++;
		}
		
		PrintDetailedIter();				

		epoch_spotted += GlobalFuncs.spottedSoFar;
		epoch_possibleSpot += GlobalFuncs.maxPossibleSpots;
		
		GlobalFuncs.iterationCount++;
		
		if (GlobalFuncs.newEpoch){
			

			String debugOutput = GlobalFuncs.currentPop.epoch();
			PrintSummaryOutput();
			GUI_NB.GCO(debugOutput);
			GlobalFuncs.curEpoch++;
			GUI_NB.GCO("Starting NEW EPOCH: #" + GlobalFuncs.curEpoch);
			FIO.appendFile(GlobalFuncs.detailedOutput, "New Epoch: #" + GlobalFuncs.curEpoch);
			FIO.appendFile(GlobalFuncs.detailedOutput, debugOutput);
			GlobalFuncs.currentRunsPerOrg = 0;
			GlobalFuncs.orgAssignNum = 0;
			death_count = 0;
			death_sum = 0.0;
			epoch_spotted = 0.0;
			epoch_possibleSpot = 0;
			
			
			// Randomly chooses a COA
			if (!GlobalFuncs.randCOAEpoch) {
				GlobalFuncs.COAIndex = GlobalFuncs.randRange(1, GlobalFuncs.allCOAs.size());
				GlobalFuncs.curCOA = GlobalFuncs.allCOAs.elementAt(GlobalFuncs.COAIndex - 1);
			}
		}
		
		if (GlobalFuncs.pauseNewIter || (GlobalFuncs.pauseNewEpoch && GlobalFuncs.newEpoch)) {
			// Pause at these iterations; since scenario already paused, will do so
		}
		else {
			if (GlobalFuncs.curEpoch > GlobalFuncs.maxEpochs) {
				// Pause when max epochs exceeded
			}
			else {
				ClockControl.SetPaused(false);	// Unpauses the scenario
			}
		}
		
		GlobalFuncs.spottedSoFar = 0;
		GlobalFuncs.maxPossibleSpots = 0;
		Clock.time = 0;
		
		if (GlobalFuncs.calcShared) GlobalFuncs.scenMap.resetSpotCounts();
		ScenIterationSetup(GlobalFuncs.numScoutsPer);
	}
	
	public static void FillScoutsRandomly(int numScouts) {
		if (GlobalFuncs.currentPop == null) {
			GUI_NB.GCO("ERROR!  Must generate a population first.");
			return;
		}
		
		for (int i = 0; i < numScouts; i++) {
			Unit finger = GlobalFuncs.friendlyUnitList.elementAt(i);
			Organism org = GlobalFuncs.currentPop.organisms.elementAt(GlobalFuncs.randRange(0, GlobalFuncs.currentPop.organisms.size() - 1));
			GUI_NB.GCO("Assiging organism #" + org.genome.genome_id + " to friendly unit " + finger.callsign);
			finger.org = org;
		}
	}
	
	/** Sequentially fill friendly units with Neural Nets from the population */
	public static void FillAllScouts() {
		if (GlobalFuncs.currentPop == null) {
			GUI_NB.GCO("ERROR!  Must generate a population first.");
			return;
		}		
		
		if (GlobalFuncs.currentPop.organisms.size() < GlobalFuncs.friendlyUnitList.size()) {
			GUI_NB.GCO("ERROR!  Not enough organisms to fill friendly units!");
			return;
		}
		
		int numOrgs = GlobalFuncs.currentPop.organisms.size();
		for (int i = 0; i < GlobalFuncs.friendlyUnitList.size(); i++) {
			Unit finger = GlobalFuncs.friendlyUnitList.elementAt(i);
			if (GlobalFuncs.orgAssignNum >= numOrgs) {
				
				GlobalFuncs.orgAssignNum = 0;
				GlobalFuncs.currentRunsPerOrg++;
				GUI_NB.GCO("Current runs per Org inc to: " + GlobalFuncs.currentRunsPerOrg);
			}
			
			if (GlobalFuncs.currentRunsPerOrg >= GlobalFuncs.maxRunsPerOrg && !GlobalFuncs.newEpoch) {
				GlobalFuncs.newEpoch = true;
				GUI_NB.GCO("Last iteration of the current epoch.  CurRuns: " + GlobalFuncs.currentRunsPerOrg + " out of max: " + GlobalFuncs.maxRunsPerOrg);
			}
			
			Organism org = GlobalFuncs.currentPop.organisms.elementAt(GlobalFuncs.orgAssignNum);
			GlobalFuncs.orgAssignNum++;
			
			//GUI_NB.GCO("Assiging organism #" + org.genome.genome_id + " to friendly unit " + finger.callsign);
			finger.org = org;
		}				
	}
	
	/** Deploy all friendly units according to their neural nets */
	public static void DeployAll() {
		for (int i = 0; i < GlobalFuncs.friendlyUnitList.size(); i++) {
			Unit finger = GlobalFuncs.friendlyUnitList.elementAt(i);
			
			unit.JNEATIntegration.DeployOne(finger);
		}
	}
	
	public static void DeployRandomly(Unit u) {
		Hex destination = GlobalFuncs.scenMap.RandomHexReconZone();
		
		while (destination.HexUnit != null) {
			destination = GlobalFuncs.scenMap.RandomHexReconZone();			
		}
		u.TeleportTo(destination);			
	}
	
	public static void DeployMaxView(Unit u, int power) {
		Hex destination = null;
		double maxView = 0;
		
		// Takes 50 samples
		for (int i = 0; i < 50; i++) {
			Hex finger = GlobalFuncs.scenMap.RandomHexReconZone();
			if (finger.HexUnit == null) {
				
				Vector<Hex> theList = Unit.GetLOSToRange(finger, GlobalFuncs.visibility);
				
				double viewage = 0.0;
				if (GlobalFuncs.calcShared){
					for (int j = 0; j < theList.size(); j++) {
						Hex f = theList.elementAt(j);
						viewage += (1.0 / (f.numSpots * power));
					}
				}
				else {
					viewage = theList.size();
				}
				
				if (viewage > maxView) {
					maxView = viewage;
					destination = finger;
				}
			}
			else i--;
		}
		
		u.TeleportTo(destination);
		if (GlobalFuncs.calcShared) {
			u.emplaced = true;
			u.UpdateSharedSpotting();
		}
		//GUI_NB.GCO("Placing " + u.callsign + " with maxView " + maxView);
	}
			
	/** Based on sensor evaluations, determine where to deploy this unit */
	public static void DeployOne(Unit u) {
		if (u.org == null) {
			GUI_NB.GCO("ERROR!  Unit " + u.callsign + " does not have an organism!");
			return;
		}
		
		if (u.orgType == OrganismTypeEnum.BASE_RANDOM) {
			DeployRandomly(u);
			return;
		}
		
		if (u.orgType == OrganismTypeEnum.BASE_MAXHEX) {
			DeployMaxView(u, 1);
			return;
		}
		
		boolean foundSpot = false;
		int errCount = 0;
		double resultThreshold = GlobalFuncs.networkResultThreshold;
		int maxErrCount = 30;
		
		// Cycle through hex locations
		while (!foundSpot) {
			errCount++;
					
			Hex prospective = GlobalFuncs.scenMap.RandomHexReconZone();
			
			// Only chooses unoccupied hexes
			if (prospective.HexUnit == null) {
				// Uses the units' respective sensor function
				double networkResult = u.UseSensors(prospective);
												
				//GUI_NB.GCO("Prospective hex: (" + prospective.x + ", " + prospective.y + ") is input: " + sensorInput + " with output: " + networkResult);
				
				if (networkResult > resultThreshold) {
					//GUI_NB.GCO("Location accepted. Teleporting unit.");					
					u.TeleportTo(prospective);
					
					if (GlobalFuncs.calcShared) {
						u.emplaced = true;
						u.UpdateSharedSpotting();
					}					
					
					//u.DisplayLOSToRange(GlobalFuncs.visibility);
					foundSpot = true;
				}
			}
			
			if (errCount > maxErrCount) {
				resultThreshold -= 0.05;
				errCount = 0;
			}
		}
	}



	/** Prints the population summary for each epoch to file */
	public static void PrintSummaryOutput() {
		FIO.appendFile(GlobalFuncs.summaryOutput, PrintSummaryLine());
	}
	
	public static void PrintTestSummary() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append(GlobalFuncs.currentTestPath.toString() + ", ");
		buf.append("COA " + GlobalFuncs.curCOA.name + ", ");
		buf.append(GlobalFuncs.currentPopEpochNum + ", ");
		buf.append((death_sum / death_count) + ", ");
		buf.append((epoch_spotted / epoch_possibleSpot));
		
		FIO.appendFile(GlobalFuncs.summaryOutput, buf.toString());
		
	}
	
	public static String PrintTestSummaryKey() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("Scenario, ");
		buf.append("COA, ");		
		buf.append("Generation, ");
		buf.append("Avg Death Rate, ");
		buf.append("Avg Team Performance");
		
		return buf.toString();
	}
	
	public static void PrintTestDetailed() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append(GlobalFuncs.currentTestPath.toString() + ", ");
		buf.append("COA " + GlobalFuncs.curCOA.name + ", ");
		buf.append(GlobalFuncs.currentPopEpochNum + ", ");
		buf.append((death_sum / death_count) + ", ");
		buf.append((epoch_spotted / epoch_possibleSpot));
		
		FIO.appendFile(GlobalFuncs.summaryOutput, buf.toString());
		
	}
	
	public static String PrintTestDetailedKey() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("Scenario, ");
		buf.append("COA, ");
		buf.append("Generation, ");
		buf.append("Avg Death Rate, ");
		buf.append("Avg Team Performance");
		
		return buf.toString();
	}
	
	public static String PrintSummaryLine() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append(GlobalFuncs.curEpoch + ", ");
		buf.append(GlobalFuncs.currentPop.organisms.size() + ", ");
		buf.append(GlobalFuncs.currentPop.species.size() + ", ");
		buf.append(GlobalFuncs.currentPop.mean_fitness + ", ");
		buf.append(GlobalFuncs.currentPop.max_fitness_this_epoch + ", ");
		buf.append(GlobalFuncs.currentPop.avg_fit_eliminated + ", ");
		buf.append((death_sum / death_count) + ", ");
		buf.append((epoch_spotted / epoch_possibleSpot));
		
		return buf.toString();
	}
	
	public static String PrintSummaryKey() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("Epoch #, ");
		buf.append("Pop Size, ");
		buf.append("Num Species, ");
		buf.append("Avg Fitness, ");
		buf.append("Max Fitness, ");
		buf.append("Avg Fit Eliminated, ");
		buf.append("Avg Death Rate, ");
		buf.append("Overall Team Performance");
		
		return buf.toString();
	}
	
	

	
	/** Prints the iteration results from this iteration to the iteration detail file */
	public static void PrintDetailedIter() {
		
		for (int i = 0; i < GlobalFuncs.friendlyUnitList.size(); i++) {
			Unit finger = GlobalFuncs.friendlyUnitList.elementAt(i);
			FIO.appendFile(GlobalFuncs.fullIterOutput, PrintDetailedItrLine(finger));
		}
		
		for (int i = 0; i < GlobalFuncs.destroyedUnitList.size(); i++) {
			Unit finger = GlobalFuncs.destroyedUnitList.elementAt(i);
			FIO.appendFile(GlobalFuncs.fullIterOutput, PrintDetailedItrLine(finger));
		}
		
	}
	
	public static String PrintDetailedIterKey() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("Iteration #, ");
		buf.append("Epoch #, ");
		buf.append("COA Name, ");
		buf.append("Organism #, ");
		buf.append("Unit Callsign, ");
		buf.append("Unit Coordinates, ");
		buf.append("Unit destroyed, ");
		buf.append("Spotting credits, ");
		buf.append("Final fitness, ");
		buf.append("Organism averaged fitness, ");
		buf.append("Organism number of averages, ");
		buf.append("Team performance");
		
		return buf.toString();
	}
	
	/** Puts it all on one line */
	public static String PrintDetailedItrLine(Unit u) {
		StringBuffer buf = new StringBuffer("");
		
		buf.append(GlobalFuncs.iterationCount + ", ");
		buf.append(GlobalFuncs.curEpoch + ", ");
		buf.append(GlobalFuncs.curCOA.name + ", ");
		buf.append(u.org.genome.genome_id + ", ");
		buf.append(u.callsign + ", ");
		buf.append(u.location.DisplayCoords() + ", ");
		buf.append(u.destroyed + ", ");
		buf.append(u.spotCredits + ", ");
		buf.append(u.spotCredits / GlobalFuncs.maxPossibleSpots + ", ");
		buf.append(u.org.fitness + ", ");
		buf.append(u.org.fitAveragedOver + ", ");
		buf.append(((double)GlobalFuncs.spottedSoFar / GlobalFuncs.maxPossibleSpots));
		
		return buf.toString();
		
	}
	
	public static void ScenIterationSetup() {
		DialogFileName x = new DialogFileName(GlobalFuncs.gui, true, "Num Friendly Units");
		x.setVisible(true);
		
		int friendlyUnits = Integer.parseInt(GlobalFuncs.tempStr);
		
		if (friendlyUnits < 1) {
			GUI_NB.GCO("ERROR!  Not a valid number.");
		}		
		else {
			JNEATIntegration.ScenIterationSetup(friendlyUnits);
		}
	}
	
	public static void ClearAllData() {
		GlobalFuncs.allCOAs.clear();
		GlobalFuncs.unitList.clear();
		GlobalFuncs.allSpots.records.clear();
		GlobalFuncs.currentPop = null;
		
	}
	
	public static void RunNextTest() {
		//testNumber = 5;
		
		switch (testNumber) {
		case 1:
			PopTestOn(GlobalFuncs.numScoutsPer, GlobalFuncs.numTests, "s41.scen");
			break;		
		case 2:
			PopTestOn(GlobalFuncs.numScoutsPer, GlobalFuncs.numTests, "testSP1.scen");
			break;
		case 3:
			PopTestOn(GlobalFuncs.numScoutsPer, GlobalFuncs.numTests, "testD2.scen");
			break;
		default:
			GUI_NB.GCO("Out of tests!");
			break;
		}	
	}
	
	public static void PopTestFromDir(int numScouts, int numTestsPer) {
		for (int i = 1; i < 51; i++) {
			FIO.CopyPop(i);
		}
		
		ClockControl.SetTimeScale((byte) 11);
		ClockControl.SetPaused(true);
		GlobalFuncs.displayMiniMap = true;
		
    	//GlobalFuncs.summaryOutput = new File("src/saves/" + GlobalFuncs.outputPrefix + "/Summary.txt").toPath();    	
    	//FIO.newFile(GlobalFuncs.summaryOutput.toString());
    	//FIO.overwriteFile(GlobalFuncs.summaryOutput, PrintTestSummaryKey());
		
		PopTestOn(numScouts, numTestsPer, "s51.scen");					
	}
	
    public static void PopTestOn(int numScouts, int numTestsPer, String scen) {
    	String scenFullPath = "src/saves/" + scen;

    	
		GUI_NB.GCO("Attempted to load with " + numScouts + " scouts and " + numTestsPer + " iterations.");
		GlobalFuncs.numTests = numTestsPer;
    
		GUI_NB.GCO("Now attempting to load scenario s22.scen");
		GlobalFuncs.currentTestPath = new File(scenFullPath).toPath();
		FIO.LoadTest(GlobalFuncs.currentTestPath);
		
		GlobalFuncs.currentPopEpochNum = 1;
		GUI_NB.GCO("Loading population data.");
		
		GlobalFuncs.targetPop = FIO.PoppyCock(GlobalFuncs.currentPopEpochNum);
		GlobalFuncs.currentPop = new Population(GlobalFuncs.targetPop);		
		GUI_NB.GCO("Population data read.  Initializing scenario with " + GlobalFuncs.currentPop.organisms.size() + " orgs");
		
    	switch (GlobalFuncs.currentPop.organisms.get(0).net.inputs.size()) {
    	case 1:
    		GlobalFuncs.defaultOrgType = OrganismTypeEnum.SIMPLE_SINGLE;
    		break;
    	case 2:
    		GlobalFuncs.defaultOrgType = OrganismTypeEnum.SIMPLE_DUAL;
    		break;
    	case 4:
    		GlobalFuncs.defaultOrgType = OrganismTypeEnum.BASE_MAXHEX;
    		break;
    	case 3:
    		GlobalFuncs.defaultOrgType = OrganismTypeEnum.BASE_RANDOM;
    		break;
    	case 7:
    		GlobalFuncs.defaultOrgType = OrganismTypeEnum.SIX_DIRECTIONAL;
    		break;
		default:
			GlobalFuncs.defaultOrgType = OrganismTypeEnum.SIMPLE_SINGLE;
			GUI_NB.GCO("ERROR!  Could not determine organism type based on population file.  Defaulting to simple single.");
			break;    			
    	}
		
		GlobalFuncs.newEpoch = true;				
		
		GlobalFuncs.COAIndex = 1;
		JNEATIntegration.TestIterationSetup(numScouts);
		
		// GUI_NB.GCO("Now initiating stuff.");
		//TestIterationSetup(numScouts);		
    }
    
    public static void PopTestNewEpoch(int newEpoch) {
    	GlobalFuncs.currentPopEpochNum = newEpoch;
    	GlobalFuncs.targetPop = FIO.PoppyCock(GlobalFuncs.currentPopEpochNum);
    	GlobalFuncs.currentPop = new Population(GlobalFuncs.targetPop);
    	GUI_NB.GCO("Population data read.  Initializing scenario with " + GlobalFuncs.currentPop.organisms.size() + " orgs");
    	
    	GlobalFuncs.newEpoch = true;
    	GlobalFuncs.COAIndex = 1;
    	JNEATIntegration.TestIterationSetup(GlobalFuncs.numScoutsPer);
    	
    	ClockControl.SetPaused(false);
    }
    

	
	public static void ScenIterationFromFile() {
		String fullPath = "src/saves/" + GlobalFuncs.tempStr;
		File popFile = FIO.newFile(fullPath);
		if (!popFile.exists()) {
			GUI_NB.GCO("Error reading population file!");
			
			DialogLoadScen x = new DialogLoadScen(GlobalFuncs.gui, true);
			x.setVisible(true);
		}
		else {
			
			GUI_NB.GCO("Loading population data.");
			Path p = popFile.toPath();
			GlobalFuncs.currentPop = new Population(p);
			GUI_NB.GCO("Population data read.  Initializing scenario with " + GlobalFuncs.currentPop.organisms.size() + " orgs");
			GlobalFuncs.newEpoch = true;
			
			int numScouts = GlobalFuncs.currentPop.PopulationSlice(GlobalFuncs.percentPerRun);
			
			JNEATIntegration.ScenIterationSetup(numScouts);
		}
	}
	
	
	public static void TestIterationSetup(int numScouts) {
		GlobalFuncs.allSpots.records.clear();
		GlobalFuncs.numScoutsPer = numScouts;
		
		// Sequentially choose a COA
		GlobalFuncs.curCOA = GlobalFuncs.allCOAs.elementAt(GlobalFuncs.COAIndex - 1);
		GlobalFuncs.curCOA.LoadCOA();
		
		if (numScouts == 0) return;
		
		GUI_NB.GCO("New test iteration: Populating " + numScouts + " scouts of pop size " + GlobalFuncs.currentPop.organisms.size());
		
		// First, eliminate friendly units from the unit roster
		for (int i = 0; i < GlobalFuncs.friendlyUnitList.size(); i++) {
			Unit finger = GlobalFuncs.friendlyUnitList.elementAt(i);
			if (finger.emplaced) finger.RemoveSharedSpotting(); 
			finger.location.HexUnit = null;
			GlobalFuncs.unitList.remove(finger);					
		}
		GlobalFuncs.friendlyUnitList.clear();
		GlobalFuncs.destroyedUnitList.clear();	// These are already gone from the map from the last run.
				
		// Now adds units along the left map boundary
		for (int i = 0; i < numScouts; i++) {
			int col = i / GlobalFuncs.scenMap.yDim;
			int row = i % GlobalFuncs.scenMap.yDim;
			
			Hex destination = GlobalFuncs.scenMap.getHex(col, row);
			if (destination.HexUnit != null) {
				// GUI_NB.GCO("Destination hex occupied, moving to the next one.");
				numScouts++;
			}
			else {
				// Add friendly unit
				destination.HexUnit = new Unit(destination, SideEnum.FRIENDLY, "M1A2", "Scout " + i, 90.0, 0.0, null, true);
			}
		}
		

		FillScoutsRandomly(numScouts);		// Puts a Org in each unit
		
		DeployAll();			// Deploys those units accordingly
		GUI_NB.GCO(">>>> Iteration: " + GlobalFuncs.currentPopEpochNum);
		
		GlobalFuncs.gui.repaint();
		if (ClockControl.paused) ClockControl.Pause();
		
	}

	/** Goes through the setup for this scenario, namely, for the current COA will initialize new units*/
	public static void ScenIterationSetup(int numScouts) {		

		GlobalFuncs.numScoutsPer = numScouts;
		GlobalFuncs.allSpots.records.clear();
		//GUI_NB.GCO("All spot records have been cleared.");
		
		// Randomly chooses a COA
		if (GlobalFuncs.randCOAEpoch) {
			GlobalFuncs.COAIndex = GlobalFuncs.randRange(0, GlobalFuncs.allCOAs.size() - 1);
			GlobalFuncs.curCOA = GlobalFuncs.allCOAs.elementAt(GlobalFuncs.COAIndex);
		}
				
		GlobalFuncs.curCOA.LoadCOA();
		if (numScouts == 0) return;
		
		GUI_NB.GCO("New scenario iteration: Populating " + numScouts + " scouts of pop size " + GlobalFuncs.currentPop.organisms.size());
		
		
		
		// First, eliminate friendly units from the unit roster
		for (int i = 0; i < GlobalFuncs.friendlyUnitList.size(); i++) {
			Unit finger = GlobalFuncs.friendlyUnitList.elementAt(i);
			if (finger.emplaced) finger.RemoveSharedSpotting(); 
			finger.location.HexUnit = null;
			GlobalFuncs.unitList.remove(finger);					
		}
		GlobalFuncs.friendlyUnitList.clear();
		GlobalFuncs.destroyedUnitList.clear();	// These are already gone from the map from the last run.
		

		
		// Now adds units along the left map boundary
		for (int i = 0; i < numScouts; i++) {
			int col = i / GlobalFuncs.scenMap.yDim;
			int row = i % GlobalFuncs.scenMap.yDim;
			
			Hex destination = GlobalFuncs.scenMap.getHex(col, row);
			if (destination.HexUnit != null) {
				// GUI_NB.GCO("Destination hex occupied, moving to the next one.");
				numScouts++;
			}
			else {
				// Add friendly unit
				destination.HexUnit = new Unit(destination, SideEnum.FRIENDLY, "M1A2", "Scout " + i, 90.0, 0.0, null, true);
			}
		}		 
		
		// GUI_NB.GCO("Num friendlies: " + GlobalFuncs.friendlyUnitList.size());
		
		
		if (GlobalFuncs.newEpoch) {
			GUI_NB.GCO("New epoch: outputting network information to: " + GlobalFuncs.detailedOutput.toString());
			
			File netInfo = FIO.newFile("src/saves/" + GlobalFuncs.outputPrefix + "/" + GlobalFuncs.outputPrefix + "pop" + GlobalFuncs.curEpoch + ".pop");
			GlobalFuncs.currentPop.SavePopulationToFile(netInfo.toPath());
			
			//FIO.appendFile(GlobalFuncs.detailedOutput, "\n\n\n---->>> NEW EPOCH at iteration " + GlobalFuncs.iterationCount + "<<<----\n\n\n");
			//FIO.appendFile(GlobalFuncs.detailedOutput, GlobalFuncs.currentPop.SavePopulation());
			//FIO.appendFile(GlobalFuncs.detailedOutput, GlobalFuncs.currentPop.PrintPopulation());
			GlobalFuncs.newEpoch = false;
		}
				
		FillAllScouts();		// Puts a Org in each unit
		
		DeployAll();			// Deploys those units accordingly
		
		GUI_NB.GCO(">>>> Iteration: " + GlobalFuncs.iterationCount + " of epoch " + GlobalFuncs.curEpoch);
		
		
		GlobalFuncs.gui.repaint();
	}

}
