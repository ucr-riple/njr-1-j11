package com.sjl.health.conditions;

import org.jmock.*;
import org.junit.*;

import com.sjl.health.*;

public class RatioBelowTest {

	private Mockery ctx;
	private Statistics successes, failures;
	
	@Before
	public void setup() {
		ctx = new Mockery();
		successes = ctx.mock(Statistics.class, "success");
		failures = ctx.mock(Statistics.class, "failure");
	}
	
	@After
	public void teardown() {
		ctx.assertIsSatisfied();
	}
	
	@Test
	public void returnsTrueIfNothingHappenedYet() {
		ctx.checking(new Expectations(){{
			oneOf(successes).getOccurrenceCount(); will(returnValue(0L));
			oneOf(failures).getOccurrenceCount(); will(returnValue(0L));
		}});
		
		Assert.assertTrue(new RatioBelow(1d).test(successes, failures));
	}
	
	@Test
	public void returnsFalseIfFailuresButNoSuccesses() {
		ctx.checking(new Expectations(){{
			oneOf(successes).getOccurrenceCount(); will(returnValue(0L));
			oneOf(failures).getOccurrenceCount(); will(returnValue(2L));
		}});
		
		Assert.assertFalse(new RatioBelow(1d).test(successes, failures));
	}
	
	@Test
	public void returnsTrueWhenRatioEqualsTarget() {
		ctx.checking(new Expectations(){{
			allowing(successes).getOccurrenceCount(); will(returnValue(2L));
			allowing(failures).getOccurrenceCount(); will(returnValue(1L));
		}});
		
		Assert.assertTrue(new RatioBelow(0.5d).test(successes, failures));
	}
	
	@Test
	public void returnsFalseWhenRatioAboveTarget() {
		ctx.checking(new Expectations(){{
			allowing(successes).getOccurrenceCount(); will(returnValue(5L));
			allowing(failures).getOccurrenceCount(); will(returnValue(4L));
		}});
		
		Assert.assertFalse(new RatioBelow(0.5d).test(successes, failures));
	}
	
	@Test
	public void returnsTrueWhenRatioBelowTarget() {
		ctx.checking(new Expectations(){{
			allowing(successes).getOccurrenceCount(); will(returnValue(4L));
			allowing(failures).getOccurrenceCount(); will(returnValue(1L));
		}});
		
		Assert.assertTrue(new RatioBelow(0.5d).test(successes, failures));
	}
	
}
