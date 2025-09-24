package org.swiftgantt.ui.timeaxis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.BaseView;
import org.swiftgantt.Config;
import org.swiftgantt.GanttChart;
import org.swiftgantt.ChartView;
import org.swiftgantt.TimeScaleView;
import org.swiftgantt.common.PaintHelper;
import org.swiftgantt.common.Time;

/**
 * Base class for all time axis.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public abstract class BaseTimeAxis implements TimeAxis {
	//TODO 约定（此约定为最新约定，很多变量需要重构）
	//所有x,y,height,length结尾的整型数字表示绘图像素值
	//所有count结尾的整型数字表示时间轴单元数量

	public static int STEPS_OF_MAJOR_SCALE = 0; // Will be inited in inheritor.
	protected Logger logger = null;
	protected int stepsToFirstMajorScale = 0;
	// From config
	protected int row_height = 0;
	protected int step_length = 0;
	protected Graphics g = null;
	protected JComponent component = null; // The component that TimeAxis paint for.
	protected GanttChart gantt = null;
	protected Time kickoffTime = new Time();
	protected Time firstStepOfChart = new Time();
	/** Total steps in valid area */
	protected int totalStepsCount = 5; // Set default to 5 is just for debug easily.
	protected int totalMajorStepsCount = 0;
	/** Rectangle that needs to draw time axis on */
	protected Rectangle comRect = null;
	protected Config config = null;

	public BaseTimeAxis() {
		logger = LogManager.getLogger(this.getClass());
	}

	/**
	 * This paint after sub-class has painted.
	 */
	public void paint(Graphics g, JComponent c, Rectangle rect) {
		if (logger.isDebugEnabled()) {
			logger.info("Paint Time Axis");
		}
		beforePaint();
		config = gantt.getConfig();
		this.component = c;
		// Init the Time Axis
		this.g = g;
		this.comRect = rect;
		step_length = this.gantt.getConfig().getTimeUnitWidth();
		row_height = 24; //Height of axis row should be fixed.
		if (gantt.getModel() != null) {
			kickoffTime = gantt.getModel().getKickoffTime();
			firstStepOfChart = this.getFirstStepTimeOfChart(kickoffTime);
		}
	}

	protected void beforePaint() {
		// Do nothing
	}

	/**
	 * Get the time of first step of the Gantt Chart.
	 * 
	 * @param kickoffTime
	 * @return
	 */
	protected abstract Time getFirstStepTimeOfChart(Time kickoffTime);

	/**
	 * Calculate the position in a major scale for specified time. Resturn 1 if it is the first day.
	 * 
	 * @param time
	 * @return
	 */
	protected abstract int calcPositionInMajorScale(Time time);

	/*
	 * Be invoked after sub-class paint.
	 */
	protected void paintMutual() {
		if (logger.isDebugEnabled()) {
			logger.debug("Prepared to paint mutual part of time axis for steps: " + totalStepsCount + ", ");
		}
		//		if(totalStepsCount * config.getTimeUnitWidth() <= component.getWidth()){
		//			totalStepsCount = component.getWidth() / config.getTimeUnitWidth() + 1;
		//		}
		//		logger.debug("but actually for steps: " + totalStepsCount);
		int y = 0;
		int h = 0;
		if (component instanceof TimeScaleView) {
			if (logger.isDebugEnabled()) {
				logger.debug("Paint for TimeAxisScaleView");
			}
			// Draw 1st row of Chart head.
			g.setColor(gantt.getConfig().getGanttChartBackColor(Color.RED));
			g.fillRect(comRect.x, 0, comRect.width - 1, row_height); // Background
			g.setColor(Color.black);
			g.drawRect(comRect.x, 0, comRect.width - 1, row_height); // Border

			drawMajorTimeScaleBorder(comRect.x); // Do drawing in derived object.

			// Draw 2nd Row of Chart head.
			y = row_height;
			h = comRect.height - 1;
			drawMinorTimeScaleBackground(comRect.x, y, h);
			g.setColor(Color.black);

			// Draw boders of each cell of 2nd row.
			for (int i = 0; i < totalStepsCount; i++) {
				g.drawRect(comRect.x + step_length * i, row_height, step_length, row_height);
			}
			// Draw border of content rectangle.
			g.drawRect(comRect.x, 0, comRect.width - 1, (comRect.height - 1));

		} else if (component instanceof ChartView) {
			if (logger.isDebugEnabled()) {
				logger.debug("Paint for GanttChartView");
			}
			// Draw background the same to minor time scale.
			h = comRect.height - 1;
			drawMinorTimeScaleBackground(comRect.x, 0, h);
			if (logger.isDebugEnabled()) {
				logger.debug("The gantt chart view back color is " + gantt.getConfig().getGanttChartBackColor(Color.RED));
			}
			// Draw row sperator lines.
			//			drawRowSperator(height, componentRect.width);
		}
	}

	protected abstract void drawMajorTimeScaleBorder(int x);

	/**
	 * The time mustn't be changed in inheritor.
	 * 
	 * @param time
	 * @param offset
	 * @return
	 */
	protected abstract String formatToMajorLabel(Time time, int offset);

	/**
	 * the y and height in <code>TimeAxisScaleView</code> are different from <code>GanttChartView</code>
	 * 
	 * @param x left border offset
	 * @param y top border offset
	 * @param height
	 */
	protected abstract void drawMinorTimeScaleBackground(int x, int y, int height);

	protected int getCurrentTimeStep() {
		Time thisTime = new Time();
		int step = TimeAxisUtils.getTimeIntervalByTimeUnit(gantt.getTimeUnit(), this.firstStepOfChart, thisTime) + 1;
		if (step < 0 || step > this.totalStepsCount) {
			return 0;
		}
		return step;
	}

	/**
	 * Draw seperator for major scales in background of Gantt Chart view.
	 */
	protected void drawMajorScaleSeperator() {
		// Draw seperator line for each major time scale.
		g.setColor(Color.black);
		int majorLen = STEPS_OF_MAJOR_SCALE * this.step_length;
		for (int i = 0; i < totalMajorStepsCount; i++) {
			int x = i * majorLen + this.stepsToFirstMajorScale * this.step_length;
			int y2 = comRect.height;
			PaintHelper.drawDashedLine(g, x, 0, x, y2);
		}
	}

	/**
	 * Draw Kickoff Time.
	 * 
	 * @param x left border offset
	 * @param y top border offset
	 * @param height
	 */
	protected void drawKickoffTime(int x, int y, int height) {
		if (logger.isDebugEnabled()) {
			logger.debug("Draw Kickoff time at " + gantt.getConfig().getBlankStepsToKickoffTime());
		}
		x = x + step_length * gantt.getConfig().getBlankStepsToKickoffTime();
		this.drawKickoffOrDeadline(gantt.getConfig().getKickoffTimeBackColor(), x, y, height);
	}

	/**
	 * Draw Deadline.
	 * 
	 * @param x left border offset
	 * @param y top border offset
	 * @param height
	 */
	protected void drawDeadline(int x, int y, int height) {
		int blanks = gantt.getConfig().getBlankStepsToKickoffTime();
		int steps = ((BaseView) this.component).getTotalScheduleSteps();
		if (logger.isDebugEnabled()) {
			logger.debug("The steps count " + steps + ", the blank steps to deadline " + blanks);
		}
		x = x + step_length * (steps + blanks);
		this.drawKickoffOrDeadline(gantt.getConfig().getDeadlineBackColor(), x, y, height);
	}

	/**
	 * 
	 * @param color
	 * @param x
	 * @param y
	 * @param height
	 */
	protected void drawKickoffOrDeadline(Color color, int x, int y, int height) {
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80));
		g.fillRect(x, y, step_length, height);
		// I found the way to render transparent, so give up this solution.
		//PaintHelper.fillBevelFoggyRect(g, x, y, step_length, height);
		g.setColor(Color.WHITE);
		g.drawRect(x, y, step_length, height);
	}

	//@since 0.3.2
	protected void drawRowSperator(int height, int width) {
		int rowHeight = gantt.getConfig().getGanttChartRowHeight();
		int rows = height / rowHeight + 1;
		for (int i = 1; i < rows + 1; i++) {
			PaintHelper.drawDashedLine(g, 0, rowHeight * i, width, rowHeight * i);
		}
	}

	public int getTotalStepsCount() {
		return totalStepsCount;
	}

	public void setTotalStepsCount(int totalStepsCount) {
		this.totalStepsCount = totalStepsCount;
	}

	public int getStepsToFirstMajorScale() {
		return stepsToFirstMajorScale;
	}
}
