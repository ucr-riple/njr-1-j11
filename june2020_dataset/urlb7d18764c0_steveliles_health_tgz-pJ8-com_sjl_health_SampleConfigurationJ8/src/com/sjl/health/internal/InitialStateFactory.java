package com.sjl.health.internal;

import com.sjl.health.*;

public interface InitialStateFactory {

	public State newInitialState(Issue anIssue);
	
}
