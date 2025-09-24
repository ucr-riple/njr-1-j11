package org.swiftgantt;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Scrollable;
import javax.swing.UIManager;

import org.swiftgantt.model.GanttModel;
import org.swiftgantt.model.TaskTreeModel;
import org.swiftgantt.ui.TaskTreeUI;

/**
 * Viewport for the task tree in left side.
 * @author Yuxing Wang
 *
 */
public class TaskTreeView extends BaseView implements AdjustmentListener, Scrollable, PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private TaskTreeUI ttUI = null;
	private int taskCount = 0;
	
	public TaskTreeView(GanttChart ganttChart) {
		super(ganttChart);
		initialize();
	}
	
	private void initialize() {
		logger.info("Start to initialize TaskTreeView");
		this.setSize(new Dimension(180, 100));
		this.setMinimumSize(new Dimension(30, 50));
		ttUI = (TaskTreeUI)UIManager.getUI(this);//Get UI by ID "TaskTreeUI".
    	super.setUI(ttUI);
    	logger.info("Set UI for TaskTreeView");
	}

	@Override
	public String getUIClassID() {
		return "TaskTreeUI";
	}

	protected void refreshDisplay() {
		GanttModel ganttModel = super.ganttChart.getModel();
		if(ganttModel == null){
			return;//The model is not ready, no one can refresh this view.
		}
		TaskTreeModel treeModel = super.ganttChart.getModel().getTaskTreeModel();
   	
		Dimension originalSize = this.getSize();
		logger.debug("TaskTreeView original component size is: " + originalSize); 
		int rowHeight = (super.config == null)? Config.DEFAULT_GANTT_CHART_ROW_HEIGHT:config.getGanttChartRowHeight();
		// Calculate width of the TimeScaleView.
		int levels = treeModel.getLevels();
		int width = (levels + 1) * rowHeight/2;
		if(width < originalSize.width){
			width = originalSize.width;
		}
		// Calculate height of the TimeScaleView.
		int height = rowHeight * this.taskCount;
		if(height < this.getGanttChart().getRowHeader().getSize().height){
			height = this.getGanttChart().getRowHeader().getSize().height;
		}
		Dimension dim = new Dimension(width, height);
		logger.debug("TaskTreeView preferred size has been changed to: " + dim);
		// Change preferred size here.
		this.setPreferredSize(dim);
		this.setBounds(0, 0, dim.width, dim.height);
		this.revalidate(); // This method actually works for resizing to scrolling.
		super.refreshView();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		this.refreshDisplay();		
	}
	
	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	public Dimension getPreferredScrollableViewportSize() {
		return this.getPreferredSize();
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return config.getGanttChartRowHeight() * 4;
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return super.ganttChart.getConfig().getGanttChartRowHeight();
	}

	/**
	 * @see TaskTreeScrollBar
	 */
	public void adjustmentValueChanged(AdjustmentEvent e) {
		//TaskTreeScrollBar bar = (TaskTreeScrollBar)e.getSource();
		int curValue = e.getValue();
		logger.debug("Scroll to " + curValue);
		ttUI.setHorizonScrollOffset(curValue);
		this.refreshView();
	}
}

