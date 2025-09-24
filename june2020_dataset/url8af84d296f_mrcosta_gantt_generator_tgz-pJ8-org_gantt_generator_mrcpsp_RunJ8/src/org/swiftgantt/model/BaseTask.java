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
import javax.swing.tree.TreeNode;

import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.event.PredecessorChangeEvent;
import org.swiftgantt.event.PredecessorChangeListener;

@SuppressWarnings("unchecked")
public abstract class BaseTask<E extends BaseTask> extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	protected static int idCounter = 1;
	protected Logger logger = null;

	protected int id = -1;
	protected String name;
	protected String description;
	protected Color backcolor;
	protected Time start;
	protected Time actualStart;
	protected Time end;
	protected Time actualEnd;
	protected int progress = 0;
	protected int level = 0;

	protected TaskTreeModel treeModel = null;
	protected List<E> predecessors = new ArrayList<E>();
	protected List<E> subsequences = new ArrayList<E>();

	protected transient PropertyChangeEvent propChangeEvent = null;
	protected transient PredecessorChangeEvent predecessorChangeEvent = null;
	protected transient EventListenerList listenerList = new EventListenerList();

	public BaseTask() {
		logger = LogManager.getLogger(this.getClass());
		this.id = idCounter++;
	}

	public void add(E... tasks) {
		if (logger.isDebugEnabled()) {
			logger.info("Add " + tasks.length + " sub-tasks to parent task: <" + this.toString() + ">");
		}
		for (int i = 0; i < tasks.length; i++) {
			this.add(tasks[i]);
		}
	}

	public void add(E subTask) {
		super.add(subTask);
		subTask.setTreeModel(this.treeModel);
		if (this.getLevel() > 0) {
			subTask.setLevel(this.getLevel() + 1);
		}
		if (logger.isDebugEnabled()) {
			logger.info("Add task : <" + subTask.toString() + "> to parent task: <" + this.toString() + ">");
		}
		PropertyChangeListener[] listeners = this.listenerList.getListeners(PropertyChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			subTask.addPropertyChangeListener(listeners[i]);
		}
		this.firePropertyChanged("SubTask added", null, subTask);
	}

	public int getTasksCount() {
		int count = 0;
		Enumeration<TreeNode> e = this.children();
		for (Task curTask = null; e.hasMoreElements(); count++) {
			curTask = (Task) e.nextElement();
			count += curTask.getTasksCount();
		}
		return count;
	}

	public List<Task> getChildren() {
		List<Task> ret = new ArrayList<Task>();
		Enumeration<TreeNode> e = this.children();
		while (e.hasMoreElements()) {
			Task t = (Task) e.nextElement();
			ret.add(t);
		}
		return ret;
	}

	public List<E> getPredecessors() {
		return UnmodifiableList.decorate(predecessors);
	}

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

	public void addPredecessor(E predecessor) {
		if (predecessor == this) {
			throw new InvalidParameterException("You can't make any task to be it's own predecessor task");
		}
		predecessors.add(predecessor);
		predecessor.subsequences.add(this);
		firePredecessorChanged(predecessor);
	}

	public void addPredecessorChangeListener(PredecessorChangeListener l) {
		this.listenerList.add(PredecessorChangeListener.class, l);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.listenerList.add(PropertyChangeListener.class, l);
		Enumeration<TreeNode> tasks = this.children();
		while (tasks.hasMoreElements()) {
			E subTask = (E) tasks.nextElement();
			subTask.addPropertyChangeListener(l);
		}
	}

	public void removePredecessorChangeListener(PredecessorChangeListener l) {
		this.listenerList.remove(PredecessorChangeListener.class, l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.listenerList.remove(PropertyChangeListener.class, l);
		Enumeration<TreeNode> tasks = this.children();
		while (tasks.hasMoreElements()) {
			E subTask = (E) tasks.nextElement();
			subTask.removePropertyChangeListener(l);
		}
	}

	protected void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PropertyChangeListener.class) {
				propChangeEvent = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
				((PropertyChangeListener) listeners[i + 1]).propertyChange(propChangeEvent);
			}
		}
	}

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
		Enumeration<TreeNode> enu = this.children();
		while (enu.hasMoreElements()) {
			E child = (E) enu.nextElement();
			child.setTreeModel(treeModel);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		Object oldValue = this.id;
		this.id = id;
		this.firePropertyChanged("ID", oldValue, id);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		Object oldValue = this.description;
		this.description = description;
		this.firePropertyChanged("Description", oldValue, description);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Object oldValue = this.name;
		this.name = name;
		this.firePropertyChanged("Name", oldValue, name);
	}

	@Override
	public int getLevel() {
		return level;
	}

	protected void setLevel(int level) {
		this.level = level;
		Enumeration<TreeNode> enu = this.children();
		while (enu.hasMoreElements()) {
			Task child = (Task) enu.nextElement();
			child.setLevel(level + 1);
		}
	}

	public Color getBackcolor() {
		return backcolor;
	}

	public void setBackcolor(Color backcolor) {
		this.backcolor = backcolor;
	}
}
