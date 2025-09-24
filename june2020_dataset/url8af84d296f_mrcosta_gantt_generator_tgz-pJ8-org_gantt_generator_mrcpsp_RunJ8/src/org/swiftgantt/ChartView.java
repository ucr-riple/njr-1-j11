package org.swiftgantt;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Scrollable;
import javax.swing.UIManager;

import org.apache.commons.lang.ArrayUtils;

import org.swiftgantt.common.EventLogger;
import org.swiftgantt.model.Task;
import org.swiftgantt.ui.ChartViewUI;
import org.swiftgantt.ui.task.TaskLocationManager;
import org.swiftgantt.ui.timeaxis.BaseTimeAxis;

/**
 * Viewport for gantt chart main body, the schedule.
 * 
 * @author Yuxing Wang
 */
public class ChartView extends BaseView implements Scrollable, PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private ChartViewUI viewUI = null;
	private int taskCount = 0;

	public ChartView(GanttChart ganttChart) {
		super(ganttChart);
		initialize();
	}

	/**
	 * This method initialize this.
	 * 
	 */
	private void initialize() {
		logger.info("Start to initialize GanttChartView");
		//		this.setSize(new Dimension(300, 100));//Not work
		//		this.setMinimumSize(new Dimension(300, 100));//Not work
		this.addComponentListener(new java.awt.event.ComponentAdapter() {

			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				EventLogger.event(e, "GanttChart componentResized() to " + getSize());
			}
		});
		//Handle mouse events.
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				EventLogger.event(e, "mouseClicked at: " + e.getX() + ", " + e.getY());
				Task selected = TaskLocationManager.getInstance().getActiveTask(e.getPoint());
				int[] oldSelectionIndex = ganttChart.getModel().getSelectedIds();
				if (ArrayUtils.isEmpty(oldSelectionIndex) && selected == null) {
					logger.debug("没有已选中的，且没有选中");
					return;//没有已选中的，且没有选中
				}
				if (oldSelectionIndex != null && oldSelectionIndex.length == 1 && selected != null && selected.getId() == oldSelectionIndex[0]) {
					logger.debug("选中已选中的一个");
					return;//选中已选中的一个
				}
				boolean isNeedSelect = false;
				if (ArrayUtils.isEmpty(oldSelectionIndex) && selected != null) {
					logger.debug("没有已选中的，且选中一个");
					isNeedSelect = true;//没有已选中的，且选中一个
				} else if (oldSelectionIndex != null && oldSelectionIndex.length == 1 && selected != null && selected.getId() != oldSelectionIndex[0]) {
					logger.debug("已选中一个，且选中另一个");
					isNeedSelect = true;//已选中一个，且选中另一个
				} else if (oldSelectionIndex != null && oldSelectionIndex.length > 1 && selected != null) {
					logger.debug("已选中多个，且选中任何一个");
					isNeedSelect = true;//已选中多个，且选中任何一个
				}
				if (isNeedSelect == true) {
					//Selected task.
					ganttChart.getModel().setSelectedIds(new int[]{selected.getId()});
					ganttChart.fireSelectionChange(e.getSource(), selected);//
					return;
				}
				//有选中若干个，且没有选中
				if (oldSelectionIndex != null && oldSelectionIndex.length >= 1 && selected == null) {
					logger.debug("有选中若干个，且没有选中");
					//Deselected task.
					ganttChart.getModel().setSelectedIds(null);
					ganttChart.fireSelectionChange(e.getSource(), selected);//
					return;
				}
				logger.debug("什么也不干");
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		});
		viewUI = (ChartViewUI) UIManager.getUI(this);
		super.setUI(viewUI);
		logger.debug("GanttChartView object constructed");
	}

	/*
	 * Recalculate the total steps count and the size of the <code>GanttChart</code>
	 */
	protected void refreshDisplay() {
		((BaseTimeAxis) this.viewUI.getTimeAxis()).setTotalStepsCount(totalSteps);
		// Adjust width of the Gantt chart view to fulfill scrolling
		Dimension originalSize = this.getSize();
		if (logger.isDebugEnabled()) {
			logger.debug("GanttChartView original size is: " + originalSize);
		}
		// Calculate width of the Gantt chart view.
		int stepLength = config == null ? Config.DEFAULT_TIME_UNIT_WIDTH : config.getTimeUnitWidth();
		//		logger.debug("########## " + (config == null? Config.DEFAULT_TIME_UNIT_WIDTH:config.getTimeUnitWidth()));
		int width = totalSteps * stepLength;
		if (width < this.ganttChart.getViewport().getSize().width) {
			width = this.ganttChart.getViewport().getSize().width;
		}
		// Calculate height of the Gantt chart view.
		int rowHeight = config == null ? Config.DEFAULT_GANTT_CHART_ROW_HEIGHT : config.getGanttChartRowHeight();
		int height = taskCount * rowHeight;
		if (height < this.ganttChart.getViewport().getSize().height) {
			height = originalSize.height;
		}
		Dimension dim = new Dimension(width, height);
		if (logger.isDebugEnabled()) {
			logger.debug("GanttChartView preferred size has been set to: " + dim);
		}
		this.setPreferredSize(dim);
		this.setBounds(x, y, dim.width, dim.height);
		this.revalidate(); // This method actually works for resizing to scrolling.
		super.refreshView();
	}

	/**
	 * The Class ID for <code>UIManager</code>.
	 */
	@Override
	public String getUIClassID() {
		return "GanttChartViewUI";
	}


	/**
	 * Get scrollable view port size for <code>JScrollPane</code>
	 */
	public Dimension getPreferredScrollableViewportSize() {
		return this.getPreferredSize();
	}

	/**
	 * Get scrollable view port size for <code>JScrollPane</code>
	 */
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return config.getGanttChartRowHeight() * 4;
	}

	/**
	 * Get scrollable tracks view port height for <code>JScrollPane</code>
	 */
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	/**
	 * Get scrollable tracks view port width for <code>JScrollPane</code>
	 */
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	/**
	 * Get scrollable unit increment for <code>JScrollPane</code>
	 */
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return super.ganttChart.getConfig().getGanttChartRowHeight();
	}

	/**
	 * Handler the property changes of <code>GanttChart</code>
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (this.ganttChart.getModel() != null) {
			this.ganttChart.recalculateSteps();
			//Recalculate by property of GanttChart or Config which will affect data, like Config.allowAccurateTaskBar.
			this.ganttChart.getModel().recalculate();
		}
		this.refreshDisplay();
	}

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + ", " + this.getUIClassID();
	}

} //  @jve:decl-index=0:visual-constraint="10,10"

