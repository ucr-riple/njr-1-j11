package com.learn.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class EventCounterTest {

	@Before
	public void setUp(){
		
	}
	
	@Test
	public void testBasicEventCounting(){
		EventCounterI eventCounter=new EventCounter();
		eventCounter.eventOccured();
		assertEquals(1, eventCounter.getEventCount());		
	}
	
	@Test
	public void testBasicReset(){
		EventCounterI eventCounter=new EventCounter();
		eventCounter.eventOccured();
		assertEquals(1, eventCounter.getEventCount());
		long prevCount=eventCounter.resetEventCount();
		assertEquals(1, prevCount);
		assertEquals(0l, eventCounter.getEventCount());
	}
}
