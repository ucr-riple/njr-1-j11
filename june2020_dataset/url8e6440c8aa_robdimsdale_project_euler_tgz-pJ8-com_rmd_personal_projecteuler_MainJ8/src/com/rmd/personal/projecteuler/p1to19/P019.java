package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Problem;

public class P019 implements Problem {

    private static final int DAYS_IN_JAN = 31;
    private static final int DAYS_IN_FEB = 28;
    private static final int DAYS_IN_FEB_LEAP_YEAR = 29;
    private static final int DAYS_IN_MAR = 31;
    private static final int DAYS_IN_APR = 30;
    private static final int DAYS_IN_MAY = 31;
    private static final int DAYS_IN_JUN = 30;
    private static final int DAYS_IN_JUL = 31;
    private static final int DAYS_IN_AUG = 31;
    private static final int DAYS_IN_SEP = 30;
    private static final int DAYS_IN_OCT = 31;
    private static final int DAYS_IN_NOV = 30;
    private static final int DAYS_IN_DEC = 31;

    public P019() {
    }

    @Override
    public String getDescription() {
        return "You are given the following information, but you may prefer to do some research for yourself.\n\n"
                + "1 Jan 1900 was a Monday.\n"
                + "Thirty days has September,\n"
                + "April, June and November.\n"
                + "All the rest have thirty-one,\n"
                + "Saving February alone,\n"
                + "Which has twenty-eight, rain or shine.\n"
                + "And on leap years, twenty-nine.\n"
                + "A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by"
                + " 400.\n\n"
                + "How many Sundays fell on the first of the month during the twentieth century "
                + "(1 Jan 1901 to 31 Dec 2000)?";
    }

    @Override
    public String run() {
        final int startDayOfWeek = 2; // Tuesday - this needs to be kept in sync with the start date info.
        final int startDay = 1;
        final int startMonth = 1;
        final int startYear = 1901;

        final int endDay = 31;
        final int endMonth = 12;
        final int endYear = 2000;

        int count = 0;

        int month = startMonth;
        int year = startYear;
        int dayOfMonth = startDay;
        int dayOfWeek = startDayOfWeek;

        while (true) {
            dayOfMonth++;
            dayOfWeek++;

            if (this.transitionMonth(dayOfMonth, month, year)) {
                dayOfMonth = 1;
                month++;
                if (month > 12) {
                    month = 1;
                    year++;
                }
            }

            if (dayOfWeek == 7) {
                dayOfWeek = 0; // We have a sunday
                if (dayOfMonth == 1) {
                    count++;
                }
            }

            if (year >= endYear && month >= endMonth && dayOfMonth >= endDay) {
                break;
            }
        }

        return String.valueOf(count);
    }

    protected boolean transitionMonth(int currentDay, int currentMonth, int currentYear) {
        switch (currentMonth) {
            case 1:
                return (currentDay > DAYS_IN_JAN);
            case 2:
                if (this.isLeapYear(currentYear)) {
                    return (currentDay > DAYS_IN_FEB_LEAP_YEAR);
                }
                return (currentDay > DAYS_IN_FEB);
            case 3:
                return (currentDay > DAYS_IN_MAR);
            case 4:
                return (currentDay > DAYS_IN_APR);
            case 5:
                return (currentDay > DAYS_IN_MAY);
            case 6:
                return (currentDay > DAYS_IN_JUN);
            case 7:
                return (currentDay > DAYS_IN_JUL);
            case 8:
                return (currentDay > DAYS_IN_AUG);
            case 9:
                return (currentDay > DAYS_IN_SEP);
            case 10:
                return (currentDay > DAYS_IN_OCT);
            case 11:
                return (currentDay > DAYS_IN_NOV);
            case 12:
                return (currentDay > DAYS_IN_DEC);
            default:
                throw new IllegalArgumentException("Unspupported month: " + currentMonth);
        }
    }

    protected boolean isLeapYear(int currentYear) {
        return currentYear % 400 == 0 || currentYear % 100 != 0 && currentYear % 4 == 0;

    }
}
