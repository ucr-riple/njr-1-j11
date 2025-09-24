package com.sjl.health;

import com.sjl.health.HealthInfo.History;
import com.sjl.health.HealthInfo.StateInfo;
import com.sjl.health.internal.*;

public class SampleConfiguration {

	public interface MyComponent {		
		public String method1();		
		public String method2();		
	}
	
	class MyComponentImpl implements MyComponent {
		public String method1() {
			return "yay";
		}

		@Override
		public String method2() {
			throw new RuntimeException();
		}		
	}
	
	public void basicExample() {
		Clock _clock = new RealTimeClock();
		MyComponent _component = new MyComponentImpl();
		
		final HistoryManager _historyManager = new HistoryManager() {
			@Override
			public History get() {
				return null;
			}

			@Override
			public void add(StateInfo aStateInfo) {
				
			}			
		};
		
		HealthFactory _hf = new HealthFactory() {
			public Health newHealth(InitialStateFactory aStates) {
				return new SimpleThreadSafeHealth(aStates, _historyManager);
			}
		}; 
		
		HealthService _health = new DynamicProxyingHealthService(
			_hf, new InMemoryIssueTrackerFactory(_clock), _clock); 
		
		MyComponent _myMonitored = _health.monitor(_component, new SimpleConfiguration(
			Transitions.demote("ok").to("failing").
				when(Exceptions.ratioExceeds(1f/100f)),
			Transitions.demote("failing").to("failed").
				when(Exceptions.ratioExceeds(1f/10f)),
			Transitions.promote("failed").to("failing").
				when(Exceptions.ratioFallsBelow(1f/20f)),
			Transitions.promote("failing").to("ok").
			    when(Exceptions.ratioFallsBelow(1f/110f))));
		
		HealthInfo _info = _health.get(_component);
		System.out.println(_info);
		
		succeedNTimes(_myMonitored, _info, 100);		
		failNTimes(_myMonitored, _info, 1);
		succeedNTimes(_myMonitored, _info, 10);
		failNTimes(_myMonitored, _info, 1);
		succeedNTimes(_myMonitored, _info, 20);
	}

	private void failNTimes(MyComponent aMonitored, HealthInfo anInfo, int aTimes) {
		for (int i=0; i<aTimes; i++) {
			try {
				aMonitored.method2();
			} catch (RuntimeException anExc) {
				// expected
				// anExc.printStackTrace();
			}
			System.out.println(anInfo);
		}
	}

	private void succeedNTimes(MyComponent aComponent, HealthInfo anInfo, int aTimes) {
		for (int i=0; i<aTimes; i++) {
			aComponent.method1();
			System.out.println(anInfo);
		}
	}
	
	public static void main(String[] anArgs) {
		SampleConfiguration _sc = new SampleConfiguration();
		_sc.basicExample();
	}
}
