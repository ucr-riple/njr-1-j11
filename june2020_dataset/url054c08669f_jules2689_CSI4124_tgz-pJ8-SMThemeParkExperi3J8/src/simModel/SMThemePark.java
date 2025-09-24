package simModel;

import absmodJ.AOSimulationModel;
import absmodJ.Behaviour;

// The Simulation model Class
import absmodJ.ExtSequelActivity;
import absmodJ.SBNotice;
import absmodJ.ScheduledAction;
import absmodJ.SequelActivity;

public class SMThemePark extends AOSimulationModel {
	// Constants available from Constants class
	/* Parameter */
	// Define the parameters
	protected int numberOfTrains; // number of trains, from 4-8
	protected int totalNumberOfCars; // total number of cars
	protected int boardingOption; // 0:single-sided; 1:double_sided
	protected boolean fixBoardingTime; // true:fix boarding time

	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	// Define the reference variables to the various entities with scope Set and
	// Unary Objects can be created here or in the Initialize Action
	Tracks[] rqTracks = new Tracks[4]; // RQ.Tracks
	Stations[] gStations = new Stations[4]; // G.Stations
	Trains[] rcgTrains; // RCG.Trains

	/* Input Variables */
	// Define any Independent Input Variables here

	// References to RVP and DVP objects
	protected RVPs rvp; // Reference to rvp object - object created in
						// constructor
	protected DVPs dvp = new DVPs(this); // Reference to dvp object
	protected UDPs udp = new UDPs(this);

	// Output object
	public Output output = new Output(this);

	// Output values - define the public methods that return values required for
	// experimentation.

	// Other values closing time of the park
	protected double closingTime;

	// Constructor
	public SMThemePark(double t0time, double tftime, int nTrains, int nCars,
			int boardingOption, boolean fixBoardingTime, Seeds sd,
			boolean traceFlag) {
		// Turn trancing on if traceFlag is true
		this.traceFlag = traceFlag;

		// Initialize parameters here
		this.numberOfTrains = nTrains;
		this.totalNumberOfCars = nCars;
		this.boardingOption = boardingOption;
		this.fixBoardingTime = fixBoardingTime;

		// Create RVP object with given seed
		rvp = new RVPs(this, sd);

		// Initial four station
		String[] names = new String[] { Constants.FP_S, Constants.SH_S,
				Constants.GI_S, Constants.RC_S };
		for (int i = 0; i < Constants.NUM_STATIONS; i++) {
			this.gStations[i] = new Stations(names[i]);
		}
		// Initial tracks
		for (int i = 0; i < this.rqTracks.length; i++) {
			this.rqTracks[i] = new Tracks();
		}

		closingTime = tftime - 30; // record the closing time. the closing time
									// is 30 minutes before the end of the
									// simulation.
		// Initialize the simulation model
		initAOSimulModel(t0time, tftime);

		Initialise init = new Initialise(this);
		scheduleAction(init); // Should always be first one scheduled.
		// Schedule other scheduled actions and activities here

		// Schedule the first arrivals
		ArriveAtStationFP arrivalFP = new ArriveAtStationFP(this);
		scheduleAction(arrivalFP); // customer arrive at FP

		ArriveAtStationGI arrivalGI = new ArriveAtStationGI(this);
		scheduleAction(arrivalGI); // customer arrive at GI

		ArriveAtStationRC arrivalRC = new ArriveAtStationRC(this);
		scheduleAction(arrivalRC); // customer arrive at RC

		ArriveAtStationSH arrivalSH = new ArriveAtStationSH(this);
		scheduleAction(arrivalSH); // customer arrive at SH
	}

	public SMThemePark() {
	}

	/************ Implementation of Data Modules ***********/
	/*
	 * Testing preconditions
	 */
	@Override
	protected void testPreconditions(Behaviour behObj) {

		// Check preconditions of Conditional Activities
		reschedule(behObj);

		// test for unboarding and boarding
		if (UnBoardingAndBoarding.precondition(this) == true) {
			UnBoardingAndBoarding act = new UnBoardingAndBoarding(this); // Generate
																			// instance
			act.startingEvent();
			scheduleActivity(act);
		}

		// Check preconditions of Interruptions in Extended Activities
		int num; // number of entries in list
		int interruptionNum; // interruption number
		SBNotice nt;
		Behaviour obj;
		num = esbl.size();

		for (int i = 0; i < num; i++) {
			nt = esbl.get(i);
			obj = (esbl.get(i)).behaviourInstance;

			if (ExtSequelActivity.class.isInstance(obj)) {
				ExtSequelActivity extSeqObj = (ExtSequelActivity) nt.behaviourInstance;
				interruptionNum = extSeqObj.interruptionPreCond();
				if (interruptionNum != 0) {
					extSeqObj.interruptionSCS(interruptionNum);
					unscheduleBehaviour(nt);
					i--;
					num--;
				}
			}
		}
	}

	// Flag for controlling tracing
	boolean traceFlag = false;

	protected void eventOccured() {
		if (traceFlag) {
			// only print the trace when there isn't an arrival because too many
			// arrivals
			if (!ScheduledAction.class
					.isInstance(this.sbl.peek().behaviourInstance)) {
				System.out.println("Clock: " + getClock() + ": ");
				for (int i = 0; i < Constants.NUM_STATIONS; i++) {
					System.out.println(gStations[i].name + " numCustomers="
							+ gStations[i].numCustomers);
					System.out.println(gStations[i].name + "'s Track size="
							+ this.rqTracks[i].getN());
					// look at the trains on the track for the station
					for (int j = 0; j < this.rqTracks[i].getN(); j++) {
						Trains t = rcgTrains[rqTracks[i].trainList.get(j)];
						System.out.println("Train " + j + " numCustomers="
								+ t.numCustomers + " status=" + t.status
								+ " numLeavingCustomers="
								+ t.numLeavingCustomers[i]);
					}
					System.out.println();
				}

				// print number of events
				System.out.println("Number of Type 1 Events: "
						+ output.getType1BoardingEvent());
				System.out.println("Number of Type 2 Events: "
						+ output.getType2BoardingEvent());
				System.out.println("Number of Type 3 Events: "
						+ output.getType3BoardingEvent());
				System.out.println("Number of Type 4 Events: "
						+ output.getType4BoardingEvent() + "\n");

				this.showSBL();
			}

		}
	}

	// Standard Procedure to start Sequel Activities with no parameters
	protected void spStart(SequelActivity seqAct) {
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}

	@Override
	protected double getClock() {
		return super.getClock();
	}

	public boolean projectGoalReached() {
		boolean result = false;
		if (this.output.getPerctOfType4Scen() == 0.0
				&& this.output.getPerctOfType3Scen() <= 5.0
				&& this.output.getPerctOfType2Scen() <= 10.0) {
			result = true;
		}
		return result;
	}

	public void outputResults() {
		System.out.println("Percentage of Type 1 Events: "
				+ output.getPerctOfType1Scen());
		System.out.println("Percentage of Type 2 Events: "
				+ output.getPerctOfType2Scen());
		System.out.println("Percentage of Type 3 Events: "
				+ output.getPerctOfType3Scen());
		System.out.println("Percentage of Type 4 Events: "
				+ output.getPerctOfType4Scen());

		System.out.println("Cost: " + output.getCost());
	}

}
