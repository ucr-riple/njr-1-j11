package org.swiftgantt.ui.timeaxis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import org.apache.commons.lang.time.DateFormatUtils;

import org.swiftgantt.GanttChart;
import org.swiftgantt.TimeScaleView;
import org.swiftgantt.common.PaintHelper;

/**
 * Represents the time axis that has no rest-out time.
 * 
 * @see WeeklyTimeAxis
 * @see MonthlyTimeAxis
 * @see YearlyTimeAxis
 * @author Yuxing Wang
 * @version 1.0
 */
public abstract class BaseNoRestoutTimeAxis extends BaseTimeAxis {

	protected int time_offset = 1; // Because we count from 1, but computer from 0, so default offset is 1. But it is special for Yearly, it is 0. 

	public BaseNoRestoutTimeAxis(GanttChart gantt) {
		super.gantt = gantt;
		// It is not allowed retrieve Gantt model here, because it isn't constructed yet
	}

	@Override
	public void paint(Graphics g, JComponent c, Rectangle rec) {
		super.paint(g, c, rec);
		//
		int kickoffPosInMajor = this.calcPositionInMajorScale(kickoffTime);
		stepsToFirstMajorScale = (STEPS_OF_MAJOR_SCALE - kickoffPosInMajor + time_offset) + super.config.getBlankStepsToKickoffTime();
		if (stepsToFirstMajorScale >= STEPS_OF_MAJOR_SCALE) {
			stepsToFirstMajorScale -= STEPS_OF_MAJOR_SCALE;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("By " + super.config.getBlankStepsToKickoffTime() + " steps before '"
					+ DateFormatUtils.format(kickoffTime.getTime(), "yyyy-MM-dd") + "', The steps to first full major scale is: "
					+ stepsToFirstMajorScale);
		}
		// Start paint common for all Time Axis
		totalMajorStepsCount = (super.totalStepsCount - stepsToFirstMajorScale) / STEPS_OF_MAJOR_SCALE + 1;
		super.paintMutual();
	}

	@Override
	protected void drawMajorTimeScaleBorder(int x) {
		final int MIN_DISPLAY_LENGTH = 10;
		//        int count = (super.totalStepsCount - stepsToFirstMajorScale ) / STEPS_OF_MAJOR_SCALE + 1;//FIXME
		g.setColor(Color.black);
		// Draw step before full all major scale first. 
		String label = "";
		if (stepsToFirstMajorScale > MIN_DISPLAY_LENGTH) {
			label = this.formatToMajorLabel(firstStepOfChart, 0);
			if (logger.isDebugEnabled()) {
				logger.debug("Draw lable for large time scale: " + label + " by first date of year " + firstStepOfChart.getTime());
			}
			g.drawChars(label.toCharArray(), 0, label.length(), 4, 0 + row_height - 4);
		}
		// Draw each full major scale.
		for (int i = 0; i < totalMajorStepsCount; i++) {
			if (logger.isDebugEnabled()) {
				logger.debug("A year start from step: " + (i * STEPS_OF_MAJOR_SCALE + stepsToFirstMajorScale));
			}
			int xx = x + super.config.getTimeUnitWidth() * (i * STEPS_OF_MAJOR_SCALE + stepsToFirstMajorScale);
			g.drawRect(xx, 0, comRect.width - 1, row_height);
			// Draw label
			label = this.formatToMajorLabel(firstStepOfChart, i + 1);
			if (logger.isDebugEnabled()) {
				logger.debug("Draw lable for large time scale: " + label);
			}
			g.drawChars(label.toCharArray(), 0, label.length(), xx + 4, 0 + row_height - 4);
		}
	}

	@Override
	protected void drawMinorTimeScaleBackground(int x, int y, int height) {
		if (logger.isDebugEnabled()) {
			logger.debug("Start to draw small time scale...");
		}
		int currentTimeStep = this.getCurrentTimeStep();// Step at cureent time: all day, week, month or year.
		for (int i = 0; i < totalStepsCount; i++) {
			// Draw background color.
			g.setColor(config.getWorkingTimeBackColor());
			g.fillRect(x + super.config.getTimeUnitWidth() * i, y, super.config.getTimeUnitWidth(), height);
			// Draw border for each step.
			g.setColor(Color.GRAY);
			g.drawRect(x + super.config.getTimeUnitWidth() * i, y, super.config.getTimeUnitWidth(), height);
		}

		if (currentTimeStep > 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("Draw this week, month or year's scale as step: " + currentTimeStep);
			}
			int xx = x + super.config.getTimeUnitWidth() * (currentTimeStep - 1);
			g.setColor(config.getCurrentTimeBackColor());
			PaintHelper.fillOppositeBevelFoggyRect(g, xx, y, super.config.getTimeUnitWidth(), height);
			g.setColor(Color.black);
			g.drawRect(xx, y, super.config.getTimeUnitWidth(), height);
		}

		super.drawMajorScaleSeperator();

		// Draw kickoff time and deadline.
		super.drawKickoffTime(x, y, height);
		super.drawDeadline(x, y, height);
		// Draw labels for every minor time scale.
		if (component instanceof TimeScaleView) {
			for (int i = 0; i < totalStepsCount; i++) {
				drawMinorTimeScaleLabel(i, x + super.config.getTimeUnitWidth() * i, row_height);
			}
		}
	}

	/**
	 * 
	 * @param i
	 * @param x
	 * @param y
	 */
	protected void drawMinorTimeScaleLabel(int i, int x, int y) {
		int posInMajorScale = 0;
		// Add one STEPS_IN_EACH_MAJOR_SCALE more is just for calculating simple. 
		posInMajorScale = (i - stepsToFirstMajorScale + STEPS_OF_MAJOR_SCALE) % STEPS_OF_MAJOR_SCALE + time_offset;
		String str = posInMajorScale + "";
		g.setColor(Color.black);
		g.drawChars(str.toCharArray(), 0, str.length(), x + 4, y + row_height - 4);
	}
}
