package org.swiftgantt.ui.task;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.swiftgantt.model.Task;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Use task id as key to keep location of all tasks in Gantt Chart<br/>
 * @author Yuxing Wang
 * @since 0.3.4
 */
public class TaskLocationManager {

	protected Logger logger = null;
	private static TaskLocationManager ins = new TaskLocationManager();
	private Map<Integer, Shape> shapeMap = new HashMap<Integer, Shape>();
	private Map<Integer, Task> taskMap = new HashMap<Integer, Task>();

	private TaskLocationManager() {
		logger = LogManager.getLogger(TaskLocationManager.class);
	}

	public static TaskLocationManager getInstance() {
		return ins;
	}

	public void clear() {
		this.shapeMap.clear();
		this.taskMap.clear();
	}

	/**
	 * 
	 * @param task
	 * @param shape
	 */
	public void addTask(Task task, Shape shape) {
		shapeMap.put(task.getId(), shape);
		taskMap.put(task.getId(), task);
	}

	/**
	 * Get id of active task.
	 * @param p
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getActiveTaskId(Point2D p) {
		Iterator it = shapeMap.keySet().iterator();
		for (; it.hasNext();) {
			Integer key = (Integer) it.next();
			Shape s = shapeMap.get(key);
			if (s.contains(p)) {
				return key.intValue();
			}
		}
		return 0;
	}

	/**
	 * Get active task.
	 * @param p
	 * @return
	 */
	public Task getActiveTask(Point2D p) {
		return taskMap.get(this.getActiveTaskId(p));
	}

	/**
	 * Get all tasks.
	 * @return
	 */
	public Collection<Task> getAllTasks() {
		return this.taskMap.values();
	}

	/**
	 * Get location of all tasks.
	 * @return
	 */
	public Collection<Shape> getAllLocations() {
		return this.shapeMap.values();
	}
}
