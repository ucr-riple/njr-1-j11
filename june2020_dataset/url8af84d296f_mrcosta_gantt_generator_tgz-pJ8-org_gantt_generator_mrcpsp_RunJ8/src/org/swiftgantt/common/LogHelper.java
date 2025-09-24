package org.swiftgantt.common;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Yuxing Wang
 *
 */
public class LogHelper {
	// Log UI
	public static final String LOGGER_UI = "org.swiftgantt.ui";
	// Log model
	public static final String LOGGER_MODEL = "org.swiftgantt.model";
	// Log events
	public static final String LOGGER_EVENT = "org.swiftgantt.event";

	public static final String logSperator = "------------------------";

	/**
	 * 
	 * @param logger
	 * @param msg
	 */
	public static void title(Logger logger, String msg) {
		logger.info(StringUtils.center(msg, 200, "-"));
	}

	/**
	 * TODO: Log with parameters
	 * @param logger
	 * @param msg
	 * @param params
	 * @since 0.3.6
	 */
	public static void log(Logger logger, String msg, String... params){
		for (int i = 0; i < params.length; i++) {
			StringUtils.replace(msg, "{" + i + "}", params[i]);
		}
		logger.debug(msg);
	}
}
