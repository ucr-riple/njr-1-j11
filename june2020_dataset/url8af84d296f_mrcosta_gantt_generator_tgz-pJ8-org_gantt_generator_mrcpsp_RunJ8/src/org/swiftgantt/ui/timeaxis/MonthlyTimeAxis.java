package org.swiftgantt.ui.timeaxis;


import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Time;

/**
 * Represent the time axis for monthly time unit.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class MonthlyTimeAxis extends BaseNoRestoutTimeAxis {

	public MonthlyTimeAxis(GanttChart gantt) {
		super(gantt);
		STEPS_OF_MAJOR_SCALE = 12; // A Year
	}

	@Override
	protected int calcPositionInMajorScale(Time time) {
		return time.getMonth();
	}

	@Override
	protected Time getFirstStepTimeOfChart(Time kickoffTime) {
		return kickoffTime.clone().increaseMonths(0 - super.config.getBlankStepsToKickoffTime());
	}

	@Override
	protected String formatToMajorLabel(Time time, int offset) {
		return time.getYear() + offset + "";
	}

}
