package org.swiftgantt.model;

import org.swiftgantt.common.Time;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.event.EventListenerList;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.event.PredecessorChangeEvent;
import org.swiftgantt.event.PredecessorChangeListener;

/**
 * Base class of the Task. With task's basic information, predecessors list and subsequences list
 * ,reference of parent object {@link org.swiftgantt.model.TaskTreeModel} .<br/>
 * {@link java.beans.PropertyChangeEvent} event raised after:<br/>
 * new sub-task added, task id changed, task name changed and description changed.<br/>
 * {@link org.swiftgantt.event.PredecessorChangeEvent} event raised after:<br/>
 * new predecessor task added.<br/>
 * @author Yuxing Wang
 * @since 0.3.0
 */
@SuppressWarnings("unchecked")
public abstract class BaseTask<E extends BaseTask> extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	protected static int idCounter = 1;

	protected Logger logger = null;

	// Properties
	protected int id = -1;

	protected String name;

	protected String description;

	protected Color backcolor;

	protected Time start;

	protected Time actualStart;

	protected Time end;

	protected Time actualEnd;

	protected int progress = 0;

	protected int level = 0; // Level in the task tree. the first level is 1;

	// Parent tree model.
	protected TaskTreeModel treeModel = null;

	protected List<E> predecessors = new ArrayList<E>();

	protected List<E> subsequences = new ArrayList<E>();

	// Internal
	protected transient PropertyChangeEvent propChangeEvent = null;
	protected transient PredecessorChangeEvent predecessorChangeEvent = null;
	protected transient EventListenerList listenerList = new EventListenerList();

	public BaseTask() {
		logger = LogManager.getLogger(this.getClass());
		this.id = idCounter++;//Assign an internal ID for task.
	}

	/**
	 * Add sub-tasks to this <code>Task</code>.
	 * 
	 * @param tasks
	 */
	public void add(E... tasks) {
		if (logger.isDebugEnabled()) {
			logger.info("Add " + tasks.length + " sub-tasks to parent task: <" + this.toString() + ">");
		}
		for (int i = 0; i < tasks.length; i++) {
			this.add(tasks[i]);
		}
	}

	/**
	 * Add sub task to this <code>Task</code>.
	 * 
	 * @param subTask
	 */
	public void add(E subTask) {
		super.add(subTask);
		subTask.setTreeModel(this.treeModel);
		if (this.getLevel() > 0) {
			subTask.setLevel(this.getLevel() + 1);
		}
		if (logger.isDebugEnabled()) {
			logger.info("Add task : <" + subTask.toString() + "> to parent task: <" + this.toString() + ">");
		}
		// Warnning, set listener here makes that the sub task must be added to a task already in the
		// TaskTreeModel, otherwise the TaskTreeModel will no get any event from this sub task. (This has been fixed, see method addPropertyChangeListener()
		PropertyChangeListener[] listeners = this.listenerList.getListeners(PropertyChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			subTask.addPropertyChangeListener(listeners[i]);
		}
		this.firePropertyChanged("SubTask added", null, subTask);
	}

	/**
	 * Get count of sub-tasks in all levels.
	 * 
	 * @return
	 */
	public int getTasksCount() {
		int count = 0;
		Enumeration<Task> e = this.children();
		for (Task curTask = null; e.hasMoreElements(); count++) {
			curTask = (Task) e.nextElement();
			count += curTask.getTasksCount();//Recursive
		}
		return count;
	}

	/**
	 * Get children tasks.
	 * 
	 * @return
	 */
	public List<Task> getChildren() {
		List<Task> ret = new ArrayList<Task>();
		Enumeration e = this.children();
		while (e.hasMoreElements()) {
			Task t = (Task) e.nextElement();
			ret.add(t);
		}
		return ret;
	}

	/**
	 * Get predecessors of this <code>Task</code>.
	 * 
	 * @return the predecessors
	 */
	public List<E> getPredecessors() {
		return UnmodifiableList.decorate(predecessors);
	}

	/*
	 * Get predecessor task that has the latest end time.
	 */
	protected E getLatestPredecessor() {
		Time t = new Time(1970, 1, 0);
		E ret = null;
		for (E task : predecessors) {
			if (t.before(task.actualEnd)) {
				t = task.actualEnd;
				ret = task;
			}
		}
		return ret;
	}

	/**
	 * Add predecessor to this <code>Task</code>.
	 * 
	 * @param predecessors the predecessors tasks to set.
	 */
	public void addPredecessor(E predecessor) {
		if (predecessor == this) {
			throw new InvalidParameterException("You can't make any task to be it's own predecessor task");
		}
		predecessors.add(predecessor);
		predecessor.subsequences.add(this);// Make this task to be the predecessor's subsequence task.
		firePredecessorChanged(predecessor);
	}

	/**
	 * Add listener for predecessor task changes event.
	 * 
	 * @param l
	 */
	public void addPredecessorChangeListener(PredecessorChangeListener l) {
		this.listenerList.add(PredecessorChangeListener.class, l);
	}

	/**
	 * Add listener to this Task and all his sub-tasks.
	 * 
	 * @param l
	 */
	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.listenerList.add(PropertyChangeListener.class, l);
		Enumeration<E> tasks = this.children();
		for (E subTask; tasks.hasMoreElements();) {
			subTask = tasks.nextElement();
			subTask.addPropertyChangeListener(l);
		}
	}

	public void removePredecessorChangeListener(PredecessorChangeListener l) {
		this.listenerList.remove(PredecessorChangeListener.class, l);
	}

	/**
	 * Remove listener from this Task and all his sub-tasks.
	 * 
	 * @param l
	 */
	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.listenerList.remove(PropertyChangeListener.class, l);
		Enumeration<E> tasks = this.children();
		for (E subTask; tasks.hasMoreElements();) {
			subTask = tasks.nextElement();
			subTask.removePropertyChangeListener(l);
		}
	}

	/*
	 * Fire property change.
	 */
	protected void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PropertyChangeListener.class) {
				propChangeEvent = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
				((PropertyChangeListener) listeners[i + 1]).propertyChange(propChangeEvent);
			}
		}
	}

	/*
	 * Fire property change.
	 */
	protected void firePredecessorChanged(BaseTask predecessor) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PredecessorChangeListener.class) {
				predecessorChangeEvent = new PredecessorChangeEvent(this, predecessor);
				((PredecessorChangeListener) listeners[i + 1]).predecessorChanged(predecessorChangeEvent);
			}
		}
	}

	protected void setTreeModel(TaskTreeModel treeModel) {
		this.treeModel = treeModel;
		// Set tree moble for all children tasks
		Enumeration<E> enu = this.children();
		for (int i = 0; enu.hasMoreElements(); i++) {
			E child = (E) enu.nextElement();
			child.setTreeModel(treeModel);
		}
	}

	/**
	 * Get the id of this Task.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the id of this Task.
	 * 
	 * @return the id
	 */
	public void setId(int id) {
		Object oldValue = this.id;
		this.id = id;
		this.firePropertyChanged("ID", oldValue, id);
	}

	/**
	 * Get description of this <code>Task</code>.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description of this <code>Task</code>.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Object oldValue = this.description;
		this.description = description;
		this.firePropertyChanged("Description", oldValue, description);
	}

	/**
	 * Get name of this <code>Task</code>.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name of this <code>Task</code>.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		Object oldValue = this.name;
		this.name = name;
		this.firePropertyChanged("Name", oldValue, name);
	}

	/**
	 * Get level in the task tree.
	 */
	@Override
	public int getLevel() {
		return level;
	}

	/**
	 * Don not set level directly.
	 * 
	 * @param level
	 */
	protected void setLevel(int level) {
		this.level = level;
		Enumeration<Task> enu = this.children();
		for (int i = 0; enu.hasMoreElements(); i++) {
			Task child = (Task) enu.nextElement();
			child.setLevel(level + 1);
		}
	}

	/**
	 * Get backcolor of the task bar in Gantt Chart.
	 * 
	 * @return
	 */
	public Color getBackcolor() {
		return backcolor;
	}

	/**
	 * Set backcolor of this task bar in Gantt Chart, not applied to task group bar.
	 * 
	 * @param backcolor
	 */
	public void setBackcolor(Color backcolor) {
		this.backcolor = backcolor;
	}
}
