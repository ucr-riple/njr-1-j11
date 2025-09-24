package Sentiens;

import java.util.*;

import Ideology.*;

public class Legacy {
	private final Map<Value, Integer> valueBins = new HashMap<Value, Integer>();
	private final Clan Me;
	
	public Legacy(Clan source) {
		Me = source;
		startFromScratch();
	}
	
	private void startFromScratch() {
		for (Value v : Values.All) {
			valueBins.put(v, 0);
		}
	}
	
	public void reenforce(ValuatableValue v, double rate) {
		final int curr = valueBins.get(v);
		final int diff = legacyValue(v) - curr;
		valueBins.put(v, newValue(curr, diff, rate));
	}
	public boolean reenforceIfPositive(ValuatableValue v, double rate) {
		final int curr = valueBins.get(v);
		final int diff = legacyValue(v) - curr;
		if (diff < 0) {return false;}
		valueBins.put(v, newValue(curr, diff, rate));
		return true;
	}
	
	private int legacyValue(ValuatableValue v) {
		if (v == Values.LEGACY) {return Me.getHeritageLength();}
		else {return v.value(Me, Me);}
	}
	
	private void reenforceBest(double rate) { //unused?
		int bestDiff = 0, bestCurr = 0; Value bestVal = null;
		for (Value v : valueBins.keySet()) {
			final int curr = valueBins.get(v);
			final int diff = legacyValue((ValuatableValue) v) - curr;
			if (diff > bestDiff) {bestDiff = diff; bestCurr = curr; bestVal = v;}
		}
		if (bestVal != null) {valueBins.put(bestVal, newValue(bestCurr, bestDiff, rate));}
	}
	
	private int newValue(int curr, int diff, double rate) {
		return (int)Math.round(rate * diff + curr);
	}
	
	public int getLegacyFor(Value v) {
		return valueBins.get(v);
	}
	
	public String improveDesc(Value v, double rate) {
		final int curr = valueBins.get(v);
		final int diff = legacyValue((ValuatableValue) v) - curr;
		return curr + " -> " + newValue(curr, diff, rate);
	}
	
}
