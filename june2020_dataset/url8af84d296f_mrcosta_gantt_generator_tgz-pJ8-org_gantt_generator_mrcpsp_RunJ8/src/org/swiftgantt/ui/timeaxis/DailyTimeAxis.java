package org.swiftgantt.ui.timeaxis;

import java.awt.Color;

import org.apache.commons.lang.time.DateFormatUtils;

import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Constants;
import org.swiftgantt.common.ResourceManager;
import org.swiftgantt.common.Time;

/**
 * Represent the time axis for daily time unit.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class DailyTimeAxis extends BaseRestoutTimeAxis {

	public DailyTimeAxis(GanttChart gantt) {
		super(gantt);
		STEPS_OF_MAJOR_SCALE = 7;
	}

	@Override
	protected Time getFirstStepTimeOfChart(Time kickoffTime) {
		return kickoffTime.clone().increaseDates(0 - super.config.getBlankStepsToKickoffTime());
	}

	@Override
	protected int calcPositionInMajorScale(Time time) {
		return time.getDayOfWeek();
	}

	@Override
	protected String formatToMajorLabel(Time time, int offset) {
		Time firstDayOfWeek = time.clone().increaseDates(super.stepsToFirstMajorScale + offset * STEPS_OF_MAJOR_SCALE);
		String label = DateFormatUtils.format(firstDayOfWeek.getTime(), Constants.TIME_FORMAT_YYYY_MM_DD_SLASH);
		return label;
	}

	@Override
	protected boolean isFreeTime(int step) {
		Time day = kickoffTime.clone().increaseDates((step - super.config.getBlankStepsToKickoffTime()) % 7);
		//logger.debug(DateFormatUtils.format(theDate.getTime(),"yyyy/MM/dd") + " by " + (step - DEFAULT_STEPS_TO_KICKOFF_TIME)%7);
		int dayOfWeek = day.getDayOfWeek();
		int[] wdSpan = super.gantt.getConfig().getWorkingDaysSpanOfWeek();
		if (dayOfWeek >= wdSpan[0] && dayOfWeek <= wdSpan[1]) {
			return true;
		}
		return false;
	}

	@Override
	protected void drawMinorTimeScaleLabel(int i, int x, int y) {
		Time theTime = super.kickoffTime.clone().increaseDates(i - super.config.getBlankStepsToKickoffTime());
//		theTime.add(Time.DATE, i - super.config.getBlankStepsToKickoffTime());
		String str = null;
		switch (theTime.getDayOfWeek()) {
			case Time.MONDAY:
				str = "Gantt.TimeAxis.Date.Monday";
				break;
			case Time.TUESDAY:
				str = "Gantt.TimeAxis.Date.Tuesday";
				break;
			case Time.WEDNESDAY:
				str = "Gantt.TimeAxis.Date.Wednesday";
				break;
			case Time.THURSDAY:
				str = "Gantt.TimeAxis.Date.Thursday";
				break;
			case Time.FRIDAY:
				str = "Gantt.TimeAxis.Date.Friday";
				break;
			case Time.SATURDAY:
				str = "Gantt.TimeAxis.Date.Saturday";
				break;
			case Time.SUNDAY:
				str = "Gantt.TimeAxis.Date.Sunday";
				break;
		}
		g.setColor(Color.black);
		char[] label = ResourceManager.getInstance().getString(str).toCharArray();
		g.drawChars(label, 0, label.length, x + 4, y + super.row_height - 4);
	}
}
