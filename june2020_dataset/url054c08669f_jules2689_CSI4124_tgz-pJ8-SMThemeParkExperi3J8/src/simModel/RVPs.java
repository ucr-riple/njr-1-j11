package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;

class RVPs {
	SMThemePark model; // for accessing the clock
	// Data Models - i.e. random variate generators for distributions
	// are created using Colt classes, define
	// reference variables here and create the objects in the
	// constructor with seeds

	/* Data Models for implementing timing maps */
	protected Exponential interArrDistFP; // Exponential distribution for interarrival times in FP
	protected Exponential interArrDistSH; // Exponential distribution for interarrival times in SH
	protected Exponential interArrDistGI; // Exponential distribution for interarrival times in GI
	protected Exponential interArrDistRC; // Exponential distribution for interarrival times in RC

	final static double MEAN_ARRL_FP_1 = 0.13;
	final static double MEAN_ARRL_FP_2 = 0.20;
	final static double MEAN_ARRL_FP_3 = 0.22;
	final static double MEAN_ARRL_FP_4 = 0.21;
	final static double MEAN_ARRL_FP_5 = 0.19;
	final static double MEAN_ARRL_FP_6 = 0.19;
	final static double MEAN_ARRL_FP_7 = 0.21;
	final static double MEAN_ARRL_FP_8 = 0.23;
	final static double MEAN_ARRL_FP_9 = 0.21;
	final static double MEAN_ARRL_FP_10 = 0.19;
	final static double MEAN_ARRL_FP_11 = 0.16;
	final static double MEAN_ARRL_FP_12 = 0.14;

	final static double MEAN_ARRL_SH_1 = 0.15;
	final static double MEAN_ARRL_SH_2 = 0.17;
	final static double MEAN_ARRL_SH_3 = 0.24;
	final static double MEAN_ARRL_SH_4 = 0.22;
	final static double MEAN_ARRL_SH_5 = 0.21;
	final static double MEAN_ARRL_SH_6 = 0.20;
	final static double MEAN_ARRL_SH_7 = 0.20;
	final static double MEAN_ARRL_SH_8 = 0.21;
	final static double MEAN_ARRL_SH_9 = 0.19;
	final static double MEAN_ARRL_SH_10 = 0.19;
	final static double MEAN_ARRL_SH_11 = 0.17;
	final static double MEAN_ARRL_SH_12 = 0.15;

	final static double MEAN_ARRL_GI_1 = 0.18;
	final static double MEAN_ARRL_GI_2 = 0.18;
	final static double MEAN_ARRL_GI_3 = 0.23;
	final static double MEAN_ARRL_GI_4 = 0.29;
	final static double MEAN_ARRL_GI_5 = 0.25;
	final static double MEAN_ARRL_GI_6 = 0.21;
	final static double MEAN_ARRL_GI_7 = 0.21;
	final static double MEAN_ARRL_GI_8 = 0.22;
	final static double MEAN_ARRL_GI_9 = 0.20;
	final static double MEAN_ARRL_GI_10 = 0.18;
	final static double MEAN_ARRL_GI_11 = 0.15;
	final static double MEAN_ARRL_GI_12 = 0.14;

	final static double MEAN_ARRL_RC_1 = 0.16;
	final static double MEAN_ARRL_RC_2 = 0.19;
	final static double MEAN_ARRL_RC_3 = 0.21;
	final static double MEAN_ARRL_RC_4 = 0.23;
	final static double MEAN_ARRL_RC_5 = 0.21;
	final static double MEAN_ARRL_RC_6 = 0.19;
	final static double MEAN_ARRL_RC_7 = 0.20;
	final static double MEAN_ARRL_RC_8 = 0.19;
	final static double MEAN_ARRL_RC_9 = 0.21;
	final static double MEAN_ARRL_RC_10 = 0.19;
	final static double MEAN_ARRL_RC_11 = 0.17;
	final static double MEAN_ARRL_RC_12 = 0.15;

	// Constructor
	public RVPs(SMThemePark model, Seeds sd) {
		this.model = model;
		// Set up distribution functions
		interArrDistFP = new Exponential(1.0 / MEAN_ARRL_FP_1, new MersenneTwister(sd.arrFP));
		interArrDistSH = new Exponential(1.0 / MEAN_ARRL_SH_1, new MersenneTwister(sd.arrSH));
		interArrDistGI = new Exponential(1.0 / MEAN_ARRL_GI_1, new MersenneTwister(sd.arrGI));
		interArrDistRC = new Exponential(1.0 / MEAN_ARRL_RC_1,new MersenneTwister(sd.arrRC));
	}

	//
	protected double uCustomersFP() {
		double mean = getMeanTimeFP();
		double nxtArrival = model.getClock() + interArrDistFP.nextDouble(1.0 / mean);
		if (nxtArrival > model.closingTime) {
			nxtArrival = -1.0; // Ends time sequence
		}
		return (nxtArrival);
	}

	protected double uCustomersSH() {
		double mean = getMeanTimeSH();
		double nxtArrival = model.getClock() + interArrDistSH.nextDouble(1.0 / mean);
		if (nxtArrival > model.closingTime) {
			nxtArrival = -1.0; // Ends time sequence
		}
		return (nxtArrival);
	}

	protected double uCustomersGI() {
		double mean = getMeanTimeGI();
		double nxtArrival = model.getClock() + interArrDistGI.nextDouble(1.0 / mean);
		if (nxtArrival > model.closingTime) {
			nxtArrival = -1.0; // Ends time sequence
		}
		return (nxtArrival);
	}

	protected double uCustomersRC() {
		double mean = getMeanTimeRC();
		double nxtArrival = model.getClock() + interArrDistRC.nextDouble(1.0 / mean);
		if (nxtArrival > model.closingTime) {
			nxtArrival = -1.0; // Ends time sequence
		}
		return (nxtArrival);
	}

	//TODO: Add to Documentation
	protected double getMeanTimeFP() {
		double mean;

		if (model.getClock() < 60)
			mean = MEAN_ARRL_FP_1;
		else if (model.getClock() < 120)
			mean = MEAN_ARRL_FP_2;
		else if (model.getClock() < 180)
			mean = MEAN_ARRL_FP_3;
		else if (model.getClock() < 240)
			mean = MEAN_ARRL_FP_4;
		else if (model.getClock() < 300)
			mean = MEAN_ARRL_FP_5;
		else if (model.getClock() < 360)
			mean = MEAN_ARRL_FP_6;
		else if (model.getClock() < 420)
			mean = MEAN_ARRL_FP_7;
		else if (model.getClock() < 480)
			mean = MEAN_ARRL_FP_8;
		else if (model.getClock() < 540)
			mean = MEAN_ARRL_FP_9;
		else if (model.getClock() < 600)
			mean = MEAN_ARRL_FP_10;
		else if (model.getClock() < 660)
			mean = MEAN_ARRL_FP_11;
		else
			mean = MEAN_ARRL_FP_12;

		return mean;
	}
	
	protected double getMeanTimeSH() {
		double mean;

		if (model.getClock() < 60)
			mean = MEAN_ARRL_SH_1;
		else if (model.getClock() < 120)
			mean = MEAN_ARRL_SH_2;
		else if (model.getClock() < 180)
			mean = MEAN_ARRL_SH_3;
		else if (model.getClock() < 240)
			mean = MEAN_ARRL_SH_4;
		else if (model.getClock() < 300)
			mean = MEAN_ARRL_SH_5;
		else if (model.getClock() < 360)
			mean = MEAN_ARRL_SH_6;
		else if (model.getClock() < 420)
			mean = MEAN_ARRL_SH_7;
		else if (model.getClock() < 480)
			mean = MEAN_ARRL_SH_8;
		else if (model.getClock() < 540)
			mean = MEAN_ARRL_SH_9;
		else if (model.getClock() < 600)
			mean = MEAN_ARRL_SH_10;
		else if (model.getClock() < 660)
			mean = MEAN_ARRL_SH_11;
		else
			mean = MEAN_ARRL_SH_12;

		return mean;
	}
	
	protected double getMeanTimeGI() {
		double mean;

		if (model.getClock() < 60)
			mean = MEAN_ARRL_GI_1;
		else if (model.getClock() < 120)
			mean = MEAN_ARRL_GI_2;
		else if (model.getClock() < 180)
			mean = MEAN_ARRL_GI_3;
		else if (model.getClock() < 240)
			mean = MEAN_ARRL_GI_4;
		else if (model.getClock() < 300)
			mean = MEAN_ARRL_GI_5;
		else if (model.getClock() < 360)
			mean = MEAN_ARRL_GI_6;
		else if (model.getClock() < 420)
			mean = MEAN_ARRL_GI_7;
		else if (model.getClock() < 480)
			mean = MEAN_ARRL_GI_8;
		else if (model.getClock() < 540)
			mean = MEAN_ARRL_GI_9;
		else if (model.getClock() < 600)
			mean = MEAN_ARRL_GI_10;
		else if (model.getClock() < 660)
			mean = MEAN_ARRL_GI_11;
		else
			mean = MEAN_ARRL_GI_12;

		return mean;
	}
	
	protected double getMeanTimeRC() {
		double mean;

		if (model.getClock() < 60)
			mean = MEAN_ARRL_RC_1;
		else if (model.getClock() < 120)
			mean = MEAN_ARRL_RC_2;
		else if (model.getClock() < 180)
			mean = MEAN_ARRL_RC_3;
		else if (model.getClock() < 240)
			mean = MEAN_ARRL_RC_4;
		else if (model.getClock() < 300)
			mean = MEAN_ARRL_RC_5;
		else if (model.getClock() < 360)
			mean = MEAN_ARRL_RC_6;
		else if (model.getClock() < 420)
			mean = MEAN_ARRL_RC_7;
		else if (model.getClock() < 480)
			mean = MEAN_ARRL_RC_8;
		else if (model.getClock() < 540)
			mean = MEAN_ARRL_RC_9;
		else if (model.getClock() < 600)
			mean = MEAN_ARRL_RC_10;
		else if (model.getClock() < 660)
			mean = MEAN_ARRL_RC_11;
		else
			mean = MEAN_ARRL_RC_12;

		return mean;
	}

}
