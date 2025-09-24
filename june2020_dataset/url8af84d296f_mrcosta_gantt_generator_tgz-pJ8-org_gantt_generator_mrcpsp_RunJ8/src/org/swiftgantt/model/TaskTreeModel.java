package org.swiftgantt.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.common.Constants;
import org.swiftgantt.common.Time;
import org.swiftgantt.ui.TimeUnit;

/**
 * Part of the <code>GanttModel</code>, represent the tree structure of tasks, it is inherited from <code>DefaultTreeModel</code> to
 * support <code>JTree</code> Swing tree view.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class TaskTreeModel extends DefaultTreeModel implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	protected Logger logger = null;

	private static Task rootNode = new Task("Root", new Time(), new Time());

	private TimeUnit timeUnit = null;

	private int tasksCount = 0;

	public TaskTreeModel() {
		super(rootNode);
		logger = LogManager.getLogger(this.getClass());
	}

	public int getLevels() {
		int deepest = 0;
		List<Task> list = this.getTasksByDFS();
		for (int i = 0; i < list.size(); i++) {
			int curLevel = (list.get(i)).getLevel();
			if (deepest < curLevel) {
				deepest = curLevel;
			}
		}
		return deepest;
	}

	/**
	 * Get count of all tasks in this model.
	 * 
	 * @return
	 */
	public int getTasksCount() {
		tasksCount = 0; // Reset.
		for (int i = 0; i < rootNode.getChildCount(); i++, tasksCount++) {
			Task curTask = (Task) rootNode.getChildAt(i);
			tasksCount += curTask.getTasksCount();
		}
		return tasksCount;
	}

	/**
	 * Add new task to the root node of the <code>TaskTreeModel</code>.
	 * 
	 * @param task
	 */
	public void add(Task newTask) {
		//TODO Need check new task existence first.
		super.insertNodeInto(newTask, rootNode, rootNode.getChildCount());
		newTask.setTreeModel(this);
		newTask.setLevel(1);
		newTask.addPropertyChangeListener(this);
		super.fireTreeNodesChanged(newTask, super.getPathToRoot(newTask), null, null);
		logger.info("Add task to root: " + newTask.toString());
	}

	/**
	 * Add new task to specified task as sub-task in the <code>TaskTreeModel</code>.
	 * 
	 * @param parent
	 * @param newTask
	 */
	public void addTo(Task parent, Task newTask) {
		super.insertNodeInto(newTask, parent, parent.getChildCount());
		newTask.setTreeModel(this);
		newTask.setLevel(parent.getLevel() + 1);
		newTask.addPropertyChangeListener(this);
		super.fireTreeNodesChanged(newTask, super.getPathToRoot(newTask), null, null);
		logger.info("Add task : <" + newTask.toString() + "> to parent task: <" + this.toString() + ">");
	}

	/**
	 * Remove Task from root node of the <code>TaskTreeModel</code>.
	 * 
	 * @param task
	 */
	public void removeTask(Task task) {
		task.removePropertyChangeListener(this);
		super.removeNodeFromParent(task);
	}

	/**
	 * Remove all task from <code>TaskTreeModel</code>.
	 */
	public void removeAll() {
		rootNode.removeAllChildren();
		this.nodeStructureChanged(rootNode);
	}

	/**
	 * Get Task by ID from given task node includes his sub-trees.
	 * 
	 * @param id
	 * @return
	 */
	public Task getTask(int id) {
		return getTaskFrom(rootNode, id);
	}

	/*
	 * Get task by task id from given task node includes his sub-trees.
	 */
	private Task getTaskFrom(MutableTreeNode parent, int id) {
		Enumeration<? extends TreeNode> tasks = parent.children();
		while (tasks.hasMoreElements()) {
			TreeNode node = tasks.nextElement();
			Task task = (Task) node;
			if (task.getId() == id) {
				return task; // Find in current level.
			}
			task = getTaskFrom(task, id); // Find in sub tree.
			if (task != null) {
				return task;
			}
		}
		return null;
	}

	/**
	 * Get all 1st level tasks.
	 * 
	 * @return
	 */
	public List<Task> getChildren() {
		List<Task> ret = new ArrayList<Task>();
		Enumeration<? extends TreeNode> e = this.root.children();
		while (e.hasMoreElements()) {
			Task t = (Task) e.nextElement();
			ret.add(t);
		}
		return ret;
	}

	/**
	 * Get all tasks from this model by Depth-First-Search principle.
	 * 
	 * @return
	 */
	public List<Task> getTasksByDFS() {
		List<Task> tasks = new ArrayList<Task>();
		getTasksByDFS(rootNode, tasks);
		return tasks;
	}

	/*
	 * Called by getTasksByDFS().
	 */
	private void getTasksByDFS(MutableTreeNode parent, List<Task> result) {
		Enumeration<? extends TreeNode> enu = parent.children();
		while (enu.hasMoreElements()) {
			Task cur = (Task) enu.nextElement();
			result.add(cur);
			if (cur.isLeaf() == false) {
				getTasksByDFS(cur, result); // Recursive
			}
		}
	}

	/**
	 * Get all tasks by Breadth-First-Search principle.
	 * 
	 * @return
	 */
	public List<Task> getTasksByBFS() {
		List<Task> tasks = new ArrayList<Task>();
		getTasksByBFS(rootNode, tasks);
		return tasks;
	}

	private void getTasksByBFS(MutableTreeNode parent, List<Task> result) {
		Enumeration<? extends TreeNode> enu = parent.children();
		while (enu.hasMoreElements()) {
			result.add((Task) enu.nextElement());
		}
		enu = parent.children();
		while (enu.hasMoreElements()) {
			Task t = (Task) enu.nextElement();
			if (t.isLeaf() == false) {
				getTasksByBFS(t, result); // Recursive
			}
		}
	}

	/**
	 * Get the end time of latest task in this TaskTreeModel,
	 * 
	 * @return If no tasks exist, return null.
	 */
	public Time getEndTimeOfLatestTask() {
		Task lastTask = this.getLatestTask();
		if (lastTask == null)
			return null;
		return lastTask.getActualEnd();
	}

	/**
	 * Get the latest task by task end time.
	 * 
	 * @return null if no tasks there
	 */
	public Task getLatestTask() {
		Task latestTask = null;
		latestTask = rootNode.getLatestSubTask();
		logger.debug("Get latest sut-task in the TaskTreeModel: " + latestTask);
		return latestTask;
	}

	/**
	 * Handle the task changes. Fire tree node change event to GanttModel.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getNewValue() != null && evt.getNewValue().getClass().equals(GregorianCalendar.class)) {
			String oldv = evt.getOldValue() == null ? null : DateFormatUtils.format(((Time) evt.getOldValue()).getTimeInMillis(),
					Constants.TIME_FORMAT_YYYY_MM_DD_HH_MM_SS_BAR);
			String newv = DateFormatUtils.format(((Time) evt.getNewValue()).getTimeInMillis(),
					Constants.TIME_FORMAT_YYYY_MM_DD_HH_MM_SS_BAR);
			logger.info("Task property " + evt.getPropertyName() + " changed from: " + oldv + " to " + newv);
		} else {
			logger.info("Task property " + evt.getPropertyName() + " changed from: " + evt.getOldValue() + " to " + evt.getNewValue());
		}
		Task task = (Task) evt.getSource();
		TreeNode[] path = this.getPathToRoot(task);
		super.fireTreeNodesChanged(task, path, null, null);
	}

	// Deprecated by singleton TimeUnit in GanttChart, maybe except Task
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	/**
	 * Set TimeUnit to all tasks.
	 */
	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}
}