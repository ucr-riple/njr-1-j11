package org.swiftgantt.ui;

import org.swiftgantt.common.LogHelper;
import java.awt.Point;
import java.awt.Rectangle;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Wang Yuxing
 */
public class PaintLogger {

	private static Logger logger = LogManager.getLogger(LogHelper.LOGGER_UI);

	/**
	 * For debugging painting only.
	 *
	 * @param message
	 */
	public static void debug(Object message) {
		if (logger.isDebugEnabled()) {
			logger.debug("[PAINT] : " + message);
		}
	}

	/**
	 *
	 * @param message
	 * @param leftTopPoint
	 * @param rightBottomPoint
	 */
	public static void debug(Point leftTopPoint, Rectangle clientRect, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug("[PAINT] [x=" + leftTopPoint.x + ",y=" + leftTopPoint.y + "]" +
					" [w=" + clientRect.width + ",h=" + clientRect.height + "] " + message);
		}
	}

	/**
	 *
	 * @param message
	 * @param leftTopPoint
	 * @param rightBottomPoint
	 */
	public static void debug(Rectangle clientRect, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug("[PAINT] [x=" + clientRect.x + ",y=" + clientRect.y + "]" +
					" [w=" + clientRect.width + ",h=" + clientRect.height + "] " + message);
		}
	}
}
