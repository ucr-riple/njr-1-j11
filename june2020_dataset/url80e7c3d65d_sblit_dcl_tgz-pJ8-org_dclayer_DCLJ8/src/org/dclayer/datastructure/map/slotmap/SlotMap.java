package org.dclayer.datastructure.map.slotmap;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class SlotMap<V, I, S extends Slot<I>> implements Iterable<S> {
	
	private ArrayList<S> slots = new ArrayList<>();
	private int numSlots = 0;
	
	public abstract S makeSlot(int slotId, V value);
	
	/**
	 * assigns the given slot value to an unoccupied slot, returning the slot object it was assigned to
	 * @param value the slot value to assign
	 * @return the slot object that the given slot value was assigned to
	 */
	public S add(V value) {
		int slotId = 0;
		S newSlotObj;
		for(S slotObj : slots) {
			if(slotObj == null) {
				slots.set(slotId, newSlotObj = this.makeSlot(slotId, value));
				return newSlotObj;
			}
			slotId++;
		}
		slots.add(newSlotObj = this.makeSlot(slotId, value));
		numSlots++;
		return newSlotObj;
	}
	
	/**
	 * assigns the given slot value to the given slot id, returning the newly created slot object
	 * @param slotId the slot id to assign the slot value to
	 * @param value the slot value to assign to the given slot
	 * @return the newly created slot object
	 */
	public S put(int slotId, V value) {
		S slotObj = this.makeSlot(slotId, value);
		if(slots.size() > slotId) {
			if(slots.get(slotId) == null) numSlots++;
			slots.set(slotId, slotObj);
		} else {
			numSlots++;
			while(slots.size() < slotId) {
				slots.add(null);
			}
			slots.add(slotObj);
		}
		return slotObj;
	}
	
	/**
	 * returns the slot object assigned to the given slot id or null if the given slot id is unoccupied
	 * @param slotId the slot id of which to return the assigned slot object
	 * @return the slot object assigned or null if the given slot id is unoccupied
	 */
	public S get(int slotId) {
		return slots.size() > slotId ? slots.get(slotId) : null;
	}
	
	/**
	 * tries to find a slot object that has the given search object assigned
	 * @param searchObject the search object to search for
	 * @return the slot object that has the given search object assigned or null if none
	 */
	public S find(Object searchObject) {
		for(S slotObj : slots) {
			if(searchObject.equals(slotObj.getSearchObject())) return slotObj;
		}
		return null;
	}
	
	/**
	 * clears the given slot with the given slot id, returning the slot object that was assigned before or null if the given slot id was already unoccupied
	 * @param slotId the slot id of the slot to clear
	 * @return the slot object that was assigned before or null if the slot id was already unoccupied
	 */
	public S remove(int slotId) {
		if(slots.size() <= slotId) return null;
		S slotObj = slots.get(slotId);
		if(slotObj != null) {
			slots.set(slotId, null);
			numSlots--;
			return slotObj;
		}
		return null;
	}
	
	public int size() {
		return numSlots;
	}
	
	@Override
	public Iterator<S> iterator() {
		return new Iterator<S>() {

			private Iterator<S> iterator = slots.iterator();
			private S next = null;
			{
				while(iterator.hasNext() && (this.next = iterator.next()) == null);
			}
			
			@Override
			public boolean hasNext() {
				return next != null;
			}

			@Override
			public S next() {
				S next = this.next;
				this.next = null;
				while(iterator.hasNext() && (this.next = iterator.next()) == null);
				return next;
			}

			@Override
			public void remove() {
				throw new RuntimeException("not implemented");
			}
			
		};
	}
	
}
