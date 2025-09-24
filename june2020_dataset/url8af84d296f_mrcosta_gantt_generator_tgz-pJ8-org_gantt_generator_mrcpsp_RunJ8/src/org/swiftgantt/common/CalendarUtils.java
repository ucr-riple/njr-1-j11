package org.swiftgantt.common;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.Config;
import java.util.GregorianCalendar;

/**
 * Common utils for Time.
 * 
 * @author Yuxing Wang
 * @version 1.0
 * 
 */
public class CalendarUtils {
	//这里所有的方法都可以重构到Time类里面。

	static Logger logger = null;


	static {
		logger = LogManager.getLogger(CalendarUtils.class);
	}
	static final int[] DAY_OF_MONTH = new int[12];


	static {
		DAY_OF_MONTH[0] = 31;
		DAY_OF_MONTH[1] = 28;// 29 when leap year.
		DAY_OF_MONTH[2] = 31;
		DAY_OF_MONTH[3] = 30;
		DAY_OF_MONTH[4] = 31;
		DAY_OF_MONTH[5] = 30;
		DAY_OF_MONTH[6] = 31;
		DAY_OF_MONTH[7] = 31;
		DAY_OF_MONTH[8] = 30;
		DAY_OF_MONTH[9] = 31;
		DAY_OF_MONTH[10] = 30;
		DAY_OF_MONTH[11] = 31;
	}

	/**
	 * Get amount of days in a month
	 * 
	 * @param month
	 * @return int
	 * @deprecated to Time
	 */
	public static int getDaysOfMonth(Time Time) {
		if (Time == null) {
			throw new NullPointerException("No time provided");
		}
		int year = Time.get(Time.YEAR);
		int month = Time.get(Time.MONTH);
		if (month == 1 && ((GregorianCalendar) Time).isLeapYear(year)) {
			return 29;
		}
		return DAY_OF_MONTH[month];
	}

	/**
	 * Truncate time by given Time filed, exclude any Week.
	 * @param time
	 * @param field The field of Time to keep.
	 * @deprecated to Time
	 * @return
	 */
	public static void truncateTime(Time time, int field) {
		switch (field) {
			case Time.YEAR:
				time.set(Time.MONTH, 0);
			case Time.MONTH:
				time.set(Time.DAY_OF_MONTH, 1);
//		case Time.WEEK_OF_YEAR:
//			time.set(Time.DAY_OF_WEEK, Time.SUNDAY);
			case Time.DAY_OF_MONTH:
				time.set(Time.HOUR_OF_DAY, 0);
			case Time.HOUR:
			case Time.HOUR_OF_DAY:
				time.set(Time.MINUTE, 0);
		}
		time.set(Time.SECOND, 0);
		time.set(Time.MILLISECOND, 0);
	}

	/**
	 * 
	 * @param start
	 * @param dates
	 * @return
	 */
	public static int calcWorkingDays(Time start, int dates) {
		Time end = start.clone().increaseDates(dates);
//		end.add(Time.DATE, dates);
		return calcWorkingDays(start, end, null);
	}

	/**
	 * Calculate working days from start day to end day(incluing end day). workingDurationInWeek represent the index of start working day
	 * and end working day in a week.
	 * 
	 * @param start
	 * @param end
	 * @param workingDurationInWeek
	 * @return
	 */
	public static int calcWorkingDays(Time start, Time end, int[] workingDurationInWeek) {
		//		if (start.after(end)) {
		//			return 0;
		//		}
		int workingDays = 0;
		if (workingDurationInWeek == null || workingDurationInWeek.length < 2) {
			workingDurationInWeek = Config.DEFAULT_WORKING_DAYS_SPAN_OF_WEEK;
		}
		int f = workingDurationInWeek[0];
		int t = workingDurationInWeek[1];
		if (f < 0 || t > 7 || f > t) {
			throw new IllegalArgumentException("Working duration in week is invalid");
		}
		int workDaysInWeek = (t - f) + 1; // Working days in week.
		int restDaysInWeek = (7 - workDaysInWeek);// Rest-out days in week.
		//		logger.debug("workDaysInWeek:" + workDaysInWeek);
		//		logger.debug("f:" + f);
		//		logger.debug("t:" + t);
//		int duration = CalendarUtils.getDateInterval(start, end) + 1;
		int duration = start.getDayIntervalTo(end)+ 1;
		if (duration <= 0) {
			return 0;
		}
		int dayOfWeekOfStart = start.getDayOfWeek();
		if (dayOfWeekOfStart < f) {// Start at Sun or other rest-out day after Sun.
			dayOfWeekOfStart = f;// Adjust to next Monday.
		}
		if (dayOfWeekOfStart > t) {// Start at Sat or other rest-out day before Sat.
			dayOfWeekOfStart = f;// Adjust to next Monday.
			duration -= restDaysInWeek; //Reduce duration by rest-out days.
		}
		//		logger.debug("dayOfWeekOfStart:" + dayOfWeekOfStart);
		int dayOfWeekOfEnd = end.getDayOfWeek();
		if (dayOfWeekOfEnd < f) { // End at Sun or other rest-out day after Sun.
			dayOfWeekOfEnd = t;//Adjust to latest working day.
			duration -= restDaysInWeek; //Reduce duration by rest-out days.
		}
		if (dayOfWeekOfEnd > t) { // End at Sat or other rest-out day before Sat.
			dayOfWeekOfEnd = t;//Adjust to last working day of this week.
		}
		int offset = dayOfWeekOfEnd - dayOfWeekOfStart + 1;

		int weeks = 0;
		if (offset < 0) {
			weeks = duration >= 0 ? duration / 7 + 1 : -1;
		} else {
			weeks = duration >= 0 ? duration / 7 : -1;
		}
		workingDays = weeks * workDaysInWeek;
		workingDays += offset;
		return workingDays;
	}

	/**
	 * Calculate hours from start time to end time, with all rest-out hours as one hour.
	 * 
	 * @param start
	 * @param end
	 * @param workingHoursOfDay Working hours of a day.
	 * @param startHour
	 * @return
	 */
	public static int calcWorkingHourDurationLen(Time start, Time end, int workingHoursOfDay, int startHour) {
		int hours = 0;
		// Is work starts and ends at working day.
		int shIndex = start.getHour();//start.getHour();
		int ehIndex = end.getHour();//end.getHour();
		boolean isInWorkingDuration = shIndex >= startHour && ehIndex <= (workingHoursOfDay + startHour - 1);
		boolean isStartAndEndAtRestout = shIndex < startHour && ehIndex > (workingHoursOfDay + startHour - 1);
		if (DateUtils.isSameDay(start, end)) {
			//In same day, it's easy!
			if (isInWorkingDuration) {
				hours = end.getHour() - start.getHour() + 1;
			} else if (isStartAndEndAtRestout) {
				hours = workingHoursOfDay;
			} else if (shIndex < startHour) {
				hours = ehIndex - startHour + 1;
			} else if (ehIndex > (workingHoursOfDay + startHour - 1)) {
				hours = (startHour + workingHoursOfDay - 1) - shIndex + 1;
			}
		} else {
			// In different day.
			hours = calcWorkingHours(start, end, workingHoursOfDay + 1, startHour);
		}
		return hours;
	}

	/**
	 * Calculate actual working hours, by specifying working hours of day and working start hour, in time duration.
	 * 
	 * @param start
	 * @param end
	 * @param workingHoursOfDay
	 * @param startHour
	 * @return
	 */
	public static int calcWorkingHours(Time start, Time end, int workingHoursOfDay, int startHour) {
		// Calculate working hours in first day.
		int hoursInFirstDay = workingHoursOfDay - (start.getHour() - startHour);
		if (hoursInFirstDay < 0) {
			hoursInFirstDay = 0;
		} else if (hoursInFirstDay > workingHoursOfDay) {
			hoursInFirstDay = workingHoursOfDay;
		}
		//		logger.debug("hoursInKickoffDay: " + hoursInFirstDay);
		// Calculate working hours in last day.
		int hoursInLastDay = end.getHour() - startHour + 1;
		if (hoursInLastDay < 0) {
			hoursInLastDay = 0;
		} else if (hoursInLastDay > workingHoursOfDay) {
			hoursInLastDay = workingHoursOfDay;
		}
		//		logger.debug("hoursInDeadlineDay: " + hoursInLastDay);
//		int day = getDateInterval(start, end) - 1;
		int day = start.getDayIntervalTo(end) - 1;
		int ret = hoursInFirstDay + hoursInLastDay + day * workingHoursOfDay;
		//		logger.debug("Result : " + ret);
		return ret;
	}

	/**
	 * 
	 * @param time
	 * @param offset
	 * @deprecated to Time
	 * @return
	 */
	public static Time cloneTimeByDateOffset(Time time, int offset) {
		Time retTime = time.clone();
		retTime.add(Time.DATE, offset);
		return retTime;
	}

	/**
	 * 
	 * @param time
	 * @param offset
	 * @deprecated to Time
	 * @return
	 */
	public static Time cloneTimeByHourOffset(Time time, int offset) {
		Time retTime = time.clone();
		retTime.add(Time.HOUR_OF_DAY, offset);
		return retTime;
	}

	/**
	 * 
	 * @param time
	 * @param offset
	 * @deprecated to Time
	 * @return
	 */
	public static Time cloneTimeByWeekOffset(Time time, int offset) {
		Time retTime = time.clone();
		retTime.add(Time.WEEK_OF_YEAR, offset);
		return retTime;
	}

	/**
	 * 
	 * @param time
	 * @param offset
	 * @deprecated to Time
	 * @return
	 */
	public static Time cloneTimeByMonthOffset(Time time, int offset) {
		Time retTime = time.clone();
		retTime.add(Time.MONTH, offset);
		return retTime;
	}

	/**
	 * 
	 * @param time
	 * @param offset
	 * @deprecated to Time
	 * @return
	 */
	public static Time cloneTimeByYearOffset(Time time, int offset) {
		Time retTime = time.clone();
		retTime.add(Time.YEAR, offset);
		return retTime;
	}
}
