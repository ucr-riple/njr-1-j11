package org.swiftgantt;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.UIManager;

import org.swiftgantt.ui.TimeScaleUI;
import org.swiftgantt.ui.timeaxis.BaseTimeAxis;

/**
 * Viewport for time sacel at the top of the gantt chart.
 * @author Yuxing Wang
 *
 */
public class TimeScaleView extends BaseView implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private TimeScaleUI tasUI = null;

	public TimeScaleView(GanttChart ganttChart) {
		super(ganttChart);
		initialize();
	}

	private void initialize() {
		logger.info("Start to initialize TimeScaleView");
		this.setSize(new Dimension(300, 100));
		this.setMinimumSize(new Dimension(300, 100));
		tasUI = (TimeScaleUI) UIManager.getUI(this);//Get UI by ID "TimeScaleUI".
		super.setUI(tasUI);
		logger.info("Set UI for TimeScaleView");
	}

	@Override
	public String getUIClassID() {
		return "TimeScaleUI";
	}

	protected void refreshDisplay() {
		if (this.tasUI == null || this.tasUI.getTimeAxis() == null) {
			return;
		}
		((BaseTimeAxis) this.tasUI.getTimeAxis()).setTotalStepsCount(totalSteps);
		// Adjust width of the Gantt chart to fulfill scrolling
		Dimension originalSize = this.getSize();
		if (logger.isDebugEnabled()) {
			logger.debug("TimeScaleView original component size is: " + originalSize);
		}
		// Calculate width of the TimeScaleView.
		int step_length = (super.config == null) ? Config.DEFAULT_TIME_UNIT_WIDTH : config.getTimeUnitWidth();
		int width = totalSteps * step_length;
		if (width < originalSize.width) {
			width = originalSize.width;
		}
		// Calculate height of the TimeScaleView.
		int rowHeight = Config.DEFAULT_GANTT_CHART_ROW_HEIGHT;
		int height = rowHeight * 2 + BORDER_WIDTH;//
		Dimension dim = new Dimension(width, height);
		if (logger.isDebugEnabled()) {
			logger.debug("TimeScaleView preferred size has been changed to: " + dim);
		}
		this.setPreferredSize(dim);
		this.setBounds(0, 0, dim.width, dim.height);
		this.revalidate(); // This method actually works for resizing to scrolling.
		//Refresh
		super.refreshView();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		this.refreshDisplay();
	}
}

