package com.sjl.health.internal;

import java.lang.reflect.*;
import java.util.*;

import com.sjl.health.*;

/**
 * Enables monitoring of all methods of all interfaces implemented by a class. 
 * 
 * Methods declared in the class which do not implement a method of an interface
 * are NOT monitored!
 * 
 * @author steve
 */
public class DynamicProxyingHealthService implements HealthService {

	interface Handler {
		public Object handle(OperationMonitor aMonitor, Object aProxied, Object[] anArgs) 
		throws Throwable;
	}
	
	private final Object lock = new Object();
	private final HealthFactory healthFactory;
	private final IssueTrackerFactory issueTrackerFactory;
	private final Clock clock;
	private final Map<Class<?>, Map<Method, Handler>> instrumentedClasses;
	private final Map<Object, Health> monitoredComponents;
	
	public DynamicProxyingHealthService(
		HealthFactory aHealthFactory, IssueTrackerFactory anIssues, Clock aClock) {
		issueTrackerFactory = anIssues;
		clock = aClock;
		healthFactory = aHealthFactory;
		// use weakhashmaps so we don't create memory leaks by
		// hanging onto components or classes beyond their natural 
		// lifecycle.
		monitoredComponents = new WeakHashMap<Object, Health>();
		instrumentedClasses = new WeakHashMap<Class<?>, Map<Method, Handler>>();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T monitor(final T aComponent, Configuration aConfig) {
		aConfig.init(issueTrackerFactory, clock);
System.out.println(aConfig);		
		final Map<Method, Handler> _handlers = getHandlers(aComponent.getClass());
		final Health _health = healthFactory.newHealth(aConfig);
		
		T _instrumented = (T) Proxy.newProxyInstance(
			aComponent.getClass().getClassLoader(), 
			aComponent.getClass().getInterfaces(), 
            new InvocationHandler() {
            @Override
            public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) 
            throws Throwable {            	
            	try {
            		Handler _h = _handlers.get(aMethod);            		
            		if (_h != null)
            			return _h.handle(_health, aComponent, aArgs);
            		return aMethod.invoke(aComponent, aArgs);
            	} catch (InvocationTargetException anExc) {          		
            		throw anExc.getCause();
            	}
            }
        });
		
		addComponent(aComponent, _health);
		
		return _instrumented;
	}

	@Override
	public HealthInfo get(Object aMaybeMonitored) {
		synchronized (lock) {
			return monitoredComponents.get(aMaybeMonitored);
		}
	}
	
	public void reset(Object aMaybeMonitored) {
		synchronized(lock) {
			Health _h = monitoredComponents.get(aMaybeMonitored);
			if (_h != null)
				_h.reset();
		}
	}
	
	private void addComponent(Object aComponent, Health aHealth) {
		synchronized(lock) {
			monitoredComponents.put(aComponent, aHealth);
		}
	}
	
	private Map<Method, Handler> getHandlers(Class<?> aComponentClass) {
		synchronized(lock) {
			Map<Method, Handler> _handlers = instrumentedClasses.get(aComponentClass);
			if (_handlers == null) {
				_handlers = initHandlers(aComponentClass);
				instrumentedClasses.put(aComponentClass, _handlers);
			}
			return _handlers;
		}
	}
	
	private Map<Method, Handler> initHandlers(Class<?> aComponentClass) {
		Map<Method, Handler> _result = new HashMap<Method, Handler>();
		// only instrument methods of implemented interfaces
		for (Class<?> _cls : aComponentClass.getInterfaces()) {
			for (Method _m : _cls.getMethods()) {
				if (_m.isAnnotationPresent(NotInstrumented.class)) {
					_result.put(_m, newUninstrumentedHandler(_m));
				} else if (_m.isAnnotationPresent(InstrumentedResult.class)) {
					_result.put(_m, newInstrumentedResultHandler(_m));
				} else {
					_result.put(_m, newInstrumentedHandler(_m));
				}
			}
		}
		return _result;
	}
	
	private Handler newUninstrumentedHandler(final Method aMethod) {
		return new Handler() {
            @Override
            public Object handle(OperationMonitor aMonitor, Object aProxied, Object[] anArgs) 
            throws Throwable {
                return aMethod.invoke(aProxied, anArgs);
            }
        };
	}
	
	private Handler newInstrumentedResultHandler(final Method aMethod) {
		return new Handler() {
			@Override
			public Object handle(OperationMonitor aMonitor, Object aProxied, Object[] anArgs)
			throws Throwable {
				throw new UnsupportedOperationException("TODO");
			}
		};
	}
	
	private Handler newInstrumentedHandler(final Method aMethod) {
		return new Handler() {
			@Override
			public Object handle(OperationMonitor aMonitor, Object aProxied, Object[] anArgs)
			throws Throwable {
				try {
					Object _result = aMethod.invoke(aProxied, anArgs);			
					aMonitor.success();
					return _result;
				} catch (InvocationTargetException anExc) {
					aMonitor.failure(anExc.getCause());
					throw anExc;
				}
			}			
		};
	}
}
