package org.swiftgantt.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.BaseView;
import org.swiftgantt.Config;
import org.swiftgantt.GanttChart;
import org.swiftgantt.common.EventLogger;
import org.swiftgantt.common.LogHelper;
import org.swiftgantt.event.TimeUnitChangeEvent;
import org.swiftgantt.event.TimeUnitChangeListener;
import org.swiftgantt.ui.timeaxis.AllDayTimeAxis;
import org.swiftgantt.ui.timeaxis.BaseTimeAxis;
import org.swiftgantt.ui.timeaxis.DailyTimeAxis;
import org.swiftgantt.ui.timeaxis.HourlyTimeAxis;
import org.swiftgantt.ui.timeaxis.MonthlyTimeAxis;
import org.swiftgantt.ui.timeaxis.TimeAxis;
import org.swiftgantt.ui.timeaxis.WeeklyTimeAxis;
import org.swiftgantt.ui.timeaxis.YearlyTimeAxis;

/**
 * The base class for gantt UI.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public abstract class BaseUI extends ComponentUI {

	protected Logger logger = null;
	protected GanttChart ganttChart = null;
	protected TimeAxis timeAxis = null; // For all UI.
	protected Config config = null; // For all UI.
	protected int clientWidth = 0;
	protected int clientHeight = 0;

	public BaseUI() {
		logger = LogManager.getLogger(LogHelper.LOGGER_UI);
		logger.setAdditivity(false);//不传递到上一级logger
	}

	@Override
	public void installUI(JComponent c) {
		logger.debug("Install UI " + c.getClass());
		BaseView ganttView = (BaseView) c;
		this.ganttChart = ganttView.getGanttChart();
		// If the time unit of gantt chart changed, the time axis must be changed also.
		this.ganttChart.addTimeUnitChangeListener(new TimeUnitChangeListener() {

			public void timeUnitChanged(TimeUnitChangeEvent evt) {
				EventLogger.event(evt, "Property " + evt.getPropertyName() + " changed from " + evt.getOldValue() + " to " + evt.getNewValue());
				// Init time axis object
				BaseTimeAxis tAxis = null;// The name is to avoid coflict.
				TimeUnit tu = evt.getNewTimeUnit();
				if (tu == TimeUnit.Hour) {
					tAxis = new HourlyTimeAxis(ganttChart);
				} else if (tu == TimeUnit.AllDay) {
					tAxis = new AllDayTimeAxis(ganttChart);
				} else if (tu == TimeUnit.Day) {
					tAxis = new DailyTimeAxis(ganttChart);
				} else if (tu == TimeUnit.Week) {
					tAxis = new WeeklyTimeAxis(ganttChart);
				} else if (tu == TimeUnit.Month) {
					tAxis = new MonthlyTimeAxis(ganttChart);
				} else if (tu == TimeUnit.Year) {
					tAxis = new YearlyTimeAxis(ganttChart);
				} else {
					throw new NullPointerException("Time Unit is not acceptable");
				}
				timeAxis = tAxis;
			}
		});
		this.ganttChart.fireTimeUnitChange(this, null, this.ganttChart.getTimeUnit());
	}

	/**
	 * 
	 */
	public void paint(Graphics g, JComponent c) {
		LogHelper.title(logger, "Start to paint gantt UI: " + c.getClass());
		logger.debug("The size of the component " + c.getSize());
		Insets insets = c.getInsets();
		g.translate(insets.left, insets.top);
		clientWidth = c.getPreferredSize().width - insets.left - insets.right;
		clientHeight = c.getPreferredSize().height - insets.top - insets.bottom;
		if (logger.isDebugEnabled()) {
			logger.debug("The client size adjusted to " + new Dimension(clientWidth, clientHeight));
		}
	}

	public TimeAxis getTimeAxis() {
		return timeAxis;
	}

	public void setTimeAxis(TimeAxis timeAxis) {
		this.timeAxis = timeAxis;
	}
}
