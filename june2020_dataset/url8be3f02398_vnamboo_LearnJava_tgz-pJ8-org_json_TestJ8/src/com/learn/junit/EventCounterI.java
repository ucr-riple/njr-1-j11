package com.learn.junit;

public interface EventCounterI {
	void eventOccured();
	long resetEventCount();
	long getEventCount();
}
