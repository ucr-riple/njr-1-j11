package org.swiftgantt.ui;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import org.swiftgantt.TimeScaleView;
import org.apache.log4j.LogManager;

/**
 * Paint time scale
 * @author Yuxing Wang
 * 
 */
public class TimeScaleUI extends BaseUI {
	protected TimeScaleView timeScaleView = null;

	public TimeScaleUI() {
		logger = LogManager.getLogger(TimeScaleUI.class);
	}

	public static ComponentUI createUI(JComponent c) {
		return new TimeScaleUI();
	}

	@Override
	public void installUI(JComponent c) {
		timeScaleView = (TimeScaleView) c;
		super.installUI(c);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		if (logger.isDebugEnabled()) {
			logger.debug("Paint as " + super.timeAxis.getClass().getName() + " for TimeAxisScaleUI");
		}
		int width = super.clientWidth;
		int height = super.clientHeight;
		// Draw time axis.
		Rectangle rec = new Rectangle(0, 0, width, height);
		super.timeAxis.paint(g, c, rec);
	}

	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
	}

}
