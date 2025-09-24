package org.swiftgantt.model;

import java.util.EventListener;

/**
 * Listener for <code>GanttModel</code> changes.
 * @author Yuxing Wang
 *
 */
public interface GanttModelListener extends EventListener{

	/**
	 * Handle the gantt model changes event.
	 * @param evt
	 */
	void ganttModelChanged(GanttModelChangeEvent evt);
}
