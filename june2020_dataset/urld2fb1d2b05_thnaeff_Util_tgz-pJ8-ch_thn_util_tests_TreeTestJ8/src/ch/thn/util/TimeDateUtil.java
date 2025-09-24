/**
 *    Copyright 2013 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class TimeDateUtil {
	
	
	private static final DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");	//Wed, 4 Jul 2001 12:08:56
	
	private static final int MILLIS_TO_SECONDS = 1000;
	private static final int MILLIS_TO_MINUTES = MILLIS_TO_SECONDS * 60;
	private static final int MILLIS_TO_HOURS = MILLIS_TO_MINUTES * 60;
	private static final int MILLIS_TO_DAYS = MILLIS_TO_HOURS * 24;
	
	
	/**
	 * Returns the date and time formatted as string, using a predefined data format 
	 * which gives a date/time string like "Wed, 4 Jul 2001 12:08:56"
	 * 
	 * @param millis Milliseconds
	 * @return The date and time as string
	 */
	public static String getDateTimeFromMillis(long millis) {
		return getDateTimeFromMillis(millis, dateFormat);
	}
	
	/**
	 * Returns the date and time formatted as string
	 * 
	 * @param millis Milliseconds
	 * @param df The date format to use
	 * @return The date and time as string
	 */
	public static String getDateTimeFromMillis(long millis, DateFormat df) {
		return df.format(new Date(millis));
	}
	
	/**
	 * Returns a string which represents time of day given with <code>millis</code>
	 * 
	 * @param millis
	 * @param separator
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @param milliseconds
	 * @param ampm
	 * @return
	 */
	public static String getTimeFromMillis(long millis, String separator, boolean hours, 
			boolean minutes, boolean seconds, boolean milliseconds, boolean ampm) {
		StringBuilder sb = new StringBuilder();
		
		if (hours) {
			if (ampm) {
				sb.append("KK");
			} else {
				sb.append("HH");
			}
		}
		
		if (minutes) {
			if (sb.length() > 0) {
				sb.append(separator);
			}
			
			sb.append("mm");
		}
		
		if (seconds) {
			if (sb.length() > 0) {
				sb.append(separator);
			}
			
			sb.append("ss");
		}
		
		if (milliseconds) {
			if (sb.length() > 0) {
				sb.append(separator);
			}
			
			sb.append("SS");
		}
		
		if (ampm) {
			sb.append("a");
		}
		
		return (new SimpleDateFormat(sb.toString())).format(new Date(millis));
		
	}
	
	/**
	 * Returns a string which represents time of day given with <code>millis</code>
	 * 
	 * @param millis
	 * @param seconds
	 * @param milliseconds
	 * @param ampm
	 * @return
	 */
	public static String getTimeFromMillis(long millis, boolean seconds, boolean milliseconds, boolean ampm) {
		return getTimeFromMillis(millis, ".", true, true, seconds, milliseconds, ampm);
	}
	
	/**
	 * 
	 * 
	 * @param millis
	 * @param separator
	 * @param day
	 * @param month
	 * @param year
	 * @param weekday The weekday as string (Mon, Tues, ...) in front of the date
	 * @param us US/European date format
	 * @param monthString The month as string (Jan, Feb, ...)
	 * @return
	 */
	public static String getDateFromMillis(long millis, String separator, 
			boolean day, boolean month, boolean year, boolean weekday, 
			boolean us, boolean monthString) {
		StringBuilder sb = new StringBuilder();
		
		if (weekday) {
			sb.append("EEE, ");
		}
		
		if (us && month) {
			if (monthString) {
				sb.append("MMM");
			} else {
				sb.append("MM");
			}
		}
		
		if (day) {
			if (us && sb.length() > 0) {
				sb.append(separator);
			}
			
			sb.append("dd");
		}
		
		if (!us && month) {
			if (sb.length() > 0) {
				sb.append(separator);
			}
			
			if (monthString) {
				sb.append("MMM");
			} else {
				sb.append("MM");
			}
		}
		
		if (year) {
			if (sb.length() > 0) {
				sb.append(separator);
			}
			
			sb.append("yyyy");
		}
		
		return (new SimpleDateFormat(sb.toString())).format(new Date(millis));
		
	}
	
	/**
	 * A formatted date, with the "/" separator for the US date and the "." 
	 * separator for the European date.
	 * 
	 * @param millis
	 * @param weekday
	 * @param us
	 * @return
	 */
	public static String getDateFromMillis(long millis, boolean weekday, boolean us) {
		return getDateFromMillis(millis, (us ? "/" : "."), true, true, true, weekday, us, false);
	}
	
	/**
	 * Returns a string which splits the given timestamp into days, hours, minutes, 
	 * seconds and milliseconds. The given time strings have two functions: they 
	 * are used as time unit strings and they define which time units are used. 
	 * If <code>days="days "</code> and <code>minutes="m "</code> is given for 
	 * example, the output string would be something like "10days 320m".
	 * 
	 * @param timestampMillis
	 * @param days
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @param milliseconds
	 * @param showIfZero If set to <code>true</code>, leading time units are shown even 
	 * they are 0. This can produce an output string like "0d 0h 50m 10s". If set 
	 * to <code>false</code>, the output string would look like "50m 10s". <br>
	 * Hint: trailing time units which are 0 are always shown. If the previous 
	 * output string would have been created with <code>milliseconds="ms"</code> 
	 * and <code>showIfZero=false</code>, the output would look like "50m 10s 0ms"
	 * @return
	 */
	public static String millisToTime(long timestampMillis, String days, String hours, 
			String minutes, String seconds, String milliseconds, boolean showIfZero) {
		long ms = timestampMillis;
		String lastUnit = null;
		
		StringBuilder sb = new StringBuilder();
		
		if (days != null) {
			if (ms > MILLIS_TO_DAYS) {
				sb.append(ms / MILLIS_TO_DAYS).append(days);
				ms %= MILLIS_TO_DAYS;
				//Always show trailing time units, even if they are zero
				showIfZero = true;
			} else if (showIfZero) {
				sb.append("0").append(days);
				//Always show trailing time units, even if they are zero
				showIfZero = true;
			} else {
				lastUnit = days;
			}
		}
		
		if (hours != null) {
			if (ms > MILLIS_TO_HOURS) {
				sb.append(ms / MILLIS_TO_HOURS).append(hours);
				ms %= MILLIS_TO_HOURS;
				showIfZero = true;
			} else if (showIfZero) {
				sb.append("0").append(hours);
				showIfZero = true;
			} else {
				lastUnit = hours;
			}
		}
		
		if (minutes != null) {
			if (ms > MILLIS_TO_MINUTES) {
				sb.append(ms / MILLIS_TO_MINUTES).append(minutes);
				ms %= MILLIS_TO_MINUTES;
				showIfZero = true;
			} else if (showIfZero) {
				sb.append("0").append(minutes);
				showIfZero = true;
			} else {
				lastUnit = minutes;
			}
		}
		
		if (seconds != null) {
			if (ms > MILLIS_TO_SECONDS) {
				sb.append(ms / MILLIS_TO_SECONDS).append(seconds);
				ms %= MILLIS_TO_SECONDS;
				showIfZero = true;
			} else if (showIfZero) {
				sb.append("0").append(seconds);
				showIfZero = true;
			} else {
				lastUnit = seconds;
			}
		}
		
		if (milliseconds != null) {
			if (ms > 0) {
				sb.append(ms).append(milliseconds);
			} else if (showIfZero) {
				sb.append("0").append(milliseconds);
			} else {
				lastUnit = milliseconds;
			}
		}
		
		if (sb.length() == 0) {
			//If nothing has been added because the timestamp is zero and 
			//showIfZero=false, show at least the smallest time unit
			sb.append("0").append(lastUnit);
		}
		
		return sb.toString();
		
	}
	
	/**
	 * Shows the timestamp with days, hours, minutes and seconds and with 
	 * <code>showIfZero=false</code>.
	 * 
	 * @param timestampMillis
	 * @return
	 * @see #millisToTime(long, String, String, String, String, String, boolean)
	 */
	public static String millisToTime(long timestampMillis) {
		return millisToTime(timestampMillis, "d ", "h ", "m ", "s ", null, false);
	}
	

}
