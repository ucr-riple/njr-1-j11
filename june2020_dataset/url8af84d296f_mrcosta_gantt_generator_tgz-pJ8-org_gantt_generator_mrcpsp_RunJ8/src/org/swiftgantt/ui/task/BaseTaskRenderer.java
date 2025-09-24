package org.swiftgantt.ui.task;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.Config;
import org.swiftgantt.GanttChart;
import org.swiftgantt.ui.Paintable;

/**
 * The basic super renderer class for tasks. Rendering task's shape in Gantt Chart.<br/>
 * <code>createDiamondShape</code> method for painting diamond shape
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public abstract class BaseTaskRenderer implements Paintable {

	protected Logger logger = null;
	protected boolean isDebug = false;
	protected int step_length = 0;
	protected int row_height = 0;
	protected int task_bar_height = 0;
	protected int progress_bar_height = 0;
	protected int padding_v = 0; //Padding to top and bottom
	protected Graphics g = null;
	protected GanttChart gantt = null;
	protected Config config = null;
	protected Rectangle rect = null;

	public BaseTaskRenderer() {
		logger = LogManager.getLogger(BaseTaskRenderer.class);
	}

	public void paint(Graphics g, JComponent c, Rectangle rec) {
		step_length = this.gantt.getConfig().getTimeUnitWidth();
		row_height = this.config.getGanttChartRowHeight();
		task_bar_height = this.config.getTaskBarHeight();
		progress_bar_height = this.config.getProgressBarHeight();
		padding_v = (row_height - task_bar_height) / 2;
	}

	/**
	 * Create diamond shape for milestone.
	 * @param topX
	 * @param topY
	 * @param width
	 * @param height
	 * @return
	 */
	protected Polygon createDiamondShape(int topX, int topY, int width, int height) {
		topY += padding_v;
		int[] xPoints = new int[]{topX, topX + width / 2, topX, topX - width / 2};
		int[] yPoints = new int[]{topY, topY + height / 2, topY + height, topY + height / 2};
		return new Polygon(xPoints, yPoints, 4);
	}

	/**
	 * Create diamond shape for task group
	 * @param topX
	 * @param topY
	 * @param radius
	 * @return
	 */
	protected Polygon createDiamondShape(int topX, int topY, int radius) {
		int[] xPoints = new int[]{topX, topX + radius, topX, topX - radius};
		int[] yPoints = new int[]{topY, topY + radius, topY + radius * 2, topY + radius};
		return new Polygon(xPoints, yPoints, 4);
	}
}
