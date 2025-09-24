package org.swiftgantt;

import javax.swing.JComponent;
import javax.swing.UIManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Basic abstract view for all views of gantt chart.
 * 
 * @author Yuxing Wang
 * 
 */
public abstract class BaseView extends JComponent {

	private static final long serialVersionUID = 1L;

	protected Logger logger = null;
	protected static final int BORDER_WIDTH = 1;
	// Configuration for Colors, Layout...
	protected GanttChart ganttChart = null;
	protected Config config = null;//Internal use.

	protected int totalSteps = 0;

	protected int totalScheduleSteps = 0;

	// Since 0.3.4
	protected int x = 0;// View offset x
	protected int y = 0;// View offset y

	public BaseView(GanttChart ganttChart) {
		logger = LogManager.getLogger(this.getClass());
		this.ganttChart = ganttChart;
		if (ganttChart != null) {
			this.config = ganttChart.getConfig();
		}
	}

	/**
	 * Force to refresh view
	 */
	protected void refreshView() {
		if (this.getGraphics() != null) {
			this.update(this.getGraphics());
		}
	}

	/**
	 * Update UI.
	 */
	@Override
	public void updateUI() {
		setUI(UIManager.getUI(this));
	}

	/**
	 * Pass GanttChart reference to UI classes.
	 * 
	 * @return
	 */
	public GanttChart getGanttChart() {
		return ganttChart;
	}

	public int getTotalSteps() {
		return totalSteps;
	}

	public void setTotalSteps(int totalSteps) {
		this.totalSteps = totalSteps;
	}

	public int getTotalScheduleSteps() {
		return totalScheduleSteps;
	}

	public void setTotalScheduleSteps(int totalScheduleSteps) {
		this.totalScheduleSteps = totalScheduleSteps;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
