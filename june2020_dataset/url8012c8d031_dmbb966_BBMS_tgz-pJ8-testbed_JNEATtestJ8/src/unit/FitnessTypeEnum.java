package unit;

import gui.GUI_NB;

import java.util.Vector;

import clock.Clock;
import spotting.SpotRecords;
import spotting.SpotReport;
import bbms.GlobalFuncs;

public enum FitnessTypeEnum {
	/** Fitness is based on the number of times it spots enemy units throughout the scenario. */
	SIMPLE_GREEDY,
	/** As SIMPLE_GREEDY, except if an enemy is spotted by multiple friendlies the reward is shared among them */
	SHARED_SPOTTING,
	/** Fitness averaged among all organisms of the same species for a given scenario */
	SOVIET_COMMUNISM,
	/** Fitness averaged among all organisms that took part in the scenario */
	FULL_COMMUNISM;
	
	public double EvaluateFitness(Unit x) {
		switch(this) {
		case SIMPLE_GREEDY:
			return EvaluateSimpleGreedy(x);
		case SHARED_SPOTTING:
			return EvaluateSharedSpotting(x);
		case SOVIET_COMMUNISM:
			return 0.9;
		case FULL_COMMUNISM:
			return 1.1;
		}
		
		return -1.0;
	}
	
	/** Cycles forward through the available fitness functions */
	public static FitnessTypeEnum CycleFitType(FitnessTypeEnum f) {
		switch(f) {
		case SIMPLE_GREEDY:
			return SHARED_SPOTTING;
		case SHARED_SPOTTING:
			return SOVIET_COMMUNISM;
		case SOVIET_COMMUNISM:
			return FULL_COMMUNISM;
		case FULL_COMMUNISM:
			return SIMPLE_GREEDY;
		}
		
		return SIMPLE_GREEDY;
	}
	
	private double EvaluateSimpleGreedy(Unit finger) {
		// Scan spot reports for the current turn to find what this unit has seen
		// Then credit it accordingly
		// Can probably be done more efficiently
		SpotRecords spots = GlobalFuncs.allSpots.getReportsTime(Clock.time);
		
		for (int i = 0; i < spots.records.size(); i++) {
			SpotReport x = spots.records.elementAt(i);
			
			if (x.spotter == finger && !GlobalFuncs.scenMap.inFriendlyZone(GlobalFuncs.scenMap.getHex(x.targetLoc))) {
				finger.spotCredits += 1.0;
				//GUI_NB.GCO("DEBUG: Unit " + finger.callsign + " credited with spotting " + x.target.callsign + " at time " + x.timeSpotted + " (total credits: " + finger.spotCredits);
			}
		}
		
		if (GlobalFuncs.shareTeamFit) return (finger.spotCredits / GlobalFuncs.maxPossibleSpots) + JNEATIntegration.teamPerformance;
		
		// Now calculates the new unit fitness, which is total spots / total number of possible spots
		return finger.spotCredits / GlobalFuncs.maxPossibleSpots;
	}
	
	private double EvaluateSharedSpotting(Unit finger) {
		// Scan spot records for the current turn to find out what this unit has seen
		// Then check to see how many other units have spotted the enemy on the same turn, and divide the
		// spotting credit accordingly
		// As above, this can probably be done more efficiently
		SpotRecords spots = GlobalFuncs.allSpots.getReportsTime(Clock.time);
		
		for (int i = 0; i < spots.records.size(); i++) {
			SpotReport x = spots.records.elementAt(i);
			
			if (x.spotter == finger && !GlobalFuncs.scenMap.inFriendlyZone(GlobalFuncs.scenMap.getHex(x.targetLoc))) {
				// Find out how to share this
				SpotRecords y = spots.getReportsTarget(x.target);
				finger.spotCredits += (1.0 / y.records.size());
				//GUI_NB.GCO("DEBUG: Unit " + finger.callsign + " credited with spotting " + x.target.callsign + " shared by " + y.records.size() + " (total credits: " + finger.spotCredits);
			}
		}
		
		if (GlobalFuncs.shareTeamFit) return (finger.spotCredits / GlobalFuncs.maxPossibleSpots) + JNEATIntegration.teamPerformance;
		
		return finger.spotCredits / GlobalFuncs.maxPossibleSpots;
	}
	
	
	
}
