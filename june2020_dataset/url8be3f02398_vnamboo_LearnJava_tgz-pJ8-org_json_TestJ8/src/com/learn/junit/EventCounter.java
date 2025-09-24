package com.learn.junit;

import java.util.concurrent.atomic.AtomicLong;

public class EventCounter implements EventCounterI {

	private AtomicLong counter=new AtomicLong();
	
	@Override
	public void eventOccured() {
		counter.getAndIncrement();
	}

	@Override
	public long getEventCount() {
		return counter.get();
	}

	@Override
	public long resetEventCount() {
		return counter.getAndSet(0l);
	}

}
