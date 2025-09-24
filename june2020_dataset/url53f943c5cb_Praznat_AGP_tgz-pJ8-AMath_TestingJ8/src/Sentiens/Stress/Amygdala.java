package Sentiens.Stress;

import java.util.*;

import Defs.M_;
import Sentiens.Clan;

public class Amygdala {

	private final Clan parent;
	private final Collection<Stressor> stressors;
	public Amygdala(Clan P) {
		parent = P;
		stressors = new ArrayList<Stressor>();
	}
	
	public void add(Stressor S) { // TODO what if add two of exactly same Stressor?
		if(getStressLevel() > 1) {
			enoughIsEnough(S);
		}
		else {stressors.add(S);}
	}
	/** put veins in goblins eyes based on this !!*/
	public double getStressLevel() {   //can be above 1 if failing to respond to stress
		double sum = 0;   double denom = parent.FB.getBeh(M_.TEMPER);
		for (Stressor stressor : stressors) {sum += stressor.getLevel(parent);}
		return sum / denom;
	}
	public void enoughIsEnough(Stressor lastStress) {
		if (lastStress.respond(parent)) {   //respond to this one
			relieveFrom(lastStress);
		}   else {
			stressors.add(lastStress); // if false too often this will get huge and cause stack overflow
		}
	}
	public Stressor largestStressor() {
		int biggest = 0; Stressor result = null;
		for (Stressor stressor : stressors) {
			int level = stressor.getLevel(parent);
			if (level > biggest) {biggest = level; result = stressor;}
		}
		return result;
	}
	public void relieveFrom(Stressor stress) {
		Stressor[] removeStressors = new Stressor[stressors.size()];  int removeN = 0;
		for (Stressor stressor : stressors) {   //remove all stressors same and less than this one
			if (stressor.sameAndLessThan(parent, stress)) {
				removeStressors[removeN++] = stressor;   //stressors.remove(stressor);
			}
		}
		for (removeN--; removeN >= 0; removeN--) {stressors.remove(removeStressors[removeN]);}
	}
	public void catharsis(int threshold) {
		Stressor[] removeStressors = new Stressor[stressors.size()];  int removeN = 0;
		for (Stressor stressor : stressors) {   //remove all stressors same and less than this one
			if (stressor.getLevel(parent) <= threshold) {
				removeStressors[removeN++] = stressor;   //stressors.remove(stressor);
			}
		}
		for (removeN--; removeN >= 0; removeN--) {stressors.remove(removeStressors[removeN]);}
	}
	public boolean containsStressor(Blameable sc) {
		for (Stressor s : stressors) if (s.getTarget() == sc) return true;
		return false;
	}
	
	@Override
	public String toString() {return stressors.toString();}
}

