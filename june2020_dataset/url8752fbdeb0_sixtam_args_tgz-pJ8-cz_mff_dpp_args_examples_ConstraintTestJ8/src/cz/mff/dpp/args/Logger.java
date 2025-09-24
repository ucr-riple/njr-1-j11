package cz.mff.dpp.args;


import java.util.logging.Level;


/** Support for logging. */
public final class Logger {
	
	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("cz.mff.dpp.args");
	//
	// STATIC CONSTRUCTOR
	//
	static{
		LOGGER.setLevel(Level.SEVERE);
	}

	
	
		/**
	 * Logs formated message with Level.SEVERE
	 * @param format formatted message to be logged
	 * @param args arguments for formatted message
	 */
	static void severe(String format, Object... args) {
		log(Level.SEVERE, format, args);
	}
		/**
	 * Logs formated message with Level.WARNING
	 * @param format formatted message to be logged
	 * @param args arguments for formatted message
	 */
	static void warning(String format, Object... args) {
		log(Level.WARNING, format, args);
	}
	
		/**
	 * Logs formated message with Level.INFO
	 * @param format formatted message to be logged
	 * @param args arguments for formatted message
	 */
	static void info(String format, Object... args) {
		log(Level.INFO, format, args);
	}
	/**
	 * Logs formated message with Level.FINE
	 * @param format formatted message to be logged
	 * @param args arguments for formatted message
	 */
	static void fine(String format, Object... args) {
		log(Level.FINE, format, args);
	}
	/**
	 * If level is loggable than formatted message is logged with given level.
	 * Otherwise nothing happens.
	 * @param level level of message
	 * @param format formated message to be logged
	 * @param args arguments for formatted message
	 */
	static void log(Level level, String format, Object... args) {
		if (LOGGER.isLoggable(level)) {
			LOGGER.log(level, String.format(format, args));
		}
	}
	/**
	 * Sets level for logging. Only messages with higher or equal
	 * priority than given will be logged.
	 *
	 * Default level is Level.SEVERE
	 * @param level level to be set for logging
	 */
	public static void setLevel(Level level) {
		LOGGER.setLevel(level);
	}
	


}
