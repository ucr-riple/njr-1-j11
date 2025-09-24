package com.sjl.health;

import com.sjl.health.conditions.*;

public class Exceptions {

	public static Condition ratioExceeds(final double aRatio) {
		return new RatioAbove(aRatio);
	}
	
	public static Condition ratioFallsBelow(final double aRatio) {
		return new RatioBelow(aRatio);
	}
	
}
