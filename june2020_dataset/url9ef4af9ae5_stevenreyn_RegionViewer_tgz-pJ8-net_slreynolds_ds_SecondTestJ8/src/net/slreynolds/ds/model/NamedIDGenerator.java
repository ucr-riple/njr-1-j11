package net.slreynolds.ds.model;

import java.util.concurrent.atomic.AtomicInteger;

public class NamedIDGenerator {

	private static AtomicInteger _nextID = new AtomicInteger();
	
	private NamedIDGenerator() {}
	
	public static NamedID next() {
		int id = _nextID.getAndIncrement();
		return new NamedID(id);
	}
	
	public void reset() {
		_nextID.set(0);
	}
}
