package org.swiftgantt.model;

import java.beans.PropertyChangeEvent;

/**
 * The change event for <code>GanttModel</code>.
 * @author Yuxing Wang
 *
 */
public class GanttModelChangeEvent extends PropertyChangeEvent{

	private static final long serialVersionUID = 1L;

	/**
	 * Gantt model changes event.
	 * @param source
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 */
	public GanttModelChangeEvent(Object source, String propertyName,
		     Object oldValue, Object newValue) {
		super(source, propertyName, oldValue, newValue);
	}


}
