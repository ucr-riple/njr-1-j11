package com.sjl.health;

public interface Condition {
	
	public boolean test(Statistics aSuccess, Statistics aFailure);
	
}
