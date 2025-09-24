package com.evs.data.util;

import java.util.Calendar;

public class CommonUtil {
	 /**
     * Convert a calendar month to a fiscal month.
     */
    public static String toFiscalMonth(Calendar systemDate)
    {
        int month = systemDate.get(Calendar.MONTH) + 4;
        if(month > 12)
        {
            month -= 12;
        }

        if(month < 10)
        {
            return "0" + Integer.toString(month);
        }
        else
        {
            return Integer.toString(month);
        }
    }

    /**
     * Convert a calendar year to a fiscal year.
     */
    public static String toFiscalYear(Calendar systemDate)
    {
        int month = systemDate.get(Calendar.MONTH) + 4;
        if(month > 12)
        {
            month -= 12;
        }

        int year = systemDate.get(Calendar.YEAR);
        if(month <= 3)
        {
            year++;
        }

        return Integer.toString(year);
    }

}
