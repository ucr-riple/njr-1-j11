package org.swiftgantt.ui.timeaxis;


import org.swiftgantt.Config;
import org.swiftgantt.GanttChart;
import org.swiftgantt.common.CalendarUtils;
import org.swiftgantt.common.Time;
import org.swiftgantt.model.Task;
import org.swiftgantt.ui.TimeUnit;

/**
 * Utils for time axis.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class TimeAxisUtils {

	/**
	 * Calculate interval between actual start time and actual end time of a task.
	 * 
	 * @param tu
	 * @param task
	 * @return
	 */
	public static int getActualTimeIntervalByTimeUnit(TimeUnit tu, Task task) {
		return getTimeIntervalByTimeUnit(tu, task.getActualStart(), task.getActualEnd());
	}

	/**
	 * Including rest-out time.
	 * 
	 * @param timeUnit
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getTimeIntervalByTimeUnit(TimeUnit timeUnit, Time start, Time end) {
		int ret = 0;
		if (timeUnit == TimeUnit.Hour) {
			// Serveral rest-out hours merge to one step.
			Config cfg = GanttChart.getStaticConfig();
			int startHour = cfg.getWorkingHoursSpanOfDay()[0];
			int hours = cfg.getWorkingHoursOfDay();
			ret = CalendarUtils.calcWorkingHourDurationLen(start, end, hours, startHour) - 1;// -1 because need return interval, not duration
		} else if (timeUnit == TimeUnit.AllDay) {
			ret = start.getHourIntervalTo(end);//CalendarUtils.getHourInterval(start, end);
		} else if (timeUnit == TimeUnit.Day) {
			ret = start.getDayIntervalTo(end);//CalendarUtils.getDateInterval(start, end);
		} else if (timeUnit == TimeUnit.Week) {
			ret = start.getWeekIntervalTo(end);//CalendarUtils.getWeekInterval(start, end);
		} else if (timeUnit == TimeUnit.Month) {
			ret = start.getMonthIntervalTo(end);//CalendarUtils.getMonthInterval(start, end);
		} else if (timeUnit == TimeUnit.Year) {
			ret = start.getYearIntervalTo(end);//CalendarUtils.getYearInterval(start, end);
		}
		return ret;
	}

	/**
	 * Calculate accurate time portion by time unit for accurate task bar rendering.
	 * 
	 * @since 0.3.2
	 * @param timeUnit
	 * @param time
	 * @return
	 */
	public static float calcAccurateTimePortionByTimeUnit(TimeUnit timeUnit, Time time) {
		float ret = 0;
		if (timeUnit == TimeUnit.Hour) {
			ret = (time.get(Time.MINUTE) + 1) / 60F;
		} else if (timeUnit == TimeUnit.AllDay) {
			ret = time.get(Time.MINUTE) / 60F;
		} else if (timeUnit == TimeUnit.Day) {
			ret = (time.get(Time.HOUR_OF_DAY) + 1) / 24F;
		} else if (timeUnit == TimeUnit.Week) {
			ret = time.get(Time.DAY_OF_WEEK) / 7F;
		} else if (timeUnit == TimeUnit.Month) {
			float days = CalendarUtils.getDaysOfMonth(time);
			ret = time.get(Time.DAY_OF_MONTH) / days;
		} else if (timeUnit == TimeUnit.Year) {
			ret = (time.get(Time.MONTH) + 1) / 12F;
		}
		return ret;
	}

}
