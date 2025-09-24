// File: Experiment.java
// Description:

import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class SMThemeParkExperi1 {
	public static void main(String[] args) {
		double startTime = 0.0, endTime = 750.0;
		SMThemePark park; // Simulation object
		int NUMRUNS = 1; // This determines how many simulations to run. New seeds are used for each run
		int[] boardingOptions = new int[] { Constants.SINGLE_SIDED, Constants.DOUBLE_SIDED, Constants.SINGLE_SIDED, Constants.DOUBLE_SIDED }; // boarding options in 4 cases
		boolean[] fixBoardingTime = new boolean[] { true, true, false, false }; // fix_boarding_time options in 4 cases

		// Lets get a set of uncorrelated seeds
		Seeds[] sds = new Seeds[NUMRUNS];
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		for (int i = 0; i < NUMRUNS; i++) {
			sds[i] = new Seeds(rsg);
		}

		// Number of Seeds
		for (int i = 0; i < NUMRUNS; i++) {
			Seeds seed = sds[i];
			int runNumber = 1;
			System.out.println("\n\n======================================================");
			System.out.println("=======================SEED #" + i + "========================");
			System.out.println("======================================================");

			// For Each Case Scenario Within Cars Within Trains
			for (int x = 0; x < boardingOptions.length; x++) {
				System.out.println("\n");
				// For Each Train Scenario Within Seeds
				traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
					// For Each Car Scenario Within Train
					for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS * numTrains; numCars++) {
						System.out.println("==========Case " + (x + 1) + ", Seed " + i + ", Run Number " + runNumber + "==========");
						System.out.println("Number of Cars: " + numCars + " Number of Trains: " + numTrains);
						park = new SMThemePark(startTime, endTime, numTrains, numCars, boardingOptions[x], fixBoardingTime[x], seed, false);
						park.runSimulation();
						park.outputResults();
						runNumber++;
						if (park.projectGoalReached()) {
							break traincarloop;
						}
					}
				}
			}
		}
	}
}
