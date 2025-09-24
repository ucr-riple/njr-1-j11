package org.swiftgantt.ui;

/**
 * The enumeration for time unit.<br/>
 * Hour, AllDay, Day, Week, Month, Year<br/>
 * Default is Day<br/>
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public enum TimeUnit {
	Hour, AllDay, Day, Week, Month, Year;

	public static final TimeUnit defaultUnit = TimeUnit.Day;

	public static TimeUnit getAccurateTimeUnit(TimeUnit tu) {
		switch (tu) {
		case Year:
			return Month;
		case Month:
			return Day;
		case Week:
			return Day;
		case Day:
			return AllDay;
		case AllDay:
			return Hour;
		}
		return tu;
	}
}
