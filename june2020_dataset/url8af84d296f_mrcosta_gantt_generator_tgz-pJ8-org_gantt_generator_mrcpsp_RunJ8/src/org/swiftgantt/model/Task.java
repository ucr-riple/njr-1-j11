package org.swiftgantt.model;

import java.util.List;

import org.swiftgantt.Config;
import org.swiftgantt.GanttChart;
import org.swiftgantt.common.CalendarUtils;
import org.swiftgantt.common.Time;
import org.swiftgantt.event.PredecessorChangeEvent;
import org.swiftgantt.event.PredecessorChangeListener;
import org.swiftgantt.ui.TimeUnit;
import org.swiftgantt.ui.timeaxis.BaseTimeAxis;
import org.swiftgantt.ui.timeaxis.TimeAxisUtils;
import java.util.Calendar;

/**
 * A <code>Task</code> is an object that represent a task in project, it is task node in the tasks tree.<br/>
 * {@link java.beans.PropertyChangeEvent} event raised after:<br/>
 * task start time changed, task end time changed, task progress changed.
 * @author Yuxing Wang
 * @version 1.0
 */
public final class Task extends BaseTask<Task> implements PredecessorChangeListener {

	private static final long serialVersionUID = 1L;

	public Task() {
		this.addPredecessorChangeListener(this);
	}

	public Task(String name, Time startTime, Time endTime) {
		this();
		this.name = name;
		this.setStart(startTime);
		this.setEnd(endTime);
	}

	public Task(String name, Time startTime, Time endTime, int progress) {
		this(name, startTime, endTime);
		this.setProgress(progress);
	}

	public Task(String name, Time startTime, Time endTime, List<Task> predecessors) {
		this(name, startTime, endTime);
		if (predecessors != null) {
			this.predecessors = predecessors;
			for (Task t : predecessors) {
				t.subsequences.add(this);
			}
		}
	}

	public Task(String name, String description, Time startTime, Time endTime, int progress, List<Task> predecessors) {
		this(name, startTime, endTime, predecessors);
		this.description = description;
		this.setProgress(progress);
	}

	/**
	 * Copy content from given source task
	 */
	public void copy(Task sourceTask) {
		this.name = sourceTask.getName();
		this.description = sourceTask.getDescription();
		this.setStart(sourceTask.getStart());
		this.setEnd(sourceTask.getEnd());
		this.progress = sourceTask.getProgress();
	}

	/**
	 * Get the sub-task, who has the earliest end time, in the sub-task tree.
	 * 
	 * @return
	 */
	public Task getEarliestSubTask() {
		Task earliestSubTask = this.getEarlistOrLatestSubTask(this, true);
		return earliestSubTask;
	}

	/**
	 * Get the sub-task, who has the earliest end time, in the sub-task tree include myself.
	 * 
	 * @return
	 */
	public Task getEarliestTask() {
		Task earliestSubTask = this.getEarlistOrLatestSubTask(this, true);
		if (earliestSubTask != null && earliestSubTask.getActualStart().after(this.getActualStart())) {
			earliestSubTask = this;
		}
		return earliestSubTask;
	}

	/**
	 * Get the last task, who has the latest end time, in the sub task tree.
	 * 
	 * @return
	 */
	public Task getLatestSubTask() {
		Task latestSubTask = this.getEarlistOrLatestSubTask(this, false);
		return latestSubTask;
	}

	/**
	 * Get the last task, who has the latest end time, in the sub task tree include myself.
	 * 
	 * @return
	 */
	public Task getLatestTask() {
		Task latestSubTask = this.getEarlistOrLatestSubTask(this, false);
		// Can not include myself because the root task node should be excluded. 
		if (latestSubTask != null && latestSubTask.getActualEnd().before(this.getActualEnd())) {
			latestSubTask = this;
		}
		return latestSubTask;
	}

	/*
	 * Get latest sub-task in the tree recursively, excludes myself.
	 */
	private Task getEarlistOrLatestSubTask(Task root, boolean earlist) {
		Task ret = null;
		for (int i = 0; i < root.getChildCount(); i++) {
			Task curChild = (Task) root.children.get(i);
			Task current = null; // Current for earliest or latest in sub-tree.
			if (curChild.isLeaf() == false) {// Get latest child if has children.
				current = getEarlistOrLatestSubTask(curChild, earlist);
			}
			if (earlist == true) {// Get earliest sub  task.
				if (current == null || current.getActualStart().after(curChild.getActualStart())) {// Compare to current child.
					current = curChild;
				}
				//Update the indicator if earlier task found in current sub-tree. 
				if (ret == null || ret.getActualStart().after(current.getActualStart())) {
					ret = current;
				}
			} else { // Get latest sub task.
				if (current == null || current.getActualEnd().before(curChild.getActualEnd())) {// Compare to current child.
					current = curChild;
				}
				//Update the indicator if later task found in current sub-tree. 
				if (ret == null || ret.getActualEnd().before(current.getActualEnd())) {
					ret = current;
				}
			}
		}
		return ret;
	}

	/**
	 * Calculate how many steps for this task in Gantt chart.
	 * 
	 * @param task
	 * @return
	 */
	public int calcTaskSteps() {
		if (this.treeModel == null || this.treeModel.getTimeUnit() == null) {
			return 0;
		}
		TimeUnit timeUnit = this.treeModel.getTimeUnit();
		int steps = TimeAxisUtils.getTimeIntervalByTimeUnit(timeUnit, this.getActualStart(), this.getActualEnd()) + 1;
		return steps;
	}

	/**
	 * Calculate displaying progress steps by task progress.
	 * 
	 * @param start
	 * @param progress
	 * @return
	 */
	public int calcProgressSteps() {
		int ret = 0;
		int progress = this.getProgress();
		if (this.treeModel == null || this.treeModel.getTimeUnit() == null) {
			logger.debug("No tree model or not specified time unit");
			return ret;
		}
		TimeUnit timeUnit = this.treeModel.getTimeUnit();
		Config config = GanttChart.getStaticConfig();
		if (timeUnit == TimeUnit.Hour) {
			int[] ws = GanttChart.getStaticConfig().getWorkingHoursSpanOfDay();
			int workingHours = config.getWorkingHoursOfDay();
			int startIndex = 0;
			int startHour = start.getHour();
			if (startHour < ws[0] || startHour > ws[1]) {
				startIndex = 1;// start at head of cycle, whatever this or next circle.
			} else {
				startIndex = startHour - ws[0] + 1;
			}
			int totalSteps = TaskHelper.getTimeCycleStepsByTimeUnit(timeUnit);
			ret = TaskHelper.calcActualStepForProgress(progress, startIndex, totalSteps, Config.HOURLY_PRE_REST_STEPS, workingHours,
					Config.HOURLY_SEQ_REST_STEPS);
		} else if (timeUnit == TimeUnit.AllDay) {
			ret = progress;
		} else if (timeUnit == TimeUnit.Day) {
			ret = 0;
			int startIndex = start.getDayOfWeek();// Start from Sunday(1)
			ret = TaskHelper.calcActualStepForProgress(progress, startIndex, Config.DAILY_TOTAL_DAYS_OF_WEEK, Config.DAILY_PRE_REST_STEPS,
					Config.DEFAULT_DAILY_WORKING_STEPS, Config.DAILY_SEQ_REST_STEPS);
		} else if (timeUnit == TimeUnit.Week) {
			ret = progress; // No rest-out time
		} else if (timeUnit == TimeUnit.Month) {
			ret = progress;
		} else if (timeUnit == TimeUnit.Year) {
			ret = progress;
		}
		return ret;
	}

	/**
	 * Get duration of this <code>Task</code>, exclude the rest-out time.
	 * 
	 * @return the duration
	 */
	public int getDuration() {
		// The duration that the task cost, it will be determined by the TimeUnit, Start time and End time.
		// Consider to change this implementaion, because it will calculate the duration every time.
		return this.calcDuration();
	}

	/**
	 * Get progress of this <code>Task</code>. If progress exceeds the task duration, it will be limited within the duration.
	 * 
	 * @return the progress
	 */
	public int getProgress() {
		if (progress < 0) {
			return 0;
		}
		int duration = this.getDuration();
		if (progress > duration) {
			return duration;
		}
		return progress;
	}

	/**
	 * Set progress of this <code>Task</code>.
	 * 
	 * @param progress the progress to set
	 */
	public void setProgress(int progress) {
		Object oldValue = this.progress;
		this.progress = progress;
		//    	logger.debug(" Progress for " + this.name + " " + this.progress + " by " + this.duration);
		this.firePropertyChanged("Progress", oldValue, progress);
	}

	/**
	 * Get start time of this <code>Task</code>.
	 * 
	 * @return the start
	 */
	public Time getStart() {
		return this.checkAndLimitStartTime(this.start);
	}

	/**
	 * Set start time of this <code>Task</code>.
	 * @param start
	 */
	public void setStart(Calendar start){
		this.setStart(new Time(start));
	}

	/**
	 * Set start time of this <code>Task</code>.
	 * 
	 * @param start the start to set
	 * @since 0.4.0
	 */
	public void setStart(Time start) {
		if (start == null) {
			return;
		}
		Object oldValue = this.start;
		this.start = start;// Clone to not to change the start time for outside.
		if (this.end == null) {
			this.end = start;
		}
		// Adjust actual start time by predecessor task
		if (this.predecessors.size() > 0) {
			Task p = super.getLatestPredecessor();
			if (p != null && (p.getEnd().after(start) || p.getEnd().equals(start))) {
				adjustActualTimesByPredecessor(p);
			} else {
				this.actualStart = start.clone();
			}
		} else {
			this.actualStart = start.clone();
		}
		this.firePropertyChanged("Start Time", oldValue, start);
	}

	/**
	 * Handler the predecessor changes event.
	 */
	public void predecessorChanged(PredecessorChangeEvent e) {
		Task p = (Task) e.getPredecessor();
		if (p != null && p.getEnd() != null && p.getEnd().after(this.getStart())) {
			this.adjustActualTimesByPredecessor();
		}
	}

	/**
	 * Adjust actual start and end time by it's latest predecessor.
	 */
	public void adjustActualTimesByPredecessor() {
		Task p = this.getLatestPredecessor();
		this.adjustActualTimesByPredecessor(p);
	}

	/*
	 * Adjust actual start and end time by specified predecessor;
	 */
	protected void adjustActualTimesByPredecessor(Task predecessor) {
		if (this.treeModel == null) {
			return;
		}
		if (predecessor != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Adjust task's time by predecessor task: " + predecessor.getName());
			}
			TimeUnit tu = this.treeModel.getTimeUnit();
			if (tu == null) {
				return;
			}
			if (this.start != null) {
				if (TaskHelper.isAllowAccurateTaskBar(tu)) {
					tu = TimeUnit.getAccurateTimeUnit(tu);
				}
				int offset = TimeAxisUtils.getTimeIntervalByTimeUnit(tu, this.start, predecessor.getEnd());

				if (logger.isInfoEnabled()) {
					logger.info("adjustActualTimesByPredecessor(Task) - @@@@@@ - tu=" + tu + ", offset=" + offset);
				}

				if (offset >= 0) {//
					offset = 1 + offset;
					//Actual task start time as per predecessor's end time.
					this.actualStart = TaskHelper.increaseCloneTimeByTimeUnit(tu, this.start, offset);
					//Actual task end time as per start time.
					this.actualEnd = TaskHelper.increaseCloneTimeByTimeUnit(tu, this.end, offset);
				} else {
					this.actualStart = this.start.clone();
					this.actualEnd = this.end.clone();
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("    After adjustment: " + this);
			}
		}
		//Recursive to sub-tasks.
		for (Task st : this.getChildren()) {
			Task p = st.getLatestPredecessor();
			st.adjustActualTimesByPredecessor(p);
		}
	}

	/**
	 * Get actual start time of this task.
	 * 
	 * @return
	 */
	public Time getActualStart() {
		return this.checkAndLimitStartTime(actualStart);
	}

	/**
	 * Set actual start time of this Task.
	 *
	 * @param actualStart
	 */
	protected void setActualStart(Calendar actualStart) {
		this.setActualStart(new Time(actualStart));
	}

	/**
	 * Set actual start time of this Task.
	 * 
	 * @param actualStart
	 */
	protected void setActualStart(Time actualStart) {
		this.actualStart = actualStart;
	}

	/**
	 * Get end time of this <code>Task</code>.
	 * 
	 * @return the end
	 * @since 0.4.0
	 */
	public Time getEnd() {
		Time endTime = this.checkAndLimitEndTime(this.end);
		//		endTime.add(Time.DATE, 1);//Temprarily, will be change at v0.3
		return endTime;
	}

	/**
	 * Set end time of this <code>Task</code>.
	 * @param end
	 */
	public void setEnd(Calendar end) {
		this.setEnd(new Time(end));
	}

	/**
	 * Set end time of this <code>Task</code>.
	 * 
	 * @param end The end time of task to set
	 * @since 0.4.0
	 */
	public void setEnd(Time end) {
		if (end == null) {
			return;
		}
		Object oldValue = this.end;
		this.end = end;// Clone to not to change the start time for outside.
		//Adjust end time by latest predecessor task
		if (this.start != null && this.predecessors.size() > 0) {
			Task p = super.getLatestPredecessor();
			if (p != null && p.getEnd() != null && p.getEnd().after(start) || p.getEnd().equals(start)) {
				adjustActualTimesByPredecessor(p);
			} else {
				this.actualEnd = end.clone();
			}
		} else {
			this.actualEnd = end.clone();
		}
		//also, as predecessor, set end time will affect sequence tasks.
		for (Task t : this.subsequences) {
			t.adjustActualTimesByPredecessor(this);
		}
		this.firePropertyChanged("End Time", oldValue, end);
	}

	/**
	 * Get actual end time of this task.
	 * 
	 * @return
	 */
	public Time getActualEnd() {
		return this.checkAndLimitEndTime(actualEnd);
	}
	/**
	 * Set actual end time of this Task.
	 *
	 * @param actualEnd
	 */
	protected void setActualEnd(Calendar actualEnd) {
		this.setActualEnd(new Time(actualEnd));
	}

	/**
	 * Set actual end time of this Task.
	 * 
	 * @param actualEnd
	 */
	protected void setActualEnd(Time actualEnd) {
		this.actualEnd = actualEnd;
	}

	/*
	 * Calculate duration by time unit, start time and end time.
	 */
	private int calcDuration() {
		int ret = 0;
		if (this.treeModel == null || this.getActualEnd() == null) {
			return ret;
		}
		Config cfg = GanttChart.getStaticConfig();
		TimeUnit timeUnit = this.treeModel.getTimeUnit();
		Time et = (Time) this.getActualEnd().clone();
		if (timeUnit != null && this.getActualStart() != null && et != null) {
			if (timeUnit == TimeUnit.Hour) {
				ret = CalendarUtils.calcWorkingHours(this.getActualStart(), et, cfg.getWorkingHoursOfDay(),
						cfg.getWorkingHoursSpanOfDay()[0]);

			} else if (timeUnit == TimeUnit.AllDay) {
//				ret = CalendarUtils.getHourInterval(start, end) + 1;
					ret = start.getHourIntervalTo(end) + 1;
			} else if (timeUnit == TimeUnit.Day) {
				ret = CalendarUtils.calcWorkingDays(this.getActualStart(), et, GanttChart.getStaticConfig().getWorkingDaysSpanOfWeek());

			} else if (timeUnit == TimeUnit.Week) {
				int yInterval = this.getActualEnd().getYear() - this.getActualStart().getYear() - 1;
				int wInterval = (52 - this.getActualStart().get(Time.WEEK_OF_YEAR)) + this.getActualEnd().get(Time.WEEK_OF_YEAR);
				ret = yInterval * 52 + wInterval;

			} else if (timeUnit == TimeUnit.Month) {
				int yInterval = this.getActualEnd().getYear() - this.getActualStart().getYear();
				int mInterval = this.getActualEnd().getMonth() - this.getActualStart().getMonth() + 1;
				ret = yInterval * 12 + mInterval;

			} else if (timeUnit == TimeUnit.Year) {
				ret = this.getActualEnd().getYear() - this.getActualStart().getYear() + 1;
			}
		//			logger.debug(" Duration for: " + this.name + " is: " + duration
		//					+ " from: " + this.start.getTime() + " to: " + et.getTime() + " in time unit: " + timeUnit);
		}
		return ret;
	}

	/*
	 * Check if start time of task is in rest-out time, adjust it to working time.
	 */
	private Time checkAndLimitStartTime(Time startTime) {
		if (startTime == null || this.treeModel == null || this.treeModel.getTimeUnit() == null) {
			return startTime;
		}
		Time adjustedStartTime = startTime.clone();
		TimeUnit timeUnit = this.treeModel.getTimeUnit();

		// Do nothing for other time unit.
		if (timeUnit == TimeUnit.Hour) {
			int lowBound = GanttChart.getStaticConfig().getWorkingHoursSpanOfDay()[0];
			int highBound = lowBound + BaseTimeAxis.STEPS_OF_MAJOR_SCALE - 1;
			int hourOfDay = startTime.get(Time.HOUR_OF_DAY);
			if (hourOfDay < lowBound || hourOfDay > highBound) {
				adjustedStartTime.set(Time.HOUR_OF_DAY, lowBound);
			}
			if (hourOfDay > highBound) {
				adjustedStartTime.add(Time.DATE, 1);
			}
		} else if (timeUnit == TimeUnit.Day) {
			int offset = 0;
			int[] wds = GanttChart.getStaticConfig().getWorkingDaysSpanOfWeek();//Working day span
			int dayOfWeek = startTime.get(Time.DAY_OF_WEEK);
			if (dayOfWeek < wds[0]) {
				offset = wds[0] - dayOfWeek;
			} else if (dayOfWeek > wds[1]) {
				offset = 7 - dayOfWeek + wds[0];
			}
			if (offset != 0) {
				adjustedStartTime.add(Time.DATE, offset);
			}
		}
		return adjustedStartTime;
	}

	/*
	 * @deprecated Check if end time of task is in rest-out time, adjust it to working time.
	 */
	private Time checkAndLimitEndTime(Time endTime) {
		if (endTime == null || this.treeModel == null || this.treeModel.getTimeUnit() == null) {
			return endTime;
		}
		Time adjustedEndTime = endTime.clone();
		TimeUnit timeUnit = this.treeModel.getTimeUnit();
		if (timeUnit == TimeUnit.Hour) {
			int f = GanttChart.getStaticConfig().getWorkingHoursSpanOfDay()[0];
			int t = GanttChart.getStaticConfig().getWorkingHoursSpanOfDay()[1];
			int hourOfDay = endTime.get(Time.HOUR_OF_DAY);
			if (hourOfDay < f || hourOfDay > t) {
				adjustedEndTime.set(Time.HOUR_OF_DAY, t);
			}
			if (hourOfDay < f) {
				adjustedEndTime.add(Time.DATE, -1);
			}
		} else if (timeUnit == TimeUnit.Day) {
			int offset = 0;
			int[] wds = GanttChart.getStaticConfig().getWorkingDaysSpanOfWeek();//Working day span
			int dayOfWeek = endTime.get(Time.DAY_OF_WEEK);
			if (dayOfWeek < wds[0]) {
				offset = wds[1] - 7 - dayOfWeek;
			} else if (dayOfWeek > wds[1]) {
				offset = wds[1] - dayOfWeek;
			}
			if (offset != 0) {
				adjustedEndTime.add(Time.DATE, offset);
			}
		}
		return adjustedEndTime;
	}

	/**
	 * The detail information for this Task.
	 */
	@Override
	public String toString() {
		String ret = null;
		String start = formatTimeByUnit(this.start);
		String end = formatTimeByUnit(this.end);
		String actualStart = formatTimeByUnit(this.actualStart);
		String actualEnd = formatTimeByUnit(this.actualEnd);
		String progressInDuration = "    [" + this.getProgress() + "/" + this.getDuration() + "]";
		ret = "[" + this.name + "]-[" + start + " ~ " + end + "]-[" + actualStart + " ~ " + actualEnd + "]" + progressInDuration;
		if (this.description != null) {
			ret += "    " + this.description;
		}
		return ret;
	}

	/**
	 * Task name, actual start and end time.
	 * 
	 * @return
	 */
	public String toSimpleString() {
		String ret = "";
		String actualStart = formatTimeByUnit(this.actualStart);
		String actualEnd = formatTimeByUnit(this.actualEnd);
		ret = "[" + this.name + "]-[" + actualStart + " ~ " + actualEnd + "]";
		return ret;
	}

	private String formatTimeByUnit(Time time) {
		String timeStr = (time == null) ? "--::" : null;
		if (time != null) {
			if (this.treeModel == null || this.treeModel.getTimeUnit() == null) {
				timeStr = TaskHelper.formatTimeByTimeUnit(TimeUnit.defaultUnit, time);
			} else {
				TimeUnit tu = this.treeModel.getTimeUnit();
				if (TaskHelper.isAllowAccurateTaskBar(tu)) {
					tu = TimeUnit.getAccurateTimeUnit(tu);
				}
				timeStr = TaskHelper.formatTimeByTimeUnit(tu, time);
			}
		}
		return timeStr;
	}
}
