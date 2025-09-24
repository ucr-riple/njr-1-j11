package simModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds {
	int arrFP; // customer arrival for FP
	int arrSH; // customer arrival for SH
	int arrGI; // customer arrival for GI
	int arrRC; // customer arrival for RC

	public Seeds(RandomSeedGenerator rsg) {
		arrFP = rsg.nextSeed();
		arrSH = rsg.nextSeed();
		arrGI = rsg.nextSeed();
		arrRC = rsg.nextSeed();
	}
}
