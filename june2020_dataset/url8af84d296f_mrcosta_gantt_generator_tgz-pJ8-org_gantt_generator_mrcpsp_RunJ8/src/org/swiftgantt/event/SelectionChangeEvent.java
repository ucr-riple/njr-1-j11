package org.swiftgantt.event;

import java.util.EventObject;

import org.swiftgantt.model.Task;

/**
 * 
 * @author Yuxing Wang
 * @since 0.3.4
 */
public class SelectionChangeEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private Task selection = null;

	public SelectionChangeEvent(Object source, Task selection) {
		super(source);
		this.selection = selection;
	}

	public Task getSelection() {
		return selection;
	}

}
