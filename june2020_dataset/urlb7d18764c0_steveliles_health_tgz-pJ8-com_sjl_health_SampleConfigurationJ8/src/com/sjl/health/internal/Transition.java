package com.sjl.health.internal;

import com.sjl.health.*;

public interface Transition {

	public State attempt(Statistics aSuccess, Statistics aFailure, Throwable aMaybeIssue);
	
}
