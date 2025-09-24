package ninja;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author  Isioma Nnodum	iun4534@rit.edu
 */
public class Time extends Thread {
	
	/**
	 * Represents the epoch time since the first time the program started
	 */
	private long _seconds;
	
	/**
	 * Represents if time is running or not.
	 */
	private boolean _active;
	
	/**
	 * The scaling factor for time
	 */
	private static double _timeScale;
	
	/**
	 * Formats dates for display.
	 */
	private static SimpleDateFormat _dateFormat = new SimpleDateFormat( "MMM d, h:mm a" );
	
	/**
	 * Initialize time from the real current time. (i.e. Genesis)
	 */
	public Time() {
		
		_seconds = Calendar.getInstance().getTimeInMillis() / 1000;
		_timeScale = 1;
		
	}
	
	/**
	 * Construct the time from the current time while scaling it.
	 * 
	 * @param timeScale the scaling factor for time ex. 0.006 means every second represents a single minute
	 */
	public Time(double timeScale) {
		
		_seconds = Calendar.getInstance().getTimeInMillis() / 1000;
		_timeScale = timeScale;
		
	}
	
	/**
	 * Construct the time from some start times and scaling it bt time scale.
	 * 
	 * @param startTime
	 * @param timeScale the scaling factor for time ex. 0.006 means every second represents a single minute
	 */
	public Time(double timeScale, long startTime) {
		
		_seconds = startTime;
		_timeScale = timeScale;
		
	}
	
	/**
	 * Stops time from increasing.
	 */
	public void stopTime() {
		_active = false;
	}
	
	/**
	 * Change the time scaling factor.
	 * 
	 * @param factor
	 */
	public void setTimeScale(double factor) {
		_timeScale = factor;
	}
	
	/**
	 * Gets the time scaling factor.
	 * 
	 * @return   the time scaling factor
	 */
	public double getTimeScale() {
		return _timeScale;
	}
	
	/**
	 * Get the current time.
	 * 
	 * @return the time in seconds
	 */
	public long getTime() {
		return _seconds;
	}
	
	/**
	 * Return the specified time in a human readable format.
	 * 
	 * @param timestamp - the specified time in seconds
	 * @return the formatted time
	 */
	public static String formatTime(long timestamp) {
		
		Date date= new Date();
		date.setTime(timestamp * 1000);
		
		return _dateFormat.format( date  );
		
	}
	
	/**
	 * Converts real integer minutes into ninja milliseconds.
	 * 
	 * @param minutes an integer that represents minutes
	 * @return ninja-milliseconds
	 */
	public static long convertToMilliseconds(int minutes) {
		long milliseconds = minutes * 60 * 1000;
		return milliseconds;
	}
	
	/**
	 * Scale time up.
	 * 
	 * @param milliseconds the milliseconds to scale.
	 * @return scaled time
	 */
	public static long scaleUp(long milliseconds) {
		return (long) (milliseconds * _timeScale);
	}
	
	/**
	 * Converts ninjaMilliseconds into real integer minutes.
	 * @return real minutes
	 */
	public static double convertToMinutes(long ninjaMilliseconds) {
		return (double) (ninjaMilliseconds / 60 ) / 1000;
	}
	
	/**
	 * Convert the given ninjaMilliseconds into real integer minutes.
	 * TODO duplicate method; see above
	 * 
	 * @param ninjaMilliseconds
	 * @return real minutes
	 */
	public static double convertToRealMinutes(long ninjaMilliseconds) {
		return (double) ((ninjaMilliseconds/_timeScale) / 60 ) / 1000;
	}
	
	/**
	 * Start time (literally).
	 */
	public void run() {
		
		_active = true;
		
		while(_active) {
			
			try {
				Thread.sleep((int) (1000 * _timeScale));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			_seconds++;
			
		}
		
	}

}
