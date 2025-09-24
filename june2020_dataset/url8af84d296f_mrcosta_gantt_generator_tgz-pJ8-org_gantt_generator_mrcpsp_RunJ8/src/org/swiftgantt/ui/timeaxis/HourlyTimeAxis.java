package org.swiftgantt.ui.timeaxis;

import java.awt.Color;

import org.apache.commons.lang.time.DateFormatUtils;

import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Constants;
import org.swiftgantt.common.Time;

/**
 * Represent the time axis for hourly time unit.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class HourlyTimeAxis extends BaseRestoutTimeAxis {

	public HourlyTimeAxis(GanttChart gantt) {
		super(gantt);
		STEPS_OF_MAJOR_SCALE = gantt.getConfig().getWorkingHoursOfDay() + 1;
	}

	@Override
	protected void beforePaint() {
		STEPS_OF_MAJOR_SCALE = gantt.getConfig().getWorkingHoursOfDay() + 1;
	}

	@Override
	protected Time getFirstStepTimeOfChart(Time kickoffTime) {
		return kickoffTime.clone().increaseHours(0 - super.config.getBlankStepsToKickoffTime());
	}

	@Override
	protected int calcPositionInMajorScale(Time time) {
		int retValue = 0;
		retValue = time.getHour() - GanttChart.getStaticConfig().getWorkingHoursSpanOfDay()[0] + 1;
		if (retValue > STEPS_OF_MAJOR_SCALE)
			retValue = STEPS_OF_MAJOR_SCALE;
		if (retValue <= 0) {
			retValue = 1;
		}
		return retValue;
	}

	@Override
	protected String formatToMajorLabel(Time time, int offset) {
		String label = DateFormatUtils.format(time.clone().increaseDates(offset).getTime(), Constants.TIME_FORMAT_YYYY_MM_DD_BAR);
		return label;
	}

	@Override
	protected boolean isFreeTime(int step) {
		int posInMajorScale = 0;
		if (step >= super.stepsToFirstMajorScale) {
			posInMajorScale = (step - super.stepsToFirstMajorScale) % STEPS_OF_MAJOR_SCALE;
			return posInMajorScale == gantt.getConfig().getWorkingHoursOfDay();
		} else {
			return step == super.stepsToFirstMajorScale - 1;
		}
	}
	
	@Override
	protected void drawMinorTimeScaleLabel(int i, int x, int y) {
		if (isFreeTime(i) == true)
			return;
		int hour = 0;
		hour = (i - super.stepsToFirstMajorScale + STEPS_OF_MAJOR_SCALE) % STEPS_OF_MAJOR_SCALE;
		String str = (hour + GanttChart.getStaticConfig().getWorkingHoursSpanOfDay()[0]) + "";
		g.setColor(Color.black);
		g.drawChars(str.toCharArray(), 0, str.length(), x + 4, y + super.row_height - 4);
	}

}
