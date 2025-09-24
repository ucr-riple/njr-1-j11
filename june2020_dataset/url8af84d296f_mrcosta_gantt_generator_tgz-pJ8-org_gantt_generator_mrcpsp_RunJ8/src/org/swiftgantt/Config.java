package org.swiftgantt;

import java.awt.Color;
import java.awt.SystemColor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.EventListenerList;

import org.swiftgantt.common.EventLogger;
import org.swiftgantt.common.Time;

/**
 * The <code>Config</code> class represents the configurations for the <code>GanttChart</code>
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class Config {
	// Constants
	public static final int DEFAULT_TIME_UNIT_WIDTH = 24;

	public static final int DEFAULT_GANTT_CHART_ROW_HEIGHT = 24;

	public static final int DEFAULT_TASK_BAR_HEIGHT = 16;

	public static final int DEFAULT_PROGRESS_BAR_HEIGHT = 8;

	public static final int DEFAULT_BLANK_STEPS_TO_KICKOFF_TIME = 2;

	public static final int DEFAULT_BLANK_STEPS_TO_DEADLINE = 10;

	public static final boolean DEFAULT_SHOW_TASK_INFO_BEHIND_TASK_BAR = true;

	// Constants for Time Unit
//	public static final int DEFAULT_HOURLY_TOTAL_STEPS_OF_DAY = 9;//FIXME?
	public static final int HOURLY_PRE_REST_STEPS = 0;
//	public static final int DEFAULT_HOURLY_WORKING_STEPS = 8;
	public static final int HOURLY_SEQ_REST_STEPS = 1;

	public static final int DAILY_TOTAL_DAYS_OF_WEEK = 7;
	public static final int DAILY_PRE_REST_STEPS = 1;
	public static final int DEFAULT_DAILY_WORKING_STEPS = 5;
	public static final int DAILY_SEQ_REST_STEPS = 1;

	public static final int[] DEFAULT_WORKING_DAYS_SPAN_OF_WEEK = new int[] { Time.MONDAY, Time.FRIDAY };//Default working days is from monday to friday.

	public static final int[] DEFAULT_WORKING_HOURS_SPAN_OF_DAY = new int[] { 9, 16 };// From 9:00 to 16:00

	// Colors configurations.
	private Color ganttChartBackColor = SystemColor.control;

	private Color workingTimeBackColor = Color.white;

	private Color restoutTimeBackColor = Color.gray;
	
	private Color currentTimeBackColor = new Color(0, 204, 0);

	private Color kickoffTimeBackColor = Color.cyan;

	private Color deadlineBackColor = new Color(255, 153, 204);

	private Color taskTreeViewBackColor = SystemColor.control;

	private Color taskGroupBarBackColor = Color.black;

	private Color taskBarBackColor = new Color(102, 153, 255);

	private Color progressBarBackColor = Color.black;
	
	private Color selectionColor = Color.pink;

	// Misc configurations.
	private int timeUnitWidth = DEFAULT_TIME_UNIT_WIDTH;

	private int ganttChartRowHeight = DEFAULT_GANTT_CHART_ROW_HEIGHT;

	private int taskBarHeight = DEFAULT_TASK_BAR_HEIGHT;

	private int progressBarHeight = DEFAULT_PROGRESS_BAR_HEIGHT;

	private int blankStepsToKickoffTime = DEFAULT_BLANK_STEPS_TO_KICKOFF_TIME;

	private int blankStepsToDeadline = DEFAULT_BLANK_STEPS_TO_DEADLINE;

	private boolean showTaskInfoBehindTaskBar = DEFAULT_SHOW_TASK_INFO_BEHIND_TASK_BAR;

	// Since 0.3.0
	private int[] workingDaysSpanOfWeek = DEFAULT_WORKING_DAYS_SPAN_OF_WEEK;

	// Since 0.3.0
	private int[] workingHoursSpanOfDay = DEFAULT_WORKING_HOURS_SPAN_OF_DAY;
	
	// Since 0.3.2
	private boolean allowAccurateTaskBar = true;
	
	// Since 0.3.4
	private boolean fillInvalidArea = false;


	// ---------------------------
	protected transient PropertyChangeEvent propChangeEvent = null;
	protected EventListenerList listenerList = new EventListenerList();


	public Config() {
	}

	/**
	 * Add listener for property changes.
	 * 
	 * @param l
	 */
	public void addPropertyChangeListener(PropertyChangeListener l) {
		listenerList.add(PropertyChangeListener.class, l);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PropertyChangeListener.class) {
				propChangeEvent = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
				PropertyChangeListener curLsner = (PropertyChangeListener) listeners[i + 1];
				curLsner.propertyChange(propChangeEvent);
				EventLogger.event(propChangeEvent, "The config value " + propertyName + " has been changed from " + oldValue + " to "
						+ newValue + "! -> Listener: " + curLsner.getClass().getName());
			}
		}
	}

	/**
	 * Get back color of Gantt chart.
     * @param red
     */
	public Color getGanttChartBackColor(Color red) {
		return ganttChartBackColor;
	}

	/**
	 * Set back color of Gantt chart.
	 * 
	 * @param ganttChartBackColor
	 */
	public void setGanttChartBackColor(Color ganttChartBackColor) {
		Object oldValue = this.ganttChartBackColor;
		this.ganttChartBackColor = ganttChartBackColor;
		this.firePropertyChange("GanttChartBackColor", oldValue, ganttChartBackColor);
	}

	/**
	 * Get back color of working time in time axis.
	 * 
	 * @return
	 */
	public Color getWorkingTimeBackColor() {
		return workingTimeBackColor;
	}

	/**
	 * Set back color of working time in time axis.
	 * 
	 * @return
	 */
	public void setWorkingTimeBackColor(Color workingTimeBackColor) {
		Object oldValue = this.workingTimeBackColor;
		this.workingTimeBackColor = workingTimeBackColor;
		this.firePropertyChange("WorkingTimeBackColor", oldValue, workingTimeBackColor);
	}

	/**
	 * Get back color of rest-out time in time axis.
	 * 
	 * @return
	 */
	public Color getRestoutTimeBackColor() {
		return restoutTimeBackColor;
	}

	/**
	 * Set back color of rest-out time in time axis.
	 * 
	 * @return
	 */
	public void setRestoutTimeBackColor(Color restoutTimeBackColor) {
		Object oldValue = this.restoutTimeBackColor;
		this.restoutTimeBackColor = restoutTimeBackColor;
		this.firePropertyChange("RestoutTimeBackColor", oldValue, restoutTimeBackColor);
	}

	/**
	 * Get back color of kickoff time in time axis.
	 * 
	 * @return
	 */
	public Color getKickoffTimeBackColor() {
		return kickoffTimeBackColor;
	}

	/**
	 * Set back color of kickoff time in time axis.
	 * 
	 * @return
	 */
	public void setKickoffTimeBackColor(Color kickoffTimeBackColor) {
		Object oldValue = this.kickoffTimeBackColor;
		this.kickoffTimeBackColor = kickoffTimeBackColor;
		this.firePropertyChange("KickoffTimeBackColor", oldValue, kickoffTimeBackColor);
	}

	/**
	 * Get back color of deadline in time axis.
	 * 
	 * @return
	 */
	public Color getDeadlineBackColor() {
		return deadlineBackColor;
	}

	/**
	 * Set back color of deadline in time axis.
	 * 
	 * @return
	 */
	public void setDeadlineBackColor(Color deadlineBackColor) {
		Object oldValue = this.deadlineBackColor;
		this.deadlineBackColor = deadlineBackColor;
		this.firePropertyChange("DeadlineBackColor", oldValue, deadlineBackColor);
	}

	/**
	 * Get back color of task group bar.
	 * 
	 * @return
	 */
	public Color getTaskGroupBarBackColor() {
		return taskGroupBarBackColor;
	}

	/**
	 * Set back color of task group bar.
	 * 
	 * @return
	 */
	public void setTaskGroupBarBackColor(Color taskGroupBarBackColor) {
		Object oldValue = this.taskGroupBarBackColor;
		this.taskGroupBarBackColor = taskGroupBarBackColor;
		this.firePropertyChange("TaskGroupBarBackColor", oldValue, taskGroupBarBackColor);
	}

	/**
	 * Get back color of task bar.
	 * 
	 * @return
	 */
	public Color getTaskBarBackColor() {
		return taskBarBackColor;
	}

	/**
	 * Set back color of task bar.
	 * 
	 * @return
	 */
	public void setTaskBarBackColor(Color taskBarBackColor) {
		Object oldValue = this.taskBarBackColor;
		this.taskBarBackColor = taskBarBackColor;
		this.firePropertyChange("TaskBarBackColor", oldValue, taskBarBackColor);
	}

	/**
	 * Get back color of progress bar.
	 * 
	 * @return
	 */
	public Color getProgressBarBackColor() {
		return progressBarBackColor;
	}

	/**
	 * Set back color of progress bar.
	 * 
	 * @return
	 */
	public void setProgressBarBackColor(Color progressBarBackColor) {
		Object oldValue = this.progressBarBackColor;
		this.progressBarBackColor = progressBarBackColor;
		this.firePropertyChange("ProgressBarBackColor", oldValue, progressBarBackColor);
	}

	/**
	 * Get width of time unit in time axis.
	 * 
	 * @return
	 */
	public int getTimeUnitWidth() {
		return timeUnitWidth;
	}

	/**
	 * Set width of time unit in time axis.
	 * 
	 * @return
	 */
	public void setTimeUnitWidth(int timeUnitWidth) {
		Object oldValue = this.timeUnitWidth;
		this.timeUnitWidth = timeUnitWidth;
		this.firePropertyChange("TimeUnitWidth", oldValue, timeUnitWidth);
	}

	/**
	 * Get row height of Gantt chart.
	 * 
	 * @return
	 */
	public int getGanttChartRowHeight() {
		return ganttChartRowHeight;
	}

	/**
	 * Set row height of Gantt chart.
	 * 
	 * @return
	 */
	public void setGanttChartRowHeight(int ganttChartRowHeight) {
		Object oldValue = this.ganttChartRowHeight;
		this.ganttChartRowHeight = ganttChartRowHeight;
		this.firePropertyChange("GanttChartRowHeight", oldValue, ganttChartRowHeight);
	}

	/**
	 * Get task bar height.
	 * 
	 * @return
	 */
	public int getTaskBarHeight() {
		return taskBarHeight;
	}

	/**
	 * Set task bar height.
	 * 
	 * @return
	 */
	public void setTaskBarHeight(int taskBarHeight) {
		Object oldValue = this.taskBarHeight;
		this.taskBarHeight = taskBarHeight;
		this.firePropertyChange("TaskBarHeight", oldValue, taskBarHeight);
	}

	/**
	 * Get progress bar height.
	 * 
	 * @return
	 */
	public int getProgressBarHeight() {
		return progressBarHeight;
	}

	/**
	 * Set progress bar height.
	 * 
	 * @return
	 */
	public void setProgresBarHeight(int progressBarHeight) {
		Object oldValue = this.progressBarHeight;
		this.progressBarHeight = progressBarHeight;
		this.firePropertyChange("ProgresBarHeight", oldValue, progressBarHeight);
	}

	/**
	 * Get blank steps to kickoff time.
	 * 
	 * @return
	 */
	public int getBlankStepsToKickoffTime() {
		return blankStepsToKickoffTime;
	}

	/**
	 * Set blank steps to kickoff time.
	 * 
	 * @return
	 */
	public void setBlankStepsToKickoffTime(int emptyStepsToKickoffTime) {
		Object oldValue = this.blankStepsToKickoffTime;
		this.blankStepsToKickoffTime = emptyStepsToKickoffTime;
		this.firePropertyChange("EmpytStepsToKickoffTime", oldValue, emptyStepsToKickoffTime);
	}

	/**
	 * Get blank steps to deadline.
	 * 
	 * @return
	 */
	public int getBlankStepsToDeadline() {
		return blankStepsToDeadline;
	}

	/**
	 * Set blank steps to deadline.
	 * 
	 * @return
	 */
	public void setBlankStepsToDeadline(int emptyStepsToDeadline) {
		Object oldValue = this.blankStepsToDeadline;
		this.blankStepsToDeadline = emptyStepsToDeadline;
		this.firePropertyChange("EmptyStepsToDeadline", oldValue, emptyStepsToDeadline);
	}

	/**
	 * Get the value that whether showing task info behind task bar.
	 * 
	 * @return
	 */
	public boolean isShowTaskInfoBehindTaskBar() {
		return showTaskInfoBehindTaskBar;
	}

	/**
	 * Set the value that whether showing task info behind task bar.
	 * 
	 * @return
	 */
	public void setShowTaskInfoBehindTaskBar(boolean showTaskInfoBehindTaskBar) {
		Object oldValue = this.showTaskInfoBehindTaskBar;
		this.showTaskInfoBehindTaskBar = showTaskInfoBehindTaskBar;
		this.firePropertyChange("ShowTaskInfoBehindTaskBar", oldValue, showTaskInfoBehindTaskBar);
	}

	/**
	 * Get backcolor of task tree view.
	 * 
	 * @return
	 */
	public Color getTaskTreeViewBackColor() {
		return taskTreeViewBackColor;
	}

	/**
	 * Set backcolor of task tree view.
	 * 
	 * @param taskTreeViewBackColor
	 */
	public void setTaskTreeViewBackColor(Color taskTreeViewBackColor) {
		Object oldValue = this.taskTreeViewBackColor;
		this.taskTreeViewBackColor = taskTreeViewBackColor;
		this.firePropertyChange("TaskTreeViewBackColor", oldValue, taskTreeViewBackColor);
	}

	/**
	 * Get span of working days in week.
	 * 
	 * @return
	 */
	public int[] getWorkingDaysSpanOfWeek() {
		return workingDaysSpanOfWeek;
	}

	/**
	 * Span of working days of each week, the value is limited from 1(Sun) to 7(Sat). 
	 * You can use constants in Calendar. This parameter is for <code>TimeUnit.Day</code> only.<BR>
	 *  e.g: int[]{2, 7} represents working days from Mon to Sat.<BR>
	 * 
	 * @param workingDaysSpanOfWeek
	 */
	public void setWorkingDaysSpanOfWeek(int[] workingDaysSpanOfWeek) {
		Object oldValue = this.workingDaysSpanOfWeek;
		if (workingDaysSpanOfWeek[0] > workingDaysSpanOfWeek[1]) {
			throw new IllegalArgumentException("The start working day of week can't be after end working day");
		}
		this.workingDaysSpanOfWeek = workingDaysSpanOfWeek;
		if (this.workingDaysSpanOfWeek[0] < 0) {
			this.workingDaysSpanOfWeek[0] = 1;
		}
		if (this.workingDaysSpanOfWeek[1] > 7) {
			this.workingDaysSpanOfWeek[1] = 7;
		}
		this.firePropertyChange("WorkingHoursSpanOfDay", oldValue, workingDaysSpanOfWeek);
	}

	/**
	 * Get span of working hours of each day.
	 * @return
	 */
	public int[] getWorkingHoursSpanOfDay() {
		return workingHoursSpanOfDay;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getWorkingHoursOfDay(){
		return this.workingHoursSpanOfDay[1] - this.workingHoursSpanOfDay[0] + 1;
	}

	/**
	 * Span of working hours of each day, the value is limited from 0 to 23. 
	 * This parameter is for <code>TimeUnit.Hour</code> only.<BR>
	 *  e.g: int[]{9, 14} represents working hours from 9:00 to 14:00.<BR>
	 * @param workingHoursSpanOfDay
	 */
	public void setWorkingHoursSpanOfDay(int[] workingHoursSpanOfDay) {
		Object oldValue = this.workingHoursSpanOfDay;
		if (workingHoursSpanOfDay[0] > workingHoursSpanOfDay[1]) {
			throw new IllegalArgumentException("The start working hour of day can't be after end working hour");
		}
		this.workingHoursSpanOfDay = workingHoursSpanOfDay;
		if (this.workingHoursSpanOfDay[0] < 0) {
			this.workingHoursSpanOfDay[0] = 0;
		}
		if (this.workingHoursSpanOfDay[1] > 23) {
			this.workingHoursSpanOfDay[1] = 23;
		}
		this.firePropertyChange("WorkingHoursSpanOfDay", oldValue, workingHoursSpanOfDay);
	}

	/**
	 * Get backcolor of current time in Gantt chart time scale.
	 * @return
	 */
	public Color getCurrentTimeBackColor() {
		return currentTimeBackColor;
	}

	/**
	 * Set backcolor of current time in Gantt chart time scale.
	 * @param currentTimeBackColor
	 */
	public void setCurrentTimeBackColor(Color currentTimeBackColor) {
		Object oldValue = this.currentTimeBackColor;
		this.currentTimeBackColor = currentTimeBackColor;
		this.firePropertyChange("WorkingHoursSpanOfDay", oldValue, currentTimeBackColor);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllowAccurateTaskBar() {
		return allowAccurateTaskBar;
	}

	/**
	 * Allow or disallow the task bar accurately displaying in Gantt chart, this feature is
	 * not available for TimeUnit.AllDay and TimeUnit.Hour.
	 * @param allowAccurateTaskBar
	 */
	public void setAllowAccurateTaskBar(boolean allowAccurateTaskBar) {
		Object oldValue = this.allowAccurateTaskBar;
		this.allowAccurateTaskBar = allowAccurateTaskBar;
		this.firePropertyChange("AllowTaskBarExcess", oldValue, allowAccurateTaskBar);
	}

	/**
	 * Get color of selected tasks.
	 * @return
	 */
	public Color getSelectionColor() {
		return selectionColor;
	}

	/**
	 * Set color of selected tasks.
	 * @param selectionColor
	 */
	public void setSelectionColor(Color selectionColor) {
		Object oldValue = this.selectionColor;
		this.selectionColor = selectionColor;
		this.firePropertyChange("SelectionColor", oldValue, selectionColor);
	}


	public boolean isFillInvalidArea() {
		return fillInvalidArea;
	}

	public void setFillInvalidArea(boolean fillInvalidArea) {
		Object oldValue = this.fillInvalidArea;
		this.fillInvalidArea = fillInvalidArea;
		this.firePropertyChange("FillInvalidArea", oldValue, fillInvalidArea);
	}

	//	public Color getTaskGroupRowBackCOlor() {
	//		return taskGroupRowBackCOlor;
	//	}
	//
	//	public void setTaskGroupRowBackCOlor(Color taskGroupRowBackCOlor) {
	//		this.taskGroupRowBackCOlor = taskGroupRowBackCOlor;
	//	}

}
