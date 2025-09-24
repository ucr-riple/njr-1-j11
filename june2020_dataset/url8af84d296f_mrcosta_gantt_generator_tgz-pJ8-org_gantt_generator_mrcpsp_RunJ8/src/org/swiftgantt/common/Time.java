package org.swiftgantt.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * TODO
 * Extended GregorianCalendar with more common operation
 * <li>Retrieve partial of time, <code>getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond()</code>
 * <li>Truncate time by kinds of time units.
 * <li>Increase and decrease time by time units.
 * <li><code>setToFirstDayOfThisWeek(), getFirstDayOfThisWeek(), setToLastDayOfThisWeek(), getLastDayOfThisWeek()</code>
 * @author Wang Yuxing
 */
public class Time extends GregorianCalendar {

	public Time(Calendar calendar) {
		this.setTime(calendar.getTime());
	}

	public Time(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
		super(year, month - 1, dayOfMonth, hourOfDay, minute, second);
	}

	public Time(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
		super(year, month - 1, dayOfMonth, hourOfDay, minute);
	}
	
	public Time(int year, int month, int dayOfMonth, int hourOfDay) {
		super(year, month - 1, dayOfMonth, hourOfDay, 0);
	}

	public Time(int year, int month, int dayOfMonth) {
		super(year, month - 1, dayOfMonth);
	}

	public Time(int year, int month) {
		super(year, month - 1, 1);
	}

	public Time(int year) {
		super(year, 0, 1);
	}

	public Time(TimeZone zone, Locale aLocale) {
		super(zone, aLocale);
	}

	public Time(Locale aLocale) {
		super(aLocale);
	}

	public Time(TimeZone zone) {
		super(zone);
	}

	public Time(Date date) {
		this.setTime(date);
	}

	public Time() {
	}

	@Override
	public Time clone() {
		return (Time) super.clone();
	}

	public Time increaseYears(int offset) {
		this.add(YEAR, offset);
		return this;
	}

	public Time increaseYear() {
		return increaseYears(1);
	}

	public Time increaseMonths(int offset) {
		this.add(MONTH, offset);
		return this;
	}

	public Time increaseMonth() {
		return this.increaseMonths(1);
	}

	public Time increaseWeeks(int offset) {
		this.add(DATE, offset * 7);
		return this;
	}

	public Time increaseWeek() {
		return increaseWeeks(1);
	}

	public Time increaseDates(int offset) {
		this.add(DATE, offset);
		return this;
	}

	public Time increaseDate() {
		return this.increaseDates(1);
	}

	public Time increaseHours(int offset) {
		this.add(HOUR_OF_DAY, offset);
		return this;
	}

	public Time increaseHour() {
		return this.increaseHours(1);
	}

	public Time increaseMinutes(int offset) {
		this.add(MINUTE, offset);
		return this;
	}

	public Time increaseMinute() {
		return this.increaseMinutes(1);
	}

	public Time increaseSeconds(int offset) {
		this.add(SECOND, offset);
		return this;
	}

	public Time increaseSecond() {
		return this.increaseSeconds(1);
	}

	/**
	 * Set time to the first day of the week that current time in.
	 */
	public Time setToFirstDayOfThisWeek() {
		this.increaseDates(SUNDAY - this.get(DAY_OF_WEEK));
		return this;
	}

	/**
	 * Return the first day of the week that current time in.
	 * @return
	 */
	public Time getFirstDayOfThisWeek() {
		return this.clone().setToFirstDayOfThisWeek();
	}

	/**
	 * Set time to the last day of the week that current time in.
	 */
	public Time setToLastDayOfThisWeek() {
		this.increaseDates(SATURDAY - this.get(DAY_OF_WEEK));
		return this;
	}

	/**
	 * Return the last day of the week that current time in.
	 * @return
	 */
	public Time getLastDayOfThisWeek() {
		return this.clone().setToLastDayOfThisWeek();
	}

	/**
	 * Check if it is working day in a week.
	 * @return
	 */
	public boolean isWorkingDay() {
		int dayOfWeek = this.get(DAY_OF_WEEK);
		return dayOfWeek > 1 && dayOfWeek < 7;
	}

	/**
	 * Check if it is NOT working day in a week.
	 * @return
	 */
	public boolean isRestDay() {
		return !isWorkingDay();
	}

	/**
	 * Truncate part of time at year
	 * @return
	 */
	public Time truncateAtYear() {
		this.truncateTime(this, Calendar.YEAR);
		return this;
	}

	/**
	 * Truncate part of time at month
	 * @return
	 */
	public Time truncateAtMonth() {
		this.truncateTime(this, Calendar.MONTH);
		return this;
	}

	/**
	 * Truncate part of time at week
	 * @return
	 */
	public Time truncateAtWeek() {
		this.truncateTimeByWeek(this);
		return this;
	}

	/**
	 * Truncate part of time at date
	 * @return
	 */
	public Time truncateAtDate() {
		this.truncateTime(this, Calendar.DAY_OF_MONTH);
		return this;
	}

	/**
	 * Truncate part of time at hour
	 * @return
	 */
	public Time truncateAtHour() {
		this.truncateTime(this, Calendar.HOUR_OF_DAY);
		return this;
	}

	/**
	 * Truncate part of time at minute
	 * @return
	 */
	public Time truncateAtMinute() {
		this.truncateTime(this, Calendar.MINUTE);
		return this;
	}

	/**
	 * Truncate part of time at second
	 * @return
	 */
	public Time truncateAtSecond() {
		this.truncateTime(this, Calendar.SECOND);
		return this;
	}

	/**
	 * Truncate part of time specifed Calender field
	 * @param time Time to be truncated
	 * @param field Calendar field: YEAR, MONTH, DAY_OF_MONTH, HOUR, HOUR_OF_DAY, MINUTE, SECOND
	 */
	protected void truncateTime(Calendar time, int field) {
		switch (field) {
			case Calendar.YEAR:
				time.set(Calendar.MONTH, 0);
			case Calendar.MONTH:
				time.set(Calendar.DAY_OF_MONTH, 1);
			case Calendar.DAY_OF_MONTH:
				time.set(Calendar.HOUR_OF_DAY, 0);
			case Calendar.HOUR:
			case Calendar.HOUR_OF_DAY:
				time.set(Calendar.MINUTE, 0);
			case Calendar.MINUTE:
				time.set(Calendar.SECOND, 0);
			case Calendar.SECOND:
				time.set(Calendar.MILLISECOND, 0);
		}
	}

	/**
	 * Week is different, so handle it seperately.
	 * @param time
	 */
	protected void truncateTimeByWeek(Calendar time) {
		time.getTime();// This must be called, otherwise following statement will not be useful. Maybe this is a bug.
		time.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public int getYearIntervalFrom(Time time) {
		if (time == null) return 0;
		return this.get(Calendar.YEAR) - time.get(Calendar.YEAR);
	}

	public int getYearIntervalTo(Time time) {
		return 0 - getYearIntervalFrom(time);
	}

	public int getMonthIntervalFrom(Time time) {
		int yearInterval = getYearIntervalFrom(time);
		return (this.get(Calendar.MONTH) - time.get(Calendar.MONTH)) + yearInterval * 12;
	}

	public int getMonthIntervalTo(Time time) {
		return 0 - getMonthIntervalFrom(time);
	}

	public int getWeekIntervalFrom(Time time) {
		if (time == null) {
			return 0;
		}
		Calendar s = time.clone().truncateAtWeek();
		Calendar e = this.clone().truncateAtWeek();
		long timeInMillis = e.getTimeInMillis() - s.getTimeInMillis();
		int weekInterval = (int) (timeInMillis / (7 * 24 * 60 * 60 * 1000));
		return weekInterval;
	}

	public int getWeekIntervalTo(Time time) {
		return 0 - getWeekIntervalFrom(time);
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public int getDayIntervalFrom(Time time) {
		if (time == null) {
			return 0;
		}
		Calendar s = time.clone().truncateAtDate();
		Calendar e = this.clone().truncateAtDate();
		long timeInMillis = e.getTimeInMillis() - s.getTimeInMillis();
		int millisOfDay = 24 * 60 * 60 * 1000;
		long intervalInDate = timeInMillis / millisOfDay;
		return (int) intervalInDate;
	}

	public int getDayIntervalTo(Time time) {
		return 0 - getDayIntervalFrom(time);
	}

	public int getHourIntervalFrom(Time time) {
		if (time == null) {
			return 0;
		}
		Calendar s = time.clone().truncateAtHour();
		Calendar e = this.clone().truncateAtHour();
		long timeInMillis = e.getTimeInMillis() - s.getTimeInMillis();
		long intervalInHour = timeInMillis / (60 * 60 * 1000);
		return (int) intervalInHour;
	}

	public int getHourIntervalTo(Time time) {
		return 0 - getHourIntervalFrom(time);
	}

	public int getMinuteIntervalFrom(Time time) {
		if (time == null) {
			return 0;
		}
		Calendar s = time.clone().truncateAtMinute();
		Calendar e = this.clone().truncateAtMinute();
		long timeInMillis = e.getTimeInMillis() - s.getTimeInMillis();
		long ret = timeInMillis / (60 * 1000);
		return (int) ret;
	}

	public int getMinuteIntervalTo(Time time) {
		return 0 - getMinuteIntervalFrom(time);
	}

	public int getSecondIntervalFrom(Time time) {
		if (time == null) {
			return 0;
		}
		Calendar s = time.clone().truncateAtSecond();
		Calendar e = this.clone().truncateAtSecond();
		long timeInMillis = e.getTimeInMillis() - s.getTimeInMillis();
		long ret = timeInMillis / 1000;
		return (int) ret;
	}

	public int getSecondIntervalTo(Time time) {
		return 0 - getSecondIntervalFrom(time);
	}

	/**
	 * Get year of time.
	 * @return
	 */
	public int getYear() {
		return this.get(YEAR);
	}

	/**
	 * Set year of time.
	 * @param year
	 */
	public void setYear(int year) {
		this.set(YEAR, year);
	}

	/**
	 * Get month of time, starts from 1, not 0.
	 * @return
	 */
	public int getMonth() {
		return this.get(MONTH) + 1;
	}

	/**
	 * Set month of time.
	 * @param month
	 */
	public void setMonth(int month) {
		this.set(MONTH, month - 1);
	}

	/**
	 * Get day of time
	 * @return
	 */
	public int getDate() {
		return this.get(DATE);
	}

	/**
	 * Set day of time.
	 * @param date
	 */
	public void setDate(int date) {
		this.set(DATE, date);
	}

	/**
	 * Get hour of time.
	 * @return
	 */
	public int getHour() {
		return this.get(HOUR_OF_DAY);
	}

	/**
	 * Set hour of time.
	 * @param hour
	 */
	public void setHour(int hour) {
		this.set(HOUR_OF_DAY, hour);
	}

	/**
	 * Get minute of time.
	 * @return
	 */
	public int getMinute() {
		return this.get(MINUTE);
	}

	/**
	 * Set minute of time.
	 * @param minute
	 */
	public void setMinute(int minute) {
		this.set(MINUTE, minute);
	}

	/**
	 * Get second of time.
	 * @return
	 */
	public int getSecond() {
		return this.get(SECOND);
	}

	/**
	 * Set second of time.
	 * @param second
	 */
	public void setSecond(int second) {
		this.set(SECOND, second);
	}

	/**
	 * Get day of week.
	 */
	public int getDayOfWeek(){
		return this.get(Calendar.DAY_OF_WEEK);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return this.getTime().toString();
	}
}


