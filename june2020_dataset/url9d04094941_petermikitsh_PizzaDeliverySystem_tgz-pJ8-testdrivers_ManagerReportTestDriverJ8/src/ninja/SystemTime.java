package ninja;

import model.Configuration;

/**
 * Represents the simulation time.
 * 
 * @author  Isioma Nnodum	iun4534@rit.edu
 */
public class SystemTime {

	/**
	 * The name of the stored time configuration.
	 */
	public static final String TIME_CONFIG_NAME = "systemTime";
	
	/**
	 * The name of the time scale configuration.
	 */
	public static final String TIMESCALE_CONFIG_NAME = "timeScale";
	
	/**
	 * An enumeration of the possible time scale values.
	 */
	public enum SystemTimeScale {
		
		TS1to1,
		TS1to10,
		TS1to30,
		TS1to60,
		TS1to120,
		TS1to300,
		TS1to600;
		
		public static final SystemTimeScale[] allTimeScales = { 
				
			TS1to1,
			TS1to10,
			TS1to30,
			TS1to60,
			TS1to120,
			TS1to300,
			TS1to600 
			
		};
		
		/**
		 * Returns a double conversion value for a given timescale.
		 */
		public double getConversionRate() {
			
			double denominator = 1.0;
			switch( this ) {

			case TS1to1:
				denominator = 1.0;
				break;

			case TS1to10:
				denominator = 10.0;
				break;

			case TS1to30:
				denominator = 30.0;
				break;

			case TS1to60:
				denominator = 60.0;
				break;

			case TS1to120:
				denominator = 120.0;
				break;

			case TS1to300:
				denominator = 300.0;
				break;

			case TS1to600:
				denominator = 600.0;
				break;
				
			}
			
			return 1.0 / denominator;
			
		}
	
		/**
		 * Returns a string with the simulation time corresponding to one second.
		 */
		public String getSimulationTimeString() {
			
			String string = "";
			switch( this ) {

			case TS1to1:
				string = "1 second";
				break;

			case TS1to10:
				string = "10 seconds";
				break;

			case TS1to30:
				string = "30 seconds";
				break;

			case TS1to60:
				string = "1 minute";
				break;

			case TS1to120:
				string = "2 minutes";
				break;

			case TS1to300:
				string = "5 minutes";
				break;

			case TS1to600:
				string = "10 minutes";
				break;
				
			}
			
			return string;
			
		}
	
		/**
		 * Recovers a time scale enum value from a string.
		 */
		public static SystemTimeScale parseTimeScale( String string ) {
			
			// Iterate through the array of every timescale, and return a time 
			// scale if it matches the string.
			for( SystemTimeScale ts : allTimeScales ) {
				
				if( ts.toString().equals( string ) ) {
					return ts;
				}
				
			}
			
			// Return null if nothing was found.
			return null;
			
		}
		
	}
	
	/**
	 * The default timescale.
	 */
	public static final SystemTimeScale DEFAULT_TIMESCALE = SystemTimeScale.TS1to60;
	
	/**
	 * The time object stored here.
	 */
	private static Time _systemTime;
	
	/**
	 * Current time scale
	 */
	private static SystemTimeScale _currentTimeScale;
	
	/**
	 * Initializes system time with the default (or previously stored) time.
	 */
	public static void initialize() {
		initialize( DEFAULT_TIMESCALE );
	}
	
	/**
	 * Initializes system time with the given time scale value.
	 * 
	 * @param timeScale
	 */
	public static void initialize( SystemTimeScale timeScale ) {
		
		// Store the timescale in the config database.
		Configuration.getDb().add( new Configuration( TIMESCALE_CONFIG_NAME, "" + timeScale ) );
		
		// Try to get the stored time.
		Configuration time = Configuration.getDb().get(TIME_CONFIG_NAME.hashCode());
		
		if ( null != time) {
			_systemTime = new Time( timeScale.getConversionRate(), Long.parseLong(time.getValue()));
		} else {
			_systemTime = new Time( timeScale.getConversionRate() );
		}
		
		_currentTimeScale = timeScale;
		_systemTime.start();
		
	}
	
	/**
	 * Gets the current system time.
	 * 
	 * @return system time
	 */
	public static long getTime() {
		
		if( null == _systemTime) {
			initialize();
		}
		return _systemTime.getTime();
		
	}
	
	/**
	 * Stop time from running.
	 */
	public static void stopTime() {
		
		_systemTime.stopTime();
		Configuration.getDb().add(new Configuration("systemTime", "" + _systemTime.getTime()));
		
	}
	
	/**
	 * Sets the value of the timescale.
	 * 
	 * @param timeScale The new timescale.
	 */
	public static void setTimeScale( SystemTimeScale timeScale ) {
		
		_systemTime.setTimeScale( timeScale.getConversionRate() );
		_currentTimeScale = timeScale;
		
		// Also store the new value in the database.
		Configuration.getDb().add( new Configuration( TIMESCALE_CONFIG_NAME, timeScale.toString() ) );
		
	}
	
	/**
	 * Get the current time scale
	 * 
	 * @return the current time scale
	 */
	public static SystemTimeScale getTimeScale() {
		
		return _currentTimeScale;
		
	}

}
