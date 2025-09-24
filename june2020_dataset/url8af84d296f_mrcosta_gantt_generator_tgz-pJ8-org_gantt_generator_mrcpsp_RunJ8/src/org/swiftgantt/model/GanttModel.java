package org.swiftgantt.model;

import java.util.List;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.common.EventLogger;
import org.swiftgantt.common.LogHelper;
import org.swiftgantt.common.Time;

/**
 * Represent the data model of the <code>GanttChart</code>,
 * consist of KickOffTime, Deadline and a <code>TaskTreeModel</code>.
 * 
 * @author Yuxing Wang
 * @version 1.0
 */
public class GanttModel {
	protected Logger logger = null;
	// Properties 
	private Time KickoffTime = new Time();

	private Time Deadline = new Time();

	private TaskTreeModel taskTreeModel = new TaskTreeModel();
	
	private int[] selectedIds = null;//since 0.3.4

	/**
	 * 
	 */
	public GanttModel() {
		logger = LogManager.getLogger(this.getClass());
		LogHelper.title(logger, "Start to construct and initialize GanttModel");
		taskTreeModel.addTreeModelListener(new javax.swing.event.TreeModelListener() {
			public void treeNodesChanged(TreeModelEvent e) {
				EventLogger.event(e, "treeNodesChanged()");
				// It is difficult and no need to identify the old value and new value.
				Task currentTask = (Task) e.getPath()[e.getPath().length - 1];
				fireGanttModelChange(e.getSource(), "TaskTreeModel", null, currentTask);
			}

			public void treeNodesInserted(TreeModelEvent e) {
				EventLogger.event(e, "treeNodesInserted()");
				Task currentTask = (Task) e.getPath()[e.getPath().length - 1];
				fireGanttModelChange(e.getSource(), "TaskTreeModel", null, currentTask);
			}

			public void treeNodesRemoved(TreeModelEvent e) {
				EventLogger.event(e, "treeNodesRemoved()");
				fireGanttModelChange(e.getSource(), "TaskTreeModel", e.getSource(), e.getTreePath());
			}

			public void treeStructureChanged(TreeModelEvent e) {
				EventLogger.event(e, "treeStructureChanged()");
				fireGanttModelChange(e.getSource(), "TaskTreeModel", e.getSource(), null);
			}
		});
	}

	protected transient GanttModelChangeEvent ganttModelChangeEvent = null;
	protected EventListenerList listenerList = new EventListenerList();

	/**
	 * Add Tasks to the root node of TaskTreeModel inside the GanttModel.
	 * 
	 * @param tasks
	 */
	public void addTask(Task[] tasks) {
		for (int i = 0; i < tasks.length; i++) {
			this.addTask(tasks[i]);
		}
	}

	/**
	 * Add Task to the root node of TaskTreeModel inside the GanttModel.
	 * 
	 * @param task
	 */
	public void addTask(Task task) {
		LogHelper.title(logger, "Add task : <" + task.toString() + "> to TaskTreeModel in GanttModel.");
//		task.setId(taskTreeModel.getTasksCount());
		taskTreeModel.add(task);
		task.adjustActualTimesByPredecessor();
	}
	
	/**
	 * Get task by task ID.
	 * @param id
	 * @return
	 */
	public Task getTask(int id){
		return this.taskTreeModel.getTask(id);
	}

	/**
	 * Get all tasks by Depth-First-Search principle.
	 * @return
	 */
	public List<Task> getTasksByDFS(){
		return taskTreeModel.getTasksByDFS();
	}

	/**
	 * Get all tasks by Breadth-First-Search principle.
	 * @return
	 */
	public List<Task> getTasksByBFS(){
		return taskTreeModel.getTasksByBFS();
	}

	/**
	 * Remove task from the Gantt chart model.
	 * 
	 * @param task
	 */
	public void removeTask(Task task) {
		LogHelper.title(logger, "Remove task : <" + task.toString() + "> from TaskTreeModel in GanttModel.");
		taskTreeModel.removeTask(task);
	}

	/**
	 * Remove all tasks from Gantt chart.
	 */
	public void removeAll() {
		LogHelper.title(logger, "Remove all tasks from TaskTreeModel in GanttModel.");
		this.taskTreeModel.removeAll();
	}
	
	/**
	 * Recaculate something that needs for displaying.
	 */
	public void recalculate(){
		//Adjust actuall start and end time by predecessors.
		for(Task t : this.taskTreeModel.getChildren()){
			t.adjustActualTimesByPredecessor();
		}
	}

	/**
	 * Add listener for Gantt chart model changes.
	 * 
	 * @param l
	 */
	public void addGanttModelListener(GanttModelListener l) {
		listenerList.add(GanttModelListener.class, l);
	}

	/**
	 * Get deadline of Gantt chart.
	 * 
	 * @return the deadline
	 */
	public Time getDeadline() {
		return Deadline;
	}

	/**
	 * Set deadline of Gantt chart.
	 * 
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Time deadline) {
		Time oldValue = this.Deadline;
		this.Deadline = deadline;
		this.fireGanttModelChange(this, "Deadline", oldValue, deadline);
	}

	/**
	 * Get kickoff time of Gantt chart.
	 * 
	 * @return the kickoffTime
	 */
	public Time getKickoffTime() {
		return KickoffTime;
	}

	/**
	 * Set kickoff time of Gantt chart.
	 * 
	 * @param kickoffTime the kickoffTime to set
	 */
	public void setKickoffTime(Time kickoffTime) {
		Time oldValue = this.KickoffTime;
		this.KickoffTime = kickoffTime;
		this.fireGanttModelChange(this, "KickoffTime", oldValue, kickoffTime);
	}

	/**
	 * Get <code>TaskTreeModel</code>.
	 * 
	 * @return the taskList
	 */
	public TaskTreeModel getTaskTreeModel() {
		return taskTreeModel;
	}

	/**
	 * Get IDs of selected tasks.
	 * @return
	 */
	public int[] getSelectedIds() {
		return selectedIds;
	}

	/**
	 * Set IDs of selected tasks.
	 * @param selectedIds
	 */
	public void setSelectedIds(int[] selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	// No setter because it has the risk that user change to another TaskTreeModel, which is not
	// registered event.
	//    /**
	//     * @param taskList the taskList to set
	//     */
	//    public void setTaskTreeModel(TaskTreeModel taskTreeModel) {
	//        this.taskTreeModel = taskTreeModel;
	//        this.fireStateChange();
	//    }

	/*
	 * 
	 */
	protected void fireGanttModelChange(Object source, String propertyName, Object oldValue, Object newValue) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == GanttModelListener.class) {
				ganttModelChangeEvent = new GanttModelChangeEvent(source, propertyName, oldValue, newValue);
				((GanttModelListener) listeners[i + 1]).ganttModelChanged(ganttModelChangeEvent);
			}
		}
	}

}
