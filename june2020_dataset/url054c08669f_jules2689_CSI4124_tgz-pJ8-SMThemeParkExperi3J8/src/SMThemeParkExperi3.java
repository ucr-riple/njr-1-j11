// File: Experiment.java
// Description:

import java.util.Arrays;

import absmodJ.ConfidenceInterval;
import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class SMThemeParkExperi3 {
	// Confidence levels - first one is the overall confidence level.
	// The other are Ck's and one more must be calculated to ensure the overall
	// CF
	public static double[] cfLevels = { 0.90, 0.99, 0.92 };

	public static void main(String[] args) {
		double startTime = 0.0, endTime = 750.0;
		SMThemePark park; // Simulation object
		int NUMRUNS =30000;
		int[] boardingOptions = new int[] { Constants.SINGLE_SIDED,
				Constants.DOUBLE_SIDED, Constants.SINGLE_SIDED,
				Constants.DOUBLE_SIDED }; // boarding options in
		boolean[] fixBoardingTime = new boolean[] { true, true, false, false }; // fix_boarding_time
																				// options
																				// in
																				// 4
																				// cases

		// Lets get a set of uncorrelated seeds
		Seeds[] sds = new Seeds[NUMRUNS];
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		for (int i = 0; i < NUMRUNS; i++) {
			sds[i] = new Seeds(rsg);
		}
		// The other are Ck's and one more must be calculated to ensure the
		// overall CF

		double[] confLevels = computeLastCFLevel(cfLevels);

		// Train Config
		double[][] valuesCasesForTrains = new double[4][NUMRUNS];

		// Cars Config
		double[][] valuesCasesForCars = new double[4][NUMRUNS];

		// Costs Config
		double[][] valuesCasesForCosts = new double[4][NUMRUNS];
		double[] valuesDiff21ForCosts = new double[NUMRUNS];
		double[] valuesDiff31ForCosts = new double[NUMRUNS];
		double[] valuesDiff41ForCosts = new double[NUMRUNS];

		System.out.println("Running "+ NUMRUNS +" seeds.");
		System.out.println("Confidence Levels: "+ confLevels);
		System.out.println("\nPlease note that a large number of seeds may take a few hours to run.");
		
		// Number of Seeds
		for (int i = 0; i < NUMRUNS; i++) {
			Seeds seed = sds[i];
			// For Each Case Scenario Within Cars Within Trains

			// case 1
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS
						* numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains,
							numCars, boardingOptions[0], fixBoardingTime[0],
							seed, false);
					park.runSimulation();
					if (park.projectGoalReached()) {
						valuesCasesForCosts[0][i] = park.output.getCost();
						valuesCasesForCars[0][i] = numCars;
						valuesCasesForTrains[0][i] = numTrains;
						break traincarloop;
					}
				}
			}

			// case 2
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS
						* numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains,
							numCars, boardingOptions[1], fixBoardingTime[1],
							seed, false);
					park.runSimulation();
					if (park.projectGoalReached()) {
						valuesCasesForCosts[1][i] = park.output.getCost();
						valuesCasesForCars[1][i] = numCars;
						valuesCasesForTrains[1][i] = numTrains;

						valuesDiff21ForCosts[i] = valuesCasesForCosts[1][i]
								- valuesCasesForCosts[0][i];
						break traincarloop;
					}
				}
			}

			// case 3
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS
						* numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains,
							numCars, boardingOptions[2], fixBoardingTime[2],
							seed, false);
					park.runSimulation();
					if (park.projectGoalReached()) {
						valuesCasesForCosts[2][i] = park.output.getCost();
						valuesCasesForCars[2][i] = numCars;
						valuesCasesForTrains[2][i] = numTrains;

						valuesDiff31ForCosts[i] = valuesCasesForCosts[2][i]
								- valuesCasesForCosts[0][i];
						break traincarloop;
					}
				}
			}

			// case 4
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS
						* numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains,
							numCars, boardingOptions[3], fixBoardingTime[3],
							seed, false);
					park.runSimulation();
					if (park.projectGoalReached()) {
						valuesCasesForCosts[3][i] = park.output.getCost();
						valuesCasesForCars[3][i] = numCars;
						valuesCasesForTrains[3][i] = numTrains;

						valuesDiff41ForCosts[i] = valuesCasesForCosts[3][i]
								- valuesCasesForCosts[0][i];
						break traincarloop;
					}
				}
			}
		}

		// Get the confidence intervals

		ConfidenceInterval cfDiff21ForCosts = new ConfidenceInterval(
				valuesDiff21ForCosts, confLevels[1]);
		ConfidenceInterval cfDiff31ForCosts = new ConfidenceInterval(
				valuesDiff31ForCosts, confLevels[2]);
		ConfidenceInterval cfDiff41ForCosts = new ConfidenceInterval(
				valuesDiff41ForCosts, confLevels[3]);

		TableHelper.printTable(cfDiff21ForCosts, cfDiff31ForCosts,
				cfDiff41ForCosts);

		System.out.println("\nCase Results (y(n)): \n");
		for (int i = 0; i < 4; i++) {
			ConfidenceInterval carsInterval = new ConfidenceInterval(
					valuesCasesForCars[i], confLevels[i]), trainsInterval = new ConfidenceInterval(
					valuesCasesForTrains[i], confLevels[i]);
			int cars = (int) Math.ceil(carsInterval.getPointEstimate());
			int trains = (int) Math.ceil(trainsInterval.getPointEstimate());
			System.out.println("Case " + (i + 1) + " - Trains: " + trains
					+ " Cars: " + cars);
		}

	}

	/*
	 * The last level must satisfy the following equation C = (1-K) +
	 * sum_k_1_K(Ck) (sum_k_1_K is sum over terms for k = 1 to K) where C =
	 * levels[0], Ck are the values of levels[i] for i = 1 to levels.length-1 K
	 * = levels.length (note that we add one element, i.e. K =
	 * newLevels.length-1) Can use alternative: C = 1 - sum_k_1_K(alpha_k) where
	 * alpha_k = 1 - Ck Thus C = 1 - sum_k_1_K-1(alpha_k) - alpha_K, and thus
	 * alpha_K = 1 - C - sum_k_1_K-1(alpha_k) (note that alpha_k for k = 1 to
	 * K-1 are known) and CK = 1 - alpha_K
	 */
	public static double[] computeLastCFLevel(double[] levels) {
		double[] newLevels = new double[levels.length + 1];
		double alphaK = 1.0 - levels[0]; // Assign C - 1 to alpha K
		newLevels[0] = levels[0]; // Save C in new array
		// Sum 1 - Ck to alphaK for k = 1 to K - 1
		// At the sam time save values in new array
		for (int k = 1; k < levels.length; k++) {
			alphaK = alphaK - (1 - levels[k]);
			newLevels[k] = levels[k];
		}
		newLevels[newLevels.length - 1] = 1.0 - alphaK;
		// Double check the values
		double sum = 0.0;
		for (int k = 1; k < newLevels.length; k++)
			sum = sum + (1 - newLevels[k]);
		if (Math.abs(newLevels[0] - (1.0 - sum)) > 0.001) {
			System.out.println("Last value invalid in Confidence Levels: "
					+ Arrays.toString(newLevels));
			newLevels = null; // return null value to flag error.
		}
		return (newLevels);
	}
}
