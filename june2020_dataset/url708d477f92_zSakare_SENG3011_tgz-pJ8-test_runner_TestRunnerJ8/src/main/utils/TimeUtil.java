package main.utils;

import java.util.Random;
import java.sql.Time;

/**
 * Utility class for generating time.
 */
public class TimeUtil {
	private static final int HOURS_IN_A_DAY = 24;
	private static final int MINUTES_IN_AN_HOUR = 60;
	private static final int SECONDS_IN_A_MINUTE = 60;
	private static final int MILLISECONDS_IN_A_SECOND = 1000;
	private static final int MILLIS_IN_A_DAY = HOURS_IN_A_DAY * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE * MILLISECONDS_IN_A_SECOND;
	
	public static Time generateTime() {
		Time time = new Time((long)generateInt(MILLIS_IN_A_DAY));

		return time;
	}
	
	public static int generateHour() {
		return generateInt(HOURS_IN_A_DAY);
	}
	
	public static int generateMinute() {
		return generateInt(MINUTES_IN_AN_HOUR);
	}
	
	public static int generateSeconds() {
		return generateInt(SECONDS_IN_A_MINUTE);
	}
	
	public static int generateMillis() {
		return generateInt(MILLISECONDS_IN_A_SECOND);
	}
	
	private static int generateInt(int timeFrame) {
		final Random random = new Random();
		
		return random.nextInt(timeFrame);
	}
}	
