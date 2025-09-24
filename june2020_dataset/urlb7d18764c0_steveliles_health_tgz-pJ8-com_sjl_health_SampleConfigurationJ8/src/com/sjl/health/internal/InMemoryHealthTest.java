package com.sjl.health.internal;

import org.hamcrest.*;
import org.jmock.*;
import org.junit.*;

import com.sjl.health.HealthInfo.StateInfo;
import com.sjl.health.*;
import com.sjl.health.internal.immutable.*;

public class InMemoryHealthTest {

	private Mockery ctx;
	
	private Exception expected;
	private SimpleThreadSafeHealth health;
	private InitialStateFactory stateFactory;
	private State state1, state2;
	private HistoryManager history;
	private HealthListener listener;
	
	@Before
	public void setup() {
		ctx = new Mockery();
		
		expected = new Exception();
		state1 = ctx.mock(State.class, "s1");
		state2 = ctx.mock(State.class, "s2");
		history = ctx.mock(HistoryManager.class);
		listener = ctx.mock(HealthListener.class);
		stateFactory = ctx.mock(InitialStateFactory.class);
		
		ctx.checking(new Expectations() {{
			allowing(stateFactory).newInitialState(null); 
			will(returnValue(state1));	
		}});
		
		health = new SimpleThreadSafeHealth(stateFactory, history);
		health.addListener(listener);
	}
	
	@After
	public void teardown() {
		ctx.assertIsSatisfied();
	}
	
	@Test
	public void invokesStateOnSuccess() {
		ctx.checking(new Expectations() {{
			oneOf(state1).success(); will(returnValue(state1));
		}});
		
		health.success();
	}
	
	@Test
	public void invokesStateOnFailure() {
		ctx.checking(new Expectations() {{
			oneOf(state1).failure(expected);
			will(returnValue(state1));
		}});
		
		health.failure(expected);
	}
	
	@Test
	public void notifiesListenersOfSuccessStateChange() {
		ctx.checking(new Expectations() {{
			ignoring(history);
			
			oneOf(state1).success();
			will(returnValue(state2));
			
			allowingAllGetMethods(state1, "s1");
			allowingAllGetMethods(state2, "s2");
			
			oneOf(listener).onChange(
				with(any(State.class)), 
				with(any(State.class)));
		}});
		
		health.success();
	}
	
	@Test
	public void notifiesListenersOfFailureStateChange() {
		ctx.checking(new Expectations() {{
			ignoring(history);
			
			oneOf(state1).failure(expected);
			will(returnValue(state2));
			
			allowingAllGetMethods(state1, "s1");
			allowingAllGetMethods(state2, "s2");
			
			oneOf(listener).onChange(
				with(any(State.class)), 
				with(any(State.class)));
		}});
		
		health.failure(expected);
	}
	
	@Test
	public void listenersReceiveImmutableCopiesOfStates() {
		ctx.checking(new Expectations() {{
			ignoring(history);
			
			oneOf(state1).failure(expected);
			will(returnValue(state2));
			
			allowingAllGetMethods(state1, "s1");
			allowingAllGetMethods(state2, "s2");
			
			oneOf(listener).onChange(
				with(immutableState("s1")), 
				with(immutableState("s2")));
		}});
		
		health.failure(expected);
	}
	
	private Matcher<StateInfo> immutableState(final String aName) {
		return new BaseMatcher<StateInfo>() {
			@Override
			public boolean matches(Object aStateInfo) {				
				return 
					(aStateInfo instanceof ImmutableStateInfo) && 
					(aName.equals(((ImmutableStateInfo)aStateInfo).getName()));
			}

			@Override
			public void describeTo(Description aDescription) {
				aDescription.appendText("ImmutableStateInfo(" + aName +")");
			}
		};
	}
	
	private void allowingAllGetMethods(final State aState, final String aStateName) {
		ctx.checking(new Expectations(){{
			oneOf(aState).getName(); will(returnValue(aStateName));
			oneOf(aState).getSuccessStats();
			oneOf(aState).getFailureStats();
			oneOf(aState).getWhenChanged();
			oneOf(aState).getWhyChanged();
			oneOf(aState).getDistinctIssues();
		}});
	}
}
