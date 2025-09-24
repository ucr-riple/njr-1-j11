package org.swiftgantt.ui.timeaxis;

import java.util.Locale;

import org.swiftgantt.GanttChart;
import org.swiftgantt.common.ResourceManager;
import org.swiftgantt.common.Time;

/**
 * Represent the time axis for yearly time unit.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class YearlyTimeAxis extends BaseNoRestoutTimeAxis {

	public YearlyTimeAxis(GanttChart gantt) {
		super(gantt);
		super.time_offset = 0;
		STEPS_OF_MAJOR_SCALE = 100; // A century
	}

	@Override
	protected Time getFirstStepTimeOfChart(Time kickoffTime) {
		return kickoffTime.clone().increaseYears(0 - super.config.getBlankStepsToKickoffTime());
	}

	@Override
	protected int calcPositionInMajorScale(Time time) {
		return time.getYear() % 100; // Retrun the year in the century.
	}

	@Override
	protected String formatToMajorLabel(Time time, int offset) {
		int century = (time.getYear() / 100 + offset) + 1;
		//		Locale locale = ResourceManager.getInstance().getLocale();
		String prefix = "";
		//		if(locale.equals(Locale.ENGLISH)){ // For later customized locale
		if (Locale.getDefault().equals(Locale.ENGLISH)) {
			if (century % 10 == 1) {
				prefix = "st ";
			} else if (century % 10 == 2) {
				prefix = "nd ";
			} else if (century % 10 == 3) {
				prefix = "rd ";
			} else {
				prefix = "th ";
			}
		}
		String centurySuffix = ResourceManager.getInstance().getString("Gantt.TimeAxis.Year.Century");
		return "TIME";//century + prefix + centurySuffix;
	}

}
