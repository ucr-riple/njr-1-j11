package org.swiftgantt.ui.task;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import org.swiftgantt.GanttChart;
import org.swiftgantt.model.GanttModel;
import org.swiftgantt.model.Task;
import org.swiftgantt.model.TaskHelper;
import org.swiftgantt.ui.PaintLogger;
import org.swiftgantt.ui.TimeUnit;
import org.swiftgantt.ui.timeaxis.TimeAxisUtils;

/**
 * The default implementation for {@link BaseTaskRenderer}.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class DefaultTaskRenderer extends BaseTaskRenderer {

	private int connectorStyle = 1;// 1 or 2.
	private List<TaskWrapper> tasks = null; // Tasks that need to be paint.

	public DefaultTaskRenderer(GanttChart gantt) {
		super.gantt = gantt;
		super.config = gantt.getConfig();
	}

	/**
	 * @param rec The client area that paint the tasks.
	 */
	@Override
	public void paint(Graphics g, JComponent c, Rectangle rec) {
		super.paint(g, c, rec);//Prepare paint.
		if(logger.isDebugEnabled()) {
			logger.info(StringUtils.center("Start to paint for all tasks.", 200, "-"));
		}
		super.g = g;
		super.rect = new Rectangle(0, 0, rec.width, rec.height);//rec;
		GanttModel model = gantt.getModel();
		if (model == null) {
			return;
		}
		TaskLocationManager.getInstance().clear();
		int[] selectedTaskIndices = model.getSelectedIds();//#
		tasks = TaskWrapper.wrapTasks(model.getTaskTreeModel().getTasksByDFS());
		TimeUnit tu = this.gantt.getTimeUnit();
		for (int i = 0; i < tasks.size(); i++) {
			TaskWrapper tw = tasks.get(i);
			int x = tw.calcTaskStartPointX(this.rect, tu, gantt.getModel().getKickoffTime());
			int y = tw.calcTaskPointY(this.rect, i, super.padding_v);
			if (logger.isDebugEnabled()) {
				PaintLogger.debug(new Point(x, y), rect, "Render the task: [" + i + "] " + tw.getTask());
//				logger.debug("Render the task: [" + i + "] '" + tw.getTask() + "' at " + x + "," + " y ");
			}
			float startVacancy = 0F;
			float endExcess = 0F;
			boolean isMilestone = false;
			if (TaskHelper.isAllowAccurateTaskBar(tu)) {
				startVacancy = tw.getStartTimeVacancy(tu);
				endExcess = tw.getEndTimeExcees(tu);
				if (logger.isDebugEnabled()) {
					logger.debug("Accurate TimeUnit: " + TimeUnit.getAccurateTimeUnit(tu) + "  " + tw.getTask().toString());
				}
				// Take all timeunit accurate at minute level for Chris Whitcombe and Bowen Wang.
				int actualInterval = tw.getTask().getActualStart().getMinuteIntervalTo(tw.getTask().getActualEnd());
				if (logger.isDebugEnabled()) {
					logger.debug("Actual Time Interval:" + actualInterval);
				}
				isMilestone = (actualInterval <= 0);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("actualTimeIntervalByTimeUnit:" + TimeAxisUtils.getActualTimeIntervalByTimeUnit(tu, tw.getTask()));
				}
				isMilestone = (TimeAxisUtils.getActualTimeIntervalByTimeUnit(tu, tw.getTask()) <= 0);
			}
			if (isMilestone) {
				logger.debug("  as milestone.");
				this.drawMilestone(tw, x, y);
			} else {
				if (tw.getTask().isLeaf() == true) {
					if (logger.isDebugEnabled()) {
						logger.debug("  as task bar, whose startVacancy=" + startVacancy + " and endExcess=" + endExcess);
					}
					// Draw task bar.
					int steps = tw.calcTaskSteps();
					drawLeafTask(x, y, steps, startVacancy, endExcess, tw);
				} else {
					logger.debug("  as task group.");
					// Draw the task group bar.
					Task earlistTask = tw.getTask().getEarliestTask();
					Task latestTask = tw.getTask().getLatestTask();
					int steps = TimeAxisUtils.getTimeIntervalByTimeUnit(tu, earlistTask.getActualStart(), latestTask.getActualEnd());
					//    			System.err.println("---------------- Task " + task.getName() + "  " + steps);
					drawNonLeafTask(x, y, steps + 1, startVacancy, endExcess, tw.getTask());
				}
			}// If not milestone task
			if (tw.getTask().getPredecessors().size() > 0) {
				drawConnectToPredecesor(tw);
			}
			// Draw selection row
			if (ArrayUtils.contains(selectedTaskIndices, tw.getTask().getId())) {
				this.drawSelectedRow(i, rect.width);
			}
		//			if (tw.getTask().getId() == this.gantt.getModel().getSelectedId()) {//TODO
		//				this.drawSelectedRow(i, rect.width);
		//			}
		} // Loop for tasks
	//this.drawDebugLocations(g);
	}

	protected void drawDebugLocations(Graphics g) {
		if (logger.isDebugEnabled()) {
			g.setColor(Color.black);
			Collection<Shape> ss = TaskLocationManager.getInstance().getAllLocations();
			for (Shape t : ss) {
				if (t instanceof Rectangle) {
					g.drawRect(((Rectangle) t).x, ((Rectangle) t).y, ((Rectangle) t).width, ((Rectangle) t).height);
				} else if (t instanceof Polygon) {
					//TODO
				}
			}
		}
	}

	private void drawMilestone(TaskWrapper tw, int x, int y) {
		// Draw task as dimond shape for milestone.
		int width = step_length;
		int height = super.config.getTaskBarHeight();
		g.setColor(super.config.getTaskGroupBarBackColor());
		Polygon poly = super.createDiamondShape(x + width / 2, y - super.padding_v, width, height);
		g.fillPolygon(poly);

		// Draw task information.
		if (super.config.isShowTaskInfoBehindTaskBar()) {
			String taskInfo = this.isDebug ? tw.getTask().toSimpleString() : tw.getTask().getName();
			g.setColor(Color.black);
			int startX = x + width + 16;
			int actualY = y + 12;
			g.drawChars(taskInfo.toCharArray(), 0, taskInfo.length(), startX, actualY);
		}
	}

	/** Draw non-leaf task, which is the task group. Adjusted the end time by the last sub task */
	private void drawNonLeafTask(int x, int y, int steps, float preVacancy, float seqExcess, Task task) {
		String taskInfo = this.isDebug ? task.toSimpleString() : task.getName();
		if (logger.isInfoEnabled()) {
			logger.info("Paint task group " + taskInfo + " at [" + x + "," + y + "], length is " + steps);
		}
		int radius = super.config.getTaskBarHeight() / 2;
		float preVacaLen = preVacancy * step_length;
		int actualX = x + (int) preVacaLen;
		g.setColor(super.config.getTaskGroupBarBackColor());
		// Draw left cursor
		Polygon leftPoly = super.createDiamondShape(actualX, y, radius);
		g.fillPolygon(leftPoly);
		// Draw bar
		float barLength = 0;
		if (TaskHelper.isAllowAccurateTaskBar(gantt.getTimeUnit()) == true) {
			barLength = (steps - 1 - preVacancy + seqExcess) * super.step_length;// For accurate task bar.
		} else {
			barLength = steps * super.step_length;
		}
		int actualLen = (int) barLength + radius * 2;
		Rectangle barRect = new Rectangle(actualX - radius, y, actualLen, radius);
		g.fillRect(barRect.x, barRect.y, barRect.width, barRect.height);
		TaskLocationManager.getInstance().addTask(task, barRect);//TODO
		// Draw right cursor
		Polygon rightPoly = super.createDiamondShape(actualX + (int) barLength, y, radius);
		g.fillPolygon(rightPoly);
		// Draw task information
		g.setColor(Color.black);
		if (super.config.isShowTaskInfoBehindTaskBar()) {
			g.drawChars(taskInfo.toCharArray(), 0, taskInfo.length(), actualX + actualLen, y + 8);
		}
	}

	private void drawConnectToPredecesor(TaskWrapper tw) {
		if (logger.isDebugEnabled()) {
			logger.debug("Paint connector lines from task: " + tw + " to all " + tw.getTask().getPredecessors().size() + " predecessors");
		}
		TimeUnit tu = gantt.getTimeUnit();
		List<TaskWrapper> preds = TaskWrapper.wrapTasks(tasks, tw.getTask().getPredecessors());
		for (int i = 0; i < preds.size(); i++) {
			TaskWrapper pred = preds.get(i);
			float distance = TaskHelper.calcOffsetByTimeUnit(tu, pred.getTask().getActualEnd(), tw.getTask().getActualStart()) - 1;
			if (logger.isDebugEnabled()) {
				logger.debug("Distance from predecessor to task is " + distance);
			}
			if (distance > 0) {
				if (TaskHelper.isAllowAccurateTaskBar(tu)) {
					// For accurate task bar.
					distance += (1 - pred.getEndTimeExcees(tu)) + tw.getStartTimeVacancy(tu);
				}
				this.drawDistantConnectToPredecessor(tw, pred, distance);
			} else {
				this.drawCloseConnectToPredecessor(tw, pred);
			}
		//DebugConsole.debug("Task :'" + pred.getName() + "' is the predecessor of task: '" + thisTask.getName() + "'");
		}
	}

	private void drawCloseConnectToPredecessor(TaskWrapper tw, TaskWrapper pred) {
		TimeUnit tu = gantt.getTimeUnit();
		int offset = 10; // Offset for connect lines.
		int thisIndex = tasks.indexOf(tw);
		int predIndex = tasks.indexOf(pred);
		int startX = pred.calcTaskStartPointX(super.rect, tu, gantt.getModel().getKickoffTime());
		int endX = pred.calcTaskEndPointX(startX);
		if (TaskHelper.isAllowAccurateTaskBar(tu)) {
			endX -= (1 - pred.getEndTimeExcees(tu)) * super.step_length;// For accurate task bar.
		}
		int y = pred.calcTaskPointY(rect, predIndex, padding_v);
		if (logger.isDebugEnabled()) {
			logger.debug("#########" + rect + " " + predIndex + " " + padding_v);
		}
		Point ps = new Point(endX, y + super.task_bar_height / 2);//Start point from predecessor.
		Point p1 = new Point(endX + offset, ps.y);//1st corner point
		Point p2 = new Point(p1.x, ps.y + super.row_height / 2);//2ed corner point
		Point p3 = new Point(endX - offset, p2.y);// 3rd corner point
		Point p4 = new Point(p3.x, y + super.task_bar_height / 2 + (thisIndex - predIndex) * super.row_height);//4th corner point
		Point pe = new Point(endX, p4.y);// End point at current task bar.
		g.drawLine(ps.x, ps.y, p1.x, p1.y);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		g.drawLine(p2.x, p2.y, p3.x, p3.y);
		g.drawLine(p3.x, p3.y, p4.x, p4.y);
		g.drawLine(p4.x, p4.y, pe.x, pe.y);
		g.drawLine(pe.x - 5, pe.y + 3, pe.x, pe.y);
		g.drawLine(pe.x - 5, pe.y - 3, pe.x, pe.y);
	}

	/*
	 * @param distance Distance from predecessor's end to this tasks start.
	 */
	private void drawDistantConnectToPredecessor(TaskWrapper t, TaskWrapper pred, float distance) {
		TimeUnit tu = gantt.getTimeUnit();
		int offset = 10; // Offset for connect lines.
		int thisIndex = tasks.indexOf(t);
		int predIndex = tasks.indexOf(pred);
		int startPX = pred.calcTaskStartPointX(rect, gantt.getTimeUnit(), gantt.getModel().getKickoffTime());
		int endX = pred.calcTaskEndPointX(startPX);
		if (TaskHelper.isAllowAccurateTaskBar(tu)) {
			endX -= (1 - pred.getEndTimeExcees(tu)) * super.step_length;// For accurate task bar.
		}
		int y = pred.calcTaskPointY(rect, predIndex, padding_v);
		Point ps = new Point(endX, y + super.task_bar_height / 2);
		Point pe = new Point(endX + (int) (distance * super.step_length), y + super.task_bar_height / 2 + (thisIndex - predIndex) * super.row_height);
		Point p1 = null, p2 = null;
		if (this.connectorStyle == 1) {
			p1 = new Point(ps.x + offset, ps.y);
			p2 = new Point(p1.x, pe.y);
		} else if (this.connectorStyle == 2) {
			p1 = new Point(pe.x - offset, ps.y);
			p2 = new Point(p1.x, pe.y);
		}
		g.drawLine(ps.x, ps.y, p1.x, p1.y);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		g.drawLine(p2.x, p2.y, pe.x, pe.y);

		//Draw Arrow.
		g.drawLine(pe.x - 5, pe.y + 3, pe.x, pe.y);
		g.drawLine(pe.x - 5, pe.y - 3, pe.x, pe.y);
	}

	/**
	 * Leaf task is the lowest level task in the task tree.
	 * @param x
	 * @param y
	 * @param steps
	 * @param preVacancy Pre vacancy before first full step.
	 * @param seqExcess Sequent excess after last full step.
	 * @param task
	 */
	private void drawLeafTask(int x, int y, int taskSteps, float preVacancy, float seqExcess, TaskWrapper tw) {
		if (logger.isInfoEnabled()) {
			logger.info("Paint task " + tw.getTask() + " at [" + x + "," + y + "], length is " + taskSteps + " with previous vacancy" + preVacancy + ", and sequential excess" + seqExcess);
		}
		int actualX = x;//actual start x for task.
		float taskLen = 0;//任务条长度
		float pgsLen = 0;//进度条长度

		// Draw Noraml Task Bar（任务条）
		Color taskBarBackcolor = tw.getTask().getBackcolor() != null ? tw.getTask().getBackcolor() : super.config.getTaskBarBackColor();
		g.setColor(new Color(taskBarBackcolor.getRed(), taskBarBackcolor.getGreen(), taskBarBackcolor.getBlue(), 255));
		float preVacaLen = preVacancy * step_length;
		if (logger.isDebugEnabled()) {
			logger.debug("preVacaLen=" + preVacaLen);
		}
		int pgsSteps = tw.getTask().calcProgressSteps();
		if (TaskHelper.isAllowAccurateTaskBar(gantt.getTimeUnit()) == true) {
			taskLen = (taskSteps - 1 - preVacancy + seqExcess) * super.step_length;// For accurate task bar.
			if (taskLen < 1L && taskLen >= 0) {
				taskLen = 1L;//At least on pixel.
			}
			if (pgsSteps > taskSteps) {
				pgsSteps = taskSteps;
			}
			if (pgsSteps > 0) {
				pgsLen = (pgsSteps - preVacancy - (1 - seqExcess)) * super.step_length;
			}
		} else {
			taskLen = taskSteps * super.step_length;
			pgsLen = pgsSteps * super.step_length;
		}
		actualX = x + (int) preVacaLen;// For accurate task bar.
		Rectangle taskRect = new Rectangle(actualX, y, (int) taskLen, super.task_bar_height);
		if (logger.isDebugEnabled()) {
			PaintLogger.debug(taskRect, "Paint " + taskSteps + "steps task " + tw.getTask());
		}
		TaskLocationManager.getInstance().addTask(tw.getTask(), taskRect);
		g.fillRect(taskRect.x, taskRect.y, taskRect.width, taskRect.height);

		// Draw Progress Bar(进度条)
		if (pgsSteps > 0) {
			Color pgsBarColor = super.config.getProgressBarBackColor();
			g.setColor(pgsBarColor);
			int pgsY = y + (super.task_bar_height - super.progress_bar_height) / 2;
			Rectangle pgsRect = new Rectangle(actualX, pgsY, (int) pgsLen, super.progress_bar_height);
			if (logger.isDebugEnabled()) {
				PaintLogger.debug(pgsRect, "Paint progress with progress " + pgsSteps);
			}
			g.fillRect(pgsRect.x, pgsRect.y, pgsRect.width, pgsRect.height);
		}

		// Draw selection
		if (ArrayUtils.contains(this.gantt.getModel().getSelectedIds(), tw.getTask().getId())) {
			drawSelection(taskRect);
		}

		// Draw task information.
		if (super.config.isShowTaskInfoBehindTaskBar()) {
			String taskInfo = this.isDebug ? tw.getTask().toSimpleString() : tw.getTask().getName();
			g.setColor(Color.black);
			int startX = actualX + (int) taskLen + 16;
			int actualY = y + 12;
			g.drawChars(taskInfo.toCharArray(), 0, taskInfo.length(), startX, actualY);
		}
	}

	//
	private void drawSelection(Shape shape) {
		if (true) {
			return;//TODO
		}
		Color selectionColor = config.getSelectionColor();
		g.setColor(new Color(selectionColor.getRed(), selectionColor.getGreen(), selectionColor.getBlue(), 130));
		if (shape instanceof Rectangle) {
			Rectangle r = (Rectangle) shape;
			int x = r.x;
			int y = r.y;
			g.fillRect(x, y + rect.y, r.width, r.height);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(x - 1, y + this.rect.y - 1, r.width + 1, r.height + 1);
		} else if (shape instanceof Polygon) {
			//TODO
		}
	}

	//
	private void drawSelectedRow(int row, int width) {
		Color selectionColor = config.getSelectionColor();
		g.setColor(new Color(selectionColor.getRed(), selectionColor.getGreen(), selectionColor.getBlue(), 80));
		g.fillRect(0, config.getGanttChartRowHeight() * row, width, config.getGanttChartRowHeight());
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, config.getGanttChartRowHeight() * row, width, config.getGanttChartRowHeight());
	}
}
