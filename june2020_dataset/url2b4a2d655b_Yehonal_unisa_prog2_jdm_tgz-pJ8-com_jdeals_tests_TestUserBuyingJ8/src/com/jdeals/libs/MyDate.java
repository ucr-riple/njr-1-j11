/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.libs;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.swing.text.MaskFormatter;

/**
 * This class would simplify date conversion/parsing and adds different tools
 * for date operations.
 *
 * @author Giuseppe Ronca
 */
public class MyDate implements Serializable, Cloneable {

    /**
     * The Constant defFormat.
     */
    public static final String defFormat = "dd/mm/yyyy";

    /**
     * The Constant defMask.
     */
    public static final String defMask = "##/##/####";

    /**
     * The Enum DiffType.
     */
    public enum DiffType {

        /**
         * The days.
         */
        DAYS(1000.0 * 24.0 * 60.0 * 60.0);

        /**
         * The val.
         */
        private double val;

        /**
         * Instantiates a new diff type.
         *
         * @param val the val
         */
        private DiffType(double val) {
            this.val = val;
        }

        /**
         * Gets the val.
         *
         * @return the val
         */
        public double getVal() {
            return this.val;
        }
    }

    /**
     * The format.
     */
    private String format;

    /**
     * The calendar.
     */
    private Calendar calendar;

    /**
     * Instantiates a new my date.
     *
     * @param format the format
     * @param date the date
     */
    public MyDate(String format, Calendar date) {
        this.format = format;
        this.calendar = date;
    }

    /**
     * Instantiates a new my date.
     */
    public MyDate() {
        this(MyDate.defFormat, Calendar.getInstance());
    }

    /**
     * From string.
     *
     * @param date the date
     * @return the my date
     * @throws Exception the exception
     */
    public MyDate fromString(String date) throws Exception {
        // Date now = new Date();
        DateFormat df = new SimpleDateFormat(this.format);
        df.setLenient(false);

        try {
            this.calendar.setTime(df.parse(date));
        } catch (ParseException e) {
            throw new Exception("Unable to parse this date: " + date);
        }

        return this;
    }

    /**
     * Gets the calendar.
     *
     * @return the calendar
     */
    public Calendar getCalendar() {
        return this.calendar;
    }

    /**
     * deprecated function - legacy only.
     *
     * @return Date time
     */
    public Date getDate() {
        return this.calendar.getTime();
    }

    /**
     * Gets the format.
     *
     * @return the format
     */
    public String getFormat() {
        return this.format;
    }

    /**
     * To string.
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        return df.format(this.calendar.getTime());
    }

    /**
     * Date to calendar.
     *
     * @param date the date
     * @return the calendar
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Day diff.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return difference of days, 0 if startDate > endDate
     */
    public static long dayDiff(final Calendar startDate, final Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    /**
     * Day diff.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the long
     */
    public static long dayDiff(final MyDate startDate, final MyDate endDate) {
        return dayDiff(startDate.getCalendar(), endDate.getCalendar());
    }
}
