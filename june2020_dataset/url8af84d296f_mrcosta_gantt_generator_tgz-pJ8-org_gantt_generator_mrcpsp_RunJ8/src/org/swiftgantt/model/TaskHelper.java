package org.swiftgantt.model;


import org.apache.commons.lang.time.DateFormatUtils;

import org.swiftgantt.Config;
import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Constants;
import org.swiftgantt.common.Time;
import org.swiftgantt.ui.TimeUnit;

/**
 * 
 * @author Yuxing Wang
 * @since 0.2.4
 */
public class TaskHelper {

	private TaskHelper() {
	}

	/**
	 * Format time to display.
	 * 
	 * @param timeUnit
	 * @param time
	 * @return
	 */
	public static String formatTimeByTimeUnit(TimeUnit timeUnit, Time time) {
		String retValue = null;
		if (timeUnit == TimeUnit.Hour || timeUnit == TimeUnit.AllDay) {
			retValue = DateFormatUtils.format(time.getTimeInMillis(), Constants.TIME_FORMAT_YEAR_HOUR_BAR);
			//retValue = TimeFormatter.formatCalendar(time, TimeFormatter.TIME_FORMAT_YYYY_MM_DD_HH_MM_SS_BAR);
		} else if (timeUnit == TimeUnit.Day) {
			retValue = DateFormatUtils.format(time.getTimeInMillis(), "yyyy-MM-ddEEE");
		} else if (timeUnit == TimeUnit.Week) {
			retValue = DateFormatUtils.format(time.getTimeInMillis(), Constants.TIME_FORMAT_YYYY_MM_DD_BAR);
		} else if (timeUnit == TimeUnit.Month) {
			retValue = DateFormatUtils.format(time.getTimeInMillis(), Constants.TIME_FORMAT_YYYY_MM_BAR);
		} else if (timeUnit == TimeUnit.Year) {
			retValue = time.get(Time.YEAR) + "";
		}
		return retValue;
	}

	/**
	 * Time offset from start time to end time.
	 * 
	 * @param timeUnit
	 * @param start
	 * @param end
	 * @return
	 */
	public static int calcOffsetByTimeUnit(TimeUnit timeUnit, Time start, Time end) {
		int offset = 0;
		if (timeUnit == TimeUnit.Hour || timeUnit == TimeUnit.AllDay) {
			offset = start.getHourIntervalTo(end);
//			offset = CalendarUtils.getHourInterval(start, end);
		} else if (timeUnit == TimeUnit.Day) {
			offset = start.getDayIntervalTo(end);
//			offset = CalendarUtils.getDateInterval(start, end);
		} else if (timeUnit == TimeUnit.Week) {
			offset = start.getWeekIntervalTo(end);
//			offset = CalendarUtils.getWeekInterval(start, end);
		} else if (timeUnit == TimeUnit.Month) {
			offset = start.getMonthIntervalTo(end);
//			offset = CalendarUtils.getMonthInterval(start, end);
		} else if (timeUnit == TimeUnit.Year) {
			offset = start.getYearIntervalTo(end);
//			offset = CalendarUtils.getYearInterval(start, end);
		}
		return offset;
	}

	/**
	 * Increase corresponding field of time by TimeUnit.
	 * 
	 * @param timeUnit
	 * @param time
	 * @param offset
	 * @return
	 */
	public static Time increaseCloneTimeByTimeUnit(TimeUnit timeUnit, Time time, int offset) {
		if (time == null) {
			return null;
		}
		Time ret = time.clone();
		increaseTimeByTimeUnit(timeUnit, ret, offset);
		return ret;
	}

	/**
	 * Increase corresponding field of time by TimeUnit.
	 * 
	 * @param timeUnit
	 * @param time
	 * @param offset
	 */
	public static void increaseTimeByTimeUnit(TimeUnit timeUnit, Time time, int offset) {
		if (timeUnit == TimeUnit.Hour || timeUnit == TimeUnit.AllDay) {
			time.increaseHours(offset);
//			time.add(Time.HOUR_OF_DAY, offset);
		} else if (timeUnit == TimeUnit.Day) {
			time.increaseDates(offset);
//			time.add(Time.DATE, offset);
		} else if (timeUnit == TimeUnit.Week) {
			time.increaseWeeks(offset);
//			time.add(Time.WEEK_OF_YEAR, offset);
		} else if (timeUnit == TimeUnit.Month) {
			time.increaseMonths(offset);
//			time.add(Time.MONTH, offset);
		} else if (timeUnit == TimeUnit.Year) {
			time.increaseYears(offset);
//			time.add(Time.YEAR, offset);
		}
	}

	/**
	 * 
	 * @param timeUnit
	 * @return
	 */
	public static int getTimeCycleStepsByTimeUnit(TimeUnit timeUnit) {
		Config config = GanttChart.getStaticConfig();
		if (timeUnit == TimeUnit.Hour) {
			return Config.HOURLY_PRE_REST_STEPS + config.getWorkingHoursOfDay() + Config.HOURLY_SEQ_REST_STEPS;
		} else if (timeUnit == TimeUnit.AllDay) {
			return 24;
		} else if (timeUnit == TimeUnit.Day) {
			return Config.DAILY_TOTAL_DAYS_OF_WEEK;
		} else if (timeUnit == TimeUnit.Week) {
			return 52;
		} else if (timeUnit == TimeUnit.Month) {
			return 12;
		} else if (timeUnit == TimeUnit.Year) {
			return 100;
		}
		return 0;
	}

	/**
	 * Calculate actual steps for provided progress.
	 * 
	 * @param progress
	 * @param startIndex Start index starts from 1
	 * @param totalSteps Total cycle steps of working and res out steps.
	 * @param preRestSteps Rest out steps before working steps.
	 * @param workSteps Working steps
	 * @param seqRestSteps Rest out steps after working steps.
	 * @return
	 */
	public static int calcActualStepForProgress(int progress, int startIndex, int totalSteps, int preRestSteps, int workSteps,
			int seqRestSteps) {
		int ret = 0;
//		int total = totalSteps;
		int pre = preRestSteps;
		int seq = seqRestSteps;
		// Calculate the first segment.
		int actualWorkTime = 0;
		if (startIndex <= pre) {// Start at previous rest time in first cycle.
			actualWorkTime = workSteps;
			ret = workSteps;
		} else if (startIndex > (pre + workSteps)) {//Start at sequent rest time in first cycle
			ret = 0;// Ignore these rest-time.
		} else {//Start at working time.
			actualWorkTime = workSteps + pre - startIndex + 1;
			ret = actualWorkTime;// + seq;// Actual working steps plus to sequent rest time.
		}
		if (progress <= actualWorkTime) {//End at first segment.
			return progress;
		} else {
			progress -= actualWorkTime;//Reduce working steps and forward next caculating.
		}
		// Loop whole segments.
		while (progress >= workSteps) {
			progress -= workSteps;
			ret += (seq + pre + workSteps);//Last cycle seq rest-out + this cycle pre rest-out + working steps each cycle.
		}
		if (progress > 0) {
			ret += (seq + pre + progress);
		}
		return ret;
	}

	/**
	 * Check whether accurate task bar is allowed. See <code>Config.allowAccurateTaskBar</code>
	 *
	 * @since 0.3.2
	 * @param tu
	 * @return
	 */
	public static boolean isAllowAccurateTaskBar(TimeUnit timeUnit) {
		return GanttChart.getStaticConfig().isAllowAccurateTaskBar();
				//&& timeUnit != TimeUnit.Hour && timeUnit != TimeUnit.AllDay;
	}
}
