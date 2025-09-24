package org.swiftgantt.common;

import java.util.EventObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Console ouput debug.
 * 
 * @author Yuxing Wang
 * 
 */
public class EventLogger {

	private static Logger logger = LogManager.getLogger(LogHelper.LOGGER_EVENT);
	//	private static Logger eventLogger = LogManager.getLogger("org.swiftgantt");
	//	private static Logger paintLogger = LogManager.getLogger("org.swiftgantt");

	public EventLogger() {
		super();
	}

	/**
	 * Log for any event.
	 * 
	 * @param message
	 */
	public static void event(EventObject e, Object message) {
		String source = (e == null) ? null : e.getSource().getClass().getName();
		if (logger.isDebugEnabled()) {
			logger.debug("  [EVENT]: [" + source + "]\t" + message + "\t");
		}
	}

}
