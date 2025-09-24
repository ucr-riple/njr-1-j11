package org.swiftgantt.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import org.swiftgantt.GanttChart;
import org.swiftgantt.ChartView;
import org.swiftgantt.ui.task.BaseTaskRenderer;
import org.swiftgantt.ui.task.DefaultTaskRenderer;
import org.apache.log4j.LogManager;

/**
 * The implementation from {@link BaseUI} for {@link org.swiftgantt.ChartView}.
 * 
 * @author Yuxing Wang
 * @version 1.0
 * @see BaseUI
 */
public class ChartViewUI extends BaseUI {
	protected ChartView ganttChartView = null;

	protected BaseTaskRenderer taskRenderer = null;

	public ChartViewUI() {
		logger = LogManager.getLogger(ChartViewUI.class);
	}

	/**
	 * Override from super class because this method is not implemented.
	 * 
	 * @param c
	 * @return
	 */
	public static ComponentUI createUI(JComponent c) {
		return new ChartViewUI();
	}

	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		if (logger.isDebugEnabled()) {
			logger.info("Paint as " + super.timeAxis.getClass().getName() + " for GanttChartViewUI");
		}
		int width = 0;
		width = this.ganttChartView.getTotalSteps() * GanttChart.getStaticConfig().getTimeUnitWidth();

		int height = super.clientHeight;
		// Draw time axis.
		Rectangle rec = new Rectangle(0, 0, width, height);
		super.timeAxis.paint(g, c, rec);

		//		BaseTimeAxis bta = (BaseTimeAxis)super.timeAxis;
		//        int stepsToFirstMajorScale = bta.getStepsToFirstMajorScale();
		//        int majorScaleSteps = bta.STEPS_OF_MAJOR_SCALE;
		//        int stepLen = this.ganttChart.getConfig().getTimeUnitWidth();
		int rowHeight = super.ganttChart.getConfig().getGanttChartRowHeight();
		//        int tasksCount = this.ganttChart.getModel().getTaskTreeModel().getTasksCount();
		//        g.drawLine(stepsToFirstMajorScale * stepLen, 0, stepsToFirstMajorScale * stepLen, tasksCount * rowHeight + 500);
		//        
		// Render Tasks
		Rectangle rec2 = new Rectangle(0, 0, width, height - rowHeight * 2);
		
		this.taskRenderer.paint(g, c, rec2);
		// Draw border for entire view
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		logger.debug("Paint GanttChartViewUI done");
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		ganttChartView = (ChartView)c;
		this.taskRenderer = new DefaultTaskRenderer(super.ganttChart);
	}

	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		//        GanttChartView ganttView = (GanttChartView)c;
	}

}
