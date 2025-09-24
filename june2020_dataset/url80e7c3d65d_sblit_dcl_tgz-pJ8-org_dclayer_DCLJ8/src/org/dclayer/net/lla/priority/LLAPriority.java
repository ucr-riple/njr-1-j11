package org.dclayer.net.lla.priority;

import java.util.LinkedList;

import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.net.lla.CachedLLA;

public class LLAPriority {
	
	public static enum Type {
		ROUTING(0x400),
		REMOTE(0x200);
		
		private long max;
		
		Type(long max) {
			this.max = max;
		}
		
		private long getMax() {
			return max;
		}
	}
	
	//
	
	private CachedLLA cachedLLA;
	
	private boolean lock = false;
	private long priority = 0;
	
	private LinkedList<LLAPriorityAspect> aspects = new LinkedList<>();
	
	private double[] typeValues = new double[Type.values().length];
	
	public LLAPriority(CachedLLA cachedLLA) {
		this.cachedLLA = cachedLLA;
	}
	
	public synchronized LLAPriorityAspect addLLAPriorityAspect(Type type, HierarchicalLevel hierarchicalLevel, String identifier) {
		LLAPriorityAspect llaPriorityAspect = new LLAPriorityAspect(this, type, hierarchicalLevel, identifier);
		aspects.add(llaPriorityAspect);
		return llaPriorityAspect;
	}
	
	public synchronized void removeLLAPriorityAspect(LLAPriorityAspect aspect) {
		aspects.remove(aspect);
		update();
	}
	
	protected synchronized void update() {

		boolean lock = false;
		
		for(LLAPriorityAspect aspect : aspects) {
			lock |= aspect.getLock();
			int i = aspect.getType().ordinal();
			typeValues[i] = Math.max(typeValues[i], aspect.getValue());
		}
		
		long priority = 0;
		
		for(int i = 0; i < typeValues.length; i++) {
			priority += typeValues[i] * Type.values()[i].getMax();
		}
		
		this.priority = priority;
		this.lock = lock;
		
	}
	
	public synchronized boolean locked() {
		return lock;
	}
	
	@Override
	public synchronized String toString() {
		
		if(lock) {
			
			StringBuilder stringBuilder = new StringBuilder(128);
			
			stringBuilder.append("(L:");
			
			boolean d = false;
			for(LLAPriorityAspect aspect : aspects) {
				if(aspect.getLock()) {
					if(d) {
						stringBuilder.append(',');
					}
					stringBuilder.append(aspect.getType().name());
					d = true;
				}
			}
			
			stringBuilder.append(')');
			
			stringBuilder.append(priority);
			
			return stringBuilder.toString();
			
		} else {
			
			return String.valueOf(priority);
			
		}
		
	}
	
}
