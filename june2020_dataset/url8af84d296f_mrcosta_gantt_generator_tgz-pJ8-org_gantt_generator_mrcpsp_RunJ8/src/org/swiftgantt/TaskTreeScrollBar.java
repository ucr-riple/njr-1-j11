package org.swiftgantt;

import javax.swing.JScrollBar;

/**
 * The scroll bar of <code>TaskTreeView</code>.
 * @author Yuxing Wang
 *
 */
public class TaskTreeScrollBar extends JScrollBar {
	private static final long serialVersionUID = 1L;
	protected TaskTreeView taskTreeView = null;;

	public TaskTreeScrollBar(TaskTreeView taskTreeView) {
		initialize();
		this.setMinimum(0);
		this.taskTreeView = taskTreeView;
		this.addAdjustmentListener(taskTreeView);
		setMaximum(500); // Temporary 100 for width.
		setBlockIncrement(10);
		setUnitIncrement(5);
		setValue(0);
		this.setSize(this.getWidth(), 28);
	}
	
	private void initialize() {

	}
}
