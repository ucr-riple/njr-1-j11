package org.dclayer.net.lla.priority;

import java.util.Collection;
import java.util.LinkedList;

import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;

public class LLAPriorityAspect implements HierarchicalLevel {

	private LLAPriority llaPriority;
	private LLAPriority.Type type;
	
	private LinkedList<Collection<LLAPriorityAspect>> containingCollections;
	
	private HierarchicalLevel parentHierarchicalLevel;
	private String identifier;
	
	private double value;
	private boolean lock;
	
	public LLAPriorityAspect(LLAPriority llaPriority, LLAPriority.Type type, HierarchicalLevel parentHierarchicalLevel, String identifier) {
		this.llaPriority = llaPriority;
		this.type = type;
		this.parentHierarchicalLevel = parentHierarchicalLevel;
		this.identifier = identifier;
		
		Log.debug(this, "created new priority aspect");
	}
	
	protected LLAPriority.Type getType() {
		return type;
	}
	
	protected String getIdentifier() {
		return identifier;
	}
	
	protected double getValue() {
		return value;
	}
	
	protected boolean getLock() {
		return lock;
	}
	
	public void setLock(boolean lock) {
		if(this.lock == lock) return;
		Log.debug(this, "setting lock to %s", lock);
		this.lock = lock;
		llaPriority.update();
	}
	
	public void update(double value) {
		if(this.value == value) return;
		Log.debug(this, "setting value to %.8f", value);
		this.value = value;
		llaPriority.update();
	}
	
	public void lock() {
		setLock(true);
	}
	
	public void unlock() {
		setLock(false);
	}
	
	public void drop() {
		dropExcept(null);
	}
	
	public synchronized void dropExcept(Object exceptCollection) {
		if(containingCollections != null) {
			for(Collection<LLAPriorityAspect> collection : containingCollections) {
				if(collection == exceptCollection) continue;
				synchronized (collection) {
					collection.remove(this);
				}
			}
		}
		llaPriority.removeLLAPriorityAspect(this);
	}
	
	public synchronized void addTo(Collection<LLAPriorityAspect> collection) {
		synchronized (collection) {
			collection.add(this);
		}
		if(containingCollections == null) {
			containingCollections = new LinkedList<>();
		}
		containingCollections.add(collection);
	}
	
	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return parentHierarchicalLevel;
	}
	
	@Override
	public String toString() {
		return String.format("LLAPriorityAspect(id %s, type %s, %slocked, value %.8f", identifier, type, lock ? "" : "not ", value);
	}
	
}
