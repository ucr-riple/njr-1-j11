// File: Experiment.java
// Description:

import absmodJ.ConfidenceInterval;
import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class SMThemeParkExperi2 {
	public static final double CONF_LEVEL = 0.967; // 1-((1-0.9)/3)

	public static void main(String[] args) {
		double startTime = 0.0, endTime = 750.0;
		SMThemePark park; // Simulation object
		int NUMRUNS = 30;
		int[] boardingOptions = new int[] { Constants.SINGLE_SIDED,
				Constants.DOUBLE_SIDED, Constants.SINGLE_SIDED,
				Constants.DOUBLE_SIDED }; // boarding options in 4 cases
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
		System.out.println("Confidence Level: "+ CONF_LEVEL);
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
				valuesDiff21ForCosts, CONF_LEVEL);
		ConfidenceInterval cfDiff31ForCosts = new ConfidenceInterval(
				valuesDiff31ForCosts, CONF_LEVEL);
		ConfidenceInterval cfDiff41ForCosts = new ConfidenceInterval(
				valuesDiff41ForCosts, CONF_LEVEL);

		TableHelper.printTable(cfDiff21ForCosts, cfDiff31ForCosts,
				cfDiff41ForCosts);
		
		System.out.println("\nCase Results (y(n)): \n");
		for (int i = 0; i < 4; i++) {
			ConfidenceInterval carsInterval = new ConfidenceInterval(
					valuesCasesForCars[i], CONF_LEVEL), trainsInterval = new ConfidenceInterval(
					valuesCasesForTrains[i], CONF_LEVEL);
			int cars = (int) Math.ceil(carsInterval.getPointEstimate());
			int trains = (int) Math.ceil(trainsInterval.getPointEstimate());
			System.out.println("Case " + (i + 1) + " - Trains: " + trains
					+ " Cars: " + cars);
		}
	}
}
