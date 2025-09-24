package com.sjl.health;

import com.sjl.health.internal.*;

public interface Configuration extends InitialStateFactory {
	
	// TODO: this sucks, and here just so we get a nice dsl? hmmmm 
	public void init(IssueTrackerFactory anIssueTrackerFactory, Clock aClock);
	
	public State newStateInstance(String aStateName, Issue anIssue);
	
}
