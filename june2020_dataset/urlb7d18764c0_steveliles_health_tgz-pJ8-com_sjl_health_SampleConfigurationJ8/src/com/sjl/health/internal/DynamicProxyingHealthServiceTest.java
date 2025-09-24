package com.sjl.health.internal;

import org.jmock.*;
import org.junit.*;

import com.sjl.health.*;

public class DynamicProxyingHealthServiceTest {

	private interface SomeInterface {
		public void method1();
		public void method2();
	}
	
	private class SomeClass implements SomeInterface {
		private boolean invoked;
		
		@Override
		public void method1() {
			invoked = true;
		}
		
		@Override
		public void method2() {
			throw expected;
		}
		
		public boolean wasInvoked() {
			return invoked;
		}
	}

	private Mockery ctx;
	private Health health;
	private HealthService healthService;
	private SomeClass uninstrumented;
	private SomeInterface instrumented;
	private RuntimeException expected;
	private Configuration config;
	private HealthFactory healthFactory;
	
	@Before
	public void setup() {
		ctx = new Mockery();
		expected = new RuntimeException();
		health = ctx.mock(Health.class);
		config = ctx.mock(Configuration.class);
		healthFactory = ctx.mock(HealthFactory.class);
		healthService = new DynamicProxyingHealthService(
			healthFactory, null, null);
		uninstrumented = new SomeClass();		
		
		ctx.checking(new Expectations() {{
			allowing(healthFactory).newHealth(config);
			will(returnValue(health));
		}});
		
		instrumented = healthService.monitor(uninstrumented, config);
	}
	
	@After
	public void teardown() {
		ctx.assertIsSatisfied();
	}
	
	@Test
	public void providesAccessToHealthInfoOfInstrumentedComponents() {
		Assert.assertNotNull(healthService.get(instrumented));
	}
	
	@Test
	public void providesAccessToHealthInfoOfInstrumentedComponentsViaRefToUninstrumented() {
		Assert.assertNotNull(healthService.get(uninstrumented));
	}
	
	@Test
	public void returnsNullForUnmonitoredObjects() {
		Assert.assertNull(healthService.get(new Object()));
	}
	
	@Test
	public void invokingMethodOnInstrumentedClassInvokesUnderlyingObject() {
		ctx.checking(new Expectations() {{
			ignoring(health);
		}});
		instrumented.method1();
		Assert.assertTrue(uninstrumented.wasInvoked());
	}
	
	@Test
	public void tracksSuccessfulMethodInvocations() {
		ctx.checking(new Expectations() {{
			oneOf(health).success();
		}});
		instrumented.method1();		
	}
	
	@Test
	public void tracksUnsuccessfulMethodInvocations() {
		ctx.checking(new Expectations() {{
			oneOf(health).failure(expected);			
		}});
		
		try {
			instrumented.method2();
		} catch (Exception anExc) {
			Assert.assertEquals(expected, anExc);
		}
	}
}
