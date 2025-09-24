package org.swiftgantt.ui.task;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.Config;
import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Time;
import org.swiftgantt.model.Task;
import org.swiftgantt.ui.TimeUnit;
import org.swiftgantt.ui.timeaxis.TimeAxisUtils;

/**
 * The wrapper class to wrap Task for UI renderer.
 * 
 * @author Yuxing Wang
 * @since 0.3.2
 */
public class TaskWrapper {

	private Logger logger = null;
	private Task task = null;

	private int rowIndex;// Row index, starts from 0;

	private Map<TimeUnit, Float> startTimeVacancyCache = new HashMap<TimeUnit, Float>();
	private Map<TimeUnit, Float> endTimeExcessCache = new HashMap<TimeUnit, Float>();

	public TaskWrapper(Task task) {
		super();
		logger = LogManager.getLogger(TaskWrapper.class);
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	/**
	 * Wrap tasks.
	 * @param tasks
	 * @return
	 */
	public static List<TaskWrapper> wrapTasks(List<Task> tasks) {
		List<TaskWrapper> ret = new ArrayList<TaskWrapper>(tasks.size());
		int i = 0;
		for (Task t : tasks) {
			TaskWrapper tw = new TaskWrapper(t);
			tw.rowIndex = i++;
			ret.add(tw);
		}
		return ret;
	}

	/**
	 * Wrap tasks, if exists, use existence ones.
	 * @param existWrappers
	 * @param tasks
	 * @return
	 */
	public static List<TaskWrapper> wrapTasks(List<TaskWrapper> existWrappers, List<Task> tasks) {
		List<TaskWrapper> ret = new ArrayList<TaskWrapper>(tasks.size());
		int i = 0;
		for (Task t : tasks) {
			boolean existed = false;
			for (TaskWrapper tw : existWrappers) {
				if (tw.getTask() == t) {
					ret.add(tw);
					existed = true;
					break;
				}
			}
			if (existed == false) {
				TaskWrapper tw = new TaskWrapper(t);
				tw.rowIndex = i++;
				ret.add(tw);
			}
		}
		return ret;
	}

	/**
	 * 
	 * @return
	 */
	public int calcTaskSteps() {
		return this.task.calcTaskSteps();
	}

	/**
	 * Calculate X of the start point of task.
	 * @param rect
	 * @param tu
	 * @param kickoffTime
	 * 
	 * @return
	 */
	public int calcTaskStartPointX(Rectangle rect, TimeUnit tu, Time kickoffTime) {
		if (logger.isDebugEnabled()) {
			logger.debug("    Calculate X for Start time of task: " + task);
		}
		Time startTime = null;
		if (task.isLeaf() == true) {
			startTime = task.getActualStart();
		} else {
			startTime = task.getEarliestSubTask().getActualStart();
			if (startTime.after(task.getActualStart())) {
				startTime = task.getActualStart();
			}
		}
		int intervalToKickoff = TimeAxisUtils.getTimeIntervalByTimeUnit(tu, kickoffTime, startTime);
		Config config = GanttChart.getStaticConfig();
		int x = (intervalToKickoff + config.getBlankStepsToKickoffTime()) * config.getTimeUnitWidth();
		if (logger.isDebugEnabled()) {
			logger.debug("      = " + intervalToKickoff + "*" + config.getTimeUnitWidth() + " + (kickoff point x) = " + x);
		}
		return x + rect.x;//adds left border offset
	}

	/**
	 * Calculate X of the end point of task
	 * 
	 * @param startPX
	 * @return
	 */
	public int calcTaskEndPointX(int startPX) {
		if (logger.isDebugEnabled()) {
			logger.debug("    Calculate X for End time of task: " + task);
		}
		int ret = 0;
		ret = startPX + task.calcTaskSteps() * GanttChart.getStaticConfig().getTimeUnitWidth();
		if (logger.isDebugEnabled()) {
			logger.debug("      = " + ret);
		}
		return ret;
	}

	/**
	 * 
	 * @param rect
	 * @param rowNum
	 * @param spaceToBar
	 * @return
	 */
	public int calcTaskPointY(Rectangle rect, int rowNum, int spaceToBar) {
		if (logger.isDebugEnabled()) {
			logger.debug("    Calculate Y of task: " + task + " for row: " + rowNum);
		}
		int y = rect.y + GanttChart.getStaticConfig().getGanttChartRowHeight() * rowNum + spaceToBar;
		if (logger.isDebugEnabled()) {
			logger.debug("      = " + y);
		}
		return y;
	}

	/**
	 * 
	 * @param tu
	 * @return
	 */
	public float getStartTimeVacancy(TimeUnit tu) {
		Float f = this.startTimeVacancyCache.get(tu);
		if (f == null) {
			f = TimeAxisUtils.calcAccurateTimePortionByTimeUnit(tu, task.getActualStart());
			//			e = (e.floatValue() > 0 ? 0 : e.floatValue());
			startTimeVacancyCache.put(tu, f);
		}
		return f.floatValue();
	}

	/**
	 * 
	 * @param tu
	 * @return
	 */
	public float getEndTimeExcees(TimeUnit tu) {
		Float e = this.endTimeExcessCache.get(tu);
		if (e == null) {
			e = TimeAxisUtils.calcAccurateTimePortionByTimeUnit(tu, task.getActualEnd());
		}
		return e.floatValue();
	}

	public int getRowIndex() {
		return rowIndex;
	}

	@Override
	public String toString() {
		return this.task.toSimpleString();
	}

}
