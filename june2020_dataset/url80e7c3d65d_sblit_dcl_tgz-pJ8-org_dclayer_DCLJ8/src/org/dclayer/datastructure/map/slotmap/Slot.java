package org.dclayer.datastructure.map.slotmap;

public abstract class Slot<I> {

	private int slot;
	private String name;
	
	public Slot(int slot) {
		this.slot = slot;
		this.name = this.getClass().getSimpleName();
	}
	
	public final int getSlot() {
		return slot;
	}
	
	@Override
	public final String toString() {
		return String.format("%s %d (%s)", name, slot, contentToString());
	}
	
	public abstract I getSearchObject();
	
	public abstract String contentToString();
	
}
