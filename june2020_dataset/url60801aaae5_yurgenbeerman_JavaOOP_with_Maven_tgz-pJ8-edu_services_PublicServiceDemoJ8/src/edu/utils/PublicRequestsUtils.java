package edu.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by yurii.pyvovarenko on 3/12/14.
 */
public class PublicRequestsUtils {
    public static String toTimeAndDateString(GregorianCalendar calendar) {
        String date;
        date = calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                calendar.get(Calendar.MINUTE) + ", " +
                calendar.get(Calendar.DAY_OF_MONTH) + "." +
                calendar.get(Calendar.MONTH) + "." +
                calendar.get(Calendar.YEAR);
        return date;
    }

    public static GregorianCalendar nowPlusTenMinutes() {
        GregorianCalendar nowPlusMinute = new GregorianCalendar();
        nowPlusMinute.add(GregorianCalendar.MINUTE, 10);
        return nowPlusMinute;
    }

    public static GregorianCalendar nowMinusTenMinutes() {
        GregorianCalendar nowPlusMinute = new GregorianCalendar();
        nowPlusMinute.add(GregorianCalendar.MINUTE, -10);
        return nowPlusMinute;
    }

}
