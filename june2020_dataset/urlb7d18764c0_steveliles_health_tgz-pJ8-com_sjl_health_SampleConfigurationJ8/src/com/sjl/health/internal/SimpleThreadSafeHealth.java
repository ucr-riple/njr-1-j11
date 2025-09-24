package com.sjl.health.internal;

import com.sjl.health.*;
import com.sjl.health.internal.immutable.*;

/**
 * TODO: hand-off handling of success/failure to another thread so we don't
 * block the caller - neither for the state change or the listener notification 
 * 
 * @author steve
 */
public class SimpleThreadSafeHealth implements Health {

	private State state;
	
	private final InitialStateFactory factory;
	private final Object lock;	
	private final HistoryManager history;
	private final HealthListeners listeners;
	private final StateInvoker success;
	private final StateInvoker failure;
	
	public SimpleThreadSafeHealth(InitialStateFactory aFactory, HistoryManager aHistoryManager) {
		factory = aFactory;		
		lock = new Object();
		listeners = new HealthListeners();
		history = aHistoryManager;
		
		success = new StateInvoker() {
			protected State invokeImpl(Throwable aMaybe) {
				return state.success();
			}			
		};
		
		failure = new StateInvoker() {
			protected State invokeImpl(Throwable aMaybe) {
				return state.failure(aMaybe);
			}
		};
		
		state = factory.newInitialState(null);
	}
	
	@Override
	public void reset() {
		synchronized(lock) {
			state = factory.newInitialState(null); // TODO: create an issue
		}
	}

	@Override
	public StateInfo getCurrentState() {
		return state;
	}

	@Override
	public History getHistory() {
		return history.get();
	}

	@Override
	public void addListener(HealthListener aListener) {
		listeners.addListener(aListener);
	}

	@Override
	public void removeListener(HealthListener aListener) {
		listeners.removeListener(aListener);
	}

	@Override
	public void success() {
		success.invoke(null);
	}

	@Override
	public void failure(Throwable aCause) {
		failure.invoke(aCause);
	}
	
	private abstract class StateInvoker {
		
		protected abstract State invokeImpl(Throwable aMaybe);
		
		public void invoke(Throwable aMaybe) {
			StateInfo _before, _after = null;
			
			synchronized(lock) {
				_before = state;
				state = invokeImpl(aMaybe);
				_after = state;
				
				if (_before != _after) {
					history.add(_after);
					_before = ImmutableStateInfo.create(_before);
					_after = ImmutableStateInfo.create(_after);
				}
			}
			
			if (!_before.equals(_after))
				listeners.onChange(_before, _after);
		}
	}
	
	@Override
	public String toString() {
		return "health - " + getCurrentState().toString();
	}
}
