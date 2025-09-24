package com.sjl.health.conditions;

import com.sjl.health.*;

public class RatioAbove implements Condition {

	private final double ratio;
	
	public RatioAbove(double aRatio) {
		ratio = aRatio;
	}
	
	@Override
	public boolean test(Statistics aSuccess, Statistics aFailure) {
		if (aSuccess.getOccurrenceCount() == 0) { 
		    return (aFailure.getOccurrenceCount() > 0);
		}

		return ((double)aFailure.getOccurrenceCount() / (double)aSuccess.getOccurrenceCount()) >= ratio;
	}
	
	@Override
	public String toString() {
		return "[ratio-above:" + ratio + "]";
	}
}
