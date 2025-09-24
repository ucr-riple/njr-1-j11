package org.swiftgantt.ui.timeaxis;

import org.apache.commons.lang.time.DateFormatUtils;

import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Constants;
import org.swiftgantt.common.Time;

/**
 * All day, 24 hours.
 * @author Yuxing Wang
 * @since 0.3.0
 */
public class AllDayTimeAxis extends BaseNoRestoutTimeAxis {

	public AllDayTimeAxis(GanttChart gantt) {
		super(gantt);
		super.time_offset = 0;
		STEPS_OF_MAJOR_SCALE = 24; // A Year
	}

	@Override
	protected int calcPositionInMajorScale(Time time) {
		return time.getHour();
	}

	@Override
	protected String formatToMajorLabel(Time time, int offset) {
		String label = DateFormatUtils.format(time.clone().increaseDates(offset).getTimeInMillis(), Constants.TIME_FORMAT_YYYY_MM_DD_BAR);
		return label;
	}

	@Override
	protected Time getFirstStepTimeOfChart(Time kickoffTime) {
		return kickoffTime.clone().increaseHours(0 - super.config.getBlankStepsToKickoffTime());
	}
}
