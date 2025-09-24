package org.swiftgantt.event;

import java.util.EventListener;

/**
 * Handle event of selection changing.
 * 
 * @author Yuxing Wang
 * @since 0.3.4
 */
public interface SelectionChangeListener extends EventListener {

	/**
	 * Selection changed.
	 * @param e
	 */
	public void selectionChanged(SelectionChangeEvent e);
}
