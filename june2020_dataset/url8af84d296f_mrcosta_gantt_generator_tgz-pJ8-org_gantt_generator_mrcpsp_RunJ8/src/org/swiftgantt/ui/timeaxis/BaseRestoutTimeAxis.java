package org.swiftgantt.ui.timeaxis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import org.swiftgantt.GanttChart;
import org.swiftgantt.TimeScaleView;

/**
 * Represent the time axis that has rest-out time.
 * @see HourlyTimeAxis
 * @see DailyTimeAxis
 * @author Yuxing Wang
 * @version 1.0
 */
public abstract class BaseRestoutTimeAxis extends BaseTimeAxis {

	public BaseRestoutTimeAxis(GanttChart gantt) {
		super.gantt = gantt;
	}

	@Override
	public void paint(Graphics g, JComponent c, Rectangle rec) {
		super.paint(g, c, rec);
		// Calculate steps to first full major scale.
		int posInMajor = calcPositionInMajorScale(kickoffTime);
		int posOfFirstStep = posInMajor - super.config.getBlankStepsToKickoffTime();
		stepsToFirstMajorScale = STEPS_OF_MAJOR_SCALE - posOfFirstStep + 1;
		if (stepsToFirstMajorScale >= STEPS_OF_MAJOR_SCALE) {
			stepsToFirstMajorScale -= STEPS_OF_MAJOR_SCALE;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("The steps to first full major scale is: " + stepsToFirstMajorScale);
		}
		totalMajorStepsCount = (totalStepsCount - stepsToFirstMajorScale) / STEPS_OF_MAJOR_SCALE + 1;
		// Start paint common for all Time Axis
		super.paintMutual();
//        DebugConsole.paintebug("The preferred size of the gantt chart is: " + gantt.getPreferredSize());
	}

	@Override
	protected void drawMajorTimeScaleBorder(int x) {
		if (logger.isDebugEnabled()) {
			logger.debug("Start to draw major time scale borders");
		}
//		int majorCount = (totalStepsCount - stepsToFirstMajorScale) / STEPS_OF_MAJOR_SCALE + 1;
		g.setColor(Color.black);
		for (int i = 0; i < totalMajorStepsCount; i++) {
			int xx = x + super.step_length * (i * STEPS_OF_MAJOR_SCALE + stepsToFirstMajorScale);
			g.drawRect(xx, 0, comRect.width - 1, super.row_height);
			// Draw label for major scale.
			String label = this.formatToMajorLabel(firstStepOfChart, i);
			g.drawChars(label.toCharArray(), 0, label.length(), xx + 4, 0 + super.row_height - 4);
		}
	}

	@Override
	protected void drawMinorTimeScaleBackground(int x, int y, int height) {
		if (logger.isDebugEnabled()) {
			logger.debug("Start to draw minor time scale background");
		}
		int currentTimeStep = this.getCurrentTimeStep();// Step at current time: hour or day.
		for (int i = 0; i < totalStepsCount; i++) {
			if (isFreeTime(i) == true) {
				g.setColor(config.getRestoutTimeBackColor());
				g.fillRect(x + super.step_length * i, y, super.step_length, height);
//        		g.setColor(Color.black);
//        		g.drawRect(super.step_length * i, super.row_height * 2, super.step_length, (componentRect.height - super.row_height * 2));
			} else {
				g.setColor(config.getWorkingTimeBackColor());
				g.fillRect(x + super.step_length * i, y, super.step_length, height);
			}
		}
		if (currentTimeStep > 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("Draw this hour or today's scale as step: " + currentTimeStep);
			}
			int xx = x + super.step_length * (currentTimeStep - 1);
			Color c = config.getCurrentTimeBackColor();
			g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 80));
			g.fillRect(xx, y, super.step_length, height);
			// I found the way to render transparent, so give up this solution.
			//PaintHelper.fillOppositeBevelFoggyRect(g, x, y, super.step_length, height);
			g.setColor(Color.black);
			g.drawRect(xx, y, super.step_length, height);
		}
		super.drawMajorScaleSeperator();
		super.drawKickoffTime(x, y, height);
		super.drawDeadline(x, y, height);

		// Drwa labels if it is time scale view.
		if (component instanceof TimeScaleView) {
			if (logger.isDebugEnabled()) {
				logger.debug("Start to draw minor time scale labels");
			}
			for (int i = 0; i < totalStepsCount; i++) {
				drawMinorTimeScaleLabel(i, x + super.step_length * i, super.row_height);
			}
		}
	}

	/**
	 * 
	 * @param i
	 * @param x
	 * @param y
	 */
	protected abstract void drawMinorTimeScaleLabel(int i, int x, int y);

	/**
	 * 
	 * @param step
	 * @return
	 */
	protected abstract boolean isFreeTime(int step);
}
