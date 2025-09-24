package org.swiftgantt.event;

import java.beans.PropertyChangeEvent;

import org.swiftgantt.ui.TimeUnit;

/**
 * TODO
 * @author Yuxing Wang
 *
 */
public class TimeUnitChangeEvent extends PropertyChangeEvent {

	private static final long serialVersionUID = 1L;

	public TimeUnitChangeEvent(Object source,Object oldTimeUnit, Object newTimeUnit) {
		super(source, "TimeUnit", oldTimeUnit, newTimeUnit);
	}
	
	public TimeUnit getNewTimeUnit(){
		return (TimeUnit)super.getNewValue();
	}
	
	public TimeUnit getOldTimeUnit(){
		return (TimeUnit)super.getOldValue();
	}
}

