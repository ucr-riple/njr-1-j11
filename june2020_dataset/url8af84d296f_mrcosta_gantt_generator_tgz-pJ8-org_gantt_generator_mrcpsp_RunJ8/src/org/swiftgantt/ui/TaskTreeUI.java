package org.swiftgantt.ui;

import java.awt.*;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import org.swiftgantt.TaskTreeView;
import org.swiftgantt.common.EventLogger;
import org.swiftgantt.model.Task;
import org.apache.log4j.LogManager;

/**
 * 
 * @author Yuxing Wang
 * 
 */
public class TaskTreeUI extends BaseUI {
	protected TaskTreeView taskTreeView = null;

	private final int NODE_WIDTH = 9;

	int fontHeight = 0;
	int rowHeight = 0;
	int cellWidth = 0;

	int hScrollOffset = 5;//Horizontal scroll offset

	public TaskTreeUI() {
		logger = LogManager.getLogger(TaskTreeUI.class);
	}

	public static ComponentUI createUI(JComponent c) {
		return new TaskTreeUI();
	}

	@Override
	public void installUI(JComponent c) {
		taskTreeView = (TaskTreeView) c;
		super.installUI(c);
	}

	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		logger.info("Paint TaskTreeUI");
		rowHeight = super.ganttChart.getConfig().getGanttChartRowHeight();
		cellWidth = rowHeight / 2;
		fontHeight = g.getFontMetrics().getHeight();
		//
		int width = super.clientWidth;
		int height = super.clientHeight;
		g.setColor(super.ganttChart.getConfig().getTaskTreeViewBackColor());
		g.fillRect(-hScrollOffset, 0, width, height);
		// Draw task tree.
		g.setColor(Color.black);
		drawTasks(g);
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height - 1);
	}

	/*
	 * Draw all tasks in tree model.
	 */
	private void drawTasks(Graphics g) {
		if(super.ganttChart == null || super.ganttChart.getModel() == null
				|| super.ganttChart.getModel().getTaskTreeModel() == null){
			return;
		}

        List<Task> tasks = super.ganttChart.getModel().getTasksByBFS();

        drawTask(tasks, g);//drawTask() knows what's the next task row num.
	}

    private void drawTask(List<Task> tasks, Graphics g) {
        int rowNum = 0;

        for (Task task : tasks) {
            int x1 = 150;
            int y1 = rowNum * rowHeight;
            Rectangle taskRect = new Rectangle(x1, y1, taskTreeView.getWidth(), rowHeight);
            this.drawLabel(g, task.getName(), taskRect);

            rowNum++;
        }
    }

	/*
	 * Draw connection from left center tree node to label.
	 */
	private void drawConnection(Graphics g, Rectangle rect) {
		int x1 = rect.x - hScrollOffset;
		int x2 = rect.x + cellWidth - hScrollOffset;
		int y = rect.y + cellWidth;
		g.drawLine(x1, y, x2, y);
	}

	/*
	 * Draw the expanded node on the top of left border center.
	 */
	private void drawNode(Graphics g, Rectangle rect) {
		int x1 = rect.x - NODE_WIDTH / 2;
		int x2 = x1 + NODE_WIDTH;
		int y1 = (rowHeight - NODE_WIDTH) / 2 + rect.y;
		int y2 = y1 + NODE_WIDTH / 2 + 1;
		//		g.drawLine(rect.x, rect.y - rowHeight/2, rect.x, y1); //Draw the connector from node above to current node. 
		g.setColor(taskTreeView.getBackground());
		g.fillRect(x1, y1, NODE_WIDTH, NODE_WIDTH);
		g.setColor(Color.black);
		g.drawRect(x1, y1, NODE_WIDTH, NODE_WIDTH); //Draw the expanded node.
		g.drawLine(x1, y2, x2, y2); // Draw the connector from expanded node to the label.
	}

	/*
	 * 
	 */
	private void drawLabel(Graphics g, String label, Rectangle rect) {
		if (label == null) {
			return;
		}
		int x = rect.x + cellWidth;
		int y = rect.y + rowHeight - fontHeight / 2;
		int width = 1000; // Temporary 100 for width.
		int height = fontHeight;
		g.setColor(super.ganttChart.getConfig().getTaskTreeViewBackColor());
		g.fillRect(x, rect.y, width, height);
		g.setColor(Color.black);
		//		g.drawRect(x, rect.y, width, height);
		g.drawChars(label.toCharArray(), 0, label.length(), x, y);
	}

	public int getHorizonScrollOffset() {
		return hScrollOffset;
	}

	public void setHorizonScrollOffset(int horizonScrollOffset) {
		this.hScrollOffset = horizonScrollOffset;
	}

}
