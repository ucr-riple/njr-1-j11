package org.swiftgantt.ui.timeaxis;


import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Time;

/**
 * Represent the time axis for weekly time unit.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class WeeklyTimeAxis extends BaseNoRestoutTimeAxis {

	public WeeklyTimeAxis(GanttChart gantt) {
		super(gantt);
		STEPS_OF_MAJOR_SCALE = 52; // Weeks in A Year
	}

	@Override
	protected String formatToMajorLabel(Time time, int offset) {
		return time.getYear() + offset + "";
	}

	@Override
	protected Time getFirstStepTimeOfChart(Time kickoffTime) {
		return kickoffTime.clone().increaseWeeks(0 - super.config.getBlankStepsToKickoffTime());
	}

	@Override
	protected int calcPositionInMajorScale(Time time) {
		return time.get(Time.WEEK_OF_YEAR);
	}

}
