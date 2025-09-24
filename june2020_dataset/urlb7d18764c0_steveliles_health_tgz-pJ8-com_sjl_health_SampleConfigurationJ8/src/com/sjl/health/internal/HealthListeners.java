package com.sjl.health.internal;

import java.util.*;
import java.util.concurrent.*;

import com.sjl.health.*;
import com.sjl.health.HealthInfo.StateInfo;

public class HealthListeners implements HealthListener {

	private List<HealthListener> listeners;
	
	public HealthListeners() {
		listeners = new CopyOnWriteArrayList<HealthListener>();
	}
	
	public void addListener(HealthListener aListener) {
		listeners.add(aListener);
	}
	
	public void removeListener(HealthListener aListener) {
		listeners.remove(aListener);
	}

	@Override
	public void onChange(StateInfo aFrom, StateInfo aTo) {
		for (HealthListener _l : listeners) {
			try {
				_l.onChange(aFrom, aTo);
			} catch (Throwable aThrowable) {
				// TODO
				aThrowable.printStackTrace();
			}
		}
	}
}
